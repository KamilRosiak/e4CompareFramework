package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import java.io.File;
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
import javax.swing.JFileChooser;

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
		 * Parent root;
		 * 
		 * Stage stage = new Stage(); stage.initModality(Modality.APPLICATION_MODAL);
		 * stage.initStyle(StageStyle.UNDECORATED); stage.setTitle("Properties");
		 * stage.show(); System.out.println();
		 */
		services = DataStructureEditorController.getServices();
		services.partService.showPart("de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.part.properties_view");
		listingMethods(convertFileIntoClass());
	}

	public void listingMethods(Class s) {
		Class c = s;
		Method[] methods = c.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			System.out.println("The method is: " + methods[i].toString());
		}
		// return methods;
	}

	public File chooseFile() {
		File filePath = null;
		JFileChooser fileChooser = new JFileChooser();
		int choosingFile = fileChooser.showOpenDialog(null);
		if (choosingFile == JFileChooser.APPROVE_OPTION) {
			filePath = fileChooser.getSelectedFile();
			System.out.println(fileChooser.getSelectedFile().getName());
			System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk" +filePath);
		}
		return filePath;
	}
	public Class convertFileIntoClass() throws IOException {
		
		File c = chooseFile();
		Class o = c.getClass();
		return o;
	}
}
