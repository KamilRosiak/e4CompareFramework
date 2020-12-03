package de.tu_bs.cs.isf.e4cf.parts.project_explorer;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.WorkspaceFileSystem;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.Directory;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import javafx.embed.swt.SWTFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TextFieldTreeCell;
import org.eclipse.swt.graphics.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;

/**
 * A tree cell that supports dragging
 */
public class CustomTreeCell extends TextFieldTreeCell<FileTreeElement> {

	private TextField editTextField;
	private Map<String, IProjectExplorerExtension> fileExtensions;
	private ServiceContainer services;
	
	public CustomTreeCell(WorkspaceFileSystem workspaceFileSystem, ServiceContainer service, Map<String, IProjectExplorerExtension> fileExtensions) {
		super();
		
		this.services = service;
		this.fileExtensions = fileExtensions;

		setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				event.acceptTransferModes(TransferMode.COPY);
			}
		});

		setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
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
			}
		});
	}
	
	/**
	 * Will be invoked by pressing F2, Enter, double click (default behavior by JavaFX)
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
		setGraphic(getImage(getItem()));
	}


	@Override
	public void updateItem(FileTreeElement item, boolean empty) {
		super.updateItem(item, empty);

		if (!empty) {
			setText(item.toString());			
			setGraphic(getImage(item));
		} else {
			setText("");
			setGraphic(null);
		}
	}

	private void renameItem() {
        String newText = editTextField.getText();
        Path source = FileHandlingUtility.getPath(getItem());
        Path target = source.getParent().resolve(newText);


        File sourceFile = source.toFile();

        if (!sourceFile.isDirectory() || (sourceFile.isDirectory() && sourceFile.list().length == 0)) {
            try {
                Files.move(source, target);
            } catch (IOException e) {
                RCPMessageProvider.errorMessage("Rename File", e.getMessage());            }
        } else {
            // Traverse the file tree and copy each file/directory.
            try {

                Files.walk(source)
                        .forEach(sourcePath -> {
                                Path targetPath = target.resolve(source.relativize(sourcePath));
                                try {
                                    Files.copy(sourcePath, targetPath);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                        });


                Files.walk(source)
                  .sorted(Comparator.reverseOrder())
                  .map(Path::toFile)
                  .forEach(File::delete);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

	private void setupEditTextField() {
		editTextField = new TextField(getItem().toString());
		editTextField.selectAll();
		editTextField.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case ENTER:
					renameItem();
					break;
				default:
					break;
				}
			}
		});
	}


	/**
	 * Returns an appropriate image for a given tree element
	 * @param element tree element
	 * @return an image
	 * TODO: Remove this method from the controller
	 */
	public Node getImage(Object element) {
		Image image = null;

		if (element instanceof FileTreeElement) {
			FileTreeElement fileElement = (FileTreeElement) element;
			if (fileElement.getParent() == null) {
				return null;
			} else if (services.workspaceFileSystem.isProject(fileElement)) {
				image = services.imageService.getImage(null, FileTable.PROJECT_PNG);
			} else if (fileElement.isDirectory()) {
				image = services.imageService.getImage(null, FileTable.FOLDER_PNG);
			} else {
				String fileExtension = fileElement.getExtension();
				// load extended file icons
				if (fileExtensions.containsKey(fileExtension)) {
					image = fileExtensions.get(fileExtension).getIcon(services.imageService);
				} else if (fileExtension.equals(E4CStringTable.FILE_ENDING_XML)) {
					image = services.imageService.getImage(null, FileTable.XML_PNG);
				} else {
					// default file icon
					image = services.imageService.getImage(null, FileTable.FILE_PNG);
				}
			}
		}

		WritableImage fxImage = SWTFXUtils.toFXImage(image.getImageData(), null);

		return new ImageView(fxImage);
	}

}
