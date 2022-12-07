package de.tu_bs.cs.isf.e4cf.compare.data_structures.impl;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractValue;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

public class FloatValueImpl extends AbstractValue<Float> {
	private static final long serialVersionUID = -1441312555598530696L;

	public FloatValueImpl(float value) {
		super(value);
	}

	@Override
	public boolean equals(Value<Float> val) {
		if (val == null) {
			return false;
		}
		return this.getValue().equals(val.getValue());
	}

}
