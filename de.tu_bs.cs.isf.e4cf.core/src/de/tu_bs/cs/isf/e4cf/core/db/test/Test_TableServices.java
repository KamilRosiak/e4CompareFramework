package de.tu_bs.cs.isf.e4cf.core.db.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
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
	
	@Test
	void testRenameTable() throws SQLException {
		DatabaseFactory.getInstance().createDatabase(_PATHTESTDATABASES, "testDB");
		TableServiceImp ts = new TableServiceImp();
		Column c1 = new Column("id", "integer");
		Column c2 = new Column("age", "integer");
		Column c3 = new Column("name","String");
		ts.createTable(_PATHTESTDATABASES, "testDB", "old_testRenameTable", c1, c2,c3);
		ts.renameTable(_PATHTESTDATABASES, "testDB", "old_testRenameTable", "new_testRenameTable");
		assertTrue(ts.tableExists(_PATHTESTDATABASES, "testDB", "new_testRenameTable"));
		assertFalse(ts.tableExists(_PATHTESTDATABASES, "testDB", "old_testRenameTable"));
	//	ts.deleteTable(_PATHTESTDATABASES, "testDB", "new_testRenameTable");
	}
	
	@Test
	void testAddColumn_normal() throws SQLException {
		DatabaseFactory.getInstance().createDatabase(_PATHTESTDATABASES, "testDB");
		TableServiceImp ts = new TableServiceImp();
		Column c1 = new Column("id", "integer");
		Column c2 = new Column("age", "integer");
		Column c3 = new Column("name","String");
		ts.createTable(_PATHTESTDATABASES, "testDB", "testAC", c1);
		ts.addColumn(_PATHTESTDATABASES, "testDB", "testAC", c2);
		assertTrue(ts.columnExists(_PATHTESTDATABASES, "testDB", "testAC", c2.getName()));
		ts.addColumn(_PATHTESTDATABASES, "testDB", "testAC", c3);
		assertTrue(ts.columnExists(_PATHTESTDATABASES, "testDB", "testAC", c3.getName()));
	}
	
	@Test
	void testAddColumn_columnPrimaryKey() throws SQLException {
		DatabaseFactory.getInstance().createDatabase(_PATHTESTDATABASES, "testDB");
		TableServiceImp ts = new TableServiceImp();
		Column c1 = new Column("table_id", "integer", true, false, false, false);
		Column c2 = new Column("age", "integer");
		Column c3 = new Column("name","String", false, false, false, false);
		ts.createTable(_PATHTESTDATABASES, "testDB", "testAC_PK", c2, c3);
		ts.addColumn(_PATHTESTDATABASES, "testDB", "testAC_PK", c1);
		assertTrue(ts.columnExists(_PATHTESTDATABASES, "testDB", "testAC_PK", c1.getName()) && ts.isColumnPrimaryKey(_PATHTESTDATABASES, "testDB", "testAC_PK", c1.getName()));
		assertFalse(ts.columnExists(_PATHTESTDATABASES, "testDB", "testAC_PK", c3.getName()) && ts.isColumnPrimaryKey(_PATHTESTDATABASES, "testDB", "testAC_PK", c3.getName()));
	}
	
	@Test
	void testAddColumn_columnUnique() throws SQLException {
		DatabaseFactory.getInstance().createDatabase(_PATHTESTDATABASES, "testDB");
		TableServiceImp ts = new TableServiceImp();
		Column c1 = new Column("id", "integer");
		Column c2 = new Column("age", "integer", false, true, false, false);
		Column c3 = new Column("name","String", false, false, false, false);
		ts.createTable(_PATHTESTDATABASES, "testDB", "testAC_Unique", c1);
		ts.addColumn(_PATHTESTDATABASES, "testDB", "testAC_Unique", c2, c3);
		assertTrue(ts.columnExists(_PATHTESTDATABASES, "testDB", "testTableAC_Unique", c2.getName()) && ts.isColumnUnique(_PATHTESTDATABASES, "testDB", "testAC_Unique", c2.getName()));
		assertFalse(ts.columnExists(_PATHTESTDATABASES, "testDB", "testTableAC_Unique", c3.getName()) && ts.isColumnUnique(_PATHTESTDATABASES, "testDB", "testAC_Unique", c3.getName()));
	}
	
	@Test
	void testAddColumn_columnAutoIncrement() throws SQLException {
		DatabaseFactory.getInstance().createDatabase(_PATHTESTDATABASES, "testDB");
		TableServiceImp ts = new TableServiceImp();
		Column c1 = new Column("id", "integer");
		Column c2 = new Column("age", "integer", false, false, true, false);
		Column c3 = new Column("name","String", false, false, false, false);
		ts.createTable(_PATHTESTDATABASES, "testDB", "testAC_AI", c1);
		ts.addColumn(_PATHTESTDATABASES, "testDB", "testAC_AI", c2, c3);
		assertTrue(ts.columnExists(_PATHTESTDATABASES, "testDB", "testTableAC_AI", c2.getName()) && ts.isColumnAutoIncrement(_PATHTESTDATABASES, "testDB", "testAC_AI", c2.getName()));
		assertFalse(ts.columnExists(_PATHTESTDATABASES, "testDB", "testTableAC_AI", c3.getName()) && ts.isColumnAutoIncrement(_PATHTESTDATABASES, "testDB", "testAC_AI", c3.getName()));
	}
	
	@Test
	void testAddColumn_columnNotNull() throws SQLException {
		DatabaseFactory.getInstance().createDatabase(_PATHTESTDATABASES, "testDB");
		TableServiceImp ts = new TableServiceImp();
		Column c1 = new Column("id", "integer");
		Column c2 = new Column("age", "integer", false, false, false, true);
		Column c3 = new Column("name","String", false, false, false, false);
		ts.createTable(_PATHTESTDATABASES, "testDB", "testAC_NN", c1);
		ts.addColumn(_PATHTESTDATABASES, "testDB", "testAC_NN", c2, c3);
		assertTrue(ts.columnExists(_PATHTESTDATABASES, "testDB", "testTableAC_NN", c2.getName()) && ts.isColumnNotNull(_PATHTESTDATABASES, "testDB", "testAC_NN", c2.getName()));
		assertFalse(ts.columnExists(_PATHTESTDATABASES, "testDB", "testTableAC_NN", c3.getName()) && ts.isColumnNotNull(_PATHTESTDATABASES, "testDB", "testAC_NN", c3.getName()));
	}
	
	@Test
	void testRenameColumn() throws SQLException {
		DatabaseFactory.getInstance().createDatabase(_PATHTESTDATABASES, "testDB");
		TableServiceImp ts = new TableServiceImp();
		Column c1 = new Column("id", "integer");
		Column c2 = new Column("age", "integer");
		ts.createTable(_PATHTESTDATABASES, "testDB", "testTableRC", c1, c2);
		ts.renameColumn(_PATHTESTDATABASES, "testDB", "testTableRC", c2.getName(), "new_name");
		assertTrue(ts.columnExists(_PATHTESTDATABASES, "testDB", "testTableRC", "new_name") && !ts.columnExists(_PATHTESTDATABASES, "testDB", "testTableRC", "name"));
		ts.renameColumn(_PATHTESTDATABASES, "testDB", "testTableRC", c2.getName(), "name");
		assertTrue(ts.columnExists(_PATHTESTDATABASES, "testDB", "testTableRC", "name") && !ts.columnExists(_PATHTESTDATABASES, "testDB", "testTableRC", "new_name"));
		ts.renameColumn(_PATHTESTDATABASES, "testDB", "testTableRC", c2.getName(), "name");
		assertTrue(ts.columnExists(_PATHTESTDATABASES, "testDB", "testTableRC", "name"));
	}
	
	@Test
	void testmakeColumnPrimaryKey() throws SQLException {
		DatabaseFactory.getInstance().createDatabase(_PATHTESTDATABASES, "testDB");
		TableServiceImp ts = new TableServiceImp();
		Column c1 = new Column("id", "integer");
		Column c2 = new Column("age", "integer");
		Column c3 = new Column("name","String");
		ts.createTable(_PATHTESTDATABASES, "testDB", "testTableColumnPK", c1, c2,c3);
		ts.makeColumnPrimaryKey(_PATHTESTDATABASES, "testDB", "testTableColumnPK", c1.getName(),c2.getName());
		assertTrue(ts.isColumnPrimaryKey(_PATHTESTDATABASES, "testDB", "testTableColumnPK", c1.getName()));
		assertTrue(ts.isColumnPrimaryKey(_PATHTESTDATABASES, "testDB", "testTableColumnPK", c2.getName()));
		assertFalse(ts.isColumnPrimaryKey(_PATHTESTDATABASES, "testDB", "testTableColumnPK", c3.getName()));
	}
	
	@Test
	void testdropColumnPrimaryKey() throws SQLException {
		DatabaseFactory.getInstance().createDatabase(_PATHTESTDATABASES, "testDB");
		TableServiceImp ts = new TableServiceImp();
		Column c1 = new Column("id", "integer");
		Column c2 = new Column("age", "integer");
		Column c3 = new Column("name","String");
		ts.dropColumnPrimaryKey(_PATHTESTDATABASES, "testDB", "testTableColumnPK", c3.getName());
		assertTrue(ts.isColumnPrimaryKey(_PATHTESTDATABASES, "testDB", "testTableColumnPK", c1.getName()));
		assertTrue(ts.isColumnPrimaryKey(_PATHTESTDATABASES, "testDB", "testTableColumnPK", c2.getName()));
		assertFalse(ts.isColumnPrimaryKey(_PATHTESTDATABASES, "testDB", "testTableColumnPK", c3.getName()));
		ts.dropColumnPrimaryKey(_PATHTESTDATABASES, "testDB", "testTableColumnPK", c1.getName());
		assertFalse(ts.isColumnPrimaryKey(_PATHTESTDATABASES, "testDB", "testTableColumnPK", c1.getName()));
		assertTrue(ts.isColumnPrimaryKey(_PATHTESTDATABASES, "testDB", "testTableColumnPK", c2.getName()));
		assertFalse(ts.isColumnPrimaryKey(_PATHTESTDATABASES, "testDB", "testTableColumnPK", c3.getName()));
		ts.dropColumnPrimaryKey(_PATHTESTDATABASES, "testDB", "testTableColumnPK", c1.getName(),c2.getName());
		assertFalse(ts.isColumnPrimaryKey(_PATHTESTDATABASES, "testDB", "testTableColumnPK", c1.getName()));
		assertFalse(ts.isColumnPrimaryKey(_PATHTESTDATABASES, "testDB", "testTableColumnPK", c2.getName()));
		assertFalse(ts.isColumnPrimaryKey(_PATHTESTDATABASES, "testDB", "testTableColumnPK", c3.getName()));		
	}
	
	@Test
	void testmakeColumnUnique() throws SQLException {
		DatabaseFactory.getInstance().createDatabase(_PATHTESTDATABASES, "testDB");
		TableServiceImp ts = new TableServiceImp();
		Column c1 = new Column("id", "integer");
		Column c2 = new Column("age", "integer");
		Column c3 = new Column("name","String");
		ts.createTable(_PATHTESTDATABASES, "testDB", "testTableColumnUnique", c1, c2,c3);
		ts.makeColumnUnique(_PATHTESTDATABASES, "testDB", "testTableColumnUnique", c1.getName());
		assertTrue(ts.isColumnUnique(_PATHTESTDATABASES, "testDB", "testTableColumnUnique", c1.getName()));
		assertFalse(ts.isColumnUnique(_PATHTESTDATABASES, "testDB", "testTableColumnUnique", c2.getName()));
		assertFalse(ts.isColumnUnique(_PATHTESTDATABASES, "testDB", "testTableColumnUnique", c3.getName()));
		ts.makeColumnUnique(_PATHTESTDATABASES, "testDB","testTableColumnUnique", c2.getName());
		assertTrue(ts.isColumnUnique(_PATHTESTDATABASES, "testDB", "testTableColumnUnique", c1.getName()));
		assertTrue(ts.isColumnUnique(_PATHTESTDATABASES, "testDB", "testTableColumnUnique", c2.getName()));
		assertFalse(ts.isColumnUnique(_PATHTESTDATABASES, "testDB", "testTableColumnUnique", c3.getName()));
	}
	
	@Test
	void testdropColumnUnique() throws SQLException {
		DatabaseFactory.getInstance().createDatabase(_PATHTESTDATABASES, "testDB");
		TableServiceImp ts = new TableServiceImp();
		Column c1 = new Column("id", "integer");
		Column c2 = new Column("age", "integer");
		Column c3 = new Column("name","String");
		ts.dropColumnUnique(_PATHTESTDATABASES, "testDB", "testTableColumnUnique", c3.getName());
		assertTrue(ts.isColumnUnique(_PATHTESTDATABASES, "testDB", "testTableColumnUnique", c1.getName()));
		assertTrue(ts.isColumnUnique(_PATHTESTDATABASES, "testDB", "testTableColumnUnique", c2.getName()));
		assertFalse(ts.isColumnUnique(_PATHTESTDATABASES, "testDB", "testTableColumnUnique", c3.getName()));
		ts.dropColumnUnique(_PATHTESTDATABASES, "testDB", "testTableColumnUnique", c1.getName());
		assertFalse(ts.isColumnUnique(_PATHTESTDATABASES, "testDB", "testTableColumnUnique", c1.getName()));
        assertTrue(ts.isColumnUnique(_PATHTESTDATABASES, "testDB", "testTableColumnUnique", c2.getName()));
		assertFalse(ts.isColumnUnique(_PATHTESTDATABASES, "testDB", "testTableColumnUnique", c3.getName()));
		ts.dropColumnUnique(_PATHTESTDATABASES, "testDB", "testTableColumnUnique",c2.getName());
		assertFalse(ts.isColumnUnique(_PATHTESTDATABASES, "testDB", "testTableColumnUnique", c1.getName()));
		assertFalse(ts.isColumnUnique(_PATHTESTDATABASES, "testDB", "testTableColumnUnique", c2.getName()));
		assertFalse(ts.isColumnUnique(_PATHTESTDATABASES, "testDB", "testTableColumnUnique", c3.getName()));
	}
	
	@Test
	void testMakeColumnNotNull() throws SQLException {
		DatabaseFactory.getInstance().createDatabase(_PATHTESTDATABASES, "testDB");
		TableServiceImp ts = new TableServiceImp();
		Column c1 = new Column("id", "integer");
		Column c2 = new Column("age", "integer");
		Column c3 = new Column("name","String");
		ts.createTable(_PATHTESTDATABASES, "testDB", "testTableColumnNotNull", c1, c2,c3);
		ts.makeColumnNotNull(_PATHTESTDATABASES, "testDB", "testTableColumnNotNull", c1.getName());
		assertTrue(ts.isColumnNotNull(_PATHTESTDATABASES, "testDB", "testTableColumnNotNull", c1.getName()));
		assertFalse(ts.isColumnNotNull(_PATHTESTDATABASES, "testDB", "testTableColumnNotNull", c2.getName()));
		assertFalse(ts.isColumnNotNull(_PATHTESTDATABASES, "testDB", "testTableColumnNotNull", c3.getName()));
		ts.makeColumnNotNull(_PATHTESTDATABASES, "testDB","testTableColumnNotNull", c2.getName());
		assertTrue(ts.isColumnNotNull(_PATHTESTDATABASES, "testDB", "testTableColumnNotNull", c1.getName()));
		assertTrue(ts.isColumnNotNull(_PATHTESTDATABASES, "testDB", "testTableColumnNotNull", c2.getName()));
		assertFalse(ts.isColumnNotNull(_PATHTESTDATABASES, "testDB", "testTableColumnNotNull", c3.getName()));
	}
	
	@Test
	void testDropColumnNotNull() throws SQLException {
		DatabaseFactory.getInstance().createDatabase(_PATHTESTDATABASES, "testDB");
		TableServiceImp ts = new TableServiceImp();
		Column c1 = new Column("id", "integer");
		Column c2 = new Column("age", "integer");
		Column c3 = new Column("name","String");
		ts.dropColumnNotNull(_PATHTESTDATABASES, "testDB", "testTableColumnNotNull", c3.getName());
		assertTrue(ts.isColumnNotNull(_PATHTESTDATABASES, "testDB", "testTableColumnNotNull", c1.getName()));
		assertTrue(ts.isColumnNotNull(_PATHTESTDATABASES, "testDB", "testTableColumnNotNull", c2.getName()));
		assertFalse(ts.isColumnNotNull(_PATHTESTDATABASES, "testDB", "testTableColumnNotNull", c3.getName()));
		ts.dropColumnNotNull(_PATHTESTDATABASES, "testDB", "testTableColumnNotNull", c1.getName());
		assertFalse(ts.isColumnNotNull(_PATHTESTDATABASES, "testDB", "testTableColumnNotNull", c1.getName()));
        assertTrue(ts.isColumnNotNull(_PATHTESTDATABASES, "testDB", "testTableColumnNotNull", c2.getName()));
		assertFalse(ts.isColumnNotNull(_PATHTESTDATABASES, "testDB", "testTableColumnNotNull", c3.getName()));
		ts.dropColumnNotNull(_PATHTESTDATABASES, "testDB", "testTableColumnNotNull",c2.getName());
		assertFalse(ts.isColumnNotNull(_PATHTESTDATABASES, "testDB", "testTableColumnNotNull", c1.getName()));
		assertFalse(ts.isColumnNotNull(_PATHTESTDATABASES, "testDB", "testTableColumnNotNull", c2.getName()));
		assertFalse(ts.isColumnNotNull(_PATHTESTDATABASES, "testDB", "testTableColumnNotNull", c3.getName()));
	}

	/**
	 * 
	 * This Method must be launched alone, because it could be that there are other
	 * tables in the DB and so it will give a wrong result.
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
