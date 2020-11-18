package de.tu_bs.cs.isf.e4cf.core.db;

public class TableServiceImp extends TableUtilities implements ITableService {

	@Override
	public void createTable(final String pPath, final String pDbName, final String tableName, final Column... attributs) {
		// Jessica
	}

	@Override
	public void createTable(final String pPath, final String pDbName, final Class<?> cls) {
		//Rami
	}

	@Override
	public void deleteTable(final String pPath, final String pDbName, final String tableName) {
		//Shupei
	}

	@Override
	public void renameTable(final String pPath, final String pDbName, final String tableName) {
		//Xen
	}

	@Override
	public void addColumn(final String pPath, final String pDbName, final String tableName, final Column... attributs) {
		//Jessica
	}

	@Override
	public void deleteColumn(final String pPath, final String pDbName, final String tableName, final String... attributNames) {
		//Shupei
	}

	@Override
	public void makeColumnPrimaryKey(final String pPath, final String pDbName, final String tableName, final String... columnNames) {
		//Xen
	}

	@Override
	public void dropPrimaryKey(final String pPath, final String pDbName, final String tableName, final String... columnNames) {
		//Jessica
	}

	@Override
	public void makeColumnAutoIncrement(final String pPath, final String pDbName, final String tableName, final String columnNames) {
		//Shupei
	}

	@Override
	public void makeColumnUnique(final String pPath, final String pDbName, final String tableName, final String columnNames) {
		//Xen
	}

	@Override
	public void makeColumnNotNull(final String pPath, final String pDbName, final String tableName, final String columnNames) {
		//Rami
	}

}
