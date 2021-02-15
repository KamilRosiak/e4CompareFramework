package de.tu_bs.cs.isf.e4cf.core.db.test;

import java.io.IOException;
import java.sql.SQLException;

import de.tu_bs.cs.isf.e4cf.core.db.DataServiceImp;
import de.tu_bs.cs.isf.e4cf.core.db.DatabaseFactory;
import de.tu_bs.cs.isf.e4cf.core.db.TableServiceImp;
import de.tu_bs.cs.isf.e4cf.core.db.model.AndCondition;
import de.tu_bs.cs.isf.e4cf.core.db.model.Column;
import de.tu_bs.cs.isf.e4cf.core.db.model.ColumnValue;
import de.tu_bs.cs.isf.e4cf.core.db.model.Condition;
import de.tu_bs.cs.isf.e4cf.core.db.model.Sorter;

/**
 * 
 * Test Class
 *
 */
public class ManualSimulation {

	public static void main(String[] args) throws SQLException, IOException {

		final String _DATABASEPATH = "./testDatabases/";
		final String _DATABASENAME = "TestDB";
		final String _NEWDATABASEPATH = "";
		final String _TABLENAME = "testTabelle";

		/* Database */

		 DatabaseFactory.getInstance().createDatabase(_DATABASEPATH, _DATABASENAME);
		 
		 /*DatabaseFactory.getInstance().renameDatabase(_DATABASEPATH, _DATABASENAME, "newName" + _DATABASENAME);
		 
		 DatabaseFactory.getInstance().moveDatabase(_DATABASENAME, _DATABASEPATH, _NEWDATABASEPATH);
		 
		 DatabaseFactory.getInstance().deleteDatabase(_DATABASEPATH, _DATABASENAME);*/

		// _____________________________________________________________________________________

		/* Tables */

		TableServiceImp ts = new TableServiceImp();

		Column column_1 = new Column("id", "integer", false, false, true, false);
		Column column_2 = new Column("name", "varchar (60)", false, true, false, true);
		Column column_3 = new Column("age", "integer");

		ts.createTable(_DATABASEPATH, _DATABASENAME, _TABLENAME, column_1, column_2, column_3); 
		
		/*System.out.println("Columns: "+ts.getColumnsTable(_DATABASEPATH, _DATABASENAME, _TABLENAME));

		ts.deleteTable(_DATABASEPATH, _DATABASENAME, _TABLENAME);
		 
		ts.renameTable(_DATABASEPATH, _DATABASENAME, _TABLENAME, "newName" + _TABLEENAME);

		ts.deleteColumn(_DATABASEPATH, _DATABASENAME, _TABLENAME, "id");

		ts.addColumn(_DATABASEPATH, _DATABASENAME, _TABLENAME, column_3);
		 
		ts.switchColumnPrimaryKey(_DATABASEPATH, _DATABASENAME, _TABLENAME, true, column_1.getName());
		 
		ts.switchColumnUnique(_DATABASEPATH, _DATABASENAME, _TABLENAME, false, column_2.getName());
		 
		ts.switchColumnAutoIncrement(_DATABASEPATH, _DATABASENAME, _TABLENAME, true, column_1.getName());
		 
		ts.switchColumnNotNull(_DATABASEPATH, _DATABASENAME, _TABLENAME, true, column_3.getName());*/
		

		// _____________________________________________________________________________________

		/* Data */

		DataServiceImp ds = new DataServiceImp();
		
		ColumnValue value_1 = new ColumnValue("name", new String("Isy P"));
		ColumnValue value_2 = new ColumnValue("age", 44);

		ColumnValue value_3 = new ColumnValue(column_2 .getName(), "Rami J");
		ColumnValue value_4 = new ColumnValue(column_3.getName(), 24);

		//ds.insertData(_DATABASEPATH, _DATABASENAME, _TABLENAME, value_1, value_2);
		
		//ds.insertData(_DATABASEPATH, _DATABASENAME, _TABLENAME, value_3, value_4);

		//ds.printTable(_DATABASEPATH, _DATABASENAME, _TABLENAME);

		Condition whereCondition = new AndCondition(value_3, value_4);

		ColumnValue newName = new ColumnValue("name", "Rami Jaballi");
		ColumnValue age = new ColumnValue("age", 24);

		//ds.updateData(_DATABASEPATH, _DATABASENAME, _TABLENAME, whereCondition, newName);

		//ds.printTable(_DATABASEPATH, _DATABASENAME, _TABLENAME);
		
		Condition orderCondition = new AndCondition(age);

		Condition noCondition = null;
		
		Sorter sorter = new Sorter(noCondition, orderCondition, "ASC");
		
		//ds.selectData(_DATABASEPATH, _DATABASENAME, _TABLENAME, noCondition, sorter, "name","age");

		//ds.deleteData(_DATABASEPATH, _DATABASENAME, _TABLENAME, orderCondition);

		//ds.printTable(_DATABASEPATH, _DATABASENAME, _TABLENAME);
		
		System.out.println(ds.count(_DATABASEPATH, _DATABASENAME, _TABLENAME, null, null, "age", false));
	}

}
