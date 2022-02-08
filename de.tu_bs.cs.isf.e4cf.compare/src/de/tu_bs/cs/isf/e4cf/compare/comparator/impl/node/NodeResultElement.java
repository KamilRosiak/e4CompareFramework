package de.tu_bs.cs.isf.e4cf.compare.comparator.impl.node;

import java.util.Map;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;
import de.tu_bs.cs.isf.e4cf.compare.comparator.templates.AbstractResultElement;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * A concrete class that stores the comparison information of a comparator.
 * 
 * @author Kamil Rosiak
 *
 */
public class NodeResultElement extends AbstractResultElement<Node> {
	
	private Map<String, Float> attributeSimilarities;
	
	public NodeResultElement(Comparator<Node> usedComparator, float similarity) {
		super(usedComparator, similarity);
	}
	
	public NodeResultElement(Comparator<Node> usedComparator, float similarity, Map<String, Float> attributeSimilarities) {
		super(usedComparator, similarity);
		this.attributeSimilarities = attributeSimilarities;
	}
	
	public Map<String, Float> getAttributeSimilarities() {
		return attributeSimilarities;
	}
}
