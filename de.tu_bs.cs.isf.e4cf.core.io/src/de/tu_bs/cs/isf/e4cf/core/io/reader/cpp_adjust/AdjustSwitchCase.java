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
		if ((nodeType.equals(Const.BODY)) && parent.getNodeType().equals(Const.SWITCH_STMT)) {
			node.cutWithoutChildren();
			// rearrange Nodes in the Body
			List<Node> children = node.getChildren().get(0).getChildren();
			Node switchEntry = null;
			for (int i = 0; i < children.size(); i++) {
				Node child = children.get(i);
				if (child.getNodeType().equals(Const.SWITCH_ENTRY)) {
					switchEntry = child;
				} else if (switchEntry == null) {
					continue;
				} else {
					child.updateParent(switchEntry);
					i--; // decrement i because we remove a child
				}
			}
		}
		if (nodeType.equals(Const.CONDITION_BIG) && parent.getNodeType().equals(Const.SWITCH_STMT)) {
			Node exprNode = getChild(node, Const.EXPR);
			String selector = exprNode.getValueAt(0);
			parent.addAttribute(new AttributeImpl(Const.SELECTOR, new StringValueImpl(selector)));
			node.cut();
		}
		if (nodeType.equals(Const.SWITCH_ENTRY) && !node.getChildren().isEmpty()
				&& !node.getChildren().get(0).getChildren().isEmpty()) {
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
			if (conditiongNode.getNodeType().equals(Const.NAME_BIG)) {
				String condition = node.getValueAt(0);
				node.setAttributes(new ArrayList<Attribute>());
				node.addAttribute(new AttributeImpl(Const.CONDITION_BIG, new StringValueImpl(condition)));
				node.addAttribute(new AttributeImpl(Const.TYPE_BIG, new StringValueImpl(Const.STMT_GRP)));
				conditiongNode.cut();
			}
			String condition = conditiongNode.getValueAt(0);
			node.addAttribute(new AttributeImpl(Const.CONDITION_BIG, new StringValueImpl(condition)));
			node.addAttribute(new AttributeImpl(Const.TYPE_BIG, new StringValueImpl(Const.STMT_GRP)));
			exprNode.cut();
		}

	}
	
	private void setDefaultCase(Node node) {
		node.addAttribute(new AttributeImpl(Const.DEFAULT_BIG, new StringValueImpl(Const.TRUE)));
		node.addAttribute(new AttributeImpl(Const.TYPE_BIG, new StringValueImpl(Const.STMT_GRP)));
	}
	
	private boolean isLastChild(Node node) {
		List<Node> children = node.getParent().getChildren();
		return children.get(children.size() -1) == node;
	}

}
