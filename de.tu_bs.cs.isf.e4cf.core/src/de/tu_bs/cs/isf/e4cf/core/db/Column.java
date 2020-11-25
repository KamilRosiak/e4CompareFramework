package de.tu_bs.cs.isf.e4cf.core.db;

public class Column {

	private String name;
	private String type;
	private boolean primaryKey;
	private boolean unique;

	public Column(final String pName, final String pType, final Boolean pPrimaryKey,final Boolean pUnique) {
		name = pName;
		type = pType;
		primaryKey = pPrimaryKey;
		unique = pUnique;
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
	
	public Boolean isUnique() {
		return unique;
	}
	
	public void setUnique(final boolean unique) {
		this.unique = unique;
	}

	@Override
	public String toString() {
		return "Attribut [name=" + name + ", type=" + type + "]";
	}

}
