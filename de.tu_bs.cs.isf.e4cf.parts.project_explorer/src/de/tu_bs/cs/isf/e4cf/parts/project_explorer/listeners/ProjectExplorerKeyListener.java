package de.tu_bs.cs.isf.e4cf.parts.project_explorer.listeners;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

import de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers.RemoveFileCommand;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers.RenameHandler;

public class ProjectExplorerKeyListener implements KeyListener {
	IEclipseContext  eclipseContext;

	public ProjectExplorerKeyListener(IEclipseContext  eclipseContext) {
		this.eclipseContext = eclipseContext;

	}
		
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.keyCode == SWT.DEL) {
			removeFile();
		} else if(e.keyCode == 114 /**Q**/ && e.stateMask == SWT.CTRL) {
			renameFile();
		}
		
	}


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

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
