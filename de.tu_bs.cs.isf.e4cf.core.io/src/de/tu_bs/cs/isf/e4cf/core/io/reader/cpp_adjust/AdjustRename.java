package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * This class renames the rest of the NodeTypes to their camel-case equivalent
 * 
 * @author David Bumm
 *
 */
public class AdjustRename extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		String newName = nodeType.substring(0, 1).toUpperCase() + nodeType.substring(1);
		node.setNodeType(newName);

	}

}
