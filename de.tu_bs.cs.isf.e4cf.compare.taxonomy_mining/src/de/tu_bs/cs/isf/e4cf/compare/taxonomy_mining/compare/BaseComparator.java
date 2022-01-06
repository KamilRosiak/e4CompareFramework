package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.compare;

import de.tu_bs.cs.isf.e4cf.compare.comparator.templates.AbstractNodeComparator;
import de.tu_bs.cs.isf.e4cf.core.compare.algorithm.LevenstheinDistance;

public abstract class BaseComparator extends AbstractNodeComparator {

	public BaseComparator(String supportedType) {
		super(supportedType);
	}

	protected float getStringSimilarity(String string1, String string2) {

		if (TaxonomySettings.useLevenshteinComparison) {

			int distance = LevenstheinDistance.computeLevenshteinDistance(string1, string2);
			int maxLength = string1.length() > string2.length() ? string1.length() : string2.length();

			return 1 - ((float) distance / maxLength);

		} else {
			return string1.equals(string2) ? 0 : 1;
		}
	}

}
