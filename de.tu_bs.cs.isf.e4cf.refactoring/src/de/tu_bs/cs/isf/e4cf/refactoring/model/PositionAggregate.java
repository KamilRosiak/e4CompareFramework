package de.tu_bs.cs.isf.e4cf.refactoring.model;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class PositionAggregate {

	private int leftPosition;
	
	private Node leftNode;
	
	private int rightPosition;
	
	private Node rightNode;

	public int getLeftPosition() {
		return leftPosition;
	}

	public void setLeftPosition(int leftPosition) {
		this.leftPosition = leftPosition;
	}

	public Node getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(Node leftNode) {
		this.leftNode = leftNode;
	}

	public int getRightPosition() {
		return rightPosition;
	}

	public void setRightPosition(int rightPosition) {
		this.rightPosition = rightPosition;
	}

	public Node getRightNode() {
		return rightNode;
	}

	public void setRightNode(Node rightNode) {
		this.rightNode = rightNode;
	}

	public PositionAggregate(int leftPosition, Node leftNode, int rightPosition, Node rightNode) {
		super();
		this.leftPosition = leftPosition;
		this.leftNode = leftNode;
		this.rightPosition = rightPosition;
		this.rightNode = rightNode;
	}
	
	
	
	
}
