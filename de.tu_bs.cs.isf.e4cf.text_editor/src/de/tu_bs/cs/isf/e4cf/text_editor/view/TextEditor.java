package de.tu_bs.cs.isf.e4cf.text_editor.view;

import java.net.URL;
import java.util.ResourceBundle;

import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.text_editor.FileUtils;
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

/**
 * View class containing all user interface items of our plugin.
 * Used to intialize the menu structure. Additionally several methods 
 * to interact with the tabPane are provided.
 * 
 * @author Soeren Christmann, Cedric Kapalla, Lukas Cronauer, Erwin Wijaya
 *
 */
public class TextEditor implements Initializable {
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

	// word and row counter labels
	@FXML
	private Label wordCount;
	@FXML
	private Label rowCount;
	// Public ?
	Clipboard systemClipboard = Clipboard.getSystemClipboard();

	@FXML
	private TabPane tabPane;

	@Inject
	private ServiceContainer services;

	// Utils class to handle file operations
	private FileUtils fileUtils;

	// Scene of this object
	private Scene scene;

	Alert alert;

	// number of new files for which the filename is not set (yet)
	int untitledCount = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createNewFileItems();
		initCountLabelItems();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
		getCurrentTab().setUserData(EditorST.NEW_TAB_TITLE);
		getCurrentTab().setContent(EditorTab.createCodeArea(""));
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
				for (Tab t : tabPane.getTabs()) {
					if (t.getUserData().toString().startsWith(EditorST.NEW_TAB_TITLE + untitledCount)) {
						untitledCount++;
					}
				}
				saveChanges();
				loadTab(EditorST.NEW_TAB_TITLE + untitledCount + "." + FileType, "");
				getCurrentTab().getContent().requestFocus();
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
		String[] fileInfo = fileUtils.openFile();
		if (!(fileInfo[1].isEmpty())) {
			loadTab(fileInfo[0], fileInfo[1]);
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
		String fileName = (String) getCurrentTab().getUserData();
		if (fileName.startsWith(EditorST.NEW_TAB_TITLE)) {
			String newpath = fileUtils.saveAs(getCurrentText());
			setCurrentTabUserData(newpath);
		} else {
			fileUtils.save((String) getCurrentTab().getUserData(), getCurrentText());
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
		String newpath = fileUtils.saveAs(getCurrentText());
		setCurrentTabUserData(newpath);
	}

	/**
	 * Sets the actions of the CloseItem item in the File menu. Closing the File
	 * that are currently open.
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya, Cedric Kapalla, Soeren Christmann
	 */
	@FXML
	private void initCloseFileAction() {
		saveChanges();
		tabPane.getTabs().remove(getCurrentTab());
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
		saveChanges();
		services.partService.setPartToBeRendered(EditorST.TEXT_EDITOR_FXML_ID, false);
	}

	/**
	 * Sets the actions of the Undo item in the Edit menu. Undoes the most recent
	 * actions taken in the editor. Currently empties the entire text-Area.
	 * 
	 * @author Cedric Kapalla,Soeren Christmann
	 */
	@FXML
	private void initUndoAction() {
		CodeArea codeArea = getCurrentCodeArea();
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
		CodeArea codeArea = getCurrentCodeArea();
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
		CodeArea codeArea = getCurrentCodeArea();

		ClipboardContent text = new ClipboardContent();
		text.putString(codeArea.getSelectedText()); // add selected text to clipboard content
		systemClipboard.setContent(text); // add content to clipboard
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
		CodeArea codeArea = getCurrentCodeArea();

		ClipboardContent text = new ClipboardContent(); // add selected text to clipboard content
		text.putString(codeArea.getSelectedText()); // add content to clipboard
		systemClipboard.setContent(text);
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
		CodeArea codeArea = getCurrentCodeArea();

		if (!systemClipboard.hasContent(DataFormat.PLAIN_TEXT)) {
			return; // does nothing if there is nothing or no text on clipboard
		}
		String pasteText = systemClipboard.getString(); // get text from Clipboard
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
		CodeArea codeArea = getCurrentCodeArea();
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
		CodeArea codeArea = getCurrentCodeArea();
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
		alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Preferences");
		alert.setHeaderText("Placeholder");
		alert.setContentText("You would be able to adjust preferences here, but this is just a placeholder for now.");
		alert.showAndWait();
	}

	/**
	 * Sets the actions of the About item in the Help menu. Currently displays
	 * creators and project information.
	 * 
	 * @author Cedric Kapalla, Soeren Christmann
	 */
	@FXML
	private void initAboutAction() {
		alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText("Text Editor");
		alert.setContentText("This is a text editor plug-in for the e4compare framework, created by "
				+ "Lukas Cronauer, Soeren Christmann, Cedric Kapalla, and Erwin Wijaya.\n\n"
				+ "It can do all the things one would expect from such an editor.");
		alert.showAndWait();
	}

	/**
	 * Sets up the wordcount
	 * 
	 * @author Soeren Christmann
	 * 
	 */
	private void initCountLabelItems() {
		initCountLabelItemAction();
	}

	/**
	 * Initialises counting of words and lines
	 * 
	 * @author Soeren Christmann, Cedric Kapalla
	 */
	// Work in Progress
	private void initCountLabelItemAction() {
		for (Tab tab : tabPane.getTabs()) {
			CodeArea codeArea = (CodeArea) tab.getContent();
			tab.selectedProperty().addListener((ov, oldvalue, newvalue) -> {
				if (newvalue) {
					count(codeArea);
				}
			});
			codeArea.setOnKeyReleased(e -> {
				count(codeArea);
			});
		}
	}

	// supporting functions start here

	/**
	 * Counts the Words and Rows in the Textfield when a key is pressed. If the
	 * Textfield is empty there is 0 Words and 0 Rows.
	 * 
	 * @author Soeren Christmann, Cedric Kapalla
	 */
	public void count(CodeArea codeArea) {

		String text = codeArea.getText();
		StringBuffer bufferText = new StringBuffer(text);
		int newLineCounter = 1;
		if (text.length() == 0) {
			wordCount.setText("Words: 0");
			rowCount.setText("Rows: 0");
			return;
		}
		// check whether there is any text to begin with
		char first = bufferText.charAt(0);
		if (first == ' ' || first == '\t') {
			bufferText.replace(0, 1, "");
		}
		if (first == '\n') {
			bufferText.replace(0, 1, "");
			newLineCounter++;
		}

		// Trims the Tabs.
		for (int i = 0; i < bufferText.length(); i++) {
			if (bufferText.charAt(i) == '\t') {
				bufferText.replace(i, i + 1, " ");
			}
		}
		// trims the Newlines out of the Text and Counts them
		for (int i = 0; i < bufferText.length(); i++) {
			if (bufferText.charAt(i) == '\n') {
				// Number of New Lines = Number of Rows
				newLineCounter++;
				bufferText.replace(i, i + 1, " ");
			}
		}
		String tmp = bufferText.toString();
		tmp = tmp.trim();
		bufferText = new StringBuffer(tmp);
		// trims the additional Spaces
		// Every Space is a new Word
		for (int i = 0; i < bufferText.length(); i++) {
			if (bufferText.charAt(i) == ' ') {
				if (bufferText.charAt(i + 1) == ' ') {
					// Placeholder so only one Space is counted for a new word
					bufferText.replace(i, i + 1, "a");
				}
			}
		}
		// Counts Spaces
		// Number of Spaces = Number of Word
		long countWord = (bufferText.chars().filter(ch -> ch == ' ').count() + 1);
		wordCount.setText("Words: " + countWord);
		rowCount.setText("Rows: " + newLineCounter);
	}

	/**
	 * Saves the current content of the codeArea to a file when there are changes
	 * compared to the last saved version.
	 * 
	 * @author Lukas Cronauer
	 */
	private void saveChanges() {
		String content = getCurrentText();
		if (content.hashCode() != fileUtils.getLastRevision()) {
			if (RCPMessageProvider.questionMessage("Save...", "Would you like to save the changes in this file?")) {
				fileUtils.save((String) getCurrentTab().getUserData(), content);
			}
		}
	}

	/**
	 * Initializes the FileUtils instance of this object with the window obtained
	 * from scene.
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
	 * Gets the currently selected tab in the tabPane
	 * 
	 * @return Currently selected Tab in the tabPane
	 *
	 * @author Lukas Cronauer
	 */
	private Tab getCurrentTab() {
		Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
		if (currentTab == null) {
			currentTab = new EditorTab(EditorST.NEW_TAB_TITLE, "", "");
			currentTab.setUserData(EditorST.NEW_TAB_TITLE);
			tabPane.getTabs().add(currentTab);
			tabPane.getSelectionModel().select(currentTab);
			initCountLabelItems();
		}
		return currentTab;
	}

	/**
	 * Gets the currently visible CodeArea in the tabPane
	 * 
	 * @return Currently visible CodeArea
	 * 
	 * @author Lukas Cronauer
	 */
	private CodeArea getCurrentCodeArea() {
		return (CodeArea) getCurrentTab().getContent();
	}

	/**
	 * Gets the text in the CodeArea of the currently selected Tab
	 * 
	 * @return Text of currently visible CodeArea
	 * 
	 * @author Lukas Cronauer
	 */
	private String getCurrentText() {
		return getCurrentCodeArea().getText();
	}

	/**
	 * Loads content into a Tab titled fileName. If the currently selected tab in
	 * the tabPane is empty it is inserted into the existing tab. If the currently
	 * selected Tab is not empty a new tab containing the data gets created. The
	 * userData of the tab is set to the (absolute) filepath if there is one.
	 * Otherwise the userData is equals {@link EditorST.NEW_TAB_TITLE}.
	 * 
	 * @param filePath The path to the loaded file
	 * @param content  The text for the codeArea in the tab
	 * 
	 * @author Lukas Cronauer, Cedric Kapalla, Soeren Christmann, Erwin Wijaya
	 */
	public void loadTab(String filePath, String content) {
		String fileName = fileUtils.parseFileNameFromPath(filePath);
		String fileEnding = "";
		for (Tab t : tabPane.getTabs()) {
			if (t.getUserData().equals(filePath)) {
				tabPane.getSelectionModel().select(t);
				return;
			}
		}
		// find out which type the given file has
		for (String fileType : EditorST.FILE_FORMATS) {
			if (fileName.endsWith(fileType)) {
				fileEnding = fileType;
			}
		}
		// check whether the file actually has a supported type
		if (fileEnding.equals("")) {
			// need to find correct error type
			throw new IllegalAccessError();
			// potentially as an alert with return
		}

		EditorTab newTab = new EditorTab(fileName, fileEnding, content);
		newTab.setUserData(filePath);
		tabPane.getTabs().add(newTab);
		tabPane.getSelectionModel().select(newTab);
		getCurrentTab().getContent().requestFocus();
		initCountLabelItems();
	}

	/**
	 * Sets the userData and the title of the currently selected tab in the tabPane
	 * by extracting the fileName from the filePath.
	 * 
	 * @param path The filepath to a file opened in one of the tabs which did not
	 *             have a title previously
	 * @author Lukas Cronauer
	 */
	private void setCurrentTabUserData(String path) {
		getCurrentTab().setUserData(path);
		getCurrentTab().setText(fileUtils.parseFileNameFromPath(path));
	}
}
