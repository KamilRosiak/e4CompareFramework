package de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;

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
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.CustomTreeCell;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.WorkspaceStructureTemplate;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.listeners.OpenFileListener;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.listeners.ProjectExplorerKeyListener;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swt.FXCanvas;
import javafx.embed.swt.SWTFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

	// E4 Injections
	@Inject
	private ServiceContainer services;
	@Inject
	private ESelectionService _selectionService;
	@Inject
	private IEventBroker _eventBroker;
	@Inject
	private EMenuService _menuService;

	// Controller fields
	private ChangeListener<TreeItem<FileTreeElement>> changeListener;
	private WorkspaceFileSystem workspaceFileSystem;
	private Map<String, IProjectExplorerExtension> fileExtensions;

	/**
	 * This method is equivalent to the previous postContruct(), in that it sets up
	 * the project explorer component.
	 */
	public void initializeView(IEclipseContext context, WorkspaceFileSystem fileSystem, FXCanvas canvas) {

		getContributions();

		// Structure of Directories representing a Tree
		FileTreeElement treeRoot = initializeInput(fileSystem);

		TreeItem<FileTreeElement> root = buildTree(treeRoot, true, null);

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

		// Cell factory for custom tree cells
		projectTree.setCellFactory(new Callback<TreeView<FileTreeElement>, TreeCell<FileTreeElement>>() {
			@Override
			public TreeCell<FileTreeElement> call(TreeView<FileTreeElement> param) {
				TreeCell<FileTreeElement> treeCell = new CustomTreeCell(fileSystem);
				return treeCell;
			}
		});

	}

	/**
	 * Traverse the filesystem tree via dfs to generate a javafx treeview
	 * 
	 * @param parentNode The parent node
	 * @param isRoot     true if a given node is a root node
	 * @param state      maps the expanded status for each node in the tree
	 * @return a new tree item
	 */
	private TreeItem<FileTreeElement> buildTree(FileTreeElement parentNode, boolean isRoot,
			HashMap<String, Boolean> state) {

		Node imgNode = isRoot ? null : getImage(parentNode);

		TreeItem<FileTreeElement> currentNode = new TreeItem<FileTreeElement>(parentNode, imgNode);
		if (state != null && state.containsKey(currentNode.getValue().getAbsolutePath())) {
			currentNode.setExpanded(state.get(currentNode.getValue().getAbsolutePath()));
		}

		for (FileTreeElement child : parentNode.getChildren()) {
			TreeItem<FileTreeElement> node = buildTree(child, false, state);
			currentNode.getChildren().add(node);
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

	/**
	 * Rebuild the project explorer and update it's view.
	 * 
	 */
	@Inject
	@Optional
	public void refresh(@UIEventTopic(E4CEventTable.EVENT_REFRESH_PROJECT_VIEWER) Object o) {
		HashMap<String, Boolean> oldTreeState = new HashMap<String, Boolean>();
		traverseTree(projectTree.getRoot(), oldTreeState);

		projectTree.getSelectionModel().selectedItemProperty().removeListener(changeListener);
		TreeItem<FileTreeElement> root = buildTree(projectTree.getRoot().getValue(), true, oldTreeState);

		projectTree.setRoot(root);
		projectTree.setShowRoot(false);
		projectTree.getSelectionModel().selectedItemProperty().addListener(changeListener);
	}

	/**
	 * Traverse the filesystem tree via dfs to save the old state of each node
	 * 
	 * @param parentNode The parent node
	 * @param state      maps the expanded status for each node in the tree
	 */
	private void traverseTree(TreeItem<FileTreeElement> parentNode, HashMap<String, Boolean> state) {

		/*
		 * put current element in hashmap with state
		 */
		state.put(parentNode.getValue().getAbsolutePath(), parentNode.isExpanded());

		for (TreeItem<FileTreeElement> child : parentNode.getChildren()) {
			traverseTree(child, state);
		}
	}

	/**
	 * Sets up the selection service via a ChangeListener on the projectTree
	 */
	private void setupSelectionService() {
		// Set no initial selection
		StructuredSelection structuredSelection = new StructuredSelection(
				services.workspaceFileSystem.getWorkspaceDirectory());
		_selectionService.setSelection(null);

		// Add a SelectionListener to tree to propagate the selection that is done in
		// the tree

		changeListener = new ChangeListener<TreeItem<FileTreeElement>>() {
			@Override
			public void changed(ObservableValue<? extends TreeItem<FileTreeElement>> observable,
					TreeItem<FileTreeElement> oldValue, TreeItem<FileTreeElement> newValue) {
				_selectionService.setSelection(new StructuredSelection(newValue.getValue()));
				_eventBroker.send(E4CEventTable.SELECTION_CHANGED_EVENT, structuredSelection);
			}
		};

		projectTree.getSelectionModel().selectedItemProperty().addListener(changeListener);
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
