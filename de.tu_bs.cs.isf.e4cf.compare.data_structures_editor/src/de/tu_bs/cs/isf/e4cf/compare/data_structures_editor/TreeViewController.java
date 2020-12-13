package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DataStructuresEditorST;
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

/**
 * Controller for VisualizeTreeView.fxml
 * 
 * @author Team05
 *
 */
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
	private TreeView<NodeUsage> treeView;

	private TreeItem<NodeUsage> rootItem;

	@FXML
	private TextField searchTextField;

	/**
	 * switching View to TreeView if a .txt file is selected from the explorer
	 * 
	 * @param tree data structure of type tree
	 */
	@Optional
	@Inject
	public void openTree(@UIEventTopic("OpenTreeEvent") Tree tree) {
		TreeViewUtilities.switchToPart(DataStructuresEditorST.TREE_VIEW_ID, services);
		treeView = TreeViewUtilities.initTree(tree, this.treeView, rootItem, services);

	}

	/**
	 * A method to close a file
	 */
	@FXML
	void closeFile() {
		// set treeview and its values to null, then remove it from the background
		treeView.setRoot(null);

	}

	/**
	 * Selects all elements in the treeview
	 */
	@FXML
	void selectAll() {
		treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		treeView.getSelectionModel().selectAll();
	}

	/**
	 * Unselects all elements in the treeview
	 */
	@FXML
	void unselectAll() {
		treeView.getSelectionModel().clearSelection();
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
	void search() {
		searchField();
	}

	@FXML
	void renameNode() {
		System.out.println("Ahh");
	}

	@FXML
	void deleteNode() throws FileNotFoundException {
		treeView.getSelectionModel().getSelectedItem().setValue(null);
		treeView.getSelectionModel().getSelectedItem().setGraphic(null);

		berechnen("basti.txt");

	}

	public void berechnen(String choosenFile) throws FileNotFoundException {

		// Datei lesen
		File filename = new File(choosenFile);
		FileReader eingabe = new FileReader(filename);
		BufferedReader pInFile = new BufferedReader(eingabe);
		try (FileWriter writer = new FileWriter(choosenFile);
	             BufferedWriter bw = new BufferedWriter(writer)) {

	            bw.write("TEST");
	            // Hier ist es noch nicht nrichtig drinne weil es eine neue datei dann gibt mit dem namen und man ja nur die Node löschen moechte
	            //dazu ist das problem das wenn man einmal die TreeView oeffnet dann nicht mehr der project explorer richtig klappt 
	            // dazu noch muss man schauen wie man die datei im explorer dann aendert und nicht nur lokal auf seinem Geraet

	        } catch (IOException e) {
	            System.err.format("IOException: %s%n", e);
	        }


	}

}
