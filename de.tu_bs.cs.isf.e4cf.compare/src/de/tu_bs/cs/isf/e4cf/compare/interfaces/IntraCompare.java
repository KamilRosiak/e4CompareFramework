package de.tu_bs.cs.isf.e4cf.compare.interfaces;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public interface IntraCompare<Type> {
	
	public ICompareEngine<Type> getCompareEngine();
	
	public List<List<Comparison<Type>>> getCloneCluster(Node first, Node second, String nodeType);
}
