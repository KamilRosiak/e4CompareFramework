package de.tu_bs.cs.isf.e4cf.compare.comparator.templates;

import java.io.Serializable;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * This class serves as template for node comparator
 * 
 * @author Kamil Rosiak
 *
 */
public abstract class AbstractNodeComparator implements Comparator<Node>, Serializable {
	private static final long serialVersionUID = 7212002340935774949L;
	private String supportedNodeType;
	private String name = "";
	private Float weight = 0f;

	public AbstractNodeComparator(String supportedType, String name) {
		this.supportedNodeType = supportedType;
		this.name = name;
	}

	@Override
	public String getSupportedNodeType() {
		return this.supportedNodeType;
	}

	@Override
	public Float getWeight() {
		return this.weight;
	}

	@Override
	public void setWeight(Float weight) {
		this.weight = weight;
	}

	@Override
	public boolean isComparable(Node firstNode, Node secondNode) {
		return ((firstNode.getNodeType().equals(secondNode.getNodeType()) || supportedNodeType.equals(WILDCARD))
				&& firstNode.getNodeType().equals(supportedNodeType)) ? true : false;
	}

	public String getName() {
		return name;
	}

	public void setName(String comparatorName) {
		this.name = comparatorName;
	}

}
