package de.tu_bs.cs.isf.e4cf.compare.comparator.util;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

public class ComparatorUtil {
	
	/**
	 * Compare two sets of values and returns 1f if a match is found else 0f.
	 */
	public static float compareValues(Attribute first, Attribute second) {
		for(Value firstValue : first.getAttributeValues()) {
			for(Value secondValue : second.getAttributeValues()) {
				if(firstValue.equals(secondValue)) {
					return 1f;
				}
			}
		}
		return 0f;
	}
}
