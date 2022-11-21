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
		if (nodeType.equals("expr") && parent.getNodeType().equals("Update")) {
			node.setNodeType("UnaryExpr");
			List<Node> children = node.getChildren();
			Node child = null;
			int i = 0;
			for (int j = 0; j < children.size(); j++) {
				if (children.get(j).getNodeType().equals("operator")) {
					child = children.get(j);
					i = j;
					break;
				}
			}
			if (child == null) {
				return;
			}
			child.setNodeType("NameExpr");
			node.addAttribute(child.getAttributes().get(0));
			child.getAttributes().remove(0);
			child.addAttribute(node.getAttributes().get(0));
			node.getAttributes().remove(0);
			changeOperator(i, node);
		}
		if (nodeType.equals("expr") && parent.getNodeType().equals("expr_stmt")) {
			List<Node> children = node.getChildren();
			if (children.size() > 2) {
				return;
			}
			for (int i = 0; i < children.size(); i++) {
				Node child = children.get(i);
				if (child.getNodeType().equals("operator")) {
					changeOperator(i, child);
					child.setNodeType("UnaryExpr");
					node.setNodeType("NameExpr");
					child.updateParent(parent.getParent());
					child.addChildWithParent(node);
					parent.cut();
				}
			}
		}

	}

}
