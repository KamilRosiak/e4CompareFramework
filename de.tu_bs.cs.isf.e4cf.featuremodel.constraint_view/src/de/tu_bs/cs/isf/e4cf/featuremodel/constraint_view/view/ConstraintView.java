package de.tu_bs.cs.isf.e4cf.featuremodel.constraint_view.view;

import java.util.List;

import CrossTreeConstraints.AbstractConstraint;
import de.tu_bs.cs.isf.e4cf.featuremodel.constraint_view.ConstraintViewController;
import de.tu_bs.cs.isf.e4cf.featuremodel.constraint_view.view.elements.ConstraintViewContextMenu;
import de.tu_bs.cs.isf.e4cf.featuremodel.constraint_view.view.elements.FXContraint;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.constraints.ConstraintEditor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
/**
 * This is the view part of the constraint view that show the constraint of the current feature model that is loaded in the featureDiagram Editor.
 * @author Kamil Rosiak
 *
 */
public class ConstraintView {
	private ConstraintViewController controller;
	private TableView<FXContraint> table;
	private Pane root;
	private Scene scene;
	
	public ConstraintView(ConstraintViewController controller, FXCanvas canvas) {
	    this.controller = controller;
	    initView(canvas);
	}

	/**
	 * Initialization of the constraint view
	 */
	private void initView(FXCanvas canvas) {
	    createTable();
            root = new StackPane();
            scene = new Scene(root);
            root.getChildren().add(table);
            canvas.setScene(scene);
	}
	
	/**
	 * This method creates the table with both columns.
	 */
	private void createTable() {
		this.table = new TableView<FXContraint>();
		
		TableColumn<FXContraint, String> constraintCol = new TableColumn<FXContraint, String>("Constraint");
		constraintCol.prefWidthProperty().bind(table.widthProperty().divide(4).multiply(3));
		constraintCol.setCellValueFactory(new PropertyValueFactory<>("constraintText"));
		
		TableColumn<FXContraint, String> descriptionCol = new TableColumn<FXContraint, String>("Description");
		descriptionCol.prefWidthProperty().bind(table.widthProperty().divide(4));
		descriptionCol.setCellValueFactory(new PropertyValueFactory<>("constraintDescription"));
		
		table.setOnMouseClicked(e -> {
			if(e.getClickCount() == 2 && e.getButton().equals(MouseButton.PRIMARY)) {
			ConstraintEditor editor = new ConstraintEditor();
			editor.editConstraint(table.getSelectionModel().getSelectedItem().getConstraint());
			}
		});
		
		table.setContextMenu(new ConstraintViewContextMenu(table, controller));
		table.getColumns().addAll(constraintCol, descriptionCol);
	}
	
	public void showConstraints(List<AbstractConstraint> constraints) {
		ObservableList<FXContraint> constraintList = FXCollections.observableArrayList();
		for(AbstractConstraint constraint : constraints) {
			constraintList.add(new FXContraint(constraint));
		}
		table.getItems().clear();
		table.getItems().addAll(constraintList);
	}
	
}
