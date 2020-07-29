package de.tu_bs.cs.isf.e4cf.replay_view.util;

import java.util.Arrays;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class TableUtil {
	
	public static void moveSelectedItemsUp(Table table) {
		// check if all items are movable upwards
		int[] selectedIndices = table.getSelectionIndices();
		for (int index : selectedIndices) {
			if (index <= 0) {
				return;
			}	
		}
		
		// sort array in ascending (!) order and move items one by one
		Arrays.sort(selectedIndices);
		for (int i = 0; i < selectedIndices.length; i++) {
			moveItemUp(selectedIndices[i], table);
		}
	}
	
	public static void moveItemUp(int index, Table table) {
		if (0 <= index && index < table.getItemCount()) {
			if (index > 0) {
				int targetIndex = index-1;
				TableItem selectedItem = table.getItem(index);
				insertClone(table, selectedItem, targetIndex);
				table.remove(index+1);
				table.select(targetIndex);
				table.redraw();
				table.update();
			}
		}
		
	}
	
	public static void moveSelectedItemsDown(Table table) {
		// check if all items are movable upwards
		int[] selectedIndices = table.getSelectionIndices();
		for (int index : selectedIndices) {
			if (index >= table.getItemCount()-1) {
				return;
			}	
		}
		
		// sort array in ascending (!) order and move items one by one
		Arrays.sort(selectedIndices);
		for (int i = selectedIndices.length-1; i >= 0; i--) {
			moveItemDown(selectedIndices[i], table);
		}
	}
	
	public static void moveItemDown(int index, Table table) {
		if (0 <= index && index < table.getItemCount()) { 
			if (index < table.getItemCount()-1) {
				int targetIndex = index+2;
				TableItem selectedItem = table.getItem(index);
				insertClone(table, selectedItem, targetIndex);
				table.remove(index);
				table.select(targetIndex-1);
				table.redraw();
				table.update();
			}
		}
		
	}
	
	/**
	 * Inserts a cloned table item in a table. 
	 * 
	 * @param parent table widget
	 * @param item template table item 
	 * @param index insertion index
	 */
	public static void insertClone(Table parent, TableItem item, int index) {
		Image image = null;
		Object o = parent.getData(ReplayViewStringTable.TABLE_ITEM_APPLY_IMAGE_KEY);
		if (o != null && o instanceof Image) {
			image = (Image) o;
		}
//		TableItem clone = ReplayView.createTableItem(parent, (String)item.getData(ReplayViewStringTable.TABLE_ITEM_MODIFICATION_KEY), image, index);
//		clone.setText(item.getText());
//		clone.setChecked(item.getChecked());
	}
}
