package de.tu_bs.cs.isf.e4cf.featuremodel.configuration.view;

import java.util.Map.Entry;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.IFeature;

public class FeatureSelectionElement {
	private Entry<IFeature, Boolean> entry;

	public FeatureSelectionElement(Entry<IFeature, Boolean> entry) {
		this.entry = entry;
	}

	public Entry<IFeature, Boolean> get() {
		return entry;
	}

	public IFeature getFeature() {
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
