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
import de.tu_bs.cs.isf.e4cf.core.import_export.services.gson.GsonImportService
import java.util.List
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node
import de.tu_bs.cs.isf.e4cf.evaluation.string_table.CloneST

@Singleton 
@Creatable 
class CloneGenerator {
	@Inject GsonExportService gsonExportService
	@Inject GsonImportService gsonImportService
	@Inject CloneLogger logger
	@Inject CloneHelper helper
	@Inject Taxonomy taxonomy
	@Inject ServiceContainer services

	def void go(Tree tree, GeneratorOptions options) {
		// store all tree states in serialized form
		var trees = newArrayList
		// save a copy of the original tree
		val originalTree = gsonExportService.exportTree((tree as TreeImpl))
		// copy tree to track modifications on original tree and remove invalid ones
		val copyTree = gsonImportService.importTree(originalTree)
		helper.trackingTreeRoot = copyTree.root
		trees.add(originalTree)
		save(options.outputRoot, originalTree, "")
		
		//createTaxonomyExamples(tree, options)
		
		// Number of mutations (taxonomy calls) given by user
		for (var pass = 0; pass < options.mutations; pass++) {
			var break = false;
			
			// TODO: respect granularity type
			val all = helper.getAllChildren(tree.root)	
			
			val cloneType = selectCloneType(options)
			switch (cloneType) {
				case 1: {
					// This is just copy and pasting of subtrees for us
					val method = taxonomy.type1Method
					// TODO Make source possibly come from external repo
					val source = all.filter[n | 
						#[ // statement level
							NodeType.VARIABLE_DECLARATION,
							NodeType.ASSIGNMENT,
							NodeType.METHOD_CALL,
							NodeType.^IF,
							NodeType.LOOP_COLLECTION_CONTROLLED,
							NodeType.LOOP_DO,
							NodeType.LOOP_COUNT_CONTROLLED,
							NodeType.LOOP_WHILE
						].contains(n.standardizedNodeType)
					].random
					
					val target = all.filter[n | n.standardizedNodeType == NodeType.BLOCK].random
					
					method.invoke(taxonomy, source, target)
				}
				case 2: {
					val method = taxonomy.type2Method
	
					if (method.name == "systematicRenaming") {
						// rename a variable
						val declaration = all.filter[ n |
								n.standardizedNodeType == NodeType.VARIABLE_DECLARATION
						].random
						if (declaration !== null) {
							method.invoke(taxonomy, declaration, '''V�new Random().nextInt�'''.toString)
						} else break = true
						
					} else if (method.name == "arbitraryRenaming") {
						// swap arguments around
						val sources = all.filter[n | 
							n.standardizedNodeType == NodeType.METHOD_CALL
							&& n.children.size > 1
						]
						if (!sources.nullOrEmpty) {
							val args = sources.random.children
							var left = args.random
							var right = args.random
							while (left == right) right = args.random
							method.invoke(taxonomy, left, right)
						} else {
							// this can not be done with our sources, omit this iteration
							break = true
						}
					}
					
				}
				case 3: {
					val method = taxonomy.type3Method
					// TODO select arguments for method
					
				}
			}
			
			// omit iteration if there have been errors
			if (break) {
				pass--
				
			} else {
				// logging end of a pass is not necessary as each pass is only one taxonomy call
				// save intermediate tree
				val intermediateTree = gsonExportService.exportTree((tree as TreeImpl))
				trees.add(intermediateTree)
				if (options.enableSaveAll) {
					save(options.outputRoot, intermediateTree, '''.mod�pass�''')
				}
			}
		}
		
		// Serialize and save the final tree (already saved if save all is enabled)
		if (!options.enableSaveAll) {	
			var String modifiedTreeSerialized = gsonExportService.exportTree((tree as TreeImpl))
			save(options.outputRoot, modifiedTreeSerialized, ".mod")
		}
		
		// store log
		logger.outputLog
		logger.resetLog
	}
	
	@SuppressWarnings("all")
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
//		val funcCall = allNodes.findFirst[n | n.standardizedNodeType == NodeType.METHOD_CALL]
//		taxonomy.arbitraryRenaming(funcCall.children.head.children.get(0), funcCall.children.head.children.get(1))
//		val taxFTree = 	gsonExportService.exportTree((tree as TreeImpl))
//		save(options.outputRoot, taxFTree, "taxE")
		
		// Test move
//		val funcCall = allNodes.findFirst[n | n.standardizedNodeType == NodeType.METHOD_CALL]
//		val nodeToMove = funcCall.children.head.children.get(0)
//		helper.move(nodeToMove, funcCall.parent)
//		print(funcCall.parent.UUID)
//		helper.move(nodeToMove, funcCall.parent.parent)
//		print(funcCall.parent.parent.UUID)
//		val taxFTree = 	gsonExportService.exportTree((tree as TreeImpl))
//		save(options.outputRoot, taxFTree, "taxE")
		
		
		// Test movepos
//		val funcCall = allNodes.findFirst[n | n.standardizedNodeType == NodeType.METHOD_CALL]
//		taxonomy.arbitraryRenaming(funcCall.children.head.children.get(0), funcCall.children.head.children.get(1))
//		val taxFTree = 	gsonExportService.exportTree((tree as TreeImpl))
//		save(options.outputRoot, taxFTree, "taxE")

		// Test attr
//		val funcCall = allNodes.findFirst[n | n.standardizedNodeType == NodeType.METHOD_CALL]
//		helper.setAttributeValue(funcCall, "Name", "Tim is ne dumme Mongo")
//		helper.setAttributeValue(funcCall, "Name", "Tim is ne dumme Mango")
//		val taxFTree = 	gsonExportService.exportTree((tree as TreeImpl))
//		save(options.outputRoot, taxFTree, "taxE")
		
		// Test delete
//		val funcCall = allNodes.findFirst[n | n.standardizedNodeType == NodeType.METHOD_CALL]
//		val nodeToMove = funcCall.children.head.children.get(0)
//		helper.move(nodeToMove, funcCall.parent)
//		helper.move(nodeToMove, funcCall.parent.parent)
//		helper.delete(nodeToMove)
//		val taxFTree = 	gsonExportService.exportTree((tree as TreeImpl))
//		save(options.outputRoot, taxFTree, "taxE")
	}
	
	/** Saves tree string to json file */
	def private void save(Path targetFolder, String content, String infix) {
		var Path selectedPath = Paths.get(
			services.rcpSelectionService.getCurrentSelectionFromExplorer().getRelativePath())
		var String fileName = '''�services.rcpSelectionService.getCurrentSelectionFromExplorer().getFileName()��infix�.tree'''
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
	
	def static private Node random(Iterable<? extends Node> l){
		return l.get(new Random().nextInt(l.size))
	}
	
}
