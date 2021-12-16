package de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.result;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class AddChildResult {

	private Node parent;

	private int position;

	private Node child;

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Node getChild() {
		return child;
	}

	public void setChild(Node child) {
		this.child = child;
	}

	public AddChildResult(Node parent, int position, Node child) {
		super();
		this.parent = parent;
		this.position = position;
		this.child = child;
	}

}
