package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.TextReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter.TextWritter;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.view.VisualizeTreeView;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.File;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import java.lang.reflect.Method;
import java.lang.reflect.*;

import javax.inject.Inject;

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
	private TreeView<?> hirarchy= new TreeView<String>();		
	
	@FXML
	public void initialize() {
		createTree();
	}

	@FXML
	void openProperties(ActionEvent event) throws IOException {
		/**
		 * Parent root;
		 * 
		 * Stage stage = new Stage(); stage.initModality(Modality.APPLICATION_MODAL);
		 * stage.initStyle(StageStyle.UNDECORATED); stage.setTitle("Properties");
		 * stage.show(); System.out.println();
		 */
		services.partService.showPart("de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.part.properties_view");		
		
	}
	
	
	void createTree() {
		//Datei wird eingelesen und als TreeView ausgegeben
		FileChooser chooser = new FileChooser();
		File selectedFile = new File (chooser.showOpenDialog(new Stage()).getPath());
		TextReader reader = new TextReader();
		Tree tr = reader.readArtifact(selectedFile);
		chooser.setTitle("Open Resource File");
		System.out.println(chooser.getTitle());
		// tw.writeArtifact(tr,selectedFile.getAbsolutePath());
	    TreeItem<String> rootItem = new TreeItem<String> (tr.getTreeName());
		rootItem.setExpanded(true);
		System.out.println(tr.getLeaves());
		for (Node node: tr.getLeaves()) {
	            TreeItem<String> item = new TreeItem<String> (node.toString());            
	            rootItem.getChildren().add(item);
			System.out.println(node.toString());
	        }    
		
		hirarchy.setShowRoot(true);
		hirarchy= new TreeView(rootItem);
		background.getChildren().add(hirarchy);
		
		System.out.println(rootItem.getValue());
		System.out.println(background.getHeight());
		System.out.println(hirarchy.getHeight());
//		NodeImpl node = new NodeImpl("class");
//		TreeImpl tree2 = new TreeImpl("testTree", node);
//		System.out.println(node.toString());
//		hirarchy = new TreeView<?>;

	}

	public TreeView<?> getHirarchy() {
		return this.hirarchy;
	}
	
	public void setHirarchy(TreeView<?> hirarchy) {
		this.hirarchy = hirarchy;
	}
	
	public VBox getBackground() {
		return background;
	}
	public void setBackground(VBox background) {
		this.background = background;
	}
	
	
}
