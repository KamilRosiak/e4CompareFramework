
package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.handler;

import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.util.ArtifactIOUtil;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

/**
 * This handler opens a given file in a tree view if a adapter exists
 * 
 * @author Kamil Rosiak
 *
 */
public class OpenInTreeViewHandler {

    /**
     * starting TreeView with a file from the explorer and sending an event
     * 
     * @param services
     */
    @Execute
    public void execute(ServiceContainer services) {
	Tree tree = ArtifactIOUtil.parseArtifact(services.rcpSelectionService.getCurrentSelectionFromExplorer());
	if (tree != null) {
	    services.partService.showPart(DSEditorST.TREE_VIEW_ID);
	    services.eventBroker.send(DSEditorST.INITIALIZE_TREE_EVENT, tree);
	}
    }
    
    /**
     * This method checks if a artifact reader is available for this view
     * @param services
     * @return
     */
    @Evaluate
    public boolean isFileReaderAvailable(ServiceContainer services) {
	Tree tree = ArtifactIOUtil.parseArtifact(services.rcpSelectionService.getCurrentSelectionFromExplorer());
	if (tree == null) {
	    return false;
	} else {
	    return true;
	}
    }
}
