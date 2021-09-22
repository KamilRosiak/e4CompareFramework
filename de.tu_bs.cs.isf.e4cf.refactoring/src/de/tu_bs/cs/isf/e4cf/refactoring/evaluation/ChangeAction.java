package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class ChangeAction {
	
	private Node target;
	
	private ChangeActionType changeActionType;

	public Node getTarget() {
		return target;
	}

	public void setTarget(Node target) {
		this.target = target;
	}

	public ChangeActionType getChangeActionType() {
		return changeActionType;
	}

	public void setChangeActionType(ChangeActionType changeActionType) {
		this.changeActionType = changeActionType;
	}

	
	public ChangeAction(Node target, ChangeActionType changeActionType) {
		super();
		this.target = target;
		this.changeActionType = changeActionType;
	}
	
	
	
	

}
