package de.tu_bs.cs.isf.e4cf.compare;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.comparator.AttrComparison;
import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.NodeComparator;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.interfaces.Result;
import de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces.Matcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.interfaces.Metric;

public class CompareEngine {
    private Matcher matcher;
    private Metric metric;

    public void compare(Tree firstArtifact, Tree secondArtifact, Metric comparisonMetric, Matcher matcher) {
	setMetric(comparisonMetric);
	setMatcher(matcher);

	Result result = compare(firstArtifact.getRoot(), secondArtifact.getRoot());

    }

    public Result compare(Node firstNode, Node secondNode) {
	Set<String> nodeTypes = getNodeTypes(firstNode, secondNode);

	/**
	 * Compare only nodes with the same type.
	 */
	for (String nodeType : nodeTypes) {
	    List<Node> firstArtifacts = firstNode.getChildrenOfType(nodeType);
	    List<Node> secondArtifacts = secondNode.getChildrenOfType(nodeType);
	    List<NodeComparator> comparator = metric.getComparatorForNodeType(nodeType);

	    for (Node leftArtifact : firstArtifacts) {
		for (Node rightArtifact : secondArtifacts) {

		}
	    }

	}
	return null;
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

    private Comparison defaultCompare(Node firstNode, Node secondNode) {
	List<Comparison> comparisons = new ArrayList<Comparison>();

	for (Attribute first_attr : firstNode.getAttributes()) {
	    for (Attribute second_attr : secondNode.getAttributes()) {
		if(isSameAttributeType(first_attr,second_attr)) {
		    comparisons.add(new AttrComparison(first_attr, second_attr, first_attr.compare(second_attr)));  
		}
	    }
	}
	comparisons = matcher.getMatching(comparisons);

	return null;
    }

    private List<Comparison> getMatching(List<Comparison> comparisons) {
	return matcher.getMatching(comparisons);
    }

    /**
     * This method compares the attribute keys of two attributes.
     * 
     * @return True if types equals else false
     */
    private boolean isSameAttributeType(Attribute firstAttr, Attribute secondAttr) {
	return firstAttr.getAttributeKey().equals(secondAttr.getAttributeKey());
    }
    
    /**
     * This method compares the node type of two nodes.
     * 
     * @return True if types equals else false
     */
    private boolean isSameNodeType(Node firstNode, Node secondNode) {
	return firstNode.getNodeType().equals(secondNode.getNodeType());
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
