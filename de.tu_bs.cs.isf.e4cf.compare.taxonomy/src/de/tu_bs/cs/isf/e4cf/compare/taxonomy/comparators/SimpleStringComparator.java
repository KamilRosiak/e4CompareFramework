package de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparators;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.comparator.impl.node.NodeResultElement;
import de.tu_bs.cs.isf.e4cf.compare.comparator.templates.AbstractNodeComparator;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

public class SimpleStringComparator extends AbstractNodeComparator {
	float keyValueRatio = 0.0f;

	public SimpleStringComparator() {
		super(WILDCARD);
	}

	public NodeResultElement compare(Tree variant, Node firstNode, Node secondNode) {
		
		NodeResultElement resultElement = compare(firstNode, secondNode);
		
		return resultElement;
	}
	
	@Override
	public NodeResultElement compare(Node firstNode, Node secondNode) {
		List<Float> similarities = new ArrayList<Float>();
		// compares for every attribute key, which is unique the corresponding values
		for (Attribute firstAttr : firstNode.getAttributes()) {
			for (Attribute secondAttr : secondNode.getAttributes()) {
				// check if attributes are the same
				if (firstAttr.keyEquals(secondAttr)) {
					similarities.add(compareValues(firstAttr, secondAttr));
				} 
			}
		}
		
		int maxAttributes  = Math.max(firstNode.getAttributes().size(), firstNode.getAttributes().size());
		float similarity = maxAttributes > 0 ? sum(similarities) / maxAttributes : 1f;
		// add 0.4 as base similarity because this node are of the same type
		similarity = similarity * (1.0f - keyValueRatio) + keyValueRatio;
//		similarity = similarity > 1.0f ? 1.0f : similarity;
		return new NodeResultElement(this, similarity);
	}

	/**
	 * calculates the sum of a list of floats
	 */
	private float sum(List<Float> values) {
		float sum = 0;
		for (float value : values) {
			sum += value;
		}
		return sum;
	}

	/**
	 * compares the values of a corresponding key returns 1 if a match is found else
	 */
	@SuppressWarnings("unchecked")
	private Float compareValues(Attribute firstAttr, Attribute secondAttr) {
		for (Value firstValue : firstAttr.getAttributeValues()) {
			for (Value secondValue : secondAttr.getAttributeValues()) {
				if (firstValue.equals(secondValue)) {
					return 1f;
				}
			}
		}
		return 0f;
	}

}
