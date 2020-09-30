package de.tu_bs.cs.isf.e4cf.compare;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.comparator.AttrComparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.interfaces.Result;

public class CompareEngine {
	
	
	
	public void compare(Tree firstArtifact, Tree secondArtifact) {

		Result result = compare(firstArtifact.getRoot(), secondArtifact.getRoot());
		
	}
	
	
	public Result compare(Node firstNode, Node secondNode) {
		if(isSameNodeType(firstNode, secondNode)) {
				
		} else {
			
		}
		return null;
	}
	

	private Comparison defaultCompare(Node firstNode, Node secondNode) {
	    List<AttrComparison> comparisons = new ArrayList<AttrComparison>();
		
		for(Attribute first_attr : firstNode.getAttributes()) {
			for(Attribute second_attr : secondNode.getAttributes()) {
			    comparisons.add(new AttrComparison(first_attr,second_attr, first_attr.compare(second_attr)));	
			}	
		}
		
		return null;
	}
	
	/**
	 * This method compare the node type of two nodes. 
	 * @return True if types equals else false
	 */
	private boolean isSameNodeType(Node firstNode, Node secondNode) {
		return firstNode.getNodeType().equals(secondNode.getNodeType());
	}
	
}
