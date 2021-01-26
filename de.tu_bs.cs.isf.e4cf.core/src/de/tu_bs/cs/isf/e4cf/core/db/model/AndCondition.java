package de.tu_bs.cs.isf.e4cf.core.db.model;

public class AndCondition extends Condition {

	private static final String _CONDITIONTYPE = "AND";

	public AndCondition(ColumnValue... columnValues) {
		super(_CONDITIONTYPE, columnValues);
	}

}
