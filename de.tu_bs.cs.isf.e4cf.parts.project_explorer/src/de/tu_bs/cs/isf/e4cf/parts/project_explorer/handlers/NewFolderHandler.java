
package de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers;

import java.io.File;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;

public class NewFolderHandler {

	private static final String FOLDER_PLACEHOLDER = "New Folder";

	@Execute
	public void execute(IEventBroker eventBroker, RCPSelectionService rcpSelectionService) {
		FileTreeElement element = rcpSelectionService.getCurrentSelectionFromExplorer();
		String stringPath = element.getAbsolutePath();
		File dir = new File(stringPath + "/" + FOLDER_PLACEHOLDER);
		int i = 2;
		while (dir.exists()) {
			dir = new File(stringPath + "/" + FOLDER_PLACEHOLDER + " (" + i + ")");
			i++;
		}
		int childrenCount = element.getChildren().size();
		dir.mkdir();

		while (childrenCount == element.getChildren().size()) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
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