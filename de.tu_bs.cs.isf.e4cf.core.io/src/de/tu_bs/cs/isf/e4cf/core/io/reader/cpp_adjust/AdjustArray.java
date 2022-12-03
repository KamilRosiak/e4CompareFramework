package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class AdjustArray extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (nodeType.equals(Const.INDEX) && parent.getParent().getParent().getNodeType().equals(Const.INITIALIZATION)
				&& !node.getChildren().isEmpty()) {
			if (!parent.getAttributes().isEmpty()) {
				int i = -1;
				try {
					i = parent.getParent().getParent().cut();
				} catch (ArrayIndexOutOfBoundsException e) {
					return; // node already has been cut
				}
				Node realParent = parent.getParent().getParent().getParent();
				addArrayAccessExpr(node, realParent, i);
				realParent.setNodeType(Const.VARIABLE_DECL);
				realParent.getParent().setNodeType(Const.VARIABLE_DECL_EXPR);
			}

		} else if (nodeType.equals(Const.INDEX)) {
			// Array access
			if (allInitialized(node)) {
				if (parent.getParent().getNodeType().equals(Const.EXPR)) { // remove element for condition edge case
					for (Attribute a : parent.getParent().getAttributes()) {
						if (a.getAttributeValues().get(0).getValue().toString().equals(parent.getValueAt(0))) {
							parent.getParent().getAttributes().remove(a);
							break;
						}
					}
				}
				Node realParent = parent.getParent().getParent().getParent();
				Node removable = realParent.getChildren().get(0);
				if (!parent.getParent().getAttributes().isEmpty() && removable.getNodeType().equals(Const.EXPR_STMT)) {
					Node assignment = new NodeImpl(Const.ASSIGNMENT, realParent);
					String target = parent.getParent().getValueAt(0);
					assignment.addAttribute(new AttributeImpl(Const.TARGET, new StringValueImpl(target)));
					assignment.addAttribute(new AttributeImpl(Const.OPERATOR_BIG, new StringValueImpl(Const.ASSIGN)));
					addArrayAccessExpr(node, assignment, removable.cut());
				} else {
					addArrayAccessExpr(node, parent.getParent(), node.getParent().cut());
				}
			}

			// change Value
			Attribute oldAttribute = null;
			for (Attribute a : parent.getParent().getAttributes()) {
				if (a.getAttributeKey().equals(Const.TYPE_BIG)) {
					oldAttribute = a;
				}
			}
			if (oldAttribute != null) {
				String oldValue = oldAttribute.getAttributeValues().get(0).getValue().toString();
				oldAttribute.getAttributeValues().get(0).setValue(oldValue + Const.BRACKET_SQUARED);
			}
			node.cut();
		}

		// cutting unnecessary expr nodes
		if (nodeType.equals(Const.BODY) && parent.getParent().getParent().getNodeType().equals(Const.FIELD_DECL )) {
			node.setNodeType(Const.ARR_INIT_EXPR);
			int length = node.getChildren().size();
			for (int i = 0; i < length; i++) {
				Node n = node.getChildren().get(0);
				if (!n.getChildren().isEmpty()) {
					n.getChildren().get(0).getAttributeForKey(Const.NAME_BIG).setAttributeKey(Const.VALUE);
				}
				n.cutWithoutChildren();
			}
		}

	}
	
	private void addArrayAccessExpr(Node node, Node accessParent, int position) {
		Node exprNode = node.getChildren().get(0);
		String value = Const.EMPTY;
		for (Node child : exprNode.getChildren()) {
			value += child.getAttributeForKey(Const.NAME_BIG).getAttributeValues().get(0).getValue().toString() + Const.SPACE;
		}
		value = value.trim();
		String name = node.getParent().getValueAt(0);
		
		Node access = new NodeImpl(Const.ARR_ACCESS_EXPR);
		access.setParent(accessParent);
		accessParent.addChild(access, position);
		access.addAttribute(new AttributeImpl(Const.VALUE, new StringValueImpl(value)));
		Node lastAccess = access;

		for (Node index : node.getParent().getChildren()) {
			// for each index node we need to add an "ArrayAccessExpr". this is the case for
			// an access like a[i][j]
			
			if (index.getNodeType().equals(Const.INDEX) && node != index) {
				value = index.getChildren().get(0).getValueAt(0);
				Node newAccess = new NodeImpl(Const.ARR_ACCESS_EXPR, lastAccess);
				newAccess.addAttribute(new AttributeImpl(Const.VALUE, new StringValueImpl(value)));
				lastAccess = newAccess;
			}
		}
		
		Node nameExpr = new NodeImpl(Const.NAME_EXPR, lastAccess);
		nameExpr.addAttribute(new AttributeImpl(Const.NAME_BIG, new StringValueImpl(name)));
	}
	
	private boolean allInitialized(Node node) {
		return !node.getChildren().isEmpty() && !node.getChildren().get(0).getAttributes().isEmpty()
				&& !node.getParent().getAttributes().isEmpty();
	}

}
