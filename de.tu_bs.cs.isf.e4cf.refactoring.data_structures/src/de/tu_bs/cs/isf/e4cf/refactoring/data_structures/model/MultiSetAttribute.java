package de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

public class MultiSetAttribute {
	private Attribute attribute;

	private Map<Value, Value> mappedValues;

	private Set<MultiSetAttribute> references;

	private String key;

	private MultiSetNode multiSetNode;

	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public boolean containsValue(Value value) {
		return mappedValues.containsKey(value);
	}

	public MultiSetNode getMultiSetNode() {
		return multiSetNode;
	}

	public void setMultiSetNode(MultiSetNode multiSetNode) {
		this.multiSetNode = multiSetNode;
	}

	public MultiSetAttribute(Attribute attribute, MultiSetNode multiSetNode) {
		this.attribute = attribute;
		this.references = new HashSet<MultiSetAttribute>();
		this.mappedValues = new HashMap<Value, Value>();
		this.multiSetNode = multiSetNode;

		for (Value value : attribute.getAttributeValues()) {
			mappedValues.put(value, new StringValueImpl((String) value.getValue()));
		}
		key = attribute.getAttributeKey();

	}

	public void setAttribute(Attribute newAttribute) {

		Map<Value, Value> mapping = new HashMap<Value, Value>();

		for (Value value : newAttribute.getAttributeValues()) {

			mapping.put(value, new StringValueImpl((String) value.getValue()));

		}
		
		Map<Value,Value> aggregatedMapping = new HashMap<Value, Value>();

		for (Entry<Value, Value> entry : mappedValues.entrySet()) {
			
			boolean hasPartner = false;
			for (Value value : newAttribute.getAttributeValues()) {

				
				if (value.getValue().equals(entry.getKey().getValue())) {
					aggregatedMapping.put(value, entry.getValue());					
					hasPartner = true;
					break;
				}
			}
			if(!hasPartner) {
				aggregatedMapping.put(entry.getKey(), entry.getValue());
			}			
		}
		
		this.attribute = newAttribute;
		this.mappedValues = aggregatedMapping;

	}

	public Attribute getAttribute() {
		return attribute;
	}

	public Set<MultiSetAttribute> getReferences() {
		return references;
	}

	public void addReference(MultiSetAttribute multiSetAttribute) {
		references.add(multiSetAttribute);
	}

	public void addValue(Value newValue) {		

		mappedValues.put(newValue, new StringValueImpl((String) newValue.getValue()));				
	}
	

	public void editValue(Value editedValue) {		

		mappedValues.clear();
		mappedValues.put(editedValue, new StringValueImpl((String) editedValue.getValue()));			
	}

	
	public Attribute restore() {
		Attribute restoredAttribute = new AttributeImpl(key);

		for (Value value : mappedValues.values()) {
			restoredAttribute.addAttributeValue(value);
		}
		return restoredAttribute;
	}
}
