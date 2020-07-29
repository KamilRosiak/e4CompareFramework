package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.algorithms;

import java.util.List;

import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramm;
import FeatureDiagram.GraphicalFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.PlacementAlgorithm;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.tree.MinMax;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.tree.TreeUtil;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.elements.FXGraphicalFeature;

public class WalkersAlgorithm implements PlacementAlgorithm {
	private double height= 75d;
	private double distance = 20d;
	
	@Override
	public void format(FeatureDiagramm diagram) {
		Feature root = diagram.getRoot();
		TreeUtil.resetTreePosition(diagram);
		
		root.getGraphicalfeature().setX(0d);
		root.getGraphicalfeature().setY(25d);
		firstWalk(root, 1d);
		root.getGraphicalfeature().setX(TreeUtil.getChildrenMinMax(root).calculateMiddle() - root.getGraphicalfeature().getWidth()/2);
		//TreeUtil.shiftFeatureWithChildren(root, 100d);
	}
	/**
	 * Init x position for every feature.
	 */
	public void firstWalk(Feature node, double level) {
		node.getGraphicalfeature().setY(level * height);
		//If the node is a leaf
		if(!node.getChildren().isEmpty()) {
			MinMax midpoint = new MinMax(Double.MAX_VALUE, Double.MIN_VALUE);
			for(Feature child : node.getChildren()) {
				firstWalk(child, level + 1);
				
				if(child.getGraphicalfeature().getX() < midpoint.min) {
					midpoint.min = child.getGraphicalfeature().getX();
				}
				if(child.getGraphicalfeature().getX() + child.getGraphicalfeature().getWidth() > midpoint.max) {
					midpoint.max = child.getGraphicalfeature().getX() + child.getGraphicalfeature().getWidth();
				}
			}
			
			if(!TreeUtil.isMostLeftFeature(node)) {
				double x = midpoint.min + (midpoint.max - midpoint.min) / 2 - node.getGraphicalfeature().getWidth()/2;
				node.getGraphicalfeature().setX(x);
			

			} else {
				MinMax featureBounce = TreeUtil.getChildrenMinMax(node);
				double x = (featureBounce.max - featureBounce.min) / 2 - node.getGraphicalfeature().getWidth() / 2;
				node.getGraphicalfeature().setX(x);	
			}
			
		} else {
			
			if(!TreeUtil.isMostLeftFeature(node)) {
				GraphicalFeature mostRightSibling = TreeUtil.getRightMostSibling(node).getGraphicalfeature();
				MinMax featureBounce = TreeUtil.getChildrenMinMax(TreeUtil.getRightMostSibling(node));
				double distance = (featureBounce.max - featureBounce.min) / 2;
				node.getGraphicalfeature().setX(mostRightSibling.getX() + mostRightSibling.getWidth() + distance + this.distance);
				
			} else {
				if(node.getParent() != null) {
					boolean isLast = node.getParent().getChildren().indexOf(node) == node.getParent().getChildren().size() - 1;
					if(isLast) {
						Feature rightMost = TreeUtil.getRightMostSibling(node);
						MinMax rightMosMinMax = TreeUtil.getChildrenMinMax(rightMost);
						
						node.getGraphicalfeature().setX(rightMost.getGraphicalfeature().getX() + rightMosMinMax.calculateMiddle()/2 + this.distance - node.getGraphicalfeature().getWidth()/2);
					}
				} else {
					node.getGraphicalfeature().setX(0d);
				}
			}
		}
	}

	@Override
	public void format(FeatureDiagramm diagram, List<FXGraphicalFeature> featureList) {
		// TODO Auto-generated method stub
	}
	
}
