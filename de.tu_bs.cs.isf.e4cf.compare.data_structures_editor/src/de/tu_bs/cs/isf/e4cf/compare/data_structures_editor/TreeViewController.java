package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.view.TreeView;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import java.lang.reflect.Method;
import java.lang.reflect.*;

public class TreeViewController {
		private ServiceContainer services;
		

	 	@FXML
	    private MenuItem properties;

	    @FXML
	    private Button search;

	    @FXML
	    private Label testLabel;
	    
		
	    @FXML
	    void openProperties(ActionEvent event) throws IOException {
	    	/**
	    	Parent root;
	    
		    Stage stage = new Stage();
		    stage.initModality(Modality.APPLICATION_MODAL);
		    stage.initStyle(StageStyle.UNDECORATED);
		    stage.setTitle("Properties");
		    stage.show(); 
		    System.out.println();
		    */
	    	services = DataStructureEditorController.getServices();
	    	services.partService.showPart("de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.part.properties_view");
	    	listingMethods();
	    	
	    	
	    }
	    
	    public void listingMethods() {
	        Class c = TreeView.class;
	        Method[] methods = c.getDeclaredMethods();
	        for(int i = 0; i < methods.length; i++) {
	            System.out.println("The method is: " + methods[i].toString());
	         }
			//return methods;
	    }	
 }

