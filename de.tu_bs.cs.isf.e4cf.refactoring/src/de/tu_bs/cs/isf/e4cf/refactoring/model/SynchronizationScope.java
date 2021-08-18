package de.tu_bs.cs.isf.e4cf.refactoring.model;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class SynchronizationScope {
	
	private Node node;
	
	private boolean synchronize;

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public boolean synchronize() {
		return synchronize;
	}

	public void setSynchronize(boolean shouldSynchronize) {
		this.synchronize = shouldSynchronize;
	}

	public SynchronizationScope(Node node, boolean shouldSynchronize) {
		super();
		this.node = node;
		this.synchronize = shouldSynchronize;
	}
	
	

}
