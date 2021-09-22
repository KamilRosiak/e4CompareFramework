package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.io.FileUtils;
import org.eclipse.e4.core.di.annotations.Creatable;

import com.google.common.io.Files;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.core.import_export.services.gson.GsonExportService;
import de.tu_bs.cs.isf.e4cf.evaluation.dialog.GeneratorOptions;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneGenerator;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneGenerator.Variant;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ClusterEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.handler.IntegrationPipeline;
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Granularity;
import de.tu_bs.cs.isf.e4cf.refactoring.model.MultiSetTree;
import de.tu_bs.cs.isf.e4cf.refactoring.util.ProcessUtil;

@Singleton
@Creatable
public class Evaluation {

	private IntegrationPipeline integrationPipeline;

	private CloneGenerator cloneGenerator;

	private Map<String, AggregatedClusteringResult> aggregatedClusteringResults;

	private Map<String, AggregatedStabilityResult> aggregatedStabilityResults;

	private float threshold = 0.15f;

	private static final String outputDirectory = "";

	@Inject
	public Evaluation(IntegrationPipeline integrationPipeline, CloneGenerator cloneGenerator,
			ClusterEngine clusterEngine, GsonExportService exportService) {
		super();
		this.integrationPipeline = integrationPipeline;
		this.cloneGenerator = cloneGenerator;
		this.aggregatedClusteringResults = new HashMap<String, AggregatedClusteringResult>();
		this.aggregatedStabilityResults = new HashMap<String, AggregatedStabilityResult>();
	}

