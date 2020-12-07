package de.tu_bs.cs.isf.e4cf.parts.project_explorer.handlers;

import java.util.List;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.stringtable.E4CEventTable;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;

/*
 * A handler that passes on a renaming event to the event broker.
 */
public class RenameHandler {

	@Execute
	public void execute(IEventBroker eventBroker) {
		eventBroker.send(E4CEventTable.EVENT_RENAME_PROJECT_EXPLORER_ITEM, null);
	}

	@CanExecute
	public boolean canExecute(RCPSelectionService selectionService) {
		List<FileTreeElement> selection = selectionService.getCurrentSelectionsFromExplorer();
		return selection != null && selection.size() == 1;
	}
}