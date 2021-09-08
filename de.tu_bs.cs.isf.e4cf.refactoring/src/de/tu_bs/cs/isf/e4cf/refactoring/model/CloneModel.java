package de.tu_bs.cs.isf.e4cf.refactoring.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Lists;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.ComponentImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.ConfigurationImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

public class CloneModel {

	private Map<Component, MultiSet> multiSets;

	private Set<Component> components;

	public void setComponents(Set<Component> components) {
		this.components = components;
	}

	public Set<Component> getComponents() {
		return components;
	}

	public Map<Component, MultiSet> getMultiSets() {
		return multiSets;
	}

	public void setMultiSets(Map<Component, MultiSet> multiSets) {
		this.multiSets = multiSets;
	}

	public CloneModel() {
		this.components = new HashSet<Component>();
		this.multiSets = new HashMap<Component, MultiSet>();
	}

	public int getNumberOfComponents() {
		return components.size();
	}

	public boolean isInsideComponent(Node node) {

		for (Component component : components) {
			List<Node> nodes = component.getAllTargets();

			if (nodes.contains(node)) {
				return true;
			}

		}
		return false;

	}

	public int getNumberOfConfigurations() {
		int numberOfConfigurations = 0;

		for (Component component : components) {
			numberOfConfigurations += component.getChildren().size();
		}
		return numberOfConfigurations;

	}

	private Tree tree;

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

	public Set<Node> getNodesNotPartOfComponentsByGranularity(String granularity) {

		Set<Node> nodes = new HashSet<Node>();

		collectNodes(tree.getRoot(), nodes, granularity);

		return nodes;
	}

	private void collectNodes(Node node, Set<Node> nodes, String granularity) {

		if (node.getNodeType().equals(granularity) && !isInsideComponent(node)) {

			Node parent = node.getParent();
			boolean isNested = false;
			while (parent != null) {

				if (parent.getNodeType().equals(node.getNodeType())) {
					isNested = true;
					break;
				}
				parent = parent.getParent();
			}

			if (!isNested) {
				nodes.add(node);
			}

		}
		for (Node child : node.getChildren()) {
			if (!(child instanceof Component)) {
				collectNodes(child, nodes, granularity);
			}
		}
	}

	public void addConfiguration(Component component, Configuration newConfiguration, Component sourceComponent) {

		Configuration baseConfiguration = new ConfigurationImpl();
		baseConfiguration.setTarget(newConfiguration.getTarget());
		baseConfiguration.setParent(component);
		component.getChildren().add(baseConfiguration);

		MultiSet multiSet = multiSets.get(component);
		multiSet.add(baseConfiguration);

		multiSet = multiSets.get(sourceComponent);
		multiSet.remove(newConfiguration);

		sourceComponent.getChildren().remove(newConfiguration);

	}

	public void removeConfigurations(RevisionComparison revisionComparison) {

		for (Container nodeToDeleteContainer : revisionComparison.getDeletedNodes()) {

			Node nodeToDelete = nodeToDeleteContainer.getNode();
			for (Component component : components) {

				List<Configuration> configurations = Lists.newArrayList(component.getConfigurations());
				for (Configuration configuration : configurations) {

					if (configuration.getTarget() == nodeToDelete) {
						removeConfiguration(component, configuration);
					}
				}
			}

		}

	}

	public Component getComponent(Node node) {

		for (Component component : components) {

			List<Node> candidates = component.getNodesOfType(node.getNodeType());

			if (candidates.contains(node)) {
				return component;
			}

		}
		return null;

	}

	public void removeConfiguration(Component component, Configuration configuration) {

		component.getChildren().remove(configuration);
		MultiSet multiSet = multiSets.get(component);
		multiSet.remove(configuration);

	}

	public Component createNewComponent(Node node) {

		Component newComponent = new ComponentImpl();
		newComponent.setLayer(node.getNodeType());
		Configuration newConfiguration = new ConfigurationImpl();
		newConfiguration.setTarget(node);
		newComponent.addChildWithParent(newConfiguration);

		multiSets.put(newComponent, MultiSet.generate(newComponent));

		return newComponent;

	}

	public Component moveConfigurationToNewComponent(Component component, Configuration configuration) {

		component.getChildren().remove(configuration);
		MultiSet multiSet = multiSets.get(component);
		multiSet.remove(configuration);

		Component newComponent = new ComponentImpl();
		newComponent.setLayer(component.getLayer());
		Configuration newConfiguration = new ConfigurationImpl();
		newConfiguration.setTarget(configuration.getTarget());
		newComponent.addChildWithParent(newConfiguration);

		multiSets.put(newComponent, MultiSet.generate(newComponent));

		return newComponent;

	}

	public void mergeComponents(Component component1, Component component2) {
		List<Configuration> configurations = new ArrayList<Configuration>(component2.getConfigurations());
		for (Configuration configuration : configurations) {
			addConfiguration(component1, configuration, component2);
		}
		this.components.remove(component2);
		this.multiSets.remove(component2);

	}

	public void removeComponent(Component component) {
		multiSets.remove(component);
		components.remove(component);
	}

	public Map<String, Set<Component>> getGranularityToComponents() {

		Map<String, Set<Component>> granularityToComponents = new HashMap<String, Set<Component>>();
		for (Component component : components) {
			if (!granularityToComponents.containsKey(component.getLayer())) {
				granularityToComponents.put(component.getLayer(), new HashSet<Component>());
			}
			granularityToComponents.get(component.getLayer()).add(component);
		}
		return granularityToComponents;
	}

}
