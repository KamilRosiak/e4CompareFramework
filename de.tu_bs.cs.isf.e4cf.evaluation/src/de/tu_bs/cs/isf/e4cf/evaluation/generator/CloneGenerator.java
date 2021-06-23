package de.tu_bs.cs.isf.e4cf.evaluation.generator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.import_export.services.gson.GsonExportService;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.evaluation.dialog.GeneratorOptions;

@Singleton
@Creatable
public class CloneGenerator {
	
	@Inject GsonExportService gsonExportService;
	@Inject CloneLogger logger;
	@Inject CloneHelper helper;
	@Inject ServiceContainer services;
	
	
	public void go(Tree tree, GeneratorOptions options) {
		
		// save a copy of the original tree
		String originalTree = gsonExportService.exportTree((TreeImpl) tree);
		save(options.outputRoot, originalTree, "");
		
		// demo modifications
//		TreeModifier modifier = new TreeModifier();
//		Tree modifiedTree = modifier.basicModification(tree);
		
		Node body = tree.getRoot().getChildren().get(0).getChildren().get(0).getChildren().get(1);
		Node varDecl = body.getChildren().get(0);
		Node varDeclClone = helper.copyRecursively(varDecl, body);

		helper.move(varDeclClone, 1);	
		
		// TODO change some attribute
		
		String modifiedTreeSerialized = gsonExportService.exportTree((TreeImpl) tree);
		save(options.outputRoot, modifiedTreeSerialized, ".mod");
		
		logger.outputLog();
	}
	
	/** Saves tree string to json file */
	private void save(Path targetFolder, String content, String infix) {
		
		Path selectedPath = Paths.get(services.rcpSelectionService.getCurrentSelectionFromExplorer().getRelativePath());
		String fileName = "";
		
		if (services.rcpSelectionService.getCurrentSelectionFromExplorer().isDirectory()) {
			System.err.println("Not available for directory selection");
		} else {
			selectedPath = selectedPath.subpath(1, selectedPath.getNameCount()-1);
			fileName = services.rcpSelectionService.getCurrentSelectionFromExplorer().getFileName() + infix + ".tree";		
		}
		
		Path logPath = targetFolder.resolve(selectedPath);
		ArrayList<String> contentAsList = new ArrayList<>();
		contentAsList.add(content);
		logger.write(logPath, fileName, contentAsList);	
		
	}
	
	
}
