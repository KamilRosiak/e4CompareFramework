package de.tu_bs.cs.isf.e4cf.core.db.test;

import java.io.IOException;
import java.sql.SQLException;

import de.tu_bs.cs.isf.e4cf.core.db.DatabaseFactory;

public class Test {

	public static void main(String[] args) throws SQLException, IOException {
		
		DatabaseFactory.getInstance().createDatabase("", "TestDatenbank");
		
	}

}
