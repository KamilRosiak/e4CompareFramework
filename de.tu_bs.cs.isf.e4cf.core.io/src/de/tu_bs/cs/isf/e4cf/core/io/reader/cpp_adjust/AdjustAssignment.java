package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class AdjustAssignment extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		//Assignment
		if (nodeType.equals(Const.EXPR_STMT) && !node.getChildren().isEmpty()) {
			Node exprNode = node.getChildren().get(0);
			if (!exprNode.getChildren().isEmpty()) {
				String operator = Const.EMPTY;
				for (Node child : exprNode.getChildren()) {
					if (child.getNodeType().equals(Const.OPERATOR_SMALL)) {
						operator = child.getValueAt(0);
					}
				}
				if (operator.equals(Const.EQ)) {
					Node assignment = new NodeImpl(Const.ASSIGN);
					assignment.setParent(node.getParent());
					node.getParent().addChild(assignment,node.cut());
					String target = exprNode.getChildren().get(0).getValueAt(0);
					assignment.addAttribute(new AttributeImpl(Const.TARGET, new StringValueImpl(target)));
					assignment.addAttribute(new AttributeImpl(Const.OPERATOR_BIG, new StringValueImpl(Const.ASSIGN)));
					exprNode.getChildren().get(exprNode.getChildren().size() -1).updateParent(assignment);
					
				}
			}
		}

	}

}
