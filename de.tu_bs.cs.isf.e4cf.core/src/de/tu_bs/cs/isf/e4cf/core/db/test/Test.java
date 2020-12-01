package de.tu_bs.cs.isf.e4cf.core.db.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import de.tu_bs.cs.isf.e4cf.core.db.Column;
import de.tu_bs.cs.isf.e4cf.core.db.DatabaseFactory;
import de.tu_bs.cs.isf.e4cf.core.db.TableServiceImp;

public class Test {

	public static void main(String[] args) throws SQLException, IOException {

		final String DATABASEPATH = "./testDatabases/";
		final String DATABASENAME = "TestDatenbank";
		final String TABLEENAME = "testTabelle";

		Column c1 = new Column("id", "integer");
		Column c2 = new Column("age", "integer");
		Column c3 = new Column("testID", "integer", false, false, false, false);

		TableServiceImp c = new TableServiceImp();

		DatabaseFactory.getInstance().createDatabase(DATABASEPATH, DATABASENAME);

		c.createTable(DATABASEPATH, DATABASENAME, TABLEENAME, c1, c2, c3);

		// hier kommen noch die anderen features

	}

}
