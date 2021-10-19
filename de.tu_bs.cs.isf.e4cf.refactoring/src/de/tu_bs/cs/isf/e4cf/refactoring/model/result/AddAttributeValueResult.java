package de.tu_bs.cs.isf.e4cf.refactoring.model.result;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

public class AddAttributeValueResult {
	
	private Attribute attribute;
	
	private Value value;
	
	private Node node;

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

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public AddAttributeValueResult(Attribute attribute, Value value, Node node) {
		super();
		this.attribute = attribute;
		this.value = value;
		this.node = node;
	}

	
	
	
	

}
