package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

public class TreeViewController {

	@Inject
	private ServiceContainer services;
	@FXML
	private MenuItem properties;
	@FXML
	private VBox background;
	@FXML
	private Button search;
	@FXML
	private Label testLabel;
	@FXML
	private TreeView<String> hirarchy;
	@FXML
	private TextField searchTextField;
	private TreeItem<String> rootItem;

	void openProperties() {
		services.partService.showPart("de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.part.properties_view");
	}

	/**
	 * A method to close a file
	 */
	@FXML
	void closeFile() {
		// set treeview and its values to null, then remove it from the background
		hirarchy.setRoot(null);
	}

	/**
	 * Selects all elements in the treeview
	 */
	@FXML
	void selectAll() {
		hirarchy.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		hirarchy.getSelectionModel().selectAll();
	}

	/**
	 * Unselects all elements in the treeview
	 */
	@FXML
	void unselectAll() {
		hirarchy.getSelectionModel().clearSelection();
	}

	@FXML
	void searchField() {
		// System.out.println(searchTextField.getText());
		String searchFieldTextToRead = searchTextField.getText();
		// TDOD: Field must compare to treeItem
	}

	/**
	 * searchButton to search what is typed in searchField
	 */
	@FXML
	void searchButton() {
		searchField();
	}

	void createTree(Tree tr) {
//		Datei wird eingelesen und als TreeView ausgegeben
		rootItem = new TreeItem<String>(tr.getTreeName());
		rootItem.setExpanded(true);
		for (Node node : tr.getLeaves()) {
			TreeItem<String> item = new TreeItem<String>(node.toString());
			rootItem.getChildren().add(item);
			System.out.println(node.toString());
		}
		hirarchy.setRoot(rootItem);
		hirarchy.setShowRoot(true);

		hirarchy.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			openProperties();
			services.eventBroker.send("nodePropertiesEvent", hirarchy.getSelectionModel().getSelectedItem().getValue());
		});
		System.out.println(rootItem.getValue());
	}

	@Optional
	@Inject
	public void openTree(@UIEventTopic("OpenTreeEvent") Tree tree) {
		createTree(tree);
		System.out.println(tree.getTreeName());
	}
}
