package de.tu_bs.cs.isf.e4cf.compare;

import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.interfaces.ICompareEngine;
import de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces.Matcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.interfaces.Metric;

public class CompareEngineHierarchical implements ICompareEngine {

	@Override
	public Tree compare(Tree first, Tree second, Metric metric, Matcher matcher) {
		NodeComparison root = compare(first.getRoot(),second.getRoot(), metric, matcher);
		
		
		//default compare
		
		
		
		
		return null;
	}
	
	
	
	
	
	

	
	
	@Override
	public NodeComparison compare(Node first, Node second, Metric metric, Matcher matcher) {
		
		//if nodes are of the same type
		if(first.getNodeType().equals(second.getNodeType())) {
			
			
			
			
		} else {
			return null;
		}
		return null;	
	}
	

	
	
	

	
	

	

}
