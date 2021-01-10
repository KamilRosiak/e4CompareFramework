package de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.WorkspaceFileSystem;
import de.tu_bs.cs.isf.e4cf.core.file_structure.tree.sync.TreeSynchronization;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileEventLog;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CEventTable;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.extension_points.ExtensionAttrUtil;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.CustomTreeCell;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.DropElement;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.FileImageProvider;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.WorkspaceStructureTemplate;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.listeners.OpenFileListener;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.listeners.ProjectExplorerKeyListener;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.drop_files.DropFilesDialog;
import javafx.collections.ListChangeListener;
import javafx.embed.swt.FXCanvas;
import javafx.embed.swt.SWTFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * Class representing the JavaFX ViewController of the project explorer
 */
public class ProjectExplorerViewController {

	// Fields dependency injected from the ProjectExplorerView.fxml
	@FXML
	public TreeView<FileTreeElement> projectTree;
	@FXML
	public ToolBar projectToolbar;

	// E4 Injections
	@Inject
	private ServiceContainer services;
	@Inject
	private ESelectionService _selectionService;
	@Inject
	private IEventBroker _eventBroker;
	@Inject
	private EMenuService _menuService;

	@Inject
	private RCPImageService imageService;

	@Inject
	@Named(IServiceConstants.ACTIVE_SHELL)
	private Shell _shell;

	@Inject
	IEclipseContext context;

	// Controller fields

	private ListChangeListener<TreeItem<FileTreeElement>> changeListener;
	private WorkspaceFileSystem workspaceFileSystem;
	private Map<String, IProjectExplorerExtension> fileExtensions;
	private ProjectExplorerToolBarController toolbarController;

	private String filter = "";
	private boolean isFlatView = false;
	private HashMap<String, Boolean> expansionState = new HashMap<String, Boolean>();

	/**
	 * This method is equivalent to the previous postContruct(), in that it sets up
	 * the project explorer component.
	 */
	public void initializeView(IEclipseContext context, WorkspaceFileSystem fileSystem, FXCanvas canvas) {

		getContributions();

		// Structure of Directories representing a Tree
		FileTreeElement treeRoot = initializeInput(fileSystem);

		TreeItem<FileTreeElement> root = buildTree(treeRoot);

		projectTree.setRoot(root);
		projectTree.setShowRoot(false);

		// Register the SWT Context menu on the canvas
		_menuService.registerContextMenu(canvas, StringTable.PROJECT_EXPLORER_CONTEXT_MENU_ID);

		setupSelectionService();
		// setup initial workspace structure
		createWorkspaceFromExtension();

		// Adding listeners to the scene
		canvas.getScene().setOnKeyPressed(new ProjectExplorerKeyListener(context));
		canvas.getScene().addEventFilter(MouseEvent.MOUSE_CLICKED,
				new OpenFileListener(context, fileExtensions, services));

		FileImageProvider fileImageProvider = new FileImageProvider(services, fileExtensions);

		// Cell factory for custom tree cells
		projectTree.setEditable(true);

		projectTree.setCellFactory(new Callback<TreeView<FileTreeElement>, TreeCell<FileTreeElement>>() {

			@Override
			public TreeCell<FileTreeElement> call(TreeView<FileTreeElement> param) {
				TreeCell<FileTreeElement> treeCell = new CustomTreeCell(fileSystem, fileImageProvider, services);
				return treeCell;
			}
		});

		// Handoff Toolbar
		toolbarController = new ProjectExplorerToolBarController(projectToolbar, context, services,
				canvas.getParent().getShell());

	}

	/**
	 * Calls the correct tree building function depending on the project explorer
	 * state (isFlatView)
	 */
	private TreeItem<FileTreeElement> buildTree(FileTreeElement node) {

		TreeItem<FileTreeElement> rootNode = null;

		if (isFlatView) {
			rootNode = buildFlatTree(node, true, null);
			rootNode.getChildren().sort(Comparator.comparing(t -> t.toString()));
		} else {
			rootNode = buildHierachialTree(node, true);
		}

		return rootNode;
	}

	/**
	 * Walks the (sub)-tree from and applies selection to the selection model if
	 * required.
	 * 
	 * @param currentItem start point for tree traversal
	 * @param selections  A list of previously selected FileTreeElements
	 */
	private void setSelections(TreeItem<FileTreeElement> currentItem, List<FileTreeElement> selections) {

		for (TreeItem<FileTreeElement> child : currentItem.getChildren()) {
			setSelections(child, selections);
		}

		if (selections.contains(currentItem.getValue())) {
			projectTree.getSelectionModel().select(currentItem);
		}

	}

