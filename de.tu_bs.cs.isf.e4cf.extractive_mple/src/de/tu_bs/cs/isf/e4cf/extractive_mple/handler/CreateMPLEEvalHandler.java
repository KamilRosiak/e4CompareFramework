package de.tu_bs.cs.isf.e4cf.extractive_mple.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Evaluate;
import org.eclipse.e4.core.di.annotations.Execute;

import com.opencsv.CSVWriter;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.util.ArtifactIOUtil;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLEPlatformUtil;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;

public class CreateMPLEEvalHandler {
	List<Float> gamas = new LinkedList<Float>();
	List<Integer> size = new LinkedList<Integer>();
	List<String> type = new LinkedList<String>();

	@Execute
	public void execute(ServiceContainer services, ReaderManager readerManager, IEclipseContext context) {
		gamas.add(0.7f);
		gamas.add(0.8f);
		gamas.add(0.9f);
		gamas.add(1.0f);
		size.add(10);
		size.add(30);
		size.add(50);
		type.add(NodeType.METHOD_DECLARATION.toString());
		type.add(NodeType.CLASS.toString());

		type.forEach(type -> {
			size.forEach(size -> {
				gamas.forEach(gama -> {
					if (services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() > 0) {
						try {
							MPLPlatform platform = new MPLPlatform();
							platform.prefs.setOptionalThreshold(gama);
							platform.prefs.setGranularityLevel(type);
							platform.prefs.setCloneSize(size);
							CSVWriter csvWritter = creatCSVWriter(
									new File(services.workspaceFileSystem.getWorkspaceDirectory().getAbsolutePath()
											+ "//" + "clone_model_cs_" + platform.prefs.getCloneSize() + "_gama_"
											+ platform.prefs.getOptionalThreshold() + "_granularity_"
											+ platform.prefs.getGranularityLevel() + ".csv"));

							csvWritter.writeNext(new String[] { "prefs:", "cloneSize: " + platform.prefs.getCloneSize(),
									"threshold: " + platform.prefs.getOptionalThreshold(),
									"granularity level: " + platform.prefs.getCloneSize() });
							csvWritter.writeNext(new String[] { "permutations", "#UUIDs", "#classes", "#clone_configs",
									"Runtime ss", "Memory Consuption" });

							List<Tree> variants = new ArrayList<Tree>();
							// read all variants
							for (FileTreeElement treeElement : services.rcpSelectionService
									.getCurrentSelectionsFromExplorer()) {
								variants.add(readerManager.readFile(treeElement));
							}
							// Collections.shuffle(variants);

							long startTime = System.currentTimeMillis();
							platform.insertVariants(variants, services);
							long endTime = System.currentTimeMillis();
							long time = endTime - startTime;

							MPLEPlatformUtil.storePlatform(
									services.workspaceFileSystem.getWorkspaceDirectory().getAbsolutePath() + "//"
											+ "clone_model_cs_" + platform.prefs.getCloneSize() + "_gama_"
											+ platform.prefs.getOptionalThreshold() + "_granularity_"
											+ platform.prefs.getGranularityLevel() + ".mpl",
									platform);
							long memoryConsumption = Runtime.getRuntime().totalMemory()
									- Runtime.getRuntime().freeMemory();

							SimpleDateFormat formater = new SimpleDateFormat("ss,SSS");
							csvWritter.writeNext(new String[] {
									"family_model_size_" + platform.prefs.getCloneSize() + "_threshold_"
											+ platform.prefs.getOptionalThreshold(),
									formater.format(time), String.valueOf(memoryConsumption) });

							csvWritter.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						// Compare resulting trees
					}
				});
			});
		});
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

	private void printPlatform(MPLPlatform platform) {
		Map<UUID, Integer> cloneClasses = new HashMap<UUID, Integer>();
		platform.configurations.forEach(config -> {
			config.getCloneConfigurations().forEach(cloneConfig -> {
				if (!cloneClasses.containsKey(cloneConfig.componentUUID)) {
					cloneClasses.put(cloneConfig.componentUUID, 1);
				} else {
					cloneClasses.put(cloneConfig.componentUUID, cloneClasses.get(cloneConfig.componentUUID) + 1);
				}
			});
		});

		int totalNumber = 0;
		for (Entry<UUID, Integer> entry : cloneClasses.entrySet()) {
			totalNumber = totalNumber + entry.getValue();
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
