package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.python.adjust;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.cpp.adjust.Const;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.cpp.adjust.TreeAdjuster;

public class PAdjustAssignment extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (nodeType.equals(Const.ASSIGNMENT)) {
			Node variableExpr = new NodeImpl(Const.VARIABLE_DECL_EXPR);
			Node variableDecl = new NodeImpl(Const.VARIABLE_DECL, variableExpr);
			String name = "";
			
			for (Node child: node.getChildren()) {
				if (child.getNodeType().equals("Targets") && !child.getChildren().isEmpty()) {
					name = child.getChildren().get(0).getValueAt(0);
				} else if (child.getNodeType().equals("Value") && !child.getChildren().isEmpty()) {
					variableDecl.addChild(child.getChildren().get(0));
				}
			}
			variableDecl.addAttribute(Const.NAME_BIG, name);
			parent.addChild(variableExpr, node.cut());
		}

	}

}
