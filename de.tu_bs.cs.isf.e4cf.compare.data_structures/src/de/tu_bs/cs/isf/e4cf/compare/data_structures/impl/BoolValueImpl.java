package de.tu_bs.cs.isf.e4cf.compare.data_structures.impl;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractValue;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

public class BoolValueImpl extends AbstractValue<Boolean> {
	private static final long serialVersionUID = 2555555287243790641L;

	public BoolValueImpl(Boolean value) {
		super(value);
	}

	@Override
	public boolean equals(Value<Boolean> val) {
		return this.getValue().booleanValue() == val.getValue().booleanValue();
	}
	
	@Override
	public String toString() {
		return this.getValue().toString();
	}
	
}
