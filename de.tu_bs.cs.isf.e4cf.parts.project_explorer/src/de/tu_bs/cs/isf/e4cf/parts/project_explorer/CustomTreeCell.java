package de.tu_bs.cs.isf.e4cf.parts.project_explorer;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Paths;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.WorkspaceFileSystem;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.Directory;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

/**
 * A tree cell that supports dragging
 */
public class CustomTreeCell extends TextFieldTreeCell<FileTreeElement> {

	public CustomTreeCell(WorkspaceFileSystem workspaceFileSystem) {
		super();

		setOnDragOver((DragEvent event) -> event.acceptTransferModes(TransferMode.COPY));

		setOnDragDropped((DragEvent event) -> {
			TreeItem<FileTreeElement> currentItem = getTreeItem();
			Directory directory = (Directory) currentItem.getValue();

			final Dragboard db = event.getDragboard();
			boolean success = false;
			if (db.hasFiles()) {
				List<java.io.File> files = db.getFiles();
				for (java.io.File file : files) {
					try {
						workspaceFileSystem.copy(Paths.get(file.getAbsolutePath()),
								Paths.get(directory.getAbsolutePath()));
						success = true;
					} catch (FileAlreadyExistsException e) {
						RCPMessageProvider.errorMessage("File already exsits.",
								"A file with the name " + file + " exists.");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			event.setDropCompleted(success);
			event.consume();
		});
	}
}
