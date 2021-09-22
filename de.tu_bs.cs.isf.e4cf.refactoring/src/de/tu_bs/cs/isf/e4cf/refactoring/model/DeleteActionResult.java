package de.tu_bs.cs.isf.e4cf.refactoring.model;

import java.util.Map;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class DeleteActionResult {
	
	private Set<Node> deletedNodes;
	
	private Map<MultiSetNode, MultiSetNode> deletedMultiSetNodes;

	public Set<Node> getDeletedNodes() {
		return deletedNodes;
	}

	public void setDeletedNodes(Set<Node> deletedNodes) {
		this.deletedNodes = deletedNodes;
	}

	public Map<MultiSetNode, MultiSetNode> getDeletedMultiSetNodes() {
		return deletedMultiSetNodes;
	}

	public void setDeletedMultiSetNodes(Map<MultiSetNode, MultiSetNode> deletedMultiSetNodes) {
		this.deletedMultiSetNodes = deletedMultiSetNodes;
	}

	public DeleteActionResult(Set<Node> deletedNodes, Map<MultiSetNode, MultiSetNode> deletedMultiSetNodes) {
		super();
		this.deletedNodes = deletedNodes;
		this.deletedMultiSetNodes = deletedMultiSetNodes;
	}
	
	

}
