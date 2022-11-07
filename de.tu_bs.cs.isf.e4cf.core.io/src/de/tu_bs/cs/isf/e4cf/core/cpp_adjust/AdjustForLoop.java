package de.tu_bs.cs.isf.e4cf.core.cpp_adjust;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class AdjustForLoop extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
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
				removeNodeInbetween(node); // no expression e.g while(true)
				return;
			}
			child.setNodeType("NameExpr");
			List<Attribute> attrList = new ArrayList<>();
			if (node.getAttributes().isEmpty()) {
				return; // edge case
			}
			attrList.add(node.getAttributes().get(0));
			node.setAttributes(new ArrayList<>());
			String operator = getOperatorString(
					child.getAttributes().get(0).getAttributeValues().get(0).getValue().toString());
			node.addAttribute(new AttributeImpl("Operator", new StringValueImpl(operator)));
			child.setAttributes(attrList);
		}
		if (nodeType.equals("LineComment")) {
			String value = getFirstValue(node);
			// remove "//" and potential space at the beginning of the Comment
			String[] arr = value.split("//");
			for (int i = 0; i < arr.length; i++) {
				value = arr[i];
			}
			parent.addAttribute(new AttributeImpl("Comment", new StringValueImpl(value)));
			removeNodeFromParent(node);
		}
		if (nodeType.equals("expr") && parent.getNodeType().equals("expr_stmt")) {
			List<Node> children = node.getChildren();
			if (children.size() > 2) {
				return;
			}
			for (int i = 0; i < children.size(); i++) {
				Node child = children.get(i);
				if (child.getNodeType().equals("operator")) {
					changeOperator(i, child);
					child.setNodeType("UnaryExpr");
					node.setNodeType("NameExpr");
					removeNodeFromParent(child);
					child.addChild(node);
					node.setParent(child);
					child.setParent(parent.getParent());
					parent.getParent().addChild(child);

					removeNodeFromParent(parent);
				}
			}
		}

	}

}
