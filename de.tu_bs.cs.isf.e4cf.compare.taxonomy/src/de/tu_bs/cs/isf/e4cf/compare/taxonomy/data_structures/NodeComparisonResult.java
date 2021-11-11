/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures;

import de.tu_bs.cs.isf.e4cf.compare.comparator.impl.node.NodeResultElement;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.util.DirectoryNodeTraverser;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.util.SourceNodeTraverser;

/**
 * @author developer-olan
 *
 */
public class NodeComparisonResult {
	private int artifactIndexOfLeftNode;
	private int artifactSourceLength;
	private Tree artifactOfLeftNode;
	private Node leftNode;
	private String leftNodeSignature;

	private Tree artifactOfRightNode;
	private Node rightNode;
	private String rightNodeSignature;

	
	private NodeResultElement resultElement;
	
	private float similarity;
	private float weightedSimilarity;
	
	private boolean marked;

	// Computed Values
	private float leftNodeWeight;
	private boolean isDirectory;

	public NodeComparisonResult(int _artifactIndexOfLeftNode, int _artifactSourceLength, Tree _artifactOfLeftNode, Node _leftNode, Node _rightNode, Tree _artifactOfRightNode,
			float _similarity) {
		this.marked = false;
		this.artifactIndexOfLeftNode = _artifactIndexOfLeftNode;
		this.artifactSourceLength = _artifactSourceLength;
		this.artifactOfLeftNode = _artifactOfLeftNode;
		this.leftNode = _leftNode;
		this.artifactOfRightNode = _artifactOfRightNode;
		this.rightNode = _rightNode;
		this.similarity = _similarity;
		
		// Computed Properties
		this.isDirectory = this.leftNode.getNodeType().equals("Directory") ? true : false;
		this.leftNodeSignature = extractNodeSignature(leftNode);
		this.rightNodeSignature = extractNodeSignature(rightNode);
		
		if (!this.isDirectory) {
			this.leftNodeWeight = computeLeftNodeWeight();
			
		} else {
			this.leftNodeWeight	= computeLeftNodeDirWeight();
		}
		
		
	}

	public float computeLeftNodeWeight() {
		float nodeWeight = 0.0f;
		
			try {

				// Compute Number of character in leftNode
				String leftNodeSource = SourceNodeTraverser.getNodeSourceRecursive(leftNode, false);

				// Compute Weight
				nodeWeight = (float) leftNodeSource.length() / (float) this.artifactSourceLength;
				

			} catch (Exception ex) {
				System.out.println("Error Computing Node Weght: " + ex.getMessage());
			}
			
		return nodeWeight;
	}
	
	public float computeLeftNodeDirWeight() {
		float nodeWeight = 0.0f;
		
		try {
			// Get variant number of Characters
			int leftVariantFolderCount = DirectoryNodeTraverser.getVariantSubdirectoryCount(this.artifactOfLeftNode.getRoot());
			
			if (leftVariantFolderCount != 0) {
				// Compute Weight
				nodeWeight = 1.0f/(float)leftVariantFolderCount;
			}
		
			
		} catch (Exception ex) {
			System.out.println("Error Computing Node Weght: " + ex.getMessage());
		}
		return nodeWeight;
	}
	
	public float computeTotalVariantSource() {
		float nodeWeight = 0.0f;
		if (SourceNodeTraverser.fileExtensions.contains(leftNode.getNodeType())) {
			
		}
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
	
	public int getArtifactSourceLength() {
		return artifactSourceLength;
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
	
	public boolean IsDirectory() {
		return this.isDirectory;
	}
	
	public boolean IsMarked() {
		return this.marked;
	}
	
	public void setIsMarked(boolean status) {
		this.marked = status;
	}
	
	public void setRightToEmptyTree(String name) {
		this.artifactOfRightNode = new TreeImpl(name);
	}
	
	public void setWeightedSimilarity(float newSimilarityValue) {
		weightedSimilarity = newSimilarityValue;
	}


	

}
