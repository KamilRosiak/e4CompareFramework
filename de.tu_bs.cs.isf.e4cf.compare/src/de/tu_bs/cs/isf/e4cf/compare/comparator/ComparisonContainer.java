package de.tu_bs.cs.isf.e4cf.compare.comparator;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class ComparisonContainer {
    private float similarity;
    private Node firstNode;
    private Node secondNode;
    private ComparisonContainer parent;
    private List<ComparisonContainer> childComparisons;
    private List<ComparisonContainer> attrComparisons;
    
    
    public List<ComparisonContainer> getAttrComparisons() {
	return attrComparisons;
    }
    public void setAttrComparisons(List<ComparisonContainer> attrComparisons) {
	this.attrComparisons = attrComparisons;
    }
    
    public List<ComparisonContainer> getChildComparisons() {
	return childComparisons;
    }
    
    public void setChildComparisons(List<ComparisonContainer> childComparisons) {
	this.childComparisons = childComparisons;
    }
    public Node getSecondNode() {
	return secondNode;
    }
    public void setSecondNode(Node secondNode) {
	this.secondNode = secondNode;
    }
    public Node getFirstNode() {
	return firstNode;
    }
    public void setFirstNode(Node firstNode) {
	this.firstNode = firstNode;
    }
    public float getSimilarity() {
	return similarity;
    }
    public void setSimilarity(float similarity) {
	this.similarity = similarity;
    }
    public ComparisonContainer getParent() {
	return parent;
    }
    public void setParent(ComparisonContainer parent) {
	this.parent = parent;
    }
    
}
