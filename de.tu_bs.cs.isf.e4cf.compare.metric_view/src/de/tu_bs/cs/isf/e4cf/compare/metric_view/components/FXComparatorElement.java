package de.tu_bs.cs.isf.e4cf.compare.metric_view.components;

import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;
import de.tu_bs.cs.isf.e4cf.compare.comparator.templates.AbstractNodeComparator;

public class FXComparatorElement {

private static final Float DEFAULT_WEIGHT = 0f;

	private String comparatorType; 
	private Comparator comparator;
	private Float weight;
	private String name;
	private Boolean ignored;
	
	public FXComparatorElement(Comparator comparator, Float weight) {
		this.comparatorType = comparator.getSupportedNodeType();
		this.comparator = comparator;
		this.weight = comparator.getWeight();
		this.name = this.comparator.toString().substring(0, this.comparator.toString().lastIndexOf("@"));
		this.ignored = false;
	}
	
	public FXComparatorElement(Comparator comparator, Float weight, Boolean ignored) {
		this.comparatorType = comparator.getSupportedNodeType();
		this.comparator = comparator;
		this.weight = comparator.getWeight();
		this.name = this.comparator.toString().substring(0, this.comparator.toString().lastIndexOf("@"));
		this.ignored = ignored;
	}
	/**
	 * typeNode Constructor
	 */
	public FXComparatorElement(String type) {
		this.comparator = null;
		this.comparatorType = type;
		this.weight = DEFAULT_WEIGHT;
		this.name = type;
		this.ignored = false;
	}
	
	public FXComparatorElement(Comparator comparator) {
		this(comparator, comparator.getWeight());
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
	
	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
		if (this.comparator != null) {
			this.comparator.setWeight(weight);
		}
	}
	
	private String splitByLastDot(Comparator elem) {	
		return elem.getClass().toString().substring(elem.getClass().toString().lastIndexOf(".") + 1);
	}
	
	public String getName() {
		return name;
	}
	
	public void setIgnored(Boolean ignored) {
		this.ignored = ignored;
	}
	
	public Boolean getIgnored() {
		return ignored;
	}
}
