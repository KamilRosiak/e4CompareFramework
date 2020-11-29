package de.tu_bs.cs.isf.e4cf.core.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TableUtilities {

	/**
	 * Method to check if a table in a database exists or not.
	 * 
	 * @param pPath     String the path where to create the database
	 * @param pDbName   String the name of the database
	 * @param tableName String name of the table
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean tableExists(final String pPath, final String pDbName, final String tableName) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		final ResultSet rs = con.getMetaData().getTables(null, null, null, null);
		while (rs.next()) {
			if (tableName.equals(rs.getString("TABLE_NAME"))) {
				con.close();
				return true;
			}
		}
		con.close();
		return false;
	}

	/**
	 * Method to get the list of all tables in a database.
	 * 
	 * @param pPath   String the path where to create the database
	 * @param pDbName String the name of the database
	 * @return List<String> list of the tables in the database
	 * @throws SQLException
	 */
	public List<String> getTables(final String pPath, final String pDbName) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		final ResultSet rs = con.getMetaData().getTables(null, null, null, null);
		List<String> tableList = new ArrayList<String>();
		while (rs.next()) {
			tableList.add(rs.getString("TABLE_NAME"));
		}
		con.close();
		return Collections.unmodifiableList(tableList);
	}

	/**
	 * Method to check whether a column is part of table or not.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String name of the table
	 * @param columnName String name of the column
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean columnExists(final String pPath, final String pDbName, final String tableName,
			final String columnName) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, tableName)) {
			final ResultSet rs = con.getMetaData().getColumns(null, null, null, null);
			while (rs.next()) {
				if (columnName.equals(rs.getString("COLUMN_NAME"))) {
					con.close();
					return true;
				}
			}
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
		return false;
	}

	/**
	 * Method to get column metadata
	 *
	 * @param pPath     String the path of the database
	 * @param pDbName   String the name of the database
	 * @param tableName String name of the table
	 * @return List<String> list of the column metadata in the database
	 * @throws SQLException
	 */

	List<Column> getColumnsTable(String pPath, String pDbName, String tableName) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		List<Column> columns = new ArrayList<>();
		String sql = "select * from " + tableName + " LIMIT 0";
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		ResultSetMetaData mrs = rs.getMetaData();
		ResultSet pkInfo = con.getMetaData().getPrimaryKeys(null, null, tableName);
		boolean isPrimaryKey = false;
		String isNull = " ";
		String isAutoincrement = " ";
		String pk = null;

		while (pkInfo.next()) {
			pk = pkInfo.getString("COLUMN_NAME");

		}

		for (int i = 1; i <= mrs.getColumnCount(); i++) {
			if (mrs.getColumnLabel(i).equals(pk)) {
				isPrimaryKey = true;
				pkInfo.close();
			}
			if (mrs.isNullable(i) == 1) {
				isNull = " NOT NULL";
			}

			if (mrs.isAutoIncrement(i)) {
				isAutoincrement = " AUTO_INCREMENT ";
			}

			Column c = new Column(mrs.getColumnLabel(i), mrs.getColumnTypeName(i), isPrimaryKey, false, false, false);
			columns.add(c);
		}
		return columns;

	}

}
