package de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable;

public class FileTable {
	private static final String PROJECT_EXPLORER_VIEWS = "de/tu_bs/cs/isf/e4cf/parts/project_explorer/controller/";

	public static final String PROJECT_EXPLORER_VIEW_FXML = PROJECT_EXPLORER_VIEWS + "ProjectExplorerView.fxml";
	public static final String COPY_DIRECTORY_PAGE_FXML = PROJECT_EXPLORER_VIEWS + "CopyDirectoryView.fxml";
	public static final String NEW_FILE_PAGE_VIEW = PROJECT_EXPLORER_VIEWS + "NewFileView.fxml";
	public static final String CUSTOM_TREE_CELL_FXML = PROJECT_EXPLORER_VIEWS + "CustomTreeCellView.fxml";

	public static final String ADD_TAG_FXML = PROJECT_EXPLORER_VIEWS + "TagView.fxml";
	public static final String TAG_LIST_CELL = PROJECT_EXPLORER_VIEWS + "TagListCell.fxml";
	
	
	private static final String ICON_LOCATION = "icons/";
	// item icon paths
	private static final String ITEM_ICON_LOCATION = ICON_LOCATION + "items/";
	public static String PROJECT_PNG = ITEM_ICON_LOCATION + "project16.png";
	public static String FOLDER_PNG = ITEM_ICON_LOCATION + "folder16.png";
	public static String NEWFOLDER_PNG = ITEM_ICON_LOCATION + "newfolder16.png";
	public static String XML_PNG = ITEM_ICON_LOCATION + "xml16.png";
	public static String FILE_PNG = ITEM_ICON_LOCATION + "file16.png";
	public static String NEWFILE_PNG = ITEM_ICON_LOCATION + "newfile16.png";
	public static String TAG_PNG = ITEM_ICON_LOCATION + "tag_16.png";

	// toolbar icons
	private static final String TOOLBAR_ICON_LOCATION = ICON_LOCATION + "ToolBar/";
	public static String SEARCH_PNG = TOOLBAR_ICON_LOCATION + "search16.png";
	public static String X_PNG = TOOLBAR_ICON_LOCATION + "x16.png";
	public static String FLAT_VIEW_PNG = TOOLBAR_ICON_LOCATION + "flatview16.png";
	public static String HIERARCICAL_VIEW_PNG = TOOLBAR_ICON_LOCATION + "hierview16.png";
	public static String DELETE_PNG = ICON_LOCATION + "delete_16.png";
	public static String EXPLORER_PNG = ICON_LOCATION + "show_in_explorer_16.png";
}
