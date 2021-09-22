package de.tu_bs.cs.isf.e4cf.refactoring.model;

import java.util.Map;

public class MoveActionResult{

	private Map<MultiSetNode, Integer> movedNodesWithPosition;

	public Map<MultiSetNode, Integer> getMovedNodesWithPosition() {
		return movedNodesWithPosition;
	}

	public void setMovedNodesWithPosition(Map<MultiSetNode, Integer> movedNodesWithPosition) {
		this.movedNodesWithPosition = movedNodesWithPosition;
	}

	public MoveActionResult(Map<MultiSetNode, Integer> movedNodesWithPosition) {
		super();
		this.movedNodesWithPosition = movedNodesWithPosition;
	}
	
	
	
	
	

}
