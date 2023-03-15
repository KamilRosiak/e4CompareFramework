package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.cpp.adjust;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * This class is a sub class of TreeAdjuster.
 * It renames the rest of the NodeTypes to their camel-case equivalent.
 * It is initially called by AdjustAll.
 * 
 * @author David Bumm
 *
 */
public class AdjustRename extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		String arr[] = nodeType.split(Const.UNDERSCORE);
		String newName = Const.EMPTY;
		for (int i = 0; i < arr.length; i++) {
			newName += arr[i].substring(0, 1).toUpperCase() + arr[i].substring(1);
		}
		node.setNodeType(newName);

	}

}
