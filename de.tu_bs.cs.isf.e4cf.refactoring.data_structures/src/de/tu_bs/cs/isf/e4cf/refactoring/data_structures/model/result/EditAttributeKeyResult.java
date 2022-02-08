package de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.result;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class EditAttributeKeyResult {

	private Node node;
	
	private Attribute attribute;
	
	private String key;

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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public EditAttributeKeyResult(Node node, Attribute attribute, String key) {
		super();
		this.node = node;
		this.attribute = attribute;
		this.key = key;
	}
	
	
	
	
	
}
