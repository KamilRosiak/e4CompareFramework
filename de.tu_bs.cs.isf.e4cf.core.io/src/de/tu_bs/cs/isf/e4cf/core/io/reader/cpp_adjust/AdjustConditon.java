package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class AdjustConditon extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (nodeType.equals("expr") && parent.getNodeType().equals("Condition")) {
			node.setNodeType("BinaryExpr");
			List<Node> children = node.getChildren();
			Node child = null;
			for (int i = 0; i < children.size(); i++) {
				if (children.get(i).getNodeType().equals("operator")) {
					child = children.get(i);
				}
			}
			if (child == null) {
				node.cutWithoutChildren(); // no expression e.g while(true)
				return;
			}
			for (Attribute a: node.getAttributes()) {
				Node newNode = new NodeImpl("NameExpr", node);
				newNode.addAttribute(a);
			}
			
			node.setAttributes(new ArrayList<>());
			String operator = getOperatorString(child.getValueAt(0));
			node.addAttribute(new AttributeImpl("Operator", new StringValueImpl(operator)));
			
			child.cutWithoutChildren();
		}

	}

}
