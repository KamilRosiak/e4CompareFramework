package de.tu_bs.cs.isf.e4cf.core.db;

import java.sql.SQLException;

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
	 *  Method to rename a column.
	 * 
	 * @param pPath			String the path of the database
	 * @param pDbName		String the name of the database
	 * @param tableName		String the name of the table
	 * @param columnName	String the old name of the column
	 * @param newColumnName	String the new name of the column
	 * @throws SQLException
	 */
	void renameColumn(final String pPath, final String pDbName, final String tableName, final String columnName, final String newColumnName) throws SQLException;
	
	/**
	 * Method to delete column form an existing table.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String the name of the table
	 * @param columns  	 Columns to delete
	 * @throws SQLException
	 */
	void deleteColumn(String pPath, String pDbName, String tableName, String... columns) throws SQLException;

	/**
	 * Method to add primary key constraints to an existing table.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param columnNames String the name of the columns to which the primary key
	 *                    will be added
	 * @throws SQLException
	 */
	void makeColumnPrimaryKey(String pPath, String pDbName, String tableName, String... columnNames)
			throws SQLException;

	/**
	 * Method to drop primary key constraints of an existing table.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param columnNames String the name of the columns of which the primary key
	 *                    will be dropped
	 * @throws SQLException
	 */
	void dropPrimaryKey(String pPath, String pDbName, String tableName, String... columnNames) throws SQLException;

	/**
	 * Method to make a Column Autoincrement of an existing table.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param columnNames String the name of the columns of which the primary key
	 *                    will be dropped
	 * @throws SQLException
	 */
	void makeColumnAutoIncrement(String pPath, String pDbName, String tableName, String columnNames)
			throws SQLException;
	
	/**
	 * Method to drop Autoincrement of column.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param columnNames String the name of the columns of which the primary key
	 *                    will be dropped
	 * @throws SQLException
	 */
	void dropColumnAutoIncrement(String pPath, String pDbName, String tableName, String columnName)
			throws SQLException;

	/**
	 * Method to drop unique constraints of a column.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param columnNames String the name of the column to which the unique
	 *                    constraints will be added
	 * @throws SQLException
	 */
	void dropColumnUnique(String pPath, String pDbName, String tableName, String columnNames) throws SQLException;

	/**
	 * Method to add unique constraints to an existing table.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param columnNames String the name of the column to which the unique
	 *                    constraints will be added
	 * @throws SQLException
	 */
	void makeColumnUnique(String pPath, String pDbName, String tableName, String columnNames) throws SQLException;
	
	/**
	 * Method to make a column not nullable.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param columnNames the name of the column to which the NOTNULL will be added
	 * @throws SQLException
	 */
	void makeColumnNotNull(String pPath, String pDbName, String tableName, String... columnNames) throws SQLException;
	
	/**
	 * Method to make a column nullable.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param columnNames the name of the column to which the NOTNULL will be added
	 * @throws SQLException
	 */
	void dropColumnNotNull(String pPath, String pDbName, String tableName, String... columnNames) throws SQLException;

}
