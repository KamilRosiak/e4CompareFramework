package de.tu_bs.cs.isf.e4cf.core.db.model;

public class LikeCondition extends Condition {
	
	private static final String _CONDITIONTYPE = "LIKE";

	public LikeCondition(ColumnValue... columnValues) {
		super(_CONDITIONTYPE, columnValues);
	}
}
