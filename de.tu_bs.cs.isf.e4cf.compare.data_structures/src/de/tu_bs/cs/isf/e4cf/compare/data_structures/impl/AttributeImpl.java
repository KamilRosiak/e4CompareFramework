package de.tu_bs.cs.isf.e4cf.compare.data_structures.impl;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractAttribute;

/**
 * Concrete implementation of the Attribute interface.
 * @author Kamil Rosiak
 *
 */
public class AttributeImpl extends AbstractAttribute {
	
	/**
	 * Create an Attribute with only a key and no value
	 */
	public AttributeImpl(String attrKey) {
		setAttributeKey(attrKey);
	}
	
	/**
	 * Create an Attribute with a kay and corrosponding value
	 */
	public AttributeImpl(String attrKey, String attrValue) {
		this(attrKey);
		addAttributeValue(attrValue);
	}
}
