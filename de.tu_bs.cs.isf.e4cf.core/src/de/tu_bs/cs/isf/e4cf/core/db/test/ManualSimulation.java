package de.tu_bs.cs.isf.e4cf.core.db.test;

import static org.junit.Assert.assertTrue;

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

		final String DATABASEPATH = "./testDatabases/";
		final String DATABASENAME = "TestDatenbank";
		final String TABLEENAME = "testTabelle";

		Column c1 = new Column("id", "integer", false, false, true, false);
		Column c2 = new Column("name", "varchar (60)");
		Column c3 = new Column("age", "integer");

		TableServiceImp c = new TableServiceImp();

		DataServiceImp ds = new DataServiceImp();

		// DatabaseFactory.getInstance().createDatabase(DATABASEPATH, DATABASENAME);
		// DatabaseFactory.getInstance().deleteDatabase(DATABASEPATH, DATABASENAME);
		// c.createTable(DATABASEPATH, DATABASENAME, TABLEENAME, c1, c2, c3);

		ColumnValue cv = new ColumnValue(c2.getName(), new String("Xong2"));
		ColumnValue cv2 = new ColumnValue(c2.getName(), new String("souma"));
		ColumnValue cv3 = new ColumnValue(c3.getName(), "60");
		// Condition cd = new OrCondition(cv,cv2);
		//Condition cd = new LikeCondition(cv,cv3);
		Condition ad = new AndCondition(cv3,cv2);
		//System.out.println("Test Condition: "+ad.getSqlCondition());
		// c.makeColumnAutoIncrement(DATABASEPATH, DATABASENAME, TABLEENAME, "id");
		 ds.insertData(DATABASEPATH, DATABASENAME, TABLEENAME, cv3,cv2);
		// ds.insertData(DATABASEPATH, DATABASENAME, TABLEENAME, cv3,cv3);
		// ds.insertData(DATABASEPATH, DATABASENAME, TABLEENAME, new
		// ColumnValue(c3.getName(), new Integer(24)),cv2);
		 //ds.updateData(DATABASEPATH, DATABASENAME, TABLEENAME, ad, new ColumnValue(c3.getName(), "100"));
		 //ds.deleteData(DATABASEPATH, DATABASENAME, TABLEENAME, ad);
		Sorting s = new Sorting(null, new Condition(new ColumnValue(c3.getName(), null)), null, null);

		ds.selectData(DATABASEPATH, DATABASENAME, TABLEENAME, null, s, null);

	}

}
