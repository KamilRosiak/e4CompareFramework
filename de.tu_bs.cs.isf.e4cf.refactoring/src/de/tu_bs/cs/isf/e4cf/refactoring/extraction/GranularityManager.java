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
import de.tu_bs.cs.isf.e4cf.refactoring.evaluation.GranularityCallback;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Granularity;
import de.tu_bs.cs.isf.e4cf.refactoring.util.SynchronizationUtil;

@Singleton
@Creatable
public class GranularityManager {

	private GranularityViewController granularityViewController;

	@Inject
	public GranularityManager(GranularityViewController granularityViewController) {
		this.granularityViewController = granularityViewController;
	}

	public Map<Tree, Map<Granularity, List<Node>>> extractNodesOfCertainGranularities(Tree tree1, Tree tree2,
			GranularityCallback granularityCallback) {

		Set<String> nodeTypes = new HashSet<String>();
		nodeTypes.addAll(tree1.getRoot().getAllNodeTypes());
		nodeTypes.addAll(tree2.getRoot().getAllNodeTypes());

		Map<Tree, Map<Granularity, List<Node>>> treeToGranularities = new HashMap<Tree, Map<Granularity, List<Node>>>();
		Map<Granularity, List<Node>> granularityToNodes1 = new HashMap<Granularity, List<Node>>();
		Map<Granularity, List<Node>> granularityToNodes2 = new HashMap<Granularity, List<Node>>();

		List<Granularity> granularities = SynchronizationUtil.getGranularities(nodeTypes);

		if (granularityCallback == null) {
			granularityViewController.showView(granularities);
			if (!granularityViewController.isResult()) {
				return null;

			}
		} else {
			granularityCallback.handle(granularities);
		}

		for (Granularity granularity : granularities) {
			if (granularity.refactor()) {
				granularityToNodes1.put(granularity, getNodesByLayer(tree1, granularity.getLayer()));
				granularityToNodes2.put(granularity, getNodesByLayer(tree2, granularity.getLayer()));
			}
		}

		treeToGranularities.put(tree1, granularityToNodes1);
		treeToGranularities.put(tree2, granularityToNodes2);

		return treeToGranularities;
	}

	public Map<Granularity, List<Node>> extractNodesOfCertainGranularities(Tree tree,
			GranularityCallback granularityCallback) {

		Map<Granularity, List<Node>> granularityToNodes = new HashMap<Granularity, List<Node>>();
		Set<String> nodeTypes = new HashSet<String>();
		nodeTypes.addAll(tree.getRoot().getAllNodeTypes());
		List<Granularity> granularities = SynchronizationUtil.getGranularities(nodeTypes);

		if (granularityCallback == null) {
			granularityViewController.showView(granularities);
			if (!granularityViewController.isResult()) {
				return null;
			}
		} else {
			granularityCallback.handle(granularities);
		}

		for (Granularity granularity : granularities) {
			if (granularity.refactor()) {
				granularityToNodes.put(granularity, getNodesByLayer(tree, granularity.getLayer()));
			}
		}

		return granularityToNodes;
	}

	private List<Node> getNodesByLayer(Tree tree, String layer) {

		List<Node> filteredNodes = new ArrayList<Node>();
		List<Node> nodes = tree.getNodesForType(layer);
		for (Node node : nodes) {
			Node parent = node.getParent();
			boolean isParentFromSameLayer = false;
			while (parent != null) {
				if (parent.getNodeType().equals(layer)) {
					isParentFromSameLayer = true;
				}
				parent = parent.getParent();
			}

			if (!isParentFromSameLayer) {
				filteredNodes.add(node);
			}

		}

		return filteredNodes;
	}

}
