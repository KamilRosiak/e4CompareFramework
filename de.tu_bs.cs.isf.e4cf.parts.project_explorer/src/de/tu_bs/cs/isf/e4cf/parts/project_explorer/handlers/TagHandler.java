package de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers;

import java.util.List;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IHandler;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.tagging.dialog.TagDialog;

/**
 * A handler that opens a dialog that lets the user see all tags and manage them
 * with basic CRUD functionality.
 */
public class TagHandler implements IHandler {

	@Execute
	public void execute(IEclipseContext context, ServiceContainer services, Shell shell) {
		List<FileTreeElement> selection = services.rcpSelectionService.getCurrentSelectionsFromExplorer();
		TagDialog dialog = new TagDialog(context, services, selection);
		dialog.open();
	}

	@CanExecute
	public boolean canExecute(RCPSelectionService selectionService) {
		List<FileTreeElement> selection = selectionService.getCurrentSelectionsFromExplorer();
		return selection != null && !selection.isEmpty();
	}
}
