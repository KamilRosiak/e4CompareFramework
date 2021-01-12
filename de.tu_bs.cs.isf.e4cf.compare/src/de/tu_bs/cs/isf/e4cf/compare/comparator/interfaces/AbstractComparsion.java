package de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public abstract class AbstractComparsion<K> implements Comparison<K> {
    private K leftElement;
    private K rightElement;
    private float similarity;
    private List<Comparator> comparator;
    private List<Comparison<K>> parents;
    private List<Comparison<K>> childComparisons;

    public AbstractComparsion(K leftElement, K rightElement) {
	setLeftElement(leftElement);
	setRightElement(rightElement);
	setParents(new ArrayList<Comparison<K>>());
	setChildComparisons(new ArrayList<Comparison<K>>());
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

    public void addParent(Comparison<K> parent) {
	this.parents.add(parent);
    }

    public void removeComparison(Comparison<K> comparison) {
	this.parents.remove(comparison);
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

    public List<Comparison<K>> getParents() {
	return parents;
    }

    @Override
    public List<Comparison<K>> getParantComparisons() {
	return parents;
    }

    public void setParents(List<Comparison<K>> parents) {
	this.parents = parents;
    }

    @Override
    public Node mergeNodes() {
	// TODO Auto-generated method stub
	return null;
    }
}
