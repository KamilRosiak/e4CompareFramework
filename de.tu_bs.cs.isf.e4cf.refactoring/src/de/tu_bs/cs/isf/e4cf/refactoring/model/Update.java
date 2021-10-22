package de.tu_bs.cs.isf.e4cf.refactoring.model;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class Update extends Action {

	public Update(Node x, Node y) {
		super(ActionType.UPDATE, x);
		this.y = y;
	}

	private Node y;

	public Node getY() {
		return y;
	}

	public void setY(Node y) {
		this.y = y;
	}
	
}
