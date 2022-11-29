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
		if (nodeType.equals("index") && parent.getParent().getParent().getNodeType().equals("Initialization")
				&& !node.getChildren().isEmpty()) {
			if (!parent.getAttributes().isEmpty()) {
				try {
					parent.getParent().getParent().cut();
				} catch (ArrayIndexOutOfBoundsException e) {
					return; // node already has been cut
				}
				
				String name = parent.getValueAt(0);
				Node exprNode = node.getChildren().get(0);
				String value = "";
				for (Node child : exprNode.getChildren()) {
					value += child.getAttributeForKey("Name").getAttributeValues().get(0).getValue().toString() + " ";
				}
				value = value.trim();
				Node realParent = parent.getParent().getParent().getParent();
				addArrayAccessExpr(value, name, node, realParent);
				
				
				realParent.setNodeType("VariableDeclarator");
				realParent.getParent().setNodeType("VariableDeclarationExpr");
			}

		} else if (nodeType.equals("index")) {
			// Array access
			if (!node.getChildren().isEmpty() && !node.getChildren().get(0).getAttributes().isEmpty()
					&& !parent.getAttributes().isEmpty()) {
				if (parent.getParent().getNodeType().equals("expr")) { // remove element for condition edge case
					for (Attribute a : parent.getParent().getAttributes()) {
						if (a.getAttributeValues().get(0).getValue().toString().equals(parent.getValueAt(0))) {
							parent.getParent().getAttributes().remove(a);
							break;
						}
					}
				}

				String value = node.getChildren().get(0).getValueAt(0);
				String name = parent.getValueAt(0);

				Node realParent = parent.getParent().getParent().getParent();
				Node removable = realParent.getChildren().get(0);
				if (!parent.getParent().getAttributes().isEmpty() && removable.getNodeType().equals("expr_stmt")) {
					Node assignment = new NodeImpl("Assignment", realParent);
					String target = parent.getParent().getValueAt(0);
					assignment.addAttribute(new AttributeImpl("Target", new StringValueImpl(target)));
					assignment.addAttribute(new AttributeImpl("Operator", new StringValueImpl("ASSIGN")));
					removable.cut();
					addArrayAccessExpr(value, name, node, assignment);
					
				} else {
					addArrayAccessExpr(value, name, node, parent.getParent());
					node.getParent().cut();
				}

			}

			// change Value
			Attribute oldAttribute = null;
			for (Attribute a : parent.getParent().getAttributes()) {
				if (a.getAttributeKey().equals("Type")) {
					oldAttribute = a;
				}
			}
			if (oldAttribute != null) {
				String oldValue = oldAttribute.getAttributeValues().get(0).getValue().toString();
				oldAttribute.getAttributeValues().get(0).setValue(oldValue + "[]");
			}
			node.cut();
		}

		// cutting unnecessary expr nodes
		if (nodeType.equals("Body") && parent.getParent().getParent().getNodeType().equals("FieldDeclaration")) {
			node.setNodeType("ArrayInitializerExpr");
			int length = node.getChildren().size();
			for (int i = 0; i < length; i++) {
				Node n = node.getChildren().get(0);
				if (!n.getChildren().isEmpty()) {
					n.getChildren().get(0).getAttributeForKey("Name").setAttributeKey("Value");
				}
				n.cutWithoutChildren();
			}
		}

	}
	
	private void addArrayAccessExpr(String value, String name, Node node, Node accessParent) {
		Node access = new NodeImpl("ArrayAccessExpr", accessParent);
		access.addAttribute(new AttributeImpl("Value", new StringValueImpl(value)));
		Node lastAccess = access;

		for (Node index : node.getParent().getChildren()) {
			// for each index node we need to add an "ArrayAccessExpr". this is the case for
			// an access like a[i][j]
			
			if (index.getNodeType().equals("index") && node != index) {
				value = index.getChildren().get(0).getValueAt(0);
				Node newAccess = new NodeImpl("ArrayAccessExpr", lastAccess);
				newAccess.addAttribute(new AttributeImpl("Value", new StringValueImpl(value)));
				lastAccess = newAccess;
			}
		}
		
		Node nameExpr = new NodeImpl("NameExpr", lastAccess);
		nameExpr.addAttribute(new AttributeImpl("Name", new StringValueImpl(name)));
	}

}
