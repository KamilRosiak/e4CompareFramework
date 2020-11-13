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
	//File Menu
	@FXML private MenuItem newFile;
	@FXML private MenuItem openFile;
	@FXML private MenuItem saveFile;
	@FXML private MenuItem saveFileAs;
	@FXML private MenuItem closeFile;
	@FXML private MenuItem closeEditor;
	
	//Edit Menu
	@FXML private MenuItem undo;
	@FXML private MenuItem redo;
	@FXML private MenuItem cutText;
	@FXML private MenuItem copyText;
	@FXML private MenuItem pasteText;
	@FXML private MenuItem deleteText;
	@FXML private MenuItem selectAllText;
	
	//Extra Menu
	@FXML private MenuItem preferences;
	
	//Help Menu
	@FXML private MenuItem about;
	
	
	@Inject private ServiceContainer services;	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initFileMenuItems();
		initEditMenuItems();
		initExtraMenuItems();
		initHelpMenuItems();
	}
	
	private void initFileMenuItems() {
		initFileMenuItemNewAction(); 
		initFileMenuItemOpenAction();
		initFileMenuItemSaveAction();
		initFileMenuItemSaveAsAction();
		initFileMenuItemCloseFileAction(); // Closes the current File
		initFileMenuItemCloseEditorAction(); //Closes the Editor Window
		
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

	
	private void initEditMenuItems() {
		initEditMenuItemUndoAction(); 
		initEditMenuItemRedoAction();
		initEditMenuItemCutTextAction();
		initEditMenuItemCopyTextAction();
		initEditMenuItemPasteTextAction();
		initEditMenuItemDeleteTextAction(); 
		initEditMenuItemSelectAllAction(); 
	}
	
	private void initEditMenuItemUndoAction() {
		undo.setOnAction(e -> {
			System.out.println("Undo text or action.");
		});
	}

	private void initEditMenuItemRedoAction() {
		redo.setOnAction(e -> {
			System.out.println("Redo text or action once undone.");
		});
	}
	
	private void initEditMenuItemCutTextAction() {
		cutText.setOnAction(e -> {
			System.out.println("Cut out selected text.");
		});
	}
	
	private void initEditMenuItemCopyTextAction() {
		copyText.setOnAction(e -> {
			System.out.println("Copy selected text.");
		});
	}
	
	private void initEditMenuItemPasteTextAction() {
		pasteText.setOnAction(e -> {
			System.out.println("Paste text on clipboard.");
		});
	}
	
	private void initEditMenuItemDeleteTextAction() {
		deleteText.setOnAction(e -> {
			System.out.println("Delete selected text.");
		});
	}
	
	private void initEditMenuItemSelectAllAction() {
		selectAllText.setOnAction(e -> {
			System.out.println("Select all text.");
		});
	}
	
	
	private void initExtraMenuItems() {
		initExtraMenuItemPreferenceAction();  
	}
	
	private void initExtraMenuItemPreferenceAction() {
		preferences.setOnAction(e -> {
			System.out.println("Adjust Preferences");
		});
	}
	
	
	private void initHelpMenuItems() {
		initHelpMenuItemAboutAction();  
	}
	
	private void initHelpMenuItemAboutAction() {
		about.setOnAction(e -> {
			System.out.println("Placeholder. There is no help for you :(");
		});
	}
}
