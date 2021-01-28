package de.tu_bs.cs.isf.e4cf.parts.project_explorer.listeners;


import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers.NewFolderHandler;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers.RemoveFileCommand;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers.RenameHandler;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers.ShowInExplorerHandler;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

/**
 * Listens for key events on the Project Explorer
 */
public class ProjectExplorerKeyListener implements EventHandler<KeyEvent> {
	
	// Eclipse context for commands
	IEclipseContext eclipseContext;
	
	@Inject
	ServiceContainer services;
	
	// Define key combinations here
	KeyCombination kcRename = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
	KeyCombination kcNewFolder = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
	KeyCombination kcExplorer = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.ALT_DOWN);
	
	public ProjectExplorerKeyListener(IEclipseContext  eclipseContext) {
		this.eclipseContext = eclipseContext;
	}
	
	@Override
	public void handle(KeyEvent event) {
		
		// Handle defined Key Combos, otherwise single key events
		if (kcRename.match(event)) {
			// Rename combo
			renameFile();
			
		} else if (kcNewFolder.match(event)) {
			// New Folder Combo
			newFolder();
			
		} else if (kcExplorer.match(event)) {
			// Show in Explorer Combo
			showInExplorer();
			
		} else {
			
			// Handle single Key Events in switch case
			KeyCode keyCode = event.getCode();
			switch (keyCode) {
				case DELETE:
					removeFile();
					break;
					
				case F2:
					renameFile();
					break;
					
				default:
					// do nothing
			}
		}
	}
	
	/**
	 * Invokes the RenameFileWizard
	 */
	private void renameFile() {
		RenameHandler renameHandler = new RenameHandler();
		renameHandler = ContextInjectionFactory.make(RenameHandler.class, eclipseContext);
		ContextInjectionFactory.invoke(renameHandler, Execute.class, eclipseContext);
	}

	/**
	 * This method removes a file from explorer 
	 */
	private void removeFile() {
		RemoveFileCommand removeFileCommand = new RemoveFileCommand();
		removeFileCommand = ContextInjectionFactory.make(RemoveFileCommand.class, eclipseContext);
		ContextInjectionFactory.invoke(removeFileCommand, Execute.class, eclipseContext);
	}
	
	/**
	 * This method creates a new folder
	 */
	private void newFolder() {
		NewFolderHandler handler = new NewFolderHandler();
		handler = ContextInjectionFactory.make(NewFolderHandler.class, eclipseContext);
		ContextInjectionFactory.invoke(handler, Execute.class, eclipseContext);
	}
	
	/**
	 * This method opens the selected file in explorer
	 */
	private void showInExplorer() {
		ShowInExplorerHandler handler = new ShowInExplorerHandler();
		handler = ContextInjectionFactory.make(ShowInExplorerHandler.class, eclipseContext);
		ContextInjectionFactory.invoke(handler, Execute.class, eclipseContext);
	}

}
