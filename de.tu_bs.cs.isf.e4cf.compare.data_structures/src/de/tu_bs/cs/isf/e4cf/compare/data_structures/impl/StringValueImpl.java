package de.tu_bs.cs.isf.e4cf.compare.data_structures.impl;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractValue;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

/**
 * Implementation of value
 */
public class StringValueImpl extends AbstractValue<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 532145710145962535L;

	public StringValueImpl(String value) {
		super(value);
	}

	@Override
	public boolean equals(Value<String> val) {
		if (val instanceof StringValueImpl) {
			return val.getValue().equals(getValue());
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return getValue();
	}

}
