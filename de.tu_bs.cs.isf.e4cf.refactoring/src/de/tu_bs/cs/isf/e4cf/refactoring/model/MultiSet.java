package de.tu_bs.cs.isf.e4cf.refactoring.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;

public class MultiSet {

	private static CompareEngineHierarchical compareEngine = new CompareEngineHierarchical(new SortingMatcher(),
			new MetricImpl("test"));

	private Map<Node, Set<Node>> multiSet;
	private Component component;

	public Map<Node, Set<Node>> getMultiSet() {
		return multiSet;
	}

	public void setMultiSet(Map<Node, Set<Node>> multiSet) {
		this.multiSet = multiSet;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	private MultiSet(Component component) {
		this.component = component;
		multiSet = new HashMap<Node, Set<Node>>();
	}

	public static Map<Component, MultiSet> generate(Iterable<Component> components) {

		Map<Component, MultiSet> componentToMultiSets = new HashMap<Component, MultiSet>();

		for (Component component : components) {
			componentToMultiSets.put(component, generate(component));
		}
		return componentToMultiSets;

	}

	public static MultiSet generate(Component component) {

		MultiSet multiSet = new MultiSet(component);

		List<Node> instances = new ArrayList<Node>();
		for (Configuration configuration : component.getConfigurations()) {
			instances.add(configuration.getChildren().get(0));
		}

		for (Node instance1 : instances) {
			for (Node instance2 : instances) {

				if (instance1 != instance2) {
					multiSet.generate(compareEngine.compare(instance1, instance2));
				}
			}

		}

		return multiSet;

	}

	public void add(Node node) {
		multiSet.put(node, new HashSet<Node>());
		
		for(Node child: node.getChildren()) {
			add(child);
		}
	}

	public void addTo(Node node, Iterable<Node> nodes) {
		for (Node newNode : nodes) {
			addTo(node, newNode);
		}
	}

	public void addTo(Node node, Node newNode) {
		multiSet.get(node).add(newNode);
		multiSet.get(newNode).add(node);
		
		for(int i = 0; i < node.getChildren().size(); i++) {
			addTo(node.getChildren().get(i), newNode.getChildren().get(i));
		}
		
	}

	private void generate(Comparison<Node> comparison) {

		// relationship between artifacts
		if (comparison.getLeftArtifact() != null && comparison.getRightArtifact() != null) {

			if (comparison.getLeftArtifact() != comparison.getRightArtifact()) {
				if (multiSet.containsKey(comparison.getLeftArtifact())) {

					boolean alreadyInList = false;
					for (Node node : multiSet.get(comparison.getLeftArtifact())) {
						if (node.equals(comparison.getRightArtifact())) {
							alreadyInList = true;
						}
					}
					if (!alreadyInList) {
						multiSet.get(comparison.getLeftArtifact()).add(comparison.getRightArtifact());
					}

				} else {
					Set<Node> set = new HashSet<Node>();
					set.add(comparison.getRightArtifact());
					multiSet.put(comparison.getLeftArtifact(), set);
				}

				if (multiSet.containsKey(comparison.getRightArtifact())) {

					boolean alreadyInList = false;
					for (Node node : multiSet.get(comparison.getRightArtifact())) {
						if (node.equals(comparison.getLeftArtifact())) {
							alreadyInList = true;
						}
					}
					if (!alreadyInList) {
						multiSet.get(comparison.getRightArtifact()).add(comparison.getLeftArtifact());
					}

				} else {
					Set<Node> set = new HashSet<Node>();
					set.add(comparison.getLeftArtifact());
					multiSet.put(comparison.getRightArtifact(), set);
				}
			}

		}

		for (Comparison<Node> childComparison : comparison.getChildComparisons()) {
			generate(childComparison);
		}

	}

	public void add(Configuration newConfiguration) {

		for (Configuration configuration : component.getConfigurations()) {
			Comparison<Node> comparison = compareEngine.compare(configuration.getChildren().get(0),
					newConfiguration.getChildren().get(0));

			generate(comparison);
		}

	}

	public void remove(Configuration removedConfiguration) {
		remove(removedConfiguration.getChildren().get(0));
	}

	public void remove(Node node) {

		multiSet.remove(node);

		for (Set<Node> nodes : multiSet.values()) {

			if (nodes.contains(node)) {
				nodes.remove(node);
			}
		}

		for (Node child : node.getChildren()) {
			remove(child);
		}

	}

}