	/**
	 * Traverse the filesystem tree via dfs to generate a javafx treeview
	 * 
	 * @param parentNode The parent node
	 * @param isRoot     true if a given node is a root node
	 * @return a new tree item
	 */
	private TreeItem<FileTreeElement> buildHierachialTree(FileTreeElement parentNode, boolean isRoot) {

		Node imgNode = isRoot ? null : getImage(parentNode);

		parentNode.setDisplayLongPath(false);
		TreeItem<FileTreeElement> currentNode = new TreeItem<FileTreeElement>(parentNode, imgNode);

		// Update our tree expansion state when a node's expansion state changes
		currentNode.expandedProperty().addListener(changeListener -> {
			expansionState.put(currentNode.getValue().getAbsolutePath(), currentNode.isExpanded());
		});

		// Apply previous tree expansion state
		if (expansionState != null && expansionState.containsKey(currentNode.getValue().getAbsolutePath())) {
			currentNode.setExpanded(expansionState.get(currentNode.getValue().getAbsolutePath()));
		}

		for (FileTreeElement child : parentNode.getChildren()) {
			TreeItem<FileTreeElement> node = buildHierachialTree(child, false);
			if (filter.equals("") || node.getValue().getRelativePath().contains(filter)
					|| !node.getChildren().isEmpty())
				currentNode.getChildren().add(node);
		}

		return currentNode;
	}

	/**
	 * Traverse the filesystem tree via dfs to generate a javafx flat treeview
	 * 
	 * @param parentNode The parent node
	 * @param isRoot     true if a given node is a root node
	 * @param rootNode   the root node that holds all the children
	 * @return a new tree item
	 */
	private TreeItem<FileTreeElement> buildFlatTree(FileTreeElement parentNode, boolean isRoot,
			TreeItem<FileTreeElement> rootNode) {

		Node imgNode = isRoot ? null : getImage(parentNode);

		parentNode.setDisplayLongPath(true);
		TreeItem<FileTreeElement> currentNode = new TreeItem<FileTreeElement>(parentNode, imgNode);

		if (isRoot) {
			rootNode = currentNode;
		}

		for (FileTreeElement child : parentNode.getChildren()) {
			TreeItem<FileTreeElement> node = buildFlatTree(child, false, rootNode);
			if (filter.equals("") || node.getValue().getRelativePath().contains(filter)
					|| !node.getChildren().isEmpty())
				rootNode.getChildren().add(node);
		}

		return currentNode;
	}

	/**
	 * Returns an appropriate image for a given tree element
	 * 
	 * @param element tree element
	 * @return an image
	 */
	public Node getImage(Object element) {
		Image image = null;

		if (element instanceof FileTreeElement) {
			FileTreeElement fileElement = (FileTreeElement) element;
			if (workspaceFileSystem.isProject(fileElement)) {
				image = services.imageService.getImage(null, FileTable.PROJECT_PNG);
			} else if (fileElement.isDirectory()) {
				image = services.imageService.getImage(null, FileTable.FOLDER_PNG);
			} else {
				String fileExtension = fileElement.getExtension();
				// load extended file icons
				if (fileExtensions.containsKey(fileExtension)) {
					image = fileExtensions.get(fileExtension).getIcon(services.imageService);
				} else if (fileExtension.equals(E4CStringTable.FILE_ENDING_XML)) {
					image = services.imageService.getImage(null, FileTable.XML_PNG);
				} else {
					// default file icon
					image = services.imageService.getImage(null, FileTable.FILE_PNG);
				}
			}
		}

		WritableImage fxImage = SWTFXUtils.toFXImage(image.getImageData(), null);

		return new ImageView(fxImage);
	}

