package de.tu_bs.cs.isf.e4cf.core.db;

/**
 * 
 * Class that contains the constant outputs.
 *
 */
public final class Messages {

	// Messages concerning database
	public static final String _ER_LOAD_JDBC_D = "Error while loading JDBC-Driver: ";
	public static final String _DB_CR = "Database created: ";
	public static final String _ER_CR_DB = "Error while creating database: ";
	public static final String _DB_AL_EX = "Database already exists: ";
	public static final String _ER_GT_DB = "Error while getting database: ";
	public static final String _DB_NOT_EX = "Database does not exist: ";
	public static final String _DB_RM = "Database deleted: ";
	public static final String _ER_DB_MV = "Error while moving database: ";
	public static final String _DB_MV_SUCC = " was moved successfully from ";
	public static final String _DB_RN_SUCC = "Renaming database ";

	// Messages concerning data
	public static final String _ER_INS_DATA = "Error while insering data: ";
	public static final String _ER_UPD_DATA = "Error while updating data: ";
	public static final String _ER_DEL_DATA = "Error while deleting data: ";
	public static final String _ER_SEL_DATA = "Error while deleting data: ";
	public static final String _ER_CNT = "Error while executing count statement: ";
	public static final String _ER_SUM = "Error while executing sum statement: ";

	// Messages concerning tables
	public static final String _TB_CR = "Table created: ";
	public static final String _TB_AL_EX = "Table already exists: ";
	public static final String _TB_NO_CR = "Can not create table without column(s).";
	public static final String _ER_CR_TB = "Error while creating table: ";
	public static final String _TB_NO_EX = "Table does not exist: ";
	public static final String _ER_SEC = "Security Issue: ";
	public static final String _TB_RM = "Table deleted: ";
	public static final String _ER_RM_TB = "Error while deleting table: ";
	public static final String _ER_RN_TB = "Error while renaming table: ";
	public static final String _ER_RN_CLM = "Error while renaming column: ";
	public static final String _CLM_RM = "Column deleted: ";
	public static final String _CLM_RN_FR_TO = "Column renamed from ";
	public static final String _ERR_CLM_RN_EX_NM = "Can not rename with an existing columnname ";
	public static final String _CLM_AL_EX = "Column already exists: ";
	public static final String _CLM_NO_EX = "Column does not exist: ";
	public static final String _CLM_ADD_SUCC = "Column added: ";
	public static final String _NO_CHANGE_ALLOWED = "Table has already data. Such change could bring an SQLFailure.";
	public static final String _FALSE_TYPE_CONSTRAINT = "False type constraint given.";
	public static final String _ER_GT_TB_METADT = "Error while getting table metadata.";
	public static final String _ER_AD_CLM = "Error while adding column: ";
	public static final String _TB_RN_SUCC = "Renaming tablename ";
	public static final String _TB_NO_RN = "Can not Rename table with an existing name: ";
	public static final String _TB_NR_ROW = "Number of rows in table";

	// SQL Statements
	public static final String INSERT = " INSERT ";
	public static final String INTO = " INTO ";
	public static final String VALUES = " VALUES ";
	public static final String UPDATE = " UPDATE ";
	public static final String SET = " SET ";
	public static final String SELECT = " SELECT ";
	public static final String FROM = " FROM ";
	public static final String STAR = " * ";
	public static final String CREATE = " CREATE ";
	public static final String TABLE = " TABLE ";
	public static final String CONSTRAINT = " CONSTRAINT ";
	public static final String UNIQUE = " UNIQUE ";
	public static final String AUTOINCREMENT = " PRIMARY KEY AUTOINCREMENT ";
	public static final String NOTNULL = " NOT NULL ";
	public static final String DROP = " DROP ";
	public static final String ALTER = " ALTER ";
	public static final String RENAME = " RENAME ";
	public static final String DELETE = " DELETE ";
	public static final String COLUMN = " COLUMN ";
	public static final String ADD = " ADD ";
	public static final String _TO = " to ";
	public static final String AS = " AS ";
	public static final String SUM = " SUM ";
	public static final String COUNT = " COUNT ";
	public static final String DISTINCT = " DISTINCT ";

	private static Messages _INSTANCE = new Messages();

	public synchronized static Messages getInstance() {
		return _INSTANCE;
	}
}
