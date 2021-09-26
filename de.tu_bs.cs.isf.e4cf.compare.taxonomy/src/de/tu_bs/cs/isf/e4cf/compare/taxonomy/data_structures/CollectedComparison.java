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
	private float cumulativeSimilaritySource;
	private float cumulativeSimilarityDirectory;

	
	public CollectedComparison(Tree _leftArtifact, Tree _rightArtifact, Float _cummulativeSimilarity, boolean isDirectory) {
		this.leftArtifact = _leftArtifact;
		this.rightArtifact = _rightArtifact;
		
		if (!isDirectory) {
			this.cumulativeSimilaritySource = _cummulativeSimilarity;
		} else {
			this.cumulativeSimilarityDirectory = _cummulativeSimilarity;	
		}
	}
	
	
	public Tree getLeftArtifact() {
		return leftArtifact;
	}
	
	public Tree getRightArtifact() {
		return rightArtifact;
	}
	
	public float getCummulativeSimilarity() {
		if (this.cumulativeSimilarityDirectory > 0.0f) {
			return getTotalSimilarity();
		} else {
			return cumulativeSimilaritySource;
		}
	}
	
	public float getCummulativeSimilarityDirectory() {
		return cumulativeSimilarityDirectory;
	}
	
	public float getTotalSimilarity() {
		return (0.8f * cumulativeSimilaritySource) + (0.2f * cumulativeSimilarityDirectory);
	}
	
	public void addCummulativeSimilarity(float valueToAdd, boolean isDirectory) {
		if (!isDirectory) {
			this.cumulativeSimilaritySource += valueToAdd;
		} else {
			this.cumulativeSimilarityDirectory += valueToAdd;
		}
	}
	
	public void addCummulativeSimilarityDirectory(float valueToAdd) {
		this.cumulativeSimilaritySource += valueToAdd;
	}
}
