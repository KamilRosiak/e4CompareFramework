package de.tu_bs.cs.isf.e4cf.core.db.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import de.tu_bs.cs.isf.e4cf.core.db.DatabaseFactory;

/**
 * Junittests for the database management. if something goes wrong: delete all
 * files in folder testDatabases then try every test alone.
 *
 */
class Test_DatabaseFactory {

	private final String _PATHTESTDATABASES = "./testDatabases/";

	@Test
	void testCreateDatabase() throws SQLException, IOException {
		DatabaseFactory.getInstance().createDatabase(_PATHTESTDATABASES, "testCreation1");
		assertTrue(databaseExists(_PATHTESTDATABASES, "testCreation1"));
		DatabaseFactory.getInstance().createDatabase(_PATHTESTDATABASES, "testCreation1");
		assertTrue(databaseExists(_PATHTESTDATABASES, "testCreation1"));
		DatabaseFactory.getInstance().createDatabase(_PATHTESTDATABASES, "testCreation2");
		assertTrue(databaseExists(_PATHTESTDATABASES, "testCreation2"));
	}

	@Test
	void testDeleteDatabase() throws SQLException, IOException {
		DatabaseFactory.getInstance().deleteDatabase(_PATHTESTDATABASES, "testCreation1");
		assertTrue(!databaseExists(_PATHTESTDATABASES, "testCreation1"));
		DatabaseFactory.getInstance().deleteDatabase(_PATHTESTDATABASES, "testCreation1");
		assertTrue(!databaseExists(_PATHTESTDATABASES, "testCreation1"));
		DatabaseFactory.getInstance().deleteDatabase(_PATHTESTDATABASES, "testCreation2");
		assertTrue(!databaseExists(_PATHTESTDATABASES, "testCreation2"));
	}

	@Test
	void testRenameDatabase() throws SQLException, IOException {
		DatabaseFactory.getInstance().renameDatabase(_PATHTESTDATABASES, "testCreation1", "testCreation3");
		assertTrue(!databaseExists(_PATHTESTDATABASES, "testCreation1")
				&& databaseExists(_PATHTESTDATABASES, "testCreation3"));
		DatabaseFactory.getInstance().renameDatabase(_PATHTESTDATABASES, "testCreation3", "testCreation1");
		assertTrue(!databaseExists(_PATHTESTDATABASES, "testCreation3")
				&& databaseExists(_PATHTESTDATABASES, "testCreation1"));
		DatabaseFactory.getInstance().renameDatabase(_PATHTESTDATABASES, "testCreation1", "testCreation1");
		assertTrue(databaseExists(_PATHTESTDATABASES, "testCreation1"));
	}

	/**
	 * Method to check whether a database exists or not.
	 * 
	 * @param pPath   String the path of the database
	 * @param pDbName String the name of the database
	 * @return boolean database exists or not
	 */
	protected boolean databaseExists(String pPath, String pDbName) {
		final File file = new File(pPath + pDbName);
		return file.exists();
	}
}
