package de.tu_bs.cs.isf.e4cf.core.db.model;

public class HavingCondition extends Condition{
	private static final String conditiontyp = "having";
	public HavingCondition(ColumnValue...columnValues) {
		super(conditiontyp,columnValues);
	}
	
	
	public String getConditionTyp() {
		return conditiontyp;
	}

	public String havingCondition(Condition condi) {
		String conditionsql = " HAVING ";
		for (ColumnValue c : condi.getColumnValuesList()) {
			conditionsql += c.getColumnName() + " = " + c.getValue();
		}
		conditionsql += ";";
		return conditionsql;
	}
}
