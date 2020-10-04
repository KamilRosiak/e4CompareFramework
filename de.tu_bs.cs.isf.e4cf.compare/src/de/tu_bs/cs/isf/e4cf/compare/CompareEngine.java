package de.tu_bs.cs.isf.e4cf.compare;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.comparator.AttrComparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.interfaces.Matcher;
import de.tu_bs.cs.isf.e4cf.compare.interfaces.Result;

public class CompareEngine {
	private Matcher matcher;
	
	
	public void compare(Tree firstArtifact, Tree secondArtifact) {
	    Result result = compare(firstArtifact.getRoot(), secondArtifact.getRoot());
		
	}
	
	/**
	 * 
	 * @param firstNode
	 * @param secondNode
	 * @return
	 */
	public Result compare(Node firstNode, Node secondNode) {
	    Set<String> nodeTypes = new HashSet<String>();
	    nodeTypes.addAll(firstNode.getAllNodeTypes());
	    nodeTypes.addAll(secondNode.getAllNodeTypes());
	    
	    /**
	     * Compare only nodes with the same type.
	     */
	    for(String nodeType : nodeTypes) {
		List<Node> firstArtifacts = firstNode.getChildrenOfType(nodeType);
		List<Node> secondArtifacts = secondNode.getChildrenOfType(nodeType);
		
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
	 * This method compares the node type of two nodes. 
	 * @return True if types equals else false
	 */
	private boolean isSameNodeType(Node firstNode, Node secondNode) {
		return firstNode.getNodeType().equals(secondNode.getNodeType());
	}


	public Matcher getMatcher() {
	    return matcher;
	}


	public void setMatcher(Matcher matcher) {
	    this.matcher = matcher;
	}
	
}
