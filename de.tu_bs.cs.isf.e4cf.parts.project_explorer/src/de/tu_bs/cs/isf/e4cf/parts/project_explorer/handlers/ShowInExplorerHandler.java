
package de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers;

import java.awt.Desktop;
import java.io.IOException;
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

public class ShowInExplorerHandler implements IHandler {

	@Execute
	public void execute(IEclipseContext context, ServiceContainer services, Shell shell) {
		FileTreeElement directoryToOpen = services.rcpSelectionService.getCurrentSelectionFromExplorer();

		// If we omit this, we can open files with the OS directly.
		if (!directoryToOpen.isDirectory()) {
			directoryToOpen = directoryToOpen.getParent();
		}

		java.io.File directoryAsFile = FileHandlingUtility.getPath(directoryToOpen).toFile();
		try {
			Desktop.getDesktop().open(directoryAsFile);
		} catch (IOException e) {
			// TODO: Is there an error handling service in this framework?
			e.printStackTrace();
		}
	}

	@CanExecute
	public boolean canExecute(RCPSelectionService selectionService) {
		List<FileTreeElement> selections = selectionService.getCurrentSelectionsFromExplorer();
		return selections != null && selections.size() == 1;
	}
}
