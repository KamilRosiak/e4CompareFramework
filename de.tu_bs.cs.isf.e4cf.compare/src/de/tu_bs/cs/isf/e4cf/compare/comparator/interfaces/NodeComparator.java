package de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
/**
 * Interface for the comparison of Nodes to define a specific comparison for types of nodes.
 * @author Kamil Rosiak
 *
 */
public interface NodeComparator {
	/**
	 * This method returns the node type that is supported by this comparator.
	 */
	public String getSupportedNodeType();
	
	/**
	 * This method checks if both nodes are comparable with this comparator.
	 */
	public boolean isComparable(Node firstNode, Node secondNode);
	
	/**
	 * This method compares two nodes of the same type.
	 */
	public Comparison compare(Node firstNode, Node secondNode);
	
}
