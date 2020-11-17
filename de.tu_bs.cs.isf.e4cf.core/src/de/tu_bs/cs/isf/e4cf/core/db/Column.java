package de.tu_bs.cs.isf.e4cf.core.db;

public class Column {

	private String name;
	private String type;

	public Column(final String pName, final String pType) {
		name = pName;
		type = pType;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Attribut [name=" + name + ", type=" + type + "]";
	}

}
