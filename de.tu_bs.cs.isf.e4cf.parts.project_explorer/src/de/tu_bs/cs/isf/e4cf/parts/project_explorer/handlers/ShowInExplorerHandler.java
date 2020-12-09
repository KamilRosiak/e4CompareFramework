
package de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers;

import java.awt.Desktop;
import java.io.IOException;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;

public class ShowInExplorerHandler {
	@Execute
	public void execute(RCPSelectionService selectionService) {
		FileTreeElement directoryToOpen = selectionService.getCurrentSelectionFromExplorer();

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
		FileTreeElement element = selectionService.getCurrentSelectionFromExplorer();
		return element != null && element.exists();
	}
}