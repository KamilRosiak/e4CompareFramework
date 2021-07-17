package de.tu_bs.cs.isf.e4cf.evaluation.evaluator

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager
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
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneHelper
import de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces.Matcher
import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass
import java.util.HashSet

@Singleton
@Creatable
class CloneEvaluator {
	
	@Inject CloneLogger logger
	@Inject ServiceContainer services
	@Inject ReaderManager readerManager
	@Inject CloneHelper helper
	
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

		// TODO: change interface and remove package export
		val comparison = engine.compare(originalTree.root, modifiedTree.root)
		
		println("Original tree nodes:" + helper.getAllChildren(originalTree.root).size)
		println("Mod tree nodes:" + helper.getAllChildren(modifiedTree.root).size)
		doComparison(comparison, matcher)
		
		// Use result similarity as it reflects the changes on the node
		val detectedClones = comparison.allChildren.filter[c | c.resultSimilarity < 1.0]
		
		var detectedClonesCount = detectedClones.size
		
		// For optionals add mandatory children
		val optionalNodes = detectedClones.filter[c | c.resultSimilarity == 0.0]
		var foundNodes = new HashSet
		for (c : optionalNodes) {
			// Only count mandatory nodes
			if(c.leftArtifact !== null) {
				foundNodes.addAll(helper.getAllChildren(c.leftArtifact).filter[n | n.variabilityClass == VariabilityClass.MANDATORY])
			}
			if(c.rightArtifact !== null) {
				foundNodes.addAll(helper.getAllChildren(c.rightArtifact).filter[n | n.variabilityClass == VariabilityClass.MANDATORY])
			}
		}
		detectedClonesCount += foundNodes.size

		val float detectableClones = getNumberOfClones(logEntries)
		println("Detected " + detectedClonesCount)
		println("Detectable  " + detectableClones)
		println("Recall " + detectedClonesCount / detectableClones)
	}
	
	def void doComparison(NodeComparison comparison, Matcher matcher) {
		println("Comparison nodes initial:" + comparison.allChildren.size)
		
		comparison.updateSimilarity()
		
		println("Comparison nodes after update:" + comparison.allChildren.filter[c | c.similarity < 1.0].size)
		
		matcher.calculateMatching(comparison)
		
		println("Comparison nodes after matching:" + comparison.allChildren.filter[c | c.similarity < 1.0].size)
		
		comparison.updateSimilarity()
		
		println("Comparison nodes after second update:" + comparison.allChildren.filter[c | c.similarity < 1.0].size)
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

	def getAllChildren(Comparison<Node> root) {
		var nodes = newArrayList
		root._getAllChildren(nodes)
		return nodes
	}
	
	private def void _getAllChildren(Comparison<Node> root, List<Comparison<Node>> nodes) {
		root.childComparisons.forEach[c | nodes.add(c); c._getAllChildren(nodes)
		]
	}

	def getNumberOfClones(List<String> logEntries) {
		logEntries.filter[l | l.startsWith(ATOMIC)].size
	}
	
//	def applyAtomic(String logEntry, Map<String, Integer> cloneMap) {
//		switch (logEntry) {
//			case COPY: {
//				val source = logger.readAttr(logEntry, SOURCE)
//				val target = logger.readAttr(logEntry, TARGET)
//				val clone = logger.readAttr(logEntry, CLONE)
//				
//				// always +1
//				val prev = cloneMap.containsKey(clone) ? cloneMap.get(clone) : 0
//				cloneMap.put(clone, prev + 1)
//			}
//			case MOVE: {
//				val source = logger.readAttr(logEntry, SOURCE)
//				val target = logger.readAttr(logEntry, TARGET)
//				
////				// +1 for unique source
////				if(!cloneMap.containsKey(clone)) {
////					cloneMap.put(source, 1)
////				}
//			}
//			case MOVEPOS: {
//				val source = logger.readAttr(logEntry, SOURCE)
//				val from = logger.readAttr(logEntry, FROM)
//				val to = logger.readAttr(logEntry, TO)
//				// TODO: xcheck +1 for unique source
//			}
//			case DELETE: {
//				val target = logger.readAttr(logEntry, TARGET)
//				// set to 0
//				// change in parent?
//				// invalidates all operations on children and target operations
//			}
//			case SETATTR: {
//				val target = logger.readAttr(logEntry, TARGET)
//				val key = logger.readAttr(logEntry, KEY)
//				val from = logger.readAttr(logEntry, FROM)
//				val to = logger.readAttr(logEntry, TO)
//				// +1 for unique source + key combination
//				
//			}
//			default: {
//				println "Unsupported atomic clone operation ignored: " + logEntry
//			}
//		}
//	}
}