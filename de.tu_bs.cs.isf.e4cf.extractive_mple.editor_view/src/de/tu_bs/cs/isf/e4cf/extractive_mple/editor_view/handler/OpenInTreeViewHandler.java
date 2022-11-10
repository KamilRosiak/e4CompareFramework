
package de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.handler;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.util.ArtifactIOUtil;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.extractive_mple.consts.MPLEEditorConsts;

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
	public void execute(ServiceContainer services, ReaderManager readerManager) {
		Tree tree = readerManager.readFile(services.rcpSelectionService.getCurrentSelectionFromExplorer());
		if (tree != null) {
			services.partService.showPart(MPLEEditorConsts.TREE_VIEW_ID);
			services.eventBroker.send(MPLEEditorConsts.SHOW_TREE, tree);
			System.out.println(tree.getRoot().getAllUUIDS(new HashSet<UUID>()).size());
		}
	}

	/**
	 * This method checks if a artifact reader is available for this view
	 * 
	 * @param services
	 * @return
	 */
	@Evaluate
	public boolean isFileReaderAvailable(ServiceContainer services, ReaderManager manager, IEclipseContext context) {
		List<FileTreeElement> selections = services.rcpSelectionService.getCurrentSelectionsFromExplorer();
		if (selections.size() != 1)
			return false;
		if (selections.get(0).isDirectory())
			return true;
		if (null == ArtifactIOUtil.getReaderForType(selections.get(0), context))
			return false;
		return true;
	}
}
