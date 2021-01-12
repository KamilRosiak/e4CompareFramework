package de.tu_bs.cs.isf.e4cf.parts.project_explorer.listeners;

import java.util.Map;

import org.eclipse.e4.core.contexts.IEclipseContext;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class OpenFileListener implements EventHandler<MouseEvent> {

	IEclipseContext eclipseContext;
	private ServiceContainer services;
	private Map<String, IProjectExplorerExtension> fileExtensions;

	public OpenFileListener(IEclipseContext eclipseContext, Map<String, IProjectExplorerExtension> fileExtensions,
			ServiceContainer services) {
		this.eclipseContext = eclipseContext;
		this.fileExtensions = fileExtensions;
		this.services = services;
	}

	/**
	 * Add double-click listener to the files of the project explorer
	 * 
	 * @param eclipseContext
	 */
	@Override
	public void handle(MouseEvent event) {
		// left mouse button and double click required
		if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
			// should not trigger on directories
			FileTreeElement target = services.rcpSelectionService.getCurrentSelectionFromExplorer();
			if (target != null && !target.isDirectory()) {
				openFile(target);
			}
		}
	}

	private void openFile(FileTreeElement target) {
		if (target != null) {
			String fileExtension = target.getExtension();
			// if a double click action is available for given extension execute it.
			if (fileExtensions.containsKey(fileExtension)) {
				fileExtensions.get(fileExtension).execute(services);
			}
		}

	}
}