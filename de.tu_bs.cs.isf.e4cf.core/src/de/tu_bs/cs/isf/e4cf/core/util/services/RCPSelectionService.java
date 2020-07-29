package de.tu_bs.cs.isf.e4cf.core.util.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.IStructuredSelection;

import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;

@Creatable
@Singleton
public class RCPSelectionService {
	private static final String PROJECT_EXPLORER_PART_ID = "de.tu_bs.cs.isf.e4cf.parts.project_explorer";
	@Inject public ESelectionService selectionService;
	
	public FileTreeElement getCurrentSelectionFromExplorer() {
		List<FileTreeElement> selections = getCurrentSelectionsFromExplorer();
		if (!selections.isEmpty()) {
			return selections.get(0);
		} else {
			return null;			
		}
		
	}
	
	public List<FileTreeElement> getCurrentSelectionsFromExplorer() {
		List<FileTreeElement> selection = new ArrayList<>();
		Object selectionObject = selectionService.getSelection(PROJECT_EXPLORER_PART_ID);
		if(selectionObject != null && selectionObject instanceof IStructuredSelection) {
			IStructuredSelection selectionFromExplorer = (IStructuredSelection) selectionObject;
			for(Object o : selectionFromExplorer.toList()) {
				if (o instanceof FileTreeElement) selection.add((FileTreeElement) o); 	
			}
		}
		return selection;
	}
	
	public String getExtensionFromCurrentSelection() {
		FileTreeElement selection = getCurrentSelectionFromExplorer();
		String extension = null;
		if (selection != null) {
			String path = selection.getAbsolutePath();
			extension = path.substring(path.lastIndexOf(".")+1);		
		}
		return extension;
	}
	
	/**
	 * Gets the file extension and the attribute from the Project explorer extension point.
	 */
	public List<Object> getAttributes(String extensionPoint, String executableAttribute) {
		IConfigurationElement[] configs = RCPContentProvider.getIConfigurationElements(extensionPoint);	
		List<Object> attributes = new ArrayList<Object>();
		for(IConfigurationElement config : configs) {
			try {
				Object attr = config.createExecutableExtension(executableAttribute);
				attributes.add(attr);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return attributes;
	}

}
