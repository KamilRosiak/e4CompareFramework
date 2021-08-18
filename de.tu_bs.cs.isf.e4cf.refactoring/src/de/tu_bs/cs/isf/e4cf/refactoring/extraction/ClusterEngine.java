package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;

public class ClusterEngine {

	private CompareEngineHierarchical compareEngine;
	private final String SCRIPT_PATH = "";

	public ClusterEngine() {
		compareEngine = new CompareEngineHierarchical(new SortingMatcher(), new MetricImpl("test"));
	}

	public List<Set<Node>> detectClusters(Iterable<Node> nodes, float threshold) {

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
					distanceString += 1 - compareEngine.compare(node1, node2).getResultSimilarity();
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
			ProcessBuilder builder = new ProcessBuilder(SCRIPT_PATH, distanceString, thresholdString);
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

	public boolean verifyCluster(Iterable<Node> nodes, float threshold) {
		List<Set<Node>> clusters = detectClusters(nodes, threshold);
		
		if(clusters.size() != 1) {
			return false;
		}
		return true;
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

}
