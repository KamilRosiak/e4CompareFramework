package de.tu_bs.cs.isf.e4cf.text_editor.project_explorer_file_extension;

import org.eclipse.swt.graphics.Image;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;
import de.tu_bs.cs.isf.e4cf.text_editor.stringtable.EditorST;

public class JavaFileExtension implements IProjectExplorerExtension {
	
	@Override
	public Image getIcon(RCPImageService imageService) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(ServiceContainer container) {
		container.eventBroker.send(EditorST.JAVA_FILE_OPENED, container.rcpSelectionService.getCurrentSelectionFromExplorer());
	}
}