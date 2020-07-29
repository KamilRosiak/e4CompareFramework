package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.abego_tree_layout;



import java.util.Collections;
import java.util.List;

import org.abego.treelayout.TreeForTreeLayout;

import com.google.common.collect.Lists;

import FeatureDiagram.Feature;

public class FDTreeForLayout implements TreeForTreeLayout<Feature> {
	private Feature root;
	
	public FDTreeForLayout(Feature root) {
		this.root = root;
	}
	
	@Override
	public Iterable<Feature> getChildren(Feature feature) {
		return getVisibleChildren(feature);
	}

	@Override
	public Iterable<Feature> getChildrenReverse(Feature feature) {
		return Lists.reverse(getVisibleChildren(feature));
	}

	@Override
	public Feature getFirstChild(Feature feature) {
		return getVisibleChildren(feature).get(0);
	}

	@Override
	public Feature getLastChild(Feature feature) {
		List<Feature> visibleChildren = getVisibleChildren(feature);
		return visibleChildren.get(visibleChildren.size()-1);
	}

	@Override
	public Feature getRoot() {
		return root;
	}

	@Override
	public boolean isChildOfParent(Feature node, Feature parent) {
		if (getVisibleChildren(parent).contains(node)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isLeaf(Feature feature) {
		if (getVisibleChildren(feature).isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Collects visible subset of the children. A feature's children are visible if it isn't hiddens.   
	 * 
	 * @param feature
	 * @return
	 */
	public List<Feature> getVisibleChildren(Feature feature) {
		if (!feature.isHidden()) {
			return feature.getChildren();		
		} else {
			return Collections.emptyList();
		}
	}
}
