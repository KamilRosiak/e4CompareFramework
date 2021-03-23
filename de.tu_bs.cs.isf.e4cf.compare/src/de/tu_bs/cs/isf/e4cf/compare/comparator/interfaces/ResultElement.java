package de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces;

public interface ResultElement<Type> {
	
	
	public Comparator<Type> getUsedComparator();

	public float getSimilarity();
	public void setSimilarity(float similarity);
}
