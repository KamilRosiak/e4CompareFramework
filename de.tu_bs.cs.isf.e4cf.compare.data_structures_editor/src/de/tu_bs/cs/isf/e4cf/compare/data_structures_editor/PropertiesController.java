package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PropertiesController {

	@FXML
	private TableView<Attribute> propertiesTable;

	/**
	 * 
	 * @param node
	 */
	void initPropertiesView(NodeUsage node) {
//		System.out.println("properties");
		ObservableList<Attribute> data = FXCollections.observableArrayList(node.getAttributes());

		TableColumn<Attribute, String> property = new TableColumn<Attribute, String>("Property");
		property.setCellValueFactory(new PropertyValueFactory<>("attributeKey"));

		TableColumn<Attribute, String> value = new TableColumn<Attribute, String>("Value");
		value.setCellValueFactory(new PropertyValueFactory<>("attributeValues"));

		propertiesTable.setItems(data);
		propertiesTable.getColumns().addAll(property, value);
		propertiesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

//	ObservableList<NodeUsage> createRow() {
//		ObservableList<NodeUsage> nodes = FXCollections.observableArrayList();
//		nodes.add(new NodeUsage("text"));
//		return nodes;
//	}

//	ObservableList<NodeUsage> createRow(NodeUsage node) {
//		ObservableList<NodeUsage> nodes = FXCollections.observableArrayList();
//		nodes.add(node);
//		return nodes;
//	}

	@Optional
	@Inject
	public void showProperties(@UIEventTopic("nodePropertiesEvent") NodeUsage node) {
		propertiesTable.getColumns().clear();
		initPropertiesView(node);
	}
}