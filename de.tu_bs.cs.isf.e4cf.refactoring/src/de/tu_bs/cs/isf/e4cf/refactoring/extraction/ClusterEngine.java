package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentLayer;

@Singleton
@Creatable
public class ClusterEngine {
	private CompareEngineHierarchical compareEngine;
	private String scriptPath;

	private float threshold = 0.35f;

	public ClusterEngine() {
		compareEngine = new CompareEngineHierarchical(new SortingMatcher(), new MetricImpl("test"));
		scriptPath = new File(
				(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "script/clustering.py")
						.substring(1)).getPath();

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

		List<Set<Node>> clusters = new ArrayList<Set<Node>>();

		String thresholdString = "";
		thresholdString += threshold;

		// Execute python clustering algorithm and process result
		try {
			ProcessBuilder builder = new ProcessBuilder("py", scriptPath, distanceString, thresholdString);
			builder.redirectErrorStream(true);
			Process process = builder.start();
			InputStream inputStream = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

			String line = reader.readLine();
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
		printMetrics(clusters, threshold);
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

				List<Set<Node>> clusters = detectClusters(targets, buildDistanceString(targets));
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

			if (newComponents.size() > 1) {
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

	private String buildDistanceComponentString(Iterable<Component> components) {

		String distanceString = "(";
		Iterator<Component> iterator1 = components.iterator();
		while (iterator1.hasNext()) {
			distanceString += "[";
			Component component1 = iterator1.next();
			Iterator<Component> iterator2 = components.iterator();
			while (iterator2.hasNext()) {
				Component component2 = iterator2.next();
				if (component1 != component2) {
					float distance = calculateDistance(component1.getAllTargets(), component2.getAllTargets());
					distanceString += distance;
				} else {
					distanceString += "0.00";
				}
				if (iterator2.hasNext()) {
					distanceString += ",";
				}
			}
			distanceString += "]";
			if (iterator1.hasNext()) {
				distanceString += ",";
			}
		}

		distanceString += ")";

		return distanceString;

	}

	private String buildDistanceString(Iterable<Node> nodes) {

		String distanceString = "(";
		Iterator<Node> iterator1 = nodes.iterator();
		while (iterator1.hasNext()) {
			distanceString += "[";
			Node node1 = iterator1.next();
			Iterator<Node> iterator2 = nodes.iterator();
			while (iterator2.hasNext()) {
				Node node2 = iterator2.next();
				if (node1 != node2) {
					Comparison<Node> comparison = compareEngine.compare(node1, node2);
					distanceString += 1 - comparison.getSimilarity();
				} else {
					distanceString += "0.00";
				}
				if (iterator2.hasNext()) {
					distanceString += ",";
				}
			}
			distanceString += "]";
			if (iterator1.hasNext()) {
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
