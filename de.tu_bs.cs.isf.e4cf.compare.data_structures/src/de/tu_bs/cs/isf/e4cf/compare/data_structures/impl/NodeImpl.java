package de.tu_bs.cs.isf.e4cf.compare.data_structures.impl;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractNode;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * The concrete implementation of the Node interface.
 * 
 * @author Kamil Rosiak
 *
 */
public class NodeImpl extends AbstractNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2646668251637650151L;

	public NodeImpl() {
		super();
	}

	/**
	 * This constructor initializes a node without a parent , e.g, the root node.
	 * 
	 * @param nodeType is the type of the node , e.g , statement, method, class
	 */
	public NodeImpl(String nodeType) {
		this();
		setNodeType(nodeType);
	}

	/**
	 * This constructor initializes a node with a given type. Also, the given parent
	 * node is set, and this node is added as a child node.
	 * 
	 * @param nodeType is the type of the node , e.g , statement, method, class
	 */
	public NodeImpl(String nodeType, Node parent) {
		this(nodeType);
		setParent(parent);
		if (parent != null) {
			parent.addChildWithParent(this);
		}
	}

	/**
	 * This constructor initializes a node with a given type and a
	 * given @VariabilityClass. Also, the given parent node is set, and this node is
	 * added as a child node.
	 * 
	 * @param nodeType is the type of the node , e.g , statement, method, class
	 */
	public NodeImpl(String nodeString, Node parent, VariabilityClass varClass) {
		this(nodeString, parent);
		setVariabilityClass(varClass);
	}

	@Override
	public Node cloneNode() {
		Node newNode = new NodeImpl();
		newNode.setNodeType(getNodeType());
		newNode.setVariabilityClass(getVariabilityClass());

		for (Attribute attribute : getAttributes()) {
			Attribute newAttribute = new AttributeImpl(attribute.getAttributeKey(), attribute.getAttributeValues());
			newNode.addAttribute(newAttribute);
		}

		for (Node child : getChildren()) {
			newNode.addChildWithParent(child.cloneNode());
		}

		return newNode;
	}

}
