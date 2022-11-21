package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class AdjustComment extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (nodeType.equals("LineComment")) {
			String value = node.getValueAt(0);
			// remove "//" and potential space at the beginning of the Comment
			if (value == null) {
				node.cut();
				return;
			}
			String[] arr = value.split("//");
			for (int i = 0; i < arr.length; i++) {
				value = arr[i];
			}
			parent.addAttribute(new AttributeImpl("Comment", new StringValueImpl(value)));
			node.cut();
		}

	}

}
