package de.tu_bs.cs.isf.e4cf.evaluation.generator

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree
import de.tu_bs.cs.isf.e4cf.core.import_export.services.gson.GsonExportService
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer
import de.tu_bs.cs.isf.e4cf.evaluation.dialog.GeneratorOptions
import java.nio.file.Files
import java.nio.file.Path
import java.util.List
import java.util.Random
import javax.inject.Inject
import javax.inject.Singleton
import org.eclipse.e4.core.di.annotations.Creatable

import static extension de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneHelper.random

@Singleton 
@Creatable 
class CloneGenerator {
	@Inject GsonExportService gsonExportService
	@Inject CloneLogger logger
	@Inject CloneHelper helper
	@Inject Taxonomy taxonomy
	@Inject ServiceContainer services

	def void go(Tree tree, GeneratorOptions options) {
		// store all tree states in serialized form
		// save a copy of the original tree
		helper.trackingTree = tree
		var variants = newArrayList(new Variant(tree, helper.trackingTree, 0, 0))
		
		val starttime = System.nanoTime();
		
		try {
			// Number of mutations (taxonomy calls) given by user
			for (var pass = 1; pass <= options.variants; pass++) {
				println('''Starting Variant Pass #«pass»''')
	
				// TODO Crossover (pass>1) or Modifications
				
				// Select Predecessor
				val predecessor = variants.random
				logger.logVariant(predecessor.index, variants.size)
				
				// Setup new Variant
				val currentTree = helper.deepCopy(predecessor.tree) // create deep copy because we might have selected that variant before
				helper.trackingTree = predecessor.trackingTree // tracking tree always deep copies
				
				// TODO: respect granularity type
				
				// Modify this Variant
				val nodeToSourceFactor = 6.0
				val modToLineFactor = 10
				val numModifications = Math.ceil(currentTree.root.depthFirstSearch.size / (nodeToSourceFactor * modToLineFactor)) * options.variantChangeDegree
				for (var mod = 1; mod <= numModifications; mod++) {
					
					// Determine Type
					if (new Random().nextInt(100) <= options.modificationRatioPercentage) {
						// Type II Modification
						taxonomy.performType2Modification(currentTree)
					} else {
						// Type III Modification
						taxonomy.performType3Modification(currentTree)
					}
					
				}
				
				// Store Variant
				variants.add(new Variant(currentTree, helper.trackingTree, predecessor.index, variants.size))
			}
		} finally {
			// Save all variants and logs
			save(options.outputRoot, variants);
			
			val endtime = System.nanoTime();
			println("Runtime: " + (endtime - starttime))
		}
	}
	
	/** Saves tree strings to json file and log */
	def private void save(Path targetFolder, List<Variant> variants) { 
		// Set the logger export project
		logger.projectFolderName = " 02 Trees"
		
		val selectedPath = logger.getOutPutDirBasedOnSelection()
		// val selectedPath = targetFolder
		// create a sub folder for this path
		var subfolderName = "GeneratedVariants"
		var i = 1; 
		while (Files.exists(selectedPath.resolve(subfolderName))) {
			subfolderName = "GeneratedVariants (" + i + ")"
			i++
		}
		val outputDirectory = selectedPath.resolve(subfolderName)
		
		// save every variant
		val selectionFileName = services.rcpSelectionService.getCurrentSelectionFromExplorer().getFileName()
		for (variant : variants) {
			val String variantSerialized = gsonExportService.exportTree((variant.tree as TreeImpl))
			val infix = "." + variant.parentIndex + "~" + variant.index
			val String fileName = '''«selectionFileName»«infix».tree'''
			logger.write(outputDirectory, fileName, newArrayList(variantSerialized))
		}
		
		// store and reset log
		logger.outputLog(outputDirectory, '''«selectionFileName».log''')
		logger.resetLogs

	}
	
	private static class Variant {
		public Tree tree
		public Tree trackingTree
		public int parentIndex
		public int index
		new (Tree t, Tree trackingT, int parentIndex, int index) {
			this.tree = t
			this.trackingTree = trackingT
			this.parentIndex = parentIndex
			this.index = index
		}
	}
	
}
