package de.tu_bs.cs.isf.e4cf.core.cpp_adjust;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class AdjustSwitchCase extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if ((nodeType.equals("Body")) && parent.getNodeType().equals("SwitchStmt")) {
			removeNodeInbetween(node);
			// rearrange Nodes in the Body
			List<Node> children = node.getChildren().get(0).getChildren();
			Node switchEntry = null;
			for (int i = 0; i < children.size(); i++) {
				Node child = children.get(i);
				if (child.getNodeType().equals("SwitchEntry")) {
					switchEntry = child;
				} else if (switchEntry == null) {
					continue;
				} else {
					switchEntry.addChild(child);
					removeNodeFromParent(child);
					child.setParent(switchEntry);
					i--; // decrement i because we remove a child
				}
			}

		}
		if (nodeType.equals("Condition") && parent.getNodeType().equals("SwitchStmt")) {
			Node exprNode = getChild(node, "expr");
			String selector = getFirstValue(exprNode);
			parent.addAttribute(new AttributeImpl("Selector", new StringValueImpl(selector)));
			removeNodeFromParent(node);
		}
		if (nodeType.equals("SwitchEntry")) {
			if (node.getChildren().isEmpty()) {
				setDefaultCase(node);
				return;
			}
			Node exprNode = node.getChildren().get(0);
			Node stringNode = exprNode.getChildren().get(0);
			if (!exprNode.getNodeType().equals("expr") || !stringNode.getNodeType().equals("literal")  ) {
				setDefaultCase(node);
				return;
			}
			String condition = getFirstValue(stringNode);
			node.addAttribute(new AttributeImpl("Condtion", new StringValueImpl(condition)));
			node.addAttribute(new AttributeImpl("Type", new StringValueImpl("STATEMENT_GROUP")));
			removeNodeFromParent(exprNode);
		}

	}
	
	private void setDefaultCase(Node node) {
		node.addAttribute(new AttributeImpl("Default", new StringValueImpl("true")));
		node.addAttribute(new AttributeImpl("Type", new StringValueImpl("STATEMENT_GROUP")));
	}

}
