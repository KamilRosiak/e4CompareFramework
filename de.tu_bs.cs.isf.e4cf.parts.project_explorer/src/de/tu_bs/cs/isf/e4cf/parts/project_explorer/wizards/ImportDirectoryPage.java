package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller.CopyDirectoryController;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;

/**
 * Represents a Wizard Page which lets the user choose to copy the content of a
 * directory or only the directory itself (empty).
 */
public class ImportDirectoryPage extends WizardPage {

	private IEclipseContext context;

	private boolean copyEmptyFolder = true;

	public ImportDirectoryPage(String pageName, IEclipseContext context) {
		super(pageName);
		this.context = context;
	}

	@Override
	public void createControl(Composite parent) {
		FXCanvas canvas = new FXCanvas(parent, SWT.None);
		FXMLLoader<CopyDirectoryController> loader = new FXMLLoader<CopyDirectoryController>(context,
				StringTable.BUNDLE_NAME, FileTable.COPY_DIRECTORY_PAGE_FXML);
		Scene scene = new Scene(loader.getNode());

		canvas.setScene(scene);

		loader.getController().copyEmptyRB.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected,
					Boolean isNowSelected) {
				copyEmptyFolder = isNowSelected;

				update();
			}
		});

		loader.getController().toggleGroup();

		setControl(canvas);
	}

	public boolean copyWithoutContent() {
		return copyEmptyFolder;
	}

	@Override
	public boolean canFlipToNextPage() {
		return !copyEmptyFolder;
	}

	private void update() {
		getWizard().getContainer().updateButtons();
	}

}
