package de.tu_bs.cs.isf.e4cf.featuremodel.configuration.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.FeatureConfiguration;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.FeatureDiagram;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.IFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.helper.FeatureDiagramIterator;

public class FeatureConfigurationBuilder {

	public FeatureConfiguration createFeatureConfiguration(String name, FeatureDiagram fd,
			Map<IFeature, Boolean> featureSelections) {
		FeatureConfiguration fc = new FeatureConfiguration(fd);
		fc.setName(name);

		fc.setName(name);
		fc.setFeatureSelection(featureSelections);
		fc.setDiagram(fd);

		return fc;
	}

	public FeatureConfiguration createFeatureConfiguration(FeatureDiagram fd) {
		String fcName;
		int i = 0;
		do {
			fcName = "Configuration_" + fd.getRoot().getName() + "_" + (fd.getConfigurations().size() + i++);
		} while (fd.getConfigurations().stream().map(FeatureConfiguration::getName).collect(Collectors.toList())
				.contains(fcName));
		FeatureConfiguration fc = createFeatureConfiguration(fcName, fd, Collections.emptyMap());

		for (Iterator<IFeature> it = new FeatureDiagramIterator(fc.getDiagram()); it.hasNext();) {
			IFeature feature = it.next();
			fc.getFeatureSelection().put(feature, false);
		}
		// add possible Configurations to FeatureConfigurationList in FeatureDiagram
		fd.getConfigurations().add(fc);
		return fc;
	}

}
