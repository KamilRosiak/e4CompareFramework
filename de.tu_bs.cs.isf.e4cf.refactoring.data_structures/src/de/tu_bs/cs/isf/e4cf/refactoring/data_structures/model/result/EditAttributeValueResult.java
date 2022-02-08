package de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.result;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

public class EditAttributeValueResult {

	private Node node;

	private Attribute attribute;

	private Value value;

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

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

	public EditAttributeValueResult(Node node, Attribute attribute, Value value) {
		super();
		this.node = node;
		this.attribute = attribute;
		this.value = value;
	}

}
