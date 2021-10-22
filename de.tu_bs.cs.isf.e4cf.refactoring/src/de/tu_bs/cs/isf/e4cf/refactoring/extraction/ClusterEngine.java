package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import com.google.common.collect.Lists;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.refactoring.evaluation.StabilityResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Granularity;
import de.tu_bs.cs.isf.e4cf.refactoring.model.MultiSetNode;
import de.tu_bs.cs.isf.e4cf.refactoring.model.MultiSetTree;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ReferenceTree;
import de.tu_bs.cs.isf.e4cf.refactoring.util.ProcessUtil;

@Singleton
@Creatable
public class ClusterEngine {
	private CompareEngineHierarchical compareEngine;
	private static String scriptPathExe;
	private static String scriptPathPython;
	public static float THRESHOLD = 0.15f;
	public static boolean PYTHON = true;

	public ClusterEngine() {
		compareEngine = new CompareEngineHierarchical(new SortingMatcher(), new MetricImpl("test"));
		scriptPathExe = new File((this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
				+ "script/clustering_sklearn.exe").substring(1)).getPath();
		scriptPathPython = new File((this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
				+ "script/clustering_sklearn.py").substring(1)).getPath();

	}

	private static ProcessUtil process;
	private StabilityResult stabilityResult;

	public StabilityResult getStabilityResult() {
		return stabilityResult;
	}

	public void setStabilityResult(StabilityResult stabilityResult) {
		this.stabilityResult = stabilityResult;
	}

	public static void startProcess() {
		if (process == null) {
			process = new ProcessUtil();
			if (PYTHON) {
				process.startProcess(scriptPathPython, true);
			} else {
				process.startProcess(scriptPathExe, false);
			}
		}
	}

	public Map<Granularity, List<Set<Node>>> detectClusters(Map<Granularity, List<Node>> nodes) {
		Map<Granularity, List<Set<Node>>> layerToClusters = new HashMap<Granularity, List<Set<Node>>>();

		for (Entry<Granularity, List<Node>> entry : nodes.entrySet()) {

			layerToClusters.put(entry.getKey(),
					detectClusters(entry.getValue(), buildDistanceString(entry.getValue())));
		}

		return layerToClusters;
	}

