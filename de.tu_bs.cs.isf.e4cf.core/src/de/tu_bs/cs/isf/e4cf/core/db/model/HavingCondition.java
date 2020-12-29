package de.tu_bs.cs.isf.e4cf.core.db.model;

public class HavingCondition extends Condition{
	private String conditiontyp = "having";
	public HavingCondition(ColumnValue...columnValues) {
		super(columnValues);
	}
	
	
	public String getConditionTyp() {
		return conditiontyp;
	}

	public String havingCondition(Condition condi) {
		String conditionsql = " HAVING ";
		for (ColumnValue c : condi.getConditionValue()) {
			conditionsql += c.getColumnName() + " " + c.getSymbol() + " " + c.getValue();
		}
		conditionsql += ";";
		return conditionsql;
	}
}
