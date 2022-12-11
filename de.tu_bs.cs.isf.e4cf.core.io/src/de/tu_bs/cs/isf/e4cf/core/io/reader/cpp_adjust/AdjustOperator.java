package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * This class is a sub class of TreeAdjuster.
 * It adjusts operator Nodes.
 * It is initially called by AdjustAll.
 * 
 * @author David Bumm
 *
 */
public class AdjustOperator extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (nodeType.equals(Const.OPERATOR_SMALL) && !node.getAttributes().isEmpty()) {
			String value = node.getValueAt(0);
			String operator = this.getOperatorString(value);
			node.setAttributes(new ArrayList<Attribute>());
			node.addAttribute(new AttributeImpl(Const.OPERATOR_BIG, new StringValueImpl(operator)));
			node.setNodeType(Const.BINARY_EXPR);
			
			if (parent != null) {
				Node before = null;
				Node after = null;
				boolean isBefore = true;
				for (Node sibling : parent.getChildren()) {
					if (sibling != node) {
						if (isBefore) {
							before = sibling;
						} else {
							after = sibling;
							break;
						}
					} else {
						isBefore = false;
					}
				}
				if (before != null && after != null) {
					if (before.getNodeType().equals(Const.NAME_BIG)) {
						before.setNodeType(Const.NAME_EXPR);
					}
					if (after.getNodeType().equals(Const.NAME_BIG)) {
						after.setNodeType(Const.NAME_EXPR);
					}
					before.updateParent(node);
					after.updateParent(node);
				}
			}
		}
	}
}