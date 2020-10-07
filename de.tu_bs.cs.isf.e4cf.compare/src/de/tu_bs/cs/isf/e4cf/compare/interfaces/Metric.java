package de.tu_bs.cs.isf.e4cf.compare.interfaces;

import java.util.List;
import java.util.Map;

public interface Metric {
    /**
     * This method returns a map with all comparator that are available in this metric.
     */
    public Map<String,List<NodeComparator>> getAllComparator();
    
    /**
     * This method returns a list of comparator that are created for the specific node type.
     */
    public List<NodeComparator> getComparatorForNodeType(String nodeType);
    
    /**
     * Returns a list of node types that are ignored and not compared during the comparison phase.
     */
    public List<String> getNodeIgnoreList();
    
    /**
     * This method checks if the given node type is on the ignore list.
     */
    public boolean isTypeIgnored(String NodeType);
    
    
}
