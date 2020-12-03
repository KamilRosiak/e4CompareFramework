package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import javax.inject.Inject;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.TextReader;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.File;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
	private TreeItem<NodeUsage> rootItem;

//	@FXML
//	public void initialize() {
//		createTree();
//	}

	void openProperties() {
		services.partService.showPart("de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.part.properties_view");
	}

	@FXML
	void openFile() {
		FileChooser chooser = new FileChooser();
		File selectedFile = new File(chooser.showOpenDialog(new Stage()).getPath());
		TextReader reader = new TextReader();
		tr = reader.readArtifact(selectedFile);
		chooser.setTitle("Open Resource File");
		System.out.println(chooser.getTitle());

		NodeUsage rootNodeUsage = new NodeUsage(tr.getRoot());
		System.out.println(rootNodeUsage);

		rootItem = new TreeItem<NodeUsage>(rootNodeUsage);
		rootItem.setExpanded(true);

		try {
			createTree();
		} catch (Exception e) {
			System.out.println("Beim einlesen der Datei ist ein Fehler aufgetreten.");
		}
	}

	public Tree getTree() {
		return this.tr;
	}

	public TreeView<?> getCurrentView() {
		return this.hirarchy;
	}

	void createTree() {
//		Datei wird eingelesen und als TreeView ausgegeben

//		System.out.println(tr.getLeaves());
		for (Node node : tr.getLeaves()) {
			NodeUsage nodeTest = new NodeUsage(node);
			TreeItem<NodeUsage> item = new TreeItem<NodeUsage>(nodeTest);
			rootItem.getChildren().add(item);

			// System.out.println(node.toString());
		}
		//System.out.println(rootItem);
		hirarchy = new TreeView<NodeUsage>(rootItem);
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
