/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparison;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.ResultElement;
import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.*;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparators.DirectoryNameComparator;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparators.DirectoryNonSourceFileComparator;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparators.DirectorySizeComparator;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparators.DirectorySourceFileComparator;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.taxonomy_control_view.data_structures.TaxonomySettings;

/**
 * @author developer-olan
 *
 */
public class TaxonomyNodeComparison extends AbstractComparsion<Node>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4156127856862331254L;
	private int numberOfChildComparisons = 0;

	/**
	 * @param leftArtifact
	 * @param rightArtifact
	 */
	public TaxonomyNodeComparison(Node leftArtifact, Node rightArtifact) {
		super(leftArtifact, rightArtifact);
	}
	
	public TaxonomyNodeComparison(Node leftArtifact, Node rightArtifact, float similarity) {
		this(leftArtifact, rightArtifact);
		setSimilarity(similarity);
	}

	@Override
	public boolean areArtifactsOfSameType() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Node mergeArtifacts() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override 
	public float getResultSimilarity() {
		float resultSimilarity = 0f;
		for (ResultElement<Node> result : getResultElements()) {
			resultSimilarity += result.getSimilarity();
		}
		if (!getResultElements().isEmpty()) {
			resultSimilarity = resultSimilarity / getResultElements().size();
		}
		//if one of both artifacts is null the similarity is 0 because its an optional
		return (getLeftArtifact()== null || getRightArtifact() == null) ? 0f : resultSimilarity;
	}
	

	@Override 
	public float getChildSimilarity() {
		float childSimilarity = 0f;
		// call update similarity recursively on all child comparison
		for (Comparison<Node> childComparison : getChildComparisons()) {
			System.out.println("Child Similarity: "+ childComparison.getLeftArtifact().getNodeType()+", "+childComparison.getRightArtifact().getNodeType()); // test
			childComparison.updateSimilarity();
			childSimilarity += childComparison.getSimilarity();
			numberOfChildComparisons++;
		}
		
		System.out.println("C = " + numberOfChildComparisons);
		
		// calculate the average value
		if (!getChildComparisons().isEmpty()) {
			return childSimilarity / getChildComparisons().size();
		} else {
			//if empty they are similar
			return (getLeftArtifact()== null || getRightArtifact() == null) ? 0f : 1f;
		}
		
	}
	
	@Override
	public void updateSimilarity() {
		float similarity = 0f;
		float childSimilarity = getChildSimilarity();	// Children based similarity
		float resultSimilarity = getResultSimilarity(); // Comparator based similarity

		// no children node attributes so they are equal on their type
		if (getChildComparisons().isEmpty() && getResultElements().isEmpty()) {
			if(areArtifactsOfSameType()) {
				similarity = 1f;
			} else {
				similarity = 0f;
			}
		}

		// only results available so the similarity is based on them
		if (getChildComparisons().isEmpty() && !getResultElements().isEmpty()) {
			similarity = resultSimilarity;
		}

		// no results available so we use the similarity of our children
		if (!getChildComparisons().isEmpty() && getResultElements().isEmpty()) {
			similarity = childSimilarity;
		}

		// no results available so we use the similarity of our children
		if (!getChildComparisons().isEmpty() && !getResultElements().isEmpty()) {
			// TODO: weight which allows to scale this value
			similarity = (childSimilarity + resultSimilarity) / 2;
		}
		setSimilarity(similarity);
	}

	
	public float calculateSimilarity(TaxonomySettings settings) {
		float resultSimilarity = 0.0f;
		
		if (settings.getSourceLevelComparison()) {
			// Return result similarity using all utilized source code file-level metrics/comparators
			resultSimilarity = this.getResultSimilarity();
		} else {
			float weightSum = 0.0f;
			ResultElement<Node> dirNameResult = null;
			// Iterate through all ResultElements
			for (ResultElement<Node> result : getResultElements()) {
				// Get weight from configuration
				if (result.getUsedComparator().getClass().equals(new DirectoryNameComparator().getClass())) {
					dirNameResult = result;
				}
				
				// Computed Weighted Similarity of static weighted comparators
				if (result.getUsedComparator().getClass().equals(new DirectorySourceFileComparator().getClass())) {
					resultSimilarity += (result.getSimilarity() * 0.25f);
					weightSum += 0.25f;
				} else if (result.getUsedComparator().getClass().equals(new DirectoryNonSourceFileComparator().getClass())) {
					resultSimilarity += (result.getSimilarity() * 0.15f);
					weightSum += 0.15f;
				} else if (result.getUsedComparator().getClass().equals(new DirectorySizeComparator().getClass())) {
					resultSimilarity += (result.getSimilarity() * 0.10f);
					weightSum += 0.10f;
				}
			}
			
			// Add Weighted similarity of Directory
			if (dirNameResult != null) {
				resultSimilarity += (dirNameResult.getSimilarity() * (1.0f - weightSum));
			} else {
				resultSimilarity = this.getResultSimilarity();
			}
		}
		
		return resultSimilarity;
	}
}
