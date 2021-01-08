
package de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers;

import java.io.File;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;

public class NewFolderHandler {

	private static final String FOLDER_PLACEHOLDER = "/New Folder";

	@Execute
	public void execute(RCPSelectionService rcpSelectionService) {
		FileTreeElement element = rcpSelectionService.getCurrentSelectionFromExplorer();
		String stringPath = element.getAbsolutePath();
		File folderToCreate = new File(stringPath + FOLDER_PLACEHOLDER);
		int folderNameExistsCounter = 2;
		while (folderToCreate.exists()) {
			folderToCreate = new File(stringPath + FOLDER_PLACEHOLDER + " (" + folderNameExistsCounter + ")");
			folderNameExistsCounter++;
		}
		folderToCreate.mkdir();
	}

	@CanExecute
	public boolean canExecute(RCPSelectionService selectionService) {
		FileTreeElement element = selectionService.getCurrentSelectionFromExplorer();
		return element != null && element.isDirectory();
	}
}