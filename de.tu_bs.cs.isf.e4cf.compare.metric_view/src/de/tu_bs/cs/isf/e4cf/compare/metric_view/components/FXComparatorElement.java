package de.tu_bs.cs.isf.e4cf.compare.metric_view.components;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;

public class FXComparatorElement {

private static final Float DEFAULT_WEIGHT = 0f;

	private String comparatorType; 
	private Comparator comparator;
	private Float weight;
	
	public FXComparatorElement(Comparator comparator, Float weight) {
		this.comparatorType = splitByLastDot(comparator);
		this.comparator = comparator;
		this.weight = weight;
	}
	
	public FXComparatorElement(String type) {
		this.comparator = null;
		this.comparatorType = null;
		this.weight = 0f;
	}
	
	public FXComparatorElement(Comparator comparator) {
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
	
	private String splitByLastDot(Comparator elem) {	
		return elem.getClass().toString().substring(elem.getClass().toString().lastIndexOf(".") + 1);
	}

}
