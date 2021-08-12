/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.util;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.VariantTaxonomyNode;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.graph.ArtifactComparison;

/**
 * @author developer-olan
 *
 */
public class TaxonomyEvaluator {
	private VariantTaxonomyNode groundTruthTaxonomy;
	private VariantTaxonomyNode taxonomyToCompare;
	private int numberOfVariants;
	
	// Primary Taxonomy performance Measures
	private float truePositivesValue;
	private float trueNegativesValue;
	private float falsePositivesValue;
	private float falseNegativesValue;
	
	// Secondary Taxonomy performance Measures
	private float truePositivesRate;
	private float trueNegativesRate;
	private float falsePositivesRate;
	private float falseNegativesRate;
	
	private float recallValue;
	private float precisionValue;
	private float accuracyValue;
	private float errorRateValue;
	
	private float falseAcceptanceFPRValue;
	private float missRateTNRValue;
	
	
	// Ground Truth Properties
	private int gtTotalNodes = 0;
	private int gtMaxDepth;
	private int levelMeasureTotal;
	
	public void computeDifferences() {
		int depth = 0;
		// Compute Depth and Total Number of Nodes 
		getGTTotalNodes(this.groundTruthTaxonomy);
		levelMeasureTotal = 0; 
		int levelMeasure = gtTotalNodes;
		for (int i = 0; i <= gtMaxDepth; i++) {
			int tp = 0;
			int tn = 0;
			int fp = 0;
			int fn = 0;
			
			
			List<VariantTaxonomyNode> gtNodesAtLevel = getNodesForLevel(this.groundTruthTaxonomy, i);
			List<VariantTaxonomyNode> ctNodesAtLevel = getNodesForLevel(this.taxonomyToCompare, i);
			
			for (VariantTaxonomyNode gtNodesOnLvl: gtNodesAtLevel) {
				if (! isVariantInList(gtNodesOnLvl, ctNodesAtLevel)) {
					fn += 1;
				} else {
					tp += 1;
				}
			}
			
			for (VariantTaxonomyNode ctNodesOnLvl: ctNodesAtLevel) {
				if (! isVariantInList(ctNodesOnLvl, gtNodesAtLevel)) {
					// Compared List not in ground truth
					fp += 1;
				}
			}
			
			tn = levelMeasure - (tp+ fp + fn) > 0 ? gtTotalNodes - (tp+ fp + fn) : 0;
			
			truePositivesValue += tp; 
			trueNegativesValue += tn; 
			falsePositivesValue += fp; 
			falseNegativesValue += fn; 
			
			levelMeasure -= gtNodesAtLevel.size();
		}
	}
	
	
	public boolean isVariantInList(VariantTaxonomyNode searchNode, List<VariantTaxonomyNode> nodesAtLevel) {
		boolean isVariantInList = false;
		for (VariantTaxonomyNode aMemberNode : nodesAtLevel) {
			if (aMemberNode.getVariantName().equals(searchNode.getVariantName())) {
				if (aMemberNode.getVariantParent() == null && searchNode.getVariantParent() == null) {
					isVariantInList = true;
					break;
				} else if (aMemberNode.getVariantParent() != null && searchNode.getVariantParent() != null) {
					if (aMemberNode.getVariantParent().getVariantName().equals(searchNode.getVariantParent().getVariantName())) 
					{
						isVariantInList = true;
						break;
					}
				}
				
			}
		}
		
		return isVariantInList;
	}
	
	public List<VariantTaxonomyNode> getNodesForLevel(VariantTaxonomyNode rootNode, int level) {
		List<VariantTaxonomyNode> nodesAtLevel =  new ArrayList<VariantTaxonomyNode>();
		getNodesForLevelRecursive(rootNode, level, nodesAtLevel);
		return nodesAtLevel;
	}
	
	public void getNodesForLevelRecursive(VariantTaxonomyNode node, int level, List<VariantTaxonomyNode> nodesAtLevel) {
		if (node.getTreeDepth() == level) {
			nodesAtLevel.add(node);
		}
		else if (node.getVariantChildren().size() > 0 && node.getTreeDepth() < level) {
			 for (VariantTaxonomyNode aChildVariantNode : node.getVariantChildren()) {
				 getNodesForLevelRecursive(aChildVariantNode, level, nodesAtLevel);
			 }
		}
	}
	/**
	 * Gets the total number of Nodes in the as well as the highest depth
	 */
	public void getGTTotalNodes(VariantTaxonomyNode taxonomyNode) {
		gtTotalNodes++;
		 if (taxonomyNode.getVariantChildren().size() > 0) {
			 for (VariantTaxonomyNode aChildVariantNode : taxonomyNode.getVariantChildren()) {
				if (aChildVariantNode.getTreeDepth() > this.gtMaxDepth) {
					this.gtMaxDepth = aChildVariantNode.getTreeDepth();
				}
				getGTTotalNodes(aChildVariantNode);
			 }
		  }
	}
	
