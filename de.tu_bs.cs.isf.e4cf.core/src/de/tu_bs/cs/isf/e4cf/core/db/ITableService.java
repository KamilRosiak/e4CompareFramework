package de.tu_bs.cs.isf.e4cf.core.db;

import java.sql.SQLException;

public interface ITableService {

	/**
	 * Method to create a table from a given String tableName and the attributes from the class Column.
	 * 
	 * @param pPath			String the path of the database
	 * @param pDbName		String the name of the database
	 * @param tableName		String the name of the table
	 * @param attributes	Class which contains the attributes and their data types	
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

	void deleteTable(String pPath, String pDbName, String tableName);

	void renameTable(String pPath, String pDbName, String tableName);

	/**
	 * Method to add columns to an existing table.
	 * 
	 * @param pPath			String the path of the database
	 * @param pDbName		String the name of the database
	 * @param tableName		String the name of the table
	 * @param attributes	Class with the name and data type of the columns which should be added
	 * @throws SQLException
	 */
	void addColumn(String pPath, String pDbName, String tableName, Column... attributes) throws SQLException;

	void deleteColumn(String pPath, String pDbName, String tableName, String... attributNames);

	void makeColumnPrimaryKey(String pPath, String pDbName, String tableName, String... columnNames);

	void dropPrimaryKey(String pPath, String pDbName, String tableName, String... columnNames);

	void makeColumnAutoIncrement(String pPath, String pDbName, String tableName, String columnNames);

	void makeColumnUnique(String pPath, String pDbName, String tableName, String columnNames);

	void makeColumnNotNull(String pPath, String pDbName, String tableName, String columnNames);

}
