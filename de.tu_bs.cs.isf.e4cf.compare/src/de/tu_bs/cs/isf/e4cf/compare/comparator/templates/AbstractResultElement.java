package de.tu_bs.cs.isf.e4cf.compare.comparator.templates;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;
import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.ResultElement;

public class AbstractResultElement<Type> implements ResultElement<Type> {
	private Comparator<Type> usedComparator;
	private float similiarty = 0f;

	public AbstractResultElement(Comparator<Type> usedComparator, float similarity) {
		setUsedComparator(usedComparator);
		setSimilarity(similarity);
	}

	@Override
	public Comparator<Type> getUsedComparator() {
		return this.usedComparator;
	}

	@Override
	public float getSimilarity() {
		return this.similiarty;
	}

	@Override
	public void setSimilarity(float similarity) {
		this.similiarty = similarity;
	}

	public void setUsedComparator(Comparator<Type> usedComparator) {
		this.usedComparator = usedComparator;
	}

	public float getSimiliarty() {
		return similiarty;
	}

	public void setSimiliarty(float similiarty) {
		this.similiarty = similiarty;
	}
}
