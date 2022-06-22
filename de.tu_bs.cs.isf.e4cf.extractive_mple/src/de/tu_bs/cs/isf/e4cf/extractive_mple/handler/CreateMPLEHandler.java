package de.tu_bs.cs.isf.e4cf.extractive_mple.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.util.ArtifactIOUtil;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLEPlatformUtil;

public class CreateMPLEHandler {

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager) {
		if (services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() > 1) {
			MPLPlatform platform = new MPLPlatform();
			List<Node> variants = new ArrayList<Node>();
			for (FileTreeElement treeElement : services.rcpSelectionService.getCurrentSelectionsFromExplorer()) {
				variants.add(readerManager.readFile(treeElement).getRoot());
			}
			platform.insertVariants(variants);
			MPLEPlatformUtil.storePlatform(
					services.workspaceFileSystem.getWorkspaceDirectory().getAbsolutePath() + "//test.mpl", platform);
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
