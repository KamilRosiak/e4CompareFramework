package de.tu_bs.cs.isf.e4cf.featuremodel.configuration.view;

import java.util.Map.Entry;

import FeatureDiagram.Feature;

public class FeatureSelectionElement {
	private Entry<Feature, Boolean> entry;
	
	public FeatureSelectionElement(Entry<Feature, Boolean> entry) {
		this.entry = entry;
	}

	public Entry<Feature, Boolean> get() {
		return entry;
	}
	
	public Feature getFeature() {
		return entry.getKey();
	}
	
	public Boolean isSelected() {
		return entry.getValue();
	}
	
	public void setSelected(boolean selected) {
		entry.setValue(selected);
	}
	
	@Override
	public String toString() {
		return entry.getKey().getName();
	}
	
	
	
}
