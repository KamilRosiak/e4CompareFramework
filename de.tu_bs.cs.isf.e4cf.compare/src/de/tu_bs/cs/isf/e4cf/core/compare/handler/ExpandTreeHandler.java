package de.tu_bs.cs.isf.e4cf.core.compare.handler;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * This handler expands a Tree if selected.
 * @author {Kamil Rosiak}
 *
 */
public class ExpandTreeHandler {
	
	@Execute
	public void execute(ESelectionService selectionService) {
		Tree tree = (Tree)selectionService.getSelection();
		
		tree.setRedraw(false);
		for(TreeItem t :tree.getItems()) {
			t.setExpanded(true);
			expandRek(t);
		}
		tree.setRedraw(true);
		tree.redraw();
	}
	
	public void expandRek(TreeItem t) {
		for(TreeItem child: t.getItems()) {
			child.setExpanded(true);
			expandRek(child);
		}
	}
	
	@CanExecute
	public boolean canExecute(ESelectionService selectionService) {
		if(selectionService.getSelection() instanceof Tree) {
			return true;
		}
		return false;
	}
}
