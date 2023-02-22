package de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable;

import java.nio.file.Paths;

public class FileTable {
	public static final String PROJECT_EXPLORER_VIEWS = "de/tu_bs/cs/isf/e4cf/parts/project_explorer/controller/";

	public static final String PROJECT_EXPLORER_VIEW_FXML = PROJECT_EXPLORER_VIEWS + "ProjectExplorerView.fxml";
	public static final String COPY_DIRECTORY_PAGE_FXML = PROJECT_EXPLORER_VIEWS + "CopyDirectoryView.fxml";
	public static final String NEW_FILE_PAGE_VIEW = PROJECT_EXPLORER_VIEWS + "NewFileView.fxml";
	public static final String CUSTOM_TREE_CELL_FXML = PROJECT_EXPLORER_VIEWS + "CustomTreeCellView.fxml";

	public static final String ADD_TAG_FXML = PROJECT_EXPLORER_VIEWS + "TagView.fxml";

	public static final String TAG_LIST_CELL = PROJECT_EXPLORER_VIEWS + "TagListCell.fxml";

	// Image paths
	public static String PROJECT_EXPLORER = Paths.get("icons", "Explorer_View").toString();
	public static String PROJECT_EXPLORER_ICONS = Paths.get(PROJECT_EXPLORER, "items").toString();
	public static String PROJECT_PNG = Paths.get(PROJECT_EXPLORER_ICONS, "project16.png").toString();
	public static String FOLDER_PNG = Paths.get(PROJECT_EXPLORER_ICONS, "folder16.png").toString();
	public static String NEWFOLDER_PNG = Paths.get(PROJECT_EXPLORER_ICONS, "newfolder16.png").toString();
	public static String XML_PNG = Paths.get(PROJECT_EXPLORER_ICONS, "xml16.png").toString();
	public static String FILE_PNG = Paths.get(PROJECT_EXPLORER_ICONS, "file16.png").toString();
	public static String NEWFILE_PNG = Paths.get(PROJECT_EXPLORER_ICONS, "newfile16.png").toString();
	public static String TAG_PNG = Paths.get(PROJECT_EXPLORER_ICONS, "tag_16.png").toString();

	// Toolbar
	private static final String TOOLBAR_ICON_LOCATION = "icons/ToolBar/";
	public static String PROJECT_EXPLORER_TOOLBAR = Paths.get(PROJECT_EXPLORER, "ToolBar").toString();
	public static String SEARCH_PNG = TOOLBAR_ICON_LOCATION + "search16.png";
	public static String X_PNG = TOOLBAR_ICON_LOCATION + "x16.png";
	public static String FLAT_VIEW_PNG = Paths.get(PROJECT_EXPLORER_TOOLBAR, "flatview16.png").toString();
	public static String HIERARCICAL_VIEW_PNG = Paths.get(PROJECT_EXPLORER_TOOLBAR, "hierview16.png").toString();
	public static String DELETE_PNG = Paths.get(PROJECT_EXPLORER, "delete_16.png").toString();
	public static String EXPLORER_PNG = Paths.get(PROJECT_EXPLORER, "show_in_explorer_16.png").toString();
}
