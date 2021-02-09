package de.tu_bs.cs.isf.e4cf.core.import_export.handler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.e4.core.di.annotations.Execute;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.core.import_export.services.gson.GsonExportService;

/**
 * Handler for the button to export a tree as JSON.
 */
public class ExportButtonHandler {
	/**
	 * Implementation of the execution hook to initialize the ExportButtonHandler with the GUI.
	 */
	@Execute
	public void execute() {
		GsonExportService exportService = new GsonExportService();

		Set<String> stringSet = new HashSet<>();
		stringSet.add("value1");
		stringSet.add("value2");


		List<Attribute> testAttributes = new LinkedList<>();
		testAttributes.add(new AttributeImpl("Key1","Value"));

		NodeImpl root = new NodeImpl("Root");
		root.setAttributes(testAttributes);
//		root.setVariabilityClass(VariabilityClass.MANDATORY);
		root.setChildren(new LinkedList<>());

		TreeImpl tree = new TreeImpl("testTree");
		tree.setArtifactType("type");
		tree.setRoot(root);

		System.out.println(exportService.exportTree(tree));
	}
}