	/**
	 * 
	 */
	public TaxonomyEvaluator(VariantTaxonomyNode _groundTruth) {
		this.groundTruthTaxonomy = _groundTruth;
	}
	
	public TaxonomyEvaluator(VariantTaxonomyNode _groundTruthTaxonomy, VariantTaxonomyNode _compTaxonomy) {
		this.groundTruthTaxonomy = _groundTruthTaxonomy;
		this.taxonomyToCompare = _compTaxonomy;
	}
	
	
	public void printMeasures() {
		System .out.println("truePositivesValue"+ truePositivesValue);
		System .out.println("trueNegativesValue"+ trueNegativesValue);
		System .out.println("falsePositivesValue"+ falsePositivesValue);
		System .out.println("falseNegativesValue"+ falseNegativesValue);
		System .out.println("Total: "+ (truePositivesValue + trueNegativesValue + falsePositivesValue + falseNegativesValue));

		System .out.println("Accuracy: "+ accuracyValue);
		System .out.println("Error: "+ errorRateValue);
		System .out.println("Recall: "+ recallValue);
		System .out.println("Precision: "+ precisionValue);
		
		System .out.println("FalseAcceptanceFPRValue: "+ falseAcceptanceFPRValue);
		System .out.println("MissRateTNRValue: "+ missRateTNRValue);
	}
	
public void calculateSecondaryMeasures() {
		
		recallValue = truePositivesValue / (truePositivesValue + falseNegativesValue);
		precisionValue = truePositivesValue / (truePositivesValue + falsePositivesValue);
		accuracyValue = (truePositivesValue + trueNegativesValue) / (truePositivesValue + falsePositivesValue + trueNegativesValue + falseNegativesValue);
		errorRateValue =  1- accuracyValue;
		
		falseAcceptanceFPRValue = falsePositivesValue / (falsePositivesValue + trueNegativesValue);
		missRateTNRValue = falseNegativesValue / (truePositivesValue + falseNegativesValue);
	}
	
	public void setComparedTaxonomy(VariantTaxonomyNode _comparedTaxonomy) {
		this.taxonomyToCompare = _comparedTaxonomy;
	}
	
	public VariantTaxonomyNode getGroundtruthTaxonomy() {
		return this.groundTruthTaxonomy;
	}
	
	public VariantTaxonomyNode getComparedTaxonomy() {
		return this.taxonomyToCompare;
	}
	
	// Ground Truth Creators
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createProjectExampleGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("stmicsBasicStackVariant.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("stmicsOverflowStackVariant.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("stmicsLoggingStackVariant.java", 2, level1ChildNode);
		
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);
		
		
		return rootNode;
	}
	
	/**
	 * Creates Ground Truth VariantTaxonomy Tree/Graph
	 * @return <VariantTaxonomyNode>
	 */
	public static VariantTaxonomyNode createGoogleCodeJamGT() {
		VariantTaxonomyNode rootNode = new VariantTaxonomyNode("2016-2-A-rank-3-eatmore.java", 0, null);
		VariantTaxonomyNode level1ChildNode = new VariantTaxonomyNode("2016-3-A-rank-6-eatmore.java", 1, rootNode);
		VariantTaxonomyNode level2ChildNode = new VariantTaxonomyNode("2016-FR-A-rank-4-eatmore.java", 2, level1ChildNode);
		
		level1ChildNode.addChildNode(level2ChildNode);
		rootNode.addChildNode(level1ChildNode);
		
		return rootNode;
	}
	
	// 
	
	// Primary Measures' Getters
	public float getTruePositivesValue() {
		return this.truePositivesValue;
	}
	
	public float getTrueNegativesValue() {
		return this.trueNegativesValue;
	}
	
	public float getFalsePositivesValue() {
		return this.falsePositivesValue;
	}
	
	public float getFalseNegativesValue() {
		return this.falseNegativesValue;
	}
	
	
	// Secondary Measures' Getters
	public float getRecallValue() {
		return this.recallValue;
	}
	
	public float getPrecisionValue() {
		return this.precisionValue;
	}
	
	public float getAccuracyValue() {
		return this.accuracyValue;
	}
	
	public float getErrorRateValue() {
		return this.errorRateValue;
	}

	public float getfalseAcceptanceFPRValue() {
		return this.falseAcceptanceFPRValue;
	}
	
	public float getMissRateTNRValue() {
		return this.missRateTNRValue;
	}


}
