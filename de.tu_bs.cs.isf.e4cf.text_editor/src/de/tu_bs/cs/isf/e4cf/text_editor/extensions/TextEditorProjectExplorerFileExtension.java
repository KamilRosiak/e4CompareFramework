package de.tu_bs.cs.isf.e4cf.text_editor.extensions;

import org.eclipse.swt.graphics.Image;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;
import de.tu_bs.cs.isf.e4cf.text_editor.stringtable.EditorST;

public abstract class TextEditorProjectExplorerFileExtension implements IProjectExplorerExtension {

	@Override
	public abstract Image getIcon(RCPImageService imageService);
	
	@Override
	public void execute(ServiceContainer container) {
		container.eventBroker.send(EditorST.FILE_OPENED,
				container.rcpSelectionService.getCurrentSelectionFromExplorer());
	}

}
