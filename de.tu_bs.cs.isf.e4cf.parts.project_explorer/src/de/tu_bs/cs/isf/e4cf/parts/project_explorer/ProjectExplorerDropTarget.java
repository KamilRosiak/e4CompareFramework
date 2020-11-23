package de.tu_bs.cs.isf.e4cf.parts.project_explorer;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import org.eclipse.e4.core.contexts.IEclipseContext;

import de.tu_bs.cs.isf.e4cf.core.dnd.drop.AbstractFileDropTarget;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.WorkspaceFileSystem;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.Directory;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.File;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;

//public class ProjectExplorerDropTarget extends AbstractFileDropTarget {
//	private WorkspaceFileSystem fileSystem;
//	
//	public ProjectExplorerDropTarget(Control control, WorkspaceFileSystem fileSystem) {
//		super(control);
//		this.fileSystem = fileSystem;
//	}
//
//	@Override
//	public void handleItem(DropTargetEvent event) {
//		TreeItem item = (TreeItem)event.item;
//		Directory directory = (Directory)item.getData();
//		String[] files = (String[])event.data;
//		for (String file : files) {
//		 	try {
//		 		fileSystem.copy(Paths.get(file), Paths.get(directory.getAbsolutePath()));
//			} catch (FileAlreadyExistsException e) {
//				RCPMessageProvider.errorMessage("File already exsits.", "A file with the name "+ file +" exists.");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//}

public class ProjectExplorerDropTarget implements EventHandler<DragEvent> {

	IEclipseContext eclipseContext;
	private ServiceContainer services;
	private WorkspaceFileSystem workspaceFileSystem;

	public ProjectExplorerDropTarget(IEclipseContext eclipseContext, ServiceContainer services,
			WorkspaceFileSystem workspaceFileSystem) {
		this.eclipseContext = eclipseContext;
		this.services = services;
		this.workspaceFileSystem = workspaceFileSystem;
	}

	@Override
	public void handle(DragEvent event) {
		System.out.println("DropTarget triggered!");
		final Dragboard db = event.getDragboard();
		boolean success = false;
		if (db.hasFiles()) {
			System.out.println("If of DropTarget reached!");
			EventTarget target = event.getTarget();
//			if(!(target instanceof Node)) {
//				event.consume();
//			}
//			Node node = (Node) target;
//			Directory directory = (Directory) event.getTarget();
			List<java.io.File> files = db.getFiles();
			for( java.io.File file : files) {
				System.out.println("Imported File: " + file.getAbsolutePath());
//				 try {
//					 	workspaceFileSystem.copy(Paths.get(file.getAbsolutePath()), Paths.get(directory.getAbsolutePath()));
//					 	success = true;
//					} catch (FileAlreadyExistsException e) {
//						RCPMessageProvider.errorMessage("File already exsits.", "A file with the name "+ file +" exists.");
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
			}
		}
		event.setDropCompleted(success);
		event.consume();

	}

}
