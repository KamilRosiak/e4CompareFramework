package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ExtractionResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentLayer;

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

		return new ExtractionResult(components, Arrays.asList(tree));
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
					Comparison<Node> comparison = compareEngine.compare(clusterInstance1, clusterInstance2);
					synchronizationUnitEngine.determineSynchronizationUnit(comparison, synchronizationUnit);
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

	public void manageComponents(ExtractionResult extractionResult) {

		System.out.println("");
		System.out.println("|--------- Verify components ---------|");

		for (Component component : extractionResult.getComponents()) {
			List<Node> nodes = new ArrayList<Node>();
			for (Configuration configuration : component.getConfigurations()) {

				nodes.add(configuration.getChildren().get(0));
			}

			boolean isValid = clusterEngine.verifyCluster(nodes, DISTANCE);

			if (!isValid) {

				System.out.println("Found invalid cluster, trying to split");

				List<Component> newComponents = splitCluster(component, nodes);
				extractionResult.setComponents(newComponents);
			}
		}

		System.out.println("All components are valid and synchronized");

	}

	private List<Component> splitCluster(Component component, List<Node> nodes) {

		List<Set<Node>> clusters = clusterEngine.detectClusters(nodes, DISTANCE);
		List<Set<Node>> clustersOfSize1 = new ArrayList<Set<Node>>();
		List<Component> newComponents = new ArrayList<Component>();

		for (Set<Node> cluster : clusters) {
			if (cluster.size() == 1) {
				clustersOfSize1.add(cluster);
				// single cluster: remove component from that place
				Node node = cluster.iterator().next();

				for (Configuration configuration : component.getConfigurations()) {
					if (configuration.getChildren().get(0) == node) {

						component.getChildren().remove(configuration);

						for (Entry<Node, Map<Integer, Configuration>> entry : component.getNodeComponentRelation()
								.entrySet()) {
							Node parentNode = entry.getKey();
							Map<Integer, Configuration> positionMapping = entry.getValue();
							int position = -1;
							for (Entry<Integer, Configuration> positionEntry : positionMapping.entrySet()) {

								if (positionEntry.getValue() == configuration) {
									position = positionEntry.getKey();
									parentNode.getChildren().remove(position);
									parentNode.getChildren().add(position, node);

									synchronizationUnitEngine.removeFromSynchronizationUnit(node,
											component.getSynchronizationUnit());

									break;
								}

							}

							if (position != -1) {
								positionMapping.remove(position);
							}

						}

					}
				}

			}
		}

		clusters.removeAll(clustersOfSize1);

		for (Set<Node> cluster : clusters) {
			Map<Node, Set<Node>> synchronizationUnit = new HashMap<Node, Set<Node>>();
			for (Node clusterInstance1 : cluster) {
				for (Node clusterInstance2 : cluster) {
					Comparison<Node> comparison = compareEngine.compare(clusterInstance1, clusterInstance2);
					synchronizationUnitEngine.determineSynchronizationUnit(comparison, synchronizationUnit);
				}

				Component newComponent = new ComponentImpl(synchronizationUnit);
				Map<Node, Map<Integer, Configuration>> newNodeComponentRelation = new HashMap<Node, Map<Integer, Configuration>>();

				for (Node clusterInstance : cluster) {
					for (Configuration configuration : component.getConfigurations()) {
						if (configuration.getChildren().get(0) == clusterInstance) {
							newComponent.addChildWithParent(configuration);

							for (Entry<Node, Map<Integer, Configuration>> entry : component.getNodeComponentRelation()
									.entrySet()) {
								Node parentNode = entry.getKey();
								Map<Integer, Configuration> positionMapping = entry.getValue();
								for (Entry<Integer, Configuration> positionEntry : positionMapping.entrySet()) {

									int position = positionEntry.getKey();
									if (positionEntry.getValue() == configuration) {

										if (!newNodeComponentRelation.containsKey(parentNode)) {
											newNodeComponentRelation.put(parentNode,
													new HashMap<Integer, Configuration>());
										}

										newNodeComponentRelation.get(parentNode).put(positionEntry.getKey(),
												configuration);

										parentNode.getChildren().remove(position);
										parentNode.getChildren().add(position, newComponent);

										break;
									}

								}

							}

						}
					}
				}
				newComponent.setNodeComponentRelation(newNodeComponentRelation);
				newComponents.add(newComponent);

			}
		}

		if (component.getChildren().size() != 0) {
			newComponents.add(component);
		}
		return newComponents;

	}

	private List<Node> getNodesByLayer(Tree tree, String layer) {

		List<Node> nodes = tree.getNodesForType(layer);

		// a cloned method within a cloned method leads to a cycle and must therefore be
		// removed
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
