package de.tu_bs.cs.isf.e4cf.text_editor.view;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;


/**
 * 
 * @author Sören Christmann, Cedric Kapalla
 *
 */
public class TextEditor implements Initializable {
	@FXML private MenuItem newFile;
	@FXML private MenuItem openFile;
	@FXML private MenuItem saveFile;
	@FXML private MenuItem saveFileAs;
	@FXML private MenuItem closeFile;
	@FXML private MenuItem closeEditor;
	
	@Inject private ServiceContainer services;
	
	

	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initFileMenuItem();
		
	}
	
	private void initFileMenuItem() {
		initFileMenuItemNewAction();
		initFileMenuItemOpenAction();
		initFileMenuItemSaveAction();
		initFileMenuItemSaveAsAction();
		// Closes the current File
		initFileMenuItemCloseFileAction();
		//Closes the Editor Window
		initFileMenuItemCloseEditorAction();
		
	}
	private void initFileMenuItemNewAction() {
		newFile.setOnAction(e -> {
			System.out.println("New");
		});
	}
	private void initFileMenuItemOpenAction() {
		openFile.setOnAction(e -> {
			System.out.println("Open");
		});
	}
	private void initFileMenuItemSaveAction() {
		saveFile.setOnAction(e -> {
			System.out.println("Save");
		});
	}
	private void initFileMenuItemSaveAsAction() {
		saveFileAs.setOnAction(e -> {
			System.out.println("Save As");
		});
	}
	private void initFileMenuItemCloseFileAction() {
		closeFile.setOnAction(e -> {
			System.out.println("Close File");
		});
	}
	private void initFileMenuItemCloseEditorAction() {
		closeEditor.setOnAction(e -> {
			System.out.println("Close Editor");
		});
	}

}
