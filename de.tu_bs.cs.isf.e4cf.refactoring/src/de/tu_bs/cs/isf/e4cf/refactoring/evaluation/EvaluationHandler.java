package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.evaluation.dialog.GeneratorOptions;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneGenerator;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneGenerator.Variant;
import de.tu_bs.cs.isf.e4cf.refactoring.handler.ExtractionPipeline;
import de.tu_bs.cs.isf.e4cf.refactoring.handler.SynchronizationPipeline;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Granularity;

public class EvaluationHandler {

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager, SynchronizationPipeline pipeline,
			CloneGenerator cloneGenerator, ExtractionPipeline extractionPipeline) {

		ActionCallback actionCallback = new ActionCallback() {

			@Override
			public void handle(List<ActionScope> actionScopes) {			

			}
		};

		GeneratorOptions generatorOptions = new GeneratorOptions();
		generatorOptions.variants = 1;
		generatorOptions.isSyntaxSafe = false;

		List<Metric> metrics = getMetrics();

		Map<Metric, List<StatsLogger>> logs = new HashMap<Metric, List<StatsLogger>>();

		for (Metric metric : metrics) {

			System.out.println("Metric: " + metric.getGranularity() + " Synchronization degree: "
					+ metric.getSynchronizationDegree() + " Type 2 degree: " + metric.getType2AttributeChangeDegree()
					+ " Variant degree: " + metric.getVariantChangeDegree());

			Tree tree = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionFromExplorer());

			logs.put(metric, new ArrayList<StatsLogger>());

			generatorOptions.modificationRatioPercentage = (int) Math.round(metric.getType2AttributeChangeDegree());
			generatorOptions.variantChangeDegree = metric.getVariantChangeDegree();

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
			CloneModel cloneModel = extractionPipeline.pipe(tree, granularityCallback, 0.15f);

			Tree currentTree = null;
			for (int i = 1; i <= 25; i++) {

				System.out.println("Revision: " + i);

				StatsLogger logger = StatsLogger.create();
				logs.get(metric).add(logger);				

				//Node target = selectTarget(cloneTree, metric.getGranularity());

				List<Variant> variants = cloneGenerator.go(cloneTree, generatorOptions, false, false);
				Variant variant1 = variants.get(1);
				currentTree = variant1.tree;

				cloneModel = pipeline.pipe(cloneModel, variant1.tree, granularityCallback, actionCallback,
						synchronizationCallback, 0.15f);

				cloneTree = currentTree.cloneTree();

			}

		}

	}

	private Node selectTarget(Tree tree, String granularity) {
		List<Node> candidates = tree.getNodesForType(granularity);
		List<Node> filteredNodes = new ArrayList<Node>();
		for (Node node : candidates) {
			Node parent = node.getParent();
			boolean isParentFromSameLayer = false;
			while (parent != null) {
				if (parent.getNodeType().equals(granularity)) {
					isParentFromSameLayer = true;
				}
				parent = parent.getParent();
			}

			if (!isParentFromSameLayer) {
				filteredNodes.add(node);
			}

		}

		Random random = new Random();
		int randomIndex = random.nextInt(filteredNodes.size());
		return filteredNodes.get(randomIndex);

	}

	@CanExecute
	public boolean canExecute(ServiceContainer services, RCPSelectionService selectionService) {
		return services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() == 1;
	}
	

	private List<Metric> getMetrics() {

		String[] granularities = new String[] { "CompilationUnit", "MethodDeclaration", "IfStmt" };
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
