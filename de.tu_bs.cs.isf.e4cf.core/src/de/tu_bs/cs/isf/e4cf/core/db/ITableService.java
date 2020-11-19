package de.tu_bs.cs.isf.e4cf.core.db;

import java.sql.SQLException;

public interface ITableService {

	void createTable(String pPath, String pDbName, String tableName, Column... attributs);

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

	void addColumn(String pPath, String pDbName, String tableName, Column... attributs);

	void deleteColumn(String pPath, String pDbName, String tableName, String... attributNames);

	void makeColumnPrimaryKey(String pPath, String pDbName, String tableName, String... columnNames);

	void dropPrimaryKey(String pPath, String pDbName, String tableName, String... columnNames);

	void makeColumnAutoIncrement(String pPath, String pDbName, String tableName, String columnNames);

	void makeColumnUnique(String pPath, String pDbName, String tableName, String columnNames);

	void makeColumnNotNull(String pPath, String pDbName, String tableName, String columnNames);

}
