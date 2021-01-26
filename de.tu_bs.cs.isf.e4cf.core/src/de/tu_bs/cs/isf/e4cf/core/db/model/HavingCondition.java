package de.tu_bs.cs.isf.e4cf.core.db.model;

public class HavingCondition extends Condition {
	
	private static final String conditiontyp = "HAVING";

	public HavingCondition(ColumnValue... columnValues) {
		super(conditiontyp, columnValues);
	}
	
}
