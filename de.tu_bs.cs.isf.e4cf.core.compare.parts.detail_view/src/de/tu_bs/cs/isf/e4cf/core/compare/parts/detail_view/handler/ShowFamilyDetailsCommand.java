package de.tu_bs.cs.isf.e4cf.core.compare.parts.detail_view.handler;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.core.compare.parts.detail_view.util.DetailViewStringTable;
import de.tu_bs.cs.isf.e4cf.core.compare.string_table.E4CompareEventTable;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPPartService;

public class ShowFamilyDetailsCommand {

	@Execute
	public void execute(ESelectionService selectionService, RCPPartService partService,IEventBroker eventBroker) {
		partService.showPart(DetailViewStringTable.FAMILYMODE_DETAIL_VIEW_ID);
		eventBroker.send(E4CompareEventTable.SHOW_DETAIL_EVENT, getAbstractContainer(selectionService));
	}
	
	@CanExecute
	public boolean canExecute(ESelectionService selectionService) {
		return getAbstractContainer(selectionService) != null;
	}
	
	public Object getAbstractContainer(ESelectionService selectionService) {
		if(selectionService.getSelection() instanceof Tree) {
			Tree tree = (Tree)selectionService.getSelection();
			if(tree.getSelectionCount() > 0) {
				TreeItem selection = (TreeItem)tree.getSelection()[0];
				return selection.getData();
			}
			return null;
		}
		return null;
	}

}
