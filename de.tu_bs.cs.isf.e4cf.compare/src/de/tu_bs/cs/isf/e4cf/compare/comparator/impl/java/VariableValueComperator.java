package de.tu_bs.cs.isf.e4cf.compare.comparator.impl.java;

import de.tu_bs.cs.isf.e4cf.compare.comparator.impl.node.NodeResultElement;
import de.tu_bs.cs.isf.e4cf.compare.comparator.templates.AbstractNodeComparator;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class VariableValueComperator extends AbstractNodeComparator {
	float keyValueRatio = 0.4f;
	public static final String NAME = "Variable Value Comparator";

	public VariableValueComperator() {
		super(NodeType.VARIABLE_DECLARATION.toString(), NAME);
	}

	@Override
	public NodeResultElement compare(Node firstNode, Node secondNode) {
		return new NodeResultElement(this, firstNode.getChildren().get(0).getAttributeForKey("Value")
				.compare(secondNode.getChildren().get(0).getAttributeForKey("Value")));
	}
}
