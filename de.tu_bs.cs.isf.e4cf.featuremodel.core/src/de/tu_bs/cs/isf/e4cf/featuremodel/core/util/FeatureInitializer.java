package de.tu_bs.cs.isf.e4cf.featuremodel.core.util;

import FeatureDiagram.ArtifactReference;
import FeatureDiagram.ComponentFeature;
import FeatureDiagram.ConfigurationFeature;
import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramFactory;
import FeatureDiagram.GraphicalFeature;
import FeatureDiagram.impl.FeatureDiagramFactoryImpl;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.FeatureDiagram;

public class FeatureInitializer {

	
	public static Feature createFeature(String name, boolean isMandatory) {
		Feature feature = FeatureDiagramFactoryImpl.eINSTANCE.createFeature();
		initialize(feature, name, isMandatory);
		return feature;
	}
	
	public static ComponentFeature createComponentFeature(String name) {
		ComponentFeature feature = FeatureDiagramFactoryImpl.eINSTANCE.createComponentFeature();
		initialize(feature, name, false);
		return feature;
	}
	
	public static ConfigurationFeature createConfigurationFeature(String name) {
		ConfigurationFeature feature = FeatureDiagramFactoryImpl.eINSTANCE.createConfigurationFeature();
		initialize(feature, name, false);
		return feature;
	}
	
	private static void initialize(Feature feature, String name, boolean isMandatory) {
		feature.setName(name);
		feature.setMandatory(isMandatory);
		feature.setAlternative(false);
		feature.setOr(false);
		feature.setAbstract(false);
		if (feature instanceof ComponentFeature) {
			feature.setOr(true);
			((ComponentFeature) feature).setFeaturediagramm(new FeatureDiagram());
			((ComponentFeature) feature).getFeaturediagramm().getRoot().setName(name);
		}
		
		GraphicalFeature graphicalFeature = FeatureDiagramFactory.eINSTANCE.createGraphicalFeature();
		feature.setGraphicalfeature(graphicalFeature);
		
		ArtifactReference artifactReference = FeatureDiagramFactoryImpl.eINSTANCE.createArtifactReference();
		artifactReference.setArtifactClearName(feature.getName());
		feature.getArtifactReferences().add(artifactReference);
	}
}
