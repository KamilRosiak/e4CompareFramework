package de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.result;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class MoveResult {
	
	private Node node;
	
	private int position;

	public MoveResult(Node node, int position) {
		super();
		this.node = node;
		this.position = position;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	

}
