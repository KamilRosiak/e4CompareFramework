package de.tu_bs.cs.isf.e4cf.core.import_export.handler;

import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.core.import_export.services.ImportService;

public class ImportButtonHandler {

	@Execute
	public void execute() {
		ImportService importService = new ImportService();
		String testString = "{\"root\":{\"nodeType\":\"Root\",\"children\":[],\"attributes\":[{\"attributeKey\":\"Key1\",\"attributeValues\":[\"Value\"]}],\"varClass\":\"MANDATORY\",\"uuid\":\"f543f948-6161-4c78-803f-43eeae4f2ff1\"},\"treeName\":\"testTree\",\"artifactType\":\"Root\"}";
		System.out.println(importService.readJSON(testString));
	}
	
}
