package de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces;

import java.util.UUID;

public abstract class AbstractValue<Type> implements Value<Type> {
	private static final long serialVersionUID = 4244536325074876104L;
	private UUID uuid;
	private Type value;

	public AbstractValue(Type value) {
		setUUID(UUID.randomUUID());
		setValue(value);
	}

	@Override
	public UUID getUUID() {
		return this.uuid;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	@Override
	public Type getValue() {
		return this.value;
	}

	@Override
	public void setValue(Type value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return String.format("UUID: %s, Value: %s", this.getUUID().toString(), this.getValue().toString());
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Value<?>)) return false;
		
		Value<?> otherVal = (Value<?>) other;
		return this.getUUID().equals(otherVal.getUUID()) &&
				this.getValue().equals(otherVal.getValue());
	}
	
	@Override
	public boolean equals(Value<Type> val) {
		return this.getUUID().equals(val.getUUID()) && 
				this.getValue().equals(val.getUUID());
	}
	
	
}
