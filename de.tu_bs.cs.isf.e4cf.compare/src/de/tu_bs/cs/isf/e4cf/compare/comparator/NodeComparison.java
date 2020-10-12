package de.tu_bs.cs.isf.e4cf.compare.comparator;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.AbstractComparsion;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * A container for the storage of node comparisons
 * @author Kamil Rosiak
 *
 */
public class NodeComparison extends AbstractComparsion<Node>{

    public NodeComparison(Node leftArtifact, Node rightArtifact) {
	super(leftArtifact, rightArtifact);
    }
    
    public NodeComparison(Node leftArtifact, Node rightArtifact, float similarity) {
	super(leftArtifact, rightArtifact);
	setSimilarity(similarity);
    }

}
