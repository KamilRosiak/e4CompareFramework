package de.tu_bs.cs.isf.e4cf.featuremodel.configuration.model;

import featureConfiguration.impl.FeatureToBooleanMapImpl;

public class ExtFeatureToBooleanMap extends FeatureToBooleanMapImpl {

	public ExtFeatureToBooleanMap() {
		super();
	}
	
	@Override
	public String toString() {
		return key != null ? key.getName() : "<no label>";
	}

}
