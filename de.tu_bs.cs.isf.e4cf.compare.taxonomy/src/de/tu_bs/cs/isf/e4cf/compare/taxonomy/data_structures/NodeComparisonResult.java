/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures;

import de.tu_bs.cs.isf.e4cf.compare.comparator.impl.node.NodeResultElement;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.util.NodeSourceTransverser;

/**
 * @author developer-olan
 *
 */
public class NodeComparisonResult {
	private int artifactIndexOfLeftNode;
	private Tree artifactOfLeftNode;
	private Node leftNode;
	private String leftNodeSignature;

	private Tree artifactOfRightNode;
	private Node rightNode;
	private String rightNodeSignature;

	
	private NodeResultElement resultElement;
	
	private float similarity;
	private float weightedSimilarity;

	// Computed Values
	private float leftNodeWeight;

	// Util
	private NodeSourceTransverser sourceTransverser = new NodeSourceTransverser();


	public NodeComparisonResult(int _artifactIndexOfLeftNode, Tree _artifactOfLeftNode, Node _leftNode, Node _rightNode, Tree _artifactOfRightNode,
			float _similarity) {
		this.artifactIndexOfLeftNode = _artifactIndexOfLeftNode;
		this.artifactOfLeftNode = _artifactOfLeftNode;
		this.leftNode = _leftNode;
		this.artifactOfRightNode = _artifactOfRightNode;
		this.rightNode = _rightNode;
		this.similarity = _similarity;
		
		// Computed Properties
		this.leftNodeWeight = computeLeftNodeWeight();
		this.leftNodeSignature = extractNodeSignature(leftNode);
		this.rightNodeSignature = extractNodeSignature(rightNode);
		
	}

	public float computeLeftNodeWeight() {
		float nodeWeight = 0.0f;
		// Get variant number of Characters
		String artifactSource = sourceTransverser.getNodeSourceRecursive(artifactOfLeftNode.getRoot());
		
		// Compute Number of character in leftNode
		String leftNodeSource = sourceTransverser.getNodeSource(leftNode);
		
		// Compute Weight
		nodeWeight = (float)leftNodeSource.length()/(float)artifactSource.length();

		return nodeWeight;
	}
	
	public String extractNodeSignature(Node nodeToExtract) {
		String firstAttributeID = nodeToExtract.getNodeType();
		for(Attribute anAttribute: nodeToExtract.getAttributes()) {
			firstAttributeID = firstAttributeID + "@" + anAttribute.toString().split("@")[1];
			break;
		}
		
		return firstAttributeID;	
	}
	
	public int getArtifactIndex() {
		return artifactIndexOfLeftNode;
	}
	
	public Tree getArtifactOfLeftNode() {
		return artifactOfLeftNode;
	}
	
	public Tree getArtifactOfRightNode() {
		return artifactOfRightNode;
	}
	
	public Node getLeftNode() {
		return leftNode;
	}
	
	public String getLeftNodeSignature() {
		return leftNodeSignature;
	}

	public Node getRightNode() {
		return rightNode;
	}
	
	public String getRightNodeSignature() {
		return rightNodeSignature;
	}
	
	public NodeResultElement getResultElement() {
		return resultElement;
	}
	
	public float getSimilarity() {
		return similarity;
	}
	
	public float getWeightedSimilarity() {
		return weightedSimilarity;
	}
	
	public float getLeftNodeWeight() {
		return leftNodeWeight;
	}
	
	public void setWeightedSimilarity(float newSimilarityValue) {
		weightedSimilarity = newSimilarityValue;
	}


	

}
