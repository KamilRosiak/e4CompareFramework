package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import java.util.ArrayList;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class AdjustOperator extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (nodeType.equals("operator") && !node.getAttributes().isEmpty()) {
			String value = node.getValueAt(0);
			if (value.length() == 1) {
				String operator = this.getOperatorString(value);
				node.setAttributes(new ArrayList<Attribute>());
				node.addAttribute(new AttributeImpl("Operator", new StringValueImpl(operator)));
				node.setNodeType("BinaryExpr");
			}
		}
	}

}