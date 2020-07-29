package de.tu_bs.cs.isf.e4cf.featuremodel.configuration.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramm;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.helper.FeatureDiagramIterator;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfiguration;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfigurationFactory;

public class FeatureConfigurationBuilder {

	private FeatureConfigurationFactory factory = FeatureConfigurationFactory.eINSTANCE;
	
	public FeatureConfiguration createFeatureConfiguration(String name, FeatureDiagramm fd, Map<Feature, Boolean> featureSelections) {
		FeatureConfiguration fc = factory.createFeatureConfiguration();
		fc.setName(name);
		fc.setFeatureDiagram(fd);
		fc.getFeatureSelection().putAll(featureSelections);
		return fc;
	}
	
	public FeatureConfiguration createFeatureConfiguration(FeatureDiagramm fd) {
		String fcName = "Configuration_"+fd.getRoot().getName();
		FeatureConfiguration fc = createFeatureConfiguration(fcName, fd, Collections.emptyMap());
		
		for (Iterator<Feature> it = new FeatureDiagramIterator(fc.getFeatureDiagram()); it.hasNext();) {
			Feature feature = it.next();
			fc.getFeatureSelection().put(feature, false);
		}
		
		return fc;
	}

}
