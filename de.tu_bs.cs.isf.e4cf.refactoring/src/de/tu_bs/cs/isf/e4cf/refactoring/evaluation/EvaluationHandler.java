package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.evaluation.dialog.GeneratorOptions;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneGenerator;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneGenerator.Variant;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ClusterEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.handler.ExtractionPipeline;
import de.tu_bs.cs.isf.e4cf.refactoring.handler.SynchronizationPipeline;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Granularity;
import de.tu_bs.cs.isf.e4cf.refactoring.util.ProcessUtil;

public class EvaluationHandler {

	private Semaphore semaphore;

	private CloneGenerator cloneGenerator;

	private ExtractionPipeline extractionPipeline;

	private SynchronizationPipeline synchronizationPipeline;

	private Map<Metric, List<StatsLogger>> logs;

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager,
			SynchronizationPipeline synchronizationPipeline, CloneGenerator cloneGenerator,
			ExtractionPipeline extractionPipeline) {

		this.cloneGenerator = cloneGenerator;
		this.synchronizationPipeline = synchronizationPipeline;
		semaphore = new Semaphore(1);

		List<Metric> metrics = getMetrics();
		ExecutorService executorService = Executors.newFixedThreadPool(20);
		List<Callable<Boolean>> evaluationCallables = new ArrayList<Callable<Boolean>>();
		for (Metric metric : metrics) {
			Tree tree = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionFromExplorer());

			Callable<Boolean> callable = () -> {

				try {

					evaluate(tree, metric, 10);

					return true;
				} catch (Exception ex) {
					ex.printStackTrace();

				}

				return false;
			};

			evaluationCallables.add(callable);

		}

		try {
			List<Future<Boolean>> futures = executorService.invokeAll(evaluationCallables);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@CanExecute
	public boolean canExecute(ServiceContainer services, RCPSelectionService selectionService) {
		return services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() == 1;
	}

	public void evaluate(Tree tree, Metric metric, int numberOfRevisions) {
		try {

			System.out.println("Metric: " + metric.getGranularity() + " Synchronization degree: "
					+ metric.getSynchronizationDegree() + " Type 2 degree: " + metric.getType2AttributeChangeDegree()
					+ " Variant degree: " + metric.getVariantChangeDegree());

			ActionCallback actionCallback = new ActionCallback() {

				@Override
				public void handle(List<ActionScope> actionScopes) {

				}
			};

			GeneratorOptions generatorOptions = new GeneratorOptions();
			generatorOptions = new GeneratorOptions();
			generatorOptions.variants = 1;
			generatorOptions.isSyntaxSafe = false;
			generatorOptions.modificationRatioPercentage = (int) Math.round(metric.getType2AttributeChangeDegree());
			generatorOptions.variantChangeDegree = metric.getVariantChangeDegree();

			ProcessUtil processUtil = new ProcessUtil();
			ClusterEngine.configureScriptPath(false);
			processUtil.startProcess(ClusterEngine.getScriptPath());

			logs.put(metric, new ArrayList<StatsLogger>());

			GranularityCallback granularityCallback = new GranularityCallback() {
				@Override
				public void handle(List<Granularity> granularities) {

					for (Granularity granularity : granularities) {
						if (granularity.getLayer().equals(metric.getGranularity())) {
							granularity.setRefactor(true);
						} else {
							granularity.setRefactor(false);
						}
					}

				}
			};

			SynchronizationCallback synchronizationCallback = new SynchronizationCallback() {
				@Override
				public void handle(Map<ActionScope, List<ActionScope>> synchronizationScopes) {

					List<ActionScope> allSynchronizations = new ArrayList<ActionScope>();
					for (List<ActionScope> actionScopes : synchronizationScopes.values()) {
						allSynchronizations.addAll(actionScopes);
					}

					for (ActionScope actionScope : allSynchronizations) {
						actionScope.setApply(false);
					}
					Random random = new Random();

					int numberOfElements = (int) (allSynchronizations.size()
							* (metric.getSynchronizationDegree() / 100));

					for (int i = 0; i < numberOfElements; i++) {
						int randomIndex = random.nextInt(allSynchronizations.size());
						ActionScope actionScope = allSynchronizations.get(randomIndex);
						actionScope.setApply(true);
					}

				}
			};

			Tree cloneTree = tree.cloneTree();
			Tree currentTree = null;

			CloneCallbackImpl cloneCallback = new CloneCallbackImpl(currentTree);

			CloneModel cloneModel = extractionPipeline.pipe(tree, granularityCallback, 0.15f, processUtil);

			for (int i = 1; i <= numberOfRevisions; i++) {

				long endTime = 0;
				long startTime = System.nanoTime();

				System.out.println("Revision: " + i);

				StatsLogger logger = new StatsLogger();
				logs.get(metric).add(logger);

				semaphore.acquire();

				List<Variant> variants = cloneGenerator.go(cloneTree, generatorOptions, false, false);
				Variant variant1 = variants.get(1);

				semaphore.release();

				startTime = System.nanoTime();

				cloneModel = synchronizationPipeline.pipe(cloneModel, variant1.tree, granularityCallback,
						actionCallback, synchronizationCallback, 0.15f, logger, processUtil, cloneCallback);

				endTime = System.nanoTime();

				cloneTree = cloneCallback.getCurrentTree();

				logger.averageInterSimilarity = EvaluationUtil.getAverageInterSimilarity(cloneModel.getComponents());
				logger.averageIntraSimilarity = EvaluationUtil.getAverageIntraSimilarity(cloneModel.getComponents());
				logger.duration += (endTime - startTime);

			}

			processUtil.stop();
		} catch (Exception ex) {

		}

	}

	public void measurePerformance(Tree tree) {
		
		Metric metric = new Metric(100, 5, 100, "MethodDeclaration");				
		evaluate(tree, metric, 100);

	}

	private List<Metric> getMetrics() {

		String[] granularities = new String[] { "MethodDeclaration" };
		List<Metric> metrics = new ArrayList<Metric>();

		for (int variantChangeDegree = 1; variantChangeDegree <= 5; variantChangeDegree++) {

			for (float type2AttributeChangeDegree = 25; type2AttributeChangeDegree <= 100; type2AttributeChangeDegree += 25) {

				for (float synchronizationDegree = 0; synchronizationDegree <= 100; synchronizationDegree += 25) {

					for (String granularity : granularities) {

						Metric metric = new Metric(type2AttributeChangeDegree, variantChangeDegree,
								synchronizationDegree, granularity);
						metrics.add(metric);

					}

				}
			}

		}

		return metrics;
	}

}
