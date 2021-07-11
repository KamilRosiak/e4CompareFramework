package de.tu_bs.cs.isf.e4cf.evaluation.generator;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.evaluation.string_table.CloneST;

@Singleton
@Creatable
public class CloneLogger {

	private final String DEFAULT_LOG_PATH = " 02 Trees";

	private List<String> log = new ArrayList<String>();
	@Inject
	private ServiceContainer services;

	public void logRaw(String message) {
		log.add(message);
	}

	public void resetLog() {
		log = new ArrayList<>();
	}

	public void outputLog() {
		// Recreate relative path from a project directory to the selected file in DEFAULT_LOG_PATH
		Path workspaceRoot = services.workspaceFileSystem.getWorkspaceDirectory().getFile();
		Path selectedPath = Paths.get(services.rcpSelectionService.getCurrentSelectionFromExplorer().getRelativePath());
		String fileName = "";
		
		if (services.rcpSelectionService.getCurrentSelectionFromExplorer().isDirectory()) {
			// For directories create a generic filename
			selectedPath = selectedPath.subpath(1, selectedPath.getNameCount());
			fileName = "CloneGenerator.log";
		} else {
			selectedPath = selectedPath.subpath(1, selectedPath.getNameCount()-1);
			fileName = services.rcpSelectionService.getCurrentSelectionFromExplorer().getFileName() + ".log";		
		}
		
		Path logPath = workspaceRoot.resolve(DEFAULT_LOG_PATH).resolve(selectedPath);
		write(logPath, fileName, log);
	}

	public void write(Path targetDir, String fileName, Iterable<? extends CharSequence> content) {
		try {
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
	
	public void read(String filePath) {
		try {
			log = Files.readAllLines(Paths.get(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getLog() {
		return log;
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
		yeeeeeeeeeeeeeeet(contains);
	}
	
	private void yeeeeeeeeeeeeeeet(String contains) {
		for (int i=0; i < log.size(); i++) {
			String entry = log.get(i);
			if(!entry.startsWith(CloneST.CLONE) && entry.contains(contains)) {
				log.remove(entry);
			}
		}
	}

}
