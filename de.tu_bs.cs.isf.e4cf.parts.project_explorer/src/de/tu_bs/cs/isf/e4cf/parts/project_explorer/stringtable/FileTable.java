package de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable;

import java.nio.file.Paths;

public class FileTable {
	public static final String PROJECT_EXPLORER_VIEW_FXML = Paths.get("ui", "view", "ProjectExplorerView.fxml")
			.toString();

	// Image paths
	public static String PROJECT_EXPLORER = Paths.get("icons", "Explorer_View").toString();
	public static String PROJECT_EXPLORER_ICONS = Paths.get(PROJECT_EXPLORER, "items").toString();
	public static String PROJECT_PNG = Paths.get(PROJECT_EXPLORER_ICONS, "project16.png").toString();
	public static String FOLDER_PNG = Paths.get(PROJECT_EXPLORER_ICONS, "folder16.png").toString();
	public static String XML_PNG = Paths.get(PROJECT_EXPLORER_ICONS, "xml16.png").toString();
	public static String FILE_PNG = Paths.get(PROJECT_EXPLORER_ICONS, "file16.png").toString();

	// Toolbar
	public static String PROJECT_EXPLORER_TOOLBAR = Paths.get(PROJECT_EXPLORER, "ToolBar").toString();
	public static String SEARCH_PNG = Paths.get(PROJECT_EXPLORER_TOOLBAR, "search16.png").toString();
	public static String X_PNG = Paths.get(PROJECT_EXPLORER_TOOLBAR, "x16.png").toString();
	public static String FLAT_VIEW_PNG = Paths.get(PROJECT_EXPLORER_TOOLBAR, "flatview16.png").toString();
	public static String HIERARCICAL_VIEW_PNG = Paths.get(PROJECT_EXPLORER_TOOLBAR, "hierview16.png").toString();
	public static String DELETE_PNG = Paths.get(PROJECT_EXPLORER, "delete_16.png").toString();
	public static String EXPLORER_PNG = Paths.get(PROJECT_EXPLORER, "show_in_explorer_16.png").toString();
}
