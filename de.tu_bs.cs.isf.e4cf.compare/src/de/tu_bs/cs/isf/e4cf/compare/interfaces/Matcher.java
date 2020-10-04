package de.tu_bs.cs.isf.e4cf.compare.interfaces;

import java.util.List;

public interface Matcher {
    /**
     * This method returns a list of comparisons. This list contains the best pairs of nodes between the compared nodes.
     * @param comparisons
     * @return
     */
    public List<Comparison> getMatching(List<Comparison> comparisons);
}
