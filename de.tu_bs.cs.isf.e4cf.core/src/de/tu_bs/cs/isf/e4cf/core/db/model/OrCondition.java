package de.tu_bs.cs.isf.e4cf.core.db.model;

public class OrCondition extends Condition{
	private String conditiontyp = "or";
	public OrCondition(ColumnValue...columnValues) {
		super(columnValues);
	}
	
	
	public String getConditionTyp() {
		return conditiontyp;
	}

	public String or_Condition(Condition condi) {
		   String conditionsql= " WHERE ";
		   for(ColumnValue c :condi.getConditionValue() ) {
			   conditionsql += c.getColumnName() + " " + c.getSymbol()+ "'" + c.getValue() + "' OR " ;
		   }
		   conditionsql = conditionsql.substring(0, conditionsql.length() - 4);
		   conditionsql += ";";
	      return conditionsql;
	   }

}
