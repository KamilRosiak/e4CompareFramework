package de.tu_bs.cs.isf.e4cf.core.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.core.db.model.Column;

public class TableServiceImp extends TableUtilities implements ITableService {

	/**
	 * Method to create a table from a given String tableName and the attributes
	 * from the class Column.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String the name of the table
	 * @param attributes Class which contains the attributes and their data types
	 * @throws SQLException
	 */
	@Override
	public void createTable(final String pPath, final String pDbName, final String tableName,
			final Column... attributes) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		final Statement stmt = con.createStatement();
		boolean primaryKey = false;
		if (attributes.length > 0) {
			if (!tableExists(pPath, pDbName, tableName)) {
				String sqlStatement = "CREATE TABLE " + tableName + "(";
				String sqlPrimaryKey = "CONSTRAINT " + tableName + "_pl PRIMARY KEY (";
				for (final Column c : attributes) {
					sqlStatement += c.getName() + " " + c.getType();
					if (c.isUnique()) {
						sqlStatement += " UNIQUE";
					}
					if (c.isAutoIncrement()) {
						sqlStatement += " PRIMARY KEY AUTOINCREMENT";
					}
					if (c.isNotNull()) {
						sqlStatement += " NOT NULL";
					}
					if (c.isPrimaryKey()) {
						sqlPrimaryKey += c.getName() + ", ";
						primaryKey = true;
					}
					sqlStatement += ", ";
				}
				if (primaryKey) {
					sqlPrimaryKey = sqlPrimaryKey.substring(0, sqlPrimaryKey.length() - 2) + ") ";
					sqlStatement += sqlPrimaryKey + ");";
				} else {
					sqlStatement = sqlStatement.substring(0, sqlStatement.length() - 2) + ");";
				}
				// System.out.println("Test STMT: " + sqlStatement);
				stmt.execute(sqlStatement);
				System.out.println("Table " + tableName + " created.");
			} else {
				System.out.println("Table " + tableName + " already exists.");
			}
		} else {
			System.out.println("Can not create table without column(s).");
		}
		con.close();
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
			String sqlStatement = "CREATE TABLE " + tableName + " (";
			// get all attribute objects from the java-class
			final Field[] fieldlist = cls.getDeclaredFields();
			for (final Field aFieldlist : fieldlist) {
				sqlStatement += aFieldlist.getName() + " " + aFieldlist.getType().getSimpleName() + ", ";
			}
			// remove the last comma
			sqlStatement = sqlStatement.substring(0, sqlStatement.length() - 2);
			sqlStatement += ");";
			s.execute(sqlStatement);
			System.out.println("Table " + tableName + " created.");
		} else {
			System.out.println("Table " + tableName + " already exists.");
		}
		con.close();
	}

	/**
	 * Method to delete a table.
	 * 
	 * @param pPath     pPath String the path of the database
	 * @param pDbName   String the name of the database
	 * @param tableName String the name of the table
	 * @throws SQLException
	 */
	@Override
	public void deleteTable(final String pPath, final String pDbName, final String tableName) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		final Statement s = con.createStatement();
		if (tableExists(pPath, pDbName, tableName)) {
			final String sqlStatement = "DROP TABLE " + tableName + ";";
			s.execute(sqlStatement);
			System.out.println("Table " + tableName + " deleted.");
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
	}

	/**
	 * Method to rename a table.
	 * 
	 * @param pPath        String the path of the database
	 * @param pDbName      String the name of the database
	 * @param tableName    String the old name of the table
	 * @param NewtableName String the new name of the table
	 * @throws SQLException
	 */
	@Override
	public void renameTable(final String pPath, final String pDbName, final String tableName, final String newTableName)
			throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		final Statement s = con.createStatement();
		if (tableExists(pPath, pDbName, tableName)) {
			if (!tableExists(pPath, pDbName, newTableName)) {
				if (!tableName.equals(newTableName)) {
					final String sqlStatement = "ALTER TABLE " + tableName + " " + "RENAME TO " + newTableName + ";";
					s.execute(sqlStatement);
					System.out.println("Renaming tablename " + tableName + " to " + newTableName);
				}
			} else {
				System.out.println("Can not Rename tablename " + tableName + " to existing name " + newTableName);
			}
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
	}

	/**
	 * Method to add columns to an existing table.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String the name of the table
	 * @param attributes Class with the name and data type of the columns which
	 *                   should be added
	 * @throws SQLException
	 */
	@Override
	public void addColumn(final String pPath, final String pDbName, final String tableName, final Column... attributes)
			throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		final Statement stmt = con.createStatement();
		if (tableExists(pPath, pDbName, tableName)) {
			for (Column c : attributes) {
				if (!columnExists(pPath, pDbName, tableName, c.getName())) {
					String sqlStatement = "ALTER TABLE " + tableName + " ADD " + c.getName() + " " + c.getType();
					if (c.isNotNull() == true) {
						sqlStatement += " NOT NULL, ";
						sqlStatement = sqlStatement.substring(0, sqlStatement.length() - 2);
					}
					sqlStatement += ";";
					System.out.println(sqlStatement);
					stmt.execute(sqlStatement);
					if (c.isPrimaryKey()) {
						makeColumnPrimaryKey(pPath, pDbName, tableName, c.getName());
					}
					if (c.isUnique()) {
						makeColumnUnique(pPath, pDbName, tableName, c.getName());
					}
					if (c.isAutoIncrement()) {
						makeColumnAutoIncrement(pPath, pDbName, tableName, c.getName());
					}
					System.out.println("Column " + c.getName() + " added to table: " + tableName);
				} else {
					System.out.println("Column " + c.getName() + " already exists.");
				}
			}
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
	}

	/**
	 * Method to rename a column.
	 * 
	 * @param pPath         String the path of the database
	 * @param pDbName       String the name of the database
	 * @param tableName     String the name of the table
	 * @param columnName    String the old name of the column
	 * @param newColumnName String the new name of the column
	 * @throws SQLException
	 */
	@Override
	public void renameColumn(final String pPath, final String pDbName, final String tableName, final String columnName,
			final String newColumnName) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		final Statement s = con.createStatement();
		if (tableExists(pPath, pDbName, tableName)) {
			if (!columnExists(pPath, pDbName, tableName, newColumnName)) {
				if (columnExists(pPath, pDbName, tableName, columnName)) {
					final String sqlStatement = "ALTER TABLE " + tableName + " RENAME COLUMN " + columnName + " TO "
							+ newColumnName + ";";
					s.execute(sqlStatement);
					System.out.println("Renaming column " + columnName + " to " + newColumnName);
				} else {
					System.out.println("Column " + columnName + " does not exist.");
				}
			} else {
				System.out.println("Can not rename " + columnName + " to existing column " + newColumnName + ".");
			}
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
	}

	/**
	 * Method to delete column form an existing table.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String the name of the table
	 * @param attributes Class with the name and data type of the columns which
	 *                   should be deleted
	 * @throws SQLException
	 */
	@Override
	public void deleteColumn(String pPath, String pDbName, String tableName, String... columns) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, tableName)) {
			renameTable(pPath, pDbName, tableName, "old_" + tableName);
			List<Column> cols = getColumnsTable(pPath, pDbName, "old_" + tableName);
			cols = new ArrayList<Column>(cols);
			for (String c : columns) {
				cols.remove(getColumn(cols, c));
				System.out.println(c + " has been deleted;");
			}
			Column[] col = new Column[cols.size()];
			col = cols.toArray(col);
			/*
			 * for(Column c : col) { System.out.println(c.getName()); }
			 */
			createTable(pPath, pDbName, tableName, /* columns.stream().toArray(Column[]::new) */col);

			deleteTable(pPath, pDbName, "old_" + tableName);
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
	}

	/**
	 * Method to add primary key constraints to an existing table.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param columnNames String the name of the columns to which the primary key
	 *                    will be added
	 * @throws SQLException
	 */
	@Override
	public void makeColumnPrimaryKey(final String pPath, final String pDbName, final String tableName,
			final String... columnNames) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, tableName)) {
			setColumnPrimaryKey(pPath, pDbName, tableName, true, columnNames);
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
	}

	/**
	 * Method to drop primary key constraints of an existing table.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param columnNames String the name of the columns of which the primary key
	 *                    will be dropped
	 * @throws SQLException
	 */
	@Override
	public void dropColumnPrimaryKey(final String pPath, final String pDbName, final String tableName,
			final String... columnNames) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, tableName)) {
			setColumnPrimaryKey(pPath, pDbName, tableName, false, columnNames);
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
	}

	/**
	 * Method to make a Column Autoincrement of an existing table, the Prerequisite
	 * is that we have not Primary key.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param columnNames String the name of the columns of which the primary key
	 *                    will be dropped
	 * @throws SQLException
	 */
	@Override
	public void makeColumnAutoIncrement(final String pPath, final String pDbName, final String tableName,
			final String columnName) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, tableName)) {
			// if(isColumnAutoIncrement(pPath, pDbName, tableName, columnName)) {
			setColumnAutoIncrement(pPath, pDbName, tableName, true, columnName);
			// }

		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
	}

	/**
	 * Method to drop Autoincrement of column.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param columnNames String the name of the columns of which the primary key
	 *                    will be dropped
	 * @throws SQLException
	 */
	@Override
	public void dropColumnAutoIncrement(String pPath, String pDbName, String tableName, String columnName)
			throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, tableName)) {
			setColumnAutoIncrement(pPath, pDbName, tableName, false, columnName);
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
	}

	@Override
	/**
	 * Method to add unique constraints to an existing table.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param columnNames String the name of the column to which the unique
	 *                    constraints will be added
	 * @throws SQLException
	 */
	public void makeColumnUnique(final String pPath, final String pDbName, final String tableName,
			final String columnName) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, tableName)) {
			setColumnUnique(pPath, pDbName, tableName, true, columnName);
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
	}

	/**
	 * Method to drop unique constraints of a column.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param columnNames String the name of the column to which the unique
	 *                    constraints will be added
	 * @throws SQLException
	 */
	@Override
	public void dropColumnUnique(String pPath, String pDbName, String tableName, String columnName)
			throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, tableName)) {
			setColumnUnique(pPath, pDbName, tableName, false, columnName);
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
	}

	/**
	 * Method to make a column not nullable.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param columnNames the name of the column to which the NOTNULL will be added
	 * @throws SQLException
	 */
	@Override
	public void makeColumnNotNull(final String pPath, final String pDbName, final String tableName,
			final String... columnNames) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, tableName)) {
			setColumnNotNull(pPath, pDbName, tableName, true, columnNames);
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
	}

	/**
	 * Method to make a column nullable.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param columnNames the name of the column to which the NOTNULL will be added
	 * @throws SQLException
	 */
	@Override
	public void dropColumnNotNull(String pPath, String pDbName, String tableName, String... columnNames)
			throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, tableName)) {
			setColumnNotNull(pPath, pDbName, tableName, false, columnNames);
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
	}

	private void setColumnPrimaryKey(final String pPath, final String pDbName, final String tableName,
			final boolean state, final String... columnNames) throws SQLException {
		renameTable(pPath, pDbName, tableName, "old_" + tableName);
		List<Column> columns = getColumnsTable(pPath, pDbName, "old_" + tableName);
		for (final String c : columnNames) {
			getColumn(columns, c).setPrimaryKey(state);
		}
		Column[] col = new Column[columns.size()];
		col = columns.toArray(col);
		createTable(pPath, pDbName, tableName, /* columns.stream().toArray(Column[]::new) */col);
		deleteTable(pPath, pDbName, "old_" + tableName);
	}

	private void setColumnAutoIncrement(final String pPath, final String pDbName, final String tableName,
			final boolean state, final String... columnNames) throws SQLException {
		renameTable(pPath, pDbName, tableName, "old_" + tableName);
		List<Column> columns = getColumnsTable(pPath, pDbName, "old_" + tableName);
		for (final String c : columnNames) {
			getColumn(columns, c).setAutoIncrement(state);
		}
		Column[] col = new Column[columns.size()];
		col = columns.toArray(col);
		createTable(pPath, pDbName, tableName, /* columns.stream().toArray(Column[]::new) */col);
		deleteTable(pPath, pDbName, "old_" + tableName);
	}

	private void setColumnUnique(final String pPath, final String pDbName, final String tableName, final boolean state,
			final String... columnNames) throws SQLException {
		renameTable(pPath, pDbName, tableName, "old_" + tableName);
		List<Column> columns = getColumnsTable(pPath, pDbName, "old_" + tableName);
		for (final String c : columnNames) {
			getColumn(columns, c).setUnique(state);
		}
		Column[] col = new Column[columns.size()];
		col = columns.toArray(col);
		createTable(pPath, pDbName, tableName, /* columns.stream().toArray(Column[]::new) */col);
		deleteTable(pPath, pDbName, "old_" + tableName);
	}

	private void setColumnNotNull(final String pPath, final String pDbName, final String tableName, final boolean state,
			final String... columnNames) throws SQLException {
		renameTable(pPath, pDbName, tableName, "old_" + tableName);
		List<Column> columns = getColumnsTable(pPath, pDbName, "old_" + tableName);
		for (final String c : columnNames) {
			getColumn(columns, c).setNotNull(state);
		}
		Column[] col = new Column[columns.size()];
		col = columns.toArray(col);
		createTable(pPath, pDbName, tableName, /* columns.stream().toArray(Column[]::new) */col);
		deleteTable(pPath, pDbName, "old_" + tableName);
	}
}
