/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.interfaces;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparison.TaxonomyNodeComparison;

/**
 * @author developer-olan
 *
 */
public interface ITaxonomyMatcher {
	/**
	 * Matches all elements within the given NodeComparison recursively
	 */
	public <K> NodeComparison calculateMatching(TaxonomyNodeComparison root);
	
    /**
     * This method returns a list of comparisons. This list contains the best pairs of nodes between the compared nodes.
     * @param comparisons
     * @return
     */
    public <K> void calculateMatching(List<Comparison<K>> comparisons);
    
    /**
	 * This method sorts a list of comparisons by their similarity values descending
	 */
	public <K> void sortBySimilarityDesc(List<Comparison<K>> comparisons);
	
    /**
     * Returns the name of the matching approach
     */
    public String getMatcherName();
    
    /**
     * Returns the description of the corresponding matching approach.
     */
    public String getMatcherDescription();
}

