package de.tu_bs.cs.isf.e4cf.compare;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.comparator.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.NodeComparator;
import de.tu_bs.cs.isf.e4cf.compare.comparator.util.ComparisonUtil;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces.Matcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.interfaces.Metric;

public class CompareEngine {
	private Matcher matcher;
	private Metric metric;

	public CompareEngine(Matcher matcher, Metric comparisonMetric) {
		setMetric(comparisonMetric);
		setMatcher(matcher);
	}

	public Tree compare(Tree firstArtifact, Tree secondArtifact) {
		// TODO: Compare, Match, Merge
		try {
			Set<NodeComparison> comparisons = compare(firstArtifact.getRoot(), secondArtifact.getRoot());

			NodeComparison root = ComparisonUtil.calculateComparisonGraph(comparisons);
			ComparisonUtil.calculateMatchingRecursivly(root, matcher);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Compares the first and the second node with all his children
	 * 
	 * @param firstNode
	 * @param secondNode
	 * @return
	 */
	public Set<NodeComparison> compare(Node firstNode, Node secondNode) {

		Set<String> nodeTypes = getNodeTypes(firstNode, secondNode);
		Set<NodeComparison> comparisons = new HashSet<NodeComparison>();

		/**
		 * Compare only nodes with the same type.
		 */
		for (String nodeType : nodeTypes) {
			// first check if the node type is not ignored
			if (!metric.isTypeIgnored(nodeType)) {
				// Gather nodes of the same type of both trees
				List<Node> firstArtifacts = firstNode.getChildrenOfType(nodeType);
				List<Node> secondArtifacts = secondNode.getChildrenOfType(nodeType);
				// Get all comparator for this node type
				List<NodeComparator> comparators = metric.getComparatorForNodeType(nodeType);

				// Compare every artifact of the same type with each other
				for (Node leftArtifact : firstArtifacts) {
					for (Node rightArtifact : secondArtifacts) {

						if (!comparators.isEmpty()) {
							NodeComparison nodeComparison = new NodeComparison(leftArtifact, rightArtifact);
							for (NodeComparator comparator : comparators) {
								comparator.compare(leftArtifact, rightArtifact);
							}
							nodeComparison.updateSimilarity();
						} else {
							comparisons.add(ComparisonUtil.defaultNodecompare(firstNode, secondNode, matcher));
						}
					}
				}
			}
		}
		return comparisons;
	}

	/**
	 * This method returns a set that contains all nodes types that are contained in
	 * this nodes.
	 * 
	 * @return a set of contained node types.
	 */
	private Set<String> getNodeTypes(Node... nodes) {
		Set<String> nodeTypes = new HashSet<String>();
		for (Node node : nodes) {
			nodeTypes.addAll(node.getAllNodeTypes());
		}
		return nodeTypes;
	}

	public Matcher getMatcher() {
		return matcher;
	}

	public void setMatcher(Matcher matcher) {
		this.matcher = matcher;
	}

	public Metric getMetric() {
		return metric;
	}

	public void setMetric(Metric metric) {
		this.metric = metric;
	}

}
