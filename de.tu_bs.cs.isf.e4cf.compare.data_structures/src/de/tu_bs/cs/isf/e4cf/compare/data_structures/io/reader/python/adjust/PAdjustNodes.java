package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.python.adjust;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.cpp.adjust.Const;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.cpp.adjust.TreeAdjuster;

public class PAdjustNodes extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (Const.BANED_NODES.contains(nodeType)) {
			node.cutWithoutChildren();
		}

	}

}
