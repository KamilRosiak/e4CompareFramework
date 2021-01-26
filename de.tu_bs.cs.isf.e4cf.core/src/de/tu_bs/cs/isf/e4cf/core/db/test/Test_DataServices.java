package de.tu_bs.cs.isf.e4cf.core.db.test;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import de.tu_bs.cs.isf.e4cf.core.db.DataServiceImp;
import de.tu_bs.cs.isf.e4cf.core.db.DatabaseFactory;
import de.tu_bs.cs.isf.e4cf.core.db.TableServiceImp;
import de.tu_bs.cs.isf.e4cf.core.db.model.Column;
import de.tu_bs.cs.isf.e4cf.core.db.model.ColumnValue;

public class Test_DataServices {

	private final String _DATABASEPATH = "./testDatabases/";
	private final String _DATABASENAME = "DB_TEST";
	private final String _TABLEENAME   = "testTabelle";
	
	public Test_DataServices() throws SQLException {
		DatabaseFactory.getInstance().createDatabase(_DATABASEPATH, _DATABASENAME);
	}
	
	@Test
	void test_insertion() throws SQLException {
		TableServiceImp ts = new TableServiceImp();	
		Column c1 = new Column("id", "integer", false, false, true, false);
		Column c2 = new Column("name", "varchar (60)");
		ts.createTable(_DATABASEPATH, _DATABASENAME, _TABLEENAME, c1, c2);
		DataServiceImp ds = new DataServiceImp();
		
		ColumnValue cv1 = new ColumnValue(c2.getName(), new String("Mohamed ali"));
		ds.insertData(_DATABASEPATH, _DATABASENAME, _TABLEENAME, cv1);
		ds.insertData(_DATABASEPATH, _DATABASENAME, _TABLEENAME, cv1);
		assertEquals(2, ds.getTableNumberRows(_DATABASEPATH, _DATABASENAME, _TABLEENAME));
	}
	
}
