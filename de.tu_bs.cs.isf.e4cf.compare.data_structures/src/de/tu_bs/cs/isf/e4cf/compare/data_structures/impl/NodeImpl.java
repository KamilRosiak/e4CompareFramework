package de.tu_bs.cs.isf.e4cf.compare.data_structures.impl;

import java.util.Iterator;
import java.util.UUID;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractNode;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * The concrete implementation of the Node interface.
 * 
 * @author Kamil Rosiak
 * @author David Bumm
 *
 */
public class NodeImpl extends AbstractNode {
	private static final long serialVersionUID = 2646668251637650151L;

	public NodeImpl() {
		super();
	}

	/**
	 * This constructor initialises a node without a parent , e.g, the root node.
	 * 
	 * @param nodeType is the type of the node , e.g , statement, method, class
	 */
	public NodeImpl(String nodeType) {
		this();
		setNodeType(nodeType);
		setStandardizedNodeType(NodeType.fromString(nodeType));
	}

	/**
	 * This constructor initialises a node without a parent , e.g, the root node.
	 * 
	 * @param standardizedNodeType is the standardised type of the node
	 */
	public NodeImpl(NodeType standardizedNodeType) {
		this();
		setNodeType(standardizedNodeType.name());
		setStandardizedNodeType(standardizedNodeType);
	}

	/**
	 * This constructor initialises a node without a parent , e.g, the root node.
	 * 
	 * @param standardizedNodeType is the standardised type of the node
	 * @param nodeType             is the type of the node , e.g , statement,
	 *                             method, class
	 */
	public NodeImpl(NodeType standardizedNodeType, String nodeType) {
		this();
		setStandardizedNodeType(standardizedNodeType);
		setNodeType(nodeType);
	}

	/**
	 * This constructor initialises a node with a given type. Also, the given parent
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
	 * This constructor initialises a node under a given parent.
	 * 
	 * @param standardizedNodeType is the standardised type of the node
	 * 
	 * @param nodeType             is the type of the node , e.g , statement,
	 *                             method, class
	 * @param parent               is the node to set as parent
	 */
	public NodeImpl(NodeType standardizedNodeType, String nodeType, Node parent) {
		this(standardizedNodeType, nodeType);
		setParent(parent);
		if (parent != null) {
			parent.addChildWithParent(this);
		}
	}

	/**
	 * This constructor initialises a node with a given type and a
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
		newNode.setNodeType(this.getNodeType());
		newNode.setStandardizedNodeType(this.getStandardizedNodeType());

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

	@Override
	public void addAttribute(String attributeKey, String value) {
		this.addAttribute(new AttributeImpl(attributeKey, new StringValueImpl(value)));

	}

	@Override
	public Node getNodeByUUID(UUID key) {
		return searchNodeByUUID(this, key);
	}

	private Node searchNodeByUUID(Node nodeImpl, UUID key) {
		if (nodeImpl.getUUID().equals(key)) {
			return nodeImpl;
		} else {
			for (Node childNode : nodeImpl.getChildren()) {
				return searchNodeByUUID(childNode, key);
			}
		}
		return null;
	}

	@Override
	public void removeElementsOfType(String string) {
		Iterator<Node> nodeIt = getChildren().iterator();
		while (nodeIt.hasNext()) {
			Node node = (Node) nodeIt.next();
			if (node.getNodeType().equals(string)) {
				nodeIt.remove();
			} else {
				if (node.getChildren() != null && !node.getChildren().isEmpty()) {
					node.getChildren().forEach(childNode -> {
						childNode.removeElementsOfType(string);
					});
				}
			}

		}
	}
}