package de.tu_bs.cs.isf.e4cf.compare.comparison.impl;

import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.comparison.util.ComparisonUtil;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * The implementation for the comparison of artifacts of type Node.
 * @author NoLimit
 *
 */
public class NodeComparison extends AbstractComparsion<Node> {
	private static final long serialVersionUID = 7260025397506680712L;
	
	public NodeComparison(Node leftArtifact, Node rightArtifact) {
		super(leftArtifact, rightArtifact);
	}
	
	public NodeComparison(Node leftArtifact, Node rightArtifact, float similarity) {
		this(leftArtifact, rightArtifact);
		setSimilarity(similarity);
	}
	
	public NodeComparison(Node leftArtifact, Node rightArtifact, NodeComparison parent) {
		this(leftArtifact, rightArtifact);
		parent.addChildComparison(this);
	}
	
	/**
	 * Copy Constructor
	 */
	public NodeComparison(NodeComparison source) {
		this(source.getLeftArtifact(), source.getRightArtifact());
		getChildComparisons().addAll(source.getChildComparisons());
		getResultElements().addAll(source.getResultElements());
		setSimilarity(source.getSimilarity());
	}

	@Override
	public Node mergeArtifacts() {
		Node node = null;
		if(getSimilarity() == ComparisonUtil.MANDATORY_VALUE) {
			node = getLeftArtifact();
			node.setVariabilityClass(VariabilityClass.MANDATORY);
		} else if(getSimilarity() >= ComparisonUtil.OPTIONAL_VALUE) {
			node = getLeftArtifact();
			node.setVariabilityClass(VariabilityClass.ALTERNATIVE);
			for(Attribute attr : getRightArtifact().getAttributes()) {
				Attribute mergAttr = node.getAttributeForKey(attr.getAttributeKey());
				if(mergAttr != null) {
					mergAttr.addAttributeValues(mergAttr.getAttributeValues());
				} else {
					node.addAttribute(mergAttr.getAttributeKey(), mergAttr.getAttributeValues());
				}
			}
		} else {
			node = getLeftArtifact() != null ? getLeftArtifact() : getRightArtifact();
			node.setVariabilityClass(VariabilityClass.OPTIONAL);
		}
		//process child comparisons recursively 
		node.getChildren().clear();
		for(Comparison<Node> childComparision : getChildComparisons()) {
			node.addChild(childComparision.mergeArtifacts());
		}
		return node;
	}
	

}
