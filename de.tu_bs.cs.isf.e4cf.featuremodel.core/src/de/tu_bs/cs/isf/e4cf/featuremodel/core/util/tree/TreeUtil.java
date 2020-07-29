package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.tree;


import java.util.Map;

import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramm;
import FeatureDiagram.GraphicalFeature;

public class TreeUtil {

	/**
	 * Returns true if the given Feature is a leaf.
	 */
	public static boolean isLeaf(Feature feature) {
		return feature.getChildren().isEmpty();
	}
	
	/**
	 * Returns true if the current feature has at least one sibling.
	 */
	public static boolean hasSibling(Feature feature) {
		if(feature.getParent()!= null) {
			Feature parentFeature = feature.getParent();
			if(parentFeature.getChildren().size() > 1) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * This method returns the next left sibling or null if the feature are the most left.
	 */
	public static Feature getRightMostSibling(Feature feature) {
		double distance = 0;
		Feature mostRightFeature = feature;
		//check for root
		if(feature.getParent()!= null) {
			double featureX = feature.getGraphicalfeature().getX();
			for(Feature sibling : feature.getParent().getChildren()) {
				double siblingX = sibling.getGraphicalfeature().getX();
				double distanceBetween = featureX - siblingX;
				if(distanceBetween <= distance && distanceBetween <= 0 ) {
					mostRightFeature = sibling;
					distance = distanceBetween;
				}
			}
		}
		return mostRightFeature;
	}
	
	/**
	 * This method returns true if the given feature is the most left sibling or it has no parent.
	 */
	public static boolean isMostLeftFeature(Feature feature) {
		if(feature.getParent() == null) {
			return true;
		}
		for(Feature sibling : feature.getParent().getChildren()) {
			if(sibling.getGraphicalfeature().getX() <= feature.getGraphicalfeature().getX() && sibling != feature) {
				return false;
			}
		}
		return true;	
	}
	
	/**
	 * This method returns true if the given feature is the most right sibling or it has no parent.
	 */
	public static boolean isMostRightFeature(Feature feature) {
		if(feature.getParent() == null) {
			return true;
		}
		for(Feature sibling : feature.getParent().getChildren()) {
			if(sibling.getGraphicalfeature().getX() >= feature.getGraphicalfeature().getX() && sibling != feature) {
				return false;
			}
		}
		return true;	
	}

	/**
	 * This method resets all x and y position of every node to 0.
	 */
	public static void resetTreePosition(FeatureDiagramm diagram) {
		Feature rootFeature = diagram.getRoot();
		resetFeaturePosition(rootFeature);
	}
	
	/**
	 * This method resets all x and y position of the given feature and his children.
	 */
	private static void resetFeaturePosition(Feature feature) {
		GraphicalFeature graFeature = feature.getGraphicalfeature();
		
		graFeature.setX(0d);
		graFeature.setY(0d);

		// only reset visible features 
		if (!feature.isHidden()) {
			for(Feature child : feature.getChildren()) {
				resetFeaturePosition(child);
			}
		}
			
	}
	
	public static void setFeaturePosition(Feature feature , Map<Feature, java.awt.geom.Rectangle2D.Double> featurePositionMap) {
		GraphicalFeature graFeature = feature.getGraphicalfeature();
		java.awt.geom.Rectangle2D.Double rec = featurePositionMap.get(feature);
		if (rec != null) {
			graFeature.setX(rec.getX());
			graFeature.setY(rec.getY());
			
			for(Feature child : feature.getChildren()) {
				setFeaturePosition(child,featurePositionMap);
			}
		}
	}
	
	
	/**
	 * This method returns the min/max value of children features if no children available it returns features min/max.
	 * @param feature
	 */
	public static MinMax getChildrenMinMax(Feature feature) {
		MinMax minMax = new MinMax(Double.MAX_VALUE, Double.MIN_VALUE);
		//if no children available 
		if(feature.getChildren().isEmpty()) {
			minMax.min = feature.getGraphicalfeature().getX();
			minMax.max = feature.getGraphicalfeature().getX() + feature.getGraphicalfeature().getWidth();
		}
		//determinate children min/max values.
		for(Feature child : feature.getChildren()) {
			GraphicalFeature childGraphicalFeature  = child.getGraphicalfeature();
			
			if(childGraphicalFeature.getX() < minMax.min) {
				minMax.min = childGraphicalFeature.getX();
			}
			
			if(childGraphicalFeature.getX() + childGraphicalFeature.getWidth() > minMax.max) {
				minMax.max = childGraphicalFeature.getX() + childGraphicalFeature.getWidth();
			}
		}
		return minMax;
	}
	
	/**
	 * This method returns the min/max value of a subtree.
	 * @param feature
	 */
	public static MinMax getSubTreebounce(Feature feature) {
		MinMax minMax = new MinMax(java.lang.Double.MAX_VALUE, java.lang.Double.MIN_VALUE);
		if(!isLeaf(feature)) {
			for(Feature child : feature.getChildren()) {
				MinMax childBounce = getSubTreebounce(child);
				
				if(minMax.min > childBounce.min) {
					minMax.min = childBounce.min;
				}
				if(minMax.max < childBounce.max) {
					minMax.max = childBounce.max;
				}
			}
		} else {
			minMax.min = feature.getGraphicalfeature().getX();
			minMax.max = feature.getGraphicalfeature().getX() + feature.getGraphicalfeature().getWidth();
		}

		return minMax;
		
	}
	

	
	
	/**
	 * This method shifts the x of the feature and his children by a given shiftValue.
	 */
	public static void shiftFeatureWithChildren(Feature feature, Double shiftValue) {
		GraphicalFeature graFeature = feature.getGraphicalfeature();
		shiftX(graFeature, shiftValue);
		
		for(Feature child : feature.getChildren()) {
			shiftFeatureWithChildren(child, shiftValue);
		}
	}
	
	/**
	 * This method shifts the x position of a feature by a given value.
	 */
	public static void shiftX(GraphicalFeature graFeature, double shiftValue) {
		graFeature.setX(graFeature.getX() + shiftValue);
	}
	

}
