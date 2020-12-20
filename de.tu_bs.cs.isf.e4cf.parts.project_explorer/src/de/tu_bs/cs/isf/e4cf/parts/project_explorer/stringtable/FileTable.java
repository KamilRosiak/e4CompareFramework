package de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable;

import java.nio.file.Paths;

public class FileTable {
	public static final String PROJECT_EXPLORER_VIEW_FXML = Paths.get("ui", "view", "ProjectExplorerView.fxml")
			.toString();

	// Image paths
	public static String PROJECT_EXPLORER_ICONS = Paths.get("icons", "Explorer_View", "items").toString();
	public static String PROJECT_PNG = Paths.get(PROJECT_EXPLORER_ICONS, "project16.png").toString();
	public static String FOLDER_PNG = Paths.get(PROJECT_EXPLORER_ICONS, "folder16.png").toString();
	public static String XML_PNG = Paths.get(PROJECT_EXPLORER_ICONS, "xml16.png").toString();
	public static String FILE_PNG = Paths.get(PROJECT_EXPLORER_ICONS, "file16.png").toString();

	// Toolbar
	public static String PROJECT_EXPLORER_TOOLBAR = Paths.get("icons", "Explorer_View", "ToolBar").toString();
	public static String SEARCH_PNG = Paths.get(PROJECT_EXPLORER_TOOLBAR, "search16.png").toString();
	public static String X_PNG = Paths.get(PROJECT_EXPLORER_TOOLBAR, "x16.png").toString();
	public static String UNDO_PNG = Paths.get(PROJECT_EXPLORER_TOOLBAR, "undo16.png").toString();
	public static String REDO_PNG = Paths.get(PROJECT_EXPLORER_TOOLBAR, "redo16.png").toString();
	public static String DELETE_PNG = Paths.get("icons", "Explorer_View", "delete_16.png").toString();
	public static String EXPLORER_PNG = Paths.get("icons", "Explorer_View", "show_in_explorer_16.png").toString();
}
