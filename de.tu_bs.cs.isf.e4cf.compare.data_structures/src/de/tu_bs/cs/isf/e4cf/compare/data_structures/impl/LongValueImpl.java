package de.tu_bs.cs.isf.e4cf.compare.data_structures.impl;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractValue;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

public class LongValueImpl extends AbstractValue<Long> {
	
	public LongValueImpl(long value) {
		super(value);		
	}

	@Override
	public boolean equals(Value<Long> val) {
		if(val == null) {
			return false;
		}		
		return this.getValue().equals(val.getValue());
	}

}
