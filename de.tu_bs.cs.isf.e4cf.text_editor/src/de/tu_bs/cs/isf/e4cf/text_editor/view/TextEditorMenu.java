package de.tu_bs.cs.isf.e4cf.text_editor.view;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.text_editor.FileUtils;
import de.tu_bs.cs.isf.e4cf.text_editor.WordCountUtils;
import de.tu_bs.cs.isf.e4cf.text_editor.stringtable.EditorST;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import org.fxmisc.richtext.CodeArea;

public class TextEditorMenu implements Initializable {
	// File Menu
	@FXML
	private Menu newFile;
	@FXML
	private MenuItem openFile;
	@FXML
	private MenuItem saveFile;
	@FXML
	private MenuItem saveFileAs;
	@FXML
	private MenuItem closeFile;
	@FXML
	private MenuItem closeEditor;

	// Edit Menu
	@FXML
	private MenuItem undo;
	@FXML
	private MenuItem redo;
	@FXML
	private MenuItem cutText;
	@FXML
	private MenuItem copyText;
	@FXML
	private MenuItem pasteText;
	@FXML
	private MenuItem deleteText;
	@FXML
	private MenuItem selectAllText;

	// Extra Menu
	@FXML
	private MenuItem preferences;

	// Help Menu
	@FXML
	private MenuItem about;

	@FXML
	public TextEditor textEditorViewController;

