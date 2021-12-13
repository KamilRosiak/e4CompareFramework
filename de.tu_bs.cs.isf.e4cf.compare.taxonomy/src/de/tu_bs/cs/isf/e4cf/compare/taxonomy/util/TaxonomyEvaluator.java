/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.util;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.VariantTaxonomyNode;

/**
 * @author developer-olan
 *
 */
public class TaxonomyEvaluator {
	private VariantTaxonomyNode groundTruthTaxonomy;
	private VariantTaxonomyNode computedTaxonomy;

	// Primary Taxonomy performance Measures
	private float truePositivesValue;
	private float trueNegativesValue = 0;
	private float falsePositivesValue = 0;
	private float falseNegativesValue = 0;

	// Secondary Taxonomy performance Measures
	private float recallValue;
	private float precisionValue;
	private float accuracyValue;
	private float errorRateValue;

	private float falseAcceptanceFPRValue;
	private float missRateTNRValue;

	// Custom Taxonomy performance measures
	private float RVPA; // Root Variant Prediction Accuracy
	private float TDA; // Tree Depth Accuracy
	private float PCRA; // Parent-Child Relation Accuracy
	private float VPLA; // Variants Per Level Accuracy
	private float NDM; // Node Displacement Measure

	// Ground Truth Properties
	private int gtTotalNodes = 0;
	private int gtMaxDepth;
	// private int levelMeasureTotal;

	// Ground Truth Properties
	private int ctTotalNodes = 0;
	private int ctMaxDepth;

	/**
	 * Compute Primary and Secondary metrics measures for taxonomies
	 */
	public void computePrimaryMeasures() {
		// this.levelMeasureTotal = 0;
//		int levelMeasure = gtTotalNodes;

		for (int i = 0; i <= gtMaxDepth; i++) {
			int tp = 0;
			int tn = 0;
			int fp = 0;
			int fn = 0;

			List<VariantTaxonomyNode> gtNodesAtLevel = getNodesForLevel(this.groundTruthTaxonomy, i);
			List<VariantTaxonomyNode> ctNodesAtLevel = getNodesForLevel(this.computedTaxonomy, i);

			for (VariantTaxonomyNode gtNodesOnLvl : gtNodesAtLevel) {
				if (!isVariantInList(gtNodesOnLvl, ctNodesAtLevel)) {
					fn += 1;
				} else {
					tp += 1;
				}
			}

			for (VariantTaxonomyNode ctNodesOnLvl : ctNodesAtLevel) {
				if (!isVariantInList(ctNodesOnLvl, gtNodesAtLevel)) {
					// Compared List not in ground truth
					fp += 1;
				}
			}

			// tn = levelMeasure - (tp + fp + fn) > 0 ? levelMeasure - (tp + fp + fn) : 0;
			tn = 0;

			truePositivesValue += tp;
			trueNegativesValue += tn;
			falsePositivesValue += fp;
			falseNegativesValue += fn;

//			levelMeasure -= gtNodesAtLevel.size();
		}
	}

