package de.tu_bs.cs.isf.e4cf.compare.interfaces;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * data structure for the storage of the comparison between two nodes.
 */
public interface Comparison {
	
	/**
	 * This method returns the right node of this comparison.
	 */
	public Node getLeftNode();
	
	/**
	 * This method returns the left node of this comparison.
	 */
	public Node getRightNode();
	
	/**
	 * This method returns the calculated similarity between both nodes.
	 */
	public float getSimilarityValue();
	
	public float getSimilariotyForChildNodes();
	
	public float getSimilarityForAttributes();
}
