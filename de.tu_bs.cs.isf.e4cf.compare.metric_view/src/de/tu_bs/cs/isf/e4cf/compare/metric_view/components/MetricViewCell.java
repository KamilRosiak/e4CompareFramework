package de.tu_bs.cs.isf.e4cf.compare.metric_view.components;

import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;

public class MetricViewCell extends TreeTableCell<FXComparatorElement, String> {
	
    ObservableList<Map<String, String>> ignoredTypes = FXCollections.<Map<String, String>>observableArrayList();;

	@Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setText(null);
        } else if (isIgnored(getType(item))) {
            setDisable(true);
            setText(item);
        } else {
            setDisable(false);
        	setText(item);
        }
    }
	
	public MetricViewCell(ObservableList<Map<String, String>> comparatorTypes) {
		super();
        ignoredTypes = comparatorTypes;
	}
	
	private boolean isIgnored(String type) {
		for (Map<String, String> typeMap : ignoredTypes) {
			if (Boolean.parseBoolean(typeMap.get("ignored")) && typeMap.get("type").equals(type)) {
				return true;
			}
		}
		return false;
		
	}

    private String getType(String item) {
        for (TreeItem<FXComparatorElement> type : getTreeTableView().getRoot().getChildren()) {
            String currentType = type.getValue().getName();
            if (currentType.equals(item)) {
                return currentType;
            }
        }
        return "";
    }
	
}
