package de.tu_bs.cs.isf.e4cf.compare.comparison.impl;

import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.comparison.util.ComparisonUtil;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

/**
 * The implementation for the comparison of artifacts of type Node.
 * 
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
		if (!getResultElements().isEmpty()) {
			float resultSimilarity = getResultSimilarity();
			if (resultSimilarity == ComparisonUtil.MANDATORY_VALUE) {
				node = getLeftArtifact();
			} else if (resultSimilarity >= ComparisonUtil.OPTIONAL_VALUE) {
				node = getLeftArtifact();

				for (Attribute rightAttr : getRightArtifact().getAttributes()) {
					Attribute leftAttr = node.getAttributeForKey(rightAttr.getAttributeKey());

					if (leftAttr != null) {
						for (Value rightVal : rightAttr.getAttributeValues()) {
							if (!leftAttr.containsValue(rightVal)) {
								leftAttr.addAttributeValue(rightVal);
							}
						}
					} else {
						node.addAttribute(rightAttr);
					}
				}
			} else {
				node = getLeftArtifact() != null ? getLeftArtifact() : getRightArtifact();
			}
		} else {
			node = getLeftArtifact() != null ? getLeftArtifact() : getRightArtifact();
		}
		node.setVariabilityClass(ComparisonUtil.getClassForSimilarity(getSimilarity()));
		// process child comparisons recursively
		node.getChildren().clear();
		for (Comparison<Node> childComparision : getChildComparisons()) {
			node.addChild(childComparision.mergeArtifacts());
		}
		return node;
	}

}
