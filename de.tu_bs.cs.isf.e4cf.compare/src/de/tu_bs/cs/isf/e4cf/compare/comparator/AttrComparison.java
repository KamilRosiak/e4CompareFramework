package de.tu_bs.cs.isf.e4cf.compare.comparator;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.AbstractComparsion;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;

/**
 * A container for the storage of attribute comparisons
 * @author Kamil Rosiak
 *
 */
public class AttrComparison extends AbstractComparsion<Attribute>{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AttrComparison(Attribute first_attr, Attribute second_attr) {
	    super(first_attr, second_attr);
	}

	public AttrComparison(Attribute first_attr, Attribute second_attr, float similarity) {
		this(first_attr, second_attr);
		setSimilarity(similarity);
	}
	
	/**
	 * Copy Constructor
	 * @param comparison
	 */
	public AttrComparison(AttrComparison comparison) {
		this(comparison.getLeftElement(), comparison.getRightElement(), comparison.getSimilarityValue());
		copyComparison(comparison);
	}

	@Override
	public float updateSimilarity() {
		return getSimilarityValue();
	}
	
}
