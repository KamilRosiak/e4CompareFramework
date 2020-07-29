package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.algorithms;

import java.util.List;

import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramm;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.PlacementAlgorithm;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.tree.MinMax;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.tree.TreeUtil;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.elements.FXGraphicalFeature;

public class RecursivelyPlacement implements PlacementAlgorithm {
	private static final double UPPER_GAP = 25d;
	private static final double DISTANCE_ON_Y = 75d;
	private static final double DISTANCE_ON_X = 20d;
	private double nextX = 0;
	
	@Override
	public void format(FeatureDiagramm diagram, List<FXGraphicalFeature> featureList) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void format(FeatureDiagramm diagram) {
		Feature root = diagram.getRoot();
		TreeUtil.resetTreePosition(diagram);
		
		root.getGraphicalfeature().setX(0d);
		root.getGraphicalfeature().setY(UPPER_GAP);
		walkTree(root, null, 1d);
	}
	
	
	
	public void walkTree(Feature node, Feature prev, double level) {
		//set y position
		node.getGraphicalfeature().setY(level * DISTANCE_ON_Y + UPPER_GAP);
		//first node in List
		if(prev == null) {
			//the most left nodes that are not leafs.
			if(!TreeUtil.isLeaf(node)) {
				Feature prevChild = null;
				for(Feature child : node.getChildren()) {
					walkTree(child, prevChild, level + 1);
					prevChild = child;
				}
				//center node in the middle of his children
				MinMax nodeBounce = TreeUtil.getSubTreebounce(node);
				double nodeX = nodeBounce.calculateMiddle() - node.getGraphicalfeature().getWidth()/2;
				node.getGraphicalfeature().setX(nodeX);
				
				if(nextX < nodeBounce.max ) {
					nextX = nodeBounce.max + DISTANCE_ON_X;
				}
			} else {
				//the most left leaf
				node.getGraphicalfeature().setX(nextX);
			}
		} else {
			if(!TreeUtil.isLeaf(node)) {
				Feature prevChild = null;
				for(Feature child : node.getChildren()) {
					walkTree(child, prevChild, level + 1);
					prevChild = child;
				}
				
				MinMax nodeBounce = TreeUtil.getSubTreebounce(node);
				double nodeX = nodeBounce.calculateMiddle() - node.getGraphicalfeature().getWidth()/2;

				node.getGraphicalfeature().setX(nodeX);
				
				if(nextX < nodeBounce.max ) {
					nextX = nodeBounce.max;
				}
				
			} else {
				MinMax prevBounce = TreeUtil.getSubTreebounce(prev);
				double nodeX = prevBounce.max + DISTANCE_ON_X;
				node.getGraphicalfeature().setX(nodeX);
				
				if(nextX < nodeX ) {
					nextX = nodeX;
				}
			}
		}
	}
}
