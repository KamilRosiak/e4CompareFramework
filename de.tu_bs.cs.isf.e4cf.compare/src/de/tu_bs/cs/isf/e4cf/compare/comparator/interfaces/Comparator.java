package de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces;

/**
 * Interface for the comparison of Nodes to define a specific comparison for types of nodes.
 * @author Kamil Rosiak
 *
 */
public interface Comparator<Type> {
	public static final String WILDCARD = "WILDCARD";
	
	/**
	 * This method returns the node type that is supported by this comparator.
	 */
	public String getSupportedNodeType();
	
	/**
	 * This method checks if both nodes are comparable with this comparator.
	 */
	public boolean isComparable(Type firstNode, Type secondNode);
	
	/**
	 * This method compares two nodes of the same type and returns the similarity.
	 */
	public ResultElement<Type> compare(Type firstNode, Type secondNode);

}
