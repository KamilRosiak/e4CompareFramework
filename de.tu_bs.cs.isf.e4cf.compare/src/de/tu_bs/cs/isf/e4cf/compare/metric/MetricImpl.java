package de.tu_bs.cs.isf.e4cf.compare.metric;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.NodeComparator;
import de.tu_bs.cs.isf.e4cf.compare.metric.interfaces.AbstractMetric;

public class MetricImpl extends AbstractMetric {
    private static final long serialVersionUID = -3144206252367501818L;
    

    public MetricImpl(String metricName) {
	super();
	setMetricName(metricName);
    }
    
    public MetricImpl(String metricName, NodeComparator... comparators) {
	this(metricName);
	addComparators(comparators);
    }
    
    /**
     * This method adds a array of node comparators to this metric.
     */
    private void addComparators(NodeComparator[] comparators) {
	for(NodeComparator comparator : comparators) {
	    addComparator(comparator.getSupportedNodeType(), comparator);
	}
    }
    

}
