package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class AddChildNodeAction extends ChangeAction {

	private Node child;

	public Node getChild() {
		return child;
	}

	public void setChild(Node child) {
		this.child = child;
	}

	public AddChildNodeAction(Node node, Node child) {
		super(node, ChangeActionType.ADD_CHILD_NODE);
		this.child = child;
	}

}
