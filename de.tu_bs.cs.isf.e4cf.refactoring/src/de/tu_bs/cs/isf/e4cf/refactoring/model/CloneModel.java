package de.tu_bs.cs.isf.e4cf.refactoring.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.ComponentImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.ConfigurationImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

public class CloneModel {

	private Map<Component, MultiSet> multiSets;

	private Map<Component, Set<Component>> componentInstances;

	public Map<Component, Set<Component>> getComponentInstances() {
		return componentInstances;
	}

	public void setComponentInstances(Map<Component, Set<Component>> componentInstances) {
		this.componentInstances = componentInstances;
	}

	public Map<Component, MultiSet> getMultiSets() {
		return multiSets;
	}

	public void setMultiSets(Map<Component, MultiSet> multiSets) {
		this.multiSets = multiSets;
	}

	public CloneModel() {
		this.componentInstances = new HashMap<Component, Set<Component>>();
		this.multiSets = new HashMap<Component, MultiSet>();
		this.trees = new ArrayList<Tree>();
	}

	public void addTree(Tree tree) {
		this.trees.add(tree);
	}

	public List<Component> getComponents() {
		return new ArrayList<Component>(componentInstances.keySet());
	}

	private List<Tree> trees;

	public List<Tree> getTrees() {
		return trees;
	}

	public void setTrees(List<Tree> trees) {
		this.trees = trees;
	}

	public List<Node> getNodesNotPartOfComponentsByGranularity(String granularity) {

		List<Node> nodes = new ArrayList<Node>();

		for (Tree tree : trees) {
			collectNodes(tree.getRoot(), nodes, granularity);
		}
		for (Component component : componentInstances.keySet()) {
			if (component.getLayer() != granularity) {
				for (Configuration configuration : component.getConfigurations()) {
					collectNodes(configuration.getTarget(), nodes, granularity);
				}
			}
		}
		return nodes;
	}

	private void collectNodes(Node node, List<Node> nodes, String granularity) {

		if (node.getNodeType() == granularity) {
			nodes.add(node);
		}
		for (Node child : node.getChildren()) {
			if (child.getNodeType() != "Component") {
				collectNodes(child, nodes, granularity);
			}
		}
	}

	public void addConfigurations(List<ComponentComparison> componentComparisons, CloneModel cloneModel2) {

		for (ComponentComparison componentComparison : componentComparisons) {

			if (componentComparison.getComponent1() != null && componentComparison.getComponent2() != null) {

				for (Configuration configuration : componentComparison.getAddedConfigurations()) {
					addConfiguration(componentComparison.getComponent1(), configuration,
							componentComparison.getComponent2(), cloneModel2);
				}
			}
		}
	}

	public void addConfiguration(Component component, Configuration newConfiguration, Component sourceComponent,
			CloneModel cloneModel2) {

		// create base configuration
		newConfiguration.setParent(component);
		component.getChildren().add(newConfiguration);
		MultiSet multiSet = multiSets.get(component);
		multiSet.add(newConfiguration);

		// add configuration instance to all component instances
		for (Component componentInstance : componentInstances.get(component)) {
			Configuration configurationInstance = new ConfigurationImpl();
			configurationInstance.setTarget(newConfiguration.getTarget());
			componentInstance.getChildren().add(configurationInstance);
		}

		// create new component instance
		Component newComponentInstance = new ComponentImpl();
		for (Configuration configuration : component.getConfigurations()) {
			Configuration configurationInstance = new ConfigurationImpl();
			configurationInstance.setTarget(configuration.getTarget());
			newComponentInstance.getChildren().add(configurationInstance);
			if (configuration.getTarget() == newConfiguration.getTarget()) {
				newComponentInstance.setSelectedConfiguration(configurationInstance);
			}
		}

		// collect component instances to remove and remove selected configuration
		List<Component> componentInstancesToRemove = new ArrayList<Component>();
		for (Component componentInstance : cloneModel2.getComponentInstances().get(sourceComponent)) {
			if (componentInstance.getSelectedConfiguration().getTarget() == newConfiguration.getTarget()) {
				int position = componentInstance.getPosition();
				Node parent = componentInstance.getParent();
				parent.getChildren().remove(position);
				parent.addChildAtPosition(newComponentInstance, position);
				componentInstancesToRemove.add(componentInstance);

			}
		}

		for (Component componentInstance : componentInstancesToRemove) {
			cloneModel2.getComponentInstances().get(sourceComponent).remove(componentInstance);
			cloneModel2.getMultiSets().get(sourceComponent).remove(componentInstance.getSelectedConfiguration());
		}

	}

