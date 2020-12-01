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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PropertiesController {

	@FXML
	private TableView<Node> propertiesTable;

	@FXML
	private TableColumn<?, ?> attribute;

	@FXML
	private TableColumn<?, ?> value;

	@Inject
	ServiceContainer container;

	@FXML
	void initialize() {
		System.out.println("initialize propertiesView");
		attribute = new TableColumn<Node, String>("nodeType");
		attribute.setCellValueFactory(new PropertyValueFactory("nodeType"));
		value = new TableColumn<Node, String>("attributeKey");
		propertiesTable = new TableView<Node>();
		propertiesTable.setItems(createRow());
//		propertiesTable.getColumns().addAll(attribute);

	}

	ObservableList<Node> createRow() {
		System.out.println("Erstelle Row");
		ObservableList<Node> nodes = FXCollections.observableArrayList();
		nodes.add(new NodeImpl("text"));
		return nodes;
	}

	@Optional
	@Inject
	public void showProperties(@UIEventTopic("nodePropertiesEvent") Object object) {
		System.out.println(object);
	}
}