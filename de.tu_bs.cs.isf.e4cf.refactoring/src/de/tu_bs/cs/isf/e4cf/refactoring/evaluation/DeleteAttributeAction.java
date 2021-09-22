package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class DeleteAttributeAction extends ChangeAction {

	private Attribute attribute;

	public DeleteAttributeAction(Node node, Attribute attribute) {
		super(node, ChangeActionType.DELETE_ATTRIBUTE);
		this.attribute = attribute;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

}
