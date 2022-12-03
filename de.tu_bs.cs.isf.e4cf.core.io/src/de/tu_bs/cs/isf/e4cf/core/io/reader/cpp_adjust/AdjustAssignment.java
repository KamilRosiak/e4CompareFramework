package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import java.util.List;
import java.util.ArrayList;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class AdjustAssignment extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		//Assignment
		if (nodeType.equals(Const.EXPR_STMT) && !node.getChildren().isEmpty()) {
			Node exprNode = node.getChildren().get(0);
			if (!exprNode.getChildren().isEmpty()) {
				String operator = Const.EMPTY;
				boolean found = false;
				boolean beforeOP = true;
				List<Node> targetList = new ArrayList<>();
				List<Node> valueList = new ArrayList<>();
				
				for (Node child : exprNode.getChildren()) {
					if (child.getNodeType().equals(Const.OPERATOR_SMALL) && child.getValueAt(0).equals(Const.EQ)) {
						operator = child.getValueAt(0);
						found = true;
						beforeOP = false;
					} 
					else if (beforeOP) {
						targetList.add(child);
					} else {
						valueList.add(child);
					}
				}
				if (found) {
					Node assignment = new NodeImpl(Const.ASSIGNMENT);
					assignment.setParent(node.getParent());
					node.getParent().addChild(assignment,node.cut());
					String target = Const.EMPTY;
					for (Node n: targetList) {
						if (!n.getAttributes().isEmpty()) {
							target += n.getValueAt(0);
						}
					}
					assignment.addAttribute(new AttributeImpl(Const.TARGET, new StringValueImpl(combineToString(targetList))));
					assignment.addAttribute(new AttributeImpl(Const.OPERATOR_BIG, new StringValueImpl(Const.ASSIGN)));
					
					if (valueList.size() == 1 && !valueList.get(0).getNodeType().equals(Const.NAME_BIG)) {
						valueList.get(0).updateParent(assignment);
					} else {
						Node valueNode = new NodeImpl(Const.NAME_EXPR, assignment);
						valueNode.addAttribute(new AttributeImpl(Const.VALUE, new StringValueImpl(combineToString(valueList))));
					}
				}
			}
		}

	}

	
	private String combineToString(List<Node> nodes) {
		String value = Const.EMPTY;
		for (Node node: nodes) {
			if (!node.getAttributes().isEmpty()) {
				if (node.getNodeType().equals(Const.ARR_ACCESS_EXPR)) {
					String text = node.getChildren().get(0).getValueAt(0);
					text += "[" + node.getValueAt(0) + "]";
					value += text;
				} else {
					value += node.getValueAt(0);
				}
			}
		}
		return value;
	}
}
