package de.tu_bs.cs.isf.e4cf.parts.project_explorer;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.WorkspaceFileSystem;
import de.tu_bs.cs.isf.e4cf.core.file_structure.tree.sync.TreeSynchronization;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers.NewFolderHandler;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.view.ProjectExplorerView;
import javafx.embed.swt.FXCanvas;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;

public class ProjectExplorerViewController {
    private static final String PROJECT_EXPLORER_VIEW_FXML = "/ui/view/ProjectExplorerView.fxml";
    
    private ProjectExplorerView view;

	@Inject ServiceContainer services;

	private TreeSynchronization treeSync;

	private WorkspaceFileSystem workspaceFileSystem;
    
    @PostConstruct
	public void postConstruct(Composite parent, IEclipseContext context, WorkspaceFileSystem fileSystem) throws IOException {
		FXCanvas canvans = new FXCanvas(parent, SWT.None);
		FXMLLoader<ProjectExplorerView> loader = new FXMLLoader<ProjectExplorerView>(context, StringTable.BUNDLE_NAME,
				PROJECT_EXPLORER_VIEW_FXML);


		FileTreeElement treeRoot = initializeInput(fileSystem);
		
		view = loader.getController();
		TreeItem<FileTreeElement> root = new TreeItem<FileTreeElement>(treeRoot);
		root.setValue(treeRoot);
		view.projectTree.setRoot(root);
		// TODO:
//		view.projectTree.setCellFactory(DefaultTreeCell.<FileTreeElement> forTreeView());
		
		EventHandler<ActionEvent> action = contextHandler();
		view.ctxNewFolder.setOnAction(action);

		Scene scene = new Scene(loader.getNode());
		canvans.setScene(scene);
	}
    
    private FileTreeElement initializeInput(WorkspaceFileSystem fileSystem) {
    	workspaceFileSystem = fileSystem;
    	workspaceFileSystem.initializeFileTree(RCPContentProvider.getCurrentWorkspacePath());
		treeSync = new TreeSynchronization(workspaceFileSystem);
		
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
