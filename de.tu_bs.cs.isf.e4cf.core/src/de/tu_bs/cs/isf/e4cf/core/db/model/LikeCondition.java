package de.tu_bs.cs.isf.e4cf.core.db.model;

public class LikeCondition extends Condition {
	private String conditiontyp = "like";
	public LikeCondition(ColumnValue...columnValues) {
		super(columnValues);
	}
	
	
	public String getConditionTyp() {
		return conditiontyp;
	}

	 public String like_Condition(Condition condi) {
		   String conditionsql= " WHERE ";
		   for(ColumnValue c :condi.getConditionValue() ) {
			   conditionsql += c.getColumnName() + " LIKE " + "'"+ c.getValue()+"'";
		   }
		   conditionsql += ";";
	      return conditionsql;
	   }
}
