package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;

public class SynchronizationUnitEngine {

	public void determineSynchronizationUnit(Comparison<Node> comparison, Map<Node, Set<Node>> map) {

		// relationship between artifacts
		if (comparison.getLeftArtifact() != null && comparison.getRightArtifact() != null) {

			if (comparison.getLeftArtifact() != comparison.getRightArtifact()) {
				if (map.containsKey(comparison.getLeftArtifact())) {

					boolean alreadyInList = false;
					for (Node node : map.get(comparison.getLeftArtifact())) {
						if (node.equals(comparison.getRightArtifact())) {
							alreadyInList = true;
						}
					}
					if (!alreadyInList) {
						map.get(comparison.getLeftArtifact()).add(comparison.getRightArtifact());
					}

				} else {
					Set<Node> set = new HashSet<Node>();
					set.add(comparison.getRightArtifact());
					map.put(comparison.getLeftArtifact(), set);
				}

				if (map.containsKey(comparison.getRightArtifact())) {

					boolean alreadyInList = false;
					for (Node node : map.get(comparison.getRightArtifact())) {
						if (node.equals(comparison.getLeftArtifact())) {
							alreadyInList = true;
						}
					}
					if (!alreadyInList) {
						map.get(comparison.getRightArtifact()).add(comparison.getLeftArtifact());
					}

				} else {
					Set<Node> set = new HashSet<Node>();
					set.add(comparison.getLeftArtifact());
					map.put(comparison.getRightArtifact(), set);
				}
			}

		}

		for (Comparison<Node> childComparison : comparison.getChildComparisons()) {
			determineSynchronizationUnit(childComparison, map);
		}

	}

	public void removeFromSynchronizationUnit(Node nodeToRemove, Map<Node, Set<Node>> synchronizationUnit) {

		synchronizationUnit.remove(nodeToRemove);

		for (Set<Node> nodes : synchronizationUnit.values()) {

			if (nodes.contains(nodeToRemove)) {
				nodes.remove(nodeToRemove);
			}
		}

		for (Node child : nodeToRemove.getChildren()) {
			removeFromSynchronizationUnit(child, synchronizationUnit);
		}

	}

	public void updateSynchronizationUnit(List<Configuration> configurations,
			Map<Node, Set<Node>> synchronizationUnit) {

		CompareEngineHierarchical engine = new CompareEngineHierarchical(new SortingMatcher(), new MetricImpl("test"));

		for (Node node1 : configurations) {

			Node artifact1 = node1.getChildren().get(0);

			for (Node node2 : configurations) {

				Node artifact2 = node2.getChildren().get(0);

				if (!artifact1.equals(artifact2)) {
					Comparison<Node> comparison = engine.compare(artifact1, artifact2);
					determineSynchronizationUnit(comparison, synchronizationUnit);
				}

			}

		}

	}

}
