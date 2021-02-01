package de.tu_bs.cs.isf.e4cf.text_editor.view;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;
import de.tu_bs.cs.isf.e4cf.text_editor.stringtable.EditorST;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.stage.Stage;

import org.fxmisc.richtext.CodeArea;

/**
 * The Class initialize the Main Menu of the Texteditor. Contains all menu items
 * and their functions.
 * 
 * @author Lukas Cronauer, Erwin Wijaya, Cedric Kapalla, Soeren Christmann
 *
 */
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
	private MenuItem find;

	// Help Menu
	@FXML
	private MenuItem about;

	@FXML
	public TextEditor textEditorViewController;

	public Scene scene;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createNewFileItems(getContributedFileFormats());

	}
	
	/**
	 * Adds all file extensions of contributing extensions for the
	 * 'file_format' extension point to a list
	 * (e.g. java, txt, xml, ...)
	 * 
	 * @return List with all registered file extensions
	 */
	private List<String> getContributedFileFormats() {
		IConfigurationElement[] configs = RCPContentProvider
				.getIConfigurationElements("de.tu_bs.cs.isf.e4cf.text_editor.file_format");
		List<String> fileExtensions = new ArrayList<>();
		for (IConfigurationElement config : configs) {
			try {
				String fileExtension = config.getAttribute("file_extension");
				fileExtensions.add(fileExtension);
			} catch (InvalidRegistryObjectException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}

		return fileExtensions;
	}
	
	/**
	 * Sets the scene for the TextEditorMenu
	 * 
	 * @param scene Scene object for the Object
	 */
	public void setScene(Scene scene) {
		this.scene = scene;
		textEditorViewController.setScene(scene);
	}

	/**
	 * Is called upon creating a new file and adds submenu-items to the "New"-menu
	 * item.
	 * 
	 * @author Soeren Christmann, Lukas Cronauer
	 * 
	 */
	public void createNewFileItems(List<String> formats) {
		for (String fileType : formats) {
			MenuItem menu = new MenuItem(fileType.toUpperCase() + "-File");
			menu.setOnAction(e -> {
				for (Tab t : textEditorViewController.tabPane.getTabs()) {
					if (t.getUserData().toString()
							.startsWith(EditorST.NEW_TAB_TITLE + textEditorViewController.untitledCount)) {
						textEditorViewController.untitledCount++;
					}
				}
				textEditorViewController.saveChanges();
				textEditorViewController
						.loadTab(EditorST.NEW_TAB_TITLE + textEditorViewController.untitledCount + "." + fileType, "");
				textEditorViewController.getCurrentTab().getContent().requestFocus();
			});
			newFile.getItems().addAll(menu);
		}
	}

	/**
	 * Sets the actions of the Open item in the File menu. Retrieves the file's Path,
	 * then reads it with a StringBuilder. The charset used is ISO_8859_1
	 * because UTF_8 is unable to parse umlauts and crashes whenever one is in a
	 * to-be-opened file.
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya, Cedric Kapalla, Soeren Christmann
	 */
	@FXML
	private void openFileAction() {
		String content = "";
		String filePath = RCPMessageProvider.getFilePathDialog(EditorST.OPEN_FILE_DIALOG,
				RCPContentProvider.getCurrentWorkspacePath());
		if (!(filePath.equals(""))) {
			StringBuilder contentBuilder = new StringBuilder();

			try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.ISO_8859_1)) {
				stream.forEach(s -> contentBuilder.append(s).append("\n"));
				content = contentBuilder.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
			textEditorViewController.loadTab(filePath, content);
		}
	}

	/**
	 * Sets the actions of the Save item in the File menu. Saving the text into
	 * current File.
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	@FXML
	private void saveAction() {
		String filePath = (String) textEditorViewController.getCurrentTab().getUserData();
		if (filePath.startsWith(EditorST.NEW_TAB_TITLE)) {
			saveAsAction();
		} else {
			FileStreamUtil.writeTextToFile(filePath, textEditorViewController.getCurrentText());
		}
	}

	/**
	 * Sets actions of the SaveAs item in the File menu. Creates copy of file
	 * in another file directory, or creates copy of the file with another name
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya
	 */
	@FXML
	private void saveAsAction() {
		final FileDialog dialog = new FileDialog(new Shell(), SWT.SAVE);
		dialog.setText(EditorST.SAVE_AS_FILE_DIALOG);
		dialog.setFilterPath(RCPContentProvider.getCurrentWorkspacePath());
		String filePath = dialog.open();
		if (filePath != null) {
			FileStreamUtil.writeTextToFile(filePath, textEditorViewController.getCurrentText());
			textEditorViewController.setCurrentTabUserData(filePath);
		}
	}

	/**
	 * Sets the actions of the CloseItem item in the File menu. Closing the File
	 * that are currently open.
	 * 
	 * @author Lukas Cronauer, Erwin Wijaya, Cedric Kapalla, Soeren Christmann
	 */
	@FXML
	private void closeFileAction() {
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
	private void closeEditorAction() {
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
	private void undoAction() {
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
	private void redoAction() {
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
	private void cutAction() {
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
	private void copyAction() {
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
	private void pasteAction() {
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
	private void deleteAction() {
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
	private void selectAllAction() {
		CodeArea codeArea = textEditorViewController.getCurrentCodeArea();
		codeArea.requestFocus();
		codeArea.selectAll();
	}

	/**
	 * Calls a separate window used to find terms in the CodeArea. Makes use of the
	 * open()-function implemented in FindOperation.java
	 * 
	 * @author Cedric Kapalla, Soeren Christmann
	 */
	@FXML
	private void findAction() throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource(EditorST.FIND_OPERATION_FXML));
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage = new Stage();
			stage.setTitle("Find");
			FindOperation controller = fxmlLoader.getController();
			controller.setTextEditor(textEditorViewController);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Sets the actions of the About item in the Help menu. Currently displays
	 * creators and project information.
	 * 
	 * @author Cedric Kapalla, Soeren Christmann
	 */
	@FXML
	private void aboutAction() {
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
