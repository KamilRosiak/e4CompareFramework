package de.tu_bs.cs.isf.e4cf.compare.comparator;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;

public class AttrComparison {
	private  Attribute firstAttr;
	private Attribute secondAttr;
	private float similarity;
	
	public AttrComparison(Attribute first_attr, Attribute second_attr) {
		setFirstAttr(first_attr);
		setSecondAttr(second_attr);
	}

	public AttrComparison(Attribute first_attr, Attribute second_attr, float similarity) {
		this(first_attr, second_attr);
		setSimilarity(similarity);
	}
	
	public Attribute getFirstAttr() {
		return firstAttr;
	}

	public void setFirstAttr(Attribute firstAttr) {
		this.firstAttr = firstAttr;
	}

	public Attribute getSecondAttr() {
		return secondAttr;
	}

	public void setSecondAttr(Attribute secondAttr) {
		this.secondAttr = secondAttr;
	}

	public float getSimilarity() {
		return similarity;
	}

	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}
	
}
