package de.tu_bs.cs.isf.e4cf.core.import_export.handler;

import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.core.import_export.services.gson.GsonImportService;

/**
 * A Handler to test the functionality of the import service.
 * 
 * @author Team 6
 */
public class ImportButtonHandler {

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
