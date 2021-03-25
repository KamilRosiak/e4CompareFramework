package de.tu_bs.cs.isf.e4cf.compare.data_structures.impl;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractValue;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

/**
 * Implementation of value
 */
public class StringValueImpl extends AbstractValue<String> {

	public StringValueImpl(String value) {
		super(value);
	}

	@Override
	public boolean equals(Value<String> val) {
		if(val instanceof StringValueImpl) {
			return val.getValue().equals(getValue());
		} else {
			return false;
		}
	}
}
