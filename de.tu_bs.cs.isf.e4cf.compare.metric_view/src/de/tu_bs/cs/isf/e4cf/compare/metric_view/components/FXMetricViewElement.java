package de.tu_bs.cs.isf.e4cf.compare.metric_view.components;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;

public class FXMetricViewElement {

private static final Float DEFAULT_WEIGHT = 0f;

	private String comparatorType; 
	private Comparator comparator;
	private Float weight;
	
	public FXMetricViewElement(Comparator comparator, Float weight) {
		this.comparatorType = "StringComparator";
		this.comparator = comparator;
		this.weight = weight;
	}
	
	public FXMetricViewElement(String comparatorType) {
		this.comparatorType = comparatorType;
		this.comparator = null;
		this.weight= DEFAULT_WEIGHT;
	}
	
	public FXMetricViewElement(Comparator comparator) {
		this(comparator, DEFAULT_WEIGHT);
	}

	public Comparator getComparator() {
		return comparator;
	}
	
	public void setComparator(Comparator comparator) {
		this.comparator = comparator;
	}
	
	public String getComparatorType() {
		return comparatorType;
	}
	
	public void setComparatorType(String comparatorType) {
		this.comparatorType = comparatorType;
	}
	
	
	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

}
