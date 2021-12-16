package de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public interface Attribute extends Serializable {

	public UUID getUuid();

	public void setUuid(UUID uuid);

	/**
	 * Returns the attribute key
	 */
	public String getAttributeKey();

	/**
	 * Set the attribute key
	 */
	public void setAttributeKey(String attributeKey);

	/**
	 * Returns the value of this attributes , e.g., String key or void
	 */
	public List<Value> getAttributeValues();

	public Value getAttributeValue(Value value);

	/**
	 * Add an value to this attribute
	 */
	public void addAttributeValue(Value value);

	/**
	 * Add an values to this attribute
	 */
	public void addAttributeValues(List<Value> value);

	/**
	 * This method returns the highest similarity value between the given attribute
	 * an this attribute.
	 */
	public float compare(Attribute attr);

	public default boolean keyEquals(Attribute attr) {
		return attr.getAttributeKey().equals(getAttributeKey());
	}

	public default boolean containsValue(Value value) {
		for (Value val : getAttributeValues()) {
			if (val.equals(value)) {
				return true;
			}
		}
		return false;
	}

	public Attribute cloneAttribute();

}
