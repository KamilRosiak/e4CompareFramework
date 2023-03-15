package de.tu_bs.cs.isf.e4cf.core.io.reader.cpp_adjust;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * This class is a sub class of TreeAdjuster.
 * It adjust everything that has to do with Name nodes.
 * It is initially called by AdjustAll.
 * 
 * @author David Bumm
 *
 */
public class AdjustName extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (nodeType.equals(Const.NAME_BIG)) {
			if (keep(node)) {
				node.setNodeType(Const.NAME_EXPR);
			} else {
				node.cutWithoutChildren();
			}
		}
		
		if (nodeType.equals(Const.TYPE_SMALL)) {
			node.cutWithoutChildren();
		}
	
		if (nodeType.equals(Const.MODIFIER) && node.getAttributes().isEmpty()) {
			node.cutWithoutChildren();
		}

	}
	
	private boolean keep(Node node) {
		String parentType = node.getParent().getNodeType();
		if (node.getAttributes().isEmpty()) {
			return false;
		}
		for (int i = 0; i < node.getParent().getAttributes().size(); i++) {
			if (node.getParent().getValueAt(i).equals(node.getValueAt(0))) {
				return false;
			}
		}
		return parentType.equals(Const.EXPR) || parentType.equals(Const.ARGUMENT_BIG) || parentType.equals(Const.ARGUMENT_SMALL)
				|| parentType.equals(Const.VARIABLE_DECL);
	}

}
