package de.tu_bs.cs.isf.e4cf.text_editor.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.inject.Inject;

import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;
import de.tu_bs.cs.isf.e4cf.text_editor.FileFormatContainer;
import de.tu_bs.cs.isf.e4cf.text_editor.WordCountUtils;
import de.tu_bs.cs.isf.e4cf.text_editor.interfaces.IFormatting;
import de.tu_bs.cs.isf.e4cf.text_editor.interfaces.IHighlighting;
import de.tu_bs.cs.isf.e4cf.text_editor.interfaces.IIndenting;
import de.tu_bs.cs.isf.e4cf.text_editor.stringtable.EditorST;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.Clipboard;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
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

	Clipboard systemClipboard = Clipboard.getSystemClipboard();

	@FXML
	protected TabPane tabPane;

	@Inject
	protected ServiceContainer services;


	private Scene scene;

	private Map<String, FileFormatContainer> fileExtensions;

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
	 * Sets up the wordcount.
	 * 
	 * @author Soeren Christmann
	 * 
	 */
	private void initCountLabelItems() {
		initCountLabelItemAction();
	}

	/**
	 * Initialises counting of words and lines.
	 * 
	 * @author Soeren Christmann, Cedric Kapalla
	 */
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
		if (RCPMessageProvider.questionMessage("Save...", "Would you like to save the changes in this file?")) {
			FileStreamUtil.writeTextToFile((String) getCurrentTab().getUserData(), content);
		}
	}

	/**
	 * Sets the Scene for this Window.
	 * 
	 * @param scene The scene that this object is part of
	 * 
	 * @author Lukas Cronauer
	 */
	public void setScene(Scene scene) {
		this.scene = scene;
		fileExtensions = getContributedFileFormats();
	}

	/**
	 * Gets the currently selected tab in the tabPane.
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
	 * Gets the currently visible CodeArea in the tabPane.
	 * 
	 * @return Currently visible CodeArea
	 * 
	 * @author Lukas Cronauer
	 */
	protected CodeArea getCurrentCodeArea() {
		return (CodeArea) getCurrentTab().getContent();
	}

	/**
	 * Gets the text in the CodeArea of the currently selected Tab.
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
		String fileName = parseFileNameFromPath(filePath);
		String fileEnding = "";
		for (Tab t : tabPane.getTabs()) {
			if (t.getUserData().equals(filePath)) {
				tabPane.getSelectionModel().select(t);
				return;
			}
		}
		// find out which type the given file has
		for (String fileType : fileExtensions.keySet()) {
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
		newTab.initDisplayActions(fileExtensions.get(fileEnding));
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
		getCurrentTab().setText(parseFileNameFromPath(path));
	}

	private Map<String, FileFormatContainer> getContributedFileFormats() {
		IConfigurationElement[] configs = RCPContentProvider.getIConfigurationElements(EditorST.EXTP_ID);
		Map<String, FileFormatContainer> fileExtensions = new HashMap<>();
		for (IConfigurationElement config : configs) {
			Object highlighter = null, indenter = null, formatter = null;
			try {
				String[] allAttributes = config.getAttributeNames();
				for (String attribute : allAttributes) {
					if (!attribute.equals(EditorST.EXTP_EXTENSION) && !attribute.equals("css")) {
						final Object extension = config.createExecutableExtension(attribute);
						if (attribute.equals(EditorST.EXTP_HIGHLIGHT) && extension instanceof IHighlighting) {
							highlighter = extension;
						} else if (attribute.equals(EditorST.EXTP_INDENT) && extension instanceof IIndenting) {
							indenter = extension;
						} else if (attribute.equals(EditorST.EXTP_FORMAT) && extension instanceof IFormatting) {
							formatter = extension;
						}
					}
					if (attribute.equals("css")) {
						String contributor = config.getContributor().getName();
						String relPath = config.getAttribute(attribute);
						String styleUrl = new URL("platform:/plugin/" + contributor + "/" + relPath).toExternalForm();
						if (styleUrl.endsWith(".css")) {
							scene.getStylesheets().add(styleUrl);
						} else {
							throw new IOException(
									"Invalid file format for css style-sheet. Must be '.css'. Contributor: "
											+ contributor);
						}

					}
				}
				String fileExtension = config.getAttribute(EditorST.EXTP_EXTENSION);
				fileExtensions.put(fileExtension, new FileFormatContainer(highlighter, indenter, formatter));
			} catch (InvalidRegistryObjectException | CoreException | IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}

		return fileExtensions;
	}

	/**
	 * Extracts the fileName from a string containing a file path.
	 * 
	 * @param path A filePath ending in a file
	 * @return The fileName with extension (e.g. name.extension)
	 * @author Lukas Cronauer
	 */
	public static String parseFileNameFromPath(String path) {
		String fileName = path;
		String[] splittedPath;
		if (!path.startsWith(EditorST.NEW_TAB_TITLE)) {
			if (System.getProperty("os.name").startsWith("Windows")) {
				splittedPath = path.split("\\\\");
			} else {
				splittedPath = path.split("/");
			}

			try {
				if (splittedPath[splittedPath.length - 1].matches(EditorST.FILE_REGEX)) {
					fileName = splittedPath[splittedPath.length - 1];
				} else {
					throw new ArrayIndexOutOfBoundsException("Invalid filename");
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println(e.getMessage());
				fileName = EditorST.NEW_TAB_TITLE;
			}
		}

		return fileName;
	}
}
