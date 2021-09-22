package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

public class DeleteAttributeValueAction extends ChangeAction {

	private Attribute attribute;

	private Value value;

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

	public DeleteAttributeValueAction(Node node, Attribute attribute, Value value) {
		super(node, ChangeActionType.DELETE_ATTRIBUTE_VALUE);
		this.attribute = attribute;
		this.value = value;

	}

}