	private void clean() {
		try {
			File file = new File(outputDirectory);
			FileUtils.deleteDirectory(file);
			file = new File(outputDirectory);
			file.mkdir();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void evaluate(Tree seed) {

		clean();

		List<Metric> metrics = getMetrics();
		ClusterEngine.configureScriptPath(false);
		ClusterEngine.THRESHOLD = threshold;

		System.out.println("Starting clone model extraction");
		ClusterEngine clusterEngine = new ClusterEngine();
		ProcessUtil processUtil = new ProcessUtil();
		processUtil.startProcess(clusterEngine.getScriptPath());

		for (int i = 0; i < metrics.size(); i++) {

			Tree clonedSeed = seed.cloneTree();
			Metric metric = metrics.get(i);
			CloneModel cloneModel = generateCloneModel(clonedSeed, metric, processUtil);
			metric.setVariants(null);

			evaluate(cloneModel, metric, clusterEngine, processUtil);

			metric.saveClusteringResults();
			List<Tree> restoredTrees = cloneModel.restoreIntegratedTrees();
			metric.saveResults(restoredTrees, new GsonExportService());

		}

		for (Metric metric : metrics) {

			if (!aggregatedStabilityResults.containsKey(metric.getGranularity().getLayer())) {
				aggregatedStabilityResults.put(metric.getGranularity().getLayer(), new AggregatedStabilityResult());
			}

			AggregatedStabilityResult aggregatedStabilityResult = aggregatedStabilityResults
					.get(metric.getGranularity().getLayer());

			for (StabilityResult stabilityResult : metric.getStabilityResults()) {
				aggregatedStabilityResult.clusterMerges += stabilityResult.getClusterMerges()
						.get(metric.getGranularity().getLayer());
				aggregatedStabilityResult.clusterSplits += stabilityResult.getClusterSplits()
						.get(metric.getGranularity().getLayer());
			}

		}

		saveAggregatedResults();

	}

	private void evaluate(CloneModel cloneModel, Metric metric, ClusterEngine clusterEngine, ProcessUtil processUtil) {

		Set<Granularity> granularities = new HashSet<Granularity>();
		granularities.add(metric.getGranularity());

		for (int i = 0; i < 20; i++) {

			System.out.println("Run " + i);
			StabilityResult stabilityResult = new StabilityResult(granularities);

			int changeType = 0;
			if (metric.isAttributeChange() && metric.isNodeChange()) {
				Random random = new Random();
				changeType = random.nextInt(2);
			} else if (metric.isAttributeChange()) {
				changeType = 0;
			} else if (metric.isNodeChange()) {
				changeType = 1;
			}

			if (changeType == 0) {

				Node target = null;
				do {
					target = getRandomNode(cloneModel, metric.getGranularity().getLayer());
				} while (target.getAttributes().size() == 0);

				Attribute attribute = getRandomAttribute(target);
				Random random = new Random();
				int method = random.nextInt(8 - 4) + 4;

				ChangeAction changeAction = null;

				if (method == ChangeActionType.ADD_ATTRIBUTE_VALUE.getValue()) {
					System.out.println("Action " + ChangeActionType.ADD_ATTRIBUTE_VALUE);
					Value value = new StringValueImpl(getRandomString());
					changeAction = new AddAttributeValueAction(target, attribute, value);
					cloneModel.addAttributeValue(target, attribute, value);
				} else if (method == ChangeActionType.EDIT_ATTRIBUTE_KEY.getValue()) {
					System.out.println("Action " + ChangeActionType.EDIT_ATTRIBUTE_KEY);
					String key = getRandomString();
					changeAction = new EditAttributeKeyAction(target, attribute, key);
					cloneModel.editAttributeKey(target, attribute, key);
				} else if (method == ChangeActionType.EDIT_ATTRIBUTE_VALUE.getValue()) {

					System.out.println("Action " + ChangeActionType.EDIT_ATTRIBUTE_VALUE);

					Value value = new StringValueImpl(getRandomString());
					changeAction = new EditAttributeValueAction(target, attribute, value);
					cloneModel.editAttributeValue(target, attribute, value);
				} else if (method == ChangeActionType.DELETE_ATTRIBUTE_VALUE.getValue()) {

					System.out.println("Action " + ChangeActionType.DELETE_ATTRIBUTE_VALUE);

					changeAction = new DeleteAttributeAction(target, attribute);
					cloneModel.removeAttribute(target, attribute);
				}

				stabilityResult.setChangeAction(changeAction);

				clusterEngine.analyzeCloneModel(cloneModel, processUtil, stabilityResult);
				metric.getStabilityResults().add(stabilityResult);

			} else if (changeType == 1) {
				Random random = new Random();
				int method = random.nextInt(4);

				Node target = null;

				if (method == ChangeActionType.DELETE_ATTRIBUTE.getValue()) {
					do {
						target = getRandomNode(cloneModel, metric.getGranularity().getLayer());
					} while (target.getAttributes().size() == 0);
				} else {
					target = getRandomNode(cloneModel, metric.getGranularity().getLayer());
				}

				ChangeAction changeAction = null;

				if (method == ChangeActionType.ADD_ATTRIBUTE.getValue()) {
					System.out.println("Action " + ChangeActionType.ADD_ATTRIBUTE);
					Attribute newAttribute = generateRandomAttribute();
					changeAction = new AddAttributeAction(target, newAttribute);
					cloneModel.addAttribute(target, newAttribute);
				} else if (method == ChangeActionType.ADD_CHILD_NODE.getValue()) {
					System.out.println("Action " + ChangeActionType.ADD_CHILD_NODE);
					Node child = generateRandomNode();
					changeAction = new AddChildNodeAction(target, child);
					int position = target.getChildren().size() == 0 ? 0 : target.getChildren().size();

					cloneModel.addChild(target, child, position);
				} else if (method == ChangeActionType.DELETE_NODE.getValue()) {
					System.out.println("Action " + ChangeActionType.DELETE_NODE);
					changeAction = new DeleteNodeAction(target);
					cloneModel.delete(target);
				} else if (method == ChangeActionType.DELETE_ATTRIBUTE.getValue()) {

					System.out.println("Action " + ChangeActionType.DELETE_ATTRIBUTE);
					Attribute attribute = getRandomAttribute(target);
					changeAction = new DeleteAttributeAction(target, attribute);
					cloneModel.removeAttribute(target, attribute);
				}
				stabilityResult.setChangeAction(changeAction);
				clusterEngine.analyzeCloneModel(cloneModel, processUtil, stabilityResult);
				metric.getStabilityResults().add(stabilityResult);

			}

		}

	}

	private CloneModel generateCloneModel(Tree seed, Metric metric, ProcessUtil processUtil) {

		Set<Granularity> granularities = new HashSet<Granularity>();
		CloneModel cloneModel = null;
		List<Variant> variants = cloneGenerator.go(seed, metric.getGeneratorOptions(), false, false);

		metric.setVariants(variants);
		metric.saveVariants(new GsonExportService());

		granularities.add(metric.getGranularity());
		Tree baseTree = variants.get(0).tree;

		cloneModel = integrationPipeline.pipe(baseTree, granularities, processUtil);

		ClusteringResult clusteringResult = getClusteringResult(cloneModel);
		aggregateClusteringResult(metric, cloneModel, clusteringResult);
		metric.getClusteringResults().add(clusteringResult);

		for (int i = 1; i < variants.size(); i++) {
			CloneModel cloneModel2 = integrationPipeline.pipe(variants.get(i).tree, cloneModel.getGranularities(),
					processUtil);

			clusteringResult = getClusteringResult(cloneModel2);
			aggregateClusteringResult(metric, cloneModel2, clusteringResult);

			metric.getClusteringResults().add(clusteringResult);

			cloneModel.merge(cloneModel2);

		}

		metric.getVariants().clear();
		variants = null;

		return cloneModel;

	}

	private ClusteringResult getClusteringResult(CloneModel cloneModel2) {
		ClusteringResult clusteringResult = new ClusteringResult();
		measureAverageInterSimilarity(cloneModel2.getAllComponents(), clusteringResult);
		measureAverageIntraSimilarity(cloneModel2.getAllComponents(), clusteringResult);
		clusteringResult.numberOfClusters = cloneModel2.getAllComponents().size();
		clusteringResult.averageClusterSize = measureAverageClusterSize(cloneModel2.getAllComponents());
		return clusteringResult;
	}

	private void aggregateClusteringResult(Metric metric, CloneModel cloneModel2, ClusteringResult clusteringResult) {

		if (!aggregatedClusteringResults.containsKey(metric.getGranularity().getLayer())) {
			aggregatedClusteringResults.put(metric.getGranularity().getLayer(), new AggregatedClusteringResult());
		}

		AggregatedClusteringResult aggregatedClusteringResult = aggregatedClusteringResults
				.get(metric.getGranularity().getLayer());

		aggregatedClusteringResult.models++;
		aggregatedClusteringResult.insufficientClustering += clusteringResult.insufficientClusters;
		aggregatedClusteringResult.sufficientClustering += (clusteringResult.numberOfClusters
				- clusteringResult.insufficientClusters);
		aggregatedClusteringResult.numberOfClusters += clusteringResult.numberOfClusters;
		aggregatedClusteringResult.numberOfConfigurations += getNumberOfConfigurations(cloneModel2.getAllComponents());
		aggregatedClusteringResult.totalInterSimilarity += clusteringResult.getAverageInterSimilarity();
		aggregatedClusteringResult.totalIntraSimilarity += clusteringResult.getAverageIntraSimilarity();
	}

	private Node generateRandomNode() {
		Node node = new NodeImpl(getRandomString());
		Random random = new Random();
		int numberOfValues = random.nextInt(10);
		for (int i = 0; i < numberOfValues; i++) {
			node.addAttribute(generateRandomAttribute());
		}
		return node;
	}

	private Attribute generateRandomAttribute() {
		Attribute attribute = new AttributeImpl(getRandomString());
		Random random = new Random();
		int numberOfValues = random.nextInt(10);

		for (int i = 0; i < numberOfValues; i++) {
			attribute.addAttributeValue(new StringValueImpl(getRandomString()));
		}
		return attribute;

	}

	private String getRandomString() {
		int leftLimit = 48;
		int rightLimit = 122;
		int targetStringLength = 10;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(j -> (j <= 57 || j >= 65) && (j <= 90 || j >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		return generatedString;
	}

	private Node getRandomNode(CloneModel cloneModel, String frameType) {
		List<Node> candidates = new ArrayList<Node>(cloneModel.getAllNodes(frameType));

		Random random = new Random();
		int index = random.nextInt(candidates.size());
		return candidates.get(index);

	}

	private Attribute getRandomAttribute(Node node) {

		Random random = new Random();
		int index = random.nextInt(node.getAttributes().size());
		return node.getAttributes().get(index);
	}

	private List<Metric> getMetrics() {

		String[] granularities = new String[] { "MethodDeclaration", "IfStmt", "JAVA", "CompilationUnit", "ForStmt" };
		List<Metric> metrics = new ArrayList<Metric>();

		for (int variantChangeDegree = 5; variantChangeDegree <= 5; variantChangeDegree++) {

			for (float type2AttributeChangeDegree = 25; type2AttributeChangeDegree <= 100; type2AttributeChangeDegree += 25) {

				for (String granularity : granularities) {

					GeneratorOptions generatorOptions = new GeneratorOptions();
					generatorOptions.isSyntaxSafe = false;
					generatorOptions.variants = 10;
					generatorOptions.variantChangeDegree = variantChangeDegree;
					generatorOptions.modificationRatioPercentage = (int) Math.round(type2AttributeChangeDegree);

					Metric metric = new Metric(new Granularity(granularity, true), true, false, generatorOptions);
					metrics.add(metric);
					metric = new Metric(new Granularity(granularity, true), false, true, generatorOptions);
					metrics.add(metric);
					metric = new Metric(new Granularity(granularity, true), true, true, generatorOptions);
					metrics.add(metric);
				}

			}

		}

		for (int i = 0; i < metrics.size(); i++) {
			metrics.get(i).setDirectory(outputDirectory + "\\Metric_" + i);
		}

		return metrics;
	}

	private void measureAverageIntraSimilarity(List<MultiSetTree> components, ClusteringResult clusteringResult) {

		if (components.size() == 0) {
			clusteringResult.setAverageIntraSimilarity(0);
			return;
		}

		float similarity = 0;
		for (MultiSetTree component : components) {
			similarity += getIntraSimilarity(component);
		}

		clusteringResult.setAverageIntraSimilarity(similarity / components.size());

	}

	private float getIntraSimilarity(MultiSetTree component) {

		CompareEngineHierarchical compareEngine = new CompareEngineHierarchical(new SortingMatcher(),
				new MetricImpl("test"));

		List<Node> nodes = new ArrayList<Node>();
		List<Tree> trees = component.restoreTrees();

		for (Tree tree : trees) {
			nodes.add(tree.getRoot());
		}

		float lowestSimilarity = 1;
		for (Node node1 : nodes) {

			for (Node node2 : nodes) {

				if (node1 != node2) {

					float similarity = compareEngine.compare(node1, node2).getSimilarity();
					if (similarity < lowestSimilarity) {
						lowestSimilarity = similarity;
					}
				}
			}
		}

		return lowestSimilarity;
	}

	private float measureAverageClusterSize(List<MultiSetTree> components) {

		float configurations = 0;

		for (MultiSetTree multiSetTree : components) {
			configurations += multiSetTree.getRoots().size();
		}

		return configurations / components.size();
	}

	private int getNumberOfConfigurations(List<MultiSetTree> components) {
		int configurations = 0;

		for (MultiSetTree multiSetTree : components) {
			configurations += multiSetTree.getNumberOfRoots();
		}

		return configurations;
	}

	private void measureAverageInterSimilarity(List<MultiSetTree> components, ClusteringResult clusteringResult) {

		Set<MultiSetTree> insufficientClusters = new HashSet<MultiSetTree>();

		if (components.size() <= 1) {
			clusteringResult.setInsufficientClusters(0);
			clusteringResult.setAverageInterSimilarity(0);
			return;
		}

		float similarity = 0;
		int comparisons = 0;
		for (MultiSetTree component1 : components) {
			for (MultiSetTree component2 : components) {
				if (component1 != component2) {
					similarity += getInterSimilarity(component1, component2, clusteringResult, insufficientClusters);
					comparisons++;
				}
			}
		}

		clusteringResult.setAverageInterSimilarity(similarity / comparisons);
		clusteringResult.setInsufficientClusters(insufficientClusters.size());

	}

	private float getInterSimilarity(MultiSetTree component1, MultiSetTree component2,
			ClusteringResult clusteringResult, Set<MultiSetTree> insufficientClusters) {

		CompareEngineHierarchical compareEngine = new CompareEngineHierarchical(new SortingMatcher(),
				new MetricImpl("test"));
		List<Node> nodes1 = new ArrayList<Node>();
		List<Tree> trees1 = component1.restoreTrees();

		for (Tree tree : trees1) {
			nodes1.add(tree.getRoot());
		}

		List<Node> nodes2 = new ArrayList<Node>();
		List<Tree> trees2 = component2.restoreTrees();

		for (Tree tree : trees2) {
			nodes2.add(tree.getRoot());
		}

		float highestSimilarity = 0;
		for (Node node1 : nodes1) {

			for (Node node2 : nodes2) {

				float similarity = compareEngine.compare(node1, node2).getSimilarity();
				if (similarity > highestSimilarity) {
					highestSimilarity = similarity;
				}
			}
		}

		if (highestSimilarity >= 0.85f) {
			insufficientClusters.add(component1);
			insufficientClusters.add(component2);
		}

		return highestSimilarity;
	}

	public void saveAggregatedResults() {

		String clusteringResultName = "aggregatedClusteringResult.csv";
		String stabilityResultName = "aggregatedStabilityResult.csv";
		try {

			String clusteringHeader = "granularity,sufficientClustering,insufficientClustering,numberOfClusters,averageClusterSize,averageInterSimilarity,averageIntraSimilarity";
			File file = new File(outputDirectory + "\\" + clusteringResultName);
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.println(clusteringHeader);

			for (Entry<String, AggregatedClusteringResult> entry : aggregatedClusteringResults.entrySet()) {
				printWriter.println(entry.getKey() + "," + entry.getValue().toString());

			}

			printWriter.close();

			file = new File(outputDirectory + "\\" + stabilityResultName);
			file.createNewFile();
			fileWriter = new FileWriter(file);
			printWriter = new PrintWriter(fileWriter);

			String stabilityHeader = "granularity,clusterSplits,clusterMerges";
			printWriter.println(stabilityHeader);

			for (Entry<String, AggregatedStabilityResult> entry : aggregatedStabilityResults.entrySet()) {
				printWriter.println(entry.getKey() + "," + entry.getValue().toString());

			}

			printWriter.close();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

}
