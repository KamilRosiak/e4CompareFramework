package de.tu_bs.cs.isf.e4cf.core.db;

import java.sql.SQLException;

import de.tu_bs.cs.isf.e4cf.core.db.model.ColumnValue;

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
}
