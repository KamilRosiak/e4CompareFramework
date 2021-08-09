package de.tu_bs.cs.isf.e4cf.refactoring.model;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

public class ExtractionResult {

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

	public ExtractionResult(List<Component> components, List<Tree> trees, List<ComponentLayer> layers) {
		super();
		this.components = components;
		this.trees = trees;
		this.layers = layers;
	}
	
	public List<ComponentLayer> getLayers() {
		return layers;
	}

	public void setLayers(List<ComponentLayer> layers) {
		this.layers = layers;
	}

	private List<ComponentLayer> layers;
	
	
}
