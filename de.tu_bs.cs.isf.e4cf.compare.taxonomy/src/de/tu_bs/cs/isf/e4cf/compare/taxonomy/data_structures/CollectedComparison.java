/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

/**
 * Identifies and Collects compared nodes into their respective variants
 * @author developer-olan
 *
 */
public class CollectedComparison {
	private Tree leftArtifact;
	private Tree rightArtifact;
	private float cummulativeSimilarity;
	
	public CollectedComparison(Tree _leftArtifact, Tree _rightArtifact, Float _cummulativeSimilarity) {
		this.leftArtifact = _leftArtifact;
		this.rightArtifact = _rightArtifact;
		this.cummulativeSimilarity = _cummulativeSimilarity;
	}
	
	
	public Tree getLeftArtifact() {
		return leftArtifact;
	}
	
	public Tree getRightArtifact() {
		return rightArtifact;
	}
	
	public float getCummulativeSimilarity() {
		return cummulativeSimilarity;
	}
	
	public void addCummulativeSimilarity(float valueToAdd) {
		this.cummulativeSimilarity += valueToAdd;
		System.out.println("Similarity: "+this.cummulativeSimilarity+", Type: "+leftArtifact.getTreeName()+" Type: "+rightArtifact.getTreeName());
	}
}
