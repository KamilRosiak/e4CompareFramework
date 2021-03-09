package de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces;

import java.io.Serializable;
import java.util.Set;

public interface Attribute extends Serializable{
    /**
     * Returns the attribute key
     */
    public String getAttributeKey();

    /**
     * Returns the value of this attributes , e.g., String key or void
     */
    public Set<String> getAttributeValues();

    /**
     * Add an value to this attribute
     */
    public void addAttributeValue(String value);

    /**
     * This method returns the highest similarity value between the given attribute
     * an this attribute.
     */
    public float compare(Attribute attr);

    public default boolean isAttributeOfSameType(Attribute attr) {
	return attr.getAttributeKey().equals(getAttributeKey());
    }

}
