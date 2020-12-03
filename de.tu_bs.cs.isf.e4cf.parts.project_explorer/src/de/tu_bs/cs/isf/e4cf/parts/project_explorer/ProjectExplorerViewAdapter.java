package de.tu_bs.cs.isf.e4cf.parts.project_explorer;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.file_structure.WorkspaceFileSystem;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.view.ProjectExplorerViewController;
import javafx.embed.swt.FXCanvas;

/**
 * View Controller for the ProjectExplorerView
 */
public class ProjectExplorerViewAdapter {
	
	@PostConstruct
	public void postConstruct(Composite parent, IEclipseContext context, WorkspaceFileSystem fileSystem)
			throws IOException {
		
		// initiate view controller
		// Create a JavaFX canvas on the SWT Composite parent
		FXCanvas canvas = new FXCanvas(parent, SWT.None);
		FXMLLoader<ProjectExplorerViewController> loader = new FXMLLoader<ProjectExplorerViewController>(context, StringTable.BUNDLE_NAME, FileTable.PROJECT_EXPLORER_VIEW_FXML);
		loader.getController().initializeView(context, fileSystem, canvas, loader);
		
	}
	
}
