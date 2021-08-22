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
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.ComponentImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.ConfigurationImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentLayer;

@Singleton
@Creatable
public class ClusterEngine {
	private CompareEngineHierarchical compareEngine;
	private String scriptPath;

	private float threshold = 0.15f;

	public ClusterEngine() {
		compareEngine = new CompareEngineHierarchical(new SortingMatcher(), new MetricImpl("test"));
		scriptPath = new File(
				(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "script/clustering.py")
						.substring(1)).getPath();

	}

	public Map<ComponentLayer, List<Set<Node>>> detectClusters(Map<ComponentLayer, List<Node>> nodes) {
		Map<ComponentLayer, List<Set<Node>>> layersToCluster = new HashMap<ComponentLayer, List<Set<Node>>>();

		for (Entry<ComponentLayer, List<Node>> entry : nodes.entrySet()) {
			layersToCluster.put(entry.getKey(), detectClusters(entry.getValue()));

		}

		return layersToCluster;
	}

	private List<Set<Node>> detectClusters(Iterable<Node> nodes) {

		List<Set<Node>> clusters = new ArrayList<Set<Node>>();

		// Build distance matrix for numpy
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

	public Map<String, List<Set<Node>>> refineComponents(List<Component> components) {
		Map<String, List<Set<Node>>> layersToClusters = new HashMap<String, List<Set<Node>>>();
		Map<String, Set<Component>> componentsByGranularities = new HashMap<String, Set<Component>>();
		for (Component component : components) {
			if (!componentsByGranularities.containsKey(component.getLayer())) {
				componentsByGranularities.put(component.getLayer(), new HashSet<Component>());
			}
			componentsByGranularities.get(component.getLayer()).add(component);
		}

		for (Entry<String, Set<Component>> entry : componentsByGranularities.entrySet()) {
			List<Node> nodes = new ArrayList<Node>();
			for (Component component : entry.getValue()) {
				for (Configuration configuration : component.getConfigurations()) {
					nodes.add(configuration.getChildren().get(0));
				}
			}

			List<Set<Node>> clusters = detectClusters(nodes);
			layersToClusters.put(entry.getKey(), clusters);

		}

		return layersToClusters;

	}

}
