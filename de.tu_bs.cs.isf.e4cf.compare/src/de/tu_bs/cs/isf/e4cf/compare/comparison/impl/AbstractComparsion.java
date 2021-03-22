package de.tu_bs.cs.isf.e4cf.compare.comparison.impl;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;

public abstract class AbstractComparsion<Type> implements Comparison<Type> {
	private static final long serialVersionUID = -2653313078560352977L;
	private Type leftArtifact;
	private Type rightArtifact;
	private float similarity;
	private List<Comparison<Type>> childComparisons;
	private List<Comparator<Type>> comparators;
	
	public AbstractComparsion(Type leftArtifact, Type rightArtifact) {
		setLeftArtifact(leftArtifact);
		setRightArtifact(rightArtifact);
		this.childComparisons = new ArrayList<Comparison<Type>>();
		this.comparators = new ArrayList<Comparator<Type>>();
	}
	
	@Override
	public Type getLeftArtifact() {
		return this.leftArtifact;
	}
	
	@Override
	public Type getRightArtifact() {
		return this.rightArtifact;
	}
	
	@Override
	public void setRightArtifact(Type artifact) {
		this.rightArtifact = artifact;
	}
	
	@Override
	public void setLeftArtifact(Type artifact) {
		this.leftArtifact = artifact;
	}
	@Override
	public float getSimilarity() {
		return this.similarity;
	}
	
	@Override
	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}

	@Override
	public List<Comparison<Type>> getChildComparisons() {
		return this.childComparisons;
	}
	
	@Override
	public void addChildComparison(Comparison<Type> comparison) {
		this.childComparisons.add(comparison);
	}
	
	@Override
	public void addComparator(Comparator<Type> comparator) {
		this.comparators.add(comparator);
	}
	
	@Override
	public List<Comparator<Type>> getComparators() {
		return this.comparators;
	}
}
