package de.tu_bs.cs.isf.e4cf.parts.project_explorer;

import java.util.Map;

import org.eclipse.swt.graphics.Image;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.stringtable.FileTable;
import javafx.embed.swt.SWTFXUtils;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

/*
 * Provides Images as a node for a given FileTreeElement
 */
public class FileImageProvider {

	private Map<String, IProjectExplorerExtension> fileExtensions;
	private ServiceContainer services;

	public FileImageProvider(ServiceContainer services, Map<String, IProjectExplorerExtension> fileExtensions) {
		this.services = services;
		this.fileExtensions = fileExtensions;
	}

	/**
	 * Returns an appropriate image for a given tree element
	 * 
	 * @param element tree element
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
