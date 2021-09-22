package de.tu_bs.cs.isf.e4cf.refactoring.model;

import java.util.Map;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class AddActionResult extends ActionResult {

	private Map<Node, Integer> affectedNodesWithPosition;
	
	private Map<MultiSetNode, MultiSetNode> addedMultiSetNodes;

	public Map<Node, Integer> getAffectedNodesWithPosition() {
		return affectedNodesWithPosition;
	}

	public void setAffectedNodesWithPosition(Map<Node, Integer> affectedNodesWithPosition) {
		this.affectedNodesWithPosition = affectedNodesWithPosition;
	}

	public AddActionResult(Set<Node> affectedNodes, Set<MultiSetNode> affectedMultiSetNodes,
			Map<Node, Integer> affectedNodesWithPosition, Map<MultiSetNode, MultiSetNode> addedMultiSetNodes) {
		super(affectedNodes, affectedMultiSetNodes);
		this.affectedNodesWithPosition = affectedNodesWithPosition;
		this.addedMultiSetNodes = addedMultiSetNodes;
	}

	public Map<MultiSetNode, MultiSetNode> getAddedMultiSetNodes() {
		return addedMultiSetNodes;
	}

	public void setAddedMultiSetNodes(Map<MultiSetNode, MultiSetNode> addedMultiSetNodes) {
		this.addedMultiSetNodes = addedMultiSetNodes;
	}

}
