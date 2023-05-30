package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.abego_tree_layout;

import org.abego.treelayout.TreeForTreeLayout;

import com.google.common.collect.Lists;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature.FXGraphicalFeature;

public class FXTreeForLayout implements TreeForTreeLayout<FXGraphicalFeature> {
	private final FXGraphicalFeature root;
	
	public FXTreeForLayout(FXGraphicalFeature root) {
		this.root = root;
	}
	
	@Override
	public FXGraphicalFeature getRoot() {
		return root;
	}

	@Override
	public boolean isLeaf(FXGraphicalFeature node) {
		return node.getChildFeatures().isEmpty();
	}

	@Override
	public boolean isChildOfParent(FXGraphicalFeature node, FXGraphicalFeature parentNode) {
		return parentNode.getChildFeatures().contains(node);
	}

	@Override
	public Iterable<FXGraphicalFeature> getChildren(FXGraphicalFeature parentNode) {
		return parentNode.getChildFeatures();
	}

	@Override
	public Iterable<FXGraphicalFeature> getChildrenReverse(FXGraphicalFeature parentNode) {
		return Lists.reverse(parentNode.getChildFeatures());
	}

	@Override
	public FXGraphicalFeature getFirstChild(FXGraphicalFeature parentNode) {
		return parentNode.getChildFeatures().get(0);
	}

	@Override
	public FXGraphicalFeature getLastChild(FXGraphicalFeature parentNode) {
		return parentNode.getChildFeatures().get(parentNode.getChildFeatures().size() - 1);
	}

}
