package de.tu_bs.cs.isf.e4cf.compare.comparator.templates;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * This class serves as template for node comparator
 * @author Kamil Rosiak
 *
 */
public abstract class AbstractNodeComparator implements Comparator<Node> {
	private String supportedNodeType;


	public AbstractNodeComparator(String supportedType) {
		this.supportedNodeType = supportedType;
	}

	@Override
	public String getSupportedNodeType() {
		return this.supportedNodeType;
	}

	@Override
	public boolean isComparable(Node firstNode, Node secondNode) {
		return ((firstNode.getNodeType().equals(secondNode.getNodeType()) || supportedNodeType.equals(WILDCARD))
				&& firstNode.getNodeType().equals(supportedNodeType)) ? true : false;
	}



}
