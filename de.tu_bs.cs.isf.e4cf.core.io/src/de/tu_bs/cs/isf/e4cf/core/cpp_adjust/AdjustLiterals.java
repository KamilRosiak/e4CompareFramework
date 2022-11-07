package de.tu_bs.cs.isf.e4cf.core.cpp_adjust;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class AdjustLiterals extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {

		if (nodeType.equals("Initialization") && !node.getAttributes().isEmpty()) {
			// for loop edge case
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

		if (nodeType.equals("literal")) {
			String value = getFirstValue(node);
			if (value.matches("\\d*")) {
				node.addAttribute(new AttributeImpl("Type", new StringValueImpl("int")));
				node.setNodeType("IntegerLiteralExpr");
			} else if (value.equals("true") || value.equals("false")) {
				node.addAttribute(new AttributeImpl("Type", new StringValueImpl("boolean")));
				node.setNodeType("BooleanLiteralExpr");
			} else if (value.matches("\\d*\\.\\d*")) {
				node.addAttribute(new AttributeImpl("Type", new StringValueImpl("double")));
				node.setNodeType("DoubleLiteralExpr");
			} else {
				node.addAttribute(new AttributeImpl("Type", new StringValueImpl("String")));
				node.setNodeType("StringLiteralExpr");
			}
			node.getAttributes().get(0).setAttributeKey("Value");
		}

	}

}
