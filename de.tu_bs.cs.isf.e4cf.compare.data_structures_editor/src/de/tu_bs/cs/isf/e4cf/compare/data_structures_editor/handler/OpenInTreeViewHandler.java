
package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.handler;

import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.TextReader;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DataStructuresEditorST;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

public class OpenInTreeViewHandler {

	/**
	 * starting TreeView with a file from the explorer and sending an event
	 * 
	 * @param services
	 */
	@Execute
	public void execute(ServiceContainer services) {
		FileTreeElement element = services.rcpSelectionService.getCurrentSelectionFromExplorer();
		
		if (element.getExtension().equals("txt")) {
			TextReader reader = new TextReader();
			services.partService.showPart(DataStructuresEditorST.TREE_VIEW_ID);
			services.eventBroker.send(DataStructuresEditorST.INITIALIZE_TREE_EVENT, reader.readArtifact(element));
		}
	}
}
