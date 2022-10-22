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
		Node parent = node.getParent();
		String nodeType = node.getNodeType();

		if (nodeType.equals("Name") || nodeType.equals("type")) {
			int index = parent.getChildren().indexOf(node);
			parent.getChildren().remove(index);
			return;
		}
		if (nodeType.equals("Body") && (parent.getNodeType().equals("EnumDeclaration")
				|| (parent.getNodeType().equals("Body")))) {
			List<Node> children = new ArrayList<Node>(node.getChildren());
			for (Node child : children) {
				parent.addChild(child);
				child.setParent(parent);
			}
			int index = parent.getChildren().indexOf(node);
			parent.getChildren().remove(index);
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
			int index = parent.getChildren().indexOf(node);
			parent.getChildren().remove(index);
		}

		if (node.getNumberOfChildren() > 0) {
			List<Node> children = new ArrayList<Node>(node.getChildren());
			for (Node child : children) {
				recursiveAdjust(child);
			}
		}
	}

}
