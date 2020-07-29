package de.tu_bs.cs.isf.e4cf.core.exchange.fide.util;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * A normalizer to handle the naming of the features
 * 
 * @author Dibo Gonda (y0085182)
 * @date 12.06.19
 */
public class Normalizer {
	
	private static final String BASE_NAME = "NewFeature";
	private static final String NOT_ALLOWED_CHARS = "[^a-zA-Z0-9_]";
	private static final String BEGINS_WITH_WORD_CHAR = "^[a-zA-Z].*";
	private static final String HAS_INDEX = ".*_[0-9]*$";
	private static final String INDEX = "[0-9]*$";
	
	/**
	 * Normalizes a string name (VAT -> FeatureIDE)
	 * 
	 * @param name a VAT name
	 * @return a suitable FeatureIDE name
	 */
	public static String getNormalizedName(String name) {
		String normalizedName = name.replaceAll(" ", "");
		normalizedName = normalizedName.replaceAll(NOT_ALLOWED_CHARS, "");

		if (Pattern.matches(BEGINS_WITH_WORD_CHAR, normalizedName) && !normalizedName.startsWith("_") && !normalizedName.equals("")) {
			return normalizedName;
		} else {
			return BASE_NAME + normalizedName;
		}
	}
	
	/**
	 * Builds an unique string name
	 * 
	 * @param actualName the normalized actualName
	 * @param names a set of already existing names
	 * @return a new unique name
	 */
	public static String getUniqueName(String actualName, Set<String> names) {
		String newName;
		int counter = 1;
		if (Pattern.matches(HAS_INDEX, actualName)) {
			actualName = actualName.replaceAll(INDEX, "");
		} else {
			actualName = actualName + "_";
		}
		
		do {
			newName = actualName + Integer.toString(counter);
			counter++;
		} while (names.contains(newName));
		
		return newName;
	}

}
