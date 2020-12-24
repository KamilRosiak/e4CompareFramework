package de.tu_bs.cs.isf.e4cf.core.db.model;

public class ColumnValue {

	private String columnName;
	private Object value;
	private String symbol = "=";

	public ColumnValue(String pName, Object pValue) {
		columnName = pName;
		value = pValue;
	}
	
	public ColumnValue(String pName, Object pValue, String pSymbol) {
		columnName = pName;
		value = pValue;
		symbol = pSymbol;
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
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		return "ColumnValue [columnName=" + columnName + ", value=" + value + "]";
	}

}