	public void removeConfigurations(List<ComponentComparison> componentComparisons) {
		for (ComponentComparison componentComparison : componentComparisons) {

			if (componentComparison.getComponent1() != null && componentComparison.getComponent2() != null) {
				for (Configuration configuration : componentComparison.getRemovedConfigurations()) {
					removeConfiguration(componentComparison.getComponent1(), configuration);
				}
			}

		}
	}

	public void removeConfiguration(Component component, Configuration configuration) {

		component.getChildren().remove(configuration);
		MultiSet multiSet = multiSets.get(component);
		multiSet.remove(configuration);

		for (Component componentInstance : componentInstances.get(component)) {
			componentInstance.getChildren().remove(configuration);

			if (componentInstance.getSelectedConfiguration() == configuration) {
				Configuration selectedConfiguration = componentInstance.getSelectedConfiguration();
				Node parent = componentInstance.getParent();
				int position = componentInstance.getPosition();
				parent.getChildren().remove(position);
				parent.addChildAtPosition(selectedConfiguration.getChildren().get(0).cloneNode(), position);
			}
		}

	}

	public Component createNewComponent(Node node) {

		Node parent = node.getParent();
		int position = node.getPosition();

		Component newComponent = new ComponentImpl();
		Configuration newConfiguration = new ConfigurationImpl();
		newConfiguration.setTarget(node);
		newComponent.addChildWithParent(newConfiguration);

		Component newInstance = new ComponentImpl();
		Configuration newConfigurationInstance = new ConfigurationImpl();
		newConfigurationInstance.setTarget(node);
		newInstance.addChildWithParent(newConfigurationInstance);
		newInstance.setSelectedConfiguration(newConfigurationInstance);

		parent.getChildren().remove(position);
		parent.addChildAtPosition(newInstance, position);

		componentInstances.put(newComponent, new HashSet<Component>());
		componentInstances.get(newComponent).add(newInstance);

		multiSets.put(newComponent, MultiSet.generate(newComponent));

		return newComponent;

	}

	public Component moveConfigurationToNewComponent(Component component, Configuration configuration) {

		component.getChildren().remove(configuration);
		MultiSet multiSet = multiSets.get(component);
		multiSet.remove(configuration);

		Component newComponent = new ComponentImpl();
		Configuration newConfiguration = new ConfigurationImpl();
		newConfiguration.setTarget(configuration.getTarget());
		newComponent.addChildWithParent(newConfiguration);

		getComponentInstances().put(newComponent, new HashSet<Component>());

		for (Component componentInstance : componentInstances.get(component)) {

			if (componentInstance.getSelectedConfiguration().getTarget() == configuration.getTarget()) {
				Component newInstance = new ComponentImpl();
				Configuration newConfigurationInstance = new ConfigurationImpl();
				newConfigurationInstance.setTarget(componentInstance.getSelectedConfiguration().getTarget());
				newInstance.addChildWithParent(newConfigurationInstance);
				newInstance.setSelectedConfiguration(newConfigurationInstance);

				Node parent = componentInstance.getParent();
				int position = componentInstance.getPosition();

				parent.getChildren().remove(position);
				parent.addChildAtPosition(newInstance, position);
				getComponentInstances().get(newComponent).add(newInstance);
			}

			for (Entry<Node, Configuration> entry : componentInstance.getConfigurationByTarget().entrySet()) {

				if (entry.getKey() == configuration.getTarget()) {

					componentInstance.getChildren().remove(entry.getValue());
				}

			}

		}

		multiSets.put(newComponent, MultiSet.generate(newComponent));

		return newComponent;

	}

	public void mergeComponents(Component component1, Component component2) {
		for (Configuration configuration : component2.getConfigurations()) {
			addConfiguration(component1, configuration, component2, this);
		}
		this.componentInstances.remove(component2);
		this.multiSets.remove(component2);
	}

