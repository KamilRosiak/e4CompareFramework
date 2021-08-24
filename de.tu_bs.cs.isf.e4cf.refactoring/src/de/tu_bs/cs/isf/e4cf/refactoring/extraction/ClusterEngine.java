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
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentLayer;
import de.tu_bs.cs.isf.e4cf.refactoring.util.ProcessUtil;

@Singleton
@Creatable
public class ClusterEngine {
	private CompareEngineHierarchical compareEngine;
	private String scriptPath;

	private float threshold = 0.35f;

	public ClusterEngine() {
		compareEngine = new CompareEngineHierarchical(new SortingMatcher(), new MetricImpl("test"));
		scriptPath = new File((this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
				+ "script/clustering_scipy.py").substring(1)).getPath();

	}

	public Map<ComponentLayer, List<Set<Node>>> detectClusters(Map<ComponentLayer, List<Node>> nodes) {
		Map<ComponentLayer, List<Set<Node>>> layersToCluster = new HashMap<ComponentLayer, List<Set<Node>>>();

		for (Entry<ComponentLayer, List<Node>> entry : nodes.entrySet()) {

			layersToCluster.put(entry.getKey(),
					detectClusters(entry.getValue(), buildDistanceString(entry.getValue())));

		}

		return layersToCluster;
	}

	private List<Set<Node>> detectClusters(Iterable<Node> nodes, String distanceString) {

		if (!ProcessUtil.isReady()) {
			ProcessUtil.startProcess(scriptPath);
		}

		List<Set<Node>> clusters = new ArrayList<Set<Node>>();

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
		// printMetrics(clusters, threshold);
		return clusters;

	}

	private void printMetrics(List<Set<Node>> clusters, float threshold) {
		int largestCluster = 0;
		int numberOfClustersWithSingleElement = 0;

		for (Set<Node> cluster1 : clusters) {
			if (cluster1.size() > largestCluster) {
				largestCluster = cluster1.size();
			}
			if (cluster1.size() == 1) {
				numberOfClustersWithSingleElement += 1;
			}

		}

		System.out.println("");
		System.out.println("--- Clustering ----");
		System.out.println("Distance: " + threshold);
		System.out.println("Number of clusters: " + clusters.size());
		System.out.println("Largest cluster: " + largestCluster);
		System.out.println("Clusters of size 1: " + numberOfClustersWithSingleElement);

	}

	public void refineComponents(CloneModel cloneModel) {

		Map<String, Set<Component>> componentsByGranularities = cloneModel.getComponentsByGranularities();

		for (Entry<String, Set<Component>> entry : componentsByGranularities.entrySet()) {

			List<Component> newComponents = new ArrayList<Component>();
			for (Component component : entry.getValue()) {

				Map<Node, Configuration> configurationByTarget = component.getConfigurationByTarget();
				Set<Node> targets = configurationByTarget.keySet();
				newComponents.add(component);

				List<Set<Node>> clusters = detectClusters(targets, buildDistanceString(Lists.newArrayList(targets)));

				// workaround due to comparison order issues
				List<Node> targets2 = Lists.newArrayList(targets);
				for (int i = 0; i < 10; i++) {
					Collections.shuffle(targets2);
					List<Set<Node>> clusters2 = detectClusters(targets2, buildDistanceString(targets2));

					if (clusters.size() != clusters2.size()) {
						// order issue
						System.out.println("Order issue, aborting..");
						return;
					}
				}

				if (clusters.size() > 1) {

					Set<Node> baseSet = clusters.get(0);

					for (Node target : targets) {
						if (baseSet.contains(target)) {
							Configuration configurationToRemove = configurationByTarget.get(target);
							Component newComponent = cloneModel.moveConfigurationToNewComponent(component,
									configurationToRemove);
							newComponents.add(newComponent);
						}

					}
				}
			}

			String distanceString = buildDistanceComponentString(newComponents);

			if (newComponents.size() > entry.getValue().size()) {
				List<Set<Node>> clusters = detectClusters(new ArrayList<Node>(newComponents), distanceString);

				List<Set<Node>> filteredClusters = new ArrayList<Set<Node>>();

				for (Set<Node> cluster : clusters) {
					if (cluster.size() == 1) {
						Component component = (Component) cluster.iterator().next();
						cloneModel.removeComponent(component);
					} else {
						filteredClusters.add(cluster);
					}
				}

				for (Set<Node> cluster : clusters) {

					for (Node node1 : cluster) {
						Component component1 = (Component) node1;

						for (Node node2 : cluster) {
							Component component2 = (Component) node2;

							if (node1 != node2) {
								cloneModel.mergeComponents(component1, component2);
							}

						}
					}

				}

			}

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
		String distanceString = "(";
		for (int i = 0; i < components.size(); i++) {
			distanceString += "[";
			for (int j = 0; j < components.size(); j++) {
				distanceString += matrix[i][j];
				if (j != components.size() - 1) {
					distanceString += ",";
				}

			}
			distanceString += "]";
			if (i != components.size() - 1) {
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
		String distanceString = "(";
		for (int i = 0; i < nodes.size(); i++) {
			distanceString += "[";
			for (int j = 0; j < nodes.size(); j++) {
				distanceString += matrix[i][j];
				if (j != nodes.size() - 1) {
					distanceString += ",";
				}

			}
			distanceString += "]";
			if (i != nodes.size() - 1) {
				distanceString += ",";
			}

		}
		distanceString += ")";

		return distanceString;

	}

	private float calculateDistance(List<Node> targets1, List<Node> targets2) {

		float maxDistance = 0.0f;
		for (Node node1 : targets1) {
			for (Node node2 : targets2) {

				float distance = 1 - compareEngine.compare(node1, node2).getSimilarity();

				if (distance > maxDistance) {
					maxDistance = distance;
				}

			}
		}
		return maxDistance;
	}

}
