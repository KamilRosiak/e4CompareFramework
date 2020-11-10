package de.tu_bs.cs.isf.e4cf.parts.project_explorer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.WorkspaceFileSystem;
import de.tu_bs.cs.isf.e4cf.core.file_structure.tree.sync.TreeSynchronization;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers.NewFolderHandler;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.view.ProjectExplorerView;
import javafx.embed.swt.FXCanvas;
import javafx.embed.swt.SWTFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

public class ProjectExplorerViewController {
	private static final String PROJECT_EXPLORER_VIEW_FXML = "/ui/view/ProjectExplorerView.fxml";

	private ProjectExplorerView view;

	@Inject
	ServiceContainer services;

	private WorkspaceFileSystem workspaceFileSystem;
	private Map<String, IProjectExplorerExtension> fileExtensions;

	@PostConstruct
	public void postConstruct(Composite parent, IEclipseContext context, WorkspaceFileSystem fileSystem)
			throws IOException {
		FXCanvas canvans = new FXCanvas(parent, SWT.None);
		FXMLLoader<ProjectExplorerView> loader = new FXMLLoader<ProjectExplorerView>(context, StringTable.BUNDLE_NAME,
				PROJECT_EXPLORER_VIEW_FXML);
		view = loader.getController();

		getContributions();

		// Structure of Directories representing a Tree
		FileTreeElement treeRoot = initializeInput(fileSystem);

		TreeItem<FileTreeElement> root = buildTree(treeRoot, true);

		view.projectTree.setRoot(root);
		view.projectTree.setShowRoot(false);

		EventHandler<ActionEvent> action = contextHandler();
		view.ctxNewFolder.setOnAction(action);

		Scene scene = new Scene(loader.getNode());
		canvans.setScene(scene);
	}

	/** Traverses the given directory tree recursively DFS */
	private TreeItem<FileTreeElement> buildTree(FileTreeElement parentNode, boolean isRoot) {

		Node imgNode = isRoot ? null : getImage(parentNode);

		TreeItem<FileTreeElement> currentNode = new TreeItem<FileTreeElement>(parentNode, imgNode);

		for (FileTreeElement child : parentNode.getChildren()) {
			TreeItem<FileTreeElement> node = buildTree(child, false);
			currentNode.getChildren().add(node);
		}

		return currentNode;
	}

	public Node getImage(Object element) {
		Image image = null;

		if (element instanceof FileTreeElement) {
			FileTreeElement fileElement = (FileTreeElement) element;
			if (workspaceFileSystem.isProject(fileElement)) {
				image = services.imageService.getImage(null, "icons/Explorer_View/items/project16.png");
			} else if (fileElement.isDirectory()) {
				image = services.imageService.getImage(null, "icons/Explorer_View/items/folder16.png");
			} else {
				String fileExtension = fileElement.getExtension();
				// load extended file icons
				if (fileExtensions.containsKey(fileExtension)) {
					image = fileExtensions.get(fileExtension).getIcon(services.imageService);
				} else if (fileExtension.equals(E4CStringTable.FILE_ENDING_XML)) {
					image = services.imageService.getImage(null, "icons/Explorer_View/items/xml16.png");
				} else {
					// default file icon
					image = services.imageService.getImage(null, "icons/Explorer_View/items/file16.png");
				}
				;
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

	private FileTreeElement initializeInput(WorkspaceFileSystem fileSystem) {
		workspaceFileSystem = fileSystem;
		workspaceFileSystem.initializeFileTree(RCPContentProvider.getCurrentWorkspacePath());
		new TreeSynchronization(workspaceFileSystem);

		return workspaceFileSystem.getWorkspaceDirectory();
	}

	private EventHandler<ActionEvent> contextHandler() {
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				MenuItem mItem = (MenuItem) event.getSource();
				String value = mItem.getText();
				if ("New Folder".equalsIgnoreCase(value)) {
					NewFolderHandler handler = new NewFolderHandler();
					if (handler.canExecute(services.rcpSelectionService)) {
						handler.execute(services.shell, services.dialogService, services.rcpSelectionService,
								services.imageService, services.workspaceFileSystem);
					} else {
						System.out.println("no sel");
					}
				}
			}
		};
	}
}