	/**
	 * Compute Primary metrics measures for the taxonomy accuracy using Predecessors
	 * and Successors prediction accuracy
	 */
	@SuppressWarnings("unused")
	public void computePrimaryMeasuresPS() {
		// Compute Depth and Total Number of Nodes
		List<VariantTaxonomyNode> allGTNodes = new ArrayList<VariantTaxonomyNode>(); // All Ground Truth Nodes
		List<VariantTaxonomyNode> allCTNodes = new ArrayList<VariantTaxonomyNode>(); // All Computes Taxonomy Nodes

		for (int i = 0; i <= gtMaxDepth; i++) {
			List<VariantTaxonomyNode> gtNodesAtLevel = getNodesForLevel(this.groundTruthTaxonomy, i);
			List<VariantTaxonomyNode> ctNodesAtLevel = getNodesForLevel(this.computedTaxonomy, i);
			// Flatten Nodes
			allGTNodes.addAll(gtNodesAtLevel);
			allCTNodes.addAll(ctNodesAtLevel);
						
		}

		for (VariantTaxonomyNode computedNode : allCTNodes) {
			int pTP = 0, sTP = 0;
			int pTN = 0, sTN = 0;
			int pFP = 0, sFP = 0;
			int pFN = 0, sFN = 0;

			// Get Successors for Node - Computed taxonomy
			List<VariantTaxonomyNode> nodeCTSuccessors = getNodeSuccessors(computedNode);
			// Get Predecessors for Node - Computed taxonomy
			List<VariantTaxonomyNode> nodeCTPredecessors = getNodePredecessors(computedNode);

			// Get Successors for Node - Computed taxonomy
			List<VariantTaxonomyNode> nodeGTSuccessors = getNodeSuccessors(
					fetchVariantInList(computedNode, allGTNodes));
			// Get Predecessors for Node - Computed taxonomy
			List<VariantTaxonomyNode> nodeGTPredecessors = getNodePredecessors(
					fetchVariantInList(computedNode, allGTNodes));

			// Compute TP, TN, FP, FN for successors
			for (VariantTaxonomyNode aNodeGTSuccessor : nodeGTSuccessors) {
				if (!isVariantInList(aNodeGTSuccessor, nodeCTSuccessors)) {
					sFN += 1;
				} else {
					sTP += 1;
				}
			}

			for (VariantTaxonomyNode aNodeCTSuccessor : nodeCTSuccessors) {
				if (!isVariantInList(aNodeCTSuccessor, nodeGTSuccessors)) {
					// Compared List not in ground truth
					sFP += 1;
				}
			}

			// Compute TP, TN, FP, FN for predecessors
			for (VariantTaxonomyNode aNodeGTPredecessors : nodeGTPredecessors) {
				if (!isVariantInList(aNodeGTPredecessors, nodeCTPredecessors)) {
					pFN += 1;
				} else {
					pTP += 1;
				}
			}

			for (VariantTaxonomyNode aNodeCTPredecessors : nodeCTPredecessors) {
				if (!isVariantInList(aNodeCTPredecessors, nodeGTPredecessors)) {
					// Compared List not in ground truth
					pFP += 1;
				}
			}

			pTN = nodeGTPredecessors.size() - (pTP + pFP + pFN) > 0 ? nodeGTPredecessors.size() - (pTP + pFP + pFN) : 0;
			sTN = nodeGTSuccessors.size() - (sTP + sFP + sFN) > 0 ? nodeGTSuccessors.size() - (sTP + sFP + sFN) : 0;

			truePositivesValue += (pTP + sTP);
			trueNegativesValue += (pTN + sTN);
			falsePositivesValue += (pFP + sFP);
			falseNegativesValue += (pFN + sFN);

//					truePositivesValue += (pTP);
//					trueNegativesValue += (pTN);
//					falsePositivesValue += (pFP);
//					falseNegativesValue += (pFN);
		}

	}

	/**
	 * Get Nodes which are Successors of a particular Node
	 * 
	 * @param givenNode
	 * @return
	 */
	public List<VariantTaxonomyNode> getNodeSuccessors(VariantTaxonomyNode givenNode) {
		List<VariantTaxonomyNode> foundNodes = new ArrayList<VariantTaxonomyNode>();
		if (givenNode != null) {
			for (VariantTaxonomyNode aChildNode : givenNode.getVariantChildren()) {
				foundNodes.add(aChildNode);
				if (aChildNode.getVariantChildren().size() > 0) {
					foundNodes.addAll(getNodeSuccessors(aChildNode));
				}
			}
		}

		return foundNodes;
	}

