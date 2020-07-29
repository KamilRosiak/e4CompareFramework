package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.algorithms;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramm;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.placement.PlacementAlgorithm;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.tree.TreeUtil;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.elements.FXGraphicalFeature;

/**
 * A Layout Algorithm for m-ary Trees, Utilized for Feature Diagrams
 * This Algorithm Honors some of the Aesthetic Rules
 * @author Schlie
 *
 */
public class AestheticPlacement implements PlacementAlgorithm{
	
	private HashMap<FXGraphicalFeature, SizeInfo> fxMap = new HashMap<FXGraphicalFeature, SizeInfo>();
	private HashMap<Integer, List<FXGraphicalFeature>> layerMap = new HashMap<Integer, List<FXGraphicalFeature>>();
//	private HashMap<FXGraphicalFeature, List<FXGraphicalFeature>> fxChildren = new HashMap<FXGraphicalFeature, List<FXGraphicalFeature>>();
	
	private int maxDepth = 0;
	private final double VERTICAL_SPACER = 60d;
	List<FXGraphicalFeature> fxFeatures;
	
	
	
	@Override
	public void format(FeatureDiagramm diagram, List<FXGraphicalFeature> featureList) {
		
		fxFeatures = new ArrayList<FXGraphicalFeature>(featureList);
		
		ArrayList<Feature> features = new ArrayList<Feature>();
		features.add(diagram.getRoot());
		
		recursivelyPreProcessNodes(features, 0);

		processForLayouting();
		
		ArrayList<Double> offsets = new ArrayList<Double>();
		offsets.add(0d);
		
		redrawDiagram(features, offsets);
		
		DecimalFormat format = new DecimalFormat("#0.00");
		for (Map.Entry<Integer,List<FXGraphicalFeature>> entry : layerMap.entrySet()) {
			
			String output = "\n";
			for (FXGraphicalFeature f : entry.getValue()) {
				output+=f.getFeature().getName()+" mit \tTw:"+(format.format(fxMap.get(f).getTotalWidth()/41))
						+" | Rw: "+(format.format(fxMap.get(f).getRegularWidth()/41))+" | C: "+
						(format.format(fxMap.get(f).getCenter()/41))+" | Aw:"+(format.format(fxMap.get(f).getAlignmentWidth()/41))+
						" | LL:"+(format.format(fxMap.get(f).getLeftLoss()/41))+" | RL:"+(format.format(fxMap.get(f).getRightLoss()/41))+")\n";
			}
			System.out.print(entry.getKey()+output);
		}
	}
	
	
	/**
	 * Comment
	 * @param nodes
	 * @param depth
	 */
	public void recursivelyPreProcessNodes(List<Feature> nodes, int depth) {
		
		if (!layerMap.containsKey(depth)) {
			layerMap.put(depth, new ArrayList<FXGraphicalFeature>());
		}
		if (depth > maxDepth) {
			maxDepth = depth;
		}
		
		for (Feature node : nodes) {
			FXGraphicalFeature fxNode = getGraphicalFeature(node);
			SizeInfo fxNodeSizeInfo = new SizeInfo();
			fxNodeSizeInfo.setRegularWidth(fxNode.getWidth());
			fxNodeSizeInfo.setDepth(depth);
			fxNodeSizeInfo.setYPosition(depth*VERTICAL_SPACER);
			fxMap.put(fxNode, fxNodeSizeInfo);
			layerMap.get(depth).add(fxNode);
			
			if (!node.getChildren().isEmpty()) {
				recursivelyPreProcessNodes(node.getChildren(), depth+1);
			}	
		}
	}
	
