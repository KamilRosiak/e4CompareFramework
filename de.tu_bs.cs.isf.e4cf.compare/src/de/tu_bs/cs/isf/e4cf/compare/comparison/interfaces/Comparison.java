package de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces;

import java.io.Serializable;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.ResultElement;

/**
 * data structure for the storage of the comparison between two nodes.
 */
public interface Comparison<Type> extends Serializable {
	
	
	public Type mergeArtifacts();
	
	public Type getLeftArtifact();
	public Type getRightArtifact();
	
	public void setRightArtifact(Type artifact);
	public void setLeftArtifact(Type artifact);
	
	public List<Comparison<Type>> getChildComparisons();
	public void addChildComparison(Comparison<Type> comparison);
	
	public void addResultElement(ResultElement<Type> result);
	public List<ResultElement<Type>> getResultElements();
	
	
	public float getSimilarity();
	public void setSimilarity(float similarity);
	
	
	public default void updateSimilarity() {
		float similarity = 0f;
		float childSimilarity = 0f;
		float resultSimilarity = 0f;
		
		//call update similarity recursively on all child comparison
		for(Comparison<Type> childComparison : getChildComparisons()) {
			childComparison.updateSimilarity();
			childSimilarity += childComparison.getSimilarity();
		}
		//calculate the average value
		if(!getChildComparisons().isEmpty()) {
			childSimilarity = childSimilarity / getChildComparisons().size();
		}
		
		for(ResultElement<Type> result : getResultElements()) {
			resultSimilarity += result.getSimilarity();
		}
		if(!getResultElements().isEmpty()) {
			resultSimilarity = resultSimilarity / getResultElements().size();
		} 
		
		//no children nod attributes so they are equal on their type
		if(getChildComparisons().isEmpty() && getResultElements().isEmpty()) {
			similarity = 1f;
		}
		
		//only results available so the similarity is based on them
		if(getChildComparisons().isEmpty() && !getResultElements().isEmpty()) {
			similarity = resultSimilarity;
		}
		
		//no results available so we use the similarity of our children
		if(!getChildComparisons().isEmpty() && getResultElements().isEmpty()) {
			similarity = childSimilarity;
		}
		
		//no results available so we use the similarity of our children
		if(!getChildComparisons().isEmpty() && !getResultElements().isEmpty()) {
			//TODO: weight which allows to scale this value 
			similarity = (childSimilarity + resultSimilarity) / 2;
		}
		setSimilarity(similarity);
	}
}
