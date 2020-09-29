package de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAttribute implements Attribute {
	private String attributeKey;
	private List<String> attributeValues;

	public AbstractAttribute() {
		setAttributeValues(new ArrayList<String>());
	}

	@Override
	public String getAttributeKey() {
		return attributeKey;
	}

	@Override
	public List<String> getAttributeValues() {
		return attributeValues;
	}

	@Override
	public void addAttributeValue(String value) {
		attributeValues.add(value);
	}

	public void setAttributeValues(List<String> attributeValues) {
		this.attributeValues = attributeValues;
	}

	public void setAttributeKey(String attributeKey) {
		this.attributeKey = attributeKey;
	}
}
