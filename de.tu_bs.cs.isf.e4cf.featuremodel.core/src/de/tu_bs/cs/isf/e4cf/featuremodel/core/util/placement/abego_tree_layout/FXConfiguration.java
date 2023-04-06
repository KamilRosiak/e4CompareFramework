package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.abego_tree_layout;

import org.abego.treelayout.Configuration;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature.FXGraphicalFeature;

public class FXConfiguration implements Configuration<FXGraphicalFeature> {
	private Location rootLocation;
	private static double GAP_BETWEEN_NODES = 10;
	
	public FXConfiguration(Location rootLocation) {
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
	public double getGapBetweenNodes(FXGraphicalFeature node1, FXGraphicalFeature node2) {
		double gap1 = node2.getLayoutX() - node1.getLayoutX() + GAP_BETWEEN_NODES;
		double gap2 = node1.getLayoutX() - node2.getLayoutX() + GAP_BETWEEN_NODES;
		return Math.max(gap1, gap2);
	}
	
	

}
