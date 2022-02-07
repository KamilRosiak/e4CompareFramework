package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.compare;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;
import de.tu_bs.cs.isf.e4cf.compare.comparator.templates.AbstractResultElement;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class TaxonomyResultElement extends AbstractResultElement<Node> {

	public TaxonomyResultElement(Comparator<Node> usedComparator, float similarity) {
		super(usedComparator, similarity);

	}

}
