package de.tu_bs.cs.isf.e4cf.core.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.tu_bs.cs.isf.e4cf.core.db.model.Sorter;
import de.tu_bs.cs.isf.e4cf.core.db.model.ColumnValue;
import de.tu_bs.cs.isf.e4cf.core.db.model.Condition;

/**
 * DAO class for the implementation of the data logic. Every SQL statement
 * concerning data is here to find.
 *
 */
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
			PreparedStatement prep = con.prepareStatement(Messages.INSERT + Messages.INTO + pTableName + " ("
					+ columnsAsSql + ") " + Messages.VALUES + " (" + dataAsSql + ");");
			prep.execute();
		} catch (SQLException e) {
			System.err.println(Messages._ER_INS_DATA + e.getMessage());
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
		try {
			String sqlStatement = Messages.UPDATE + pTableName + Messages.SET;
			// preparing the columns and their data for the sql statement
			for (final ColumnValue c : data) {
				sqlStatement += c.getColumnName() + " = " + "'" + c.getValue() + "', ";
			}
			// delete the last commas
			sqlStatement = sqlStatement.substring(0, sqlStatement.length() - 2);
			sqlStatement += condition.getConditionAsSql();
			executeStatement(pPath, pDbName, sqlStatement);
		} catch (Exception e) {
			System.err.println(Messages._ER_UPD_DATA + e.getMessage());
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
		try {
			final String sqlStatement = Messages.DELETE + Messages.FROM + pTableName + condition.getConditionAsSql();
			executeStatement(pPath, pDbName, sqlStatement);
		} catch (SQLException e) {
			System.err.println(Messages._ER_DEL_DATA + e.getMessage());
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
			Sorter sorting, final String... attributes) {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
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
			final String sqlStatement = Messages.SELECT + attributsAsSql + Messages.FROM + pTableName + " "
					+ conditionsAsSql + sortingsAsSql;
			rs = stm.executeQuery(sqlStatement);
			printResultSet(stm, sqlStatement);
			con.close();
		} catch (SQLException e) {
			System.err.println(Messages._ER_SEL_DATA + e.getMessage());
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
			Sorter sorting, final String attributes, final boolean distinct) {
		return getNumberSqlStatement(pPath, pDbName, pTableName, Messages.COUNT, condition, sorting, attributes, distinct);
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
			Sorter sorting, final String attributes, final boolean distinct) {
		return getNumberSqlStatement(pPath, pDbName, pTableName, Messages.SUM, condition, sorting, attributes, distinct);
	}
	
	/**
	 * Method to retrieve a number from an sql statement such as sum or count.
	 * 
	 * @param pPath
	 * @param pDbName
	 * @param pTableName
	 * @param sqlAction
	 * @param condition
	 * @param sorting
	 * @param attributes
	 * @param distinct
	 * @return
	 */
	protected long getNumberSqlStatement(final String pPath, final String pDbName, final String pTableName, final String sqlAction, Condition condition,
			Sorter sorting, final String attributes, final boolean distinct) {
		long resultSum = -1;
		try {
			final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
			final Statement stm = con.createStatement();
			String sqlStatement = "";
			String conditionsAsSql = "", sortingsAsSql = "";
			if (null != condition) {
				conditionsAsSql = condition.getConditionAsSql();
			}
			if (null != sorting) {
				sortingsAsSql = sorting.getSortingAsSql();
			}
			sqlStatement = Messages.SELECT + sqlAction + " ( ";
			if (distinct) {
				sqlStatement += Messages.DISTINCT;
			}
			sqlStatement += attributes + ") " + Messages.AS + attributes + "_num" + Messages.FROM + pTableName + " "
					+ conditionsAsSql + sortingsAsSql;

			ResultSet rs = stm.executeQuery(sqlStatement);
			resultSum = rs.getLong(attributes + "_num");
			con.close();
		} catch (SQLException e) {
			if(Messages.SUM.equals(sqlAction)) {
				System.err.println(Messages._ER_SUM + e.getMessage());
			}else {
				System.err.println(Messages._ER_CNT + e.getMessage());
			}
		}
		return resultSum;
	}

	/**
	 * Methode to execute sql statements.
	 * 
	 * @param pPath
	 * @param pDbName
	 * @param sqlStatement
	 * @throws SQLException
	 */
	protected void executeStatement(final String pPath, final String pDbName, final String sqlStatement)
			throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		Statement s = con.createStatement();
		s.execute(sqlStatement);
		con.close();
	}
}
