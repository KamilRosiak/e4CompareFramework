package de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractAttribute implements Attribute {
	private String attributeKey;
	private Set<String> attributeValues;

	public AbstractAttribute() {
		setAttributeValues(new HashSet<String>());
	}

	@Override
	public float compare(Attribute attr) {
	    float similarity = 0f;
	    //checks if the keys are equals
	    if(attr.getAttributeKey().equals(getAttributeKey())) {
		similarity += 0.25f;
	    }
	    for(String first_value : attr.getAttributeValues()) {
       		for(String second_value : getAttributeValues()) {
       		    if(first_value.equals(second_value)) {
       			similarity += 0.75f;
       			break;
       		    }
		}
	    }
	    return similarity;
	}
	
	@Override
	public String getAttributeKey() {
		return attributeKey;
	}

	@Override
	public Set<String> getAttributeValues() {
		return attributeValues;
	}

	@Override
	public void addAttributeValue(String value) {
		attributeValues.add(value);
	}

	public void setAttributeValues(Set<String> attributeValues) {
		this.attributeValues = attributeValues;
	}

	public void setAttributeKey(String attributeKey) {
		this.attributeKey = attributeKey;
	}
}
