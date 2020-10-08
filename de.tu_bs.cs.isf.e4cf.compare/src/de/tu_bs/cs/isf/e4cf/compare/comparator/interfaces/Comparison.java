package de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces;

import java.util.List;

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
	
	public Comparison<Type> getParantComparison();
	
	public List<Comparison<Type>> getChildComparisons();
	
	public void addChildComparison(Comparison<Type> child);
	
	public default float updateSimilarity() {
	    float childSimilarity = 0f;
	    float attributeSimilarity = 0f;
	    
	    for(Comparison<Type> child : getChildComparisons()) {
		childSimilarity += child.updateSimilarity();
		child.updateSimilarity();
	    }
	    
	    /**
	    TODo: IMPL
	     */
	    return childSimilarity;
	}
}
