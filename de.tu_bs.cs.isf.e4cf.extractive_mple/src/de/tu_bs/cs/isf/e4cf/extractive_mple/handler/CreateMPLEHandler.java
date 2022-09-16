package de.tu_bs.cs.isf.e4cf.extractive_mple.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writter.TreeWritter;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.util.ArtifactIOUtil;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLEPlatformUtil;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;

public class CreateMPLEHandler {

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager, IEclipseContext context) {
		if (services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() > 0) {
			int numberPermutations = 1;

			for (int i = 0; i < numberPermutations; i++) {
				MPLPlatform platform = new MPLPlatform();
				List<Tree> variants = new ArrayList<Tree>();

				for (FileTreeElement treeElement : services.rcpSelectionService.getCurrentSelectionsFromExplorer()) {
					variants.add(readerManager.readFile(treeElement));
				}
				variants.sort((a, b) -> {
					return -Integer.compare(a.getRoot().getAmountOfNodes(0), b.getRoot().getAmountOfNodes(0));
				});
				// Collections.shuffle(variants);

				platform.insertVariants(variants);
				MPLEPlatformUtil.storePlatform(services.workspaceFileSystem.getWorkspaceDirectory().getAbsolutePath()
						+ "//" + variants.get(0).getTreeName() + "_" + i + ".mpl", platform);
				System.out.println("_______________________run______________");
				printPlatform(platform);
			}
			// Compare resulting trees
		}
	}

	private void storePlatformTree(MPLPlatform platform, IEclipseContext context, String name,
			ServiceContainer services) {
		TreeWritter treeWriter = new TreeWritter();
		ContextInjectionFactory.inject(treeWriter, context);
		treeWriter.witeArtifact(new TreeImpl(name, platform.model),
				services.workspaceFileSystem.getWorkspaceDirectory().getAbsolutePath() + "//" + name);
	}

	private void printPlatform(MPLPlatform platform) {
		System.out.println("Platform number of nodes: " + platform.model.getAmountOfNodes(0));
		Map<UUID, Integer> cloneClasses = new HashMap<UUID, Integer>();
		platform.configurations.forEach(config -> {
			config.getComponentConfigurations().forEach(cloneConfig -> {
				if (!cloneClasses.containsKey(cloneConfig.componentUUID)) {
					cloneClasses.put(cloneConfig.componentUUID, 1);
				} else {
					cloneClasses.put(cloneConfig.componentUUID, cloneClasses.get(cloneConfig.componentUUID) + 1);
				}
			});
		});

		System.out.println("Total Clone Classes: " + cloneClasses.size());
		System.out.println("Clone Configurations");
		cloneClasses.entrySet().forEach(e -> {
			System.out.println("CloneClassId: " + e.getKey() + " number of configs: " + e.getValue());
		});
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
