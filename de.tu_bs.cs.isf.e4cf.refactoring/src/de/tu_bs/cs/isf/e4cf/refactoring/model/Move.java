package de.tu_bs.cs.isf.e4cf.refactoring.model;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class Move extends Action {

	public Move(Node x, Node y, int position) {
		super(ActionType.MOVE, x);		
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



	private int savedPosition;

	@Override
	public void apply() {
		savedPosition = x.getPosition();
		x.getParent().getChildren().remove(x);
		y.addChildAtPosition(x, position);

	}

	@Override
	public void undo() {
		x.getParent().addChildAtPosition(x, savedPosition);
		y.getChildren().remove(x);

	}
}
