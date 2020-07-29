package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.abego_tree_layout;

import org.abego.treelayout.Configuration;

import FeatureDiagram.Feature;

public class FDConfiguration implements Configuration<Feature> {
	private Location rootLocation;
	private static double GAP_BETWEEN_NODES = 10;
	
	public FDConfiguration(Location rootLocation) {
		this.rootLocation = rootLocation;
	}
	
	@Override
	public Location getRootLocation() {
		return rootLocation;
	}

	@Override
	public AlignmentInLevel getAlignmentInLevel() {
		return AlignmentInLevel.TowardsRoot;
	}

	@Override
	public double getGapBetweenLevels(int nextLevel) {
		return 40;
	}

	@Override
	public double getGapBetweenNodes(Feature node1, Feature node2) {
		double gap1 = node2.getGraphicalfeature().getX() - node1.getGraphicalfeature().getX() + GAP_BETWEEN_NODES;
		double gap2 = node1.getGraphicalfeature().getX() - node2.getGraphicalfeature().getX() + GAP_BETWEEN_NODES;
		return Math.max(gap1, gap2);
	}
}
