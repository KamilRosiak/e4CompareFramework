package de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers;

import java.nio.file.Path;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.WorkspaceFileSystem;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPDialogService;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.NewFileWizard;

/**
 * This handler handles the request from the ContextMenu Children for creating a
 * new file.
 *
 */
public class NewFileHandler {

	@Inject
	IEclipseContext context;

	@Inject
	@Named(IServiceConstants.ACTIVE_SHELL)
	private Shell shell;

	@Execute
	public void execute(IEclipseContext context, RCPDialogService dialogService, RCPSelectionService selectionService,
			RCPImageService imageService, WorkspaceFileSystem fileSystem) {

		FileTreeElement selectedElement = selectionService.getCurrentSelectionFromExplorer();

		Path directory;
		if (selectedElement != null) {
			directory = FileHandlingUtility.getPath(selectedElement);
		} else {
			directory = FileHandlingUtility.getPath(fileSystem.getWorkspaceDirectory());
		}

		if (context != null) {
			this.context = context;
		}

		NewFileWizard newFileWizard = new NewFileWizard(this.context, directory);
		WizardDialog dialog = new WizardDialog(shell, newFileWizard);
		dialog.open();
	}

	@CanExecute
	public boolean canExecute(RCPSelectionService selectionService) {
		FileTreeElement element = selectionService.getCurrentSelectionFromExplorer();
		return element == null || element.isDirectory();
	}

}
