package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards;

import java.nio.file.Path;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller.NewFileViewController;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;

/**
 * Wizard Page that lets the user create a new file.
 */
public class NewFilePage extends WizardPage {

	private IEclipseContext context;

	private String fileName = "";

	private FXMLLoader<NewFileViewController> loader;

	// the regex to check if filename is valid.
	private static final String FILE_NAME_REGEX = "[a-zA-Z0-9-_#]*[.]{1}[a-zA-Z0-9]+";

	protected NewFilePage(String pageName, Path target, IEclipseContext context) {
		super(pageName, pageName, null);
		this.context = context;
		setDescription(target.toString());
	}

	@Override
	public void createControl(Composite parent) {

		FXCanvas canvas = new FXCanvas(parent, SWT.None);
		loader = new FXMLLoader<NewFileViewController>(context, StringTable.BUNDLE_NAME, FileTable.NEW_FILE_PAGE_VIEW);
		Scene scene = new Scene(loader.getNode());

		loader.getController().fileNameTextField.requestFocus();

		/**
		 * Ensures that as soon as the user focuses the textfield the file already
		 * exists error is dismissed.
		 */
		loader.getController().fileNameTextField.focusedProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue) {
				setFileAlreadyExistsHint(false);
			}
		});

		loader.getController().fileNameTextField.textProperty().addListener((obs, oldValue, newValue) -> {
			fileName = newValue;
			if (!isFilenameValid()) {
				loader.getController().notValidText.setVisible(true);
			} else {
				loader.getController().notValidText.setVisible(false);
			}
			update();
		});

		canvas.setScene(scene);

		setControl(canvas);
	}

	@Override
	public void setDescription(String description) {
		super.setDescription("Root directory: " + description);
	}

	/**
	 * Sets a hint for the user if there is a already a file with the same name.
	 * 
	 * @param alreadyExits true if error should be shown, false otherwise.
	 */
	public void setFileAlreadyExistsHint(boolean alreadyExits) {
		loader.getController().alreadyExitsText.setVisible(alreadyExits);
	}

	/**
	 * Check if user typed in a valid filename.
	 * 
	 * @return true if filename matches with FILE_NAME_REGEX
	 */
	public boolean isFilenameValid() {
		return fileName.matches(FILE_NAME_REGEX);
	}

	/**
	 * Get the whole filename as a string. It includes the extension.
	 * 
	 * @return the whole filename
	 */
	public String getFilename() {
		return fileName;
	}

	/**
	 * Update the {Next, Previous, Finish}-Button of the wizard
	 */
	private void update() {
		getWizard().getContainer().updateButtons();
	}

}
