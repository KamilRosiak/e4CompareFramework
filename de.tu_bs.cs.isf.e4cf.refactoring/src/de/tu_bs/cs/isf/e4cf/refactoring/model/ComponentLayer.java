package de.tu_bs.cs.isf.e4cf.refactoring.model;

public class ComponentLayer {

	private String layer;

	private boolean refactor;

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	public boolean refactor() {
		return refactor;
	}

	public void setRefactor(boolean refactor) {
		this.refactor = refactor;
	}

	public ComponentLayer(String layer, boolean refactor) {
		super();
		this.layer = layer;
		this.refactor = refactor;
	}

}
