package de.tu_bs.cs.isf.e4cf.compare.metric_view.components;


import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.StringConverter;


public class WeightCell extends TextFieldTreeTableCell<FXComparatorElement, Float> {

	public WeightCell() {
		super();
		setConverter(new StringToFloatConverter());
	}
	
	@Override
	public void updateItem(Float item, boolean empty) {
        super.updateItem(item, empty);
		if (empty || item == null) {
			setText(null);
			setGraphic(null);
			return;
		}

        FXComparatorElement fx = getTreeTableRow().getItem();
        if (fx != null) {
        	//typeNode
	        if (fx.getComparator() == null && fx.getName() != null) {
	        	setEditable(false);
	        //leafNode
	        } else {
	        	setEditable(true);
	        }   
		}
		if (item > 100f) {
			setTextFill(Color.RED);
		}
		setText(String.format("%.2f", item));
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
			return 0f;
		}
	
	}
	
	public static Callback<TreeTableColumn<FXComparatorElement, Float>, TreeTableCell<FXComparatorElement, Float>> forTreeTableColumn() {
        return new Callback<TreeTableColumn<FXComparatorElement, Float>, TreeTableCell<FXComparatorElement, Float>>() {
			@Override
			public TreeTableCell<FXComparatorElement, Float> call(TreeTableColumn<FXComparatorElement, Float> param) {
				
				return new WeightCell();
			}
		};
    }
}
