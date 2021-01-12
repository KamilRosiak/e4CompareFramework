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
}
