package de.tu_bs.cs.isf.e4cf.featuremodel.configuration.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramm;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.FeatureDiagram;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.helper.FeatureDiagramIterator;
import featureConfiguration.FeatureConfiguration;
import featureConfiguration.FeatureConfigurationFactory;

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
		String fcName;
		int i = 0;
		do {
			fcName = "Configuration_" + fd.getRoot().getName() + "_" + (fd.getFeatureConfiguration().size() + i++);
		} while (fd.getFeatureConfiguration().stream()
				.map(FeatureConfiguration::getName)
				.collect(Collectors.toList())
				.contains(fcName));
		FeatureConfiguration fc = createFeatureConfiguration(fcName, fd, Collections.emptyMap());
		
		for (Iterator<Feature> it = new FeatureDiagramIterator(fc.getFeatureDiagram()); it.hasNext();) {
			Feature feature = it.next();
			fc.getFeatureSelection().put(feature, false);
		}
		//add possible Configurations to FeatureConfigurationList in FeatureDiagram
		fd.getFeatureConfiguration().add(fc);
		System.out.println(fd.getFeatureConfiguration().get(0).getName());
		return fc;
	}

}
