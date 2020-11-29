package de.tu_bs.cs.isf.e4cf.core.db.test;

import java.io.IOException;
import java.sql.SQLException;

import de.tu_bs.cs.isf.e4cf.core.db.Column;
import de.tu_bs.cs.isf.e4cf.core.db.DatabaseFactory;
import de.tu_bs.cs.isf.e4cf.core.db.TableServiceImp;

public class Test {

	public static void main(String[] args) throws SQLException, IOException {

		final String DATABASEPATH = "./testDatabases/";
		final String DATABASENAME = "TestDatenbank";

		TableServiceImp c = new TableServiceImp();

		DatabaseFactory.getInstance().createDatabase(DATABASEPATH, DATABASENAME);

		c.createTable(DATABASEPATH, DATABASENAME, "testTabelle", new Column("id", "integer"));
		
		c.renameTable(DATABASEPATH, DATABASENAME, "testTabelle", "testTabelleRenamed");
		
		c.deleteColumn(DATABASEPATH, DATABASENAME, "testTabelle", new Column("id", "integer"));;
		
		//hier kommen noch die anderen features

	}

}
