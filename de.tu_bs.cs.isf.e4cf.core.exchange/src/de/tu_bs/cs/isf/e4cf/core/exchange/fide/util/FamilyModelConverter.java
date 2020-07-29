package de.tu_bs.cs.isf.e4cf.core.exchange.fide.util;

import java.util.HashSet;
import java.util.Set;

import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramm;
import FeatureModel.FeatureModelFactory;
import FeatureModel.FeatureModell;
import FeatureModel.impl.FeatureModelFactoryImpl;


/**
 * Converts VAT Model to Feature IDE Model
 * @author Dibo Gonda (y0085182)  / Patrick Suszek (y0082857) / Dzhakhar Akperov (y0087205)
 * @date 27.01.2020
 */
public class FamilyModelConverter {
	
	private static Set<String> uniqueNames;
	
	/**
	 * Converts FeatureDiagram to FeatureIDE Model
	 * @param familyModel
	 * @return
	 */
	public static FeatureModell convertToFeatureIDE(FeatureDiagramm familyModel) {
		uniqueNames = new HashSet<>();
		
		FeatureModell featureIDEModel = FeatureModelFactory.eINSTANCE.createFeatureModell();
		
		Feature familyModelRoot = familyModel.getRoot();
		FeatureModel.Feature fideRoot = getFideFeature(familyModelRoot);
		
		convertFamilyModelFeature(fideRoot, familyModelRoot);
				
		uniqueNames.clear();
		
		featureIDEModel.setStruct(FeatureModelFactoryImpl.eINSTANCE.createStruct());
		
		if (familyModelRoot.isOr()) {
			featureIDEModel.getStruct().setOr(fideRoot);
		} else if (familyModelRoot.isAlternative()) {
			featureIDEModel.getStruct().setAlt(fideRoot);
		} else if (familyModelRoot.getChildren().isEmpty()) {
			featureIDEModel.getStruct().setFeature(fideRoot);
		}
		else {
			featureIDEModel.getStruct().setAnd(fideRoot);
		}
		
		featureIDEModel.setProperties(FeatureModelFactoryImpl.eINSTANCE.createProperties());
		return featureIDEModel;
	}

	
	
	/**
	 * Converts Children of FeatureDiagram to FeatureIDE
	 * @param fideFeature
	 * @param feature
	 */
	private static void convertFamilyModelFeature(FeatureModel.Feature fideFeature, Feature feature) {
		if (!feature.getChildren().isEmpty()) {
			feature.getChildren().stream().forEach(child -> {
				FeatureModel.Feature ideChild = getFideFeature(child);
				if (child.getChildren() != null || !feature.getChildren().isEmpty()) {
					convertFamilyModelFeature(ideChild, child);
				}
				if (child.getChildren().isEmpty() || child.getChildren() == null) {
					fideFeature.getFeature().add(ideChild);
				} else if (child.isOr()) {
					fideFeature.getOr().add(ideChild);
				} else if (child.isAlternative()) {
					fideFeature.getAlt().add(ideChild);
				} else {
					fideFeature.getAnd().add(ideChild);
				}
			});
		}
	}
	
	/**
	 * Converts FeatureDiagram into FeatureIDE
	 * @param feature
	 * @return
	 */
	private static FeatureModel.Feature getFideFeature(Feature feature) {
		FeatureModel.Feature fideFeature = FeatureModelFactoryImpl.eINSTANCE.createFeature();
		
		fideFeature.setName(getValidName(feature));
				
		if (feature.isAbstract()) {
			fideFeature.setAbstract(true);
		}
		if (feature.isHidden()) {
			fideFeature.setHidden(true);
		}
		if (feature.isMandatory()) {
			fideFeature.setMandatory(true);
		}
		return fideFeature;
	}
	
	/**
	 * Converts a feature name into a valid name
	 * Also generates an unique name if the actual one is not unique within the model
	 * 
	 * @param feature that holds a possibly invalid name
	 * @return a valid unique name
	 */
	public static <T> String getValidName(T feature) {
		String validName = "";
		if (feature instanceof Feature) {
			validName = Normalizer.getNormalizedName(((Feature) feature).getName());
		}
		
		if (uniqueNames.contains(validName))
		{
			validName = Normalizer.getUniqueName(validName, uniqueNames);
		}
		uniqueNames.add(validName);
		return validName;
	}
	
}
