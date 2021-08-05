package de.tu_bs.cs.isf.e4cf.refactoring.model;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class DeleteAction extends Action {

	public DeleteAction(Node affectedNode, Node actionNode) {
		super(ActionType.DELETE, affectedNode, actionNode);
		// TODO Auto-generated constructor stub
	}

}
