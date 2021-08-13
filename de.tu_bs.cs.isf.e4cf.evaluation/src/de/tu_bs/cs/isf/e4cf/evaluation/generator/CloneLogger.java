package de.tu_bs.cs.isf.e4cf.evaluation.generator;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.evaluation.string_table.CloneST;

@Singleton
@Creatable
public class CloneLogger {

	public String projectFolderName = " 02 Trees";
	public boolean isActive = true;

	private Map<Integer, List<String>> variantLogs = new HashMap<>();
	private List<String> currentVariantLog;
	@Inject
	private ServiceContainer services;
	
	public Map<Integer, List<String>> getLogs() {
		return variantLogs;
	}
	
	public List<String> getLog() {
		return currentVariantLog;
	}
	
	public Path getOutputPath() {
		return services.workspaceFileSystem.getWorkspaceDirectory().getFile().resolve(projectFolderName);
	}
	
	/** Directly write a message into the current variants log */
	public void logRaw(String message) {
		if (currentVariantLog == null) {
			System.out.println("LOG ERROR: No variant to log to specified!");
			return;
		}
		if(isActive) currentVariantLog.add(message);
	}
	
	/** Create a new Log for a new variant */
	public void logVariant(final int parentId, final int variantId) {
		ArrayList<String> newLog = new ArrayList<>();
		currentVariantLog = newLog;
		// reconstruct the logs from the base (id=0) variant up to this parent
		ArrayList<Integer> parentSequence = new ArrayList<>();
		reconstructVariantTaxonomy(parentId, parentSequence);
		// reverse parentSequence to start from 0 for log reconstruction
		Collections.reverse(parentSequence);

		for (int reconstructionId : parentSequence) {
			if (variantLogs.containsKey(reconstructionId)) {
				List<String> predecessorLog = variantLogs.get(reconstructionId);
				for (String entry : predecessorLog) {
					logRaw(entry);
				}
			}
		}
		// begin the section of this variant
		logRaw(CloneST.VARIANT + " " + parentId + "~" + variantId);
		variantLogs.put(variantId, currentVariantLog);
	}
	
	// TODO
	public void logVariantCrossover(final int receiverId, final int donorId, final int variantId) {
		logVariant(receiverId, variantId);
		logRaw(CloneST.CROSSOVER + " " + donorId);
		System.out.println("A crossover happened yay: " + receiverId + "~" + donorId + "~" + variantId);
	}
	
	/** 
	 * Reconstructs the sequence of variants up to the given id, 
	 * e.g.taxonomy will be #[10,6,3,1,0] 
	 */
	public void reconstructVariantTaxonomy(int id, List<Integer> taxonomy) {
		if (variantLogs.containsKey(id)) {
			taxonomy.add(id);
			List<String> predecessorLog = variantLogs.get(id);
			int predecessorId = Integer.parseInt(predecessorLog.get(0).split("[ ,~]")[1]);
			reconstructVariantTaxonomy(predecessorId, taxonomy);
		} 
	}
	
	public void resetLogs() {
		variantLogs = new HashMap<>();
	}
	
	public void outputLog(Path targetDir, String fileName) {
		// Flatten the logs and add separators
		ArrayList<String> content = new ArrayList<String>();
		for (List<String> log : variantLogs.values()) {
			content.add(CloneST.LOG_SEPARATOR);
			content.addAll(log);
		}
		write(targetDir, fileName, content);
	}

	/**
	 * Utility for saving content to a file 
	 * @param targetDir directory relative to the project path
	 * @param fileName The name of the created file
	 * @param content
	 */
	public void write(Path targetDir, String fileName, Iterable<? extends CharSequence> content) {
		try {
			targetDir = getOutputPath().resolve(targetDir);
			
			Files.createDirectories(targetDir);
		
			Files.write(targetDir.resolve(fileName), content,
					StandardOpenOption.CREATE,
			        StandardOpenOption.TRUNCATE_EXISTING);
			
		} catch (FileAlreadyExistsException alreadyException) {
			alreadyException.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Path getOutPutDirBasedOnSelection() {
		// setup save path based on selection in explorer
		Path selectedPath = Paths.get(services.rcpSelectionService.getCurrentSelectionFromExplorer().getRelativePath());
		
		if (services.rcpSelectionService.getCurrentSelectionFromExplorer().isDirectory()) {
			if (selectedPath.getNameCount() < 2) {
				return null;
			}
			selectedPath = selectedPath.subpath(1, selectedPath.getNameCount());
		} else {
			if (selectedPath.getNameCount() < 3) {
				return null;
			}
			selectedPath = selectedPath.subpath(1, selectedPath.getNameCount() - 1);
		}
		
		return getOutputPath().resolve(selectedPath);
	}
	
	public void read(String filePath) {
		try {
			List<String> content = Files.readAllLines(Paths.get(filePath));
			variantLogs = new HashMap<Integer, List<String>>();
			for (String line : content) {
				if (line.equals(CloneST.LOG_SEPARATOR)) {
					currentVariantLog = new ArrayList<String>();
					variantLogs.put(variantLogs.size()+1, currentVariantLog);
				} else {
					currentVariantLog.add(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String readAttr(String logEntry, String key) {
		for (String part : logEntry.split(" ")) {
			String keyNoSpace = key.substring(1);
			
			if(part.startsWith(keyNoSpace)) {
				return part.replace(keyNoSpace, "");
			}
		}
		
		return null;
	}
	
	public void deleteLogsContainingString(String contains) {
		if(isActive) for (int i=0; i < currentVariantLog.size(); i++) {
			String entry = currentVariantLog.get(i);
			if(entry.startsWith(CloneST.ATOMIC) 
					&& !entry.startsWith(CloneST.COPY + CloneST.SOURCE)
					&& entry.contains(contains)) {
				currentVariantLog.remove(entry);
			}
		}
	}
}
