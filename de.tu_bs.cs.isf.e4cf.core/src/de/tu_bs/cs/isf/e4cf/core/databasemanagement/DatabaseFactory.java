package de.tu_bs.cs.isf.e4cf.core.databasemanagement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
	 * Method to get a database if it exists.
	 * 
	 * @param pPath String the path where the database is situated 
	 * @param pDbName String the name of the database
	 * @return Connection
	 * @throws SQLException error while getting database
	 */
	public Connection getDatabase(String pPath, String pDbName) throws SQLException {
		if (databaseExists(pPath, pDbName)) {
			System.out.println("Getting database " + pDbName + ".");
			return DriverManager.getConnection("jdbc:sqlite:" + pPath + pDbName);
		} else {
			System.out.println(pDbName + " database does not exist.");
			return null;
		}
	}
	
	/**
	 * Method to delete a database if it exists already.
	 * 
	 * @param pPath String the path where the database is situated
	 * @param pDbName String the name of the database
	 * @throws SQLException error while deleting database
	 * @throws IOException 
	 */
	public void deleteDatabase(String pPath, String pDbName) throws SQLException, IOException {		
		if (databaseExists(pPath, pDbName)) {
			System.out.println("Deleting database " + pDbName + ".");
			getDatabase(pPath, pDbName).close();
			final File file = new File(pPath + pDbName);
			file.delete();
			System.out.println("Database deleted.");
		} else {
			System.out.println(pDbName + " database does not exist.");
		}
	}

	/**
	 * Method to change the name of a database.
	 * 
	 * @param pPath String the path where the database is situated
	 * @param pOldDbName pDbName String the old name of the database 
	 * @param pNewDbName pDbName String the new name of the database
	 */
	public void renameDatabase(String pPath, String pOldDbName, String pNewDbName) {
		if (databaseExists(pPath, pOldDbName)) {
			//hier kommt code SHUPEI
		} else {
			System.out.println(pOldDbName + " database does not exist.");
		}
	}
	
	
	/**
	 * Method to move a database to an other directory.
	 * 
	 * @param pDbName String the name of the database 
	 * @param pOldPath String the path where the database is situated
	 * @param pNewPath String the new directory where the database must be placed
	 * @throws IOException
	 */
	public void moveDatabase(String pDbName, String pOldPath,  String pNewPath) throws IOException {
		if (databaseExists(pOldPath, pDbName)) {
			//hier kommt code, bitte den folder testDatabases nutzen XEN
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
	private boolean databaseExists(String pPath, String pDbName) {
		final File file = new File(pPath + pDbName);
		return file.exists();
	}
}
