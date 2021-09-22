package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class DeleteNodeAction extends ChangeAction {

	public DeleteNodeAction(Node node) {
		super(node, ChangeActionType.DELETE_NODE);
	}

}
