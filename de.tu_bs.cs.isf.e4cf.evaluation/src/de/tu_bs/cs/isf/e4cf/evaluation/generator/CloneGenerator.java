package de.tu_bs.cs.isf.e4cf.evaluation.generator;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.import_export.services.gson.GsonExportService;
import de.tu_bs.cs.isf.e4cf.core.import_export.services.gson.GsonImportService;

@Singleton
@Creatable
public class CloneGenerator {
	
	@Inject GsonExportService gsonExportService;
	@Inject GsonImportService gsonImportService;
	
	public void go(Tree tree) {
		System.out.println("I got a tree.");
		String treeStr = gsonExportService.exportTree((TreeImpl) tree);
		System.out.println(treeStr);
		System.out.println("Try importing again");
		Tree t2 = gsonImportService.importTree(treeStr);
		System.out.println("Breakpoint");
	}

	
}