	/**
	 * Get Nodes which are predecessors of a particular Node
	 * 
	 * @param givenNode
	 * @return
	 */
	public List<VariantTaxonomyNode> getNodePredecessors(VariantTaxonomyNode givenNode) {
		List<VariantTaxonomyNode> foundNodes = new ArrayList<VariantTaxonomyNode>();
		if (givenNode != null) {
			VariantTaxonomyNode givenNodeParent = givenNode.getVariantParent();
			if (givenNodeParent != null) {
				foundNodes.add(givenNodeParent);
				if (givenNodeParent.getVariantParent() != null) {
					foundNodes.addAll(getNodePredecessors(givenNodeParent));
				}
			}
		}

		return foundNodes;
	}

	/**
	 * Compute Custom Metrics for Table
	 */
	public void computeCustomMeasures() {
		// Compute Depth and Total Number of Nodes
		List<VariantTaxonomyNode> allGTNodes = new ArrayList<VariantTaxonomyNode>(); // All Ground Truth Nodes
		List<VariantTaxonomyNode> allCTNodes = new ArrayList<VariantTaxonomyNode>(); // All Computes Taxonomy Nodes

		// Initialize Custom Secondary metrics
		this.RVPA = 0.0f; // Root Variant Prediction Accuracy
		this.TDA = 0.0f; // Tree Depth Accuracy
		this.PCRA = 0.0f; // Parent-Child Relation Accuracy
		this.VPLA = 0.0f;// Variants Per Level Accuracy
		this.NDM = 0.0f; // Node Displacement Measure

		for (int i = 0; i <= gtMaxDepth; i++) {
			List<VariantTaxonomyNode> gtNodesAtLevel = getNodesForLevel(this.groundTruthTaxonomy, i);
			List<VariantTaxonomyNode> ctNodesAtLevel = getNodesForLevel(this.computedTaxonomy, i);
			// Compute RVPA
			if (i == 0) {
				for (VariantTaxonomyNode gtNodeOnLvl : gtNodesAtLevel) {

					if (isVariantInList(gtNodeOnLvl, ctNodesAtLevel)) {
						RVPA += 1.0f;
					}

				}

				RVPA = RVPA / (float) gtNodesAtLevel.size();
			}

			// Sum VPLA
			if (gtNodesAtLevel.size() == ctNodesAtLevel.size()) {
				VPLA += 1.0f;
			}

			// Flatten Nodes
			allGTNodes.addAll(gtNodesAtLevel);
			allCTNodes.addAll(ctNodesAtLevel);
		}

		// Compute VPLA
		VPLA = VPLA / ((float) gtMaxDepth + 1.0f);

		// Compute TDA
		if (gtTotalNodes == ctTotalNodes) {
			this.TDA = 1.0f;
		}

		// Compute PCRA & NDM
		for (VariantTaxonomyNode aGTNode : allGTNodes) {
			VariantTaxonomyNode foundNode = fetchVariantInList(aGTNode, allCTNodes);
			if (!(foundNode == null)) {
				float NDMParentSim = 0;
				float NDMDepthSim = 0;
				// SUM PCRA
				if ((aGTNode.getVariantParent() == null && foundNode.getVariantParent() == null)) {
					this.PCRA += 1.0f;
					NDMParentSim = 1.0f * 0.6f;
					if (aGTNode.getTreeDepth() == foundNode.getTreeDepth()) {
						NDMDepthSim = 1.0f * 0.4f;
					}

					this.NDM += NDMParentSim + NDMDepthSim;
				} else if (aGTNode.getVariantParent() != null && foundNode.getVariantParent() != null) {
					if (aGTNode.getVariantParent().getVariantName()
							.equals(foundNode.getVariantParent().getVariantName())) {
						this.PCRA += 1.0f;
						NDMParentSim = 1.0f * 0.6f;
						if (aGTNode.getTreeDepth() == foundNode.getTreeDepth()) {
							NDMDepthSim = 1.0f * 0.4f;
						}

						this.NDM += NDMParentSim + NDMDepthSim;
					}
				}
			}
		}

		// Compute PCRA
		PCRA = PCRA / (float) allGTNodes.size();

		// Compute NDM
		NDM = 1.0f - (NDM / (float) allGTNodes.size());
	}

