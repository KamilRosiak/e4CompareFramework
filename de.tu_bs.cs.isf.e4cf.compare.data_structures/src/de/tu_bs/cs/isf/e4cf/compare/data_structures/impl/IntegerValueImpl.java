package de.tu_bs.cs.isf.e4cf.compare.data_structures.impl;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractValue;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

public class IntegerValueImpl extends AbstractValue<Integer> {

	public IntegerValueImpl(int value) {
		super(value);		
	}

	@Override
	public boolean equals(Value<Integer> val) {
		if(val == null) {
			return false;
		}		
		return this.getValue().equals(val.getValue());
	}

}
