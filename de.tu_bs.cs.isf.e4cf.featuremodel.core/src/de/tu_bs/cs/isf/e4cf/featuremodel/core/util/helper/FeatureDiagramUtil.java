package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.helper;

import java.util.HashSet;
import java.util.Set;

import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramm;

/**
 * Utility class with helper methods for the featureDiagram
 * @author Kamil Rosiak
 *
 */
public class FeatureDiagramUtil {
	
	/**
	 * This method returns a Set of all Feature in a FeatureDiagram.
	 */
	public static Set<Feature> getAllFeature(FeatureDiagramm diagram) {
		Set<Feature> featureSet = new HashSet<Feature>();
		addFeatureWithChildren(featureSet, diagram.getRoot());
		return featureSet;
	}
	
	/**
	 * Helper Method that adding the given feature to the given set.
	 */
	public static void addFeatureWithChildren(Set<Feature> featureSet, Feature parentFeature) {
		featureSet.add(parentFeature);
		for(Feature feature : parentFeature.getChildren()) {
			addFeatureWithChildren(featureSet, feature);
		}
	}	
}
