package de.tu_bs.cs.isf.e4cf.text_editor.stringtable;

/**
 * StringTable for the TextEditor plugin
 *
 * @author Cedric Kapalla, Soeren Christmann, Lukas Cronauer, Erwin Wijaya
 *
 */
public class EditorST {
	public static final String FILE_OPENED = "OPEN_FILE";

	public static final String TEXT_EDITOR_VIEW_FXML = "/ui/view/TextEditorView.fxml";
	public static final String TEXT_EDITOR_MENU_VIEW_FXML = "/ui/view/TextEditorMenuView.fxml";
	public static final String BUNDLE_NAME = "de.tu_bs.cs.isf.e4cf.text_editor";
	public static final String TEXT_EDITOR_FXML_ID = "de.tu-bs.cs.isf.e4cf.text_editor.part.view";
	public static final String NEW_TAB_TITLE = "untitled";

	public static final String[] FILE_FORMATS = { "java", "txt", "xml" };
	public static final String FILE_REGEX = ".+\\.\\w*";
	public static final String FILE_NAME_CHOSEN = "NEW_FILE_NAME";

}