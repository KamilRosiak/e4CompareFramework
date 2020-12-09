package de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers;

import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.List;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.WorkspaceFileSystem;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPDialogService;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.FileImportWizard;

/**
 * A handler that copies files into a selected directory.
 * 
 * @author Oliver Urbaniak
 */
public class FileImportHandler {
	private static final String WINDOW_TITLE = "Import Files";
	private static final Point WINDOW_SIZE = new Point(800, 600);
	private static final String FILE_ICON_PATH = "icons/Explorer_View/items/file32.png";

	@Execute
	public void execute(RCPImageService imageService, RCPDialogService dialogService,
			RCPSelectionService selectionService, @Named(IServiceConstants.ACTIVE_SHELL) Shell shell,
			WorkspaceFileSystem fileSystem) {
		try {
			Path target = getTargetPath(selectionService);
			FileImportWizard wizard = buildFileImportWizard(shell, imageService);
			dialogService.constructDialog(WINDOW_TITLE, WINDOW_SIZE, wizard).open();
			copyFiles(target, wizard.getSourceFiles(), fileSystem);
		} catch (IOException e) {
			RCPMessageProvider.errorMessage(WINDOW_TITLE, e.getMessage());
		} catch (NullPointerException e) {
			RCPMessageProvider.errorMessage(WINDOW_TITLE, e.getMessage());
		}

	}

	private void copyFiles(Path target, List<Path> selectedFiles, WorkspaceFileSystem fileSystem) throws IOException {
		for (Path selectedFile : selectedFiles) {
			fileSystem.copy(selectedFile, target);
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

	private FileImportWizard buildFileImportWizard(Shell shell, RCPImageService imageService) {
		ImageDescriptor image = imageService.getImageDescriptor(null, FILE_ICON_PATH);
		FileImportWizard wizard = new FileImportWizard(shell, image);
		return wizard;
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

}