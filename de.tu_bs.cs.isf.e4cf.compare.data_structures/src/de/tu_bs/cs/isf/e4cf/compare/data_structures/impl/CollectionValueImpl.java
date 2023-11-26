package de.tu_bs.cs.isf.e4cf.compare.data_structures.impl;

import java.util.Collection;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractValue;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;

public class CollectionValueImpl<T extends Collection<?>> extends AbstractValue<Collection<?>> {
	private static final long serialVersionUID = -2277406314560325962L;

	public CollectionValueImpl(Collection<T> value) {
		super(value);
	}

	@Override
	public boolean equals(Value<Collection<?>> val) {
		return this.getUUID().equals(val.getUUID()) && this.getValue().equals(val.getValue());
	}
	
	

}
