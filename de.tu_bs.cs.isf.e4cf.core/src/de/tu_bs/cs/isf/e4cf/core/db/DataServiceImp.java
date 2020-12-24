package de.tu_bs.cs.isf.e4cf.core.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import de.tu_bs.cs.isf.e4cf.core.db.model.ColumnValue;
import de.tu_bs.cs.isf.e4cf.core.db.model.Condition;

public class DataServiceImp extends DataUtilities implements IDataService {

	@Override
	public void insertData(final String pPath, final String pDbName, final String pTableName, ColumnValue... data)
			throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
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
	}

	@Override
	public void updateData(String pPath, String pDbName, String pTableName, Condition condition, ColumnValue... data)
			throws SQLException {
		 final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		 final Statement s = con.createStatement();
		 String sqlStatement ="UPDATE "  + pTableName + " SET ";
		 for (ColumnValue c : data) {
			sqlStatement += c.getColumnName() + " = " + c.getValue() + ", ";	
		}
		sqlStatement = sqlStatement.substring(0, sqlStatement.length() - 2);
		sqlStatement += W_condition(condition);
		System.out.println(sqlStatement);
		s.execute(sqlStatement);
		con.close();
		
	}
}
