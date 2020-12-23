package de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable;

import java.nio.file.Paths;

public class FileTable {
	public static final String PROJECT_EXPLORER_VIEW_FXML = Paths.get("ui", "view", "ProjectExplorerView.fxml")
			.toString();
	public static final String PROJECT_EXPLORER_WIZARD_FXML = Paths.get("ui", "view", "FirstPageVie.fxml").toString();

	public static final String COPY_DIRECTORY_PAGE_FXML = Paths.get("ui", "view", "CopyDirectoryView.fxml").toString();
	public static final String RECURSIVE_COPY_OPTIONS_PAGE_FXML = Paths
			.get("ui", "view", "RecursiveCopyOptionsView.fxml").toString();

	// Image paths
	public static String PROJECT_EXPLORER_ICONS = Paths.get("icons", "Explorer_View", "items").toString();
	public static String PROJECT_PNG = Paths.get(PROJECT_EXPLORER_ICONS, "project16.png").toString();
	public static String FOLDER_PNG = Paths.get(PROJECT_EXPLORER_ICONS, "folder16.png").toString();
	public static String XML_PNG = Paths.get(PROJECT_EXPLORER_ICONS, "xml16.png").toString();
	public static String FILE_PNG = Paths.get(PROJECT_EXPLORER_ICONS, "file16.png").toString();
}
