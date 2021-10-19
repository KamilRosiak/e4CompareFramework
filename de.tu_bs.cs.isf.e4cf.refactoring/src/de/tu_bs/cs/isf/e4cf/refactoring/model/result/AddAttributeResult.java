package de.tu_bs.cs.isf.e4cf.refactoring.model.result;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class AddAttributeResult {

	
	private Node node;
	
	private Attribute attribute;

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

	public AddAttributeResult(Node node, Attribute attribute) {
		super();
		this.node = node;
		this.attribute = attribute;
	}
	
	
	
}
