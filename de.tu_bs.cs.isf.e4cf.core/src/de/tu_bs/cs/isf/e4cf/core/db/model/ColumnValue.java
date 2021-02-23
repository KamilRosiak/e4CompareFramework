package de.tu_bs.cs.isf.e4cf.core.db.model;

/**
 * 
 * Class to represent the value of column in a row.
 *
 */
public class ColumnValue {

	private String columnName;
	private Object value;

	public ColumnValue(String pName, Object pValue) {
		columnName = pName;
		value = pValue;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public String toString() {
		return "ColumnValue [columnName=" + columnName + ", value=" + value + "]";
	}

}
