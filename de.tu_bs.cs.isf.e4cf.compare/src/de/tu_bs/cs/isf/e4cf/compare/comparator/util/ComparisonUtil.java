package de.tu_bs.cs.isf.e4cf.compare.comparator.util;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparison;
/**
 * A Utility class for the manipulation of comparisons
 * @author Kamil Rosiak
 *
 */
public class ComparisonUtil {
	
	/**
	 * This method sorts a list of Comparison by their similarity value.
	 */
    public static void sortComparisonBySimilarity(List<Comparison> comparisons) {
		comparisons.sort((first, second)->{
		    if(first.getSimilarityValue() == second.getSimilarityValue()) {
			return 0;
		    }
		    return first.getSimilarityValue() < second.getSimilarityValue() ? 1 : -1;
		});
    }
}
