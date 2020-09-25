package de.tu_bs.cs.isf.e4cf.compare.data_structures.compare.elements;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * Wrapper to store the similarity between two nodes. It also can be interpreted as and a weighted edge between two nodes.
 * @author Kamil Rosiak
 *
 */
public class Comparison {
	private Node firstElement;
	private Node secondElement;
	private float similarity; 
	
	public Comparison(Node firstNode, Node secNode, float similarity) {
		setFirstElement(firstNode);
		setSecondElement(secNode);
		setSimilarity(similarity);
	}

	public Node getFirstElement() {
		return firstElement;
	}

	public void setFirstElement(Node firstElement) {
		this.firstElement = firstElement;
	}

	public Node getSecondElement() {
		return secondElement;
	}

	public void setSecondElement(Node secondElement) {
		this.secondElement = secondElement;
	}

	public float getSimilarity() {
		return similarity;
	}

	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}
}
