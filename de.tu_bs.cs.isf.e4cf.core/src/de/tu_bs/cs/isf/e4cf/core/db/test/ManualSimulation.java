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
import de.tu_bs.cs.isf.e4cf.core.db.model.HavingCondition;
import de.tu_bs.cs.isf.e4cf.core.db.model.LikeCondition;
import de.tu_bs.cs.isf.e4cf.core.db.model.OrCondition;
import de.tu_bs.cs.isf.e4cf.core.db.model.Sorting;

public class ManualSimulation {

	public static void main(String[] args) throws SQLException, IOException {

		final String _DATABASEPATH = "./testDatabases/";
		final String _DATABASENAME = "TEstDB";
		final String _TABLEENAME = "testTabelle";

		/* Database */

		 //DatabaseFactory.getInstance().createDatabase(_DATABASEPATH, _DATABASENAME);
		// DatabaseFactory.getInstance().renameDatabase(_DATABASEPATH, _DATABASENAME,
		// "newName" + _DATABASENAME);
		// DatabaseFactory.getInstance().renameDatabase(_DATABASEPATH, "newName" +
		// _DATABASENAME, _DATABASENAME);
		// DatabaseFactory.getInstance().moveDatabase(_DATABASENAME, _DATABASEPATH, "");
		// DatabaseFactory.getInstance().moveDatabase(_DATABASENAME, "", _DATABASEPATH);
		// DatabaseFactory.getInstance().deleteDatabase(_DATABASEPATH, _DATABASENAME);

		// _____________________________________________________________________________________

		/* Tables */

		TableServiceImp ts = new TableServiceImp();

		Column c1 = new Column("id", "integer", false, false, true, false);
		Column c2 = new Column("name", "varchar (60)", false, true, false, true);
		Column c3 = new Column("age", "integer");

		 //ts.createTable(_DATABASEPATH, _DATABASENAME, _TABLEENAME, c1, c2, c3);
		// System.out.println("Columns: "+ts.getColumnsTable(_DATABASEPATH,
		// _DATABASENAME, _TABLEENAME));

		// ts.deleteTable(_DATABASEPATH, _DATABASENAME, _TABLEENAME);
		// ts.renameTable(_DATABASEPATH, _DATABASENAME, _TABLEENAME, "newName" +
		// _TABLEENAME);
		// ts.renameTable(_DATABASEPATH, _DATABASENAME, "newName" + _TABLEENAME,
		// _TABLEENAME);

		// ts.renameColumn(_DATABASEPATH, _DATABASENAME, _TABLEENAME, "id", "id_table");
		// ts.renameColumn(_DATABASEPATH, _DATABASENAME, _TABLEENAME, "id_table", "id");

		// ts.deleteColumn(_DATABASEPATH, _DATABASENAME, _TABLEENAME, "id");

		// ts.addColumn(_DATABASEPATH, _DATABASENAME, _TABLEENAME, c1, c2);

		//ts.makeColumnPrimaryKey(_DATABASEPATH, _DATABASENAME, _TABLEENAME, c1.getName());
		//ts.dropColumnPrimaryKey(_DATABASEPATH, _DATABASENAME, _TABLEENAME, c1.getName(), c2.getName());

		//ts.makeColumnUnique(_DATABASEPATH, _DATABASENAME, _TABLEENAME, c1.getName());
		//ts.dropColumnUnique(_DATABASEPATH, _DATABASENAME, _TABLEENAME, c1.getName());

		//ts.dropColumnNotNull(_DATABASEPATH, _DATABASENAME, _TABLEENAME, c1.getName());

		 //ts.makeColumnAutoIncrement(_DATABASEPATH, _DATABASENAME, _TABLEENAME, c1.getName());

		// _____________________________________________________________________________________

		/* Data */

		DataServiceImp ds = new DataServiceImp();

		//ColumnValue cv = new ColumnValue(c1.getName(), new Integer(15));
		ColumnValue cv1 = new ColumnValue(c2.getName(), new String("Isy P"));
		ColumnValue cv2 = new ColumnValue(c3.getName(), 44);

		//ColumnValue cvv = new ColumnValue(c1.getName(), new Integer(1111));
		ColumnValue cv3 = new ColumnValue(c2.getName(), "Mohamedd ali");
		ColumnValue cv4 = new ColumnValue(c3.getName(), 48);
		//ColumnValue cv5 = new ColumnValue(c2.getName(), "Mohamedd ali");

		 //ds.insertData(_DATABASEPATH, _DATABASENAME, _TABLEENAME, cv1, cv2);
		// ds.insertData(_DATABASEPATH, _DATABASENAME, _TABLEENAME, cv3, cv4);
		 
		// ds.insertData(_DATABASEPATH, _DATABASENAME, _TABLEENAME, cv1, cv4);
		// ds.insertData(_DATABASEPATH, _DATABASENAME, _TABLEENAME, cv3, cv2);
		 //ds.printTable(_DATABASEPATH, _DATABASENAME, _TABLEENAME);

		Condition cd = new AndCondition(cv4);

		ColumnValue cvn = new ColumnValue(c2.getName(), "Dalii");
		ColumnValue cvn2 = new ColumnValue(c3.getName(), 25);

		 //ds.updateData(_DATABASEPATH, _DATABASENAME, _TABLEENAME, cd, cvn2);

		//ds.printTable(_DATABASEPATH, _DATABASENAME, _TABLEENAME);
		
		Condition orderCondition = new AndCondition(cvn2);

		Sorting sort = new Sorting(null, orderCondition, "ASC");

		 //ds.deleteData(_DATABASEPATH, _DATABASENAME, _TABLEENAME, cd);
		 
		 //ds.printTable(_DATABASEPATH, _DATABASENAME, _TABLEENAME);

		 //ds.selectData(_DATABASEPATH, _DATABASENAME, _TABLEENAME, null, sort, null);
		
		//System.out.println("Test count: "+ds.count(_DATABASEPATH, _DATABASENAME, _TABLEENAME, cd, null, "age", true));
		
		//System.out.println("Test sum:" +ds.sum(_DATABASEPATH, _DATABASENAME, _TABLEENAME, cd, null, "id", false));


	}

}
