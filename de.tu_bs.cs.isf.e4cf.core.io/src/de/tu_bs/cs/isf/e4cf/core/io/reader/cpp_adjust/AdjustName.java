package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class AdjustName extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (nodeType.equals(Const.NAME_BIG)) {
			if (parent.getNodeType().equals(Const.EXPR)) {
				node.setNodeType(Const.NAME_EXPR);
			} else {
				node.cutWithoutChildren();
			}
		}

	}

}
