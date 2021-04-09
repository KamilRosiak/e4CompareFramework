package de.tu_bs.cs.isf.e4cf.core.db;

import java.sql.SQLException;

import de.tu_bs.cs.isf.e4cf.core.db.model.Column;

/**
 * 
 * Java-Interface for the manipulation of tables structure in databases.
 *
 */
public interface ITableService {

	/**
	 * Method to create a table from a given String tableName and the attributes
	 * from the class Column.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String the name of the table
	 * @param attributes Class which contains the attributes and their data types
	 * @throws SQLException
	 */
	void createTable(String pPath, String pDbName, String tableName, Column... attributes) throws SQLException;

	/**
	 * Method to create a table from a java-class in a generic way.
	 * 
	 * @param pPath   String the path of the database
	 * @param pDbName String the name of the database
	 * @param cls     the class from which the table will be created
	 * @throws SQLException
	 */
	void createTable(String pPath, String pDbName, Class<?> cls) throws SQLException;

	/**
	 * Method to delete a table.
	 * 
	 * @param pPath     pPath String the path of the database
	 * @param pDbName   String the name of the database
	 * @param tableName String the name of the table
	 * @throws SQLException
	 */
	void deleteTable(String pPath, String pDbName, String tableName) throws SQLException;

	/**
	 * Method to rename a table name.
	 * 
	 * @param pPath        String the path of the database
	 * @param pDbName      String the name of the database
	 * @param tableName    String the old name of the table
	 * @param NewtableName String the new name of the table
	 * @throws SQLException
	 */

	void renameTable(String pPath, String pDbName, String tableName, String NewtableName) throws SQLException;

	/**
	 * Method to add columns to an existing table.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String the name of the table
	 * @param attributes Class with the name and data type of the columns which
	 *                   should be added
	 * @throws SQLException
	 */
	void addColumn(String pPath, String pDbName, String tableName, Column... attributes) throws SQLException;

	/**
	 * Method to rename a column.
	 * 
	 * @param pPath         String the path of the database
	 * @param pDbName       String the name of the database
	 * @param tableName     String the name of the table
	 * @param columnName    String the old name of the column
	 * @param newColumnName String the new name of the column
	 * @throws SQLException
	 */
	void renameColumn(final String pPath, final String pDbName, final String tableName, final String columnName,
			final String newColumnName) throws SQLException;

	/**
	 * Method to delete column form an existing table.
	 * 
	 * @param pPath     String the path of the database
	 * @param pDbName   String the name of the database
	 * @param tableName String the name of the table
	 * @param columns   Columns to delete
	 * @throws SQLException
	 */
	void deleteColumn(String pPath, String pDbName, String tableName, String... columns) throws SQLException;

	/**
	 * Method to make or drop the nullability of columns.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param state       true/false
	 * @param columnNames the name of columns to make them NOTNULL or not.
	 * @throws SQLException
	 */
	void switchColumnNotNull(String pPath, String pDbName, String pTableName, boolean state, String... columnNames)
			throws SQLException;

	/**
	 * Method to make or drop unique constraint of columns.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param state       true/false
	 * @param columnNames the name of columns to make them unique or not.
	 * @throws SQLException
	 */
	void switchColumnUnique(String pPath, String pDbName, String pTableName, boolean state, String... columnNames)
			throws SQLException;

	/**
	 * Method to make or drop primary key constraint of columns.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param state       true/false
	 * @param columnNames the name of columns to make them unique or not.
	 * @throws SQLException
	 */
	void switchColumnPrimaryKey(String pPath, String pDbName, String pTableName, boolean state, String... columnNames)
			throws SQLException;

	/**
	 * Method to make or drop auto increment constraint of columns.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param state       true/false
	 * @param columnNames the name of columns to make them unique or not.
	 * @throws SQLException
	 */
	void switchColumnAutoIncrement(String pPath, String pDbName, String pTableName, boolean state, String columnName)
			throws SQLException;

}
