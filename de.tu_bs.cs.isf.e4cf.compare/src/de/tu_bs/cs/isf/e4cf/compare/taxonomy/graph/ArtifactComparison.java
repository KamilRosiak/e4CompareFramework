package de.tu_bs.cs.isf.e4cf.compare.taxonomy.graph;

import de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparison.TaxonomyNodeComparison;

/**
 * @author developer-olan
 *
 */
public class ArtifactComparison {
  private String leftArtifactName;
  private String rightArtifactName;
  
  private TaxonomyNodeComparison artifactComparison;
  
  public ArtifactComparison (TaxonomyNodeComparison _artifactComparison, String _leftArtifactName, String _rightArtifactName)
  {
	  artifactComparison = _artifactComparison;
	  leftArtifactName = _leftArtifactName;
	  rightArtifactName = _rightArtifactName;
  }
  
  public String getLeftArtifactName() 
  {
	  return leftArtifactName;
  }
  
  public String getRightArtifactName() 
  {
	  return rightArtifactName;
  }
  
  public TaxonomyNodeComparison getNodeComparison() {
	  return artifactComparison;
  }
  
}
