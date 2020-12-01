package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.TextReader;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.File;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
	private TreeView<?> hirarchy;

	private Tree tr;
	private TreeItem<String> rootItem;

//	@FXML
//	public void initialize() {
//		createTree();
//	}

	void openProperties() {
		services.partService.showPart("de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.part.properties_view");
	}

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

//	@Optional
//	@Inject
//	public void showConstraints(@UIEventTopic("SHOW_CONSTRAINT_EVENT") String event) {
//		updateView();
//	}
//	
//	@Focus
//	public void updateView() {
//		try {
//			DataStructureEditorController dsec = ContextInjectionFactory.make(DataStructureEditorController.class,
//					EclipseContextFactory.create());
//			currentModel = dsec.getCurrentFeatureDiagram();
//			view.showConstraints(fmec.getCurrentFeatureDiagram().getConstraints());
//		} catch (Exception e) {
//			RCPMessageProvider.errorMessage("Error", "No FeatureModel Loaded");
//		}
//
//	}

	void createTree() {
//		Datei wird eingelesen und als TreeView ausgegeben

//		System.out.println(tr.getLeaves());
		for (Node node : tr.getLeaves()) {
			TreeItem<String> item = new TreeItem<String>(node.toString());
			rootItem.getChildren().add(item);
			System.out.println(node.toString());
		}

		hirarchy = new TreeView<String>(rootItem);
		hirarchy.setShowRoot(true);

		hirarchy.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			openProperties();
			services.eventBroker.send("nodePropertiesEvent", hirarchy.getSelectionModel().getSelectedItem().getValue());
		});

//		hirarchy.setRoot(rootItem);
//		hirarchy= new TreeView(rootItem);
		background.getChildren().add(hirarchy);

		System.out.println(rootItem.getValue());
//		NodeImpl node = new NodeImpl("class");
//		TreeImpl tree2 = new TreeImpl("testTree", node);
//		System.out.println(node.toString());
//		hirarchy = new TreeView<?>;

	}

	public TreeView<?> getHirarchy() {
		return this.hirarchy;
	}

	public void setHirarchy(TreeView<String> hirarchy) {
		this.hirarchy = hirarchy;
	}

	public VBox getBackground() {
		return background;
	}

	public void setBackground(VBox background) {
		this.background = background;
	}

}
