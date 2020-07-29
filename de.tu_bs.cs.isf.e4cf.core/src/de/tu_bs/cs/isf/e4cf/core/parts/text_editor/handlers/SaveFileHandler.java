 
package de.tu_bs.cs.isf.e4cf.core.parts.text_editor.handlers;

import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class SaveFileHandler {
	
	@Execute
	public void execute(@Active MPart part, EPartService partService) {
		partService.savePart(part, true);
	}
	
	
	@CanExecute
	public boolean canExecute() {
		
		return true;
	}
		
}