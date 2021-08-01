package de.tu_bs.cs.isf.e4cf.compare.taxonomy.graph;

import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison;

/**
 * @author developer-olan
 *
 */
public class ArtifactComparison {
  private String leftArtifactName;
  private String rightArtifactName;
  
  private NodeComparison artifactComparison;
  
  public ArtifactComparison (NodeComparison _artifactComparison, String _leftArtifactName, String _rightArtifactName)
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
  
  public NodeComparison getNodeComparison() {
	  return artifactComparison;
  }
  
}
