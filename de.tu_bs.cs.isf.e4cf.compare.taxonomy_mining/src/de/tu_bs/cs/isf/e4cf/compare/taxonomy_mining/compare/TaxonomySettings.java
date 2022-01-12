package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.compare;

import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.util.StringComparison;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.util.TaxonomyConstruction;

public class TaxonomySettings {

	public static StringComparison comparisonAlgorithm = StringComparison.JACCARD;
	
	public static TaxonomyConstruction taxonomyConstruction = TaxonomyConstruction.SIMILARITY;

}
