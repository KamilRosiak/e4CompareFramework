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

	@Override
	public void insertData(final String pPath, final String pDbName, final String pTableName, ColumnValue... data) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		try {
			String columns = "";
			String datas = "";
			for (ColumnValue c : data) {
				columns += c.getColumnName() + ",";
				datas += "'" + c.getValue() + "',";
			}
			// delete the last commas
			columns = columns.substring(0, columns.length() - 1);
			datas = datas.substring(0, datas.length() - 1);
			PreparedStatement prep = con
					.prepareStatement("insert into " + pTableName + " (" + columns + ") values (" + datas + ");");
			prep.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		con.close();
	}

	@Override
	public void updateData(String pPath, String pDbName, String pTableName, Condition condition, ColumnValue... data)
			throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		final Statement s = con.createStatement();
		String sqlStatement = "UPDATE " + pTableName + " SET ";
		for (ColumnValue c : data) {
			sqlStatement += c.getColumnName() + " = " + "'" + c.getValue() + "', ";
		}
		sqlStatement = sqlStatement.substring(0, sqlStatement.length() - 2);
		sqlStatement += condition.getConditionAsSql();
		//System.out.println(sqlStatement);
		s.execute(sqlStatement);
		con.close();
	}

	@Override
	public void deleteData(String pPath, String pDbName, String pTableName, Condition condition) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		Statement stm = con.createStatement();
		String sql = "DELETE FROM " + pTableName + condition.getConditionAsSql();
		//System.out.println(sql);
		stm.execute(sql);
		con.close();
	}

	@Override
	public ResultSet selectData(final String pPath, final String pDbName, final String pTableName, Condition condition,
			Sorting sorting, final String... attributes) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		final Statement stm = con.createStatement();
		String attributs = "", conditions = "", sortings = "";
		if (null == attributes || attributes.length == 0) {
			attributs = "*";
		} else {
			for (final String str : attributes) {
				attributs += str + ", ";
			}
			attributs = attributs.substring(0, attributs.length() - 2);
		}
		if (null != condition) {
			conditions = condition.getConditionAsSql();
		}
		if (null != sorting) {
			sortings = sorting.getSortingAsSql();
		}
		String sqlStatement = "SELECT " + attributs + " FROM " + pTableName + " " + conditions + sortings;
		//System.out.println("Test Select: " + sqlStatement);
		ResultSet rs = stm.executeQuery(sqlStatement);
		printResultSet(stm, sqlStatement);
		con.close();
		return rs;
	}

	public long summe(final String pPath, final String pDbName, final String pTableName, Condition condition,
			Sorting sorting, final String... attributes) {
		return 0;
	}
	
	public long count(final String pPath, final String pDbName, final String pTableName, Condition condition,
			Sorting sorting, final String... attributes) {
		return 0;
	}

}
