/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.metrics;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparison.TaxonomyNodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.interfaces.ITaxonomyMatcher;

/**
 * @author developer-olan
 *
 */
public class TaxonomyAbstractMatcher implements ITaxonomyMatcher{
	
	private String matcherName;
	private String matcherDescription;
	
	public TaxonomyAbstractMatcher(String matcherName, String matcherDescription) {
		setMatcherName(matcherName);
		setMatcherDescription(matcherDescription);
	}

	@Override
	public String getMatcherName() {
		return this.matcherName;
	}

	@Override
	public String getMatcherDescription() {
		return this.matcherDescription;
	}

	public void setMatcherName(String matcherName) {
		this.matcherName = matcherName;
	}

	public void setMatcherDescription(String matcherDescription) {
		this.matcherDescription = matcherDescription;
	}
	
	@Override
	public String toString() {
	    return matcherName;
	}

	@Override
	public <K> NodeComparison calculateMatching(TaxonomyNodeComparison root) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> void calculateMatching(List<Comparison<K>> comparisons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <K> void sortBySimilarityDesc(List<Comparison<K>> comparisons) {
		// TODO Auto-generated method stub
		
	}

}
