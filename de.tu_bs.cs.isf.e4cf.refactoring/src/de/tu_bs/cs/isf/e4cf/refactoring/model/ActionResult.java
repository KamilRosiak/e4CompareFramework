package de.tu_bs.cs.isf.e4cf.refactoring.model;

import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class ActionResult {

	private Set<Node> affectedNodes;
	
	private Set<MultiSetNode> affectedMultiSetNodes;

	public Set<Node> getAffectedNodes() {
		return affectedNodes;
	}

	public void setAffectedNodes(Set<Node> affectedNodes) {
		this.affectedNodes = affectedNodes;
	}

	public Set<MultiSetNode> getAffectedMultiSetNodes() {
		return affectedMultiSetNodes;
	}

	public void setAffectedMultiSetNodes(Set<MultiSetNode> affectedMultiSetNodes) {
		this.affectedMultiSetNodes = affectedMultiSetNodes;
	}

	public ActionResult(Set<Node> affectedNodes, Set<MultiSetNode> affectedMultiSetNodes) {
		super();
		this.affectedNodes = affectedNodes;
		this.affectedMultiSetNodes = affectedMultiSetNodes;
	}

	

	
	
	
	
}
