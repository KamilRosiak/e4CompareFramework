package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractAttribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.CommandManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions.DeleteAttributeAction;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions.ModifyValuesAction;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions.NodeAttributePair;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions.RenamePropertyAction;
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

	private Node selectedNode;

	CommandManager propertiesManager = new CommandManager();

	/**
	 * empties the properties table
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

	/**
	 * refreshes views
	 */
	private void refreshGUI() {
		services.eventBroker.send(DataStructuresEditorST.REFRESH_TREEVIEW_EVENT, true);
		services.eventBroker.send(DataStructuresEditorST.REOPEN_ITEM_EVENT, true);
	}

	/**
	 * @return the currently selected item
	 */
	private Attribute getSelectedItem() {
		return propertiesTable.getSelectionModel().getSelectedItem();
	}

	/**
	 * Method to edit the name of a property
	 */
	@FXML
	public void editNodeProperty() {
		String s = PropertiesViewUtilities.getInput("Please enter new property designation");
		String oldName = "";
		if (s != null) {
			AbstractAttribute attr = (AbstractAttribute) getSelectedItem();
			oldName = getSelectedItem().getAttributeKey();
			attr.setAttributeKey(s);
			propertiesManager.execute(
					new RenamePropertyAction("renameProperty", oldName, (AbstractAttribute) getSelectedItem()));
			refreshGUI();
		}
	}

	/**
	 * edits the selected Nodes Value
	 */
	@FXML
	public void editNodeValue() {
		Set<String> oldAttributeValues = getCurrentAttributeValues();
		String newValue = PropertiesViewUtilities.getInput("Please enter new Value");
		Set<String> newAttributeValues;

		if (newValue != null) {
			getSelectedItem().getAttributeValues().clear();
			getSelectedItem().getAttributeValues().add(newValue);
			newAttributeValues = getSelectedItem().getAttributeValues();
			propertiesManager.execute(new ModifyValuesAction("Modify Values", oldAttributeValues, newAttributeValues));
			refreshGUI();
		}
	}

	/**
	 * removes the selected attribute
	 */
	@FXML
	public void removeNodeAttribute() {
		if (TreeViewUtilities.confirmationAlert("Are you sure you want to do this?") == true) {
			Attribute deletedAttribute = getSelectedItem();
			services.eventBroker.send(DataStructuresEditorST.DELETE_ATTRIBUTE_EVENT, getSelectedItem());
			services.eventBroker.send(DataStructuresEditorST.ASK_FOR_SELECTED_ITEM_EVENT, true);
			NodeAttributePair pair = new NodeAttributePair(selectedNode, deletedAttribute);
			propertiesManager.execute(new DeleteAttributeAction("removeAction", pair, services));
			refreshGUI();
		}

	}

	/**
	 * adds a new Value to the selected attribute
	 */
	@FXML
	public void addNodeValue() {
		Set<String> oldAttributeValues = getCurrentAttributeValues();
		Set<String> newAttributeValues;
		String s = PropertiesViewUtilities.getInput("Please enter another Value");
		if (s != null) {
			getSelectedItem().getAttributeValues().add(s);
			newAttributeValues = getSelectedItem().getAttributeValues();
			propertiesManager.execute(new ModifyValuesAction("Modify Values", oldAttributeValues, newAttributeValues));
			refreshGUI();
		}
	}

	/**
	 * undoes the last action made in the properties view
	 */
	@FXML
	void undoProperties() {
		propertiesManager.undo();
		refreshGUI();
	}

	/**
	 * @return the current attributes values before changes are made
	 */
	Set<String> getCurrentAttributeValues() {
		Set<String> oldAttributeValues = new HashSet<String>();
		// save the old attribute values
		for (String value : getSelectedItem().getAttributeValues()) {
			oldAttributeValues.add(value);
		}
		return oldAttributeValues;
	}

	/**
	 * Event to receive the node which is currently selected in the treeView
	 * 
	 * @param node
	 */
	@Optional
	@Inject
	public void receiveSelectedNode(@UIEventTopic(DataStructuresEditorST.RECEIVE_SELECTED_NODE_EVENT) Node node) {
		selectedNode = node;
	}

}
