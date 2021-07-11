package de.tu_bs.cs.isf.e4cf.evaluation.evaluator

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneLogger
import java.util.List
import javax.inject.Inject
import javax.inject.Singleton
import org.eclipse.e4.core.di.annotations.Creatable

import static de.tu_bs.cs.isf.e4cf.evaluation.string_table.CloneST.*
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node
import java.util.HashMap
import java.util.Map
import java.util.ArrayList

@Singleton
@Creatable
class CloneEvaluator {
	
	@Inject CloneLogger logger
	@Inject ServiceContainer services
	@Inject ReaderManager readerManager
	
	def evaluate() {
		val directoryElement = services.rcpSelectionService.getCurrentSelectionFromExplorer()
		directoryElement.getChildren()

		// read
		var Tree originalTree = null
		var Tree modifiedTree = null
		var List<String> logEntries = null
		
		for (FileTreeElement child : directoryElement.getChildren()) {
			val name = child.getFileName()
			
			if (name.endsWith(".mod.tree")) {
				modifiedTree = readerManager.readFile(child)
			} else if (name.endsWith(".tree")) {
				originalTree = readerManager.readFile(child)
			} else if (name.endsWith(".log")) {
				logger.read(child.getAbsolutePath())
				logEntries = logger.log
			}
		}

		val matcher = new SortingMatcher()
		val metric = new MetricImpl("Metric")

		val engine = new CompareEngineHierarchical(matcher, metric)
//		var mergedTree = engine.compare(originalTree, modifiedTree)
//		services.eventBroker.send(DSEditorST.INITIALIZE_TREE_EVENT, mergedTree)
		
		// call with root nodes
//		val comparison = engine.compare(originalTree.root, modifiedTree.root)
//		comparison.updateSimilarity()
//		matcher.calculateMatching(comparison)
//		comparison.updateSimilarity()
		
		// TODO evaluate
		// change -> 2 attribute values instead of one (?VARIABILITY CLASS (alternative))
		// change -> VARIABILITY CLASS (optional)
		
		
		
//		val evaluationTreeRoot = buildRecursive(originalTree.root);
		val root = originalTree.root
		
//		val evaluationNodes = newArrayList
//		buildRecursive(root, evaluationNodes)
		
//		val attributeMap = new HashMap()
//		val nodeMap = new HashMap()
//		createInitalAttributeMapRecursive(attributeMap, nodeMap, root)

		getNumberOfClones(logEntries)
		// Get all nodes (optional / alternative)
		// -> xcheck in log for an (?) event
		
		// precision and recall
	}
	
//	def void buildRecursive(Node originalNode, List<EvaluationNode> nodes) {
//		val node = new EvaluationNode(originalNode)
//		nodes.add(node)
//		
//		originalNode.children.forEach[
//			c | buildRecursive(c, nodes)
//		]
//	}
//
//	def void createInitalAttributeMapRecursive(Map<String, String> attributeMap, Map<String, String> nodeMap, Node node) {
//		attributeMap.put(node.UUID.toString, null)
//		nodeMap.put(node.UUID.toString, null)
//		
//		node.children.forEach[
//			c | createInitalAttributeMapRecursive(attributeMap, nodeMap, c)
//		]
//	}

	def getNumberOfClones(List<String> logEntries) {
		val cloneMap = new HashMap()
		
		logEntries.forEach[
			e | if (e.startsWith(ATOMIC)) {
				applyAtomic(e, cloneMap);
			}
		]
	}
	
	def applyAtomic(String logEntry, Map<String, Integer> cloneMap) {
		switch (logEntry) {
			case COPY: {
				val source = logger.readAttr(logEntry, SOURCE)
				val target = logger.readAttr(logEntry, TARGET)
				val clone = logger.readAttr(logEntry, CLONE)
				
				// always +1
				val prev = cloneMap.containsKey(clone) ? cloneMap.get(clone) : 0
				cloneMap.put(clone, prev + 1)
			}
			case MOVE: {
				val source = logger.readAttr(logEntry, SOURCE)
				val target = logger.readAttr(logEntry, TARGET)
				
//				// +1 for unique source
//				if(!cloneMap.containsKey(clone)) {
//					cloneMap.put(source, 1)
//				}
			}
			case MOVEPOS: {
				val source = logger.readAttr(logEntry, SOURCE)
				val index = logger.readAttr(logEntry, INDEX)
				// TODO: xcheck +1 for unique source
			}
			case DELETE: {
				val target = logger.readAttr(logEntry, TARGET)
				// set to 0
				// change in parent?
				// invalidates all operations on children and target operations
			}
			case SETATTR: {
				val target = logger.readAttr(logEntry, TARGET)
				val key = logger.readAttr(logEntry, KEY)
				val from = logger.readAttr(logEntry, FROM)
				val to = logger.readAttr(logEntry, TO)
				// +1 for unique source + key combination
				
			}
			default: {
				println "Unsupported atomic clone operation ignored: " + logEntry
			}
		}
	}
}