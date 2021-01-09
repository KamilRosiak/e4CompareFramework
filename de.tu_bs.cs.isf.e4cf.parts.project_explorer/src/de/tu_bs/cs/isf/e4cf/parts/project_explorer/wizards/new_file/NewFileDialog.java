package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.new_file;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.eclipse.e4.core.contexts.IEclipseContext;

import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CFileTable;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

/** A wizard that lets the user create a new file. */
public class NewFileDialog {
	
	private Dialog<Path> dialog;
	private NewFilePage newFilePage;

	/**
	 * @param parentPath
	 * @param imageService
	 */
	public NewFileDialog(IEclipseContext context, Path parentPath, RCPImageService imageService) {
		dialog = new Dialog<Path>();
		dialog.setTitle("Create New File...");
		
		final DialogPane pane = dialog.getDialogPane();
		pane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		final Stage stage = (Stage) pane.getScene().getWindow();
		stage.getIcons().add(imageService.getFXImage(null, E4CFileTable.FRAMEWORK_LOGO_SMALL).getImage());
		
		this.newFilePage =  new NewFilePage(context,  parentPath);
		pane.setContent(newFilePage.createControl(pane));

		dialog.setResultConverter(dialogBtn -> {
			if (dialogBtn == ButtonType.OK) {
		        return this.newFilePage.targetPath;
		    }
		    return null;
		});
	}

	/** shows the dialog and creates the file, name is checked in the dialog */
	public void open() {
		Optional<Path> result = dialog.showAndWait();
		result.ifPresent(path -> {
			try {
				Files.createFile(path);
			} catch (FileAlreadyExistsException alreadyException) {
				alreadyException.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}


}
