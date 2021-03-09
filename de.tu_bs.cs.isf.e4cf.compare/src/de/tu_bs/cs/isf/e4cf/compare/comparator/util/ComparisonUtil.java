package de.tu_bs.cs.isf.e4cf.compare.comparator.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import de.tu_bs.cs.isf.e4cf.compare.comparator.AttrComparison;
import de.tu_bs.cs.isf.e4cf.compare.comparator.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces.Matcher;

/**
 * A Utility class for the manipulation of comparisons
 * 
 * @author Kamil Rosiak
 *
 */
public class ComparisonUtil {
    
    
    
    
    
    public static Tree compare(List<Tree> trees) {
	
	
	
	return null;
    }
    

    /**
     * This method sorts a list of Comparison by their similarity value.
     */
    public static <K> void sortComparisonBySimilarity(List<Comparison<K>> comparisons) {
	comparisons.sort((first, second) -> {
	    if (first.getSimilarityValue() == second.getSimilarityValue()) {
		return 0;
	    }
	    return first.getSimilarityValue() < second.getSimilarityValue() ? 1 : -1;
	});
    }

    /**
     * This method compares two nodes based on a string comparison and returns a
     * NodeComparison which contains a similarity value between both elements.
     */
    public static NodeComparison defaultNodecompare(Node firstNode, Node secondNode, Matcher matcher) {
	List<Comparison<Attribute>> comparisons = new ArrayList<Comparison<Attribute>>();

	/**
	 * Creates all attribute comparisons for the given nodes
	 */
	for (Attribute first_attr : firstNode.getAttributes()) {
	    for (Attribute second_attr : secondNode.getAttributes()) {
		if (first_attr.isAttributeOfSameType(second_attr)) {
		    comparisons.add(new AttrComparison(first_attr, second_attr, first_attr.compare(second_attr)));
		}
	    }
	}
	// determine the amount of attributes and match the attribute comparisons
	int maxAttrs = Math.max(firstNode.getAttributes().size(), secondNode.getAttributes().size());
	comparisons = matcher.getMatching(comparisons);

	float similarity = 0.0f;
	// add the similarity values of all attribute comparisons and dived it by the
	// number of attribute
	for (Comparison<Attribute> attrComp : comparisons) {
	    similarity += attrComp.getSimilarityValue();
	}

	similarity = similarity / maxAttrs;

	return new NodeComparison(firstNode, secondNode, similarity);

    }

    /**
     * This method removes all left elements out of the given comparison container
     * and all child elements.
     */
    public static <K> void removeAllLeftElements(Comparison<K> comparison) {
	removeAllElementsForSide(true, comparison);
    }

    /**
     * This method removes all right elements out of the given comparison container
     * and all child elements.
     */
    public static <K> void removeAllRightElements(Comparison<K> comparison) {
	removeAllElementsForSide(false, comparison);
    }

    /**
     * Removes all elements for a given side and sets the similarity of the
     * respective container to 0.
     * 
     * @param <K>
     * @param side       if TRUE the left elements are removed elese right elements
     *                   are removed
     * @param comparison
     */
    public static <K> void removeAllElementsForSide(boolean side, Comparison<K> comparison) {
	removeElementsFromComparison(side, comparison);
	for (Comparison<K> childComparison : comparison.getChildComparisons()) {
	    removeAllElementsForSide(side, childComparison);
	}
    }

    /**
     * Removes the specifice elements from the comparison and sets the similarity to
     * 0.
     * 
     * @param <K>
     * @param side       if TRUE the left elements are removed elese right elements
     *                   are removed
     * @param comparison
     */
    public static <K> void removeElementsFromComparison(boolean side, Comparison<K> comparison) {
	comparison.setSimilarity(0);
	if (side) {
	    comparison.setLeftElement(null);
	} else {
	    comparison.setRightElement(null);
	}
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
     * This method calculates a matching of a given comparison graph
     */
    public static void calculateMatchingRecursivly(Comparison<Node> comparison, Matcher matcher) {
	// if the comparison has some child elements call this method recursively
	if (!comparison.getChildComparisons().isEmpty()) {
	    comparison.getChildComparisons().stream().forEach(e -> {
		calculateMatchingRecursivly(e, matcher);
	    });
	    // calculate the matching for all child comparisons
	    matcher.getMatching(comparison.getChildComparisons());
	    comparison.updateSimilarity();
	}

    }

    /**
     * This method creates a composition of the used comparisons with a bottom-up
     * approach, which starts with all leaf nodes and connects all comparisons. The
     * returning value is the root comparison.
     */
    public static NodeComparison calculateComparisonGraph(Set<NodeComparison> comparisons) {
	NodeComparison rootComparison = getRootComparison(comparisons);
	calculateGraphRec(comparisons, rootComparison);
	return rootComparison;
    }

    /**
     * This method creates a graph out of all comparisons that are given as a set.
     */
    private static void calculateGraphRec(Set<NodeComparison> comparisons, NodeComparison parent) {
	// get all child comparisons of the given parent comparison
	Set<NodeComparison> nextLevelObjects = comparisons.stream()
		.filter(e -> parent.getLeftElement().getChildren().contains(e.getLeftElement())
			|| parent.getRightElement().getChildren().contains(e.getRightElement()))
		.map(e -> (NodeComparison) e).collect(Collectors.toSet());
	// add every comparison to children an set them as parent
	nextLevelObjects.stream().forEach(e -> {
	    parent.addChildComparison(e);
	    e.addParent(parent);
	});
	// remove the parent because all child comparisons are already added.
	comparisons.remove(parent);
	// call the method recursively for the children comparisons
	nextLevelObjects.stream().forEach(e -> calculateGraphRec(comparisons, e));
    }

    private Set<NodeComparison> getAllLeafComparisons(Set<NodeComparison> comparisons) {
	return comparisons.stream()
		.filter(e -> e.getLeftElement().getChildren().isEmpty() && e.getRightElement().getChildren().isEmpty())
		.collect(Collectors.toSet());
    }

    private static NodeComparison getRootComparison(Set<NodeComparison> comparisons) {
	return comparisons.stream()
		.filter(e -> e.getLeftElement().getParent() == null && e.getRightElement().getParent() == null)
		.findAny().get();
    }

}
