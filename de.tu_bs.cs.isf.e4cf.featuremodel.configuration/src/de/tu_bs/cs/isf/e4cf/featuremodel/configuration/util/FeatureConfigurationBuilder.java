package de.tu_bs.cs.isf.e4cf.featuremodel.configuration.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import FeatureDiagram.Feature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.FeatureDiagram;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.helper.FeatureDiagramIterator;
import featureConfiguration.FeatureConfiguration;
import featureConfiguration.FeatureConfigurationFactory;

public class FeatureConfigurationBuilder {

	private FeatureConfigurationFactory factory = FeatureConfigurationFactory.eINSTANCE;
	
	public FeatureConfiguration createFeatureConfiguration(String name, FeatureDiagram fd, Map<Feature, Boolean> featureSelections) {
		FeatureConfiguration fc = factory.createFeatureConfiguration();
		fc.setName(name);
		fc.setFeatureDiagram(fd);
		fc.getFeatureSelection().putAll(featureSelections);
		return fc;
	}
	
	public FeatureConfiguration createFeatureConfiguration(FeatureDiagram fd) {
		String fcName = "Configuration_"+fd.getRoot().getName();
		FeatureConfiguration fc = createFeatureConfiguration(fcName, fd, Collections.emptyMap());
		
		for (Iterator<Feature> it = new FeatureDiagramIterator(fc.getFeatureDiagram()); it.hasNext();) {
			Feature feature = it.next();
			fc.getFeatureSelection().put(feature, false);
		}
		fd.getFeatureConfiguration().add(fc);
		System.out.println(fd.getFeatureConfiguration().get(0).getName());
		return fc;
	}

}
