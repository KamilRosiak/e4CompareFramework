package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.model;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

public class MatchSet {

	private Tree variant1;

	private Tree variant2;

	private Node node1;

	private Node node2;

	private float similarity;

	public MatchSet(Tree variant1, Tree variant2, Node node1, Node node2, float similarity) {
		super();
		this.variant1 = variant1;
		this.variant2 = variant2;
		this.node1 = node1;
		this.node2 = node2;
		this.similarity = similarity;
	}

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

	public Node getNode1() {
		return node1;
	}

	public void setNode1(Node node1) {
		this.node1 = node1;
	}

	public Node getNode2() {
		return node2;
	}

	public void setNode2(Node node2) {
		this.node2 = node2;
	}

	public float getSimilarity() {
		return similarity;
	}

	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}

}
