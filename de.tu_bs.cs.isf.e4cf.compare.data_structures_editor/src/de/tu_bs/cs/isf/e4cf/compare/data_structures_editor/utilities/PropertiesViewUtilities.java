package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.utilities;

import java.util.Optional;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
	public static TableView<Attribute> getAttributeTable(Node node, TableView<Attribute> tableView) {
		ObservableList<Attribute> data = FXCollections.observableArrayList(node.getAttributes());

		TableColumn<Attribute, String> property = new TableColumn<Attribute, String>("Property");
		property.setCellValueFactory(new PropertyValueFactory<>("attributeKey"));

		TableColumn<Attribute, String> value = new TableColumn<Attribute, String>("Value");
		value.setCellValueFactory(new PropertyValueFactory<>("attributeValues"));
		value.setCellValueFactory(e -> {
			String valueString = "";
			for(Value singleValue : e.getValue().getAttributeValues()) {
				if(singleValue instanceof StringValueImpl) {
					valueString+= singleValue.getValue()+" ";
				}
				
			}
			return new SimpleStringProperty(valueString);
		});
		
		tableView.setItems(data);
		tableView.getColumns().addAll(property, value);
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		return tableView;
	}

	/**
	 * @param displayedDialog
	 * @return the given input
	 */
	public static String getInput(String displayedDialog) {
		TextInputDialog td = new TextInputDialog();
		td.setHeaderText(displayedDialog);
		td.setGraphic(null);
		td.setTitle("Dialog");
		td.getDialogPane().lookupButton(ButtonType.CANCEL).addEventFilter(ActionEvent.ACTION,
				event -> td.getEditor().setText(null));
		Stage stage = (Stage) td.getDialogPane().getScene().getWindow();
		stage.setOnCloseRequest(e -> {
			e.consume();
			td.getEditor().setText(null);
			stage.close();
		});
		stage.setAlwaysOnTop(true);
		td.showAndWait();
		String s = td.getEditor().getText();
		if (s.equals("") || s.equals(null)) {
			if (confirmationAlert(DSEditorST.NO_INPUT_ALERT) == true) {
				return null;
			} else {
				getInput(displayedDialog);
			}
		}
		// Important because of overwriting returns in case of a recursion
		if (s.equals("") || s.equals(null)) {
			return null;
		} else {
			return s;
		}
	}

	/**
	 * @param outputText
	 * @return true if button ok is pressed, false if it isn't
	 */
	public static boolean confirmationAlert(String outputText) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setContentText(outputText);
		alert.setTitle(DSEditorST.CONFIRMATION_REQUIRED);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		}
		return false;
	}
}
