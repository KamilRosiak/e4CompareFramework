package de.tu_bs.cs.isf.e4cf.core.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.tu_bs.cs.isf.e4cf.core.db.model.Sorting;
import de.tu_bs.cs.isf.e4cf.core.db.model.ColumnValue;
import de.tu_bs.cs.isf.e4cf.core.db.model.Condition;

public class DataServiceImp extends DataUtilities implements IDataService {

	/**
	 * Method to insert data into a table.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param pTableName String the name of the table
	 * @param data       data of the table
	 * @throws SQLException
	 */
	@Override
	public void insertData(final String pPath, final String pDbName, final String pTableName, ColumnValue... data)
			throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		try {
			String columnsAsSql = "";
			String dataAsSql = "";
			// preparing the columns and their data for the sql statement
			for (final ColumnValue c : data) {
				columnsAsSql += c.getColumnName() + ",";
				dataAsSql += "'" + c.getValue() + "',";
			}
			// delete the last commas
			columnsAsSql = columnsAsSql.substring(0, columnsAsSql.length() - 1);
			dataAsSql = dataAsSql.substring(0, dataAsSql.length() - 1);
			PreparedStatement prep = con.prepareStatement(
					"insert into " + pTableName + " (" + columnsAsSql + ") values (" + dataAsSql + ");");
			prep.execute();
		} catch (SQLException e) {
			System.err.println("Error while insering data: " + e.getMessage());
		}
		con.close();
	}

	/**
	 * Method to update data.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param pTableName String the name of the table
	 * @param condition  where condition
	 * @param data       data of the table
	 */
	@Override
	public void updateData(final String pPath, final String pDbName, final String pTableName, Condition condition,
			ColumnValue... data) {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		try {
			final Statement s = con.createStatement();
			String sqlStatement = "UPDATE " + pTableName + " SET ";
			for (final ColumnValue c : data) {
				sqlStatement += c.getColumnName() + " = " + "'" + c.getValue() + "', ";
			}
			sqlStatement = sqlStatement.substring(0, sqlStatement.length() - 2);
			sqlStatement += condition.getConditionAsSql();
			System.out.println("Test:" + sqlStatement);
			s.execute(sqlStatement);
			con.close();
		} catch (SQLException e) {
			System.err.println("Error while updating data: " + e.getMessage());
		}
	}

	/**
	 * Method to delete data from a table.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param pTableName String the name of the table
	 * @param condition  where condition
	 */
	@Override
	public void deleteData(final String pPath, final String pDbName, final String pTableName, Condition condition) {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		try {
			final Statement stm = con.createStatement();
			final String sql = "DELETE FROM " + pTableName + condition.getConditionAsSql();
			stm.execute(sql);
			con.close();
		} catch (SQLException e) {
			System.err.println("Error while deleting data: " + e.getMessage());
		}
	}

	/**
	 * Method to execute a select statement.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param pTableName String the name of the table
	 * @param condition  where condition
	 * @param sorting    wished sorting to have
	 * @param attributes the columns to select, if null, means all columns.
	 * @return ResultSet
	 */
	@Override
	public ResultSet selectData(final String pPath, final String pDbName, final String pTableName, Condition condition,
			Sorting sorting, final String... attributes) {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		// System.out.println("Test Select: " + sqlStatement);
		ResultSet rs = null;
		try {
			final Statement stm = con.createStatement();
			String attributsAsSql = "", conditionsAsSql = "", sortingsAsSql = "";
			if (null == attributes || attributes.length == 0) {
				attributsAsSql = "*";
			} else {
				for (final String str : attributes) {
					attributsAsSql += str + ", ";
				}
				attributsAsSql = attributsAsSql.substring(0, attributsAsSql.length() - 2);
			}
			if (null != condition) {
				conditionsAsSql = condition.getConditionAsSql();
			}
			if (null != sorting) {
				sortingsAsSql = sorting.getSortingAsSql();
			}
			final String sqlStatement = "SELECT " + attributsAsSql + " FROM " + pTableName + " " + conditionsAsSql
					+ sortingsAsSql;
			rs = stm.executeQuery(sqlStatement);
			printResultSet(stm, sqlStatement);
			con.close();
		} catch (SQLException e) {
			System.err.println("Error while select statement: " + e.getMessage());
		}
		return rs;
	}

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
	@Override
	public long count(final String pPath, final String pDbName, final String pTableName, Condition condition,
			Sorting sorting, final String attributes, final boolean distinct) {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		long countResult = 0;
		try {
			final Statement stm = con.createStatement();
			String sqlStatement = "";
			String conditionsAsSql = "", sortingsAsSql = "";
			if (null != condition) {
				conditionsAsSql = condition.getConditionAsSql();
			}
			if (null != sorting) {
				sortingsAsSql = sorting.getSortingAsSql();
			}
			if (distinct) {
				sqlStatement = "SELECT " + "COUNT(DISTINCT " + attributes + ") " + " AS " + attributes + "_num"
						+ " FROM " + pTableName + " " + conditionsAsSql + sortingsAsSql;
			} else {
				sqlStatement = "SELECT " + "COUNT(" + attributes + ") " + " AS " + attributes + "_num" + " FROM "
						+ pTableName + " " + conditionsAsSql + sortingsAsSql;
			}
			System.out.println("Test count: " + sqlStatement);
			ResultSet rs = stm.executeQuery(sqlStatement);
			countResult = rs.getLong(attributes + "_num");
			con.close();
		} catch (SQLException e) {
			System.err.println("Error while count statement: " + e.getMessage());
		}
		return countResult;
	}

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
	 */
	public long sum(final String pPath, final String pDbName, final String pTableName, Condition condition,
			Sorting sorting, final String attributes, final boolean distinct) {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		long resultSum = 0;
		try {
			final Statement stm = con.createStatement();
			String sqlStatement = "";
			String conditionsAsSql = "", sortingsAsSql = "";
			if (null != condition) {
				conditionsAsSql = condition.getConditionAsSql();
			}
			if (null != sorting) {
				sortingsAsSql = sorting.getSortingAsSql();
			}
			if (distinct) {
				sqlStatement = "SELECT " + "SUM(DISTINCT " + attributes + ") " + " AS " + attributes + "_num" + " FROM "
						+ pTableName + " " + conditionsAsSql + sortingsAsSql;
			} else {
				sqlStatement = "SELECT " + "SUM(" + attributes + ") " + " AS " + attributes + "_num" + " FROM "
						+ pTableName + " " + conditionsAsSql + sortingsAsSql;
			}
			ResultSet rs = stm.executeQuery(sqlStatement);
			resultSum = rs.getLong(attributes + "_num");
			con.close();
		} catch (SQLException e) {
			System.err.println("Error while sum statement: " + e.getMessage());
		}
		return resultSum;
	}

}
