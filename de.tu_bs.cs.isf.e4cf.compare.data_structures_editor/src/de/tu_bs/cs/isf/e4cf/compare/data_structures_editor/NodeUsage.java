package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractNode;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class NodeUsage extends AbstractNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = 378278236426734L;

	public NodeUsage(String nodeType) {
		setNodeType(nodeType);
	}

	public NodeUsage(Node node) {
		setNodeType(node.getNodeType());
		setAttributes(node.getAttributes());
		setParent(node.getParent());
	}

	@Override
	public String toString() {
		String s1 = "";
		// Condition must be revised so that not every node without attributes is root
		if (this.getAttributes().isEmpty()) {
			return s1 + "Root";
		}
//		String returnString = getAttributeForKey("text").toString();
		for (Attribute attribut : this.getAttributes()) {
			for (String s : attribut.getAttributeValues()) {
				s1 = s;
			}
		}
		return s1;
	}
//
//	public String fakeToString() {
//		String returnString = getNodeType();
//		returnString += " NodeUsage";
//		return returnString;
//	}

	public NodeUsage(String nodeType, Node parent) {
		this(nodeType);
		setParent(parent);
		parent.addChild(this);
	}

	public NodeUsage(String nodeString, Node parent, VariabilityClass varClass) {
		this(nodeString, parent);
		setVariabilityClass(varClass);
	}

}
