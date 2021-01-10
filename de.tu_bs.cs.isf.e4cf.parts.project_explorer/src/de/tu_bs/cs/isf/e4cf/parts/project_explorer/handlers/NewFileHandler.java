package de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers;

import java.nio.file.Path;
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
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.new_file.NewFileDialog;

/**
 * This handler handles the request from the ContextMenu Children for creating a
 * new file.
 *
 */
public class NewFileHandler implements IHandler {

	@Execute
	public void execute(IEclipseContext context, ServiceContainer services, Shell shell) {
		FileTreeElement selectedElement = services.rcpSelectionService.getCurrentSelectionFromExplorer();

		Path directory;
		if (selectedElement != null) {
			directory = FileHandlingUtility.getPath(selectedElement);
		} else {
			directory = FileHandlingUtility.getPath(services.workspaceFileSystem.getWorkspaceDirectory());
		}

		NewFileDialog dialog = new NewFileDialog(context, directory, services.imageService);
		dialog.open();
	}

	@CanExecute
	public boolean canExecute(RCPSelectionService selectionService) {
		List<FileTreeElement> selections = selectionService.getCurrentSelectionsFromExplorer();
		return selections != null && selections.size() == 1 && selections.get(0).isDirectory();
	}

}
