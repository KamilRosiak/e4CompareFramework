package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.algorithms;

import java.awt.geom.Rectangle2D.Double;
import java.util.List;
import java.util.Map;

import org.abego.treelayout.Configuration;
import org.abego.treelayout.Configuration.Location;
import org.abego.treelayout.NodeExtentProvider;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;

import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramm;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.feature.FXGraphicalFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.PlacementAlgorithm;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.abego_tree_layout.FDConfiguration;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.abego_tree_layout.FDNodeExtentProvider;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.abego_tree_layout.FDTreeForLayout;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.tree.TreeUtil;

public class AbegoPlacement implements PlacementAlgorithm {

	@Override
	public void format(FeatureDiagramm diagram, List<FXGraphicalFeature> featureList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void format(FeatureDiagramm root) {
		TreeUtil.resetTreePosition(root);
		TreeForTreeLayout<Feature> tree = new FDTreeForLayout(root.getRoot());
		NodeExtentProvider<Feature> nodeExtend = new FDNodeExtentProvider();
		Configuration<Feature> config = new FDConfiguration(Location.Top);
		TreeLayout<Feature> treeLayout = new TreeLayout<Feature>(tree, nodeExtend, config);
		Map<Feature, Double> featurePositionMap =  treeLayout.getNodeBounds();
		TreeUtil.setFeaturePosition(root.getRoot(), featurePositionMap);
	}
	
	@Override
	public void format(FXGraphicalFeature root) {
		int width = 0;
		for (int i = 0; i < root.getChildFeatures().size(); i++) {
			FXGraphicalFeature child = root.getChildFeatures().get(i);
			child.setPosition(width, root.xPos.doubleValue() + 30);
			format(child);
			width += child.getWidth() + 10;
		}
	}
}
