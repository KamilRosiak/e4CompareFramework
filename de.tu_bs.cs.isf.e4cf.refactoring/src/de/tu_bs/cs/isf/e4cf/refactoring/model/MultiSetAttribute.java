package de.tu_bs.cs.isf.e4cf.refactoring.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

		for (Entry<Value, Value> entry : mappedValues.entrySet()) {

			boolean hasPartner = false;
			for (Value value : newAttribute.getAttributeValues()) {

				if (value.getValue().equals(entry.getKey().getValue())) {
					hasPartner = true;
					break;
				}
			}
			if (!hasPartner) {
				mapping.put(entry.getKey(), entry.getValue());
			}
		}

		this.attribute = newAttribute;
		this.mappedValues = mapping;

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

	public Set<MultiSetAttribute> addValue(Value newValue) {

		Set<MultiSetAttribute> affectedAttributes = new HashSet<MultiSetAttribute>();

		if (!mappedValues.containsKey(newValue)) {
			mappedValues.put(newValue, new StringValueImpl((String) newValue.getValue()));
		}

		affectedAttributes.add(this);
		
		if(!attribute.getAttributeValues().contains(newValue)) {
			attribute.addAttributeValue(newValue);
		}

		List<MultiSetAttribute> exclude = new ArrayList<MultiSetAttribute>();
		exclude.add(this);
		for (MultiSetAttribute reference : references) {
			if (!exclude.contains(reference)) {
				reference.addValue(newValue, exclude, affectedAttributes);
			}
		}
		return affectedAttributes;
	}

	public void addValue(Value newValue, List<MultiSetAttribute> exclude, Set<MultiSetAttribute> affectedAttributes) {
		if (!mappedValues.containsKey(newValue)) {
			mappedValues.put(newValue, new StringValueImpl((String) newValue.getValue()));
		}

		if(!attribute.getAttributeValues().contains(newValue)) {
			attribute.addAttributeValue(newValue);
		}

		affectedAttributes.add(this);
		exclude.add(this);

		for (MultiSetAttribute reference : references) {
			if (!exclude.contains(reference)) {
				reference.addValue(newValue, exclude, affectedAttributes);
			}
		}
	}

	public Set<MultiSetAttribute> editValue(Value editedValue) {

		Set<MultiSetAttribute> affectedAttributes = new HashSet<MultiSetAttribute>();

		if (!mappedValues.containsKey(editedValue)) {
			mappedValues.clear();
			mappedValues.put(editedValue, new StringValueImpl((String) editedValue.getValue()));
		}
		
		if(!attribute.getAttributeValues().contains(editedValue)) {
			attribute.getAttributeValues().clear();
			attribute.getAttributeValues().add(editedValue);
		}

		affectedAttributes.add(this);

		List<MultiSetAttribute> exclude = new ArrayList<MultiSetAttribute>();
		exclude.add(this);
		for (MultiSetAttribute reference : references) {
			if (!exclude.contains(reference)) {
				reference.editValue(editedValue, exclude, affectedAttributes);
			}
		}
		return affectedAttributes;
	}

	public void editValue(Value editedValue, List<MultiSetAttribute> exclude,
			Set<MultiSetAttribute> affectedAttributes) {
		if (!mappedValues.containsKey(editedValue)) {
			mappedValues.clear();
			mappedValues.put(editedValue, new StringValueImpl((String) editedValue.getValue()));
		}

		if(!attribute.getAttributeValues().contains(editedValue)) {
			attribute.getAttributeValues().clear();
			attribute.getAttributeValues().add(editedValue);
		}

		affectedAttributes.add(this);
		exclude.add(this);

		for (MultiSetAttribute reference : references) {
			if (!exclude.contains(reference)) {
				reference.editValue(editedValue, exclude, affectedAttributes);
			}
		}
	}

	public Set<MultiSetAttribute> editKey(String newKey) {

		Set<MultiSetAttribute> affectedAttributes = new HashSet<MultiSetAttribute>();

		if (!key.equals(newKey)) {
			key = newKey;
		}

		affectedAttributes.add(this);
		
		if(!attribute.getAttributeKey().equals(newKey)) {
			attribute.setAttributeKey(newKey);
		}
		

		List<MultiSetAttribute> exclude = new ArrayList<MultiSetAttribute>();
		exclude.add(this);
		for (MultiSetAttribute reference : references) {
			if (!exclude.contains(reference)) {
				reference.editKey(newKey, exclude, affectedAttributes);
			}
		}
		return affectedAttributes;
	}

	public void editKey(String newKey, List<MultiSetAttribute> exclude, Set<MultiSetAttribute> affectedAttributes) {
		if (!key.equals(newKey)) {
			key = newKey;
		}

		if(!attribute.getAttributeKey().equals(newKey)) {
			attribute.setAttributeKey(newKey);
		}

		affectedAttributes.add(this);
		exclude.add(this);

		for (MultiSetAttribute reference : references) {
			if (!exclude.contains(reference)) {
				reference.editKey(newKey, exclude, affectedAttributes);
			}
		}
	}

	public Attribute restore() {
		Attribute restoredAttribute = new AttributeImpl(key);

		for (Value value : mappedValues.values()) {
			restoredAttribute.addAttributeValue(value);
		}
		return restoredAttribute;
	}

}
