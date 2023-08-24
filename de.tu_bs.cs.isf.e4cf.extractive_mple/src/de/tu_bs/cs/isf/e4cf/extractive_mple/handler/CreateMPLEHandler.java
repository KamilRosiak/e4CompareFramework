package de.tu_bs.cs.isf.e4cf.extractive_mple.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.util.ArtifactIOUtil;
import de.tu_bs.cs.isf.e4cf.compare.preferences.dialog.ComparisonPrefDialog;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLEPlatformUtil;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;

public class CreateMPLEHandler {

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager, IEclipseContext context) {
		try {
			if (services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() > 0) {
				MPLPlatform platform = new MPLPlatform();

				List<Tree> variants = new ArrayList<Tree>();
				// read all variants
				for (FileTreeElement treeElement : services.rcpSelectionService.getCurrentSelectionsFromExplorer()) {
					variants.add(readerManager.readFile(treeElement));
				}
				
				variants.forEach(variant -> {
					System.out.println(variant.getTreeName());
					System.out.println(variant.cloneTree().getRoot().getNodesOfType(NodeType.COMPILATION_UNIT.toString()).size());
					System.out.println(variant.cloneTree().getRoot().getNodesOfType(NodeType.METHOD_DECLARATION.toString()).size());
				});
				
				new ComparisonPrefDialog(platform.prefs, variants.get(0).getRoot());

				new Job("create mpl job") {
					@Override
					protected IStatus run(IProgressMonitor monitor) {
						platform.insertVariants(variants, services);
						MPLEPlatformUtil
								.storePlatform(services.workspaceFileSystem.getWorkspaceDirectory().getAbsolutePath()
										+ "//" + platform.prefs.getFileName() + ".mpl", platform);
						return Status.OK_STATUS;
					}
				}.schedule();

			}
		} catch (Exception e) {
			e.printStackTrace();
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