	/**
	 * Searches for a particular node in a list of Nodes
	 * 
	 * @param searchNode
	 * @param nodesAtLevel
	 * @return
	 */
	public boolean isVariantInList(VariantTaxonomyNode searchNode, List<VariantTaxonomyNode> nodesAtLevel) {
		boolean isVariantInList = false;
		for (VariantTaxonomyNode aMemberNode : nodesAtLevel) {
			if (aMemberNode.getVariantName().equals(searchNode.getVariantName())) {

//				if (aMemberNode.getVariantParent() == null && searchNode.getVariantParent() == null) {
//					isVariantInList = true;
//					break;
//				} else if (aMemberNode.getVariantParent() != null && searchNode.getVariantParent() != null) {
//					if (aMemberNode.getVariantParent().getVariantName()
//							.equals(searchNode.getVariantParent().getVariantName())) {
//						isVariantInList = true;
//						break;
//					}
//				}

				isVariantInList = true;
				break;

			}
		}

		return isVariantInList;
	}

	/**
	 * Searches for a particular node in a list of Nodes
	 * 
	 * @param searchNode
	 * @param nodesAtLevel
	 * @return
	 */
	public VariantTaxonomyNode fetchVariantInList(VariantTaxonomyNode searchNode,
			List<VariantTaxonomyNode> listToSearch) {
		VariantTaxonomyNode isVariantInList = null;
		for (VariantTaxonomyNode aMemberNode : listToSearch) {
			if (aMemberNode.getVariantName().equals(searchNode.getVariantName())) {
				isVariantInList = aMemberNode;
				break;
			}
		}

		return isVariantInList;
	}

	/**
	 * Gets Nodes on a particular level of the supplied taxonomy tree
	 * 
	 * @param rootNode
	 * @param level
	 * @return
	 */
	public List<VariantTaxonomyNode> getNodesForLevel(VariantTaxonomyNode rootNode, int level) {
		List<VariantTaxonomyNode> nodesAtLevel = new ArrayList<VariantTaxonomyNode>();
		nodesAtLevel.addAll(getNodesForLevelRecursive(rootNode, level));
		return nodesAtLevel;
	}

