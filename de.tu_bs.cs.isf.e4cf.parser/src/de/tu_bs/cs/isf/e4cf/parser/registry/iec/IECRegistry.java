package de.tu_bs.cs.isf.e4cf.parser.registry.iec;

import de.tu_bs.cs.isf.e4cf.parser.registry.INamedEntry;

public enum IECRegistry implements INamedEntry {
	POU_REG("POU Registry"),
	TYPE_REG("Type Registry"),
	LOCAL_VAR_REG("Local POU Variable Registry"),
	LOCAL_ACTION_REG("Local POU Action Registry");
	
	private final String name;
	
	IECRegistry(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
};
