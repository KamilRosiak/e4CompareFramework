package de.tu_bs.cs.isf.e4cf.core.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.tu_bs.cs.isf.e4cf.core.db.model.ColumnValue;
import de.tu_bs.cs.isf.e4cf.core.db.model.Condition;
import de.tu_bs.cs.isf.e4cf.core.db.model.Sorting;

public interface IDataService {

	/**
	 * Method to insert data into a table.
	 * 
	 * @param pPath
	 * @param pDbName
	 * @param pTableName
	 * @param data
	 * @throws SQLException
	 */
	public void insertData(final String pPath, final String pDbName, final String pTableName, ColumnValue... data)
			throws SQLException;

	/**
	 * Method to update data into a table.
	 * 
	 * @param pPath
	 * @param pDbName
	 * @param pTableName
	 * @param data
	 * @throws SQLException
	 */
	void updateData(String pPath, String pDbName, String pTableName, Condition condition, ColumnValue... data)
			throws SQLException;

	/**
	 * Method to delete data from a table
	 * 
	 * @param pPath
	 * @param pDbName
	 * @param pTableName
	 * @param condition
	 * @throws SQLException
	 */
	public void deleteData(String pPath, String pDbName, String pTableName, Condition condition)throws SQLException;

	/**
	 * 
	 * @param pPath
	 * @param pDbName
	 * @param pTableName
	 * @param condition
	 * @param sorting
	 * @param attributes
	 * @return
	 * @throws SQLException
	 */
	public ResultSet selectData(String pPath, String pDbName, String pTableName, Condition condition, Sorting sorting,
			String... attributes) throws SQLException;
}
