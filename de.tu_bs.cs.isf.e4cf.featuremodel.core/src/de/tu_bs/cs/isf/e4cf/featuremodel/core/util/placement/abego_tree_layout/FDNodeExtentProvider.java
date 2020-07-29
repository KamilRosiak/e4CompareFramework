package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.abego_tree_layout;

import org.abego.treelayout.NodeExtentProvider;

import FeatureDiagram.Feature;

public class FDNodeExtentProvider implements NodeExtentProvider<Feature> {
	
	@Override
	public double getHeight(Feature feature) {
		return feature.getGraphicalfeature().getHeight();
	}

	@Override
	public double getWidth(Feature feature) {
		return feature.getGraphicalfeature().getWidth();
	}

}
