package de.tu_bs.cs.isf.e4cf.compare.matcher.util;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces.Matcher;
import de.tu_bs.cs.isf.e4cf.compare.stringtable.CompareST;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;

public class MatcherUtil {
    /**
     * This method returns all matcher that extending the
     * de.tu_bs.cs.isf.e4cf.compare.Matcher extension point.
     */
    public static List<Matcher> getAllMatcher() {
	List<Matcher> matcher = RCPContentProvider.<Matcher>getInstanceFromBundle(CompareST.MATCHER_SYMBOLIC_NAME,
		CompareST.MATCHER_EXTENSION);
	if (matcher != null) {
	    return matcher;
	}
	return new ArrayList<Matcher>();
    }
}
