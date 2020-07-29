package de.tu_bs.cs.isf.e4cf.featuremodel.configuration.model;

import java.util.Map.Entry;

import FeatureDiagram.Feature;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.impl.FeatureConfigurationFactoryImpl;

public class ExtFeatureConfigurationFactory extends FeatureConfigurationFactoryImpl {

	@Override
	public Entry<Feature, Boolean> createFeatureToBooleanMap() {
		return new ExtFeatureToBooleanMap();
	}

	


}
