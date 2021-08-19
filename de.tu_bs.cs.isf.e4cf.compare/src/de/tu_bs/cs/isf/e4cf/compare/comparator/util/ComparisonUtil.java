package de.tu_bs.cs.isf.e4cf.compare.comparator.util;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;
import de.tu_bs.cs.isf.e4cf.compare.extensions.preferences.OptionalThresholdPref;
import de.tu_bs.cs.isf.e4cf.compare.stringtable.CompareST;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;

/**
 * A Utility class for the manipulation of comparisons
 * 
 * @author Kamil Rosiak
 *
 */
public class ComparisonUtil {

	/**
	 * Return the Threshold Value from preferences.
	 */
	public static float getOptionalThreshold() {
		return PreferencesUtil.getValueWithDefault(CompareST.BUNDLE_NAME, OptionalThresholdPref.OPTIONAL_THRESHOLD,
				String.valueOf(OptionalThresholdPref.DEFAULT_THRESHOLD)).getFloatValue() / 100;
	}
	
	/**
	 * This method returns Node comparators that extending the
	 * de.tu_bs.cs.isf.e4cf.compare.Comparator extension point.
	 */
	public static List<Comparator> getComparator() {
		List<Comparator> comparator = RCPContentProvider.<Comparator>getInstanceFromBundle(
				CompareST.COMPARISON_SYMBOLIC_NAME, CompareST.COMPARISON_EXTENSION);
		if (comparator != null) {
			return comparator;
		}
		return new ArrayList<Comparator>();
	}

}
