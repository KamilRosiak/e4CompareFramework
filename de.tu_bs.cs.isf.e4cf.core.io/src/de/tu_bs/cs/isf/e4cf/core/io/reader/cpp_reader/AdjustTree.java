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
			removeNodeFromParent(node);
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
			removeNodeFromParent(node);
		}
		// VariableDeclaration for literals
		if (nodeType.equals("Initialization") && !node.getAttributes().isEmpty()) {
			//for loop edge case
			if (parent.getParent().getNodeType().equals("Initialization")) {
				Node newNode = new NodeImpl();
				newNode.setParent(parent.getParent());
				newNode.addChild(parent);
				removeNodeFromParent(parent);
				parent.getParent().addChild(newNode);
				parent.setParent(newNode);

			}
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
		//for-loops
		if (nodeType.equals("expr") && parent.getNodeType().equals("Update")) {
			node.setNodeType("UnaryExpr");
			List<Node> children = node.getChildren();
			Node child = null;
			int i = 0;
			for (int j = 0; j < children.size(); j++) {
				if (children.get(j).getNodeType().equals("operator")) {
					child = children.get(j);
					i = j;
					break;
				}
			}
			if (child == null) {
				return;
			}
			child.setNodeType("NameExpr");
			node.addAttribute(child.getAttributes().get(0));
			child.getAttributes().remove(0);
			child.addAttribute(node.getAttributes().get(0));
			node.getAttributes().remove(0);
			changeOperator(i, node);
		}
		if (nodeType.equals("expr") && parent.getNodeType().equals("Condition")) {
			node.setNodeType("BinaryExpr");
			List<Node> children = node.getChildren();
			Node child = null;
			for (int i = 0; i < children.size(); i++) {
				if (children.get(i).getNodeType().equals("operator")) {
					child = children.get(i);
				}
			}
			if (child == null) {
				return;
			}
			child.setNodeType("NameExpr");
			List<Attribute> attrList = new ArrayList<>();
			attrList.add(node.getAttributes().get(0));
			node.setAttributes(new ArrayList<>());
			String operator = getOperatorString(child.getAttributes().get(0).getAttributeValues().get(0).getValue().toString());
			node.addAttribute(new AttributeImpl("Operator", new StringValueImpl(operator)));
			child.setAttributes(attrList);
		}
	}
	
	private String getOperatorString(String operator) {
		switch (operator) {
		case "<":
			return "LESS";
		case ">":
			return "GREATER";
		case ">=":
			return "GREATER_EQUALS";
		case "<=":
			return "LESS_EQUALS";
		default:
			return "";
		}
	}
	
	private void changeOperator(int i, Node node) {
		String operator = node.getAttributes().get(0).getAttributeValues().get(0).getValue().toString();
		String value = "";
		if (i == 1) {
			if (operator.equals("++")) {
				value = "POSTFIX_INCREMENT";
			} else if (operator.equals("--")) {
				value = "POSTFIX_DECREMENT";
			}
		} else if (i == 0) {
			//TODO this case is never reached
			if (operator.equals("++")) {
				value = "PREFIX_INCREMENT";
			} else if (operator.equals("--")) {
				value = "PREFIX_DECREMENT";
			}
		}
		node.getAttributes().get(0).getAttributeValues().set(0, new StringValueImpl(value));
		node.getAttributes().get(0).setAttributeKey("Operator");
	}

	private void removeNodeFromParent(Node node) {
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
