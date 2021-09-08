package de.tu_bs.cs.isf.e4cf.refactoring.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class RevisionComparison {

	
	private Map<Container,Node> matchedNodes;
	
	private List<Container> deletedNodes;

	public Map<Container, Node> getMatchedNodes() {
		return matchedNodes;
	}

	public void setMatchedNodes(Map<Container, Node> matchedNodes) {
		this.matchedNodes = matchedNodes;
	}

	public List<Container> getDeletedNodes() {
		return deletedNodes;
	}

	public void setDeletedNodes(List<Container> deletedNodes) {
		this.deletedNodes = deletedNodes;
	}

	public RevisionComparison() {
		super();
		this.deletedNodes = new ArrayList<Container>();
		this.matchedNodes = new HashMap<Container, Node>();
	}
	
	
	
}
