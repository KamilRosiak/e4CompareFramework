package de.tu_bs.cs.isf.e4cf.parts.project_explorer;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Paths;

import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.core.dnd.drop.AbstractFileDropTarget;
import de.tu_bs.cs.isf.e4cf.core.file_structure.WorkspaceFileSystem;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.Directory;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;

public class ProjectExplorerDropTarget extends AbstractFileDropTarget {
	private WorkspaceFileSystem fileSystem;
	
	public ProjectExplorerDropTarget(Control control, WorkspaceFileSystem fileSystem) {
		super(control);
		this.fileSystem = fileSystem;
	}

	@Override
	public void handleItem(DropTargetEvent event) {
		TreeItem item = (TreeItem)event.item;
		Directory directory = (Directory)item.getData();
		String[] files = (String[])event.data;
		for (String file : files) {
		 	try {
		 		fileSystem.copy(Paths.get(file), Paths.get(directory.getAbsolutePath()));
			} catch (FileAlreadyExistsException e) {
				RCPMessageProvider.errorMessage("File already exsits.", "A file with the name "+ file +" exists.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

	
