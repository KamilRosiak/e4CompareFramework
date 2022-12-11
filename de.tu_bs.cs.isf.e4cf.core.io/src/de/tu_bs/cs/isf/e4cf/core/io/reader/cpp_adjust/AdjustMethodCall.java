package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * This class is a sub class of TreeAdjuster.
 * It adjust everything that has to do with method call expressions.
 * It is initially called by AdjustAll.
 * 
 * @author David Bumm
 *
 */
public class AdjustMethodCall extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (nodeType.equals(Const.EXPR_STMT)) {
			node.setNodeType(Const.METHOD_CALL);
			if (node.getChildren().size() > 0) {
				Node exprNode = node.getChildren().get(0);
				if (exprNode.getChildren().size() > 0) {
					Node callNode = exprNode.getChildren().get(0);
					if (callNode.getAttributes().size() > 0) {
						node.addAttribute(callNode.getAttributes().get(0));
						callNode.cutWithoutChildren();
					}
				}
				if (!exprNode.getAttributes().isEmpty()) {
					Node nameExpr = new NodeImpl(Const.NAME_EXPR, node);
					nameExpr.addAttribute(exprNode.getAttributes().get(0));
				}
				exprNode.cutWithoutChildren();
			}
		}
		
		if (nodeType.equals(Const.ARGUMENT_SMALL)) {
			if (node.getChildren().isEmpty()) {
				return;
			}
			Node exprNode = node.getChildren().get(0);
			exprNode.cutWithoutChildren();
		}

	}

}
