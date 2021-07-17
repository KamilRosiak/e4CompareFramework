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
import java.util.ArrayList

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
		val detectedChanges = comparison.allChildren.filter[c | c.resultSimilarity < 1.0]
		
		// For optionals add mandatory children
		val optionalNodes = detectedChanges.filter[c | c.resultSimilarity == 0.0]
		val detectedClones = new HashSet
		for (c : optionalNodes) {
			// Only count mandatory nodes
			if(c.leftArtifact !== null) {
				detectedClones.addAll(helper.getAllChildren(c.leftArtifact).filter[n | n.variabilityClass == VariabilityClass.MANDATORY])
			}
			if(c.rightArtifact !== null) {
				detectedClones.addAll(helper.getAllChildren(c.rightArtifact).filter[n | n.variabilityClass == VariabilityClass.MANDATORY])
			}
		}
		detectedChanges.forEach[c | detectedClones.add(c.mergeArtifacts)]
		var detectedClonesCount = detectedClones.size
		
		
		val detectableClones = getNumberOfClones(logEntries)
		println("===== Summary =====")
		println("Detectable\t" + detectableClones)
		println("Detected\t" + detectedClonesCount)
		println("Recall\t\t" + detectedClonesCount as float / detectableClones * 100 + " %")
		
		var foundEntries = new ArrayList
		for(logEntry : logEntries.filter[l | l.startsWith(ATOMIC)]) {
			if(applyAtomic(logEntry, detectedClones)) {
				foundEntries.add(logEntry)
			}
		}
		
		val truePositives = foundEntries.size
		println("True positives\t" + truePositives)
		println("Precision\t" + truePositives as float / detectedClonesCount * 100 + " %")
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
	
	def boolean applyAtomic(String logEntry, HashSet<Node> detectedClones) {
		switch (logEntry.split(" ").get(0)) {
			// TODO: can / should we have stronger conditions?
			// We can't differentiate operation on the same node as by design of the comparison
			// there is only one similarity value which identifies a change
			case COPY: {
				val source = logger.readAttr(logEntry, SOURCE)
				val target = logger.readAttr(logEntry, TARGET)
				val clone = logger.readAttr(logEntry, CLONE)
				
				return !detectedClones.filter[n | n.UUID.toString == clone].nullOrEmpty
			}
			case MOVE: {
				val source = logger.readAttr(logEntry, SOURCE)
				val target = logger.readAttr(logEntry, TARGET)
				
				return !detectedClones.filter[n | n.UUID.toString == target].nullOrEmpty
			}
			case MOVEPOS: {
				val source = logger.readAttr(logEntry, SOURCE)
				val from = logger.readAttr(logEntry, FROM)
				val to = logger.readAttr(logEntry, TO)
				
				return !detectedClones.filter[n | n.UUID.toString == source].nullOrEmpty
			}
			case DELETE: {
				val target = logger.readAttr(logEntry, TARGET)
				
				return !detectedClones.filter[n | n.UUID.toString == target].nullOrEmpty
			}
			case SETATTR: {
				val target = logger.readAttr(logEntry, TARGET)
				val key = logger.readAttr(logEntry, KEY)
				val from = logger.readAttr(logEntry, FROM)
				val to = logger.readAttr(logEntry, TO)
				
				return !detectedClones.filter[n | n.UUID.toString == target].nullOrEmpty
			}
			default: {
				println("Unsupported atomic clone operation ignored: " + logEntry)
				return false
			}
		}
	}
}