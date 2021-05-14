package de.tu_bs.cs.isf.e4cf.compare.metric_view.components;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;


//need to changed to TextFieldTreeTableCell
public class WeightCell extends TextFieldTreeTableCell<FXComparatorElement, Float> {

	public WeightCell() {
		super();
	}
	
	@Override
	public void updateItem(Float item, boolean empty) {
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

	public static class StringToFloatConverter extends StringConverter<Float> {

		@Override
		public String toString(Float object) {
			return String.valueOf(object);			
		}

		@Override
		public Float fromString(String string) {
//			getTreeTableView().getRoot().getChildren().forEach(child -> {
//				child.getChildren().forEach(child2 -> {
//					System.out.println(child2.getValue().getWeight());
//				});
//			});
			try {
				return Float.parseFloat(string);
				
			} catch(NumberFormatException e) {
				System.err.println("Float Format Error");
			}
			return null;
		}
	
	}
	
	public static Callback<TreeTableColumn<FXComparatorElement, Float>, TreeTableCell<FXComparatorElement, Float>> forTreeTableColumn() {
        return forTreeTableColumn(new StringToFloatConverter());
    }
}
