package de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CEventTable;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers.FileImportHandler;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers.NewFileHandler;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers.NewFolderHandler;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers.RemoveFileCommand;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers.ShowInExplorerHandler;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

/**
 * Fills and handles the ProjectExplorer Toolbar. Please note that the use of FX
 * Accelerators as shortcuts is discouraged as FX <13 consumes their KeyCombos
 * application wide and this is not desired behavior.
 */
public class ProjectExplorerToolBarController {

	private ServiceContainer services;

	private ToolBar bar;

	private Button btnNewFolder;
	private Button btnNewFile;
	private Button btnImportFiles;
	private Button btnDelete;
	private Button btnShowExplorer;

	private boolean isFlatView = false;

	/**
	 * Fills the toolbar with Buttons performing project explorer related actions.
	 * 
	 * @param toolbar  The FXML Toolbar Object defined in UI
	 * @param services The ServiceContainer that holds all e4c Services, required
	 *                 for some handlers
	 * @param shell    The current UI Shell passed on from the ViewController's
	 *                 Parent, required for some handlers
	 */
	public ProjectExplorerToolBarController(ToolBar toolbar, IEclipseContext context, ServiceContainer services,
			Shell shell) {
		bar = toolbar;
		this.services = services;

		// Search Section
		TextField search = new TextField();
		search.setVisible(false);
		search.setOnAction(actionEvent -> {
			services.eventBroker.send(E4CEventTable.EVENT_FILTER_CHANGED, search.getText());
		});

		ImageView searchImage = services.imageService.getFXImage(null, FileTable.SEARCH_PNG);
		Tooltip searchTooltip = new Tooltip("Search Files");
		ImageView closeSearchImage = services.imageService.getFXImage(null, FileTable.X_PNG);
		Tooltip closeSearchTooltip = new Tooltip("Exit Search");
		Button searchButton = new Button("", searchImage);
		searchButton.setTooltip(searchTooltip);
		searchButton.setOnAction(actionEvent -> {
			search.setVisible(!search.isVisible());
			// Removing and adding the search bar allows automatic resizing
			if (search.isVisible()) {
				searchButton.setGraphic(closeSearchImage);
				searchButton.setTooltip(closeSearchTooltip);
				bar.getItems().add(1, search);
				search.requestFocus();
			} else {
				searchButton.setGraphic(searchImage);
				searchButton.setTooltip(searchTooltip);
				search.setText("");
				bar.getItems().remove(search);
				services.eventBroker.send(E4CEventTable.EVENT_FILTER_CHANGED, null);
			}

		});
		bar.getItems().add(searchButton);

		bar.getItems().add(new Separator());

		// New Folder
		btnNewFolder = createToolbarButton("Create New Folder", FileTable.NEWFOLDER_PNG, actionEvent -> {
			NewFolderHandler handler = new NewFolderHandler();
			if (handler.canExecute(services.rcpSelectionService)) {
				handler.execute(context, services, shell);
			}
		});

		// File Actions
		btnNewFile = createToolbarButton("Create new File", FileTable.NEWFILE_PNG, actionEvent -> {
			NewFileHandler handler = new NewFileHandler();
			if (handler.canExecute(services.rcpSelectionService)) {
				handler.execute(context, services, shell);
			}
		});

		// Import Files
		btnImportFiles = createToolbarButton("Import Files", FileTable.FILE_PNG, actionEvent -> {
			FileImportHandler handler = new FileImportHandler();
			if (handler.canExecute(services.rcpSelectionService)) {
				handler.execute(context, services, shell);
			}
		});

		// Delete
		btnDelete = createToolbarButton("Delete Selected Entries", FileTable.DELETE_PNG, actionEvent -> {
			RemoveFileCommand handler = new RemoveFileCommand();
			if (handler.canExecute(services.rcpSelectionService)) {
				handler.execute(context, services, shell);
			}
		});

		// Show in Explorer
		btnShowExplorer = createToolbarButton("Show in Explorer", FileTable.EXPLORER_PNG, actionEvent -> {
			ShowInExplorerHandler handler = new ShowInExplorerHandler();
			if (handler.canExecute(services.rcpSelectionService)) {
				handler.execute(context, services, shell);
			}
		});

		// Switch between hierarchical and flat view
		ImageView flatViewImage = services.imageService.getFXImage(null, FileTable.FLAT_VIEW_PNG);
		ImageView hierViewImage = services.imageService.getFXImage(null, FileTable.HIERARCICAL_VIEW_PNG);
		Button btnToggleView = new Button("", flatViewImage);
		btnToggleView.setTooltip(new Tooltip("Change Representation"));
		btnToggleView.setOnAction(actionEvent -> {
			isFlatView = !isFlatView;
			services.eventBroker.send(E4CEventTable.EVENT_VIEW_TOGGLE, null);

			if (isFlatView) {
				btnToggleView.setGraphic(hierViewImage);
			} else {
				btnToggleView.setGraphic(flatViewImage);
			}
		});
		bar.getItems().add(btnToggleView);

		update();
	}

	/**
	 * Creates an FX Button and adds it to the toolbar.
	 * 
	 * @param tooltip   The Tooltip to be displayed
	 * @param imagePath Path to a 16px Image, ideally via FileTable
	 * @param value     The Action to be performed once the button is clicked
	 * @return The created button
	 */
	private Button createToolbarButton(String tooltip, String imagePath, EventHandler<ActionEvent> value) {
		ImageView image = services.imageService.getFXImage(null, imagePath);
		Button button = new Button("", image);
		button.setTooltip(new Tooltip(tooltip));
		button.setOnAction(value);
		bar.getItems().add(button);
		return button;
	}

	/** Update Button states based on their handler */
	public void update() {

		// NewFolder
		NewFolderHandler nfh = new NewFolderHandler();
		btnNewFolder.setDisable(!nfh.canExecute(services.rcpSelectionService));

		// Import Files
		FileImportHandler fih = new FileImportHandler();
		btnImportFiles.setDisable(!fih.canExecute(services.rcpSelectionService));

		// Delete Files
		RemoveFileCommand rmc = new RemoveFileCommand();
		btnDelete.setDisable(!rmc.canExecute(services.rcpSelectionService));

		// Show in Explorer
		ShowInExplorerHandler seh = new ShowInExplorerHandler();
		btnShowExplorer.setDisable(!seh.canExecute(services.rcpSelectionService));

		// New file
		NewFileHandler newFileHandler = new NewFileHandler();
		btnNewFile.setDisable(!newFileHandler.canExecute(services.rcpSelectionService));

	}

}
