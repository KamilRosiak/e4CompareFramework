package de.tu_bs.cs.isf.e4cf.core.db.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * The class which stands for the where condition in an sql statement.
 *
 */
public class Condition {

	private List<ColumnValue> columnValuesList;
	private String type;

	protected Condition(ColumnValue... columnValues) {
		List<ColumnValue> columnVa = new ArrayList<>();
		for (ColumnValue c : columnValues) {
			columnVa.add(c);
		}
		columnValuesList = columnVa;
	}

	protected Condition(String type, ColumnValue... columnValues) {
		List<ColumnValue> columnVa = new ArrayList<>();
		for (ColumnValue c : columnValues) {
			columnVa.add(c);
		}
		columnValuesList = columnVa;
		this.type = type;
	}

	public List<ColumnValue> getColumnValuesList() {
		return columnValuesList;
	}

	public void setConditonValue(ColumnValue... value) {
		for (ColumnValue c : value) {
			columnValuesList.add(c);
		}

	}

	public String getConditionAsSql() {
		String conditionSql = " WHERE ";
		for (ColumnValue c : columnValuesList) {
			conditionSql += c.getColumnName() + " = '" + c.getValue() + "' " + type + " ";
		}
		conditionSql = conditionSql.substring(0, conditionSql.length() - 4) + ";";
		return conditionSql;
	}

}
