package de.tu_bs.cs.isf.e4cf.refactoring.model.result;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class DeleteResult {

	
	private Node node;

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public DeleteResult(Node node) {
		super();
		this.node = node;
	}
	
}
