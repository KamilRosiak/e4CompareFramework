package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.tree;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.elements.FXGraphicalFeature;

public class TreeFXUtil {
	
	/**
	 * This method returns the next left sibling or null if the feature are the most left.
	 */
	public static FXGraphicalFeature getRightMostSibling(FXGraphicalFeature feature) {
		double distance = 0;
		FXGraphicalFeature nextLeftFeature = null;
		
		
		if(feature.getParent()!= null) {
			double featureX = feature.getTranslateX();
			for(FXGraphicalFeature sibling : feature.getChildFeatures()) {
				double siblingX = sibling.getTranslateX();
				double distanceBetween = featureX - siblingX;
				if(distanceBetween <= distance && distanceBetween <= 0 ) {
					nextLeftFeature = sibling;
					distance = distanceBetween;
				}
			}
		}
		if(nextLeftFeature == null || nextLeftFeature.equals(feature)) {
			return feature;
		}
		return nextLeftFeature;
	}
	
	
	/**
	 * This method returns true if the given feature is the most left sibling or it has no parent.
	 */
	public static boolean isMostLeftFeature(FXGraphicalFeature feature) {
		if(feature.getParent() == null) {
			return true;
		}
		for(FXGraphicalFeature sibling : feature.getChildFeatures()) {
			if(sibling.getTranslateX() <= feature.getTranslateX()) {
				return false;
			}
		}
		return true;	
	}
	
	/**
	 * This method calculates the min x and max x value of a List of nodes.
	 * @return returns a pair first = min , second = max
	 */
	public static MinMax getMinMaxX(List<FXGraphicalFeature> children) {
		MinMax minMax = new MinMax(Double.MAX_VALUE, Double.MIN_VALUE);
		for(FXGraphicalFeature child : children) {

			if(child.getTranslateX() < minMax.min) {
				minMax.min = child.getTranslateX();
			}
			if(child.getTranslateX() + child.getWidth() > minMax.max) {
				minMax.max = child.getTranslateX() + child.getWidth();
			}
		}
		return minMax;
	}
	
	/**
	 * This method resets all positions of the given feature and his childs.
	 */
	public static void resetTreePosition(FXGraphicalFeature root) {
		root.getFeature().getGraphicalfeature().setX(0d);
		root.getFeature().getGraphicalfeature().setY(0d);
		for(FXGraphicalFeature child : root.getChildFeatures()) {
			resetTreePosition(child);
		}
	}
	
	
}
