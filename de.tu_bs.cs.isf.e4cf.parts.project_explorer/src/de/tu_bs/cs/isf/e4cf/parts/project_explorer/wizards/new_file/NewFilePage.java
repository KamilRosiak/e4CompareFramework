package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.new_file;

import java.nio.file.Files;
import java.nio.file.Path;
import org.eclipse.e4.core.contexts.IEclipseContext;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller.NewFileViewController;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


/** A wizard that lets the user create a new file. */
public class NewFilePage {
	private IEclipseContext context;
	private FXMLLoader<NewFileViewController> loader;
	public Path parentPath;
	public Path targetPath;
	public String inputValue;


	// the regex to check if filename is valid.
	private static final String FILE_NAME_REGEX = "[a-zA-Z0-9-_#]*[.]{1}[a-zA-Z0-9]+";
	private static final String MISSIN_TYPE_REGEX = "[a-zA-Z0-9-_#]*[.]*[a-zA-Z0-9]*";

	/**
	 * @param context
	 * @param parentPath
	 */
	public NewFilePage(IEclipseContext context, Path parentPath) {
		this.context = context;
		this.parentPath = parentPath;
	}
	
	public Parent createControl(DialogPane pane) {
		
		final Button btnOk = (Button) pane.lookupButton(ButtonType.OK);
		btnOk.setDisable(true);
		loader = new FXMLLoader<NewFileViewController>(context, StringTable.BUNDLE_NAME,
				FileTable.NEW_FILE_PAGE_VIEW);
		
		TextField input = loader.getController().fileNameTextField;
		Text alreadyExitsText = loader.getController().alreadyExitsText;
		Text notValidText = loader.getController().notValidText;
		Text fileTypeMissingText = loader.getController().fileTypeMissingText;
		input.textProperty().addListener((obs, oldValue, newValue) -> {
			String inputValue = input.getText();
			if (isFilenameValid(inputValue) && isFilePathValid(inputValue)) {
				inputValue= input.getText();
				btnOk.setDisable(false);
				alreadyExitsText.setVisible(false);
				notValidText.setVisible(false);
				fileTypeMissingText.setVisible(false);
			} else {
				btnOk.setDisable(true);
				
				if (!isFilePathValid(inputValue)) {
					alreadyExitsText.setVisible(true);
				} else {
					alreadyExitsText.setVisible(false);
				}
				
				if (isFileTypeMising(inputValue)) {
					notValidText.setVisible(true);
				} else {
					notValidText.setVisible(false);
				}
				
				if (isFileTypeMising(inputValue)) {
					fileTypeMissingText.setVisible(true);
				} else {
					fileTypeMissingText.setVisible(false);
				}
			}
		});
		
		return loader.getNode();
	}
	/**
	 * Check if user typed in a valid filename.
	 * 
	 * @return true if filename matches with FILE_NAME_REGEX
	 */
	private boolean isFilenameValid(String name) {
		this.targetPath = parentPath.resolve(name.trim());
		if (name.matches(FILE_NAME_REGEX)) {
			return true;
		}
		return false;
	}
	/**
	 * Check if user typed in an unused filename.
	 * 
	 * @return true if file does not exist
	 */
	private boolean isFilePathValid(String name) {
		this.targetPath = parentPath.resolve(name.trim());
		if (Files.exists(targetPath)) {
			return false;
		}
		return true;
	}
	/**
	 * Check if user input misses an file type.
	 * 
	 * @return true if FILE_NAME_REGEX not matched, but MISSIN_TYPE_REGEX is matched.
	 */
	private boolean isFileTypeMising(String name) {
		return name.matches(MISSIN_TYPE_REGEX) && !name.matches(FILE_NAME_REGEX);
	}
}

