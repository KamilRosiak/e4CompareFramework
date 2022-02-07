package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.compare;

import de.tu_bs.cs.isf.e4cf.compare.comparator.templates.AbstractNodeComparator;
import de.tu_bs.cs.isf.e4cf.core.compare.algorithm.JaccardIndex;
import de.tu_bs.cs.isf.e4cf.core.compare.algorithm.LevenstheinDistance;

public abstract class BaseComparator extends AbstractNodeComparator {

	public BaseComparator(String supportedType) {
		super(supportedType);
	}

	protected float getStringSimilarity(String string1, String string2) {

		int distance;
		int maxLength = string1.length() > string2.length() ? string1.length() : string2.length();

		switch (TaxonomySettings.comparisonAlgorithm) {

		case DAMERAULEVENSHTEIN:
			distance = LevenstheinDistance.computeDamerauLevenshteinDistance(string1, string2);
			return 1 - ((float) distance / maxLength);
		case LEVENSHTEIN:
			distance = LevenstheinDistance.computeLevenshteinDistance(string1, string2);
			return 1 - ((float) distance / maxLength);
		case SIMPLE:
			return string1.equals(string2) ? 1 : 0;
		case JACCARD:
			return JaccardIndex.computeJaccardIndex(string1, string2);

		}
		return 0;

	}

}
