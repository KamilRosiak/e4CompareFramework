package de.tu_bs.cs.isf.e4cf.compare.comparator;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.NodeComparator;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class StringComparator implements NodeComparator {

	@Override
	public String getSupportedNodeType() {
		return "ALL";
	}

	@Override
	public boolean isComparable(Node firstNode, Node secondNode) {
		return true;
	}

	@Override
	public Comparison compare(Node firstNode, Node secondNode) {
		
		
	
		
		
		return null;
	}
	
	
	public void compareAttributes(Node firstNode, Node secondNode) {
		
	}
	

	

}
