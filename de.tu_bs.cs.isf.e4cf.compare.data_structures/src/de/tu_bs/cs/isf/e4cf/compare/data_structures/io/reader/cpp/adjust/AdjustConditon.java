package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.cpp.adjust;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * 
 * This class is a sub class of TreeAdjuster.
 * It adjust everything that has to do with Conditions.
 * Conditions occur for example in if cases.
 * It is initially called by AdjustAll.
 * 
 * @author David Bumm
 *
 */
public class AdjustConditon extends TreeAdjuster {

	@Override
	protected void adjust(Node node, Node parent, String nodeType) {
		if (nodeType.equals(Const.EXPR) && parent.getNodeType().equals(Const.CONDITION_BIG)) {
			node.cutWithoutChildren();
		}
	}
}
