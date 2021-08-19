package de.tu_bs.cs.isf.e4cf.core.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.core.db.model.Column;

/**
 * DAO class for the implementation of the table logic. CRUD methods and other
 * are implemented hier.
 *
 */
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
					String sqlStatement = Messages.CREATE + Messages.TABLE + pTableName + "(";
					String sqlPrimaryKey = Messages.CONSTRAINT + pTableName + "_pl PRIMARY KEY (";
					for (final Column c : attributes) {
						sqlStatement += c.getName() + " " + c.getType();
						if (c.isUnique()) {
							sqlStatement += Messages.UNIQUE;
						}
						if (c.isAutoIncrement()) {
							sqlStatement += Messages.AUTOINCREMENT;
						}
						if (c.isNotNull()) {
							sqlStatement += Messages.NOTNULL;
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
					System.out.println(Messages._TB_CR + pTableName);
				} else {
					System.out.println(Messages._TB_AL_EX + pTableName);
				}
			} else {
				System.err.println(Messages._TB_NO_CR);
			}
			con.close();
		} catch (SQLException e) {
			System.err.println(Messages._ER_CR_TB + pTableName + ". " + e.getMessage());
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
				String sqlStatement = Messages.CREATE + Messages.TABLE + tableName + " (";
				// get all attribute objects from the java-class
				final Field[] fieldlist = cls.getDeclaredFields();
				for (final Field aFieldlist : fieldlist) {
					sqlStatement += aFieldlist.getName() + " " + aFieldlist.getType().getSimpleName() + ", ";
				}
				// remove the last comma
				sqlStatement = sqlStatement.substring(0, sqlStatement.length() - 2);
				sqlStatement += ");";
				s.execute(sqlStatement);
				System.out.println(Messages._TB_CR + tableName);
			} else {
				System.out.println(Messages._TB_AL_EX + tableName);
			}
			con.close();
		} catch (SecurityException e) {
			System.err.println(Messages._ER_SEC + e.getMessage());
		} catch (SQLException e) {
			System.err.println(Messages._ER_CR_TB + cls.getSimpleName() + ". " + e.getMessage());
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
				final String sqlStatement = Messages.DROP + Messages.TABLE + pTableName + ";";
				s.execute(sqlStatement);
				System.out.println(Messages._TB_RM + pTableName);
			} else {
				System.out.println(Messages._TB_NO_EX + pTableName);
			}
			con.close();
		} catch (SQLException e) {
			System.err.println(Messages._ER_RM_TB + pTableName + ". " + e.getMessage());
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
						final String sqlStatement = Messages.ALTER + Messages.TABLE + pTableName + Messages.RENAME
								+ Messages._TO + pNewTableName + ";";
						s.execute(sqlStatement);
						System.out.println(Messages._TB_RN_SUCC + pTableName + Messages._TO + pNewTableName);
					}
				} else {
					System.out.println(Messages._TB_NO_RN + pNewTableName);
				}
			} else {
				System.out.println(Messages._TB_NO_EX + pTableName);
			}
			con.close();
		} catch (SQLException e) {
			System.err.println(Messages._ER_RN_TB + pTableName + ". " + e.getMessage());
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
							final String sqlStatement = Messages.ALTER + Messages.TABLE + pTableName + Messages.ADD
									+ c.getName() + " " + c.getType() + ";";
							stmt.execute(sqlStatement);
							if (c.isNotNull()) {
								switchColumnNotNull(pPath, pDbName, pTableName, true, c.getName());
							}
							if (c.isPrimaryKey()) {
								switchColumnPrimaryKey(pPath, pDbName, pTableName, true, c.getName());
							}
							if (c.isUnique()) {
								switchColumnUnique(pPath, pDbName, pTableName, true, c.getName());
							}
							if (c.isAutoIncrement()) {
								switchColumnAutoIncrement(pPath, pDbName, pTableName, true, c.getName());
							}
							System.out.println(Messages._CLM_ADD_SUCC + c.getName());
						} catch (Exception e) {
							System.err.println(Messages._ER_AD_CLM + c.getName() + ". " + e.getMessage());
						}
					} else {
						System.out.println(Messages._CLM_AL_EX + c.getName());
					}
				}
			} else {
				System.out.println(Messages._TB_NO_EX + pTableName);
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
						final String sqlStatement = Messages.ALTER + Messages.TABLE + pTableName + Messages.RENAME
								+ Messages.COLUMN + pColumnName + Messages._TO + pNewColumnName + ";";
						s.execute(sqlStatement);
						System.out.println(Messages._CLM_RN_FR_TO + pColumnName + Messages._TO + pNewColumnName);
					} else {
						System.out.println(Messages._CLM_NO_EX + pColumnName);
					}
				} else {
					System.out.println(Messages._ERR_CLM_RN_EX_NM + pNewColumnName + ".");
				}
			} else {
				System.out.println(Messages._TB_NO_EX + pTableName);
			}
			con.close();
		} catch (SQLException e) {
			System.err.println(Messages._ER_RN_CLM + pColumnName + ". " + e.getMessage());
		}
	}

	/**
	 * Method to delete column from an existing table.
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
					System.out.println(Messages._CLM_NO_EX + c);
				} else {
					cols.remove(getColumn(cols, c));
					System.out.println(Messages._CLM_RM + c);
				}
			}
			Column[] col = new Column[cols.size()];
			col = cols.toArray(col);
			createTable(pPath, pDbName, pTableName, col);
			deleteTable(pPath, pDbName, "old_" + pTableName);
		} else {
			System.out.println(Messages._TB_NO_EX + pTableName);
		}
		con.close();
	}

	/**
	 * Method to make or drop primary key constraint of columns.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param state       true/false
	 * @param columnNames the name of columns to make them unique or not.
	 * @throws SQLException
	 */
	@Override
	public void switchColumnPrimaryKey(final String pPath, final String pDbName, final String pTableName,
			final boolean state, final String... columnNames) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, pTableName)) {
			// Security issue
			if (!tableHasData(pPath, pDbName, pTableName)) {
				setColumnConstraint(pPath, pDbName, pTableName, "pk", state, columnNames);
			} else {
				System.err.println(Messages._NO_CHANGE_ALLOWED);
			}
		} else {
			System.out.println(Messages._TB_NO_EX + pTableName);
		}
		con.close();
	}

	/**
	 * Method to make or drop auto increment constraint of columns.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param state       true/false
	 * @param columnNames the name of columns to make them unique or not.
	 * @throws SQLException
	 */
	@Override
	public void switchColumnAutoIncrement(final String pPath, final String pDbName, final String pTableName,
			final boolean state, final String columnName) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, pTableName)) {
			// Security issue
			if (!tableHasData(pPath, pDbName, pTableName)) {
				setColumnConstraint(pPath, pDbName, pTableName, "ai", state, columnName);
			} else {
				System.err.println(Messages._NO_CHANGE_ALLOWED);
			}
		} else {
			System.out.println(Messages._TB_NO_EX + pTableName);
		}
		con.close();
	}

	/**
	 * Method to make or drop unique constraint of columns.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param state       true/false
	 * @param columnNames the name of columns to make them unique or not.
	 * @throws SQLException
	 */
	@Override
	public void switchColumnUnique(final String pPath, final String pDbName, final String pTableName,
			final boolean state, final String... columnNames) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, pTableName)) {
			// Security issue
			if (!tableHasData(pPath, pDbName, pTableName)) {
				setColumnConstraint(pPath, pDbName, pTableName, "un", state, columnNames);
			} else {
				System.err.println(Messages._NO_CHANGE_ALLOWED);
			}
		} else {
			System.out.println(Messages._TB_NO_EX + pTableName);
		}
		con.close();
	}

	/**
	 * Method to make or drop the nullability of columns.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param state       true/false
	 * @param columnNames the name of columns to make them NOTNULL or not.
	 * @throws SQLException
	 */
	@Override
	public void switchColumnNotNull(final String pPath, final String pDbName, final String pTableName,
			final boolean state, final String... columnNames) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, pTableName)) {
			// Security issue
			if (!tableHasData(pPath, pDbName, pTableName)) {
				setColumnConstraint(pPath, pDbName, pTableName, "nn", state, columnNames);
			} else {
				System.err.println(Messages._NO_CHANGE_ALLOWED);
			}
		} else {
			System.out.println(Messages._TB_NO_EX + pTableName);
		}
		con.close();
	}

	/**
	 * A method where a type constraint of column(s) would be set on a given state.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param constraint  nn for notNull, pk for primary key, un for unique, ai for
	 *                    auto increment
	 * @param state       true/false
	 * @param columnNames the name of columns to modify.
	 */
	private void setColumnConstraint(final String pPath, final String pDbName, final String tableName,
			final String constraint, final boolean state, final String... columnNames) {
		renameTable(pPath, pDbName, tableName, "old_" + tableName);
		List<Column> columns = getColumnsTable(pPath, pDbName, "old_" + tableName);
		for (final String c : columnNames) {
			switch (constraint) {
			case "nn":
				getColumn(columns, c).setNotNull(state);
				break;
			case "pk":
				getColumn(columns, c).setPrimaryKey(state);
				break;
			case "un":
				getColumn(columns, c).setUnique(state);
				break;
			case "ai":
				getColumn(columns, c).setAutoIncrement(state);
				getColumn(columns, c).setPrimaryKey(false);
				break;
			default:
				System.err.println(Messages._FALSE_TYPE_CONSTRAINT);
			}

		}
		Column[] col = new Column[columns.size()];
		col = columns.toArray(col);
		createTable(pPath, pDbName, tableName, col);
		deleteTable(pPath, pDbName, "old_" + tableName);
	}
}
