package de.tu_bs.cs.isf.e4cf.core.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A singleton class to manage databases in the project.
 * 
 *
 */
public class DatabaseFactory {

	private static DatabaseFactory INSTANCE = null;
	private final String _JDBC_DRIVER = "org.sqlite.JDBC";

	/**
	 * Constructor
	 * 
	 */
	private DatabaseFactory() {
		// load JDBC-driver in driver-manager
		try {
			Class.forName(_JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(_JDBC_DRIVER + " can not be loaded.");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return ConnectionFactory the instance of the class
	 */
	public synchronized static DatabaseFactory getInstance() {
		if (null == INSTANCE) {
			INSTANCE = new DatabaseFactory();
		}
		return INSTANCE;
	}

	/**
	 * Method to create a database. The Database will be created if it does not
	 * already exists.
	 * 
	 * @param pPath   String the path where to create the database
	 * @param pDbName String the name of the database
	 * @return Connection
	 * @throws SQLException error while creating database
	 */
	public void createDatabase(final String pPath, final String pDbName) throws SQLException {
		if (!databaseExists(pPath, pDbName)) {
			final Connection con = DriverManager.getConnection("jdbc:sqlite:" + pPath + pDbName);
			con.close();
			System.out.println("Database " + pDbName + " created.");
		} else {
			System.out.println("Database " + pDbName + " already exists.");
		}
	}

	/**
	 * Method to get a database if it exists.
	 * 
	 * @param pPath   String the path where the database is situated
	 * @param pDbName String the name of the database
	 * @return Connection
	 * @throws SQLException error while getting database
	 */
	public Connection getDatabase(final String pPath, final String pDbName) throws SQLException {
		if (databaseExists(pPath, pDbName)) {
			return DriverManager.getConnection("jdbc:sqlite:" + pPath + pDbName);
		} else {
			System.out.println(pDbName + " database does not exist.");
			return null;
		}
	}

	/**
	 * Method to delete a database if it exists already.
	 * 
	 * @param pPath   String the path where the database is situated
	 * @param pDbName String the name of the database
	 * @throws SQLException error while deleting database
	 * @throws IOException
	 */
	public void deleteDatabase(final String pPath, final String pDbName) throws SQLException, IOException {
		if (databaseExists(pPath, pDbName)) {
			getDatabase(pPath, pDbName).close();
			final File file = new File(pPath + pDbName);
			file.delete();
			System.out.println("Database " + pDbName + " deleted.");
		} else {
			System.out.println(pDbName + " database does not exist.");
		}
	}

	/**
	 * Method to change the name of a database.
	 * 
	 * @param pPath      String the path where the database is situated
	 * @param pOldDbName pDbName String the old name of the database
	 * @param pNewDbName pDbName String the new name of the database
	 */
	public void renameDatabase(final String pPath, final String pOldDbName, final String pNewDbName) {
		if (databaseExists(pPath, pOldDbName)) {
			if (!pOldDbName.equals(pNewDbName)) {
				new File(pPath + pOldDbName).renameTo(new File(pPath + pNewDbName));
				System.out.println("Renaming database " + pOldDbName + " to " + pNewDbName);
			}
		} else {
			System.out.println(pOldDbName + " database does not exist.");
		}
	}

	/**
	 * Method to move a database to an other directory.
	 * 
	 * @param pDbName  String the name of the database
	 * @param pOldPath String the path where the database is situated
	 * @param pNewPath String the new directory where the database must be placed
	 * @throws IOException
	 */
	public void moveDatabase(final String pDbName, final String pOldPath, final String pNewPath) {
		if (databaseExists(pOldPath, pDbName)) {
			final File startFile = new File(pOldPath + pDbName);
			final File endDirection = new File(pNewPath);
			if (!endDirection.exists()) {
				endDirection.mkdirs();
			}
			File endFile = new File(endDirection + File.separator + startFile.getName());
			if (startFile.renameTo(endFile)) {
				System.out.println("File moved successfully! Target path��{" + endFile.getAbsolutePath() + "}");
			} else {
				System.out.println("File move failed! Starting path��{" + startFile.getAbsolutePath() + "}");
			}
		} else {
			System.out.println(pDbName + " database does not exist.");
		}
	}

	/**
	 * Method to check whether a database exists or not.
	 * 
	 * @param pPath   String the path of the database
	 * @param pDbName String the name of the database
	 * @return boolean database exists or not
	 */
	private boolean databaseExists(final String pPath, final String pDbName) {
		final File file = new File(pPath + pDbName);
		return file.exists();
	}
}