	/**
	 * TODO comment
	 */
	public void processForLayouting() {
		
		for (int i = maxDepth; i>=0; i--) {
			
			for (FXGraphicalFeature fx : layerMap.get(i)) {
				SizeInfo currentFX = fxMap.get(fx);
				if(fx.getChildFeatures().isEmpty()) {
					currentFX.setTotalWidth(fx.getWidth());
					currentFX.setCenter(currentFX.getTotalWidth()/2);
					currentFX.setAlignmentWidth((currentFX.getCenter()+(currentFX.getRegularWidth())/2));
				}
				else {
					double totalWidth = 0d;
					double combinedChildAlignmentWidth = 0d;
					for (FXGraphicalFeature fxChildFeature : fx.getChildFeatures()) {
						SizeInfo childFX = fxMap.get(fxChildFeature);
						totalWidth += childFX.getTotalWidth();
						if (TreeUtil.isMostLeftFeature(fxChildFeature.getFeature())){
							combinedChildAlignmentWidth += childFX.getCenter()+(childFX.getRegularWidth()/2);
						}
						else if(TreeUtil.isMostRightFeature(fxChildFeature.getFeature())) {
							combinedChildAlignmentWidth += childFX.getCenter()+(childFX.getRegularWidth()/2);
						}

						else {
							combinedChildAlignmentWidth += childFX.getAlignmentWidth();	
						}
						
					}
					if (fx.getWidth() < totalWidth) {
						currentFX.setTotalWidth(totalWidth);			
						currentFX.setCenter(combinedChildAlignmentWidth / 2);
						currentFX.setAlignmentWidth(currentFX.getCenter()+(currentFX.getRegularWidth()/2));	
						currentFX.setLeftLoss((combinedChildAlignmentWidth/2)-(currentFX.getRegularWidth() / 2));
						currentFX.setRightLoss((combinedChildAlignmentWidth/2)-(currentFX.getRegularWidth() / 2));
//						currentFX.setLeftLoss(combinedLeftLoss);
//						currentFX.setRightLoss(combinedRightLoss);
						
						
					}
					else {
						currentFX.setTotalWidth(fx.getWidth());			
						currentFX.setCenter(currentFX.getTotalWidth() / 2);
						currentFX.setAlignmentWidth(currentFX.getRegularWidth());
						currentFX.resetLeftLoss();
						currentFX.resetRightLoss();
					}
				}
			}
		}
	}

	/**
	 * TODO comment
	 */
	public void redrawDiagram(List<Feature> nodes, ArrayList<Double> xOffSet) {
		
		for (Feature node : nodes) {
			
			FXGraphicalFeature fx = getGraphicalFeature(node);
			
			double y = fxMap.get(fx).getYPosition();
			double x = 0d;
			
			if (xOffSet.size() > fxMap.get(fx).getDepth()) {
				 x = (fxMap.get(fx).getTotalWidth() / 2)+xOffSet.get(fxMap.get(fx).getDepth());
				 xOffSet.set(fxMap.get(fx).getDepth(), xOffSet.get(fxMap.get(fx).getDepth())+fxMap.get(fx).getTotalWidth());
			}
			else {
				x = (fxMap.get(fx).getTotalWidth() / 2);
				xOffSet.add(fxMap.get(fx).getDepth(), fxMap.get(fx).getTotalWidth());
			}
				
			
			
			
			node.getGraphicalfeature().setY(y);
			node.getGraphicalfeature().setX(x);
			
			if (!node.getChildren().isEmpty()) {
				redrawDiagram(node.getChildren(), xOffSet);
			}
			else {
				
			}
			
		}
			

	}
	

	/**
	 * TODO DUPLICATE
	 * This method returns the FXGraphicalFeature corresponding to the given Feature.
	 */
	public FXGraphicalFeature getGraphicalFeature(Feature feature) {
		for(FXGraphicalFeature graphicalFeature : fxFeatures) {
			if(graphicalFeature.getFeature().equals(feature)) {
				return graphicalFeature;
			}
		}
		return null;
	}

	@Override
	public void format(FeatureDiagramm diagram) {
		format(diagram, null);
	}

	
	/**
	 * Private Class to Hold Temporary Size Information Required by this Layout Algorithm
	 * @author Schlie
	 *
	 */
	class SizeInfo {
		private double width = 0;
		private double totalwidth = 0;
		private double center = 0;
		private double alignmentWidth = 0;
		private int depth = 0;
		
		private double leftLoss = 0;
		private double rightLoss = 0;
		

		private double yPosition = 0;
		
		
		private void setRegularWidth(double w) {
			width = w;
		}
		private void setTotalWidth(double tw) {
			totalwidth += tw;
		}
		private void setCenter(double c) {
			center = c;
		}
		private void setDepth(int d) {
			depth = d;
		}
		private void setAlignmentWidth(double cw) {
			alignmentWidth = cw;
		}
		
		private void setYPosition(double y) {
			yPosition = y;
		}
		private double getRegularWidth() {
			return width;
		}
		private double getTotalWidth() {
			return totalwidth;
		}
		private double getCenter() {
			return center;
		}
		private int getDepth() {
			return depth;
		}
		private double getAlignmentWidth() {
			return alignmentWidth;
		}

		private double getYPosition() {
			return yPosition;
		}
		private void setRightLoss(double rl) {
			rightLoss += rl;
		}
		private void setLeftLoss(double ll) {
			leftLoss += ll;
		}
		private void resetLeftLoss() {
			leftLoss = 0d;
		}
		private void resetRightLoss() {
			rightLoss = 0;
		}
		private double getLeftLoss() {
			return leftLoss;
		}
		private double getRightLoss() {
			return rightLoss;
		}
	}
	

}
