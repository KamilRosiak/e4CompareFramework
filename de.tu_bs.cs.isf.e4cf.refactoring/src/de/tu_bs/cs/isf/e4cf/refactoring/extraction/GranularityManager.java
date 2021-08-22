package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.refactoring.controllers.ComponentLayerViewController;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentLayer;
import de.tu_bs.cs.isf.e4cf.refactoring.util.SynchronizationUtil;

@Singleton
@Creatable
public class GranularityManager {

	private ComponentLayerViewController componentLayerViewController;

	public GranularityManager() {
		componentLayerViewController = new ComponentLayerViewController();
	}

	public Map<Tree, Map<ComponentLayer, List<Node>>> extractNodesOfCertainGranularities(Tree tree1, Tree tree2) {

		Set<String> nodeTypes = new HashSet<String>();
		nodeTypes.addAll(tree1.getRoot().getAllNodeTypes());
		nodeTypes.addAll(tree2.getRoot().getAllNodeTypes());

		Map<Tree, Map<ComponentLayer, List<Node>>> treeToLayers = new HashMap<Tree, Map<ComponentLayer,List<Node>>>();
		Map<ComponentLayer, List<Node>> layerToNodes1 = new HashMap<ComponentLayer, List<Node>>();
		Map<ComponentLayer, List<Node>> layerToNodes2 = new HashMap<ComponentLayer, List<Node>>();

		List<ComponentLayer> componentLayers = SynchronizationUtil.getComponentLayers(nodeTypes);

		componentLayerViewController.showView(componentLayers);
		if (componentLayerViewController.isResult()) {

			for (ComponentLayer layer : componentLayers) {
				if (layer.refactor()) {
					layerToNodes1.put(layer, getNodesByLayer(tree1, layer.getLayer()));
					layerToNodes2.put(layer, getNodesByLayer(tree2, layer.getLayer()));
				}
			}
		}
		else {
			return null;
		}

		treeToLayers.put(tree1, layerToNodes1);
		treeToLayers.put(tree2, layerToNodes2);
		
		return treeToLayers;
	}

	public Map<ComponentLayer, List<Node>> extractNodesOfCertainGranularities(Tree tree) {

		Map<ComponentLayer, List<Node>> layerToNodes = new HashMap<ComponentLayer, List<Node>>();
		Set<String> nodeTypes = new HashSet<String>();
		nodeTypes.addAll(tree.getRoot().getAllNodeTypes());
		List<ComponentLayer> componentLayers = SynchronizationUtil.getComponentLayers(nodeTypes);

		componentLayerViewController.showView(componentLayers);
		if (componentLayerViewController.isResult()) {

			for (ComponentLayer layer : componentLayers) {
				if (layer.refactor()) {
					layerToNodes.put(layer, getNodesByLayer(tree, layer.getLayer()));
				}
			}
		}
		else {
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
