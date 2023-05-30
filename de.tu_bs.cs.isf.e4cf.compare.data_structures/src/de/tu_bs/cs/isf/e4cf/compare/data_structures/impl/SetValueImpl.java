package de.tu_bs.cs.isf.e4cf.compare.data_structures.impl;

import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractValue;

public class SetValueImpl<T> extends AbstractValue<Set<T>> {
	private static final long serialVersionUID = 976246729027612665L;

	public SetValueImpl(Set<T> value) {
		super(value);
	}
}
