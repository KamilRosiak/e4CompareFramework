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
	 *  Method to get the primary key of a Table
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String name of the table
	 * @param columnName String name of the column
	 * @return List<String> list of the primary key in the Table
	 * @throws SQLException
	 */
	public List<String> getTablePrimaryKey(final String pPath, final String pDbName, final String tableName) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		ResultSet pkInfo = con.getMetaData().getPrimaryKeys(null, null, tableName);
		List<String> primarykeyset = new ArrayList<>();
		while(pkInfo.next()) {
			String primarykey = pkInfo.getString("COLUMN_NAME");
			primarykeyset.add(primarykey);
			
		}
		return primarykeyset;
		
	}
	
	/**
	 *  Method to get the unique constraints of a Table
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String name of the table
	 * @param columnName String name of the column
	 * @return List<String> list of the unique keys in the Table
	 * @throws SQLException
	 */
	
	public List<String> getTableUniqueKey(final String pPath, final String pDbName, final String tableName) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		ResultSet IndexInfo = con.getMetaData().getIndexInfo(null, null, tableName, true, false);
		List<String> uniqueset = new ArrayList<>();
		while (IndexInfo.next()) {
			String Unique = IndexInfo.getString("COLUMN_NAME");
				uniqueset.add(Unique);
			}
		return uniqueset;
		
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
		List<String> PrimaryKeySet = getTablePrimaryKey(pPath, pDbName, tableName);
		List<String> UniqueKeySet = getTableUniqueKey(pPath, pDbName, tableName);
		boolean isPrimaryKey = false;
		boolean isNull = false;
		boolean isAutoIncrement = false;
		boolean isUnique = false;
		for (int i = 1; i <= mrs.getColumnCount(); i++) {
			isPrimaryKey = PrimaryKeySet.contains(mrs.getColumnLabel(i));
			
			isAutoIncrement = mrs.isAutoIncrement(i);
			
		    if(!PrimaryKeySet.contains(mrs.getColumnLabel(i))) {
			  isUnique = UniqueKeySet.contains(mrs.getColumnLabel(i));
		    }
		    
			if (mrs.isNullable(i) == ResultSetMetaData.columnNoNulls) {
				isNull = false;
			}
			
			Column c = new Column(mrs.getColumnLabel(i), mrs.getColumnTypeName(i), isPrimaryKey, isUnique,
					isAutoIncrement, isNull);
			columns.add(c);
		}
		return columns;
	}

	/**
	 * Method to get a column in a given table.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String name of the table
	 * @param columnName String name of the column
	 * @return boolean
	 * @throws SQLException
	 */
	public Column getColumn(final String pPath, final String pDbName, final String tableName, final String columnName)
			throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, tableName)) {
			for (Column c : getColumnsTable(pPath, pDbName, tableName)) {
				if (columnName.equals(c.getName())) {
					return c;
				}
			}
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
		return null;
	}

	/**
	 * Method to get a column in a given table.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String name of the table
	 * @param columnName String name of the column
	 * @return boolean
	 * @throws SQLException
	 */
	public Column getColumn(final List<Column> columnList, final String columnName) throws SQLException {
		for (Column c : columnList) {
			if (columnName.equals(c.getName())) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Method to check whether a column is primary key or not.
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String name of the table
	 * @param columnName String name of the column
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean isColumnPrimaryKey(final String pPath, final String pDbName, final String tableName,
			final String columnName) throws SQLException {
		Column c = getColumn(pPath, pDbName, tableName, columnName);
		if (c.isPrimaryKey()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method to check whether a column is unique or not.
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String name of the table
	 * @param columnName String name of the column
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean isColumnUnique(final String pPath, final String pDbName, final String tableName,
			final String columnName) throws SQLException {
        Column c = getColumn(pPath, pDbName, tableName, columnName);
		if (c.isUnique()) {
			return true;
		} else {
			return false;
		}
	
	}

	/**
	 * Method to check whether a column allows Null values.
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String name of the table
	 * @param columnName String name of the column
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean isColumnNotNull(final String pPath, final String pDbName, final String tableName,
			final String columnName) throws SQLException {
		Column c = getColumn(pPath, pDbName, tableName, columnName);
		if (c.isNotNull() == true) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method to check whether a column is autoincrement.
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String name of the table
	 * @param columnName String name of the column
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean isColumnAutoIncrement(final String pPath, final String pDbName, final String tableName,
			final String columnName) throws SQLException {
		Column c = getColumn(pPath, pDbName, tableName, columnName);
		if (c.isAutoIncrement() == true) {
			return true;
		} else {
			return false;
		}
	}

}
