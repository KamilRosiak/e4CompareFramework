package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.refactoring.controllers.GranularityViewController;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Granularity;
import de.tu_bs.cs.isf.e4cf.refactoring.util.SynchronizationUtil;

@Singleton
@Creatable
public class GranularityManager {

	@Inject
	private GranularityViewController granularityViewController;

	public Map<Tree, Map<Granularity, List<Node>>> extractNodesOfCertainGranularities(Tree tree1, Tree tree2) {

		Set<String> nodeTypes = new HashSet<String>();
		nodeTypes.addAll(tree1.getRoot().getAllNodeTypes());
		nodeTypes.addAll(tree2.getRoot().getAllNodeTypes());

		Map<Tree, Map<Granularity, List<Node>>> treeToLayers = new HashMap<Tree, Map<Granularity, List<Node>>>();
		Map<Granularity, List<Node>> layerToNodes1 = new HashMap<Granularity, List<Node>>();
		Map<Granularity, List<Node>> layerToNodes2 = new HashMap<Granularity, List<Node>>();

		List<Granularity> componentLayers = SynchronizationUtil.getGranularities(nodeTypes);

		granularityViewController.showView(componentLayers);
		if (granularityViewController.isResult()) {

			for (Granularity layer : componentLayers) {
				if (layer.refactor()) {
					layerToNodes1.put(layer, getNodesByLayer(tree1, layer.getLayer()));
					layerToNodes2.put(layer, getNodesByLayer(tree2, layer.getLayer()));
				}
			}
		} else {
			return null;
		}

		treeToLayers.put(tree1, layerToNodes1);
		treeToLayers.put(tree2, layerToNodes2);

		return treeToLayers;
	}

	public Map<Granularity, List<Node>> extractNodesOfCertainGranularities(Tree tree) {

		Map<Granularity, List<Node>> layerToNodes = new HashMap<Granularity, List<Node>>();
		Set<String> nodeTypes = new HashSet<String>();
		nodeTypes.addAll(tree.getRoot().getAllNodeTypes());
		List<Granularity> componentLayers = SynchronizationUtil.getGranularities(nodeTypes);

		granularityViewController.showView(componentLayers);
		if (granularityViewController.isResult()) {

			for (Granularity layer : componentLayers) {
				if (layer.refactor()) {
					layerToNodes.put(layer, getNodesByLayer(tree, layer.getLayer()));
				}
			}
		} else {
			return null;
		}

		return layerToNodes;
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
