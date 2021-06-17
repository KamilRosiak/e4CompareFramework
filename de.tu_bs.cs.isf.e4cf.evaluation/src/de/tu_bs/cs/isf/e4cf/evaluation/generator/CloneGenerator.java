package de.tu_bs.cs.isf.e4cf.evaluation.generator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.import_export.services.gson.GsonExportService;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

@Singleton
@Creatable
public class CloneGenerator {
	
	@Inject GsonExportService gsonExportService;
	@Inject CloneLogger logger;
	@Inject CloneHelper helper;
	@Inject ServiceContainer services;
	
	
	public void go(Tree tree) {
		
		// save a copy of the original tree
		String originalTree = gsonExportService.exportTree((TreeImpl) tree);
		save(originalTree);
		
		// demo modifications
		
		
		
		logger.outputLog();
	}
	
	private void save(String content) {
		
		Path workspaceRoot = services.workspaceFileSystem.getWorkspaceDirectory().getFile();
		Path selectedPath = Paths.get(services.rcpSelectionService.getCurrentSelectionFromExplorer().getRelativePath());
		String fileName = "";
		
		if (services.rcpSelectionService.getCurrentSelectionFromExplorer().isDirectory()) {
			System.err.println("Not available for directory selection");
		} else {
			selectedPath = selectedPath.subpath(1, selectedPath.getNameCount()-1);
			fileName = services.rcpSelectionService.getCurrentSelectionFromExplorer().getFileName() + ".json";		
		}
		
		Path logPath = workspaceRoot.resolve(" 02 Trees").resolve(selectedPath);
		ArrayList<String> contentAsList = new ArrayList<>();
		contentAsList.add(content);
		logger.write(logPath, fileName, contentAsList);	
		
	}

	
}
