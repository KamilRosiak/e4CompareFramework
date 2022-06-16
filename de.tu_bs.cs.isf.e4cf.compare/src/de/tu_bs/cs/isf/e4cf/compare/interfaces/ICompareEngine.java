package de.tu_bs.cs.isf.e4cf.compare.interfaces;

import java.io.Serializable;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces.Matcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.interfaces.Metric;

public interface ICompareEngine<Type> extends Serializable {

	/**
	 * This method compares two trees and all underlying child nodes and returns the
	 * merged tree which contains all information of both trees. Uses IMatcher ,
	 * IMerger,
	 */
	public Tree compare(Tree first, Tree second);

	/**
	 * This method compares a list of trees and all underlying child nodes and
	 * returns the merged tree which contains all information of both trees. Uses
	 * IMatcher , IMerger,
	 */
	public Tree compare(List<Tree> variants);

	/**
	 * This method compares two nodes and all underlying child nodes recursively
	 * 
	 * @param first
	 * @param second
	 * @return comparison between two nodes
	 */
	public Comparison<Type> compare(Node first, Node second);

	/**
	 * Returns the default comparator for the comparison of two nodes
	 */
	public Comparator<Type> getDefaultComparator();

	/**
	 * This method returns the comparison metric that is used for the comparison in
	 * a compare engine instance
	 */
	public Metric getMetric();

	/**
	 * This method returns the matcher that is used to match nodes from the
	 * comparison.
	 */
	public Matcher getMatcher();

}
