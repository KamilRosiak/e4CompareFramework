package de.tu_bs.cs.isf.e4cf.core.parts.console_view.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.swt.widgets.Text;

public class ClearConsolHandler {
	
	@Execute
	public void execute(ESelectionService selectionSerivce) {
		if(selectionSerivce.getSelection() instanceof Text) {
			((Text)selectionSerivce.getSelection()).setText("");
		}
	}
}
