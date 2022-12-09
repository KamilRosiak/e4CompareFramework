package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class AdjustSpecifier extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (nodeType.equals(Const.SPECIFIER) && !parent.getAttributes().isEmpty()) {
			Attribute type = null;
			for (Attribute a: parent.getAttributes() ) {
				if (a.getAttributeKey().equals(Const.TYPE_BIG)) {
					type = a;
				}
			}
			if (type != null) {
				type.getAttributeValues().get(0).setValue(node.getValueAt(0) + " " +
						type.getAttributeValues().get(0).getValue().toString());
				node.cutWithoutChildren();
			}
		}

	}

}
