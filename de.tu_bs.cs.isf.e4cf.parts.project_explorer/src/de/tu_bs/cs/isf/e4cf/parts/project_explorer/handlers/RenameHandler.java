package de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers;

import java.util.List;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CEventTable;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IHandler;

/**
 * A handler that passes on a renaming event to the event broker.
 */
public class RenameHandler implements IHandler {

	@Execute
	public void execute(IEclipseContext context, ServiceContainer services, Shell shell) {
		services.eventBroker.send(E4CEventTable.EVENT_RENAME_PROJECT_EXPLORER_ITEM, null);

	}

	@CanExecute
	public boolean canExecute(RCPSelectionService selectionService) {
		List<FileTreeElement> selection = selectionService.getCurrentSelectionsFromExplorer();
		return selection != null && selection.size() == 1;
	}
}
