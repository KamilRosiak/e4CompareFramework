package de.tu_bs.cs.isf.e4cf.core.io.reader.python_adjust;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust.Const;
import de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust.TreeAdjuster;

public class PAdjustNodes extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (Const.BANED_NODES.contains(nodeType)) {
			node.cutWithoutChildren();
		}

	}

}
