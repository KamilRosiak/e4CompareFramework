package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * 
 * This class is a sub class of TreeAdjuster.
 * It adjust everything that has to do with Conditions.
 * Conditions occur for example in if cases.
 * It is initially called by AdjustAll.
 * 
 * @author David Bumm
 *
 */
public class AdjustConditon extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (nodeType.equals(Const.EXPR) && parent.getNodeType().equals(Const.CONDITION_BIG)) {
			node.setNodeType(Const.BINARY_EXPR);
			List<Node> children = node.getChildren();
			Node child = null;
			for (int i = 0; i < children.size(); i++) {
				if (children.get(i).getNodeType().equals(Const.BINARY_EXPR)
						|| children.get(i).getNodeType().equals(Const.OPERATOR_SMALL)) {
					child = children.get(i);
				}
			}
			if (child == null) {
				node.cutWithoutChildren(); // no expression e.g while(true)
				return;
			}
			for (Attribute a : node.getAttributes()) {
				Node newNode = new NodeImpl(Const.NAME_EXPR, node);
				newNode.addAttribute(a);
			}
			node.setAttributes(new ArrayList<>());
			String operator = getOperatorString(child.getValueAt(0));
			node.addAttribute(new AttributeImpl(Const.OPERATOR_BIG, new StringValueImpl(operator)));
			child.cutWithoutChildren();
		}
	}
}
