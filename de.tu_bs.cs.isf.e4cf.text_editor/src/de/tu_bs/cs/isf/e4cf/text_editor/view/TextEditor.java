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

	/**
	 * Initialises the Edit menu. Includes undo/redo, copy/paste/cut, Delete, and Select All
	 * 
	 * @author Cedric Kapalla
	 */
	private void initEditMenuItems() {
		initEditMenuItemUndoAction();	//undo
		initEditMenuItemRedoAction();	//redo
		initEditMenuItemCutTextAction();	//cut
		initEditMenuItemCopyTextAction();	//copy
		initEditMenuItemPasteTextAction();	//paste
		initEditMenuItemDeleteTextAction();	 //delete
		initEditMenuItemSelectAllAction(); 	//select all
	}
	
	/**
	 * Sets the actions of the Undo item in the Edit menu.
	 * Undoes the most recent actions taken in the editor.
	 * 
	 * @author Cedric Kapalla
	 */
	private void initEditMenuItemUndoAction() {
		undo.setOnAction(e -> {		//activate once pressed
			System.out.println("Undo text or action.");	
		}); //currently a placeholder
	}

	/**
	 * Sets the actions of the Redo item in the Edit menu.
	 * Redoes an action previously undone.
	 * 
	 * @author Cedric Kapalla
	 */
	private void initEditMenuItemRedoAction() {
		redo.setOnAction(e -> {
			System.out.println("Redo text or action once undone."); 
		}); //currently a placeholder
	}

	/**
	 * Sets the actions of the Cut item in the Edit menu.
	 * Cuts out the selected text from the editor window.
	 * 
	 * @author Cedric Kapalla
	 */
	private void initEditMenuItemCutTextAction() {
		cutText.setOnAction(e -> {
			System.out.println("Cut out selected text.");
		}); //currently a placeholder
	}
	
	/**
	 * Sets the actions of the Copy item in the Edit menu.
	 * Copies the selected text in the editor window.
	 * 
	 * @author Cedric Kapalla
	 */
	private void initEditMenuItemCopyTextAction() {
		copyText.setOnAction(e -> {
			System.out.println("Copy selected text.");
		}); //currently a placeholder
	}
	
	/**
	 * Sets the actions of the Paste item in the Edit menu.
	 * Pastes text from clipboard into the editor window.
	 * 
	 * @author Cedric Kapalla
	 */
	private void initEditMenuItemPasteTextAction() {
		pasteText.setOnAction(e -> {
			System.out.println("Paste text on clipboard.");
		}); //currently a placeholder
	}
	
	/**
	 * Sets the actions of the Delete item in the Edit menu.
	 * Deletes the selected text in the editor window.
	 * 
	 * @author Cedric Kapalla
	 */
	private void initEditMenuItemDeleteTextAction() {
		deleteText.setOnAction(e -> {
			System.out.println("Delete selected text.");
		}); //currently a placeholder
	}
	
	/**
	 * Sets the actions of the Select All item in the Edit menu.
	 * Selects all text open in the editor window.
	 * 
	 * @author Cedric Kapalla
	 */
	private void initEditMenuItemSelectAllAction() {
		selectAllText.setOnAction(e -> {
			System.out.println("Select all text.");
		}); //currently a placeholder
	}
	
	/**
	 * This function initialises the Extra menu. It consists of the 'Preference' item
	 * 
	 * @author Cedric Kapalla
	 */
	private void initExtraMenuItems() {
		initExtraMenuItemPreferenceAction();  //preferences
	}
	
	/**
	 * Sets the actions of the Preferences item in the Extra menu.
	 * 
	 * @author Cedric Kapalla
	 */
	private void initExtraMenuItemPreferenceAction() {
		preferences.setOnAction(e -> {
			System.out.println("Adjust Preferences");
		}); //currently a placeholder
	}
	
	/**
	 * This function initialises the Help menu. It consists of the 'Preference' item
	 * 
	 * @author Cedric Kapalla
	 */
	private void initHelpMenuItems() {
		initHelpMenuItemAboutAction();  //about
	}
	
	/**
	 * Sets the actions of the About item in the Help menu
	 * 
	 * @author Cedric Kapalla
	 */
	private void initHelpMenuItemAboutAction() {
		about.setOnAction(e -> {
			System.out.println("Placeholder. There is no help for you :(");
		}); //currently a placeholder
	}
}
