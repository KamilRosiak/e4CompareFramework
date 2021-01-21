package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractAttribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.utilities.PropertiesViewUtilities;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
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
	private ContextMenu contextMenu;

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
	public void showProperties(@UIEventTopic("nodePropertiesEvent") NodeImpl node) {
		propertiesTable.getColumns().clear();
		propertiesTable = PropertiesViewUtilities.getAttributeTable(node, propertiesTable);
		propertiesTable.setOnMouseEntered(e -> contextMenu.hide());
	}

	@FXML
	public void editNodeAttribute() {
		String s = PropertiesViewUtilities.getInput("Please enter new property designation");
		AbstractAttribute attr = (AbstractAttribute) getSelectedItem();
		attr.setAttributeKey(s);
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
		services.eventBroker.send("DeleteAttributeEvent", getSelectedItem().getAttributeKey().toString());
		refreshGUI();
	}

	@FXML
	public void AddNodeValue() {
		getSelectedItem().getAttributeValues().add(PropertiesViewUtilities.getInput("Please enter another Value"));
		refreshGUI();
	}

	private void refreshGUI() {
		services.eventBroker.send("RefreshTreeViewEvent", true);
		propertiesTable.refresh();
	}

	private Attribute getSelectedItem() {
		return propertiesTable.getSelectionModel().getSelectedItem();
	}
}
