package de.tu_bs.cs.isf.e4cf.compare.comparator.impl.node;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.comparator.templates.AbstractNodeComparator;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

public class StringComparator extends AbstractNodeComparator {
	float keyValueRatio = 0.4f;

	public StringComparator() {
		super(WILDCARD);
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
		// calculate the avarage similarity
		int maxAttributes = Math.max(firstNode.getAttributes().size(), firstNode.getAttributes().size());
		float similarity = maxAttributes > 0 ? sum(similarities) / maxAttributes : 1f;
		// add 0.2 as base similarity becuase this node are of the same type
		similarity = similarity * (1.0f - keyValueRatio) + keyValueRatio;
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
