package de.tu_bs.cs.isf.e4cf.refactoring.model;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class UpdateAction extends Action {

	public UpdateAction(Node affectedNode, Node actionNode) {
		super(ActionType.UPDATE, affectedNode, actionNode);
		// TODO Auto-generated constructor stub
	}

}
