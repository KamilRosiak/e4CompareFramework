package de.tu_bs.cs.isf.e4cf.text_editor.stringtable;

/**
 * StringTable for the TextEditor plugin.
 *
 * @author Cedric Kapalla, Soeren Christmann, Lukas Cronauer, Erwin Wijaya
 *
 */
public class EditorST {
	public static final String FILE_OPENED = "OPEN_FILE";

	public static final String TEXT_EDITOR_VIEW_FXML = "/ui/view/TextEditorView.fxml";
	public static final String TEXT_EDITOR_MENU_VIEW_FXML = "/ui/view/TextEditorMenuView.fxml";
	public static final String FIND_OPERATION_FXML = "/ui/view/FindOperationView.fxml";
	public static final String BUNDLE_NAME = "de.tu_bs.cs.isf.e4cf.text_editor";
	public static final String TEXT_EDITOR_FXML_ID = "de.tu-bs.cs.isf.e4cf.text_editor.part.view";
	public static final String NEW_TAB_TITLE = "untitled";
	public static final String OPEN_FILE_DIALOG = "Open";
	public static final String SAVE_AS_FILE_DIALOG = "Save as";

	public static final String FILE_REGEX = ".+\\.\\w*";
	public static final String FILE_NAME_CHOSEN = "NEW_FILE_NAME";
	
	// extension point strings
	public static final String EXTP_ID = "de.tu_bs.cs.isf.e4cf.text_editor.file_format";
	public static final String EXTP_HIGHLIGHT = "highlighting";
	public static final String EXTP_INDENT = "indentation";
	public static final String EXTP_FORMAT = "formatting";
	public static final String EXTP_EXTENSION = "file_extension";
	public static final String EXTP_STYLE = "css";
	
	//icons
	public static final String JAVA_ICON = "icons/java-icon.png";
	public static final String XML_ICON = "icons/xml-icon.png";
	public static final String TXT_ICON = "icons/txt-icon.png";
}
