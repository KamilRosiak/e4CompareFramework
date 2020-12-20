package de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller;

import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers.FileImportHandler;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers.NewFolderHandler;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers.RemoveFileCommand;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers.ShowInExplorerHandler;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

public class ProjectExplorerToolBarController {

	private ServiceContainer services;

	private ToolBar bar;

	private Button btnUndo;
	private Button btnRedo;
	private Button btnNewFile;
	private Button btnNewFolder;
	private Button btnImportFiles;
	private Button btnDelete;
	private Button btnShowExplorer;

	public ProjectExplorerToolBarController(ToolBar toolbar, ServiceContainer services, Shell shell) {
		bar = toolbar;
		this.services = services;

		// Search Section
		TextField search = new TextField();
		search.setVisible(false);
		search.setOnAction(actionEvent -> {
			// TODO Perform Filter / send filter event
			System.out.println(search.getText());
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
				// TODO Remove all filters from project view / send event
			}

		});
		bar.getItems().add(searchButton);

		bar.getItems().add(new Separator());

		// Command Actions TODO to enable these buttons command stack systems are
		// required
		ImageView undoImage = services.imageService.getFXImage(null, FileTable.UNDO_PNG);
		btnUndo = new Button("", undoImage);
		btnUndo.setTooltip(new Tooltip("Undo"));
		btnUndo.setDisable(true);
		btnUndo.setOnAction(actionEvent -> {
			System.out.println("Undo");
		});
		bar.getItems().add(btnUndo);

		ImageView redoImage = services.imageService.getFXImage(null, FileTable.REDO_PNG);
		btnRedo = new Button("", redoImage);
		btnRedo.setTooltip(new Tooltip("Redo"));
		btnRedo.setDisable(true);
		btnRedo.setOnAction(actionEvent -> {
			System.out.println("Redo");
		});
		bar.getItems().add(btnRedo);

		bar.getItems().add(new Separator());

		// File Actions
		// TODO New File

		// New Folder
		ImageView newFolderImage = services.imageService.getFXImage(null, FileTable.FOLDER_PNG);
		btnNewFolder = new Button("", newFolderImage);
		btnNewFolder.setTooltip(new Tooltip("Create New Folder"));
		btnNewFolder.setOnAction(actionEvent -> {
			NewFolderHandler handler = new NewFolderHandler();
			if (handler.canExecute(services.rcpSelectionService)) {
				handler.execute(shell, services.dialogService, services.rcpSelectionService, services.imageService,
						services.workspaceFileSystem);
			}
		});
		bar.getItems().add(btnNewFolder);

		// Import Files
		ImageView importFilesImage = services.imageService.getFXImage(null, FileTable.FILE_PNG);
		btnImportFiles = new Button("", importFilesImage);
		btnImportFiles.setTooltip(new Tooltip("Import Files"));
		btnImportFiles.setOnAction(actionEvent -> {
			FileImportHandler h = new FileImportHandler();
			if (h.canExecute(services.rcpSelectionService)) {
				h.execute(services.imageService, services.dialogService, services.rcpSelectionService, shell,
						services.workspaceFileSystem);
			}
		});
		bar.getItems().add(btnImportFiles);

		// Delete
		ImageView deleteImage = services.imageService.getFXImage(null, FileTable.DELETE_PNG);
		btnDelete = new Button("", deleteImage);
		btnDelete.setTooltip(new Tooltip("Delete Selected Files"));
		btnDelete.setOnAction(actionEvent -> {
			RemoveFileCommand h = new RemoveFileCommand();
			if (h.canExecute(services.rcpSelectionService)) {
				h.execute(shell, services.rcpSelectionService, services.imageService, services.workspaceFileSystem);
			}
		});
		bar.getItems().add(btnDelete);

		// Show in Explorer
		ImageView explorerImage = services.imageService.getFXImage(null, FileTable.EXPLORER_PNG);
		btnShowExplorer = new Button("", explorerImage);
		btnShowExplorer.setTooltip(new Tooltip("Show in Explorer"));
		btnShowExplorer.setOnAction(actionEvent -> {
			ShowInExplorerHandler h = new ShowInExplorerHandler();
			if (h.canExecute(services.rcpSelectionService)) {
				h.execute(services.rcpSelectionService);
			}
		});
		bar.getItems().add(btnShowExplorer);

		update();
	}

	/** Update Button states based on their handler */
	public void update() {
		// TODO Command Buttons
		// TODO NewFile
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