	public Scene scene;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createNewFileItems();
	}

	public void setScene(Scene scene) {
		this.scene = scene;
		textEditorViewController.initFileUtils(scene);
	}

	/**
	 * Is called upon creating a new file and adds submenu-items to the "New"-menu
	 * item.
	 * 
	 * @author Soeren Christmann, Lukas Cronauer
	 * 
	 */
	public void createNewFileItems() {
		for (String FileType : EditorST.FILE_FORMATS) {
			MenuItem menu = new MenuItem(FileType + "-File");
			menu.setOnAction(e -> {
				for (Tab t : textEditorViewController.tabPane.getTabs()) {
					if (t.getUserData().toString()
							.startsWith(EditorST.NEW_TAB_TITLE + textEditorViewController.untitledCount)) {
						textEditorViewController.untitledCount++;
					}
				}
				textEditorViewController.saveChanges();
				textEditorViewController
						.loadTab(EditorST.NEW_TAB_TITLE + textEditorViewController.untitledCount + "." + FileType, "");
				textEditorViewController.getCurrentTab().getContent().requestFocus();
			});
			newFile.getItems().addAll(menu);
		}
	}

	/**
	 * Sets the actions of the Open item in the File menu. Open a File with a
	 * extension set in @FileUtils fileChooser fileExtensions
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	@FXML
	private void initOpenFileAction() {
		String[] fileInfo = textEditorViewController.fileUtils.openFile();
		if (!(fileInfo[1].isEmpty())) {
			textEditorViewController.loadTab(fileInfo[0], fileInfo[1]);
		}
	}

	/**
	 * Sets the actions of the Save item in the File menu. Saving the text into
	 * current File
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	@FXML
	private void initSaveAction() {
		String fileName = (String) textEditorViewController.getCurrentTab().getUserData();
		if (fileName.startsWith(EditorST.NEW_TAB_TITLE)) {
			String newpath = textEditorViewController.fileUtils.saveAs(textEditorViewController.getCurrentText());
			textEditorViewController.setCurrentTabUserData(newpath);
		} else {
			textEditorViewController.fileUtils.save((String) textEditorViewController.getCurrentTab().getUserData(),
					textEditorViewController.getCurrentText());
		}
	}

	/**
	 * Sets the actions of the SaveAs item in the File menu. Make a copy of the file
	 * in another file directory Or make a copy of the file with another name
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	@FXML
	private void initSaveAsAction() {
		String newpath = textEditorViewController.fileUtils.saveAs(textEditorViewController.getCurrentText());
		textEditorViewController.setCurrentTabUserData(newpath);
	}

	/**
	 * Sets the actions of the CloseItem item in the File menu. Closing the File
	 * that are currently open.
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya, Cedric Kapalla, Soeren Christmann
	 */
	@FXML
	private void initCloseFileAction() {
		textEditorViewController.saveChanges();
		textEditorViewController.tabPane.getTabs().remove(textEditorViewController.getCurrentTab());
	}

	/**
	 * Sets the actions of the CloseEditor item in the File menu. Closes the whole
	 * Editor after asking to save progress, then writes a closing message on the
	 * command line.
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya, Cedric Kapalla, Soeren Christmann
	 */
	@FXML
	private void initCloseEditorAction() {
		textEditorViewController.saveChanges();
		textEditorViewController.services.partService.setPartToBeRendered(EditorST.TEXT_EDITOR_FXML_ID, false);
	}

	/**
	 * Sets the actions of the Undo item in the Edit menu. Undoes the most recent
	 * actions taken in the editor. Currently empties the entire text-Area.
	 * 
	 * @author Cedric Kapalla,Soeren Christmann
	 */
	@FXML
	private void initUndoAction() {
		CodeArea codeArea = textEditorViewController.getCurrentCodeArea();
		codeArea.undo();
	}

	/**
	 * Sets the actions of the Redo item in the Edit menu. Redoes an action
	 * previously undone.
	 * 
	 * @author Cedric Kapalla,Soeren Christmann
	 */
	@FXML
	private void initRedoAction() {
		CodeArea codeArea = textEditorViewController.getCurrentCodeArea();
		codeArea.redo();
	}

	/**
	 * Sets the actions of the Cut item in the Edit menu. Cuts out the selected text
	 * from the editor window.
	 * 
	 * @author Cedric Kapalla,Soeren Christmann
	 */
	@FXML
	private void initCutAction() {
		CodeArea codeArea = textEditorViewController.getCurrentCodeArea();

		ClipboardContent text = new ClipboardContent();
		text.putString(codeArea.getSelectedText()); // add selected text to clipboard content
		textEditorViewController.systemClipboard.setContent(text); // add content to clipboard
		codeArea.deleteText(codeArea.getSelection());
	}

	/**
	 * Sets the actions of the Copy item in the Edit menu. Copies the selected text
	 * in the editor window.
	 * 
	 * @author Cedric Kapalla
	 */
	@FXML
	private void initCopyAction() {
		CodeArea codeArea = textEditorViewController.getCurrentCodeArea();

		ClipboardContent text = new ClipboardContent(); // add selected text to clipboard content
		text.putString(codeArea.getSelectedText()); // add content to clipboard
		textEditorViewController.systemClipboard.setContent(text);
	}

	/**
	 * Sets the actions of the Paste item in the Edit menu. Pastes text from
	 * clipboard into the editor window by reading it from the clipboard and then
	 * composing a new string around this content.
	 * 
	 * @author Cedric Kapalla
	 */
	@FXML
	private void initPasteAction() {
		CodeArea codeArea = textEditorViewController.getCurrentCodeArea();

		if (!textEditorViewController.systemClipboard.hasContent(DataFormat.PLAIN_TEXT)) {
			return; // does nothing if there is nothing or no text on clipboard
		}
		String pasteText = textEditorViewController.systemClipboard.getString(); // get text from Clipboard
		IndexRange range = codeArea.getSelection(); // finds cursor position and selected text to overwrite

		// initialisation of variables
		int endPos = 0; // defines where the cursor will be after paste action
		String updatedText = "";
		String origText = codeArea.getText();

		// Separates the points before and after the inserted text's position
		String firstPart = StringUtils.substring(origText, 0, range.getStart());
		String lastPart = StringUtils.substring(origText, range.getEnd(), StringUtils.length(origText));

		// puts together the new String
		updatedText = firstPart + pasteText + lastPart;

		// checks for where to put the cursor after adding text in
		if (range.getStart() == range.getEnd()) {
			endPos = range.getEnd() + StringUtils.length(pasteText);
		} else {
			endPos = range.getStart() + StringUtils.length(pasteText);
		}

		codeArea.replaceText(updatedText);
		codeArea.displaceCaret(endPos);
	}

	/**
	 * Sets the actions of the Delete item in the Edit menu. Deletes the selected
	 * text in the editor window.
	 * 
	 * @author Cedric Kapalla,Soeren Christmann
	 */
	@FXML
	private void initDeleteAction() {
		CodeArea codeArea = textEditorViewController.getCurrentCodeArea();
		codeArea.deleteText(codeArea.getSelection());
	}

	/**
	 * Sets the actions of the Select All item in the Edit menu. Selects all text
	 * open in the editor window.
	 * 
	 * @author Cedric Kapalla, Erwin Wijaya
	 */
	@FXML
	private void initSelectAllAction() {
		CodeArea codeArea = textEditorViewController.getCurrentCodeArea();
		codeArea.requestFocus();
		codeArea.selectAll();
	}

	/**
	 * Sets the actions of the Preferences item in the Extra menu. Currently just a
	 * placeholder until it is determined what Preferences are needed, or that the
	 * Item is unnecessary.
	 * 
	 * @author Cedric Kapalla, Soeren Christmann
	 */
	@FXML
	private void initPreferencesAction() {
		textEditorViewController.alert = new Alert(AlertType.INFORMATION);
		textEditorViewController.alert.setTitle("Preferences");
		textEditorViewController.alert.setHeaderText("Placeholder");
		textEditorViewController.alert.setContentText(
				"You would be able to adjust preferences here, but this is just a placeholder for now.");
		textEditorViewController.alert.showAndWait();
	}

	/**
	 * Sets the actions of the About item in the Help menu. Currently displays
	 * creators and project information.
	 * 
	 * @author Cedric Kapalla, Soeren Christmann
	 */
	@FXML
	private void initAboutAction() {
		textEditorViewController.alert = new Alert(AlertType.INFORMATION);
		textEditorViewController.alert.setTitle("About");
		textEditorViewController.alert.setHeaderText("Text Editor");
		textEditorViewController.alert
				.setContentText("This is a text editor plug-in for the e4compare framework, created by "
						+ "Lukas Cronauer, Soeren Christmann, Cedric Kapalla, and Erwin Wijaya.\n\n"
						+ "It can do all the things one would expect from such an editor.");
		textEditorViewController.alert.showAndWait();
	}

}
