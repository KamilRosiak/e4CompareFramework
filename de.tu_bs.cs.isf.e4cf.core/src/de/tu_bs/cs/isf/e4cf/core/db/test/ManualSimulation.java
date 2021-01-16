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
		final String _DATABASENAME = "TestDatenbank";
		final String _TABLEENAME   = "testTabelle";
		
		
		/* Database */
		
		//DatabaseFactory.getInstance().createDatabase(_DATABASEPATH, _DATABASENAME);
		//DatabaseFactory.getInstance().deleteDatabase("", _DATABASENAME);
		//DatabaseFactory.getInstance().renameDatabase(_DATABASEPATH, _DATABASENAME, "newName"+_DATABASENAME);

		//_____________________________________________________________________________________
		
		/* Tables */
		
		TableServiceImp ts = new TableServiceImp();
		
		Column c1 = new Column("id", "integer", false, false, false, false);
		Column c2 = new Column("name", "varchar (60)", false, false, false, true);
		Column c3 = new Column("age", "integer");
		
		//ts.createTable(_DATABASEPATH, _DATABASENAME, _TABLEENAME, c1, c2, c3);	
		//System.out.println("Columns: "+ts.getColumnsTable(_DATABASEPATH, _DATABASENAME, _TABLEENAME));
		//ts.deleteTable(_DATABASEPATH, _DATABASENAME, _TABLEENAME);
		
		//ts.makeColumnPrimaryKey(_DATABASEPATH, _DATABASENAME, _TABLEENAME, c1.getName());
		
		//ts.dropColumnUnique(_DATABASEPATH, _DATABASENAME, _TABLEENAME, c2.getName());
		
		//ts.makeColumnAutoIncrement(_DATABASEPATH, _DATABASENAME, _TABLEENAME, c1.getName());
		
		//_____________________________________________________________________________________
		
		/* Data */
		
		DataServiceImp ds = new DataServiceImp();
		
		ColumnValue cv = new ColumnValue(c1.getName(), new Integer(15));
		ColumnValue cv1 = new ColumnValue(c2.getName(), new String("Mohamedd ali"));
		ColumnValue cv2 = new ColumnValue(c3.getName(), 44);
		
		//ds.insertData(_DATABASEPATH, _DATABASENAME, _TABLEENAME, cv1, cv2);
		
		ColumnValue cvv = new ColumnValue(c1.getName(), new Integer(1111));
		ColumnValue cv3 = new ColumnValue(c2.getName(), "Rami ");
		ColumnValue cv4 = new ColumnValue(c3.getName(), 48);
		
		//ds.insertData(_DATABASEPATH, _DATABASENAME, _TABLEENAME, cv3, cv4);
		//ds.printTable(_DATABASEPATH, _DATABASENAME, _TABLEENAME);
		
		Condition cd = new AndCondition(cv4);
		
		ColumnValue cvn = new ColumnValue(c2.getName(), "John Q");
		ColumnValue cvn2 = new ColumnValue(c3.getName(), 20);
		
		//ds.updateData(_DATABASEPATH, _DATABASENAME, _TABLEENAME, cd, cvn);
		
		Condition groupCondition = new HavingCondition(cvn);
		
		Sorting sort = new Sorting(groupCondition, null, "ASC");
		
		//ds.deleteData(_DATABASEPATH, _DATABASENAME, _TABLEENAME, dc);
		
		ds.selectData(_DATABASEPATH, _DATABASENAME, _TABLEENAME, null, sort, null);
		

	}

}
