package de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.file_structure.WorkspaceFileSystem;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;

/**
 * Adapts JavaFX ViewController to be loaded and initialized on PostConstruct
 */
public class ProjectExplorerViewAdapter {

	@PostConstruct
	public void postConstruct(Composite parent, IEclipseContext context, WorkspaceFileSystem fileSystem) {

		// Create a JavaFX canvas on the SWT Composite parent
		FXCanvas canvas = new FXCanvas(parent, SWT.None);
		FXMLLoader<ProjectExplorerViewController> loader = new FXMLLoader<ProjectExplorerViewController>(context,
				StringTable.BUNDLE_NAME, FileTable.PROJECT_EXPLORER_VIEW_FXML);

		// Add the scene of the view to the canvas
		Scene scene = new Scene(loader.getNode());
		canvas.setScene(scene);

		loader.getController().initializeView(context, fileSystem, canvas);

	}

}
