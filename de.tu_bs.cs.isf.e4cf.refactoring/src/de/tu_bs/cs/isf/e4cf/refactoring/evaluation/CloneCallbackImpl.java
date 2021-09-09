package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

public class CloneCallbackImpl implements CloneCallback {

	private Tree currentTree;

	public CloneCallbackImpl(Tree currentTree) {
		super();
		this.currentTree = currentTree;
	}

	public Tree getCurrentTree() {
		return currentTree;
	}

	public void setCurrentTree(Tree currentTree) {
		this.currentTree = currentTree;
	}

	@Override
	public void handle(Tree tree) {
		currentTree = tree.cloneTree();

	}

}
