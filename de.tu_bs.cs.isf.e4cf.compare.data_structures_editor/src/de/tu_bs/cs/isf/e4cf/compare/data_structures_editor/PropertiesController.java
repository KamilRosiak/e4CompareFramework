package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PropertiesController {

	@FXML
	private TableView<NodeUsage> propertiesTable;
	
	@Inject
	ServiceContainer container;

	@FXML

	/**
	 * initializes propertiesView
	 */
	void initPropertiesView() {
		//propertiesTable = new TableView<NodeUsage>();
		System.out.println("properties");
		final ObservableList<NodeUsage> data = FXCollections.observableArrayList(
		         new NodeUsage("file1"),
		         new NodeUsage("file2"),
		         new NodeUsage("file4")
		      );
		TableColumn<NodeUsage,String> properties = new TableColumn<NodeUsage, String>("Properties");
		properties.setCellValueFactory(new PropertyValueFactory<>("nodeType"));
		TableColumn<NodeUsage,String> value = new TableColumn<NodeUsage, String>("Value");
		value.setCellValueFactory(new PropertyValueFactory<>("attributeKey"));	
		propertiesTable.setItems(data);
		propertiesTable.getColumns().addAll(properties,value);
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
		System.out.println(node);
		initPropertiesView();
//		createRow(node);
	}
}