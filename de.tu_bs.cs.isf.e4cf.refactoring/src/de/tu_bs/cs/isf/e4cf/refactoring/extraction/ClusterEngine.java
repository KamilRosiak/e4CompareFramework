package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.refactoring.evaluation.StatsLogger;
import de.tu_bs.cs.isf.e4cf.refactoring.handler.SynchronizationPipeline;
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Granularity;
import de.tu_bs.cs.isf.e4cf.refactoring.util.ProcessUtil;

@Singleton
@Creatable
public class ClusterEngine {
	private CompareEngineHierarchical compareEngine;
	private String scriptPath;

	private float threshold = 0.15f;

	public ClusterEngine() {
		compareEngine = new CompareEngineHierarchical(new SortingMatcher(), new MetricImpl("test"));
		scriptPath = new File((this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
				+ "script/clustering_sklearn.py").substring(1)).getPath();

	}

	public float getThreshold() {
		return threshold;
	}

	public void setThreshold(float threshold) {
		this.threshold = threshold;
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

		// start process
		if (!ProcessUtil.isReady()) {
			ProcessUtil.startProcess(scriptPath);
		}

		List<Set<Node>> clusters = new ArrayList<Set<Node>>();
		if (nodes.size() == 1) {
			Set<Node> nodeSet = new HashSet<Node>();
			nodeSet.add(nodes.get(0));
			clusters.add(nodeSet);
			return clusters;
		}

		String thresholdString = "";
		thresholdString += threshold;

		// Execute python clustering algorithm and process result
		try {
			BufferedWriter writer = ProcessUtil.getWriter();
			BufferedReader reader = ProcessUtil.getReader();

			writer.write(distanceString + " " + thresholdString + "\r\n");
			writer.flush();

			String line = reader.readLine();
			reader.readLine();
			String[] results = line.split(",");
			
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

		} catch (IOException e) {
			e.printStackTrace();
		}
		return clusters;

	}

	public void analyzeCloneModel(CloneModel cloneModel, StatsLogger statsLogger) {

		int clusterSplits = 0;
		int clusterMerges = 0;
		Map<String, Set<Component>> granularityToComponents = cloneModel.getGranularityToComponents();

		for (Entry<String, Set<Component>> entry : granularityToComponents.entrySet()) {

			List<Component> components = new ArrayList<Component>();
			for (Component component : entry.getValue()) {

				Map<Node, Configuration> targetToConfiguration = component.getConfigurationByTarget();
				Set<Node> targets = targetToConfiguration.keySet();
				components.add(component);

				// analyze intra-cluster similarity; exclude all new clusters from component
				List<Set<Node>> clusters = detectClusters(Lists.newArrayList(targets),
						buildDistanceString(Lists.newArrayList(targets)));

				if (clusters.size() > 1) {

					clusterSplits++;

					Set<Node> baseSet = clusters.get(0);
					for (Node target : targets) {
						if (!baseSet.contains(target)) {

							Configuration configurationToRemove = targetToConfiguration.get(target);
							Component newComponent = cloneModel.moveConfigurationToNewComponent(component,
									configurationToRemove);
							components.add(newComponent);

						}

					}
				}
			}

			// create components for all nodes of certain granularity not belonging to a
			// component yet
			for (String granularity : granularityToComponents.keySet()) {
				Set<Node> nodes = cloneModel.getNodesNotPartOfComponentsByGranularity(granularity);

				for (Node node : nodes) {
					Component newComponent = cloneModel.createNewComponent(node);

					components.add(newComponent);

				}
			}

			// analyze inter-cluster similarity and merge clusters of components to single
			// component
			String distanceString = buildDistanceComponentString(components);
			List<Set<Node>> clusters = detectClusters(new ArrayList<Node>(components), distanceString);
			for (Set<Node> cluster : clusters) {

				Component baseComponent = (Component) cluster.iterator().next();

				for (Node node : cluster) {
					Component component = (Component) node;

					if (baseComponent != component) {
						cloneModel.mergeComponents(baseComponent, component);

						clusterMerges++;
					}

				}

			}

			// remove all components of size 1

			List<Component> componentList = new ArrayList<Component>(cloneModel.getComponents());
			for (Component component : componentList) {
				if (component.getConfigurations().size() <= 1) {
					cloneModel.removeComponent(component);

				}
			}

		}
		
		if(statsLogger != null) {
			statsLogger.clusterMerges += clusterMerges;
			statsLogger.clusterSplits += clusterSplits;
		}

	}

	private String buildDistanceComponentString(List<Component> components) {

		float[][] matrix = new float[components.size()][components.size()];
		for (int i = 0; i < components.size(); i++) {
			for (int j = i; j < components.size(); j++) {

				Component component1 = components.get(i);
				Component component2 = components.get(j);

				if (i == j) {
					matrix[i][j] = 0.0f;
				} else {
					float distance = calculateDistance(component1.getAllTargets(), component2.getAllTargets());
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

	// calculate complete linkage distance between clusters
	private float calculateDistance(List<Node> nodes1, List<Node> nodes2) {
		float maxDistance = 0.0f;
		for (Node node1 : nodes1) {
			for (Node node2 : nodes2) {

				float distance = 1 - compareEngine.compare(node1, node2).getSimilarity();

				if (distance > maxDistance) {
					maxDistance = distance;
				}

			}
		}
		return maxDistance;
	}

}
