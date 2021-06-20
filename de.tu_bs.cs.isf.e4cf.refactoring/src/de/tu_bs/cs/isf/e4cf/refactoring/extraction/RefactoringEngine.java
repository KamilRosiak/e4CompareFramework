package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import de.tu_bs.cs.isf.e4cf.refactoring.model.RefactoringLayer;
import de.tu_bs.cs.isf.e4cf.refactoring.model.RefactoringResult;

@Singleton
@Creatable
public class RefactoringEngine {

	public final float THRESHOLD = 0.85f;

	private CompareEngineHierarchical compareEngine;
	private DetectionEngine detectionEngine;
	private SynchronizationUnitEngine synchronizationUnitEngine;

	public RefactoringEngine() {
		compareEngine = new CompareEngineHierarchical(new SortingMatcher(), new MetricImpl("test"));
		detectionEngine = new DetectionEngine();
		synchronizationUnitEngine = new SynchronizationUnitEngine();

	}

	public RefactoringResult refactor(Tree tree, List<RefactoringLayer> refactoringLayers,
			boolean removeType1And2Clones) {

		// assign position to tree elements
		assignPosition(tree.getRoot(), 0);

		List<Component> components = new ArrayList<Component>();
		for (RefactoringLayer layer : refactoringLayers) {
			if (layer.refactor()) {
				components.addAll(extractComponents(getNodesByLayer(tree, layer.getLayer()), removeType1And2Clones));
			}

		}

		return new RefactoringResult(components, Arrays.asList(tree));
	}

	private List<Component> extractComponents(List<Node> nodes, boolean removeType1And2Clones) {

		// find clusters with predefined threshold
		List<Set<Node>> clusters = detectionEngine.findClusters_Version2(nodes, THRESHOLD);

		List<List<Set<Node>>> filteredClusters = filterClusters(clusters, removeType1And2Clones);
	
		List<Component> components = new LinkedList<Component>();

		for (List<Set<Node>> cluster : filteredClusters) {

			Map<Node, Set<Node>> synchronizationUnit = new HashMap<Node, Set<Node>>();
			Component component = new ComponentImpl(synchronizationUnit);
			component.setNodeComponentRelation(new HashMap<Node, Map<Integer, Configuration>>());

			// build clean set to find synchronization unit only between type 3 clones
			Set<Node> nodesToSynchronize = new HashSet<Node>();
			for (Set<Node> nodeSet : cluster) {
				nodesToSynchronize.add(nodeSet.iterator().next());
			}

			// determine synchronization units
			for (Node node1 : nodesToSynchronize) {
				for (Node node2 : nodesToSynchronize) {
					if (!node1.equals(node2)) {
						Comparison<Node> comparison = compareEngine.compare(node1, node2);
						synchronizationUnitEngine.determineSynchronizationUnit(comparison, synchronizationUnit);
					}
				}
			}

			// create configuration
			for (Set<Node> cloneSet : cluster) {

				Node node = cloneSet.iterator().next();

				Configuration configuration = new ConfigurationImpl();

				configuration.addChild(node);
				component.addChildWithParent(configuration);

				for (Node clone : cloneSet) {
					Node cloneParent = clone.getParent();

					// replace clone with component
					int position = clone.getPosition();
					int index = cloneParent.getChildren().indexOf(clone);
					cloneParent.getChildren().remove(index);
					cloneParent.getChildren().add(index, component);

					// save relation between parent and selected configuration
					if (!component.getNodeComponentRelation().containsKey(cloneParent)) {
						component.getNodeComponentRelation().put(cloneParent, new HashMap<Integer, Configuration>());
					}
					component.getNodeComponentRelation().get(cloneParent).put(position, configuration);

				}

				node.setParent(configuration);
			}

			components.add(component);
		}

		return components;
	}

	private List<List<Set<Node>>> filterClusters(List<Set<Node>> clusters, boolean removeType1And2Clones) {

		Map<Node, Attribute> savedPositionAttribute = new HashMap<Node, Attribute>();

		List<List<Set<Node>>> filteredClusters = new ArrayList<List<Set<Node>>>();
		for (Set<Node> cluster : clusters) {

			List<Set<Node>> filteredCluster = new ArrayList<Set<Node>>();

			for (Node node : cluster) {
				Attribute position = node.getAttributeForKey("Position");
				savedPositionAttribute.put(node, position);
				node.getAttributes().remove(position);
			}

			List<Set<Node>> cloneClusters = new ArrayList<Set<Node>>();
			
			if(removeType1And2Clones) {
				// find clusters of type 1 or 2 clones
				cloneClusters = detectionEngine.findClusters_Version2(cluster, 1.0f);
			}
			
		

			for (Set<Node> node : cloneClusters) {
				filteredCluster.add(node);
			}

			for (Node node : cluster) {

				boolean alreadyInList = false;
				for (Set<Node> nodeSet : filteredCluster) {
					if (nodeSet.contains(node)) {
						alreadyInList = true;
					}

				}
				if (!alreadyInList) {
					Set<Node> set = new HashSet<Node>();
					set.add(node);
					filteredCluster.add(set);
				}

			}

			filteredClusters.add(filteredCluster);

			for (Node node : cluster) {
				node.addAttribute(savedPositionAttribute.get(node));
			}

		}

		return filteredClusters;
	}

	private void assignPosition(Node node, int position) {

		node.addAttribute("Position", new StringValueImpl(position + ""));

		int childPosition = 0;
		for (Node childNode : node.getChildren()) {
			assignPosition(childNode, childPosition);
			childPosition++;
		}

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