	public void addComponents(List<ComponentComparison> componentComparisons, CloneModel cloneModel2) {

		for (ComponentComparison componentComparison : componentComparisons) {

			if (componentComparison.getComponent1() == null && componentComparison.getComponent2() != null) {
				Component component = componentComparison.getComponent2();

				Set<Component> instances = cloneModel2.getComponentInstances().get(component);
				componentInstances.put(component, instances);
				multiSets.put(component, cloneModel2.getMultiSets().get(component));

			}
		}

	}

	public void removeComponents(List<ComponentComparison> componentComparisons) {

		for (ComponentComparison componentComparison : componentComparisons) {

			if (componentComparison.getComponent1() != null && componentComparison.getComponent2() == null) {
				Component component = componentComparison.getComponent1();
				removeComponent(component);

			}
		}

	}

	public void removeComponent(Component component) {
		multiSets.remove(component);

		for (Component componentInstance : componentInstances.get(component)) {
			Configuration selectedConfiguration = componentInstance.getSelectedConfiguration();
			Node parent = componentInstance.getParent();
			int position = componentInstance.getPosition();
			parent.getChildren().remove(position);
			parent.addChildAtPosition(selectedConfiguration.getChildren().get(0), position);
		}

		componentInstances.remove(component);
	}

	public Map<String, Set<Component>> getGranularityToComponents() {

		Map<String, Set<Component>> granularityToComponents = new HashMap<String, Set<Component>>();
		for (Component component : componentInstances.keySet()) {
			if (!granularityToComponents.containsKey(component.getLayer())) {
				granularityToComponents.put(component.getLayer(), new HashSet<Component>());
			}
			granularityToComponents.get(component.getLayer()).add(component);
		}
		return granularityToComponents;
	}

	public void replaceComponents(List<ComponentComparison> componentComparisons, CloneModel cloneModel2) {

		for (ComponentComparison componentComparison : componentComparisons) {

			if (componentComparison.getComponent1() != null && componentComparison.getComponent2() != null) {

				for (Component componentInstance : cloneModel2.getComponentInstances()
						.get(componentComparison.getComponent2())) {

					Node parent = componentInstance.getParent();
					int position = parent.getChildren().indexOf(componentInstance);
					Node target = componentInstance.getSelectedConfiguration().getChildren().get(0);

					Component newInstance = new ComponentImpl();

					for (Configuration configuration : componentComparison.getComponent1().getConfigurations()) {

						Configuration newConfiguration = new ConfigurationImpl();
						newConfiguration.addChildWithParent(configuration.getChildren().get(0));
						newInstance.addChildWithParent(newConfiguration);
					}

					for (Entry<Configuration, Configuration> entry : componentComparison.getMatchedConfigurations()
							.entrySet()) {

						if (entry.getValue().getChildren().get(0) == target) {

							for (Configuration configuration : componentInstance.getConfigurations()) {

								if (configuration.getChildren().get(0) == target) {
									componentInstance.setSelectedConfiguration(configuration);
									configuration.getChildren().clear();
									configuration.getChildren().add(entry.getKey().getChildren().get(0));
									break;
								}
							}
						} else {
							for (Configuration configuration : componentInstance.getConfigurations()) {
								if (configuration.getChildren().get(0) == entry.getValue().getChildren().get(0)) {
									configuration.getChildren().clear();
									configuration.getChildren().add(entry.getKey().getChildren().get(0));
									break;
								}
							}

						}

					}

					if (componentInstance.getSelectedConfiguration() == null) {
						for (Configuration addedConfiguration : componentComparison.getAddedConfigurations()) {

							if (addedConfiguration.getChildren().get(0) == target) {

								for (Configuration configuration : componentInstance.getConfigurations()) {

									if (configuration.getChildren().get(0) == target) {
										componentInstance.setSelectedConfiguration(configuration);
										break;
									}

								}

							}

						}
					}

					parent.getChildren().remove(position);
					parent.addChildAtPosition(componentInstance, position);
					componentInstances.get(componentComparison.getComponent1()).add(componentInstance);

				}
			}

		}

	}
}
