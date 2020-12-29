package de.tu_bs.cs.isf.e4cf.core.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.tu_bs.cs.isf.e4cf.core.db.model.AddCondition;
import de.tu_bs.cs.isf.e4cf.core.db.model.Column;
import de.tu_bs.cs.isf.e4cf.core.db.model.Sorting;
import de.tu_bs.cs.isf.e4cf.core.db.model.ColumnValue;
import de.tu_bs.cs.isf.e4cf.core.db.model.Condition;
import de.tu_bs.cs.isf.e4cf.core.db.model.HavingCondition;
import de.tu_bs.cs.isf.e4cf.core.db.model.LikeCondition;
import de.tu_bs.cs.isf.e4cf.core.db.model.OrCondition;

public class DataUtilities {

	/**
	 * Method of obtaining conditions of data from a table
	 * 
	 * @param condi
	 * @return
	 */
	 public String W_condition(Condition condi) {
		  final String sql;
		  if(condi != null) {
			  switch(condi.getClass().getSimpleName()) {
			   case "AddCondition" : sql = new AddCondition().and_Condition(condi);
			                break;
			   case "OrCondition"  : sql = new OrCondition().or_Condition(condi);
			                 break;
			   case "LikeCondition": sql = new LikeCondition().like_Condition(condi);
			                 break;
			   case "HavingCondition":sql = new HavingCondition().havingCondition(condi);
					         break;
			   default:    sql= new Condition().default_Condition(condi) ; 
			   
			   }
			   return sql;
		  } else {
			  return ";";
		  }
	   }
	   



	public String sort(Sorting sorting) {
		final String sql;
		if (sorting != null) {
			switch (sorting.getSortingType()) {
			case "group by":
				sql = group(sorting);
				break;
			case "order by":
				sql = order(sorting);
				break;
			default:
				sql = "";
			}
			return sql;
		} else {
			return ";";
		}
	}

	public String group(Sorting sort) {
		String groupSql = " GROUP BY ";
		for (Column c : sort.getColumns()) {
			groupSql += c.getName() + " ";
		}
		if (sort.getGroupCondition() != null) {
			groupSql += new HavingCondition().havingCondition(sort.getGroupCondition());
			groupSql = groupSql.substring(0, groupSql.length() - 1);
		}
		groupSql += ";";
		return groupSql;
	}

	public String order(Sorting sort) {
		String orderSql = " ORDER BY ";
		for (Column c : sort.getColumns()) {
			orderSql += c.getName() + ", ";
		}
		orderSql = orderSql.substring(0, orderSql.length() - 2);
		orderSql += " " + sort.getOrderType() + ";";
		return orderSql;
	}
	
	 /**
		 * Method to get the number of rows.
		 * 
		 * @param pPath      String the path of the database
		 * @param pDbName    String the name of the database
		 * @param tableName  String name of the table
		 * @return int
		 * @throws SQLException
		 */
	   
	   public int getRowsCount(final String pPath, final String pDbName, final String pTableName) 
		  throws SQLException {
				final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
				Statement stm =  con.createStatement();
				ResultSet rs = stm.executeQuery("SELECT * FROM " + pTableName);
				int rowcounts = 0;
				while (rs.next()) {
					rowcounts++ ;
				}
				con.close();
				System.out.println(rowcounts);
				return rowcounts;
			}
	   
}
