package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.utilities.PropertiesViewUtilities;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

/**
 * 
 * @author Team05
 *
 */
public class PropertiesController {

	@FXML
	private TableView<Attribute> propertiesTable;

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

	/**
	 * 
	 * @param bool
	 */
	@Optional
	@Inject
	public void emptyPropertiesTable(@UIEventTopic("EmptyPropertiesTableEvent") boolean bool) {
		propertiesTable.getItems().clear();
	}

	/**
	 * 
	 * @param node
	 */
	@Optional
	@Inject
	public void showProperties(@UIEventTopic("nodePropertiesEvent") NodeUsage node) {
		propertiesTable.getColumns().clear();
		propertiesTable = PropertiesViewUtilities.getAttributeTable(node, propertiesTable);
	}
	
	

}