	private List<Set<Node>> detectClusters(List<Node> nodes, String distanceString) {

		try {

			List<Set<Node>> clusters = new ArrayList<Set<Node>>();
			if (nodes.size() == 1) {
				Set<Node> nodeSet = new HashSet<Node>();
				nodeSet.add(nodes.get(0));
				clusters.add(nodeSet);
				return clusters;
			}

			String[] results = getClusterResults(distanceString);

			Iterator<Node> iterator = nodes.iterator();
			Map<Integer, Set<Node>> map = new HashMap<Integer, Set<Node>>();

			for (int i = 0; i < results.length; i++) {
				int cluster = Integer.parseInt(results[i]);
				if (!map.containsKey(cluster)) {
					map.put(cluster, new HashSet<Node>());
				}
				map.get(cluster).add(iterator.next());
			}

			for (Set<Node> sets : map.values()) {
				clusters.add(sets);
			}
			return clusters;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	private String[] getClusterResults(String distanceString) throws IOException {
		String thresholdString = "";
		thresholdString += THRESHOLD;
		BufferedWriter writer = process.getWriter();
		BufferedReader reader = process.getReader();
		writer.write(distanceString + " " + thresholdString + "\r\n");
		writer.flush();
		String line = reader.readLine();
		reader.readLine();
		String[] results = line.split(",");
		return results;
	}

	private List<Set<MultiSetTree>> detectComponentClusters(List<MultiSetTree> nodes, String distanceString) {

		try {

			List<Set<MultiSetTree>> clusters = new ArrayList<Set<MultiSetTree>>();
			if (nodes.size() == 1) {
				Set<MultiSetTree> nodeSet = new HashSet<MultiSetTree>();
				nodeSet.add(nodes.get(0));
				clusters.add(nodeSet);
				return clusters;
			}

			String[] results = getClusterResults(distanceString);

			Iterator<MultiSetTree> iterator = nodes.iterator();
			Map<Integer, Set<MultiSetTree>> map = new HashMap<Integer, Set<MultiSetTree>>();

			for (int i = 0; i < results.length; i++) {
				int cluster = Integer.parseInt(results[i]);
				if (!map.containsKey(cluster)) {
					map.put(cluster, new HashSet<MultiSetTree>());
				}
				map.get(cluster).add(iterator.next());
			}

			for (Set<MultiSetTree> sets : map.values()) {
				clusters.add(sets);
			}
			return clusters;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	public void analyzeCloneModel(CloneModel cloneModel) {

		for (Entry<ReferenceTree, List<MultiSetTree>> entry : cloneModel.getComponents().entrySet()) {

			ReferenceTree completeTree = entry.getKey();

			Map<String, List<MultiSetTree>> granularityMapping = cloneModel.getGranularitiesToComponents(completeTree);

			for (Entry<String, List<MultiSetTree>> innerEntry : granularityMapping.entrySet()) {

				List<MultiSetTree> newMultiSetTrees = new ArrayList<MultiSetTree>();

				for (MultiSetTree multiSetTree : innerEntry.getValue()) {
					analyzeIntraSimilarity(newMultiSetTrees, multiSetTree, completeTree);
				}

				List<MultiSetTree> allMultiSetTrees = new ArrayList<MultiSetTree>();
				allMultiSetTrees.addAll(innerEntry.getValue());
				allMultiSetTrees.addAll(newMultiSetTrees);

				analyzeInterSimilarity(cloneModel, completeTree, allMultiSetTrees);

			}

		}

	}

	private void analyzeInterSimilarity(CloneModel cloneModel, ReferenceTree completeTree,
			List<MultiSetTree> allMultiSetTrees) {
		String distanceString = buildDistanceComponentString(allMultiSetTrees);
		List<Set<MultiSetTree>> clusters = detectComponentClusters(new ArrayList<MultiSetTree>(allMultiSetTrees),
				distanceString);
		for (Set<MultiSetTree> cluster : clusters) {
			MultiSetTree baseComponent = (MultiSetTree) cluster.iterator().next();
			for (MultiSetTree node : cluster) {
				if (baseComponent != node) {
					cloneModel.mergeTrees(completeTree, baseComponent, node);
					countMerge(baseComponent);
				}

			}

		}
	}

	private void analyzeIntraSimilarity(List<MultiSetTree> newMultiSetTrees, MultiSetTree multiSetTree,
			ReferenceTree completeTree) {
		Map<Node, MultiSetNode> mapping = new HashMap<Node, MultiSetNode>();
		for (MultiSetNode multiSetNode : multiSetTree.getRoots()) {
			Node node = multiSetNode.restoreNode();
			mapping.put(node, multiSetNode);
		}

		Set<Node> nodes = mapping.keySet();
		List<Set<Node>> clusters = detectClusters(Lists.newArrayList(nodes),
				buildDistanceString(Lists.newArrayList(nodes)));

		if (clusters.size() > 1) {
			Set<Node> baseSet = clusters.get(0);
			for (Node node : nodes) {
				if (!baseSet.contains(node)) {
					MultiSetNode multiSetNode = mapping.get(node);
					MultiSetTree newTree = multiSetTree.removeRootAndCreateNewTree(multiSetNode);
					newMultiSetTrees.add(newTree);

					countSplit(multiSetTree);

				}

			}
		}
	}

	private void countSplit(MultiSetTree multiSetTree) {
		if (stabilityResult != null) {
			Map<String, Integer> clusterSplits = stabilityResult.getClusterSplits();
			int count = clusterSplits.get(multiSetTree.getGranularity().getLayer()) + 1;
			clusterSplits.put(multiSetTree.getGranularity().getLayer(), count);
		}
	}

	private void countMerge(MultiSetTree multiSetTree) {
		if (stabilityResult != null) {
			Map<String, Integer> clusterMerges = stabilityResult.getClusterMerges();
			int count = clusterMerges.get(multiSetTree.getGranularity().getLayer()) + 1;
			clusterMerges.put(multiSetTree.getGranularity().getLayer(), count);
		}
	}

	private String buildDistanceComponentString(List<MultiSetTree> components) {

		float[][] matrix = new float[components.size()][components.size()];
		for (int i = 0; i < components.size(); i++) {
			for (int j = i; j < components.size(); j++) {

				MultiSetTree component1 = components.get(i);
				MultiSetTree component2 = components.get(j);

				if (i == j) {
					matrix[i][j] = 0.0f;
				} else {
					float distance = calculateDistance(component1.getRoots(), component2.getRoots());
					matrix[i][j] = distance;
					matrix[j][i] = distance;
				}

			}

		}
		return getDistanceString(matrix, components.size());
	}

	private String getDistanceString(float[][] matrix, int size) {
		String distanceString = "(";
		for (int i = 0; i < size; i++) {
			distanceString += "[";
			for (int j = 0; j < size; j++) {
				distanceString += matrix[i][j];
				if (j != size - 1) {
					distanceString += ",";
				}

			}
			distanceString += "]";
			if (i != size - 1) {
				distanceString += ",";
			}

		}
		distanceString += ")";

		return distanceString;
	}

	private String buildDistanceString(List<Node> nodes) {
		float[][] matrix = new float[nodes.size()][nodes.size()];
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = i; j < nodes.size(); j++) {

				Node node1 = nodes.get(i);
				Node node2 = nodes.get(j);

				if (i == j) {
					matrix[i][j] = 0.0f;
				} else {
					float distance = 1 - compareEngine.compare(node1, node2).getSimilarity();
					matrix[i][j] = distance;
					matrix[j][i] = distance;
				}
			}
		}
		return getDistanceString(matrix, nodes.size());
	}

	private float calculateDistance(List<MultiSetNode> nodes1, List<MultiSetNode> nodes2) {
		float maxDistance = 0.0f;
		for (MultiSetNode node1 : nodes1) {
			for (MultiSetNode node2 : nodes2) {
				Node restored1 = node1.restoreNode();
				Node restored2 = node2.restoreNode();
				Comparison<Node> comparison = compareEngine.compare(restored1, restored2);
				float distance = 1 - comparison.getSimilarity();
				if (distance > maxDistance) {
					maxDistance = distance;
				}
			}
		}
		return maxDistance;
	}

}
