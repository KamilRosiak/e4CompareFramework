package de.tu_bs.cs.isf.e4cf.compare.factory;

import de.tu_bs.cs.isf.e4cf.compare.comparator.AttrComparison;
import de.tu_bs.cs.isf.e4cf.compare.comparator.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.factory.interfaces.IComparisionFactory;

public class ComparisonFactory implements IComparisionFactory {
	
	
	@Override
	public Comparison getComparison(Comparison comparison) {
		if(comparison instanceof AttrComparison) {
			return new AttrComparison((AttrComparison)comparison);
		} else if(comparison instanceof NodeComparison) {
			return new NodeComparison((NodeComparison)comparison);
		}
		return null;
	}

}
