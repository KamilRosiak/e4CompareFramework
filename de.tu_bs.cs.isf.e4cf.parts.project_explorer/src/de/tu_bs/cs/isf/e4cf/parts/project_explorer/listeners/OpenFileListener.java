package de.tu_bs.cs.isf.e4cf.parts.project_explorer.listeners;

import java.util.Map;

import javax.inject.Inject;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.parts.project_explorer.interfaces.IProjectExplorerExtension;

public class OpenFileListener implements IDoubleClickListener {
	@Inject private ServiceContainer container;
	private Map<String,IProjectExplorerExtension> fileExtensions;
	
	public OpenFileListener(Map<String, IProjectExplorerExtension> fileExtensions) {
		this.fileExtensions = fileExtensions;
	}
	
	@Override
	public void doubleClick(DoubleClickEvent event) {	
		FileTreeElement target = container.rcpSelectionService.getCurrentSelectionFromExplorer();
		if(target != null && !target.isDirectory()) {
			String path = target.getAbsolutePath();
			String fileExtension = path.substring(path.lastIndexOf(".")+1);
			//if a double click action is available for given extension execute it.
			if(fileExtensions.containsKey(fileExtension)) {
				fileExtensions.get(fileExtension).execute(container);
			}
		}
	}
}
