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

import org.eclipse.e4.core.contexts.IEclipseContext;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.WorkspaceFileSystem;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.Directory;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.FXMLLoader;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CEventTable;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.tagging.Tag;
import de.tu_bs.cs.isf.e4cf.core.util.tagging.TagService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.controller.CustomTreeCellController;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.StringTable;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.wizards.drop_files.DropFilesDialog.DropMode;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
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
public class CustomTreeCell extends TreeCell<FileTreeElement> {

	private TagService tagService;

	private TextField editTextField;
	private FileImageProvider fileImageProvider;

	/**
	 * Indicates whether a file transfer operation actually changed the filetree.
	 */
	private boolean fileMoved;

	private FXMLLoader<CustomTreeCellController> loader;
	private CustomTreeCellController controller;

	public CustomTreeCell(WorkspaceFileSystem workspaceFileSystem, FileImageProvider fileImageProvider,
			ServiceContainer services, IEclipseContext context) {
		loader = new FXMLLoader<CustomTreeCellController>(context, StringTable.BUNDLE_NAME,
				FileTable.CUSTOM_TREE_CELL_FXML);
		controller = loader.getController();
		this.tagService = services.tagService;

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
			// keeps track from where the drop has been received. this is important for the
			// copying behavior.
			DropMode dropMode = DropMode.COPY;
			/**
			 * Keeps tracks of all directories of the current selection
			 */
			List<File> directories = new ArrayList<File>();

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
							dropMode = DropMode.MOVE;
							/**
							 * If current file is a directory with content it has to have special copying
							 * functionality.
							 */
							if (file.isDirectory() && file.listFiles().length > 0) {
								// we only want the parent since it is full recursive copying. Can be improved
								// later.
								if (!files.contains(file.getParentFile())) {
									directories.add(file);
								}
							} else {
								// if this file is not a parent of a currently selected folder copy it now.
								if (!files.contains(file.getParentFile())) {
									moveFileOrDirectory(Paths.get(file.getAbsolutePath()),
											Paths.get(directory.getAbsolutePath(), file.getName()));
								}

							}
						} else {
							// From file system: Copy File
							dropMode = DropMode.COPY;
							// only copy folder with content, otherwise just handle as a normal file.
							if (file.isDirectory() && file.listFiles().length > 0) {
								directories.add(file);

							} else {
								try {
									workspaceFileSystem.copy(Paths.get(file.getAbsolutePath()),
											Paths.get(directory.getAbsolutePath()));
								} catch (FileAlreadyExistsException already) {
									RCPMessageProvider.errorMessage("File already exsits.",
											"A file with the name " + file.getName() + " exists.");
								}
							}
						}
						success = true;
					} catch (IOException e) {
						success = false;
						e.printStackTrace();
						break;
					}
				}
				/**
				 * If there were directories with content copy them.
				 */
				if (directories.size() > 0) {
					Path[] sources = directories.stream().map(file -> file.toPath()).toArray(Path[]::new);
					// transfer files from system.
					if (dropMode == DropMode.COPY) {
						// dispatch copying to wizard. the wizard keeps track of All directories at
						// once.
						DropElement dropElement = new DropElement(Paths.get(directory.getAbsolutePath()), sources);
						services.eventBroker.post(E4CEventTable.EVENT_DROP_ELEMENT_IN_EXPLORER, dropElement);
					} else {
						// move elements in the filetree full recursive.
						for (Path source : sources) {
							moveFileOrDirectory(source,
									Paths.get(directory.getAbsolutePath()).resolve(source.getFileName()));
						}
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
		setupEditTextField();
		setGraphic(editTextField);
	}

	/**
	 * Will be invoked by pressing ESCAPE Key (default behavior by JavaFX)
	 */
	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setGraphic(loader.getNode());
	}

	@Override
	public void updateItem(FileTreeElement item, boolean empty) {
		super.updateItem(item, empty);

		if (!empty) {
			controller.text.setText(item.toString());
			controller.image.setImage(fileImageProvider.getImage(item));

			ObservableList<Node> tagContainer = controller.tags.getChildren();
			tagContainer.clear();

			// Sort the tags
			List<Tag> tags = tagService.getTags(item);

			// Create UI for the tags
			for (Tag tag : tags) {
				tagContainer.add(tag.getTagIcon());
			}

			setGraphic(loader.getNode());
		} else {
			setGraphic(null);
		}
	}

	private void moveFileOrDirectory(Path source, Path target) {
		File sourceFile = source.toFile();

		if (!sourceFile.isDirectory() || (sourceFile.isDirectory() && sourceFile.list().length == 0)) {
			try {
				Files.move(source, target);
				tagService.moveTags(new Path[] { source, target });
			} catch (FileAlreadyExistsException alreadyExists) {
				RCPMessageProvider.errorMessage("File already exsits.",
						"A file with the name " + sourceFile.getName() + " exists.");
				alreadyExists.printStackTrace();
			} catch (IOException e) {
				RCPMessageProvider.errorMessage("Error when moving a file or directory", e.getMessage());
			}
		} else {
			// Traverse the file tree and copy each file/directory.
			// reset with every iteration the fileMoved so it is specific for every file in
			// the iteration
			fileMoved = true;
			try {
				// walk through all levels -> MAX_VALUE
				Files.walk(source, Integer.MAX_VALUE).forEach(sourcePath -> {
					// if parent did not move, children do not move either
					if (!fileMoved) {
						return;
					}

					Path targetPath = target.resolve(source.relativize(sourcePath));

					// if file is dropped on itself, do nothing
					if (targetPath.equals(sourcePath)) {
						// target did not move
						fileMoved = false;
					} else {
						try {
							Files.copy(sourcePath, targetPath);
							tagService.moveTags(new Path[] { sourcePath, targetPath });
						} catch (FileAlreadyExistsException alreadyExistExc) {
							// file did not move but is already there, so throw an exception only for the
							// first time this occurs.
							if (fileMoved) {
								RCPMessageProvider.errorMessage("File already exsits.",
										"A file with the name " + sourceFile.getName() + " exists.");
							}
							// because file is already there it did not move.
							fileMoved = false;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				});

				// if files have changed there location clean up the old space.
				if (fileMoved) {
					Files.walk(source).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
				}

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
		editTextField = new TextField(getItem().getFileName());
		editTextField.selectAll();
		editTextField.setOnKeyReleased((KeyEvent event) -> {
			if (event.getCode() == KeyCode.ENTER) {
				renameItem();
			}
		});
	}
}
