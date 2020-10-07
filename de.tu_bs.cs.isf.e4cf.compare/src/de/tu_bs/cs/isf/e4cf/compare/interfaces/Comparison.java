package de.tu_bs.cs.isf.e4cf.compare.interfaces;

/**
 * data structure for the storage of the comparison between two nodes.
 */
public interface Comparison<Type> {	
	/**
	 * This method returns the right node of this comparison.
	 */
	public Type getLeftElement();
	
	/**
	 * This method sets the left element of type Type
	 */
	public void setLeftElement(Type element);
	
	/**
	 * This method returns the left node of this comparison.
	 */
	public Type getRightElement();
	
	/**
	 * This method sets the right element of type Type
	 */
	public void setRightElement(Type element);
	
	/**
	 * This method returns the calculated similarity between both nodes.
	 */
	public float getSimilarityValue();
	
	/**
	 * This method sets the calculated similarity between both nodes.
	 */
	public void setSimilarity(float similarity);
}
