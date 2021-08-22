package de.tu_bs.cs.isf.e4cf.refactoring.model;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class Insert extends Action {

	public Insert(Node x, Node y, int position) {
		super(ActionType.INSERT, x);
		this.y = y;
		this.position = position;
	}

	private Node y;

	private int position;

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Node getY() {
		return y;
	}

	public void setY(Node y) {
		this.y = y;
	}

	@Override
	public void apply() {
		y.addChildAtPosition(x, position);
	}

	@Override
	public void undo() {
		y.getChildren().remove(x);

	}

}
