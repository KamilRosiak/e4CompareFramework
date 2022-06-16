package de.tu_bs.cs.isf.e4cf.extractive_mple.structure.handler;

import java.io.File;
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
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLEPlatform;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLEPlatformUtil;

public class CreateMPLEHandler {

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager) {
		if (services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() > 1) {
			MPLEPlatform platform = new MPLEPlatform();
			List<Node> variants = new ArrayList<Node>();
			variants.add(
					readerManager.readFile(services.rcpSelectionService.getCurrentSelectionFromExplorer()).getRoot());
			platform.insertVariants(variants);
			MPLEPlatformUtil.storePlatform(services.workspaceFileSystem.getWorkspaceDirectory().getAbsolutePath()+"//test.mpl", platform);
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
