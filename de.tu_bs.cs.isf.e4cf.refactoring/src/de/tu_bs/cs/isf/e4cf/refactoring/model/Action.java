package de.tu_bs.cs.isf.e4cf.refactoring.model;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class Action {
	
	private ActionType actionType;
	
	private Node affectedNode;
	
	private Node actionNode;	
	

	public Action(ActionType actionType, Node affectedNode, Node actionNode) {
	
		this.actionType = actionType;
		this.affectedNode = affectedNode;
		this.actionNode = actionNode;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public Node getAffectedNode() {
		return affectedNode;
	}

	public void setAffectedNode(Node affectedNode) {
		this.affectedNode = affectedNode;
	}

	public Node getActionNode() {
		return actionNode;
	}

	public void setActionNode(Node actionNode) {
		this.actionNode = actionNode;
	}
	
	

}
