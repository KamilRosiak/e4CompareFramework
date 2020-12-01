package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractNode;
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
//		String returnString = getAttributeForKey("text").toString();
		String returnString = getNodeType();
		returnString += " NodeUsage";
		return returnString;
	}

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
