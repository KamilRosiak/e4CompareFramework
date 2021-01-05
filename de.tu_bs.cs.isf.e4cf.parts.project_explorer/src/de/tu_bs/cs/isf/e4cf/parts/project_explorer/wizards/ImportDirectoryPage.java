package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards;

import java.nio.file.Path;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller.CopyDirectoryController;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;

/**
 * Represents a Wizard Page which lets the user choose to copy the content of a
 * directory or only the directory itself (empty).
 */
public class ImportDirectoryPage extends WizardPage {

	private IEclipseContext context;

	// the strategy how the dropped directories should be copied (3 states)
	private CopyStrategy copyStrategy = CopyStrategy.EMPTY;

	private FXMLLoader<CopyDirectoryController> loader;

	/**
	 * Creates a new page for the wizard that displays importing a directory
	 * options.
	 * 
	 * @param pageName      the name of the page, it is use to display a title in
	 *                      the wizard.
	 * @param path          the target path, where the dropped items should copied
	 *                      to.
	 * @param context       the Eclipse Context used for the FXML loader
	 * @param imgDescriptor an image for the page to display.
	 */
	public ImportDirectoryPage(String pageName, Path path, IEclipseContext context, ImageDescriptor imgDescriptor) {
		super(pageName, pageName, imgDescriptor);
		this.context = context;
		setDescription(path.toString());
	}

	@Override
	public void createControl(Composite parent) {
		FXCanvas canvas = new FXCanvas(parent, SWT.None);
		loader = new FXMLLoader<CopyDirectoryController>(context, StringTable.BUNDLE_NAME,
				FileTable.COPY_DIRECTORY_PAGE_FXML);
		Scene scene = new Scene(loader.getNode());

		canvas.setScene(scene);

		loader.getController().copyEmptyRB.selectedProperty()
				.addListener((obs, wasPreviouslySelected, isNowSelected) -> {
					if (isNowSelected) {
						copyStrategy = CopyStrategy.EMPTY;
					}

					update();
				});

		loader.getController().copyShallowRB.selectedProperty()
				.addListener((obs, wasPreviouslySelected, isNowSelected) -> {
					if (isNowSelected) {
						copyStrategy = CopyStrategy.SHALLOW;
					}
					update();
				});

		loader.getController().copyContentRB.selectedProperty()
				.addListener((obs, wasPreviouslySelected, isNowSelected) -> {
					if (isNowSelected) {
						copyStrategy = CopyStrategy.RECURSIVE;
					}
					update();
				});

		loader.getController().toggleGroup();

		setControl(canvas);
	}

	@Override
	public void setDescription(String description) {
		super.setDescription("To: " + description);
	};

	/**
	 * Get the currently selected copying strategy
	 * 
	 * @return the currently selected copying strategy
	 */
	public CopyStrategy getCopyStrategy() {
		return copyStrategy;
	}

	/**
	 * Update the buttons of the wizard
	 */
	private void update() {
		getWizard().getContainer().updateButtons();
	}

	/**
	 * the states how a directory can be copied to its destination EMPTY: only
	 * create folder, do not copy content in it. SHALLOW: copy only 1st level
	 * children RECURSIVE: copy everything inside the directory
	 */
	enum CopyStrategy {
		EMPTY, SHALLOW, RECURSIVE;
	}
}
