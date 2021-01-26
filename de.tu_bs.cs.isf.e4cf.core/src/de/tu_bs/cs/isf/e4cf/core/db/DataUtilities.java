package de.tu_bs.cs.isf.e4cf.core.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataUtilities {

	/**
	 * Method to get the number of rows of a table.
	 * 
	 * @param pPath     String the path of the database
	 * @param pDbName   String the name of the database
	 * @param tableName String name of the table
	 * @return int Number of rowd in the table
	 */
	public int getTableNumberRows(final String pPath, final String pDbName, final String pTableName) {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		int rowcounts = 0;
		try {
			final Statement stm = con.createStatement();
			final ResultSet rs = stm.executeQuery("SELECT * FROM " + pTableName);
			rowcounts = 0;
			while (rs.next()) {
				rowcounts++;
			}
			con.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return rowcounts;
	}

	/**
	 * Method to print a table.
	 * 
	 * @param pPath     String the path of the database
	 * @param pDbName   String the name of the database
	 * @param tableName String name of the table
	 * @throws SQLException
	 */
	public void printTable(final String pPath, final String pDbName, final String pTableName) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		final Statement stm = con.createStatement();
		final String sqlStatement = "SELECT * FROM " + pTableName;
		printResultSet(stm, sqlStatement);
		con.close();
	}

	/**
	 * Method to get the number of rows in a ResultSet. There is no defined method
	 * for it.
	 * 
	 * @param rs ResultSet
	 * @return int number of rows
	 * @throws SQLException
	 */
	protected long getSizeResultSet(ResultSet rs) throws SQLException {
		long i = 0;
		while (rs.next()) {
			i++;
		}
		return i;
	}

	/**
	 * The main methode to print a table or a ResultSet.
	 * 
	 * @param stm
	 * @param sqlStatement
	 * @throws SQLException
	 */
	protected void printResultSet(final Statement stm, final String sqlStatement) throws SQLException {
		ResultSet rs = stm.executeQuery(sqlStatement);
		System.out
				.println("Number of rows in table '" + rs.getMetaData().getTableName(1) + "': " + getSizeResultSet(rs));
		final String separationLine = getSeparationLine(stm, sqlStatement);
		System.out.println(getSeparationLine(stm, sqlStatement));
		System.out.println(getTableHeader(stm, sqlStatement));
		System.out.println(separationLine);
		printTableData(stm, sqlStatement);
		System.out.println(separationLine);
	}

	/**
	 * Method to get the separation line (+---+---+---+)
	 * 
	 * @param stm
	 * @param sqlStatement
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("resource")
	protected String getSeparationLine(final Statement stm, final String sqlStatement) throws SQLException {
		ResultSet rs = stm.executeQuery(sqlStatement);
		String line = "+";
		for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
			int max = rs.getMetaData().getColumnName(i).length();
			while (rs.next()) {
				if (max < rs.getString(i).length()) {
					max = rs.getString(i).length();
				}
			}
			rs = stm.executeQuery(sqlStatement);
			for (int j = 0; j < max + 2; j++) {
				line += "-";
			}
			line += "+";
		}
		return line;
	}

	/**
	 * Method to get the header of a table.
	 * 
	 * @param stm
	 * @param sqlStatement
	 * @return
	 * @throws SQLException
	 */
	protected String getTableHeader(final Statement stm, final String sqlStatement) throws SQLException {
		ResultSet rs = stm.executeQuery(sqlStatement);
		String colLine = "";
		int colNow = 1;
		int l = 0;
		while (l < getSeparationLine(stm, sqlStatement).length() - 1) {
			if (getSeparationLine(stm, sqlStatement).charAt(l) == '+'
					&& l != getSeparationLine(stm, sqlStatement).length() - 1) {
				colLine += "| " + rs.getMetaData().getColumnName(colNow) + " ";
				l += 3 + rs.getMetaData().getColumnName(colNow).length();
				colNow++;
			} else {
				colLine += " ";
				l++;
			}
		}
		colLine += "|";
		return colLine;
	}

	/**
	 * Method to print the data of a table.
	 * 
	 * @param stm
	 * @param sqlStatement
	 * @return
	 * @throws SQLException
	 */
	protected void printTableData(final Statement stm, final String sqlStatement) throws SQLException {
		ResultSet rs = stm.executeQuery(sqlStatement);
		final String separationLine = getSeparationLine(stm, sqlStatement);
		String tabLine;
		while (rs.next()) {
			tabLine = "";
			int columnNow = 1;
			int ll = 0;
			while (ll < separationLine.length() - 1) {
				if (separationLine.charAt(ll) == '+' && ll != separationLine.length() - 1) {
					tabLine += "| " + rs.getString(columnNow) + " ";
					ll += 3 + rs.getString(columnNow).length();
					columnNow++;
				} else {
					tabLine += " ";
					ll++;
				}
			}
			tabLine += "|";
			System.out.println(tabLine);
		}
	}
}
