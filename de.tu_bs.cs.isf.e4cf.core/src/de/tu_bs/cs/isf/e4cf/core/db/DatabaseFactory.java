package de.tu_bs.cs.isf.e4cf.core.db;

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
 * A singleton class to manage databases in the project. By creating a database,
 * if no path is given the database-file would be put directly under the
 * Projectpath, else in the given folder (existing file !!).
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
	protected DatabaseFactory() {
		// load JDBC-driver in driver-manager
		try {
			Class.forName(_JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			System.err.println("Error while loading JDBC-Driver: " + _JDBC_DRIVER + "." + e.getMessage());
		}
	}

	/**
	 * Methode to get the instance of the class.
	 * 
	 * @return ConnectionFactory instance of class
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
	public void createDatabase(final String pPath, final String pDbName) {
		if (!databaseExists(pPath, pDbName)) {
			try {
				final Connection con = DriverManager.getConnection("jdbc:sqlite:" + pPath + pDbName);
				con.close();
				System.out.println("Database created: " + pDbName);
			} catch (SQLException e) {
				System.err.println("Error while creating database: " + pDbName + ". " + e.getMessage());
			}
		} else {
			System.out.println("Database already exists: " + pDbName);
		}
	}

	/**
	 * Method to get a database if it exists.
	 * 
	 * @param pPath   String the path where the database is situated
	 * @param pDbName String the name of the database
	 * @return Connection
	 */
	public Connection getDatabase(final String pPath, final String pDbName) {
		if (databaseExists(pPath, pDbName)) {
			try {
				return DriverManager.getConnection("jdbc:sqlite:" + pPath + pDbName);
			} catch (SQLException e) {
				System.err.println("Error while getting database: " + pDbName + ". " + e.getMessage());
			}
		} else {
			System.out.println("Database does not exist: " + pDbName);
		}
		return null;
	}

	/**
	 * Method to delete a database if it exists already.
	 * 
	 * @param pPath   String the path where the database is situated
	 * @param pDbName String the name of the database
	 * @throws SQLException error while deleting database
	 * @throws IOException
	 */
	public void deleteDatabase(final String pPath, final String pDbName) {
		if (databaseExists(pPath, pDbName)) {
			new File(pPath + pDbName).delete();
			System.out.println("Database deleted: " + pDbName);
		} else {
			System.out.println("Database does not exist: " + pDbName);
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
			System.out.println("Database does not exist: " + pOldDbName);
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
			final Path source = Paths.get(pOldPath + pDbName);
			final Path newdir = Paths.get(pNewPath);
			try {
				Files.move(source, newdir.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
				System.out.println(pDbName + " was moved successfully from " + pOldPath + " to " + pNewPath + ".");
			} catch (IOException e) {
				System.err.println("Error while moving database: " + pDbName);
			}
		} else {
			System.out.println("Database does not exist: " + pDbName);
		}
	}

	/**
	 * Method to check whether a database exists or not.
	 * 
	 * @param pPath   String the path of the database
	 * @param pDbName String the name of the database
	 * @return boolean database exists or not
	 */
	protected boolean databaseExists(final String pPath, final String pDbName) {
		final File file = new File(pPath + pDbName);
		return file.exists();
	}
}
