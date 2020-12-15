package de.tu_bs.cs.isf.e4cf.compare.matcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.comparator.util.ComparisonUtil;
import de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces.AbstractMatcher;

public class SortingMatcher extends AbstractMatcher {
    public static final float MANDATORY_VALUE = 1.0f;
    public static final String MATCHER_NAME = "SortingMatcher";
    public static final String MATCHER_DESCRIPTION = "This matching approach sorts all comparisons by similarity and takes those pairs with the highest similarity.";

    public SortingMatcher() {
	super(MATCHER_NAME, MATCHER_DESCRIPTION);
    }

    @Override
    public List<Comparison> getMatching(List<Comparison> comparisons) {
	ComparisonUtil.sortComparisonBySimilarity(comparisons);

	Iterator<Comparison> comparisonIterator = comparisons.iterator();
	Set<Object> matchedElements = new HashSet<Object>();
	List<Comparison> removedComparisons = new ArrayList<Comparison>();

	while (comparisonIterator.hasNext()) {
	    Comparison nextComparison = comparisonIterator.next();
	    // Checks if the elements are not marked an if the similarity of the comparison
	    // is higher than the optional threshold.
	    if (!matchedElements.contains(nextComparison.getLeftElement())
		    && !matchedElements.contains(nextComparison.getRightElement())
		    && nextComparison.getSimilarityValue() == MANDATORY_VALUE) {

		matchedElements.add(nextComparison.getLeftElement());
		matchedElements.add(nextComparison.getRightElement());
	    } else {
		removedComparisons.add(nextComparison);
		comparisonIterator.remove();
	    }
	}

	/**
	 * Post Processing all optional elements that are only contained in one of the
	 * trees.
	 */
	for (Comparison comparison : removedComparisons) {
	    if (comparison.getLeftElement() != null && comparison.getRightElement() != null) {
		if (!matchedElements.contains(comparison.getLeftElement())) {
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
}
