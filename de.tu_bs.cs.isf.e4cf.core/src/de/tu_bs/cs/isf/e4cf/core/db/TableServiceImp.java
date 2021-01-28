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
	 * Method to create a table in a given database. There must be at least one
	 * attribute to create a table, else SQLFailure.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String the name of the table
	 * @param attributes Class which contains the attribute and its data type
	 */
	@Override
	public void createTable(final String pPath, final String pDbName, final String pTableName,
			final Column... attributes) {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		try {
			final Statement stmt = con.createStatement();
			// a flag to mark, if there is a public key in the attributes.
			boolean primaryKey = false;
			// at least one attribute to the table, else SQLFailure
			if (attributes.length > 0) {
				if (!tableExists(pPath, pDbName, pTableName)) {
					String sqlStatement = "CREATE TABLE " + pTableName + "(";
					String sqlPrimaryKey = "CONSTRAINT " + pTableName + "_pl PRIMARY KEY (";
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
					// System.out.println("Test SQLStatement: " + sqlStatement);
					stmt.execute(sqlStatement);
					System.out.println("Table created: " + pTableName);
				} else {
					System.out.println("Table already exists: " + pTableName);
				}
			} else {
				System.err.println("Can not create table without column(s).");
			}
			con.close();
		} catch (SQLException e) {
			System.err.println("Error while creating table: " + pTableName + ". " + e.getMessage());
		}
	}

	/**
	 * Method to create a table from a java-class in a generic way. This is actually
	 * a luxury class.
	 * 
	 * @param pPath   String the path of the database
	 * @param pDbName String the name of the database
	 * @param cls     the class from which the table will be created
	 */
	@Override
	public void createTable(final String pPath, final String pDbName, final Class<?> cls) {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		try {
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
				System.out.println("Table created: " + tableName);
			} else {
				System.out.println("Table already exists: " + tableName);
			}
			con.close();
		} catch (SecurityException e) {
			System.err.println("Security Issue: " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Error while creating table from class: " + cls.getSimpleName() + ". " + e.getMessage());
		}
	}

	/**
	 * Method to delete a table.
	 * 
	 * @param pPath     pPath String the path of the database
	 * @param pDbName   String the name of the database
	 * @param tableName String the name of the table
	 */
	@Override
	public void deleteTable(final String pPath, final String pDbName, final String pTableName) {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		try {
			final Statement s = con.createStatement();
			if (tableExists(pPath, pDbName, pTableName)) {
				final String sqlStatement = "DROP TABLE " + pTableName + ";";
				s.execute(sqlStatement);
				System.out.println("Table deleted: " + pTableName);
			} else {
				System.out.println("Table does not exist: " + pTableName);
			}
			con.close();
		} catch (SQLException e) {
			System.err.println("Error while deleting table: " + pTableName + ". " + e.getMessage());
		}
	}

	/**
	 * Method to rename a table.
	 * 
	 * @param pPath        String the path of the database
	 * @param pDbName      String the name of the database
	 * @param tableName    String the old name of the table
	 * @param NewtableName String the new name of the table
	 */
	@Override
	public void renameTable(final String pPath, final String pDbName, final String pTableName,
			final String pNewTableName) {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		try {
			final Statement s = con.createStatement();
			if (tableExists(pPath, pDbName, pTableName)) {
				if (!tableExists(pPath, pDbName, pNewTableName)) {
					if (!pTableName.equals(pNewTableName)) {
						final String sqlStatement = "ALTER TABLE " + pTableName + " " + "RENAME TO " + pNewTableName
								+ ";";
						s.execute(sqlStatement);
						System.out.println("Renaming tablename " + pTableName + " to " + pNewTableName);
					}
				} else {
					System.out.println("Can not Rename table with an existing name: " + pNewTableName);
				}
			} else {
				System.out.println("Table does not exist: " + pTableName);
			}
			con.close();
		} catch (SQLException e) {
			System.err.println("Error while renaming table: " + pTableName + ". " + e.getMessage());
		}
	}

	/**
	 * Method to add columns to an existing table.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String the name of the table
	 * @param attributes Class with the name and data type of the columns which
	 *                   should be added
	 */
	@Override
	public void addColumn(final String pPath, final String pDbName, final String pTableName,
			final Column... attributes) {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		try {
			final Statement stmt = con.createStatement();
			if (tableExists(pPath, pDbName, pTableName)) {
				for (Column c : attributes) {
					if (!columnExists(pPath, pDbName, pTableName, c.getName())) {
						try {
							final String sqlStatement = "ALTER TABLE " + pTableName + " ADD " + c.getName() + " "
									+ c.getType() + ";";
							stmt.execute(sqlStatement);
							if (c.isNotNull()) {
								makeColumnNotNull(pPath, pDbName, pTableName, c.getName());
							}
							if (c.isPrimaryKey()) {
								makeColumnPrimaryKey(pPath, pDbName, pTableName, c.getName());
							}
							if (c.isUnique()) {
								makeColumnUnique(pPath, pDbName, pTableName, c.getName());
							}
							if (c.isAutoIncrement()) {
								makeColumnAutoIncrement(pPath, pDbName, pTableName, c.getName());
							}
							System.out.println("Column " + c.getName() + " added to table: " + pTableName);
						} catch (Exception e) {
							System.err.println("Error while adding column: " + c.getName() + ". " + e.getMessage());
						}
					} else {
						System.out.println("Column already exists: " + c.getName());
					}
				}
			} else {
				System.out.println("Table does not exist:" + pTableName);
			}
			con.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
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
	public void renameColumn(final String pPath, final String pDbName, final String pTableName,
			final String pColumnName, final String pNewColumnName) {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		try {
			final Statement s = con.createStatement();
			if (tableExists(pPath, pDbName, pTableName)) {
				if (!columnExists(pPath, pDbName, pTableName, pNewColumnName)) {
					if (columnExists(pPath, pDbName, pTableName, pColumnName)) {
						final String sqlStatement = "ALTER TABLE " + pTableName + " RENAME COLUMN " + pColumnName
								+ " TO " + pNewColumnName + ";";
						s.execute(sqlStatement);
						System.out.println("Column renamed from " + pColumnName + " to " + pNewColumnName);
					} else {
						System.out.println("Column does not exist: " + pColumnName);
					}
				} else {
					System.out.println("Can not rename with an existing columnname " + pNewColumnName + ".");
				}
			} else {
				System.out.println("Table does not exist:" + pTableName);
			}
			con.close();
		} catch (SQLException e) {
			System.err.println("Error while renaming column: " + pColumnName + ". " + e.getMessage());
		}
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
	public void deleteColumn(String pPath, String pDbName, String pTableName, String... columns) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, pTableName)) {
			renameTable(pPath, pDbName, pTableName, "old_" + pTableName);
			List<Column> cols = getColumnsTable(pPath, pDbName, "old_" + pTableName);
			cols = new ArrayList<Column>(cols);
			for (String c : columns) {
				if (null == getColumn(cols, c)) {
					System.out.println("Column does not exist: " + c);
				} else {
					cols.remove(getColumn(cols, c));
					System.out.println("Column deleted: " + c);
				}
			}
			Column[] col = new Column[cols.size()];
			col = cols.toArray(col);
			createTable(pPath, pDbName, pTableName, col);
			deleteTable(pPath, pDbName, "old_" + pTableName);
		} else {
			System.out.println("Table does not exist: " + pTableName);
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
	public void makeColumnPrimaryKey(final String pPath, final String pDbName, final String pTableName,
			final String... columnNames) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, pTableName)) {
			// Security issue
			if (!tableHasData(pPath, pDbName, pTableName)) {
				setColumnPrimaryKey(pPath, pDbName, pTableName, true, columnNames);
			} else {
				System.err.println("Table has already data. Such change could bring an SQLFailure.");
			}
		} else {
			System.out.println("Table does not exist: " + pTableName);
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
	public void dropColumnPrimaryKey(final String pPath, final String pDbName, final String pTableName,
			final String... columnNames) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, pTableName)) {
			// Security issue
			if (!tableHasData(pPath, pDbName, pTableName)) {
				setColumnPrimaryKey(pPath, pDbName, pTableName, false, columnNames);
			} else {
				System.err.println("Table has already data. Such change could bring an SQLFailure.");
			}
		} else {
			System.out.println("Table does not exist: " + pTableName);
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
	public void makeColumnAutoIncrement(final String pPath, final String pDbName, final String pTableName,
			final String columnName) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, pTableName)) {
			// Security issue
			if (!tableHasData(pPath, pDbName, pTableName)) {
				setColumnAutoIncrement(pPath, pDbName, pTableName, true, columnName);
			} else {
				System.err.println("Table has already data. Such change could bring an SQLFailure.");
			}
		} else {
			System.out.println("Table does not exist: " + pTableName);
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
	public void dropColumnAutoIncrement(String pPath, String pDbName, String pTableName, String columnName)
			throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, pTableName)) {
			// Security issue
			if (!tableHasData(pPath, pDbName, pTableName)) {
				setColumnAutoIncrement(pPath, pDbName, pTableName, false, columnName);
			} else {
				System.err.println("Table has already data. Such change could bring an SQLFailure.");
			}
		} else {
			System.out.println("Table does not exist: " + pTableName);
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
	public void makeColumnUnique(final String pPath, final String pDbName, final String pTableName,
			final String columnName) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, pTableName)) {
			// Security issue
			if (!tableHasData(pPath, pDbName, pTableName)) {
				setColumnUnique(pPath, pDbName, pTableName, true, columnName);
			} else {
				System.err.println("Table has already data. Such change could bring an SQLFailure.");
			}
		} else {
			System.out.println("Table does not exist: " + pTableName);
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
	public void dropColumnUnique(String pPath, String pDbName, String pTableName, String columnName)
			throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, pTableName)) {
			// Security issue
			if (!tableHasData(pPath, pDbName, pTableName)) {
				setColumnUnique(pPath, pDbName, pTableName, false, columnName);
			} else {
				System.err.println("Table has already data. Such change could bring an SQLFailure.");
			}
		} else {
			System.out.println("Table does not exist: " + pTableName);
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
	public void makeColumnNotNull(final String pPath, final String pDbName, final String pTableName,
			final String... columnNames) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, pTableName)) {
			// Security issue
			if (!tableHasData(pPath, pDbName, pTableName)) {
				setColumnNotNull(pPath, pDbName, pTableName, true, columnNames);
			} else {
				System.err.println("Table has already data. Such change could bring an SQLFailure.");
			}
		} else {
			System.out.println("Table does not exist: " + pTableName);
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
	public void dropColumnNotNull(String pPath, String pDbName, String pTableName, String... columnNames)
			throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, pTableName)) {
			// Security issue
			if (!tableHasData(pPath, pDbName, pTableName)) {
				setColumnNotNull(pPath, pDbName, pTableName, false, columnNames);
			} else {
				System.err.println("Table has already data. Such change could bring an SQLFailure.");
			}
		} else {
			System.out.println("Table does not exist: " + pTableName);
		}
		con.close();
	}

	private void setColumnPrimaryKey(final String pPath, final String pDbName, final String tableName,
			final boolean state, final String... columnNames) {
		renameTable(pPath, pDbName, tableName, "old_" + tableName);
		List<Column> columns = getColumnsTable(pPath, pDbName, "old_" + tableName);
		for (final String c : columnNames) {
			getColumn(columns, c).setPrimaryKey(state);
		}
		Column[] col = new Column[columns.size()];
		col = columns.toArray(col);
		createTable(pPath, pDbName, tableName, col);
		deleteTable(pPath, pDbName, "old_" + tableName);
	}

	private void setColumnAutoIncrement(final String pPath, final String pDbName, final String tableName,
			final boolean state, final String... columnNames) {
		renameTable(pPath, pDbName, tableName, "old_" + tableName);
		List<Column> columns = getColumnsTable(pPath, pDbName, "old_" + tableName);
		for (final String c : columnNames) {
			getColumn(columns, c).setAutoIncrement(state);
		}
		Column[] col = new Column[columns.size()];
		col = columns.toArray(col);
		createTable(pPath, pDbName, tableName, col);
		deleteTable(pPath, pDbName, "old_" + tableName);
	}

	private void setColumnUnique(final String pPath, final String pDbName, final String tableName, final boolean state,
			final String... columnNames) {
		renameTable(pPath, pDbName, tableName, "old_" + tableName);
		List<Column> columns = getColumnsTable(pPath, pDbName, "old_" + tableName);
		for (final String c : columnNames) {
			getColumn(columns, c).setUnique(state);
		}
		Column[] col = new Column[columns.size()];
		col = columns.toArray(col);
		createTable(pPath, pDbName, tableName, col);
		deleteTable(pPath, pDbName, "old_" + tableName);
	}

	private void setColumnNotNull(final String pPath, final String pDbName, final String tableName, final boolean state,
			final String... columnNames) {
		renameTable(pPath, pDbName, tableName, "old_" + tableName);
		List<Column> columns = getColumnsTable(pPath, pDbName, "old_" + tableName);
		for (final String c : columnNames) {
			getColumn(columns, c).setNotNull(state);
		}
		Column[] col = new Column[columns.size()];
		col = columns.toArray(col);
		createTable(pPath, pDbName, tableName, col);
		deleteTable(pPath, pDbName, "old_" + tableName);
	}
}
