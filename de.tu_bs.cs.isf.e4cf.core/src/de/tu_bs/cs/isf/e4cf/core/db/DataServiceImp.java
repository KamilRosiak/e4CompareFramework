package de.tu_bs.cs.isf.e4cf.core.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import de.tu_bs.cs.isf.e4cf.core.db.model.ColumnValue;

public class DataServiceImp implements IDataService{
	
	@Override
	public void insertData(final String pPath, final String pDbName, final String pTableName, ColumnValue... data)
			throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		// final Statement s = con.createStatement();
		String columns = "";
		String datas = "";
		for (ColumnValue c : data) {
			columns += c.getColumnName() + ",";
			datas += "'"+c.getValue() + "',";
			System.out.println("Test "+c.getValue());
		}
		columns = columns.substring(0, columns.length() - 1);
		datas = datas.substring(0, datas.length() - 1);
		System.out.println("insert into " + pTableName + " ("+columns+" ) values ( "+datas+" );");
		PreparedStatement prep = con.prepareStatement("insert into " + pTableName + " ("+columns+" ) values ( "+datas+" );");
		prep.execute();
	}
}
