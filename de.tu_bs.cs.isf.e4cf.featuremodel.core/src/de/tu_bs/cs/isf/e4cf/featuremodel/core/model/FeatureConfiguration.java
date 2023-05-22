package de.tu_bs.cs.isf.e4cf.featuremodel.core.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class FeatureConfiguration implements IFeatureConfiguration, Serializable {
	private static final long serialVersionUID = -4352729706432310608L;
	
	private final FeatureDiagram diagram;
	private Set<IFeature> selectedFeatures = new HashSet<>();
	
	public FeatureConfiguration(FeatureDiagram diagram) {
		this.diagram = diagram;
	}
	
	@Override
	public FeatureDiagram getDiagram() {
		return diagram;
	}

	@Override
	public Set<IFeature> getSelectedFeatures() {
		return selectedFeatures;
	}

	@Override
	public void selectFeature(IFeature feature) {
		if (diagram.contains(feature)) {
			selectedFeatures.add(feature);
		}
	}

	@Override
	public void deselectFeature(IFeature feature) {
		if (diagram.contains(feature)) {
			selectedFeatures.remove(feature);
		}
	}
}
