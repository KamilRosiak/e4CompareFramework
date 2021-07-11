package de.tu_bs.cs.isf.e4cf.evaluation.generator

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.java_reader.JavaNodeTypes
import de.tu_bs.cs.isf.e4cf.core.import_export.services.gson.GsonExportService
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer
import de.tu_bs.cs.isf.e4cf.evaluation.dialog.GeneratorOptions
import java.nio.file.Path
import java.nio.file.Paths
import java.util.ArrayList
import java.util.Random
import javax.inject.Inject
import javax.inject.Singleton
import org.eclipse.e4.core.di.annotations.Creatable
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType
import java.lang.invoke.MethodHandles.Lookup

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
		var trees = newArrayList
		// save a copy of the original tree
		val originalTree = gsonExportService.exportTree((tree as TreeImpl))
		trees.add(originalTree)
		save(options.outputRoot, originalTree, "")
		
		createTaxonomyExamples(tree, options)
		
		// Number of mutations (taxonomy calls) given by user
		for (var pass = 0; pass < options.mutations; pass++) {
			
			// get list of modifiable nodes for granularity type
			val suitableNodes = findSuitableNodes(tree, options.granularity)	
			
			val cloneType = selectCloneType(options)
			switch (cloneType) {
				case 1: {
					val method = taxonomy.type1Method
					// TODO select arguments for method
					
				}
				case 2: {
					val method = taxonomy.type2Method
					// TODO select arguments for method
					
				}
				case 3: {
					val method = taxonomy.type3Method
					// TODO select arguments for method
					
				}
			}
			
			// save intermediate tree
			val intermediateTree = gsonExportService.exportTree((tree as TreeImpl))
			trees.add(intermediateTree)
			if (options.enableSaveAll) {
				save(options.outputRoot, intermediateTree, '''.mod«pass»''')
			}
			
			
		}
		
		
		// Serialize and save the final tree (already saved if save all is enabled)
		if (!options.enableSaveAll) {	
			var String modifiedTreeSerialized = gsonExportService.exportTree((tree as TreeImpl))
			//save(options.outputRoot, modifiedTreeSerialized, ".mod")
		}
		
		// store log
		logger.outputLog
		logger.resetLog
	}
	
	def private void createTaxonomyExamples(Tree tree, GeneratorOptions options) {
		
		// Base setup
		// Copy & Paste
		// Create a full copy and modify it further
		val allNodes = helper.getAllChildren(tree.root)
//		val sumTimesFunc = allNodes.findFirst[n | 
//			n.standardizedNodeType == NodeType.METHOD_CALL
//		]
//		val someFuncBody = allNodes.findFirst[n | 
//			n.standardizedNodeType == NodeType.BLOCK
//			&& n.parent.standardizedNodeType == NodeType.METHOD_DECLARATION
//		]
//		helper.copyRecursively(sumTimesFunc, someFuncBody)
//		val taxATree = gsonExportService.exportTree((tree as TreeImpl))
//		save(options.outputRoot, taxATree, ".taxA")
		
		// systematic renaming (F)
//		val sumDecl = allNodes.findFirst[n |
//			n.standardizedNodeType == NodeType.VARIABLE_DECLARATION
//			&& helper.getAttributeValue(n, "Name") == "s"
//		]
//		taxonomy.systematicRenaming(sumDecl, "sum")
//		val productDecl = allNodes.findFirst[n |
//			n.standardizedNodeType == NodeType.VARIABLE_DECLARATION
//			&& helper.getAttributeValue(n, "Name") == "p"
//		]
//		taxonomy.systematicRenaming(productDecl, "product")
//		val taxDTree = gsonExportService.exportTree((tree as TreeImpl))
//		save(options.outputRoot, taxDTree, "taxD")

		// expression for Parameters F
//		val scope = allNodes.findFirst[n |
//			n.standardizedNodeType == NodeType.BLOCK
//			&& n.parent.standardizedNodeType == NodeType.LOOP_COUNT_CONTROLLED
//		]
//		taxonomy.expressionsForParameters(scope, "i", "(i*i)")
//		val taxFTree = 	gsonExportService.exportTree((tree as TreeImpl))
//		save(options.outputRoot, taxFTree, "taxF")

		// Arbitrary Renaming (E)
		val funcCall = allNodes.findFirst[n | n.standardizedNodeType == NodeType.METHOD_CALL]
		taxonomy.arbitraryRenaming(funcCall.children.head.children.get(0), funcCall.children.head.children.get(1))
		val taxFTree = 	gsonExportService.exportTree((tree as TreeImpl))
		save(options.outputRoot, taxFTree, "taxE")
		
		
		
		
		
	}
	
	/** Saves tree string to json file */
	def private void save(Path targetFolder, String content, String infix) {
		var Path selectedPath = Paths.get(
			services.rcpSelectionService.getCurrentSelectionFromExplorer().getRelativePath())
		var String fileName = '''«services.rcpSelectionService.getCurrentSelectionFromExplorer().getFileName()»«infix».tree'''
		if (services.rcpSelectionService.getCurrentSelectionFromExplorer().isDirectory()) {
			selectedPath = selectedPath.subpath(1, selectedPath.getNameCount())
		} else {
			selectedPath = selectedPath.subpath(1, selectedPath.getNameCount() - 1)
		}
		var Path logPath = targetFolder.resolve(selectedPath)
		var ArrayList<String> contentAsList = new ArrayList()
		contentAsList.add(content)
		logger.write(logPath, fileName, contentAsList)
	}
	
	/** Selects a random clone group 
	 * @return an integer representing the clone type (1, 2 or 3) */
	def private selectCloneType(GeneratorOptions options) {
		// TODO: may be optimized
		val rng = new Random()
		val randomValue = rng.nextFloat;
		val float weightSum = options.weightType1 + options.weightType2 + options.weightType3
		val thresholdType1 = options.weightType1 / weightSum
		val thresholdType2 = options.weightType2 / weightSum + thresholdType1
		
		if (randomValue < thresholdType1) {
			return 1;
		} else if (randomValue < thresholdType2) {
			return 2;
		} else {
			return 3;
		}
	}
	
	/** Returns a list of Nodes in the tree that may serve as modification sources for a given granularity */
	def private findSuitableNodes(Tree t, Granularity granularity) {
		// TODO: unsure node types: Body? Bound? 
		switch (granularity) {
			case CLASS: {
				return helper.getAllChildren(t.root).filter[ n |
					n.nodeType == JavaNodeTypes.Class.name ||
					n.nodeType == JavaNodeTypes.Interface.name
				]
			}
			case METHOD: {
				return helper.getAllChildren(t.root).filter[ n |
					n.nodeType == JavaNodeTypes.Argument.name
				]
			}
			case STATEMENT: {
				return helper.getAllChildren(t.root).filter[ n |
					n.nodeType == JavaNodeTypes.Assignment.name
				]
			}
			/*
			 * Unused:
			 * - BlockComment
			 * - Break
			 * - Cast
			 * - Continue
			 * - Else
			 * - Finally
			 * - Import
			 * - JavadocComment
			 * - LineComment
			 * - Synchronized
			 * - Then
			 * 
			 */
		}
		return null
	}
	
}
