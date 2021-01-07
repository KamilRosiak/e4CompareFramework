package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CFileTable;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

/**
 * A wizard that lets the user create a new file.
 *
 */
public class NewFileDialog {

	private Path parentPath;
	private Path targetPath;
	private TextInputDialog dialog;

	// the regex to check if filename is valid.
	private static final String FILE_NAME_REGEX = "[a-zA-Z0-9-_#]*[.]{1}[a-zA-Z0-9]+";

	/**
	 * @param parentPath
	 * @param imageService
	 */
	public NewFileDialog(Path parentPath, RCPImageService imageService) {
		this.parentPath = parentPath;
		dialog = new TextInputDialog("New File");

		final Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(imageService.getFXImage(null, E4CFileTable.FRAMEWORK_LOGO_SMALL).getImage());

		dialog.setTitle("File Creation Dialog");
		dialog.setHeaderText("Root Directory: " + parentPath.getFileName() + "\nEnter the new file name:");
		dialog.setGraphic(imageService.getFXImage(null, FileTable.NEWFILE_PNG));

		final Button btnOk = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
		btnOk.setDisable(true);

		final TextField input = dialog.getEditor();
		input.setOnKeyReleased(name -> {
			if (isFilenameValid(input.getText())) {
				btnOk.setDisable(false);
			} else {
				btnOk.setDisable(true);
			}
		});
	}

	/** shows the dialog and creates the file, name is checked in the dialog */
	public void showDialog() {
		Optional<String> result = dialog.showAndWait();
		result.ifPresent(name -> {
			try {
				Files.createFile(targetPath);
			} catch (FileAlreadyExistsException alreadyException) {
				alreadyException.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Check if user typed in a valid and unused filename.
	 * 
	 * @return true if file does not exist and filename matches with FILE_NAME_REGEX
	 */
	private boolean isFilenameValid(String name) {
		targetPath = parentPath.resolve(name);
		if (Files.exists(targetPath)) {
			return false;
		} else {
			return name.matches(FILE_NAME_REGEX);
		}
	}
}
