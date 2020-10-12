package de.tu_bs.cs.isf.e4cf.core.compare.templates;

import java.io.Serializable;

import de.tu_bs.cs.isf.e4cf.core.compare.interfaces.IWeighted;

/**
 * This class represents a abstract attribute with basic functionality. Attributes are needed to compare Items from a Model with each other.
 * The compare method is given by a interface that is implemented in the template classes.
 * @author {Kamil Rosiak}

 */
public abstract class AbstractAttribute implements Serializable, IWeighted {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8662748979463105622L;
	
	private String attrName, attrDescription;
	private boolean isEnabled = false;
	private float weight = 0;
	
	public AbstractAttribute(String attrName, String attrDescription) {
		setAttributeName(attrName);
		setAttributeDescription(attrDescription);
	}
	
	/**
	 * @return The name of the attribute.
	 */
	public String getAttributeName() {
		return attrName;
	}
	/**
	 * @param name that is shown in all views.
	 */
	public void setAttributeName(String attributeName) {
		attrName = attributeName;
	}
	
	/**
	 * @return description of the attribute.
	 */
	public String getAttributDescription() {
		return attrDescription;
	}
	/**
	 * @param attributeDescription declarative description of this module that is showen in all views.
	 */
	public void setAttributeDescription(String attributeDescription) {
		attrDescription = attributeDescription;
	}

	/**
	 * Returns the state of this attribute.
	 * @return
	 */
	public boolean isEnabled() {
		return isEnabled;
	}
	/**
	 *  Enables this attribute so that the compare engine will compare with it.
	 * @param isEnabled
	 */
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	/**
	 * Get the current weight of this Attribute.
	 */
	@Override
	public float getWeight() {
		return weight;
	}
	/**
	 * Set the current weight of this attribute.
	 */
	public void setWeight(float weight) {
		this.weight = weight;
	}
}
