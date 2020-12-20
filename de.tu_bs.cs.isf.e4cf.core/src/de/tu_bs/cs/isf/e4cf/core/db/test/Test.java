package de.tu_bs.cs.isf.e4cf.core.db.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import de.tu_bs.cs.isf.e4cf.core.db.DataServiceImp;
import de.tu_bs.cs.isf.e4cf.core.db.DatabaseFactory;
import de.tu_bs.cs.isf.e4cf.core.db.TableServiceImp;
import de.tu_bs.cs.isf.e4cf.core.db.model.Column;
import de.tu_bs.cs.isf.e4cf.core.db.model.ColumnValue;

public class Test {

	public static void main(String[] args) throws SQLException, IOException {

		final String DATABASEPATH = "./testDatabases/";
		final String DATABASENAME = "TestDatenbank";
		final String TABLEENAME = "testTabelle";

		Column c1 = new Column("id", "integer");
		Column c2 = new Column("name", "varchar (60)");
		Column c3 = new Column("age", "integer");

		TableServiceImp c = new TableServiceImp();

		DataServiceImp ds = new DataServiceImp();

		DatabaseFactory.getInstance().createDatabase(DATABASEPATH, DATABASENAME);

		c.createTable(DATABASEPATH, DATABASENAME, TABLEENAME, c1, c2, c3);
		
		ColumnValue cv = new ColumnValue(c2.getName(), new String("Rami"));

		//c.makeColumnAutoIncrement(DATABASEPATH, DATABASENAME, TABLEENAME, "id");
		ds.insertData(DATABASEPATH, DATABASENAME, TABLEENAME, cv,new ColumnValue(c3.getName(), new Integer(25)));
	}

}
