package de.tu_bs.cs.isf.e4cf.core.db.model;

/**
 * 
 * Class that represents the column in a table.
 *
 */
public class Column {

	private String name;
	private String type;
	private boolean primaryKey = false;
	private boolean unique = false;
	private boolean autoIncrement = false;
	private boolean notNull = false;

	public Column() {
	}

	public Column(final String pName, final String pType, final Boolean pPrimaryKey, final Boolean pUnique,
			final Boolean pIncrement, final Boolean pNotNull) {
		name = pName;
		type = pType;
		primaryKey = pPrimaryKey;
		unique = pUnique;
		autoIncrement = pIncrement;
		notNull = pNotNull;
	}

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

	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(final boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	public boolean isNotNull() {
		return notNull;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}

	@Override
	public String toString() {
		return "Attribut [name=" + name + ", type=" + type + "]";
	}

}
