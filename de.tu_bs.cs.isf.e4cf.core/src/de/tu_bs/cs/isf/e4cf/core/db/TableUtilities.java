package de.tu_bs.cs.isf.e4cf.core.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.core.db.model.Column;

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
	public boolean tableExists(final String pPath, final String pDbName, final String tableName) {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		try {
			final ResultSet rs = con.getMetaData().getTables(null, null, null, null);
			while (rs.next()) {
				if (tableName.equals(rs.getString("TABLE_NAME"))) {
					con.close();
					return true;
				}
			}
			con.close();
		} catch (SQLException e) {
			System.err.println("Error while getting tables. " + e.getMessage());
		}
		return false;
	}

	/**
	 * Method to check if a table has data.
	 * 
	 * @param pPath     String the path of the database
	 * @param pDbName   String the name of the database
	 * @param tableName String name of the table
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean tableHasData(final String pPath, final String pDbName, final String pTableName) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		final Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery("SELECT * FROM " + pTableName);
		int rowcounts = 0;
		while (rs.next()) {
			rowcounts++;
		}
		con.close();
		return rowcounts > 0;
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
			final ResultSet rs = con.getMetaData().getColumns(null, null, tableName, null);
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
	 * Method to get the primary key of a Table
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String name of the table
	 * @param columnName String name of the column
	 * @return List<String> list of the primary key in the Table
	 */
	public List<String> getPrimaryKeyTable(final String pPath, final String pDbName, final String tableName) {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		List<String> primarykeyList = new ArrayList<>();
		try {
			final ResultSet rs = con.getMetaData().getPrimaryKeys(null, null, tableName);
			primarykeyList = new ArrayList<>();
			while (rs.next()) {
				String primarykey = rs.getString("COLUMN_NAME");
				primarykeyList.add(primarykey);
			}
		} catch (SQLException e) {
			System.err.println("Error while getting table metadata.");
		}
		return Collections.unmodifiableList(primarykeyList);
	}

	/**
	 * Method to get the unique constraints of a Table.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String name of the table
	 * @param columnName String name of the column
	 * @return List<String> list of the unique keys in the Table
	 */
	public List<String> getUniqueKeyTable(final String pPath, final String pDbName, final String tableName) {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		List<String> uniquekeyList = new ArrayList<>();
		try {
			final ResultSet rs = con.getMetaData().getIndexInfo(null, null, tableName, true, false);
			while (rs.next()) {
				String unique = rs.getString("COLUMN_NAME");
				uniquekeyList.add(unique);
			}
		} catch (SQLException e) {
			System.err.println("Error while getting table metadata.");
		}
		return Collections.unmodifiableList(uniquekeyList);
	}

	/**
	 * Method to get column metadata.
	 *
	 * @param pPath     String the path of the database
	 * @param pDbName   String the name of the database
	 * @param tableName String name of the table
	 * @return List<Column> list of the column in the database
	 */
	public List<Column> getColumnsTable(final String pPath, final String pDbName, final String pTableName) {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		List<Column> columns = new ArrayList<>();
		try {
			final String sql = "select * from " + pTableName + " LIMIT 0";
			final Statement statement = con.createStatement();
			final ResultSet rs = statement.executeQuery(sql);
			final ResultSetMetaData mrs = rs.getMetaData();
			final List<String> primaryKeySet = getPrimaryKeyTable(pPath, pDbName, pTableName);
			final List<String> uniqueKeySet = getUniqueKeyTable(pPath, pDbName, pTableName);

			for (int i = 1; i <= mrs.getColumnCount(); i++) {
				Column c = new Column(mrs.getColumnLabel(i), mrs.getColumnTypeName(i));
				c.setPrimaryKey(primaryKeySet.contains(mrs.getColumnLabel(i)));
				c.setUnique(uniqueKeySet.contains(mrs.getColumnLabel(i)));
				c.setAutoIncrement(mrs.isAutoIncrement(i));
				columns.add(c);
			}

			DatabaseMetaData meta = con.getMetaData();
			ResultSet rs_columns = meta.getColumns(null, null, pTableName, null);
			while (rs_columns.next()) {
				String columnName = rs_columns.getString(4);
				String nullable = rs_columns.getString(18);
				int colSize = columns.size();
				for (int i = 0; i < colSize; i++) {
					Column col = columns.get(i);
					if (col.getName().equals(columnName)) {
						if (nullable.equals("YES")) {
							getColumn(columns, col.getName()).setNotNull(false);
						} else if (nullable.equals("NO")) {
							getColumn(columns, col.getName()).setNotNull(true);
						}
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("Error while getting table metadata.");
		}
		return Collections.unmodifiableList(columns);
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
			System.out.println("Column " + columnName + " does not exist.");
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
	 */
	protected Column getColumn(final List<Column> columnList, final String columnName) {
		for (Column c : columnList) {
			if (columnName.equals(c.getName())) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Method to check whether a column is primary key or not.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String name of the table
	 * @param columnName String name of the column
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean isColumnPrimaryKey(final String pPath, final String pDbName, final String tableName,
			final String columnName) throws SQLException {
		return getColumn(pPath, pDbName, tableName, columnName).isPrimaryKey();
	}

	/**
	 * Method to check whether a column is unique or not.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String name of the table
	 * @param columnName String name of the column
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean isColumnUnique(final String pPath, final String pDbName, final String tableName,
			final String columnName) throws SQLException {
		return getColumn(pPath, pDbName, tableName, columnName).isUnique();
	}

	/**
	 * Method to check whether a column allows null values.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String name of the table
	 * @param columnName String name of the column
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean isColumnNotNull(final String pPath, final String pDbName, final String tableName,
			final String columnName) throws SQLException {
		return getColumn(pPath, pDbName, tableName, columnName).isNotNull();
	}

	/**
	 * Method to check whether a column is autoincrement.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String name of the table
	 * @param columnName String name of the column
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean isColumnAutoIncrement(final String pPath, final String pDbName, final String tableName,
			final String columnName) throws SQLException {
		return getColumn(pPath, pDbName, tableName, columnName).isAutoIncrement();
	}

}
