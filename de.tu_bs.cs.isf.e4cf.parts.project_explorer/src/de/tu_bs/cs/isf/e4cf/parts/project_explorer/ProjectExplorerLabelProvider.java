package de.tu_bs.cs.isf.e4cf.parts.project_explorer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.WorkspaceFileSystem;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CStringTable;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;

/**
 * The label provider of the project explorer defines what kind of
 * representation the loaded items becomes
 * 
 * @author {Kamil Rosiak}
 *
 */
public class ProjectExplorerLabelProvider implements ILabelProvider {
	private final List<ILabelProviderListener> listeners = new ArrayList<ILabelProviderListener>();
	private RCPImageService imageService;
	private WorkspaceFileSystem workspaceFileSystem;
	private Map<String, IProjectExplorerExtension> fileExtensions;

	public ProjectExplorerLabelProvider(RCPImageService imageService, WorkspaceFileSystem workspaceFileSystem,
			Map<String, IProjectExplorerExtension> fileExtensions) {
		this.imageService = imageService;
		this.workspaceFileSystem = workspaceFileSystem;
		this.fileExtensions = fileExtensions;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	/**
	 * This method gives the different files a different look
	 */
	@Override
	public Image getImage(Object element) {
		Image image = null;

		if (element instanceof FileTreeElement) {
			FileTreeElement fileElement = (FileTreeElement) element;
			if (workspaceFileSystem.isProject(fileElement)) {
				image = imageService.getImage(null, "icons/Explorer_View/items/project16.png");
			} else if (fileElement.isDirectory()) {
				image = imageService.getImage(null, "icons/Explorer_View/items/folder16.png");
			} else {
				String fileExtension = fileElement.getExtension();
				// load extended file icons
				if (fileExtensions.containsKey(fileExtension)) {
					image = fileExtensions.get(fileExtension).getIcon(imageService);
				} else if (fileExtension.equals(E4CStringTable.FILE_ENDING_XML)) {
					image = imageService.getImage(null, "icons/Explorer_View/items/xml16.png");
				} else {
					// default file icon
					image = imageService.getImage(null, "icons/Explorer_View/items/file16.png");
				};
			}
		}
		return image;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof FileTreeElement) {
			return FileHandlingUtility.getPath((FileTreeElement) element).getFileName().toString();
		} else {
			return "placeholder";
		}
	}

}
