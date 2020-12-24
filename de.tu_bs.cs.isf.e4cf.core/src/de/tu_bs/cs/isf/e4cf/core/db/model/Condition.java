package de.tu_bs.cs.isf.e4cf.core.db.model;

import java.util.ArrayList;
import java.util.List;

public class Condition {
	 private List<ColumnValue> columnvalue;
	 private String conditiontyp;
	  
	  public Condition(String pConditiontyp, ColumnValue...columnValues) {
		  List<ColumnValue> columnVa = new ArrayList<>();
		  for (ColumnValue c: columnValues) {
				columnVa.add(c);
			}
		  columnvalue = columnVa;
		  conditiontyp = pConditiontyp;
	  }
	  
	  
	  public List<ColumnValue> getConditionValue() {
			return columnvalue;
		}
	  
		public void setConditonValue(ColumnValue...value ) {
			for (ColumnValue c: value) {
				columnvalue.add(c);
			}
			
		}
		
		public String getConditionTyp() {
			return conditiontyp;
		}

		public void setConditionTyp(String conditiontyp) {
			this.conditiontyp = conditiontyp;
		}

		@Override
		public String toString() {
			
			return "toString" + columnvalue.toString() + "  " + conditiontyp;
		}
}
