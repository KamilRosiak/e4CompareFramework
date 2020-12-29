package de.tu_bs.cs.isf.e4cf.core.db.model;

public class AddCondition extends Condition {
	private String conditiontyp = "and";
	public AddCondition(ColumnValue...columnValues) {
		super(columnValues);
	}
	
	
	public String getConditionTyp() {
		return conditiontyp;
	}

	public String and_Condition(Condition condi) {
		   String conditionsql= " WHERE ";
		   for(ColumnValue c :condi.getConditionValue() ) {
			   conditionsql += c.getColumnName() + " " + c.getSymbol()+"' " + c.getValue() + "' AND " ;
		   }
		   conditionsql = conditionsql.substring(0, conditionsql.length() - 4);
		   conditionsql += ";";
	      return conditionsql;
	   }

}
