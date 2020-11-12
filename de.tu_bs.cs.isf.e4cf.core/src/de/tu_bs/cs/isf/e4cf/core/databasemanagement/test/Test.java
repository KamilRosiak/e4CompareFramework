package de.tu_bs.cs.isf.e4cf.core.databasemanagement.test;

import java.sql.SQLException;

import de.tu_bs.cs.isf.e4cf.core.databasemanagement.DatabaseFactory;

public class Test {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		DatabaseFactory.getInstance().createDatabase("", "Test1");
	}

}
