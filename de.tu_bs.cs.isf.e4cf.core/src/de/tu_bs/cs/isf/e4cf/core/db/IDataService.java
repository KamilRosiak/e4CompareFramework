package de.tu_bs.cs.isf.e4cf.core.db;

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
	 * Method to select certain data from a table and show it.
	 * 
	 * @param pPath
	 * @param pDbName
	 * @param pTableName
	 * @param attribute
	 * @throws SQLException
	 */
	public void selectData(String pPath, String pDbName, String pTableName, String attribute) throws SQLException;

	/**
	 * Method to select certain data from a table and show it sorted.
	 * 
	 * @param pPath
	 * @param pDbName
	 * @param pTableName
	 * @param attribute
	 * @param sort
	 * @throws SQLException
	 */
	public void selectData(String pPath, String pDbName, String pTableName, String attribute, Sorting sort)
			throws SQLException;

	/**
	 * Method to select certain data according to a given condition from a table and
	 * show it sorted.
	 * 
	 * @param pPath
	 * @param pDbName
	 * @param pTableName
	 * @param attribute
	 * @param condition
	 * @param sort
	 * @throws SQLException
	 */
	public void selectData(String pPath, String pDbName, String pTableName, String attribute, Condition condition,
			Sorting sort) throws SQLException;
}
