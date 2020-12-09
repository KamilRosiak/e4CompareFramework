
package de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers;

import java.nio.file.Path;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.WorkspaceFileSystem;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPDialogService;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.NewFolderPage;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.NewFolderWizard;

public class NewFolderHandler {

	@Execute
	public void execute(Shell shell, RCPDialogService dialogService, RCPSelectionService selectionService,
			RCPImageService imageService, WorkspaceFileSystem fileSystem) {
		FileTreeElement selectedElement = selectionService.getCurrentSelectionFromExplorer();

		NewFolderPage page = new NewFolderPage("NewFolder", "Choose a Folder Name",
				imageService.getImageDescriptor(null, "icons/Explorer_View/items/project32.png"));

		// create selected directory when selected element available, else create new
		// project
		Path directory;
		if (selectedElement != null) {
			directory = FileHandlingUtility.getPath(selectedElement);
		} else {
			directory = FileHandlingUtility.getPath(fileSystem.getWorkspaceDirectory());
		}

		NewFolderWizard folderWizard = new NewFolderWizard(directory, page);
		folderWizard.addPage(page);
		dialogService.constructDialog("Create a new Folder", new Point(600, 200), folderWizard).open();
	}

	@CanExecute
	public boolean canExecute(RCPSelectionService selectionService) {
		FileTreeElement element = selectionService.getCurrentSelectionFromExplorer();
		return element == null || element.isDirectory();
	}
}