package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;

import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.view.VisualizeTreeView;
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
	private TreeView<?> hirarchy;

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
	@FXML
	void createTree(ActionEvent event) throws IOException {
		
		TreeItem<String> rootItem = new TreeItem<String> ("class");
		rootItem.setExpanded(true);
		 for (int i = 1; i < 6; i++) {
	            TreeItem<String> item = new TreeItem<String> ("Message" + i);            
	            rootItem.getChildren().add(item);
	        }    
		
		hirarchy = new TreeView<String> (rootItem);
		background.getChildren().add(hirarchy);
		
		
		System.out.println(rootItem.getValue());
		System.out.println(background.getHeight());
		System.out.println(hirarchy.getHeight());
//		NodeImpl node = new NodeImpl("class");
//		TreeImpl tree2 = new TreeImpl("testTree", node);
//		System.out.println(node.toString());
//		hirarchy = new TreeView<?>;
	}

	
}
