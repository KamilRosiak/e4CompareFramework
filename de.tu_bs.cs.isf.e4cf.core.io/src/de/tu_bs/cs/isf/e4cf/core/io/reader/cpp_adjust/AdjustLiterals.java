package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * 
 * This class is a sub class of TreeAdjuster. It adjust everything that has to
 * do with 'literal' Nodes (e.g variable declaration). It is initially called by
 * AdjustAll.
 * 
 * @author David Bumm
 *
 */
public class AdjustLiterals extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {

		if (nodeType.equals(Const.INITIALIZATION) && !node.getAttributes().isEmpty()) {
			// for loop edge case
			if (parent.getParent().getNodeType().equals(Const.INITIALIZATION)) {
				Node newNode = new NodeImpl();
				newNode.updateParent(parent.getParent());
				parent.updateParent(newNode);

			}
			Attribute attr = node.getAttributeForKey(Const.NAME_BIG);
			if (attr == null) {
				return;
			}
			String value = attr.getAttributeValues().get(0).getValue().toString();
			if (value != null && value.equals(Const.EQ)) {
				parent.getParent().setNodeType(Const.VARIABLE_DECL_EXPR);
				parent.setNodeType(Const.VARIABLE_DECL);

				Node literal = node.getChildren().get(0).getChildren().get(0);
				List<Attribute> attributes = literal.getAttributes();
				for (Attribute att : attributes) {
					if (att.getAttributeKey().equals(Const.NAME_BIG)) {
						att.setAttributeKey(Const.VALUE);
					}
				}
				node.getChildren().get(0).cutWithoutChildren();
				node.cutWithoutChildren();
			}
		}
		if (nodeType.equals(Const.LITERAL)) {

		}

		if (nodeType.equals(Const.LITERAL)) {
			boolean containsType = false;
			for (Attribute attribute : node.getAttributes()) {
				if (attribute.getAttributeKey().equals(Const.TYPE_BIG)) {
					containsType = true;
					String value = attribute.getAttributeValues().get(0).getValue().toString();
					renameLiteral(node, value);
				}
			}
			if (containsType) {
				for (Attribute attribute : node.getAttributes()) {
					if (attribute.getAttributeKey().equals(Const.NAME_BIG)) {
						attribute.setAttributeKey(Const.VALUE);
					}
				}
				return;
			}

			// the following is only needed if the literal is only a number/String
			String value = node.getValueAt(0);
			if (value == null) {
				return;
			}
			if (value.matches(Const.REGEX_INT)) {
				node.addAttribute(new AttributeImpl(Const.TYPE_BIG, new StringValueImpl(Const.INT)));
				node.setNodeType(Const.INT_LIT);
			} else if (value.equals(Const.TRUE) || value.equals(Const.FALSE)) {
				node.addAttribute(new AttributeImpl(Const.TYPE_BIG, new StringValueImpl(Const.BOOLEAN)));
				node.setNodeType(Const.BOOLEAN_LIT);
			} else if (value.matches(Const.REGEX_DOUBLE)) {
				node.addAttribute(new AttributeImpl(Const.TYPE_BIG, new StringValueImpl(Const.DOUBLE)));
				node.setNodeType(Const.DOUBLE_LIT);
			} else if (value.matches(Const.REGEX_FLOAT)) {
				node.addAttribute(new AttributeImpl(Const.TYPE_BIG, new StringValueImpl(Const.FLOAT)));
				node.setNodeType(Const.FLOAT_LIT);
			} else {
				node.addAttribute(new AttributeImpl(Const.TYPE_BIG, new StringValueImpl(Const.STRING)));
				node.setNodeType(Const.STRING_LIT);
			}
			node.getAttributes().get(0).setAttributeKey(Const.VALUE);
		}

	}

	private void renameLiteral(Node node, String value) {
		switch (value) {
		case "string":
			node.setNodeType(Const.STRING_LIT);
			return;
		case "int":
			node.setNodeType(Const.INT_LIT);
			return;
		case "float":
			node.setNodeType(Const.FLOAT_LIT);
			return;
		case "bool":
			node.setNodeType(Const.BOOLEAN_LIT);
			return;
		case "double":
			node.setNodeType(Const.DOUBLE_LIT);
			return;
		default:
			return;
		}
	}
}
