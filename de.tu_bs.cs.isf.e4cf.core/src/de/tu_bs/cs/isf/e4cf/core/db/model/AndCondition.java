package de.tu_bs.cs.isf.e4cf.core.db.model;

/**
 * 
 * Class that represents where condition with 'AND' operator.
 *
 */
public class AndCondition extends Condition {

	private static final String _CONDITIONTYPE = "AND";

	public AndCondition(ColumnValue... columnValues) {
		super(_CONDITIONTYPE, columnValues);
	}

}
