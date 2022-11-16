package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class AdjustArray extends TreeAdjuster{

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (nodeType.equals("index")) {
			String key = "Type";
			Attribute oldAttribute = null;
			for (Attribute a : parent.getAttributes()) {
				if (a.getAttributeKey().equals(key)) {
					oldAttribute = a;
				}
			}
			if (oldAttribute != null) {
				String oldValue = oldAttribute.getAttributeValues().get(0).getValue().toString();
				oldAttribute.getAttributeValues().get(0).setValue(oldValue + "[]");
			}
			node.cut();
		}
		
		if (nodeType.equals("Body") && parent.getNodeType().equals("VariableDeclarator")) {
			node.setNodeType("ArrayInitializerExpr");
			int length = node.getChildren().size();
			for (int i = 0; i < length; i++) {
				node.getChildren().get(0).cutWithoutChildren();
			}
		}
	}

}
