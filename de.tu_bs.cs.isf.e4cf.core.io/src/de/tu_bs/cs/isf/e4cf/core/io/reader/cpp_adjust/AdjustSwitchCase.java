package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * 
 * This class is a sub class of TreeAdjuster.
 * It adjust everything that has to do with Switch-Cases.
 * It is initially called by AdjustAll.
 * 
 * @author David Bumm
 *
 */
public class AdjustSwitchCase extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if ((nodeType.equals("Body")) && parent.getNodeType().equals("SwitchStmt")) {
			node.cutWithoutChildren();
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
					child.updateParent(switchEntry);
					i--; // decrement i because we remove a child
				}
			}

		}
		if (nodeType.equals("Condition") && parent.getNodeType().equals("SwitchStmt")) {
			Node exprNode = getChild(node, "expr");
			String selector = exprNode.getValueAt(0);
			parent.addAttribute(new AttributeImpl("Selector", new StringValueImpl(selector)));
			node.cut();
		}
		if (nodeType.equals("SwitchEntry")) {
			if (node.getChildren().isEmpty()) {
				setDefaultCase(node);
				return;
			}
			if  (isLastChild(node)) {
				setDefaultCase(node);
				return;
			}
			Node exprNode = node.getChildren().get(0);
			Node conditiongNode = exprNode.getChildren().get(0);
			if (conditiongNode.getNodeType().equals("Name")) {
				String condition = node.getValueAt(0);
				node.setAttributes(new ArrayList<Attribute>());
				node.addAttribute(new AttributeImpl("Condtion", new StringValueImpl(condition)));
				node.addAttribute(new AttributeImpl("Type", new StringValueImpl("STATEMENT_GROUP")));
				conditiongNode.cut();
			}
			String condition = conditiongNode.getValueAt(0);
			node.addAttribute(new AttributeImpl("Condtion", new StringValueImpl(condition)));
			node.addAttribute(new AttributeImpl("Type", new StringValueImpl("STATEMENT_GROUP")));
			exprNode.cut();
		}

	}
	
	private void setDefaultCase(Node node) {
		node.addAttribute(new AttributeImpl("Default", new StringValueImpl("true")));
		node.addAttribute(new AttributeImpl("Type", new StringValueImpl("STATEMENT_GROUP")));
	}
	
	private boolean isLastChild(Node node) {
		List<Node> children = node.getParent().getChildren();
		return children.get(children.size() -1) == node;
	}

}
