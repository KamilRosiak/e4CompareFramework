package de.tu_bs.cs.isf.e4cf.compare.interfaces;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AbstractMatcher implements Matcher {
    public static final float MANDATORY_VALUE = 1.0f;
    @Override
    
    
    public List<Comparison> getMatching(List<Comparison> comparisons) {
	sortComparisonBySimilarity(comparisons);
	
	Iterator<Comparison> comparisonIterator = comparisons.iterator();
	Set<Object> matchedElements = new HashSet<Object>();
	List<Comparison> removedComparisons =  new ArrayList<Comparison>();
	
	while (comparisonIterator.hasNext()) {
	   Comparison nextComparison = comparisonIterator.next();
	    //Checks if the elements are not marked an if the similarity of the comparison is higher than the optional threshold.
	    if(!matchedElements.contains(nextComparison.getLeftElement()) && !matchedElements.contains(nextComparison.getRightElement()) 
		    && nextComparison.getSimilarityValue() == MANDATORY_VALUE) {
		
		matchedElements.add(nextComparison.getLeftElement());
		matchedElements.add(nextComparison.getRightElement());
	    } else {
		removedComparisons.add(nextComparison);
		comparisonIterator.remove();
	    }  
	}
	
	/**
	 * Post Processing all optional elements that are only contained in one of the trees.
	 */
	for(Comparison comparison : removedComparisons) {
	    if(comparison.getLeftElement() != null && comparison.getRightElement() != null) {
		    if(!matchedElements.contains(comparison.getLeftElement())) {
			matchedElements.add(comparison.getLeftElement());
			comparison.setRightElement(null);
			comparison.setSimilarity(0);
		    } else if (!matchedElements.contains(comparison.getRightElement())) {
			matchedElements.add(comparison.getRightElement());
			comparison.setLeftElement(null);
			comparison.setSimilarity(0);
		    }
		    comparisons.add(comparison);
	    } 
	}
	return comparisons;
    }
    
	/**
	 * Sorting Elements by Similarity
	 */
    public void sortComparisonBySimilarity(List<Comparison> comparisons) {
	comparisons.sort((first, second)->{
	    if(first.getSimilarityValue() == second.getSimilarityValue()) {
		return 0;
	    }
	    return first.getSimilarityValue() < second.getSimilarityValue() ? 1 : -1;
	});
    }

}
