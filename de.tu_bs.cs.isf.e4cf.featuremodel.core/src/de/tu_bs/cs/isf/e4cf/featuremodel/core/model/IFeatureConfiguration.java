package de.tu_bs.cs.isf.e4cf.featuremodel.core.model;

import java.util.Set;

public interface IFeatureConfiguration {
	
	FeatureDiagram getDiagram();
	Set<IFeature> getSelectedFeatures();
	
	void selectFeature(IFeature feature);
	void deselectFeature(IFeature feature);
}
