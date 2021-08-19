package de.tu_bs.cs.isf.e4cf.core.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.tu_bs.cs.isf.e4cf.core.db.model.ColumnValue;
import de.tu_bs.cs.isf.e4cf.core.db.model.Condition;
import de.tu_bs.cs.isf.e4cf.core.db.model.Sorter;

/**
 * 
 * Java-Interface for the manipulation of data in databases.
 *
 */
public interface IDataService {

	/**
	 * Method to insert data into a table.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param pTableName String the name of the table
	 * @param data       data of the table
	 * @throws SQLException
	 */
	public void insertData(final String pPath, final String pDbName, final String pTableName, ColumnValue... data)
			throws SQLException;

	/**
	 * Method to update data.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param pTableName String the name of the table
	 * @param condition  where condition
	 */
	public void updateData(final String pPath, final String pDbName, final String pTableName, Condition condition,
			ColumnValue... data);

	/**
	 * Method to delete data from a table.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param pTableName String the name of the table
	 * @param condition  where condition
	 */
	public void deleteData(String pPath, String pDbName, String pTableName, Condition condition);

	/**
	 * Method to execute a select statement.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param pTableName String the name of the table
	 * @param condition  where condition
	 * @param sorting    wished sorting to have
	 * @param attributes the columns to select
	 * @return ResultSet
	 */
	public ResultSet selectData(final String pPath, final String pDbName, final String pTableName, Condition condition,
			Sorter sorting, final String... attributes);

	/**
	 * Method to count rows with special conditions.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param pTableName String the name of the table
	 * @param condition  where condition
	 * @param sorting    wished sorting to have
	 * @param attributes the columns to select
	 * @param distinct   boolean for distinct counting
	 * @return long
	 */
	public long count(final String pPath, final String pDbName, final String pTableName, Condition condition,
			Sorter sorting, final String attributes, final boolean distinct);

	/**
	 * Method to count the sum of column rows with special conditions.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param pTableName String the name of the table
	 * @param condition  where condition
	 * @param sorting    wished sorting to have
	 * @param attributes the columns to select
	 * @param distinct   boolean for distinct counting
	 * @return long
	 * @throws SQLException 
	 */
	public long sum(final String pPath, final String pDbName, final String pTableName, Condition condition,
			Sorter sorting, final String attributes, final boolean distinct) throws SQLException;
}
