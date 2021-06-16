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
		outputLog(logPath, fileName);
	}

	public void outputLog(Path logDir, String fileName) {
		try {
			Files.createDirectories(logDir);
			
			Files.write(logDir.resolve(fileName), log,
					StandardOpenOption.CREATE,
			        StandardOpenOption.TRUNCATE_EXISTING);
			System.out.println(logDir.toString());
			
		} catch (FileAlreadyExistsException alreadyException) {
			alreadyException.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
