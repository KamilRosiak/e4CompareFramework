package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.model;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

public class TaxonomyEdge {

	private Tree variant1;

	private Tree variant2;

	private float weight;

	public Tree getVariant1() {
		return variant1;
	}

	public void setVariant1(Tree variant1) {
		this.variant1 = variant1;
	}

	public Tree getVariant2() {
		return variant2;
	}

	public void setVariant2(Tree variant2) {
		this.variant2 = variant2;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public TaxonomyEdge(Tree variant1, Tree variant2, float weight) {
		super();
		this.variant1 = variant1;
		this.variant2 = variant2;
		this.weight = weight;
	}

}
