package de.tu_bs.cs.isf.e4cf.refactoring.model;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public abstract class Action {

	private ActionType actionType;

	protected Node x;

	public Node getX() {
		return x;
	}

	public void setX(Node x) {
		this.x = x;
	}

	protected Action(ActionType actionType, Node x) {
		this.actionType = actionType;
		this.x = x;

	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

}
