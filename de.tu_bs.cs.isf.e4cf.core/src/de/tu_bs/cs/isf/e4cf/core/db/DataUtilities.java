package de.tu_bs.cs.isf.e4cf.core.db;

import de.tu_bs.cs.isf.e4cf.core.db.model.ColumnValue;
import de.tu_bs.cs.isf.e4cf.core.db.model.Condition;

public class DataUtilities {
	
	/**
	 * Method of obtaining conditions of data from a table
	 * @param condi
	 * @return
	 */
	 public String W_condition(Condition condi) {
		  final String sql;
		  if(condi != null) {
			  switch(condi.getConditionTyp()) {
			   case "and" : sql =  and_Condition(condi);
			                break;
			   case "or"  : sql = or_Condition(condi);
			                 break;
			   case "like": sql = like_Condition(condi);
			                break;
			   default:    sql= default_Condition(condi); 
			   
			   }
			   return sql;
		  } else {
			  return ";";
		  }
		 
	   }
	     
	  public String and_Condition(Condition condi) {
		   String conditionsql= " WHERE ";
		   for(ColumnValue c :condi.getConditionValue() ) {
			   conditionsql += c.getColumnName() + " " + c.getSymbol()+" " + c.getValue() + " AND " ;
		   }
		   conditionsql = conditionsql.substring(0, conditionsql.length() - 4);
		   conditionsql += ";";
	      return conditionsql;
	   }
	   
	   public String or_Condition(Condition condi) {
		   String conditionsql= " WHERE ";
		   for(ColumnValue c :condi.getConditionValue() ) {
			   conditionsql += c.getColumnName() +" " + c.getSymbol() +" " + c.getValue() + " OR " ;
		   }
		   conditionsql = conditionsql.substring(0, conditionsql.length() - 4);
		   conditionsql += ";";
	      return conditionsql;
	   }
	   
	   public String like_Condition(Condition condi) {
		   String conditionsql= " WHERE ";
		   for(ColumnValue c :condi.getConditionValue() ) {
			   conditionsql += c.getColumnName() + " LIKE " + "'"+ c.getValue()+"'";
		   }
		   conditionsql += ";";
	      return conditionsql;
	   }
	   
	   public String default_Condition(Condition condi) {
			   String conditionsql= " WHERE ";
			   for(ColumnValue c :condi.getConditionValue() ) {
				   conditionsql += c.getColumnName() + " " + c.getSymbol() + " " + c.getValue();
			   }
			   conditionsql += ";";
		      return conditionsql; 
		 
	   }
	   
	   
	

}
