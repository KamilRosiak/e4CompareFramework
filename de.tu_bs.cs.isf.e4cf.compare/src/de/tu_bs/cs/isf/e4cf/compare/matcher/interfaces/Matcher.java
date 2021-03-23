package de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;

public interface Matcher {
	
	/**
	 * Matches all elements within the given NodeComparison recursively
	 */
	public <K> NodeComparison calculateMatching(NodeComparison root);
	
    /**
     * This method returns a list of comparisons. This list contains the best pairs of nodes between the compared nodes.
     * @param comparisons
     * @return
     */
    public <K> void calculateMatching(List<Comparison<K>> comparisons);
    
    /**
     * Returns the name of the matching approach
     */
    public String getMatcherName();
    
    /**
     * Returns the description of the corresponding matching approach.
     */
    public String getMatcherDescription();
}
