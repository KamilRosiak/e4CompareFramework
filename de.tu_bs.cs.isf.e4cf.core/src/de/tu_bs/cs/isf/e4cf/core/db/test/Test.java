package de.tu_bs.cs.isf.e4cf.core.db.test;

import java.io.IOException;
import java.sql.SQLException;

import de.tu_bs.cs.isf.e4cf.core.db.Column;
import de.tu_bs.cs.isf.e4cf.core.db.DatabaseFactory;
import de.tu_bs.cs.isf.e4cf.core.db.TableServiceImp;

public class Test {

	public static void main(String[] args) throws SQLException, IOException {
		Column co1 = new Column("eins", "INTEGER", false, false,false);
		Column co2 = new Column("zwei", "String", false, false,false);
		Column co3 = new Column("drei", "char", false, false,false);
		Column co4 = new Column("vier", "INTEGER", false, false,false);
		
		//DatabaseFactory.getInstance().createDatabase("", "TestDatenbank");
		//DatabaseFactory.getInstance().deleteDatabase("", "TestDatenbank");
		TableServiceImp c = new TableServiceImp();
		//c.deleteTable("", "TestDatenbank", "TestTable3");
		//c.createTable("", "TestDatenbank", "TestTable3", co1,co2,co4);
		c.makeColumnAutoIncrement("", "TestDatenbank", "TestTable3", co1.getName());
	}

}
