package de.tu_bs.cs.isf.e4cf.core.db.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.tu_bs.cs.isf.e4cf.core.db.Column;
import de.tu_bs.cs.isf.e4cf.core.db.DatabaseFactory;
import de.tu_bs.cs.isf.e4cf.core.db.TableServiceImp;

class Test_TableServices {

	private final String _PATHTESTDATABASES = "./testDatabases/";

	@Test
	void testCreateTable_normal() throws SQLException {
		DatabaseFactory.getInstance().createDatabase(_PATHTESTDATABASES, "testDB");
		TableServiceImp ts = new TableServiceImp();
		Column c1 = new Column("id", "integer");
		Column c2 = new Column("age", "integer");
		ts.createTable(_PATHTESTDATABASES, "testDB", "testTable", c1, c2);
		assertTrue(ts.tableExists(_PATHTESTDATABASES, "testDB", "testTable"), "Error by creating table.");
		assertFalse(ts.tableExists(_PATHTESTDATABASES, "testDB", "tt"));
	}

	@Test
	void testCreateTable_columnPrimaryKey() throws SQLException {
		DatabaseFactory.getInstance().createDatabase(_PATHTESTDATABASES, "testDB");
		TableServiceImp ts = new TableServiceImp();
		Column c1 = new Column("id", "integer", true, false, false, false);
		Column c2 = new Column("age", "integer", false, false, false, false);
		ts.createTable(_PATHTESTDATABASES, "testDB", "testTablePK", c1, c2);
		assertTrue(ts.isColumnPrimaryKey(_PATHTESTDATABASES, "testDB", "testTablePK", c1.getName()));
		assertFalse(ts.isColumnPrimaryKey(_PATHTESTDATABASES, "testDB", "testTablePK", c2.getName()));
	}

	@Test
	void testCreateTable_columnUnique() throws SQLException {
		DatabaseFactory.getInstance().createDatabase(_PATHTESTDATABASES, "testDB");
		TableServiceImp ts = new TableServiceImp();
		Column c1 = new Column("id", "integer", false, false, false, false);
		Column c2 = new Column("age", "integer", false, true, false, false);
		ts.createTable(_PATHTESTDATABASES, "testDB", "testTableUnique", c1, c2);
		assertFalse(ts.isColumnUnique(_PATHTESTDATABASES, "testDB", "testTableUnique", c1.getName()));
		assertTrue(ts.isColumnUnique(_PATHTESTDATABASES, "testDB", "testTableUnique", c2.getName()));
	}

	@Test
	void testCreateTable_columnAutoIncrement() throws SQLException {
		DatabaseFactory.getInstance().createDatabase(_PATHTESTDATABASES, "testDB");
		TableServiceImp ts = new TableServiceImp();
		Column c1 = new Column("id", "integer", false, false, true, false);
		Column c2 = new Column("age", "integer", false, false, false, false);
		ts.createTable(_PATHTESTDATABASES, "testDB", "testTableAI", c1, c2);
		assertFalse(ts.isColumnAutoIncrement(_PATHTESTDATABASES, "testDB", "testTableAI", c2.getName()));
		assertTrue(ts.isColumnAutoIncrement(_PATHTESTDATABASES, "testDB", "testTableAI", c1.getName()));
	}

	@Test
	void testCreateTable_columnNotNull() throws SQLException {
		DatabaseFactory.getInstance().createDatabase(_PATHTESTDATABASES, "testDB");
		TableServiceImp ts = new TableServiceImp();
		Column c1 = new Column("id", "integer", false, false, false, true);
		Column c2 = new Column("age", "integer", false, false, false, false);
		ts.createTable(_PATHTESTDATABASES, "testDB", "testTableNN", c1, c2);
		ts.makeColumnNotNull(_PATHTESTDATABASES, "testDB", "testTableNN", c1.getName());
		ts.getColumnsTable(_PATHTESTDATABASES, "testDB", "testTableNN");
		assertTrue(ts.isColumnNotNull(_PATHTESTDATABASES, "testDB", "testTableNN", c1.getName()));
		assertFalse(ts.isColumnNotNull(_PATHTESTDATABASES, "testDB", "testTableNN", c2.getName()));
	}

	/**
	 * 
	 * This Method must be launched alone, because it could be that there are other
	 * tables in the DB and so it will give a wrong resultat.
	 * 
	 * @throws SQLException
	 */
	@Test
	void testTablesExistance() throws SQLException {
		final List<String> listTables = Arrays.asList("t1", "t2", "t3");
		final Column c1 = new Column("id", "integer");
		final TableServiceImp ts = new TableServiceImp();
		DatabaseFactory.getInstance().createDatabase(_PATHTESTDATABASES, "testDB");
		ts.createTable(_PATHTESTDATABASES, "testDB", "t1", c1);
		ts.createTable(_PATHTESTDATABASES, "testDB", "t2", c1);
		ts.createTable(_PATHTESTDATABASES, "testDB", "t3", c1);
		assertEquals(true, ts.getTables(_PATHTESTDATABASES, "testDB").contains(listTables));
	}
	
}
