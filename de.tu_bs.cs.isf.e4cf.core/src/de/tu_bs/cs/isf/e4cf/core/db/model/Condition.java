package de.tu_bs.cs.isf.e4cf.core.db.model;

import java.util.ArrayList;
import java.util.List;

public class Condition {
	 private List<ColumnValue> columnvalue;  
	  public Condition(ColumnValue...columnValues) {
		  List<ColumnValue> columnVa = new ArrayList<>();
		  for (ColumnValue c: columnValues) {
				columnVa.add(c);
			}
		  columnvalue = columnVa;
	  }
	  
	  
	  public List<ColumnValue> getConditionValue() {
			return columnvalue;
		}
	  
		public void setConditonValue(ColumnValue...value ) {
			for (ColumnValue c: value) {
				columnvalue.add(c);
			}
			
		}

		 public String default_Condition(Condition condi) {
			   String conditionsql= " WHERE ";
			   for(ColumnValue c :condi.getConditionValue() ) {
				   conditionsql += c.getColumnName() + " " + c.getSymbol() + " " + c.getValue();
			   }
			   conditionsql += ";";
		      return conditionsql; 
		 }
		 
		@Override
		public String toString() {
			
			return "toString" + columnvalue.toString() + "  " ;
		}
}
