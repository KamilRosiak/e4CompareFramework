package de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller;


import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CEventTable;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers.FileImportHandler;
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
 * Fills and handles the ProjectExplorer Toolbar.
 * Please note that the use of FX Accelerators as shortcuts is discouraged as FX <13 consumes their KeyCombos application wide
 * and this is not desired behavior.
 */
public class ProjectExplorerToolBarController {

	private ServiceContainer services;

	private ToolBar bar;

	private Button btnUndo;
	private Button btnRedo;
	private Button btnNewFolder;
	private Button btnImportFiles;
	private Button btnDelete;
	private Button btnShowExplorer;
	boolean isFlatView = false;

	/**
	 * Fills the toolbar with Buttons performing project explorer related actions.
	 * @param toolbar The FXML Toolbar Object defined in UI
	 * @param services The ServiceContainer that holds all e4c Services, required for some handlers
	 * @param shell The current UI Shell passed on from the ViewController's Parent, required for some handlers
	 */
	public ProjectExplorerToolBarController(ToolBar toolbar, ServiceContainer services, Shell shell) {
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
		ImageView xImage = services.imageService.getFXImage(null, FileTable.X_PNG);
		Tooltip closeSearchTooltip = new Tooltip("Exit Search");
		Button searchButton = new Button("", searchImage);
		searchButton.setTooltip(searchTooltip);
		searchButton.setOnAction(actionEvent -> {
			search.setVisible(!search.isVisible());
			// Removing and readding the search bar allows automatic resizing
			if (search.isVisible()) {
				searchButton.setGraphic(xImage);
				searchButton.setTooltip(closeSearchTooltip);
				bar.getItems().add(1, search);
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

		// Command Actions TODO to enable these buttons command stack systems are required Task #34
		btnUndo = createToolbarButton("Undo", FileTable.UNDO_PNG, actionEvent -> {
			System.out.println("Undo");
		});

		btnRedo = createToolbarButton("Redo", FileTable.REDO_PNG, actionEvent -> {
			System.out.println("Redo");
		});

		bar.getItems().add(new Separator());

		// File Actions
		// TODO New File Task #33

		// New Folder
		btnNewFolder = createToolbarButton("Create New Folder", FileTable.FOLDER_PNG, actionEvent -> {
			NewFolderHandler handler = new NewFolderHandler();
			if (handler.canExecute(services.rcpSelectionService)) {
				handler.execute(shell, services.dialogService, services.rcpSelectionService, services.imageService,
						services.workspaceFileSystem);
			}
		});

		// Import Files
		btnImportFiles = createToolbarButton("Import Files", FileTable.FILE_PNG, actionEvent -> {
			FileImportHandler h = new FileImportHandler();
			if (h.canExecute(services.rcpSelectionService)) {
				h.execute(services.imageService, services.dialogService, services.rcpSelectionService, shell,
						services.workspaceFileSystem);
			}
		});

		// Delete
		btnDelete = createToolbarButton("Delete Selected Files", FileTable.DELETE_PNG, actionEvent -> {
			RemoveFileCommand h = new RemoveFileCommand();
			if (h.canExecute(services.rcpSelectionService)) {
				h.execute(shell, services.rcpSelectionService, services.imageService, services.workspaceFileSystem);
			}
		});

		// Show in Explorer
		btnShowExplorer = createToolbarButton("Show in Explorer", FileTable.EXPLORER_PNG, actionEvent -> {
			ShowInExplorerHandler h = new ShowInExplorerHandler();
			if (h.canExecute(services.rcpSelectionService)) {
				h.execute(services.rcpSelectionService);
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
	 * @param tooltip The Tooltip to be displayed
	 * @param imagePath Path to a 16px Image, ideally via FileTable
	 * @param value The Action to be performed once the button is clicked
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
		// Command Buttons are currently UI Placeholders TODO #34
		btnRedo.setDisable(true);
		btnUndo.setDisable(true);
		
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
		ShowInExplorerHandler h = new ShowInExplorerHandler();
		btnShowExplorer.setDisable(!h.canExecute(services.rcpSelectionService));

	}

}
