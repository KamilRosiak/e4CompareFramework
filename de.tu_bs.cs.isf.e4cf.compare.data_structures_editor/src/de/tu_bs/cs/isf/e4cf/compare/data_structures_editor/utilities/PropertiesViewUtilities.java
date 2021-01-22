package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.utilities;

import java.util.Optional;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.NodeImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Utility Class for PropertyView
 * 
 * @author Team05
 *
 */
public class PropertiesViewUtilities {

	/**
	 * 
	 * @param node
	 * @param tableView
	 * @return
	 */
	public static TableView<Attribute> getAttributeTable(NodeImpl node, TableView<Attribute> tableView) {

		ObservableList<Attribute> data = FXCollections.observableArrayList(node.getAttributes());

		TableColumn<Attribute, String> property = new TableColumn<Attribute, String>("Property");
		property.setCellValueFactory(new PropertyValueFactory<>("attributeKey"));

		TableColumn<Attribute, String> value = new TableColumn<Attribute, String>("Value");
		value.setCellValueFactory(new PropertyValueFactory<>("attributeValues"));

		tableView.setItems(data);
		tableView.getColumns().addAll(property, value);
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		return tableView;
	}

	public static String getInput(String displayedDialog) {
		TextInputDialog td = new TextInputDialog();
		td.setHeaderText(displayedDialog);
		td.setGraphic(null);
		td.setTitle("Dialog");
		td.showAndWait();
		String s = td.getEditor().getText();
		if (s.equals("") || s.equals(null)) {
			alert("Bitte einen Wert eingeben");
		}
		return s;
	}

	public static void alert(String outputText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(outputText);
		alert.setTitle("Fehler");
		alert.showAndWait();
	}
	
	public static boolean confirmationAlert(String outputText) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setContentText(outputText);
		alert.setTitle("Confirmation required");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			return true;
		}
		return false;
	}
}
