package de.tu_bs.cs.isf.e4cf.compare.comparator.util;

import de.tu_bs.cs.isf.e4cf.compare.extensions.preferences.OptionalThresholdPref;
import de.tu_bs.cs.isf.e4cf.compare.stringtable.CompareST;
import de.tu_bs.cs.isf.e4cf.core.preferences.util.PreferencesUtil;

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

}
