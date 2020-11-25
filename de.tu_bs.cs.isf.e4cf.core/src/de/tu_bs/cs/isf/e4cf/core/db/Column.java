package de.tu_bs.cs.isf.e4cf.core.db;

public class Column {

	private String name;
	private String type;
	private boolean primaryKey;

	public Column(final String pName, final String pType, final Boolean pPrimaryKey) {
		name = pName;
		type = pType;
		primaryKey = pPrimaryKey;
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
	
	public Boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(final boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Override
	public String toString() {
		return "Attribut [name=" + name + ", type=" + type + "]";
	}

}
