package de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers;

import java.nio.file.Path;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.WorkspaceFileSystem;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.NewFileDialog;

/**
 * This handler handles the request from the ContextMenu Children for creating a
 * new file.
 *
 */
public class NewFileHandler {
	@Execute
	public void execute(RCPSelectionService selectionService, RCPImageService imageService,
			WorkspaceFileSystem fileSystem) {

		FileTreeElement selectedElement = selectionService.getCurrentSelectionFromExplorer();

		Path directory;
		if (selectedElement != null) {
			directory = FileHandlingUtility.getPath(selectedElement);
		} else {
			directory = FileHandlingUtility.getPath(fileSystem.getWorkspaceDirectory());
		}

		NewFileDialog dialog = new NewFileDialog(directory, imageService);
		dialog.open();
	}

	@CanExecute
	public boolean canExecute(RCPSelectionService selectionService) {
		FileTreeElement element = selectionService.getCurrentSelectionFromExplorer();
		return element == null || element.isDirectory();
	}

}
