package de.tu_bs.cs.isf.e4cf.refactoring.model;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class AddAction extends Action {

	public AddAction(Node affectedNode, Node actionNode, boolean addAtPositionZero) {
		super(ActionType.ADD, affectedNode, actionNode);
		this.addAtPositionZero = addAtPositionZero;
	}
	
	public boolean addAtPositionZero() {
		return addAtPositionZero;
	}

	public void setAddAtPositionZero(boolean addAtPositionZero) {
		this.addAtPositionZero = addAtPositionZero;
	}

	private boolean addAtPositionZero;

}
