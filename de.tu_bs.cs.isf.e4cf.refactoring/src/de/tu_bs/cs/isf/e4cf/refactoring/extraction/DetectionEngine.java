package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import com.google.common.collect.Sets;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;

public class DetectionEngine {

	private CompareEngineHierarchical compareEngine;

	public DetectionEngine() {
		compareEngine = new CompareEngineHierarchical(new SortingMatcher(), new MetricImpl("test"));
	}

	public List<Set<Node>> findClusters_Version2(Iterable<Node> nodes, float threshold) {
		List<NodeComparison> nodeComparisons = new LinkedList<NodeComparison>();

		for (Node node1 : nodes) {
			for (Node node2 : nodes) {

				if (node1 != node2) {
					NodeComparison comparison = compareEngine.compare(node1, node2);
					if (comparison.getSimilarity() >= threshold) {
						nodeComparisons.add(compareEngine.compare(node1, node2));
					}
				}
			}
		}

		List<Set<Node>> clusters = new ArrayList<Set<Node>>();

		for (NodeComparison nodeComparison : nodeComparisons) {

			boolean inSet = false;

			for (int i = 0; i < clusters.size(); i++) {

				if (clusters.get(i).contains(nodeComparison.getLeftArtifact())
						|| clusters.get(i).contains(nodeComparison.getRightArtifact())) {
					clusters.get(i).add(nodeComparison.getLeftArtifact());
					clusters.get(i).add(nodeComparison.getRightArtifact());
					inSet = true;
					break;

				}

			}

			if (!inSet) {
				Set<Node> set = new HashSet<Node>();
				set.add(nodeComparison.getLeftArtifact());
				set.add(nodeComparison.getRightArtifact());
				clusters.add(set);
			}

		}

		Iterator<Set<Node>> iterator = clusters.iterator();

		while (iterator.hasNext()) {

			Set<Node> pivotCluster = iterator.next();

			if (clusters.contains(pivotCluster)) {
				List<Set<Node>> clustersToRemove = new ArrayList<Set<Node>>();
				for (Set<Node> cluster : clusters) {

					if (pivotCluster != cluster) {

						if (Sets.intersection(pivotCluster, cluster).size() != 0) {
							pivotCluster.addAll(cluster);
							clustersToRemove.add(cluster);
						}

					}

				}

				for (Set<Node> clusterToRemove : clustersToRemove) {

					clusters.remove(clusterToRemove);
				}
			}
		}

		return clusters;

	}

	public List<Set<Node>> findClusters_Version1(Iterable<Node> nodes, float threshold) {

		List<NodeComparison> nodeComparisons = new LinkedList<NodeComparison>();

		for (Node node1 : nodes) {
			for (Node node2 : nodes) {

				if (node1 != node2) {
					NodeComparison comparison = compareEngine.compare(node1, node2);
					if (comparison.getSimilarity() >= threshold) {
						nodeComparisons.add(compareEngine.compare(node1, node2));
					}
				}
			}
		}
		LinkedList<Set<Node>> nodeLists = new LinkedList<Set<Node>>();

		if (!nodeComparisons.isEmpty()) {

			NodeComparison defaultComparison = nodeComparisons.get(0);
			Set<Node> set = new HashSet<Node>();
			set.add(defaultComparison.getLeftArtifact());
			set.add(defaultComparison.getRightArtifact());

			nodeLists.add(set);

			for (NodeComparison comparison : nodeComparisons) {

				ListIterator<Set<Node>> nodeListIterator = nodeLists.listIterator();
				while (nodeListIterator.hasNext()) {

					Set<Node> nodeSet = nodeListIterator.next();

					boolean containsLeftArtefact = nodeSet.contains(comparison.getLeftArtifact());
					boolean containsRightArtefact = nodeSet.contains(comparison.getRightArtifact());

					if (containsLeftArtefact || containsRightArtefact) {
						set.add(comparison.getLeftArtifact());

						if (containsLeftArtefact) {

							if (nodeLists.size() > 1) {
								ListIterator<Set<Node>> innerNodeListIterator = nodeLists.listIterator();
								while (innerNodeListIterator.hasNext()) {
									Set<Node> innerSet = innerNodeListIterator.next();
									if (innerSet.contains(comparison.getRightArtifact())) {
										nodeSet.addAll(innerSet);
										// nodeLists.remove(innerSet);
										innerNodeListIterator.remove();

									}

								}
							}

							set.add(comparison.getRightArtifact());

						} else if (containsRightArtefact) {

							if (nodeLists.size() > 1) {
								ListIterator<Set<Node>> innerNodeListIterator = nodeLists.listIterator();

								while (innerNodeListIterator.hasNext()) {
									Set<Node> innerSet = innerNodeListIterator.next();
									if (innerSet.contains(comparison.getLeftArtifact())) {
										nodeSet.addAll(innerSet);
										// nodeLists.remove(innerSet);
										innerNodeListIterator.remove();

									}

								}
							}

							set.add(comparison.getLeftArtifact());
						}

					} else {
						Set<Node> newSet = new HashSet<Node>();
						newSet.add(comparison.getLeftArtifact());
						newSet.add(comparison.getRightArtifact());
						// nodeLists.add(newSet);
						nodeListIterator.add(newSet);

					}
				}

			}
		}
		return nodeLists;
	}

}
