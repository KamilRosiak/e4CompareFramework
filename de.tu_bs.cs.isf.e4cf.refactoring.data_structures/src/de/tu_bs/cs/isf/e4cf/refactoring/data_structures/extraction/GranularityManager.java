package de.tu_bs.cs.isf.e4cf.refactoring.data_structures.extraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.Granularity;

@Singleton
@Creatable
public class GranularityManager {

	public Map<Granularity, List<Node>> extractNodesOfCertainGranularities(Tree tree, Set<Granularity> granularities) {

		Map<Granularity, List<Node>> granularityToNodes = new HashMap<Granularity, List<Node>>();

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
