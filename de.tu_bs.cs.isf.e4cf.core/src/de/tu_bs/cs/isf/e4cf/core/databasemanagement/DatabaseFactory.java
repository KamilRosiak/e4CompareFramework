package de.tu_bs.cs.isf.e4cf.core.databasemanagement;

import java.io.File;
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
	public static DatabaseFactory getInstance() {
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
	public Connection createDatabase(String pPath, String pDbName) throws SQLException {
		if (!databaseExists(pPath, pDbName)) {
			System.out.println("Creating database " + pDbName + ".");
			return DriverManager.getConnection("jdbc:sqlite:" + pPath + pDbName);
		} else {
			System.out.println("Database " + pDbName + " already exists.");
			return null;
		}
	}
	
	/**
	 * Method to check whether a database exists or not.
	 * 
	 * @param pPath   String the path of the database
	 * @param pDbName String the name of the database
	 * @return boolean database exists or not
	 */
	private boolean databaseExists(String pPath, String pDbName) {
		final File file = new File(pPath + pDbName);
		return file.exists();
	}
}
