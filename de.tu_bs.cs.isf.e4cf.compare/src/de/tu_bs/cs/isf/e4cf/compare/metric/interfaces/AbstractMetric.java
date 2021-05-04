package de.tu_bs.cs.isf.e4cf.compare.metric.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;
import javafx.collections.FXCollections;

public class AbstractMetric implements Metric {
	private static final long serialVersionUID = 2908094055388556104L;

	private String metricName;
	private Map<String, List<Comparator>> comparators;
	private Map<String, Boolean> nodeIgnorList;

	public AbstractMetric() {
		setComparator(new HashMap<String, List<Comparator>>());
		setNodeIgnorList(new HashMap<String, Boolean>());
	}

	@Override
	public void ignoreType(String type) {
		nodeIgnorList.put(type, true);
	}

	@Override
	public void unignorType(String type) {
		nodeIgnorList.put(type, false);
	}
	
	@Override
	public void removeIgnoreType(String type) {
		nodeIgnorList.remove(type);
	}
	
	

	@Override
	public void addComparator(String nodeType, Comparator comparator) {
		if (comparators.containsKey(nodeType)) {
			comparators.get(nodeType).add(comparator);
		} else {
			comparators.put(nodeType, new ArrayList<Comparator>(FXCollections.observableArrayList(comparator)));
		}
	}
	
	@Override
	public void removeComparator(String nodeType, Comparator comparator) {
		if (comparators.containsKey(nodeType) && comparators.get(nodeType).contains(comparator)) {
			comparators.get(nodeType).remove(comparator);
		}
	}

	@Override
	public String getMetricName() {
		return metricName;
	}

	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}

	@Override
	public Map<String, List<Comparator>> getAllComparator() {
		return comparators;
	}

	@Override
	public List<Comparator> getComparatorForNodeType(String nodeType) {
		return comparators.containsKey(nodeType) ? comparators.get(nodeType) : new ArrayList<Comparator>();
	}

	@Override
	public List<String> getComparatorTypes() {
		return new ArrayList<String>(comparators.keySet());
	}

	@Override
	public Map<String, Boolean> getNodeIgnoreList() {
		return nodeIgnorList;
	}

	public void setNodeIgnorList(Map<String, Boolean> nodeIgnorList) {
		this.nodeIgnorList = nodeIgnorList;
	}

	@Override
	public boolean isTypeIgnored(String NodeType) {
		return nodeIgnorList.containsKey(NodeType) ? nodeIgnorList.get(NodeType) : false;
	}

	public void setComparator(Map<String, List<Comparator>> comparator) {
		this.comparators = comparator;
	}
}
