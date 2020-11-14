package de.tu_bs.cs.isf.e4cf.text_editor.view;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.text_editor.FileUtils;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.IndexRange;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;



/**
 * 
 * @author Sören Christmann, Cedric Kapalla, Lukas Cronauer, Erwin Wijaya
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
	
	//Text area set up
	@FXML private TextArea textArea;
	Clipboard systemClipboard = Clipboard.getSystemClipboard();
	
	@Inject private ServiceContainer services;

	//Utils class to handle file operations
	private FileUtils fileUtils;
	
	//Scene of this object
	private Scene scene;
	
	

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
	
	/**
	 * Sets the actions of the New item in the File menu.
	 * Make a new File
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	private void initFileMenuItemNewAction() {
		newFile.setOnAction(e -> {
			//TODO: Ask to save unsaved changes
			textArea.setText(null);
		});
	}
	
	/**
	 * Sets the actions of the Open item in the File menu.
	 * Open a File with a extension set in @FileUtils fileChooser
	 * fileExtensions
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	private void initFileMenuItemOpenAction() {
		openFile.setOnAction(e -> {
			String content = fileUtils.openFile();
			if (!content.equals("")) {
			 	textArea.setText(content);
			}
		});
	}
	
	/**
	 * Sets the actions of the Save item in the File menu.
	 * Saving the text into current File
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	private void initFileMenuItemSaveAction() {
		saveFile.setOnAction(e -> {
			String content = textArea.getText();
			if (!fileUtils.save(content)) {
				// show error dialog
			}
		});
	}
	
	/**
	 * Sets the actions of the SaveAs item in the File menu.
	 * Make a copy of the file in another file directory  
	 * Or make a copy of the file with another name
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	private void initFileMenuItemSaveAsAction() {
		saveFileAs.setOnAction(e -> {
			String content = textArea.getText();
			if (!fileUtils.saveAs(content)) {
				// show error dialog
			}
		});
	}
	
	/**
	 * Sets the actions of the CloseItem item in the File menu.
	 * Closing the File that are currently open.
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	private void initFileMenuItemCloseFileAction() {
		closeFile.setOnAction(e -> {
			saveChanges();
			//TODO: most likely futher logic needed to close current file/tab
		});
	}
	
	/**
	 * Sets the actions of the CloseEditor item in the File menu.
	 * Closing the whole Editor.
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	private void initFileMenuItemCloseEditorAction() {
		closeEditor.setOnAction(e -> {
			saveChanges();
			//TODO: most likely futher logic needed to close entire text editor if possible
		});
	}

	/**
	 * Saves the current content of the textArea to a file
	 * when there are changes compared to the last saved version
	 * 
	 * @author Lukas Cronauer
	 */
	private void saveChanges() {
		String content = textArea.getText();
		if (content.hashCode() != fileUtils.getLastRevision()) {
			fileUtils.save(content);
		}
	}
	
	/**
	 * Initializes the FileUtils instance of this object with the window
	 * obtained from scene
	 * 
	 * @param scene The scene that this object is part of
	 * 
	 * @author Lukas Cronauer
	 */
	public void initFileUtils(Scene scene) {
		 this.scene = scene;
		 if (scene != null) {
			fileUtils = new FileUtils(scene.getWindow());
		 }
	}

	/**
	 * Initializes the Edit menu. Includes undo/redo, copy/paste/cut, Delete, and Select All
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
			ClipboardContent text = new ClipboardContent();
			text.putString(textArea.getSelectedText());	//add selected text to clipboard content
			systemClipboard.setContent(text);	//add content to clipboard
			textArea.deleteText(textArea.getSelection());
		});
	}
	
	/**
	 * Sets the actions of the Copy item in the Edit menu.
	 * Copies the selected text in the editor window.
	 * 
	 * @author Cedric Kapalla
	 */
	private void initEditMenuItemCopyTextAction() {
		copyText.setOnAction(e -> {
			ClipboardContent text = new ClipboardContent();	//add selected text to clipboard content
			text.putString(textArea.getSelectedText());	//add content to clipboard
			systemClipboard.setContent(text);
		});
	}
	
	/**
	 * Sets the actions of the Paste item in the Edit menu.
	 * Pastes text from clipboard into the editor window by reading it from the clipboard and then
	 * composing a new string around this content.
	 * 
	 * @author Cedric Kapalla
	 */
	private void initEditMenuItemPasteTextAction() {
		pasteText.setOnAction(e -> {
			if( !systemClipboard.hasContent(DataFormat.PLAIN_TEXT) ) {
			   return;	//does nothing if there is nothing or no text on clipboard
			}
			
			String pasteText = systemClipboard.getString();	//get text from Clipboard
			IndexRange range = textArea.getSelection();	//finds cursor position and selected text to overwrite
			
			//initialisation of variables
			int endPos = 0;	//defines where the cursor will be after paste action
			String updatedText = "";
			String origText = textArea.getText();
			
			//Separates the points before and after the inserted text's position
			String firstPart = StringUtils.substring (origText, 0, range.getStart());
			String lastPart = StringUtils.substring(origText, range.getEnd(), StringUtils.length(origText));
			
			//puts together the new String
			updatedText = firstPart + pasteText + lastPart;
			
			//checks for where to put the cursor after adding text in
			if (range.getStart() == range.getEnd()) {
				endPos = range.getEnd() + StringUtils.length(pasteText);
			} else {
				endPos = range.getStart() + StringUtils.length(pasteText);
			}
			
			textArea.setText(updatedText);
			textArea.positionCaret(endPos);
		}); 
	}
	
	/**
	 * Sets the actions of the Delete item in the Edit menu.
	 * Deletes the selected text in the editor window.
	 * 
	 * @author Cedric Kapalla
	 */
	private void initEditMenuItemDeleteTextAction() {
		deleteText.setOnAction(e -> {
			textArea.deleteText(textArea.getSelection());
		});
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
	 * This function initializes the Extra menu. It consists of the 'Preference' item
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
	 * This function initializes the Help menu. It consists of the 'Preference' item
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