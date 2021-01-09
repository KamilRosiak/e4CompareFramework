package de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IHandler;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * A handler that opens the systems file import dialog.
 * 
 */
public class FileImportHandler implements IHandler {

	@Execute
	public void execute(IEclipseContext context, ServiceContainer services, Shell shell) {
		Path target = Paths.get(services.workspaceFileSystem.getWorkspaceDirectory().getAbsolutePath());

		final FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
		List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

		if (selectedFiles != null && !selectedFiles.isEmpty()) {
			try {
				FileTreeElement selection = services.rcpSelectionService.getCurrentSelectionFromExplorer();
				target = FileHandlingUtility.getPath(selection);
				for (File file : selectedFiles) {
					services.workspaceFileSystem.copy(file.toPath(), target);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@CanExecute
	public boolean canExecute(RCPSelectionService selectionService) {
		FileTreeElement element = selectionService.getCurrentSelectionFromExplorer();
		return element != null && element.isDirectory();
	}
}
