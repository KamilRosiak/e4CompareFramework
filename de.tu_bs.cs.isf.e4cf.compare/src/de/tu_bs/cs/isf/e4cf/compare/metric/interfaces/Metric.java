package de.tu_bs.cs.isf.e4cf.compare.metric.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;

public interface Metric extends Serializable {
    
    /**
     * This method adds a node type to the ignore list this means the node will not be compared.
     */
    public void ignoreType(String type);
    
    /**
     * This method unignores a node type in the ignore list.
     */
    public void unignorType(String type);
    
    /**
     * This method removes a node type from the ignore list
     * @param type
     */
    public void removeIgnoreType(String type);
    
    /**
     * This method give a comparator to the comparator map.
     */
    public void addComparator(String nodeType, Comparator comparator);
    
    /**
     * This method removes a comparator from the comparator map
     */
    public void removeComparator(String nodeType, Comparator comparator);
    
    /**
     * Returns the name of this metric
     */
    public String getMetricName();
    
    /**
     * This method returns a map with all comparator that are available in this metric.
     */
    public Map<String,List<Comparator>> getAllComparator();
    
    /**
     * This method returns a list of comparator that are created for the specific node type.
     */
    public List<Comparator> getComparatorForNodeType(String nodeType);
    
    /**
     * Get all types of comparators that are contained in this metric
     */
    public List<String> getComparatorTypes(); 
    
    /**
     * Returns a list of node types that are ignored and not compared during the comparison phase.
     */
    public Map<String, Boolean> getNodeIgnoreList();
    
    /**
     * This method checks if the given node type is on the ignore list.
     */
    public boolean isTypeIgnored(String NodeType);
    
    
}
