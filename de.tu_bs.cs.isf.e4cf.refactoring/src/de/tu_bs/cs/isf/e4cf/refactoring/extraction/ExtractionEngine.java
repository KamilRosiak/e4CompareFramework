package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.ComponentImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.ConfigurationImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentLayer;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ExtractionResult;

@Singleton
@Creatable
public class ExtractionEngine {

	public final boolean PRINT_METRICS = true;
	public final float DISTANCE = 0.15f;

	private CompareEngineHierarchical compareEngine;
	private ClusterEngine clusterEngine;
	private SynchronizationUnitEngine synchronizationUnitEngine;

	public ExtractionEngine() {
		compareEngine = new CompareEngineHierarchical(new SortingMatcher(), new MetricImpl("test"));
		clusterEngine = new ClusterEngine();
		synchronizationUnitEngine = new SynchronizationUnitEngine();

	}

	public ExtractionResult extractComponents(Tree tree, List<ComponentLayer> componentLayers) {

		System.out.println("");
		System.out.println("|--------- Extract components ---------|");

		List<Component> components = new ArrayList<Component>();

		for (ComponentLayer layer : componentLayers) {
			if (layer.refactor()) {
				components.addAll(extractComponents(getNodesByLayer(tree, layer.getLayer())));
			}

		}

		System.out.println("");
		System.out.println("Extracted components: " + components.size());

		return new ExtractionResult(components, Arrays.asList(tree),
				componentLayers.stream().filter(w -> w.refactor() == true).collect(Collectors.toList()));
	}

	private List<Component> extractComponents(List<Node> nodes) {

		List<Component> components = new LinkedList<Component>();

		if (nodes.size() == 1) {
			return components;
		}

		// find clusters with predefined threshold
		List<Set<Node>> clusters = clusterEngine.detectClusters(nodes, DISTANCE);

		// filter clusters
		List<Set<Node>> filteredClusters = new ArrayList<Set<Node>>();
		for (Set<Node> cluster : clusters) {
			if (cluster.size() != 1) {
				filteredClusters.add(cluster);
			}

		}

		for (Set<Node> cluster : filteredClusters) {
			Map<Node, Set<Node>> synchronizationUnit = new HashMap<Node, Set<Node>>();
			Component component = new ComponentImpl(synchronizationUnit);
			component.setNodeComponentRelation(new HashMap<Node, Map<Integer, Configuration>>());

			for (Node clusterInstance1 : cluster) {
				for (Node clusterInstance2 : cluster) {

					if (clusterInstance1 != clusterInstance2) {
						synchronizationUnitEngine.determineSynchronizationUnit(
								compareEngine.compare(clusterInstance1, clusterInstance2), synchronizationUnit);

					}

				}

				Configuration configuration = new ConfigurationImpl();
				configuration.addChild(clusterInstance1);
				component.addChildWithParent(configuration);

				Node cloneParent = clusterInstance1.getParent();
				int position = cloneParent.getChildren().indexOf(clusterInstance1);

				if (!component.getNodeComponentRelation().containsKey(cloneParent)) {
					component.getNodeComponentRelation().put(cloneParent, new HashMap<Integer, Configuration>());
				}
				component.getNodeComponentRelation().get(cloneParent).put(position, configuration);

				// replace clone with component
				int index = cloneParent.getChildren().indexOf(clusterInstance1);
				cloneParent.getChildren().remove(index);
				cloneParent.getChildren().add(index, component);

			}

			components.add(component);
		}
		return components;
	}

	public void analyzeComponents(ExtractionResult extractionResult) {

		System.out.println("");
		System.out.println("|--------- Rebuild components ---------|");

		List<Component> componentsToAdd = new ArrayList<Component>();
		List<Component> componentsToDelete = new ArrayList<Component>();

		for (Component component : extractionResult.getComponents()) {

			Map<Node, Configuration> nodes = new HashMap<Node, Configuration>();

			for (Configuration configuration : component.getConfigurations()) {
				nodes.put(configuration.getChildren().get(0), configuration);
			}

			boolean verification = clusterEngine.verifyCluster(nodes.keySet(), DISTANCE);

			if (!verification) {

				System.out.println("Found invalid cluster.");
				
				componentsToDelete.add(component);
				List<Set<Node>> clusters = clusterEngine.detectClusters(nodes.keySet(), DISTANCE);

				for (Set<Node> cluster : clusters) {

					if (cluster.size() == 1) {

						Node clusterInstance = cluster.iterator().next();
						Configuration configuration = nodes.get(clusterInstance);

						for (Entry<Node, Map<Integer, Configuration>> entry : component.getNodeComponentRelation()
								.entrySet()) {
							Node parentNode = entry.getKey();
							Map<Integer, Configuration> positionMapping = entry.getValue();
							for (Entry<Integer, Configuration> positionEntry : positionMapping.entrySet()) {

								if (positionEntry.getValue() == configuration) {

									int index = positionEntry.getKey();
									parentNode.getChildren().remove(index);
									parentNode.getChildren().add(index, clusterInstance);
								}

							}
						}

					} else {
						Map<Node, Set<Node>> synchronizationUnit = new HashMap<Node, Set<Node>>();
						Component newComponent = new ComponentImpl(synchronizationUnit);
						component.setNodeComponentRelation(new HashMap<Node, Map<Integer, Configuration>>());

						for (Node clusterInstance : cluster) {
							Configuration newConfiguration = new ConfigurationImpl();
							newConfiguration.addChild(clusterInstance);
							newComponent.addChildWithParent(newConfiguration);

							Configuration oldConfiguration = nodes.get(clusterInstance);

							for (Entry<Node, Map<Integer, Configuration>> entry : component.getNodeComponentRelation()
									.entrySet()) {
								Node parentNode = entry.getKey();
								Map<Integer, Configuration> positionMapping = entry.getValue();
								for (Entry<Integer, Configuration> positionEntry : positionMapping.entrySet()) {

									if (positionEntry.getValue() == oldConfiguration) {
										if (!newComponent.getNodeComponentRelation().containsKey(parentNode)) {
											newComponent.getNodeComponentRelation().put(parentNode,
													new HashMap<Integer, Configuration>());
										}
										newComponent.getNodeComponentRelation().get(parentNode)
												.put(positionEntry.getKey(), newConfiguration);

										int index = positionEntry.getKey();
										parentNode.getChildren().remove(index);
										parentNode.getChildren().add(index, newComponent);
									}

								}
							}

						}
						for (Node clusterInstance1 : cluster) {
							for (Node clusterInstance2 : cluster) {

								if (clusterInstance1 != clusterInstance2) {
									synchronizationUnitEngine.determineSynchronizationUnit(
											compareEngine.compare(clusterInstance1, clusterInstance2),
											synchronizationUnit);

								}

							}
						}
						componentsToAdd.add(newComponent);
					}

				}

			}

		}

		extractionResult.getComponents().removeAll(componentsToDelete);
		extractionResult.getComponents().addAll(componentsToAdd);

	}

	private List<Node> getNodesByLayer(Tree tree, String layer) {

		List<Node> nodes = tree.getNodesForType(layer);

		// a cloned method within a cloned method leads to a cycle and must therefore be removed
		if (layer.equals("MethodDeclaration")) {

			List<Node> cleanNodes = new ArrayList<Node>();
			for (Node node : nodes) {
				if (node.getParent().getNodeType().equals("CompilationUnit")) {
					cleanNodes.add(node);
				}
			}
			return cleanNodes;
		}

		return nodes;
	}

}
