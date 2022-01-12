package de.tu_bs.cs.isf.e4cf.core.compare.algorithm;

import java.util.HashSet;
import java.util.Set;

public class JaccardIndex {

	public static float computeJaccardIndex(String string1, String string2, int nGramSize) {

		Set<String> set1 = getNGrams(string1, nGramSize);
		Set<String> set2 = getNGrams(string2, nGramSize);

		Set<String> intersection = new HashSet<String>(set1);
		intersection.retainAll(set2);
		
		return ((float) intersection.size()) / (set1.size() + set2.size() - intersection.size());
	}

	public static float computeJaccardIndex(String string1, String string2) {
		return computeJaccardIndex(string1, string2, 3);
	}

	private static Set<String> getNGrams(String target, int n) {
		Set<String> nGrams = new HashSet<String>();
		for (int i = 0; i < target.length() - n + 1; i++)
			nGrams.add(target.substring(i, i + n));
		return nGrams;
	}

}
