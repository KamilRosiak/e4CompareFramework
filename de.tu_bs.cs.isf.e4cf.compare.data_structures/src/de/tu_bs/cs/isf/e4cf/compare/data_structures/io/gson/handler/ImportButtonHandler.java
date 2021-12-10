package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.gson.handler;

import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.gson.GsonImportService;

/**
 * Handler for the button to import a tree from JSON.
 */
public class ImportButtonHandler {
	/**
	 * Implementation of the execution hook to initialize the ImportButtonHandler with the GUI.
	 */
	@Execute
	public void execute() {
		GsonImportService importService = new GsonImportService();
		String testString = "{\"root\":{\"nodeType\":\"Root\",\"children\":[],\"attributes\":"
				+ "[{\"attributeKey\":\"Key1\",\"attributeValues\":[\"Value\"]}],\"varClass\":"
				+ "\"MANDATORY\",\"uuid\":\"f543f948-6161-4c78-803f-43eeae4f2ff1\"},\"treeName\":"
				+ "\"testTree\",\"artifactType\":\"Root\"}";
		System.out.println(importService.importTree(testString));
	}
}
