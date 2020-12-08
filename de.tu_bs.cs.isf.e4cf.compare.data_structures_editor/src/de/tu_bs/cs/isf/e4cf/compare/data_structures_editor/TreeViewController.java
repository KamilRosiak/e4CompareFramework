package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
<<<<<<< HEAD
=======
import javafx.scene.control.TextField;
>>>>>>> dca6141e12c99bd71eabaa7e17329e32483abdeb
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
	private TreeView<NodeUsage> hirarchy;

	private TreeItem<NodeUsage> rootItem;

	@FXML
	private TextField searchTextField;

	void switchToPart(String path) {
		services.partService.showPart(path);
	}

<<<<<<< HEAD
	@FXML
	void openFile() {
		//check for opened files and close them
		if(hirarchy.getRoot() != null) {
			//TODO: check if user really wants to close the file
			closeFile();
		}
		FileChooser chooser = new FileChooser();
		File selectedFile = new File(chooser.showOpenDialog(new Stage()).getPath());
		TextReader reader = new TextReader();
		tr = reader.readArtifact(selectedFile);
		chooser.setTitle("Open Resource File");
		System.out.println(chooser.getTitle());
		rootItem = new TreeItem<String>(tr.getTreeName());
		rootItem.setExpanded(true);

		try {
			createTree();
		} catch (Exception e) {
			System.out.println("Beim einlesen der Datei ist ein Fehler aufgetreten.");
		}
=======
	public TreeView<?> getCurrentView() {
		return this.hirarchy;
>>>>>>> dca6141e12c99bd71eabaa7e17329e32483abdeb
	}
	
	/**
	 * A method to close a file
	 */
	@FXML
	void closeFile() {
		//set treeview and its values to null, then remove it from the background
		hirarchy.setRoot(null);
		hirarchy.setBackground(null);
		rootItem = null;
		tr = null;
		background.getChildren().remove(hirarchy);
	}
	
	/**
	 * Selects all elements in the treeview
	 */
	@FXML
	void selectAll() {
		hirarchy.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		hirarchy.getSelectionModel().selectAll();
		//System.err.println("selected: " + hirarchy.getSelectionModel().getSelectedItems());
	}
	
	/**
	 * Unselects all elements in the treeview
	 */
	@FXML
	void unselectAll() {
		//hirarchy.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		//nullpointer exception occurs
		hirarchy.getSelectionModel().clearSelection();
	}

	void createTree(Tree tr) {
		NodeUsage rootNodeUsage = new NodeUsage(tr.getRoot());
		rootItem = new TreeItem<NodeUsage>(rootNodeUsage);
		rootItem.setExpanded(true);
		for (Node node : tr.getLeaves()) {
			NodeUsage nodeTest = new NodeUsage(node);
			TreeItem<NodeUsage> item = new TreeItem<NodeUsage>(nodeTest);
			rootItem.getChildren().add(item);
		}
		hirarchy.setRoot(rootItem);
		hirarchy.setShowRoot(true);

		hirarchy.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			switchToPart("de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.part.properties_view");
			services.eventBroker.send("nodePropertiesEvent", hirarchy.getSelectionModel().getSelectedItem().getValue());
		});

	}

	@Optional
	@Inject
	public void openTree(@UIEventTopic("OpenTreeEvent") Tree tree) {
		switchToPart("de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.part.tree_view");
		createTree(tree);
		System.out.println(tree.getTreeName());
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

	public TreeView<?> getHirarchy() {
		return this.hirarchy;
	}

	public void setHirarchy(TreeView<NodeUsage> hirarchy) {
		this.hirarchy = hirarchy;
	}

	public VBox getBackground() {
		return background;
	}

	public void setBackground(VBox background) {
		this.background = background;
	}

}