	/**
	 * Recursively fetches Nodes on a particular level of the supplied taxonomy tree
	 * 
	 * @param node
	 * @param level
	 * @param nodesAtLevel
	 */
	public List<VariantTaxonomyNode> getNodesForLevelRecursive(VariantTaxonomyNode node, int level) {
		List<VariantTaxonomyNode> nodesAtLevel = new ArrayList<VariantTaxonomyNode>();
		if (node.getTreeDepth() == level) {
			nodesAtLevel.add(node);
		} else if (node.getVariantChildren().size() > 0 && node.getTreeDepth() < level) {
			for (VariantTaxonomyNode aChildVariantNode : node.getVariantChildren()) {
				nodesAtLevel.addAll(getNodesForLevelRecursive(aChildVariantNode, level));
			}
		}
		return nodesAtLevel;
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
	 * Gets the total number of Nodes in the as well as the highest depth
	 */
	public void getCTTotalNodes(VariantTaxonomyNode taxonomyNode) {
		ctTotalNodes++;
		if (taxonomyNode.getVariantChildren().size() > 0) {
			for (VariantTaxonomyNode aChildVariantNode : taxonomyNode.getVariantChildren()) {
				if (aChildVariantNode.getTreeDepth() > this.ctMaxDepth) {
					this.ctMaxDepth = aChildVariantNode.getTreeDepth();
				}
				getCTTotalNodes(aChildVariantNode);
			}
		}

	}

	/**
	 * Evaluator Constructor with ground truth only
	 */
	public TaxonomyEvaluator(VariantTaxonomyNode _groundTruth) {
		this.groundTruthTaxonomy = _groundTruth;
	}

	/**
	 * Taxonomy Evaluator Constructor
	 * 
	 * @param _groundTruthTaxonomy
	 * @param _compTaxonomy
	 */
	public TaxonomyEvaluator(VariantTaxonomyNode _groundTruthTaxonomy, VariantTaxonomyNode _compTaxonomy) {
		this.groundTruthTaxonomy = _groundTruthTaxonomy;
		//workaround if engine could not find taxonomy
		this.computedTaxonomy = _compTaxonomy != null ? _compTaxonomy : new VariantTaxonomyNode("");
		// Compute Depth and Total Number of Nodes
		getGTTotalNodes(this.groundTruthTaxonomy);		
		getCTTotalNodes(this.computedTaxonomy);
		
		
	}

	public void printMeasures() {
		System.out.println("======================");
		System.out.println("Total # of Nodes: " + (gtTotalNodes));
		System.out.println("truePositivesValue" + truePositivesValue);
		System.out.println("trueNegativesValue" + trueNegativesValue);
		System.out.println("falsePositivesValue" + falsePositivesValue);
		System.out.println("falseNegativesValue" + falseNegativesValue);
		System.out.println(
				"Total: " + (truePositivesValue + trueNegativesValue + falsePositivesValue + falseNegativesValue));
		System.out.println("======================");
		System.out.println("Precision: " + precisionValue);
		System.out.println("Recall: " + recallValue);
		System.out.println("FalseAcceptanceFPRValue: " + falseAcceptanceFPRValue);
		System.out.println("MissRateTNRValue: " + missRateTNRValue);
		System.out.println("Accuracy: " + accuracyValue);
		System.out.println("Error: " + errorRateValue);
	}

	public void printCustomMeasures() {
		System.out.println("======================");
		System.out.println("RVPA: " + this.RVPA);
		System.out.println("TDA: " + this.TDA);
		System.out.println("PCRA: " + this.PCRA);
		System.out.println("VPLA: " + this.VPLA);
		System.out.println("NDM: " + this.NDM);
	}

	public void calculateSecondaryMeasures() {

		recallValue = truePositivesValue
				/ ((truePositivesValue + falseNegativesValue) == 0 ? 1.0f : (truePositivesValue + falseNegativesValue));
		precisionValue = truePositivesValue
				/ ((truePositivesValue + falsePositivesValue) == 0 ? 1.0f : (truePositivesValue + falsePositivesValue));
		accuracyValue = (truePositivesValue + trueNegativesValue)
				/ (truePositivesValue + falsePositivesValue + trueNegativesValue + falseNegativesValue);
		errorRateValue = 1 - accuracyValue;

		falseAcceptanceFPRValue = falsePositivesValue
				/ ((falsePositivesValue + trueNegativesValue) == 0 ? 1.0f : (falsePositivesValue + trueNegativesValue));
		missRateTNRValue = falseNegativesValue
				/ ((truePositivesValue + falseNegativesValue) == 0 ? 1.0f : (truePositivesValue + falseNegativesValue));
	}

	public void setComparedTaxonomy(VariantTaxonomyNode _comparedTaxonomy) {
		this.computedTaxonomy = _comparedTaxonomy;
	}

	public VariantTaxonomyNode getGroundtruthTaxonomy() {
		return this.groundTruthTaxonomy;
	}

	public VariantTaxonomyNode getComparedTaxonomy() {
		return this.computedTaxonomy;
	}

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

	// Secondary Custom Measures' Getters
	public float getRVPA() {
		return this.RVPA;
	}

	public float getTDA() {
		return this.TDA;
	}

	public float getPCRA() {
		return this.PCRA;
	}

	public float getVPLA() {
		return this.VPLA;
	}

	public float getNDM() {
		return this.NDM;
	}
}
