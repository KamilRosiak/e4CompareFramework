package de.tu_bs.cs.isf.e4cf.core.db;

public interface ITableService {

	void createTable(String pPath, String pDbName, String tableName, Column... attributs);

	void createTable(String pPath, String pDbName, Class<?> cls);

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
