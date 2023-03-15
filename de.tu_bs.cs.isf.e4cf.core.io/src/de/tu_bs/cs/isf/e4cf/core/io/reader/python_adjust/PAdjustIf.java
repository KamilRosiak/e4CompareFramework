package de.tu_bs.cs.isf.e4cf.core.io.reader.python_adjust;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust.Const;
import de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust.TreeAdjuster;

public class PAdjustIf extends TreeAdjuster{

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (nodeType.equals(Const.IF_STMT_BIG)) {
			for (Node child: node.getChildren()) {
				if (child.getNodeType().equals(Const.BODY)) {
					child.setNodeType(Const.THEN_BIG);
				} else if (child.getNodeType().equals("Test")) {
					child.setNodeType(Const.CONDITION_BIG);
				}
			}
		}
		
	}

}
