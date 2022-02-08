package de.tu_bs.cs.isf.e4cf.parts.project_explorer.listeners;

import java.awt.Desktop;
import java.io.IOException;
import java.util.Map;

import org.eclipse.e4.core.contexts.IEclipseContext;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers.ShowInExplorerHandler;
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
			if (target != null) {
				if (!target.isDirectory()) {
					openFile(target);
				}
			}
			event.consume();
			
		} else if (event.getButton().equals(MouseButton.MIDDLE)) {
			FileTreeElement target = services.rcpSelectionService.getCurrentSelectionFromExplorer();
			if (target != null) {
				if (target.isDirectory()) {
					new ShowInExplorerHandler().execute(services);
				}
			}
		}
	}

	private void openFile(FileTreeElement target) {
		if (target != null) {
			String fileExtension = target.getExtension();
			// if a double click action is available for given extension execute it.
			if (fileExtensions.containsKey(fileExtension)) {
				fileExtensions.get(fileExtension).execute(services);
			} else {
				// Open the file in system editor
				java.io.File directoryAsFile = FileHandlingUtility.getPath(target).toFile();
				try {
					Desktop.getDesktop().open(directoryAsFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}