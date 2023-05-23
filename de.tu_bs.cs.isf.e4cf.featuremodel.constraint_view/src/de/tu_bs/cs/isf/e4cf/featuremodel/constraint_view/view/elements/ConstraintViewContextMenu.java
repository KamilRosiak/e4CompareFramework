package de.tu_bs.cs.isf.e4cf.featuremodel.constraint_view.view.elements;

import de.tu_bs.cs.isf.e4cf.featuremodel.constraint_view.ConstraintViewController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;

public class ConstraintViewContextMenu extends ContextMenu {
	private TableView<FXContraint> table;
	private ConstraintViewController controller;
	public ConstraintViewContextMenu(TableView<FXContraint> table,ConstraintViewController controller) {
		this.controller = controller;
		this.table = table;
		this.getItems().add(createRemoveContraintMenuItem());
	}
	
	private MenuItem createRemoveContraintMenuItem() {
		MenuItem item = new MenuItem("Remove Constraint");
		item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	controller.getCurrentFeatureDiagram().removeConstraint(table.getSelectionModel().getSelectedItem().getConstraint());
        		controller.updateView();
            	event.consume();
            }
        });

		return item;
	}
	
}