	/**
	 * Gets the file extension and the attribute from the Project explorer extension
	 * point.
	 */
	private void getContributions() {
		IConfigurationElement[] configs = RCPContentProvider
				.getIConfigurationElements(StringTable.PROJECT_EXPLORER_FILE_EXTENSION_POINT);
		fileExtensions = new HashMap<String, IProjectExplorerExtension>();
		for (IConfigurationElement config : configs) {
			try {
				IProjectExplorerExtension attr = (IProjectExplorerExtension) config
						.createExecutableExtension(StringTable.FILE_EXT_ATTR);
				String fileExtension = config.getAttribute(StringTable.FILE_EXT);
				fileExtensions.put(fileExtension, attr);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Initializes the file tree and creates tree sync.
	 * 
	 * @param fileSystem the filesystem
	 * @return the root of the filesystem
	 */
	private FileTreeElement initializeInput(WorkspaceFileSystem fileSystem) {
		workspaceFileSystem = fileSystem;
		workspaceFileSystem.initializeFileTree(RCPContentProvider.getCurrentWorkspacePath());
		new TreeSynchronization(workspaceFileSystem);

		return workspaceFileSystem.getWorkspaceDirectory();
	}

	/** Rebuild the project explorer and update it's view. */
	@Inject
	@Optional
	public void refresh(@UIEventTopic(E4CEventTable.EVENT_REFRESH_PROJECT_VIEWER) Object o) {

		// Store list of current selections before the tree is destroyed and rebuilt
		List<FileTreeElement> selections = services.rcpSelectionService.getCurrentSelectionsFromExplorer();

		TreeItem<FileTreeElement> root = buildTree(projectTree.getRoot().getValue());

		projectTree.setRoot(root);
		projectTree.setShowRoot(false);

		// re-apply selections
		setSelections(root, selections);

	}

	/** Subscribing on the rename event */
	@Inject
	@Optional
	public void rename(@UIEventTopic(E4CEventTable.EVENT_RENAME_PROJECT_EXPLORER_ITEM) Object o) {
		projectTree.edit(projectTree.getSelectionModel().getSelectedItem());
	}

	/** Subscribing on filter / search change */
	@Inject
	@Optional
	public void filter(@UIEventTopic(E4CEventTable.EVENT_FILTER_CHANGED) Object o) {
		if (o instanceof String) {
			filter = (String) o;
		} else {
			filter = "";
		}
		services.eventBroker.send(E4CEventTable.EVENT_REFRESH_PROJECT_VIEWER, null);
	}

	/** Subscribing on toggling hierarchical / flat view */
	@Inject
	@Optional
	public void toggleView(@UIEventTopic(E4CEventTable.EVENT_VIEW_TOGGLE) Object o) {
		isFlatView = !isFlatView;
		services.eventBroker.send(E4CEventTable.EVENT_REFRESH_PROJECT_VIEWER, null);
	}

	/** Subscribing on the drop element event */
	@Inject
	@Optional
	public void dropElements(@UIEventTopic(E4CEventTable.EVENT_DROP_ELEMENT_IN_EXPLORER) Object o) {
		if (o instanceof DropElement) {
			DropFilesDialog dialog = new DropFilesDialog(context, (DropElement) o, imageService);
			dialog.open();
		}
	}

	/** Sets up the selection service via a ChangeListener on the projectTree */
	private void setupSelectionService() {
		// Set no initial selection
		StructuredSelection structuredSelection = new StructuredSelection(
				services.workspaceFileSystem.getWorkspaceDirectory());
		_selectionService.setSelection(null);

		// Set Selection Mode
		projectTree.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// Add a SelectionListener to tree to propagate the selection that is done in
		// the tree

		changeListener = new ListChangeListener<TreeItem<FileTreeElement>>() {
			@Override
			public void onChanged(Change<? extends TreeItem<FileTreeElement>> change) {

				StructuredSelection selection = new StructuredSelection(
						projectTree.getSelectionModel().getSelectedItems());

				_selectionService.setSelection(selection);
				_eventBroker.send(E4CEventTable.SELECTION_CHANGED_EVENT, structuredSelection);
				toolbarController.update();
			}

		};

		projectTree.getSelectionModel().getSelectedItems().addListener(changeListener);
	}

	/**
	 * This method creates the workspace based on the
	 * PROJECT_EXPLORER_WORKSPACE_STRUCUTRE extension. If not available, no
	 * directories are added initially.
	 */
	private void createWorkspaceFromExtension() {
		List<WorkspaceStructureTemplate> workspaceExtension = ExtensionAttrUtil.getAttrsFromExtension(
				StringTable.PROJECT_EXPLORER_WORKSPACE_STRUCUTRE, StringTable.WORKSPACE_STR_ATTR);
		FileTreeElement root = workspaceFileSystem.getWorkspaceDirectory();
		Path rootPath = FileHandlingUtility.getPath(root);
		Path projectPath = rootPath.resolve("");

		for (WorkspaceStructureTemplate extension : workspaceExtension) {
			for (String directory : extension.getDirectories()) {
				try {
					if (!Files.exists(projectPath.resolve(directory))) {
						FileEventLog.getInstance().insertEvent("Create Directory", projectPath.resolve(directory));
						Files.createDirectory(projectPath.resolve(directory));
					}
				} catch (IOException e) {
					e.printStackTrace();
					try {
						FileHandlingUtility.delete(projectPath);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		refresh(null);
	}

}
