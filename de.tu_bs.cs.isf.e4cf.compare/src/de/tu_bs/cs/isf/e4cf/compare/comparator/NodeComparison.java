package de.tu_bs.cs.isf.e4cf.compare.comparator;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.AbstractComparsion;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * A container for the storage of node comparisons
 * @author Kamil Rosiak
 *
 */
public class NodeComparison extends AbstractComparsion<Node>{
    private List<AttrComparison> attributeComparions;
    
    public NodeComparison(Node leftArtifact, Node rightArtifact) {
	super(leftArtifact, rightArtifact);
	setAttributeComparions(new ArrayList<AttrComparison>());
    }
    
    public NodeComparison(Node leftArtifact, Node rightArtifact, float similarity) {
	super(leftArtifact, rightArtifact);
	setSimilarity(similarity);
    }
    
    public List<AttrComparison> getAttributeComparions() {
	return attributeComparions;
    }

    public void setAttributeComparions(List<AttrComparison> attributeComparions) {
	this.attributeComparions = attributeComparions;
    }
    
    public void addAttributeComparion(AttrComparison attributeComparion) {
	this.attributeComparions.add(attributeComparion);
    }
}
