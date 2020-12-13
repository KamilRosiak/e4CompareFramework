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

/**
 * View class containing all user interface items of our plugin. Used to
 * intialize the menu structure. Additionally several methods to interact with
 * the tabPane are provided.
 * 
 * @author Soeren Christmann, Cedric Kapalla, Lukas Cronauer, Erwin Wijaya
 *
 */
public class TextEditor implements Initializable {
	// word and row counter labels
	@FXML
	protected Label wordCount;
	@FXML
	protected Label rowCount;
	// Public ?
	Clipboard systemClipboard = Clipboard.getSystemClipboard();

	@FXML
	protected TabPane tabPane;

	@Inject
	protected ServiceContainer services;

	// Utils class to handle file operations
	protected FileUtils fileUtils;

	// Scene of this object
	private Scene scene;

	Alert alert;

	// number of new files for which the filename is not set (yet)
	protected int untitledCount = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initCountLabelItems();
		getCurrentTab().setUserData(EditorST.NEW_TAB_TITLE);
		getCurrentTab().setContent(CodeAreaFactory.createCodeArea(""));
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
	protected void initCountLabelItemAction() {
		for (Tab tab : tabPane.getTabs()) {
			CodeArea codeArea = (CodeArea) tab.getContent();
			tab.selectedProperty().addListener((ov, oldvalue, newvalue) -> {
				if (newvalue) {
					int[] labels = WordCountUtils.count(codeArea.getText());
					wordCount.setText("Words: " + labels[0]);
					rowCount.setText("Rows: " + labels[1]);
				}
			});
			codeArea.setOnKeyReleased(e -> {
				int[] labels = WordCountUtils.count(codeArea.getText());
				wordCount.setText("Words: " + labels[0]);
				rowCount.setText("Rows: " + labels[1]);
			});
		}
	}

	// supporting functions start here

	/**
	 * Saves the current content of the codeArea to a file when there are changes
	 * compared to the last saved version.
	 * 
	 * @author Lukas Cronauer
	 */
	protected void saveChanges() {
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
	protected Tab getCurrentTab() {
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
	protected CodeArea getCurrentCodeArea() {
		return (CodeArea) getCurrentTab().getContent();
	}

	/**
	 * Gets the text in the CodeArea of the currently selected Tab
	 * 
	 * @return Text of currently visible CodeArea
	 * 
	 * @author Lukas Cronauer
	 */
	protected String getCurrentText() {
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
	protected void setCurrentTabUserData(String path) {
		getCurrentTab().setUserData(path);
		getCurrentTab().setText(fileUtils.parseFileNameFromPath(path));
	}
}
