package de.tu_bs.cs.isf.e4cf.core.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.tu_bs.cs.isf.e4cf.core.db.model.AndCondition;
import de.tu_bs.cs.isf.e4cf.core.db.model.Column;
import de.tu_bs.cs.isf.e4cf.core.db.model.Sorting;
import de.tu_bs.cs.isf.e4cf.core.db.model.ColumnValue;
import de.tu_bs.cs.isf.e4cf.core.db.model.Condition;
import de.tu_bs.cs.isf.e4cf.core.db.model.HavingCondition;
import de.tu_bs.cs.isf.e4cf.core.db.model.LikeCondition;
import de.tu_bs.cs.isf.e4cf.core.db.model.OrCondition;

public class DataUtilities {

	/**
	 * Method to get the number of rows.
	 * 
	 * @param pPath     String the path of the database
	 * @param pDbName   String the name of the database
	 * @param tableName String name of the table
	 * @return int
	 * @throws SQLException
	 */

	public int getRowsCount(final String pPath, final String pDbName, final String pTableName) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery("SELECT * FROM " + pTableName);
		int rowcounts = 0;
		while (rs.next()) {
			rowcounts++;
		}
		con.close();
		return rowcounts;
	}
	
	

}
