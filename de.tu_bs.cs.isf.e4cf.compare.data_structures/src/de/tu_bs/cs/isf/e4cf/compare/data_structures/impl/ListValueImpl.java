package de.tu_bs.cs.isf.e4cf.compare.data_structures.impl;

import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractValue;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

public class ListValueImpl<T> extends AbstractValue<List<T>> {
	private static final long serialVersionUID = -3999633743705136931L;

	public ListValueImpl(List<T> value) {
		super(value);
	}

	@Override
	public boolean equals(Value<List<T>> val) {
		return this.getValue().equals(val.getValue());
	}
	
	@Override
	public String toString() {
		return this.getValue().toString();
	}

}
