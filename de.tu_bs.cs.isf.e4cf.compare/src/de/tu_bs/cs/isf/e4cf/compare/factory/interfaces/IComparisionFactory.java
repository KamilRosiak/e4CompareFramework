package de.tu_bs.cs.isf.e4cf.compare.factory.interfaces;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparison;

public interface IComparisionFactory {
	/**
	 * This method returns a new instance of the given comparison.
	 * @param <K>
	 * @param comparison
	 * @return
	 */
	public Comparison getComparison(Comparison comparison);
}
