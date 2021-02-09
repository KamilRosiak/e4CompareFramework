package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.drop_files;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.e4.core.contexts.IEclipseContext;

import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CFileTable;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.DropElement;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.DialogPane;

/**
 * A dialog that handles drops from the system explorer to the project explorer,
 * if the drop contains a nested element (e.g. directory)
 */
public class DropFilesDialog {

	private DropElement dropElement;
	private Alert alert;
	private ImportDirectoryPage importDirectoryPage;
	// indicates whether a file has been moved.
	private boolean didFileMove = true;

	/**
	 * Dialog to present different copy strategies
	 * 
	 * @param context     IEclipseContextfor the FXMLLoader
	 * @param dropElement the files dragged from the system explorer
	 * @param imgService  to get
	 */
	public DropFilesDialog(IEclipseContext context, DropElement dropElement, RCPImageService imgService) {
		this.dropElement = dropElement;

		alert = new Alert(AlertType.NONE);
		alert.setTitle("Import a Directory");

		final DialogPane pane = alert.getDialogPane();
		pane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		
		final Stage stage = (Stage) pane.getScene().getWindow();
		stage.getIcons().add(imgService.getFXImage(null, E4CFileTable.FRAMEWORK_LOGO_SMALL).getImage());

		this.importDirectoryPage = new ImportDirectoryPage(dropElement.getTarget(), context);
		pane.setContent(importDirectoryPage.createControl());

	}

	/** show the dialog and wait for the users input */
	public void open() {
		alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> {
			this.performFinish();
		});
	}

	/** evaluate the copyStrategy */
	public void performFinish() {

		switch (importDirectoryPage.getCopyStrategy()) {
		case EMPTY:
			copyRecursively(0);
			break;
		case RECURSIVE:
			copyRecursively(Integer.MAX_VALUE);
			break;
		case SHALLOW:
			copyRecursively(1);
			break;
		default:
			copyRecursively(0);
			break;
		}
	}

	/**
	 * 
	 * @param depth indicates to which level the user want to copy the files. 0:
	 *              Create only folder without content 1: Copy only first level
	 *              children 2: Copy all children recursively
	 */
	private void copyRecursively(int depth) {

		for (Path directory : dropElement.getSources()) {
			didFileMove = true;
			try {
				Files.walk(directory, depth).forEach(sourcePath -> {
					// if parent didn't move then children don't need to move either.
					if (!didFileMove) {
						return;
					}
					// construct new target path
					Path target = (dropElement.getTarget().resolve(directory.getFileName()))
							.resolve(directory.relativize(sourcePath));
					if (target.equals(sourcePath)) {
						// no need to copy
						didFileMove = false;
					} else {
						try {
							Files.copy(sourcePath, target);
						} catch (FileAlreadyExistsException fae) {
							if (didFileMove) {
								RCPMessageProvider.errorMessage("Directory already exsits.",
										"A Directory with the name " + directory.getFileName() + " exists.");
							}
							didFileMove = false;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * All options for file transfer behavior.
	 */
	public enum DropMode {
		COPY, MOVE;
	}
}
