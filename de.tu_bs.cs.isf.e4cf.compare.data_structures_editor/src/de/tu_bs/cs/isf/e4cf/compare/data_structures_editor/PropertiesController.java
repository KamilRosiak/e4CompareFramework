package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractAttribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DataStructuresEditorST;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.utilities.PropertiesViewUtilities;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.utilities.TreeViewUtilities;
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
	public void emptyPropertiesTable(@UIEventTopic(DataStructuresEditorST.EMPTY_PROPERTIES_TABLE_EVENT) boolean bool) {
		propertiesTable.getItems().clear();
	}

	/**
	 * 
	 * @param node to be shown in the properties table
	 */
	@Optional
	@Inject
	public void showProperties(@UIEventTopic(DataStructuresEditorST.NODE_PROPERTIES_EVENT) NodeImpl node) {
		propertiesTable.getColumns().clear();
		propertiesTable = PropertiesViewUtilities.getAttributeTable(node, propertiesTable);
		propertiesTable.setOnMouseEntered(e -> contextMenu.hide());
	}
	
	private void refreshGUI() {
		services.eventBroker.send(DataStructuresEditorST.REFRESH_TREEVIEW_EVENT, true);
		services.eventBroker.send(DataStructuresEditorST.REOPEN_ITEM_EVENT, true);
	}

	private Attribute getSelectedItem() {
		return propertiesTable.getSelectionModel().getSelectedItem();
	}

	@FXML
	public void editNodeAttribute() {
		String s = PropertiesViewUtilities.getInput("Please enter new property designation");
		if (s != null) {
			AbstractAttribute attr = (AbstractAttribute) getSelectedItem();
			attr.setAttributeKey(s);
			refreshGUI();
		}
	}

	@FXML
	public void editNodeValue() {
		String s = PropertiesViewUtilities.getInput("Please enter new Value");
		if(s != null) {
			getSelectedItem().getAttributeValues().clear();
			getSelectedItem().getAttributeValues().add(s);
			refreshGUI();
		}
	}

	@FXML
	public void removeNodeAttribute() {
		if (TreeViewUtilities.confirmationAlert("Are you sure you want to do this?") == true) {
			services.eventBroker.send(DataStructuresEditorST.DELETE_ATTRIBUTE_EVENT, getSelectedItem().getAttributeKey().toString());
			refreshGUI();
		}
		
	}

	@FXML
	public void AddNodeValue() {
		String s = PropertiesViewUtilities.getInput("Please enter another Value");
		if(s != null) {
			getSelectedItem().getAttributeValues().add(s);
			refreshGUI();
		}
	}

	
}
