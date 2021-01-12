
package de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IHandler;

public class NewFolderHandler implements IHandler {

	private static final String FOLDER_PLACEHOLDER = "New Folder";

	@Execute
	public void execute(IEclipseContext context, ServiceContainer services, Shell shell) {
		FileTreeElement element = services.rcpSelectionService.getCurrentSelectionFromExplorer();
		String stringPath = element.getAbsolutePath();
		File folderToCreate = new File(Paths.get(stringPath, FOLDER_PLACEHOLDER).toAbsolutePath().toString());
		int folderNameExistsCounter = 2;
		while (folderToCreate.exists()) {
			folderToCreate = new File(Paths.get(stringPath, FOLDER_PLACEHOLDER + " (" + folderNameExistsCounter + ")").toAbsolutePath().toString());
			folderNameExistsCounter++;
		}
		folderToCreate.mkdir();
	}

	@CanExecute
	public boolean canExecute(RCPSelectionService selectionService) {
		List<FileTreeElement> selections = selectionService.getCurrentSelectionsFromExplorer();
		return selections != null && selections.size() == 1 && selections.get(0).isDirectory();
	}
}
