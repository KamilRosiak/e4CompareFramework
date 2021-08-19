package de.tu_bs.cs.isf.e4cf.core.db.test;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import de.tu_bs.cs.isf.e4cf.core.db.DataServiceImp;
import de.tu_bs.cs.isf.e4cf.core.db.DatabaseFactory;
import de.tu_bs.cs.isf.e4cf.core.db.TableServiceImp;
import de.tu_bs.cs.isf.e4cf.core.db.model.AndCondition;
import de.tu_bs.cs.isf.e4cf.core.db.model.Column;
import de.tu_bs.cs.isf.e4cf.core.db.model.ColumnValue;
import de.tu_bs.cs.isf.e4cf.core.db.model.Condition;
import de.tu_bs.cs.isf.e4cf.core.db.model.OrCondition;

/**
 * 
 * JUnitstests for data management.
 *
 */
public class Test_DataServices {

	private final String _DATABASEPATH = "./testDatabases/";
	private final String _DATABASENAME = "DB_TEST";
	private final String _TABLEENAME = "testTabelle";

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
		ts.deleteTable(_DATABASEPATH, _DATABASENAME, _TABLEENAME);
	}

	@Test
	void test_update() throws SQLException {
		TableServiceImp ts = new TableServiceImp();
		Column c1 = new Column("id", "integer", false, false, false, false);
		Column c2 = new Column("name", "varchar (60)");
		ts.createTable(_DATABASEPATH, _DATABASENAME, "testTabelle_update", c1, c2);
		DataServiceImp ds = new DataServiceImp();
		ColumnValue cv1 = new ColumnValue(c2.getName(), new String("Mohamed ali"));
		ColumnValue cv2 = new ColumnValue(c2.getName(), new String("Xue"));
		ds.insertData(_DATABASEPATH, _DATABASENAME, "testTabelle_update", cv1);
		ds.insertData(_DATABASEPATH, _DATABASENAME, "testTabelle_update", cv2);
		Condition cd = new AndCondition(cv2);
		ColumnValue cv3 = new ColumnValue(c1.getName(), 25);
		ds.updateData(_DATABASEPATH, _DATABASENAME, "testTabelle_update", cd, cv3);
		assertEquals(2, ds.getTableNumberRows(_DATABASEPATH, _DATABASENAME, "testTabelle_update"));
		ts.deleteTable(_DATABASEPATH, _DATABASENAME, "testTabelle_update");
	}

	@Test
	void test_delete() throws SQLException {
		TableServiceImp ts = new TableServiceImp();
		Column c1 = new Column("id", "integer", false, false, false, false);
		Column c2 = new Column("name", "varchar (60)");
		ts.createTable(_DATABASEPATH, _DATABASENAME, "testTabelle_delete", c1, c2);
		DataServiceImp ds = new DataServiceImp();
		ColumnValue cv1 = new ColumnValue(c2.getName(), new String("Mohamed ali"));
		ColumnValue cv3 = new ColumnValue(c1.getName(), 25);
		ds.insertData(_DATABASEPATH, _DATABASENAME, "testTabelle_delete", cv1, cv3);
		Condition cd = new AndCondition(cv3);
		ds.deleteData(_DATABASEPATH, _DATABASENAME, "testTabelle_delete", cd);
		assertEquals(0, ds.getTableNumberRows(_DATABASEPATH, _DATABASENAME, "testTabelle_delete"));
		ts.deleteTable(_DATABASEPATH, _DATABASENAME, "testTabelle_delete");
	}

	@Test
	void test_count() throws SQLException {
		TableServiceImp ts = new TableServiceImp();
		Column c1 = new Column("id", "integer", false, false, false, false);
		Column c2 = new Column("name", "varchar (60)");
		ts.createTable(_DATABASEPATH, _DATABASENAME, "testTabelle_count", c1, c2);
		DataServiceImp ds = new DataServiceImp();

		ColumnValue cv1 = new ColumnValue(c2.getName(), new String("Mohamed ali"));
		ColumnValue cv2 = new ColumnValue(c2.getName(), new String("Xue"));
		ds.insertData(_DATABASEPATH, _DATABASENAME, "testTabelle_count", cv1);
		ds.insertData(_DATABASEPATH, _DATABASENAME, "testTabelle_count", cv2);
		Condition cd = new OrCondition(cv1, cv2);
		ColumnValue cv3 = new ColumnValue(c1.getName(), 25);
		ds.updateData(_DATABASEPATH, _DATABASENAME, "testTabelle_count", cd, cv3);
		assertEquals(2, ds.count(_DATABASEPATH, _DATABASENAME, "testTabelle_count", cd, null, c1.getName(), false));
		assertEquals(1, ds.count(_DATABASEPATH, _DATABASENAME, "testTabelle_count", cd, null, c1.getName(), true));
		ts.deleteTable(_DATABASEPATH, _DATABASENAME, "testTabelle_count");
	}

	@Test
	void test_sum() throws SQLException {
		TableServiceImp ts = new TableServiceImp();
		Column c1 = new Column("id", "integer", false, false, false, false);
		Column c2 = new Column("name", "varchar (60)");
		ts.createTable(_DATABASEPATH, _DATABASENAME, "testTabelle_sum", c1, c2);
		DataServiceImp ds = new DataServiceImp();

		ColumnValue cv1 = new ColumnValue(c2.getName(), new String("Mohamed ali"));
		ColumnValue cv2 = new ColumnValue(c2.getName(), new String("Xue"));
		ds.insertData(_DATABASEPATH, _DATABASENAME, "testTabelle_sum", cv1);
		ds.insertData(_DATABASEPATH, _DATABASENAME, "testTabelle_sum", cv2);
		Condition cd = new OrCondition(cv1, cv2);
		ColumnValue cv3 = new ColumnValue(c1.getName(), 25);
		ds.updateData(_DATABASEPATH, _DATABASENAME, "testTabelle_sum", cd, cv3);
		assertEquals(50, ds.sum(_DATABASEPATH, _DATABASENAME, "testTabelle_sum", cd, null, c1.getName(), false));
		assertEquals(25, ds.sum(_DATABASEPATH, _DATABASENAME, "testTabelle_sum", cd, null, c1.getName(), true));
		ts.deleteTable(_DATABASEPATH, _DATABASENAME, "testTabelle_sum");
	}

}
