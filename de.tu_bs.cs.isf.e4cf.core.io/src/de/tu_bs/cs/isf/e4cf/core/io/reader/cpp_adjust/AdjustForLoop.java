package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import java.util.List;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * 
 * This class is a sub class of TreeAdjuster.
 * It adjust everything that has to do with For-loops.
 * It is initially called by AdjustAll.
 * 
 * @author David Bumm
 *
 */
public class AdjustForLoop extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (nodeType.equals(Const.EXPR) && parent.getNodeType().equals(Const.UPDATE)) {
			node.setNodeType(Const.UNARY_EXPR);
			List<Node> children = node.getChildren();
			Node child = null;
			int i = 0;
			for (int j = 0; j < children.size(); j++) {
				if (children.get(j).getNodeType().equals(Const.OPERATOR_SMALL)) {
					child = children.get(j);
					i = j;
					break;
				}
			}
			if (child == null) {
				return;
			}
			child.setNodeType(Const.NAME_EXPR);
			node.addAttribute(child.getAttributes().get(0));
			child.getAttributes().remove(0);
			child.addAttribute(node.getAttributes().get(0));
			node.getAttributes().remove(0);
			changeOperator(i, node);
		}
		if (nodeType.equals(Const.EXPR) && parent.getNodeType().equals(Const.EXPR_STMT)) {
			List<Node> children = node.getChildren();
			if (children.size() > 2) {
				return;
			}
			for (int i = 0; i < children.size(); i++) {
				Node child = children.get(i);
				if (child.getNodeType().equals(Const.OPERATOR_SMALL)) {
					changeOperator(i, child);
					child.setNodeType(Const.UNARY_EXPR);
					node.setNodeType(Const.NAME_EXPR);
					child.updateParent(parent.getParent());
					child.addChildWithParent(node);
					parent.cut();
				}
			}
		}

	}

}
