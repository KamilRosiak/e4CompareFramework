package de.tu_bs.cs.isf.e4cf.compare.comparison.impl;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class NodeComparison extends AbstractComparsion<Node> {

	private static final long serialVersionUID = 7260025397506680712L;

	public NodeComparison(Node leftArtifact, Node rightArtifact) {
		super(leftArtifact, rightArtifact);
	}
	
	public NodeComparison(Node leftArtifact, Node rightArtifact, NodeComparison parent) {
		this(leftArtifact, rightArtifact);
		
	}
	

}
