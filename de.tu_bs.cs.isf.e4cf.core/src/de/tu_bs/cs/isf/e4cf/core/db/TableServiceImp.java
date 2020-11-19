package de.tu_bs.cs.isf.e4cf.core.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableServiceImp extends TableUtilities implements ITableService {

	@Override
	public void createTable(final String pPath, final String pDbName, final String tableName,
			final Column... attributs) {
		// Jessica
	}

	/**
	 * Method to create a table from a java-class in a generic way.
	 * 
	 * @param pPath   String the path of the database
	 * @param pDbName String the name of the database
	 * @param cls     the class from which the table will be created
	 * @throws SQLException
	 */
	@Override
	public void createTable(final String pPath, final String pDbName, final Class<?> cls) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		final Statement s = con.createStatement();
		final String tableName = cls.getSimpleName();
		if (!tableExists(pPath, pDbName, cls.getSimpleName())) {
			String sqlStatment = "CREATE TABLE " + tableName + " (";
			// get all attribute objects from the java-class
			final Field[] fieldlist = cls.getDeclaredFields();
			for (final Field aFieldlist : fieldlist) {
				sqlStatment += aFieldlist.getName() + " " + aFieldlist.getType().getSimpleName() + ", ";
			}
			// remove the last comma
			sqlStatment = sqlStatment.substring(0, sqlStatment.length() - 2);
			sqlStatment += ");";
			s.execute(sqlStatment);
			System.out.println("Table " + tableName + " created.");
		} else {
			System.out.println("Table " + tableName + " already exists.");
		}
		con.close();
	}

	@Override
	public void deleteTable(final String pPath, final String pDbName, final String tableName) {
		// Shupei
	}

	@Override
	public void renameTable(final String pPath, final String pDbName, final String tableName) {
		// Xen
	}

	@Override
	public void addColumn(final String pPath, final String pDbName, final String tableName, final Column... attributs) {
		// Jessica
	}

	@Override
	public void deleteColumn(final String pPath, final String pDbName, final String tableName,
			final String... attributNames) {
		// Shupei
	}

	@Override
	public void makeColumnPrimaryKey(final String pPath, final String pDbName, final String tableName,
			final String... columnNames) {
		// Xen
	}

	@Override
	public void dropPrimaryKey(final String pPath, final String pDbName, final String tableName,
			final String... columnNames) {
		// Jessica
	}

	@Override
	public void makeColumnAutoIncrement(final String pPath, final String pDbName, final String tableName,
			final String columnNames) {
		// Shupei
	}

	@Override
	public void makeColumnUnique(final String pPath, final String pDbName, final String tableName,
			final String columnNames) {
		// Xen
	}

	@Override
	public void makeColumnNotNull(final String pPath, final String pDbName, final String tableName,
			final String columnNames) {
		// Rami
	}

}
