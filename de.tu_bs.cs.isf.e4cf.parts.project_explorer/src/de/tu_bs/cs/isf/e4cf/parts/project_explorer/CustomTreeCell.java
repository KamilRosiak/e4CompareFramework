package de.tu_bs.cs.isf.e4cf.parts.project_explorer;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.WorkspaceFileSystem;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.Directory;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import javafx.geometry.Insets;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * A tree cell that supports dragging
 */
public class CustomTreeCell extends TextFieldTreeCell<FileTreeElement> {

	private TextField editTextField;
	private FileImageProvider fileImageProvider;

	public CustomTreeCell(WorkspaceFileSystem workspaceFileSystem, FileImageProvider fileImageProvider) {
		this.fileImageProvider = fileImageProvider;

		setOnDragOver((DragEvent event) -> event.acceptTransferModes(TransferMode.COPY, TransferMode.MOVE));

		setOnDragDropped((DragEvent event) -> {
			TreeItem<FileTreeElement> currentItem = getTreeItem();
			Directory directory = (Directory) currentItem.getValue();

			final Dragboard db = event.getDragboard();
			boolean success = false;

			// For drops in the tree we just get a String of the sources absolute path
			if (db.hasString()) {
				String path = db.getString();

				// Ignore drop on the same element that is displayed
				if (path == currentItem.getValue().getAbsolutePath()) {
					event.setDropCompleted(success);
					event.consume();
					return;
				}

				Path source = Paths.get(path);
				Path target = Paths.get(directory.getAbsolutePath()).resolve(source.getFileName());
				moveFileOrDirectory(source, target);

				success = true;
			}

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

		setOnDragDetected((MouseEvent event) -> {
			TreeItem<FileTreeElement> currentItem = getTreeItem();
			Dragboard db = startDragAndDrop(TransferMode.MOVE);

			ClipboardContent content = new ClipboardContent();
			content.putString(currentItem.getValue().getAbsolutePath());
			db.setContent(content);

			event.consume();
		});

		setOnDragEntered((DragEvent event) -> {
			Background background = new Background(
					new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY));

			if (event.getDragboard().hasString()) {
				if (event.getGestureSource() instanceof CustomTreeCell) {
					CustomTreeCell source = (CustomTreeCell) event.getGestureSource();
					// Don't color if we are the source
					if (!source.getTreeItem().equals(getTreeItem())) {
						setBackground(background);
					}
				} else {
					setBackground(background);
				}
			}

			event.consume();
		});

		setOnDragExited((DragEvent event) -> {
			setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
			setTextFill(Color.BLACK);
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
