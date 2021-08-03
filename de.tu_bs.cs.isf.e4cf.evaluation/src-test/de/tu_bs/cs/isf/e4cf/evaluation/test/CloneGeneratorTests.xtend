package de.tu_bs.cs.isf.e4cf.evaluation.test

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.File
import de.tu_bs.cs.isf.e4cf.core.import_export.services.gson.GsonExportService
import de.tu_bs.cs.isf.e4cf.core.import_export.services.gson.GsonImportService
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneGenerator
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneHelper
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneLogger
import de.tu_bs.cs.isf.e4cf.evaluation.generator.Taxonomy
import java.lang.reflect.Field
import java.nio.file.Path
import java.nio.file.Paths
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.assertEquals

/** 
 * Small Suite for Testing Clone Generator Mechanisms
 * !!! THIS SUITE NEEDS TO BE RUN AS PLUGIN-TEST !!!
 */
class CloneGeneratorTests {
	CloneLogger logger
	CloneHelper helper
	CloneGenerator generator
	Taxonomy taxonomy
	GsonExportService exporter

	// TODO Rewrite for new program flow
	@Before def void setup() {
		logger = new CloneLogger
		helper = new CloneHelper
		generator = new CloneGenerator
		taxonomy = new Taxonomy
		exporter = new GsonExportService
		val importer = new GsonImportService
		// setup injected fields for clone helper
		setInjectedField(helper, "logger", logger)
		setInjectedField(helper, "importer", importer)
		setInjectedField(helper, "exporter", exporter)
		// setup taxonomy
		setInjectedField(taxonomy, "logger", logger)
		setInjectedField(taxonomy, "helper", helper)
		// setup clone generator
		setInjectedField(generator, "logger", logger)
		setInjectedField(generator, "helper", helper)
		setInjectedField(generator, "taxonomy", taxonomy)
		setInjectedField(generator, "gsonExportService", exporter)
		setInjectedField(generator, "gsonImportService", importer)
	}

	def private void setInjectedField(Object instance, String fieldName, Object value) {
		try {
			var Field field = instance.getClass().getDeclaredField(fieldName)
			field.setAccessible(true)
			field.set(instance, value)
		} catch (Exception e) {
			System.err.
				println('''Failed to setup Field Access for «instance.getClass().getSimpleName()» on field «fieldName»''')
			e.printStackTrace()
		}

	}

	/** 
	 * Create a tree usually from a java file inside of this projects resources folder 
	 */
	def private TreeImpl loadTreeFromFS(String path) {
		// Load a file and wrap it in a fte
		var Path inputPath = Paths.get(path)
		var FileTreeElement input = new File(inputPath.toAbsolutePath().toString())
		return (new ReaderManager().readFile(input) as TreeImpl)
	}

	@Test 
	def void cloneType1Tests() {
//		val tree = loadTreeFromFS("resources/test/input/Base.java")
//		val root = tree.root
//		helper.trackingTree = tree
//		
//		// Make sure the input file is parsed somewhat correctly
//		var initialChildren = helper.getAllChildren(root).size
//		assertEquals(41, initialChildren)
//		
//		// Create a simple clone
//		var sourceNode = CloneHelper.getAllChildren(root).findFirst[n | 
//			n.standardizedNodeType == NodeType.VARIABLE_DECLARATION
//			&& !n.attributes.filter[a | a.attributeKey == "Name"].nullOrEmpty
//			&& helper.getAttributeValue(n, "Name") == "s"
//		].parent
//		var targetNode = helper.getAllChildren(root).findFirst[n | n.standardizedNodeType == NodeType.BLOCK]
//		taxonomy.copyAndPaste(sourceNode, targetNode)
//		
//		assertEquals(44, helper.getAllChildren(root).size)
//		assertEquals(5, logger.logs.size)
		
	}
}
