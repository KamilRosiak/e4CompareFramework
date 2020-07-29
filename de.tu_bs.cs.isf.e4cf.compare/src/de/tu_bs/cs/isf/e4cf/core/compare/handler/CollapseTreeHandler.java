package de.tu_bs.cs.isf.e4cf.core.compare.handler;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import de.tu_bs.cs.isf.e4cf.core.util.services.RCPSelectionService;

/**
 * This handler expands a Tree if selected.
 * @author {Kamil Rosiak}
 *
 */
public class CollapseTreeHandler {
	
	@Execute
	public void execute(ESelectionService selectionService) {
		Tree tree = (Tree) selectionService.getSelection();
		
		tree.setRedraw(false);
		for(TreeItem t: tree.getItems()) {
			t.setExpanded(false);
			colapseRek(t);
		}
		tree.setRedraw(true);
		tree.redraw();	
	}

	public void colapseRek(TreeItem t) {
		for(TreeItem child: t.getItems()) {
			child.setExpanded(false);
			colapseRek(child);
		}
	}
	
	@CanExecute
	public boolean canExecute(ESelectionService selectionService, RCPSelectionService selection) {
		if(selectionService.getSelection() instanceof Tree) {
			return true;
		}
		return false;
	}
}
