package de.tu_bs.cs.isf.e4cf.core.io.reader.python_adjust;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust.AdjustRename;
import de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust.TreeAdjuster;

public class PAdjustAll extends TreeAdjuster{

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		TreeAdjuster nodeAdjuster = new PAdjustNodes();
		nodeAdjuster.recursiveAdjust(node);
		
		TreeAdjuster renameAdjuster = new AdjustRename();
		renameAdjuster.recursiveAdjust(node);
		
		RenamerPython.getInstance().renameNode(node);
		
		TreeAdjuster ifAdjuster = new PAdjustIf();
		ifAdjuster.recursiveAdjust(node);
		
	}

}
