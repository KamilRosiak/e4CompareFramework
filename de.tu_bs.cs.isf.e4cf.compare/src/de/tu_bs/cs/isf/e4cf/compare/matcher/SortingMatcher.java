package de.tu_bs.cs.isf.e4cf.compare.matcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.comparator.util.ComparisonUtil;
import de.tu_bs.cs.isf.e4cf.compare.factory.ComparisonFactory;
import de.tu_bs.cs.isf.e4cf.compare.factory.interfaces.IComparisionFactory;
import de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces.AbstractMatcher;

public class SortingMatcher extends AbstractMatcher {
	public static final float MANDATORY_VALUE = 1.0f;
	public static final String MATCHER_NAME = "SortingMatcher";
	public static final String MATCHER_DESCRIPTION = "This matching approach sorts all comparisons by similarity and takes those pairs with the highest similarity.";
	
	public IComparisionFactory compFactory = new ComparisonFactory();
	
	public SortingMatcher() {
		super(MATCHER_NAME, MATCHER_DESCRIPTION);
	}

	@Override
	public <K> List<Comparison<K>> getMatching(List<Comparison<K>> comparisons) {
		ComparisonUtil.sortComparisonBySimilarity(comparisons);

		Iterator<Comparison<K>> comparisonIterator = comparisons.iterator();
		Set<Object> matchedElements = new HashSet<Object>();
		List<Comparison<K>> removedComparisons = new ArrayList<Comparison<K>>();

		while (comparisonIterator.hasNext()) {
			Comparison<K> nextComparison = comparisonIterator.next();
			// Checks if the elements are not marked an if the similarity of the comparison
			// is higher than the optional threshold.
			if (!matchedElements.contains(nextComparison.getLeftElement())
					&& !matchedElements.contains(nextComparison.getRightElement())
					&& nextComparison.getSimilarityValue() >= ComparisonUtil.getOptionalThreshold()) {
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
		for (Comparison<K> comparison : removedComparisons) {
			// in the case both artifacts only have one element both are optional
			if (comparison.getLeftElement() != null && comparison.getRightElement() != null) {
				Comparison<K> comparisonCopy = compFactory.getComparison(comparison);
				comparison.setSimilarity(0);
				//check if elements are contained before adding
				if(!matchedElements.contains(comparison.getLeftElement())) {
					matchedElements.add(comparison.getLeftElement());
					ComparisonUtil.removeAllLeftElements(comparison);
					comparisons.add(comparison);
				} else if(!matchedElements.contains(comparisonCopy.getRightElement())) {
					matchedElements.add(comparison.getRightElement());
					ComparisonUtil.removeAllRightElements(comparisonCopy);
					comparisons.add(comparisonCopy);
				}

			} else if (comparison.getLeftElement() != null) {
				if (!matchedElements.contains(comparison.getLeftElement())) {
					matchedElements.add(comparison.getLeftElement());
					comparison.setRightElement(null);
					comparison.setSimilarity(0);
				}
			} else if (comparison.getRightElement() != null) {
				if (!matchedElements.contains(comparison.getRightElement())) {
					matchedElements.add(comparison.getRightElement());
					comparison.setLeftElement(null);
					comparison.setSimilarity(0);
				}
			}
		}
		return comparisons;
	}
}
