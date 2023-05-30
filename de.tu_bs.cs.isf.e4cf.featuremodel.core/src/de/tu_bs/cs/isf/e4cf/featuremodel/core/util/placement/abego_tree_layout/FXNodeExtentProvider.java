package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.abego_tree_layout;

import org.abego.treelayout.NodeExtentProvider;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature.FXGraphicalFeature;

public class FXNodeExtentProvider implements NodeExtentProvider<FXGraphicalFeature> {

	@Override
	public double getWidth(FXGraphicalFeature treeNode) {
		return treeNode.getWidth();
	}

	@Override
	public double getHeight(FXGraphicalFeature treeNode) {
		return treeNode.getHeight();
	}
	

}
