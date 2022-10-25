package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_reader;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

import java.util.ArrayList;
import java.util.List;

public final class AdjustTree {
	private Node rootNode;

	public AdjustTree(Node rootNode) {
		this.rootNode = rootNode;
	}

	public Node adjustAllNodes() {
		if (rootNode == null) {
			return null;
		}
		Node node = new NodeImpl("JAVA");
		node.addChild(rootNode);
		rootNode.setParent(node);
		rootNode.addAttribute(new AttributeImpl("IsInterface", new StringValueImpl("false")));
		rootNode.addAttribute(new AttributeImpl("AccessModifier", new StringValueImpl("PUBLIC")));
		recursiveAdjust(rootNode);
		return node;
	}

	private void recursiveAdjust(Node node) {
		if (node == null) {
			return;
		}

		adjust(node);

		if (node.getNumberOfChildren() > 0) {
			List<Node> children = new ArrayList<Node>(node.getChildren());
			for (Node child : children) {
				recursiveAdjust(child);
			}
		}
	}

	private void adjust(Node node) {
		Node parent = node.getParent();
		String nodeType = node.getNodeType();

		if (nodeType.equals("Name") || nodeType.equals("type")) {
			removeNode(node);
		}
		if (nodeType.equals("control") || nodeType.equals("Body")
				&& (parent.getNodeType().equals("EnumDeclaration") || (parent.getNodeType().equals("Body")))) {
			removeNodeInbetween(node);
		}
		if (nodeType.equals("FieldDeclaration") // enum edge case
				&& parent.getNodeType().equals("EnumDeclaration")) {
			node.setNodeType("EnumConstantDeclaration");
		}
		if (nodeType.equals("FieldDeclaration") && parent.getNodeType().equals("Argument")) {
			List<Attribute> attrList = new ArrayList<Attribute>(node.getAttributes());
			for (Attribute attribute : attrList) {
				parent.addAttribute(attribute);
			}
			removeNode(node);
		}
		// VatiableDeclaration for literals
		if (nodeType.equals("Initialization") && !node.getAttributes().isEmpty()) {
			Attribute attr = node.getAttributeForKey("Name");
			if (attr == null) {
				return;
			}
			String value = attr.getAttributeValues().get(0).getValue().toString();
			if (value != null && value.equals("=")) {
				parent.getParent().setNodeType("VariableDeclarationExpr");
				parent.setNodeType("VariableDeclarator");

				Node literal = node.getChildren().get(0).getChildren().get(0);
				List<Attribute> attributes = literal.getAttributes();
				for (Attribute att : attributes) {
					if (att.getAttributeKey().equals("Name")) {
						att.setAttributeKey("Value");
					}
				}
				removeNodeInbetween(node.getChildren().get(0));
				removeNodeInbetween(node);
			}
		}
	}

	private void removeNode(Node node) {
		int index = node.getParent().getChildren().indexOf(node);
		node.getParent().getChildren().remove(index);
	}

	private void removeNodeInbetween(Node node) {
		List<Node> children = new ArrayList<Node>(node.getChildren());
		for (Node child : children) {
			node.getParent().addChild(child);
			child.setParent(node.getParent());
		}
		int index = node.getParent().getChildren().indexOf(node);
		node.getParent().getChildren().remove(index);
	}

}
