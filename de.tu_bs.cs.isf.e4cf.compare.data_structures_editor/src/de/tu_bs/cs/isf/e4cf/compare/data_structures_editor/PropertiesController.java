package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.utilities.PropertiesViewUtilities;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

/**
 * 
 * @author Team05
 *
 */
public class PropertiesController {

	@Inject
	private ServiceContainer services;

	@FXML
	private TableView<Attribute> propertiesTable;

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
	 * @param node to be shown in the properties table
	 */
	@Optional
	@Inject
	public void showProperties(@UIEventTopic("nodePropertiesEvent") NodeUsage node) {
		propertiesTable.getColumns().clear();
		propertiesTable = PropertiesViewUtilities.getAttributeTable(node, propertiesTable);
	}

	@FXML
	public void editNodeAttribute() {
		String s = PropertiesViewUtilities.getInput("Please enter new Attribute Name");
		refreshGUI();
	}

	@FXML
	public void editNodeValue() {
		String s = PropertiesViewUtilities.getInput("Please enter new Value");
		getSelectedItem().getAttributeValues().clear();
		getSelectedItem().getAttributeValues().add(s);
		refreshGUI();
	}

	@FXML
	public void removeNodeAttribute() {
	}

	@FXML
	public void AddNodeValue() {
		String s = PropertiesViewUtilities.getInput("Please enter another Value");
		getSelectedItem().getAttributeValues().add(s);
		refreshGUI();
	}

	private void refreshGUI() {
		propertiesTable.refresh();
		services.eventBroker.send("RefreshTreeViewEvent", true);
	}

	private Attribute getSelectedItem() {
		return propertiesTable.getSelectionModel().getSelectedItem();
	}
}
