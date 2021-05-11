package de.tu_bs.cs.isf.e4cf.compare.metric_view.components;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;


//need to changed to TextFieldTreeTableCell
public class WeightCell extends TreeTableCell<FXComparatorElement, Float> {

	public WeightCell() {
		super();
	}
	
	@Override
	protected void updateItem(Float item, boolean empty) {
        super.updateItem(item, empty);
        TreeTableView<FXComparatorElement> table = getTreeTableView();
        TreeTableRow<FXComparatorElement> row = getTreeTableRow();
        FXComparatorElement fx = row.getItem();
        if (fx != null) {
	        if (getTreeTableRow().getItem().getComparator() == null && getTreeTableRow().getItem().getName() != null) {
	        	setEditable(false);
	        } else {
	        	setEditable(true);
	        }
	        setText(String.valueOf(item));
		}
	}
	
}
