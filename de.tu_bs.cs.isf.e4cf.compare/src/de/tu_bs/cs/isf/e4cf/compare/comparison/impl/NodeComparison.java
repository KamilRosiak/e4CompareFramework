package de.tu_bs.cs.isf.e4cf.compare.comparison.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.comparison.util.ComparisonUtil;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.Pair;

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

	public Pair<Map<String, List<Comparison<Node>>>, Map<String, List<Comparison<Node>>>> findOptionalMatchings() {
		Map<String, List<Comparison<Node>>> leftOptionals = new HashMap<String, List<Comparison<Node>>>();
		Map<String, List<Comparison<Node>>> rightOptionals = new HashMap<String, List<Comparison<Node>>>();
		findOptionalMatchingsRecursivly(this, leftOptionals, rightOptionals);
		return new Pair<Map<String, List<Comparison<Node>>>, Map<String, List<Comparison<Node>>>>(leftOptionals,
				rightOptionals);
	}

	/**
	 * Visits every node comparison and stores all optional elements in two maps
	 * 
	 * @param nextNode
	 */
	private void findOptionalMatchingsRecursivly(Comparison<Node> nextNode,
			Map<String, List<Comparison<Node>>> leftOptionals, Map<String, List<Comparison<Node>>> rightOptionals) {
		// Collect Optional elements
		if (nextNode.getLeftArtifact() == null) {
			String nodeType = nextNode.getRightArtifact().getNodeType();
			if (!rightOptionals.containsKey(nodeType)) {
				rightOptionals.put(nodeType, new ArrayList<Comparison<Node>>());
			} else {
				rightOptionals.get(nodeType).add(nextNode);
			}
		} else if (nextNode.getRightArtifact() == null) {
			String nodeType = nextNode.getLeftArtifact().getNodeType();
			if (!leftOptionals.containsKey(nodeType)) {
				leftOptionals.put(nodeType, new ArrayList<Comparison<Node>>());
			} else {
				leftOptionals.get(nodeType).add(nextNode);
			}
		}
		// call recursively for each element
		nextNode.getChildComparisons().forEach(childNode -> {
			findOptionalMatchingsRecursivly(childNode, leftOptionals, rightOptionals);
		});
	}

	@Override
	public Node mergeArtifacts() {
		return mergeArtifacts(true);
	}

	/**
	 * Sets the variability class of nodes and their children to optional
	 */
	public void setNodeOptionalWithChildren(Node node) {
		node.setVariabilityClass(ComparisonUtil.getClassForSimilarity(0f));
		node.getChildren().forEach(childNode -> {
			setNodeOptionalWithChildren(childNode);
		});
	}

	public Node mergeArtifacts(boolean omitOptionalChildren) {
		// if one of both artifact is null its means that we have an optional and can
		// keep the implementation below this artifacts.
		if (getLeftArtifact() == null || getRightArtifact() == null) {
			Node node = getLeftArtifact() == null ? getRightArtifact() : getLeftArtifact();
			if (omitOptionalChildren) {
				// Set all nodes to optional
				setNodeOptionalWithChildren(node);
			} else {
				// Process the Children as they were compared
				node.setVariabilityClass(ComparisonUtil.getClassForSimilarity(getSimilarity()));
				node.getChildren().clear();
				for (Comparison<Node> childComparision : getChildComparisons()) {
					node.addChildWithParent(((NodeComparison) childComparision).mergeArtifacts(omitOptionalChildren));
				}
			}
			return node;
		}

		// all artifacts which are equals
		if (getSimilarity() == ComparisonUtil.MANDATORY_VALUE) {
			// mandatory is a default value if the artifacts was optional in a previous
			// iteration it should stay as optional
			getRightArtifact().setUUID(getLeftArtifact().getUUID());//Set the UUID of the  left artifact because it is the base
			return getLeftArtifact();
		} else {
			getLeftArtifact().setVariabilityClass(ComparisonUtil.getClassForSimilarity(getSimilarity()));
			// first merge attributes
			Set<Attribute> containedAttrs = new HashSet<Attribute>();
			for (Attribute leftAttr : getLeftArtifact().getAttributes()) {
				for (Attribute rightAttr : getRightArtifact().getAttributes()) {
					// same attr type
					if (leftAttr.keyEquals(rightAttr)) {
						
						for (Value rightValue : rightAttr.getAttributeValues()) {
							if (!leftAttr.containsValue(rightValue)) {
								leftAttr.addAttributeValue(rightValue);
							} else {
								rightValue.setUUID(leftAttr.getAttributeValue(rightValue).getUUID());
							}
						}
						rightAttr.setUuid(leftAttr.getUuid());
						containedAttrs.add(rightAttr);
					}
				}
			}
			//remove all contained attrs from all others 
			Set<Attribute> allAttrs = new HashSet<Attribute>(getRightArtifact().getAttributes());
			allAttrs.removeAll(containedAttrs);
			// put all other attributes from right to left because it wasn't contained
			// before
			getRightArtifact().getAttributes().addAll(allAttrs);
			getRightArtifact().setUUID(getLeftArtifact().getUUID());
			// process child comparisons recursively
			getLeftArtifact().getChildren().clear();
			for (Comparison<Node> childComparision : getChildComparisons()) {
				getLeftArtifact()
						.addChildWithParent(((NodeComparison) childComparision).mergeArtifacts(omitOptionalChildren));
			}
			// add artifacts min line number
			getLeftArtifact()
					.setStartLine(Math.min(getLeftArtifact().getStartLine(), getRightArtifact().getStartLine()));
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
