package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * This class iterates over a Tree to a given root.
 * For every Child the tree will be adjust whether a condition is met or not.
 * Adjustments are renames of NodeTypes, deleting the Node or a small restructure to specific parts of the tree.
 * Goal of the adjustments is to make this tree more similar to a tree made of java source code.
 * Every subclass adjust the Tree according to specific parts of code.
 * 
 * @author David Bumm
 *
 */
public abstract class TreeAdjuster {

	/**
	 * This method iterates over a tree to a given root.
	 * For each root the abstract adjust-method is called to adjust this root.
	 * 
	 * @param node is the rootNode of the tree
	 */
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

	/**
	 * This method adjusts a Node to make this tree more similar to a tree made of java source code.
	 * 
	 * @param node is the current node
	 * @param parent is the parent of the current node
	 * @param nodeType is the NodeType of the current node
	 */
	protected abstract void adjust(Node node, Node parent, String nodeType);

	/**
	 * This method returns the is the parent of the current node
	 * Null is returned if no Child with the given nodeType exists.
	 * 
	 * @param parent 
	 * @param name the nodeType of the Child
	 * @return first Child found with the nodeType or null.
	 */
	protected Node getChild(Node parent, String name) {
		List<Node> children = parent.getChildren();
		for (int j = 0; j < children.size(); j++) {
			if (children.get(j).getNodeType().equals(name)) {
				return children.get(j);
			}
		}
		return null;
	}

	/**
	 * This method translates a given operator to its String representation.
	 * If the operator couldn't be matched an empty String is returned.
	 * 
	 * @param operator to be translated
	 * @return String representation of the operator
	 */
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
		case "/":
			return "DIVIDE";
		case "+":
			return "PLUS";
		case "-":
			return "MINUS";
		case "*":
			return "MULTIPLY";
		default:
			return operator;
		}
	}

	/**
	 * This method translates a given unary operator to their String representation and
	 * adds sets the attribute value.
	 * 
	 * 
	 * @param index which decides if its a pre- or postfix-expression
	 * @param node which gets the new attribute values
	 */
	protected void changeOperator(int index, Node node) {
		String operator = node.getAttributes().get(0).getAttributeValues().get(0).getValue().toString();
		String value = Const.EMPTY;
		if (index == 1) {
			if (operator.equals("++")) {
				value = "POSTFIX_INCREMENT";
			} else if (operator.equals("--")) {
				value = "POSTFIX_DECREMENT";
			}
		} else if (index == 0) {
			if (operator.equals("++")) {
				value = "PREFIX_INCREMENT";
			} else if (operator.equals("--")) {
				value = "PREFIX_DECREMENT";
			}
		}
		node.getAttributes().get(0).getAttributeValues().set(0, new StringValueImpl(value));
		node.getAttributes().get(0).setAttributeKey(Const.OPERATOR_BIG);
	}
}
