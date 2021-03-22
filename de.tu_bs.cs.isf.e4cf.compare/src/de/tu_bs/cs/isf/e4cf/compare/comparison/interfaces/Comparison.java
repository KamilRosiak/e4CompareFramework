package de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces;

import java.io.Serializable;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;

/**
 * data structure for the storage of the comparison between two nodes.
 */
public interface Comparison<Type> extends Serializable {
	
	public Type getLeftArtifact();
	public Type getRightArtifact();
	
	public void setRightArtifact(Type artifact);
	public void setLeftArtifact(Type artifact);
	
	
	public List<Comparison<Type>> getChildComparisons();
	public void addChildComparison(Comparison<Type> comparison);
	
	public void addComparator(Comparator<Type> comparator);
	public List<Comparator<Type>> getComparators();
	
	public float getSimilarity();
	public void setSimilarity(float similarity);

}
