package de.tu_bs.cs.isf.e4cf.text_editor.project_explorer_file_extension;

import org.eclipse.swt.graphics.Image;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.services.RCPImageService;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;

public class JavaFileExtension implements IProjectExplorerExtension {
	public static final String JAVA_FILE_OPENED = "OPEN_JAVA_THNKS";
	
	@Override
	public Image getIcon(RCPImageService imageService) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(ServiceContainer container) {
		// TODO Auto-generated method stub
		System.out.println("Vorher JAVA");	//placeholder
		container.eventBroker.send(JAVA_FILE_OPENED, container.rcpSelectionService.getCurrentSelectionFromExplorer());
		System.out.println("Nachher JAVA"); 	//placeholder
	}
}