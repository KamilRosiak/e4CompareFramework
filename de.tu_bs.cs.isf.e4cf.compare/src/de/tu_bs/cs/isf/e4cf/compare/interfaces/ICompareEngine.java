package de.tu_bs.cs.isf.e4cf.compare.interfaces;

import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces.Matcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.interfaces.Metric;

public interface ICompareEngine<K> {

	/**
	 * This method compares two trees and all underlying child nodes and returns the merged tree which contains all information of both trees.
	 * Uses IMatcher , IMerger,
	 */
	public Tree compare(Tree first, Tree second, Metric metric, Matcher matcher);
	
	
	public Comparison<K> compare(Node first, Node second, Metric metric, Matcher matcher);
}
