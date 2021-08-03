package de.tu_bs.cs.isf.e4cf.compare.comparison.impl;

import java.util.Iterator;

import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.comparison.util.ComparisonUtil;
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
		// if one of both artifact is null its means that we have an optional and can
		// keep the implementation below this artifacts.
		if (getLeftArtifact() == null || getRightArtifact() == null) {
			Node node = getLeftArtifact() == null ? getRightArtifact() : getLeftArtifact();
			node.setVariabilityClass(ComparisonUtil.getClassForSimilarity(0f));
			return node;
		}
		// all artifacts which are equals
		if (getSimilarity() == ComparisonUtil.MANDATORY_VALUE) {
			// mandatory is a default value if the artifacts was optional in a previous
			// iteration it should stay as optional
			return getLeftArtifact();
		} else {
			getLeftArtifact().setVariabilityClass(ComparisonUtil.getClassForSimilarity(getSimilarity()));
			// first merge attributes

			for (Attribute leftAttr : getLeftArtifact().getAttributes()) {
				Iterator<Attribute> rightAttrs = getRightArtifact().getAttributes().iterator();
				while (rightAttrs.hasNext()) {
					Attribute rightAttr = rightAttrs.next();
					if (leftAttr.keyEquals(rightAttr)) {
						for (Value rightValue : rightAttr.getAttributeValues()) {
							if (!leftAttr.containsValue(rightValue)) {
								leftAttr.addAttributeValue(rightValue);
							}
						}
						rightAttrs.remove();
					}
				}
			}

			// put all other attributes from right to left because it wasn't contained
			// before
			getRightArtifact().getAttributes().stream().forEach(e -> getLeftArtifact().addAttribute(e));

			// process child comparisons recursively
			getLeftArtifact().getChildren().clear();
			for (Comparison<Node> childComparision : getChildComparisons()) {
				getLeftArtifact().addChildWithParent(childComparision.mergeArtifacts());
			}
			//add artifacts min line number 
			getLeftArtifact().setStartLine(Math.min(getLeftArtifact().getStartLine(), getRightArtifact().getStartLine()));
			getLeftArtifact().setEndLine(Math.min(getLeftArtifact().getEndLine(), getRightArtifact().getEndLine()));

			getLeftArtifact().sortChildNodes();
			return getLeftArtifact();
		}
	}

	@Override
	public boolean areArtifactsOfSameType() {
		return (getLeftArtifact() != null && getRightArtifact() != null)
				? getLeftArtifact().getNodeType().equals(getRightArtifact().getNodeType())
				: false;
	}

}
