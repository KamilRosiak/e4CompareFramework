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
}
