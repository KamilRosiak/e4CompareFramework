package de.tu_bs.cs.isf.e4cf.refactoring.model;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

public class RefactoringResult {

	private List<Component> components;

	private List<Tree> trees;

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public List<Tree> getTrees() {
		return trees;
	}

	public void setTrees(List<Tree> trees) {
		this.trees = trees;
	}

	public RefactoringResult(List<Component> components, List<Tree> trees) {
		super();
		this.components = components;
		this.trees = trees;
	}

}
