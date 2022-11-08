package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public abstract class TreeAdjuster {

	public void recursiveAdjust(Node node) {
		if (node == null) {
			return;
		}

		adjust(node, node.getParent(), node.getNodeType());

		if (node.getNumberOfChildren() > 0) {
			List<Node> children = new ArrayList<Node>(node.getChildren());
			for (Node child : children) {
				recursiveAdjust(child);
			}
		}
	}

	protected abstract void adjust(Node node, Node parent, String nodeType);

	protected Node getChild(Node parent, String name) {
		List<Node> children = parent.getChildren();
		for (int j = 0; j < children.size(); j++) {
			if (children.get(j).getNodeType().equals(name)) {
				return children.get(j);
			}
		}
		return null;
	}

	protected String getFirstValue(Node node) {
		return node.getAttributes().get(0).getAttributeValues().get(0).getValue().toString();
	}

	protected String getOperatorString(String operator) {
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

	protected void changeOperator(int i, Node node) {
		String operator = node.getAttributes().get(0).getAttributeValues().get(0).getValue().toString();
		String value = "";
		if (i == 1) {
			if (operator.equals("++")) {
				value = "POSTFIX_INCREMENT";
			} else if (operator.equals("--")) {
				value = "POSTFIX_DECREMENT";
			}
		} else if (i == 0) {
			if (operator.equals("++")) {
				value = "PREFIX_INCREMENT";
			} else if (operator.equals("--")) {
				value = "PREFIX_DECREMENT";
			}
		}
		node.getAttributes().get(0).getAttributeValues().set(0, new StringValueImpl(value));
		node.getAttributes().get(0).setAttributeKey("Operator");
	}

	protected void removeNodeInbetween(Node node) {
		List<Node> children = new ArrayList<Node>(node.getChildren());
		for (Node child : children) {
			node.getParent().addChild(child);
			child.setParent(node.getParent());
		}
		int index = node.getParent().getChildren().indexOf(node);
		node.getParent().getChildren().remove(index);
	}
}
