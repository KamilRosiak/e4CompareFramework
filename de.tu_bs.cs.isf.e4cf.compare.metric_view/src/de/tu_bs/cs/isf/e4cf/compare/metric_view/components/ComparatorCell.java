package de.tu_bs.cs.isf.e4cf.compare.metric_view.components;

import javax.inject.Inject;

import de.tu_bs.cs.isf.e4cf.compare.metric_view.stringtable.MetricST;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableRow;
import javafx.scene.input.MouseButton;

public class ComparatorCell extends TreeTableCell<FXComparatorElement, String> {
	
	 @Override
	    protected void updateItem(String item, boolean empty) {
	        super.updateItem(item, empty);

	        if (item == null || empty) {
	            setText(null);
	        } else {
	            setText(item);
	        }
	    }
	
	private ServiceContainer serviceContainer;
	
	public ComparatorCell(ServiceContainer container) {
		super();
		this.serviceContainer = container;
		
		setOnMouseClicked(event -> {
			if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
	            TreeTableRow<FXComparatorElement> row = getTreeTableRow();
	            TreeItem<FXComparatorElement> item = row.getTreeItem();
	            if (!row.getTreeTableView().getRoot().getChildren().contains(item)) {
		            serviceContainer.partService.showPart(MetricST.BUNDLE_NAME);
		            ObservableList<FXComparatorElement> temp = FXCollections.observableArrayList();
		            temp.add(item.getValue());
		            serviceContainer.eventBroker.send("comparatorListEvent", temp);
	            }
			}
        });
		
	}

}
