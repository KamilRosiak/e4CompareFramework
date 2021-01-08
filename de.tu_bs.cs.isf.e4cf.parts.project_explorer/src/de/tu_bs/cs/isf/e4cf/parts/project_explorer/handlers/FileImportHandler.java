package de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers;

import java.io.File;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.WorkspaceFileSystem;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * A handler that opens the systems file import dialog.
 * 
 */
public class FileImportHandler {

	@Execute
	public void execute(RCPSelectionService selectionService, WorkspaceFileSystem workspaceFileSystem) {
		Path target = Paths.get(workspaceFileSystem.getWorkspaceDirectory().getAbsolutePath());

		final FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
		List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

		if (selectedFiles != null && !selectedFiles.isEmpty()) {
			try {
				target = getTargetPath(selectionService);
				for (File file : selectedFiles) {
					workspaceFileSystem.copy(file.toPath(), target);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@CanExecute
	public boolean canExecute(RCPSelectionService selectionService) {
		try {
			FileTreeElement element = selectionService.getCurrentSelectionFromExplorer();
			checkForValidSelection(element);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private Path getTargetPath(RCPSelectionService selectionService) throws NotDirectoryException {
		FileTreeElement selection = selectionService.getCurrentSelectionFromExplorer();
		checkForValidSelection(selection);
		Path target = FileHandlingUtility.getPath(selection);
		return target;
	}

	private void checkForValidSelection(FileTreeElement selection) throws NotDirectoryException {
		if (selection == null) {
			throw new NullPointerException("selection is invalid.");
		}
		if (!selection.isDirectory()) {
			throw new NotDirectoryException("selection is not a directory.");
		}
	}
}