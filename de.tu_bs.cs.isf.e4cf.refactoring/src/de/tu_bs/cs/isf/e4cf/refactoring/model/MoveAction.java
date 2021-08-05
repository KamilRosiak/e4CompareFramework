package de.tu_bs.cs.isf.e4cf.refactoring.model;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class MoveAction extends Action {

	public MoveAction(Node affectedNode, Node actionNode, int originalPosition, int newPosition) {
		super(ActionType.MOVE, affectedNode, actionNode);	
		this.originalPosition = originalPosition;
		this.newPosition = newPosition;
		
	}
	
	private int originalPosition;
	
	private int newPosition;

	

	public int getOriginalPosition() {
		return originalPosition;
	}

	public void setOriginalPosition(int originalPosition) {
		this.originalPosition = originalPosition;
	}

	public int getNewPosition() {
		return newPosition;
	}

	public void setNewPosition(int newPosition) {
		this.newPosition = newPosition;
	}

	

}
