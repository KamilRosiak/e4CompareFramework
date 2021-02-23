package de.tu_bs.cs.isf.e4cf.core.db.model;

/**
 * 
 * Class that represents where condition with 'OR' operator.
 *
 */
public class OrCondition extends Condition {

	private static final String _CONDITIONTYPE = "OR";

	public OrCondition(ColumnValue... columnValues) {
		super(_CONDITIONTYPE, columnValues);
	}

}
