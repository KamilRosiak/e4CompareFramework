package de.tu_bs.cs.isf.e4cf.extractive_mple.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Execute;

import com.opencsv.CSVWriter;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writer.TreeWriter;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.util.ArtifactIOUtil;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLEPlatformUtil;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;

public class CreateMPLEHandler {

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager, IEclipseContext context) {
		if (services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() > 0) {

			MPLPlatform platform = new MPLPlatform();
			List<Tree> variants = new ArrayList<Tree>();
			// read all variants
			for (FileTreeElement treeElement : services.rcpSelectionService.getCurrentSelectionsFromExplorer()) {
				variants.add(readerManager.readFile(treeElement));
			}

			platform.insertVariants(variants);

			MPLEPlatformUtil.storePlatform(
					services.workspaceFileSystem.getWorkspaceDirectory().getAbsolutePath() + "//" + "clone_model.mpl",
					platform);
		}
	}

	public static void removeRandomVariants(List<Tree> trees) {
		Random r = new Random();
		int times = r.nextInt(trees.size() - 1) + 1;

		for (int i = 0; i < times; i++) {
			int randomIndex = r.nextInt(trees.size() - 1) + 1;
			trees.remove(randomIndex);
		}
	}

	/**
	 * Create a UTF8 CSV Writer with semicolon as separation symbol
	 */
	public static CSVWriter creatCSVWriter(File file) {
		CSVWriter writer = null;
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),
					StandardCharsets.UTF_8);
			writer = new CSVWriter(outputStreamWriter, ';', '"', '/', "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer;
	}

	private void storePlatformTree(MPLPlatform platform, IEclipseContext context, String name,
			ServiceContainer services) {
		TreeWriter treeWriter = new TreeWriter();
		ContextInjectionFactory.inject(treeWriter, context);
		treeWriter.witeArtifact(new TreeImpl(name, platform.model),
				services.workspaceFileSystem.getWorkspaceDirectory().getAbsolutePath() + "//" + name);
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
