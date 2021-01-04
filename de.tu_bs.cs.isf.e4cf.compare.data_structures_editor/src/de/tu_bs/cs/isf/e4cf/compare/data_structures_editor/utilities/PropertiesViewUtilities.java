package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.utilities;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.NodeUsage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
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
	public static TableView<Attribute> getAttributeTable(NodeUsage node, TableView<Attribute> tableView) {

		ObservableList<Attribute> data = FXCollections.observableArrayList(node.getAttributes());

		TableColumn<Attribute, String> property = new TableColumn<Attribute, String>("Property");
		property.setCellValueFactory(new PropertyValueFactory<>("attributeKey"));

		TableColumn<Attribute, String> value = new TableColumn<Attribute, String>("Value");
		value.setCellValueFactory(new PropertyValueFactory<>("attributeValues"));

		tableView.setItems(data);
		tableView.getColumns().addAll(property, value);
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		return tableView;
	}

//	  public static void handle(MouseEvent event, TableView<Attribute> tempTable) {  
//		  if (event.getClickCount()==2) {  
//			  
//	    }  
//	  }  
}
