package de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.drop_files;

import java.nio.file.Path;

import org.eclipse.e4.core.contexts.IEclipseContext;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller.CopyDirectoryController;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;
import javafx.scene.Parent;

/**
 * Represents a Wizard Page which lets the user choose to copy the content of a
 * directory or only the directory itself (empty).
 */
public class ImportDirectoryPage {

	private IEclipseContext context;

	// the strategy how the dropped directories should be copied (3 states)
	private CopyStrategy copyStrategy = CopyStrategy.EMPTY;

	private FXMLLoader<CopyDirectoryController> loader;
	// by passing the Pane of the dialog into createControl the dialog buttons become accessible to the page

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
	public ImportDirectoryPage(Path path, IEclipseContext context) {
		this.context = context;
	}

	public Parent createControl() {
		loader = new FXMLLoader<CopyDirectoryController>(context, StringTable.BUNDLE_NAME,
				FileTable.COPY_DIRECTORY_PAGE_FXML);
		loader.getController().copyEmptyRB.selectedProperty()
				.addListener((obs, wasPreviouslySelected, isNowSelected) -> {
					if (isNowSelected) {
						this.copyStrategy = CopyStrategy.EMPTY;
					}
				});

		loader.getController().copyShallowRB.selectedProperty()
				.addListener((obs, wasPreviouslySelected, isNowSelected) -> {
					if (isNowSelected) {
						this.copyStrategy = CopyStrategy.SHALLOW;
					}
				});

		loader.getController().copyContentRB.selectedProperty()
				.addListener((obs, wasPreviouslySelected, isNowSelected) -> {
					if (isNowSelected) {
						copyStrategy = CopyStrategy.RECURSIVE;
					}
				});

		loader.getController().toggleGroup();
		
		return loader.getNode();
	}

	/**
	 * Get the currently selected copying strategy
	 * 
	 * @return the currently selected copying strategy
	 */
	public CopyStrategy getCopyStrategy() {
		return this.copyStrategy;
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
