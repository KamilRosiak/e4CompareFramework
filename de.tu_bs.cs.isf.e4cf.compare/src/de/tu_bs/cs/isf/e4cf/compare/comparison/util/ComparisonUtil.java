package de.tu_bs.cs.isf.e4cf.compare.comparison.util;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;

public class ComparisonUtil {
	public static final float MANDATORY_VALUE = 1.0f;
	public static final float OPTIONAL_VALUE = 0.3f;
	
	
	
	/**
	 * Returns the variability class for a given float similarity value 
	 */
	public static VariabilityClass getClassForSimilarity(float similarity) {
		if(similarity == MANDATORY_VALUE) {
			return VariabilityClass.MANDATORY;
		} else if(similarity >= OPTIONAL_VALUE) {
			return VariabilityClass.ALTERNATIVE;
		} else {
			return VariabilityClass.OPTIONAL;
		}
	}
}
