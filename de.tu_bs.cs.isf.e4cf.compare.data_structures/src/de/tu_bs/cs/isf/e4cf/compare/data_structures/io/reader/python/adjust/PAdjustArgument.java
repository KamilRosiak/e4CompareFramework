package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.python.adjust;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.cpp.adjust.Const;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.cpp.adjust.TreeAdjuster;

public class PAdjustArgument extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (nodeType.equals("Args") && !node.getChildren().isEmpty()) {
			if (node.getChildren().get(0).getNodeType().equals("Arguments")) {
				node.cutWithoutChildren();
			}
		} else if (nodeType.equals("Args") || nodeType.equals("Arguments")) {
			node.setNodeType(Const.ARGUMENT_BIG);
		}

	}

}
