package de.tu_bs.cs.isf.e4cf.parts.project_explorer;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.WorkspaceFileSystem;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.Directory;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

/**
 * A tree cell that supports dragging
 */
public class CustomTreeCell extends TextFieldTreeCell<FileTreeElement> {

	private TextField editTextField;
	private FileImageProvider fileImageProvider;

	public CustomTreeCell(WorkspaceFileSystem workspaceFileSystem, FileImageProvider fileImageProvider,
			ServiceContainer services) {
		this.fileImageProvider = fileImageProvider;
		
		// Allow Drops on Directory TreeItems but not on files
		setOnDragOver((DragEvent event) -> {
			if (getTreeItem().getValue() instanceof Directory)
				event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			else
				event.acceptTransferModes(TransferMode.NONE);
		});

		setOnDragDropped((DragEvent event) -> {
			TreeItem<FileTreeElement> currentItem = getTreeItem();
			Directory directory = (Directory) currentItem.getValue();

			final Dragboard db = event.getDragboard();
			boolean success = false;

			if (db.hasFiles()) {
				List<java.io.File> files = db.getFiles();

				// Prevent Dropping on a directory contained in drag source
				if (files.contains(directory.getFile().toFile())) {
					RCPMessageProvider.errorMessage("Drag and Drop Error",
							"Drop-Target is not allowed to be contained in Drag-Sources");
					event.setDropCompleted(false);
					event.consume();
					return;
				}

				for (java.io.File file : files) {
					try {
						// Determine if a drop originates from tree or from file system
						// This can not be done via transfer mode!
						if (event.getGestureSource() instanceof CustomTreeCell) {
							// In-Tree: Perform Move
							moveFileOrDirectory(Paths.get(file.getAbsolutePath()),
									Paths.get(directory.getAbsolutePath(), file.getName()));
						} else {
							// From file system: Copy File
							workspaceFileSystem.copy(Paths.get(file.getAbsolutePath()),
									Paths.get(directory.getAbsolutePath()));
						}
						success = true;
					} catch (FileAlreadyExistsException e) {
						success = false;
						RCPMessageProvider.errorMessage("File already exsits.",
								"A file with the name " + file + " exists.");
						break;
					} catch (IOException e) {
						success = false;
						e.printStackTrace();
						break;
					}
				}
			}
			event.setDropCompleted(success);
			event.consume();
		});

		// Start Drag
		setOnDragDetected((MouseEvent event) -> {
			Dragboard db = startDragAndDrop(TransferMode.MOVE);

			ClipboardContent content = new ClipboardContent();

			ArrayList<java.io.File> sources = new ArrayList<java.io.File>();

			for (FileTreeElement entry : services.rcpSelectionService.getCurrentSelectionsFromExplorer()) {
				sources.add(new java.io.File(entry.getAbsolutePath()));
			}

			content.putFiles(sources);

			db.setContent(content);

			event.consume();
		});

	}

	/**
	 * Will be invoked by pressing F2, Enter, double click (default behavior by
	 * JavaFX)
	 */
	@Override
	public void startEdit() {
		super.startEdit();
		setText("");
		setupEditTextField();
		setGraphic(editTextField);
	}

	/**
	 * Will be invoked by pressing ESCAPE Key (default behavior by JavaFX)
	 */
	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setText(getItem().toString());
		setGraphic(fileImageProvider.getImage(getItem()));
	}

	@Override
	public void updateItem(FileTreeElement item, boolean empty) {
		super.updateItem(item, empty);

		if (!empty) {
			setText(item.toString());
			setGraphic(fileImageProvider.getImage(item));
		} else {
			setText("");
			setGraphic(null);
		}
	}

	private void moveFileOrDirectory(Path source, Path target) {
		File sourceFile = source.toFile();

		if (!sourceFile.isDirectory() || (sourceFile.isDirectory() && sourceFile.list().length == 0)) {
			try {
				Files.move(source, target);
			} catch (IOException e) {
				RCPMessageProvider.errorMessage("Error when moving a file or directory", e.getMessage());
			}
		} else {
			// Traverse the file tree and copy each file/directory.
			try {

				Files.walk(source).forEach(sourcePath -> {
					Path targetPath = target.resolve(source.relativize(sourcePath));
					try {
						Files.copy(sourcePath, targetPath);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});

				Files.walk(source).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void renameItem() {
		String newText = editTextField.getText();
		Path source = FileHandlingUtility.getPath(getItem());
		Path target = source.getParent().resolve(newText);

		moveFileOrDirectory(source, target);
	}

	private void setupEditTextField() {
		editTextField = new TextField(getItem().toString());
		editTextField.selectAll();
		editTextField.setOnKeyReleased((KeyEvent event) -> {
			if (event.getCode() == KeyCode.ENTER) {
				renameItem();
			}
		});
	}
}
