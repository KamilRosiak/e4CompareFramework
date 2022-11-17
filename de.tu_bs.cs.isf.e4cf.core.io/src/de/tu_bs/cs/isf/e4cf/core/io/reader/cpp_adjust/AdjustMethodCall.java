package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class AdjustMethodCall extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (nodeType.equals("expr_stmt")) {
			node.setNodeType("MethodCallExpr");
			if (node.getChildren().size() > 0) {
				Node exprNode = node.getChildren().get(0);
				if (exprNode.getChildren().size() > 0) {
					Node callNode = exprNode.getChildren().get(0);
					if (callNode.getAttributes().size() > 0) {
						node.addAttribute(callNode.getAttributes().get(0));
						callNode.cutWithoutChildren();
					}
				}
				exprNode.cutWithoutChildren();
			}
		}
		
		if  (nodeType.equals("argument")) {
			node.setNodeType("Argument");
			if (node.getChildren().size() == 0) {
				return;
			}
			Node nodeWithValue = node.getChildren().get(0);
			if (nodeWithValue.getChildren().size() > 0) {
				Node old = nodeWithValue;
				nodeWithValue = nodeWithValue.getChildren().get(0);
				old.cut();
			}
			if (nodeWithValue.getAttributes().size() > 0) {
				node.addAttribute(nodeWithValue.getAttributes().get(0));
			}
			nodeWithValue.cut();
		}

	}

}
