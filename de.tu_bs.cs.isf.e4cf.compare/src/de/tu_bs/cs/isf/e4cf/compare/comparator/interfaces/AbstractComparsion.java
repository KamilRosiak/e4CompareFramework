package de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces;

import java.util.List;

public abstract class AbstractComparsion<K> implements Comparison<K> {
    private K leftElement;
    private K rightElement;
    private float similarity;
    private Comparison<K> parent;
    private List<Comparison<K>> childComparisons; 
    
    
    public AbstractComparsion(K leftElement, K rightElement) {
	setLeftElement(leftElement);
	setRightElement(rightElement);
    }
    
    @Override
    public K getLeftElement() {
	return leftElement;
    }

    @Override
    public K getRightElement() {
	return rightElement;
    }

    @Override
    public float getSimilarityValue() {
	return similarity;
    }
    
    @Override
    public void setLeftElement(K leftElement) {
	this.leftElement = leftElement;
    }
    
    @Override
    public void setRightElement(K rightElement) {
	this.rightElement = rightElement;
    }
    
    @Override
    public void setSimilarity(float similarity) {
	this.similarity = similarity;
    }
    
    @Override
    public Comparison<K> getParantComparison() {
	return parent;
    }

    public void setParent(Comparison<K> parent) {
	this.parent = parent;
    }
    
    @Override
    public List<Comparison<K>> getChildComparisons() {
	return childComparisons;
    }
    
    public void setChildComparisons(List<Comparison<K>> childComparisons) {
	this.childComparisons = childComparisons;
    }
    
    @Override
    public void addChildComparison(Comparison<K> child) {
	childComparisons.add(child);
    }

}
