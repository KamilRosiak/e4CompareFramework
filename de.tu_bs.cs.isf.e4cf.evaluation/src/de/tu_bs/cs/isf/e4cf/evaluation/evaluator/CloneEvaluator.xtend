package de.tu_bs.cs.isf.e4cf.evaluation.evaluator

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical
import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher
import de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces.Matcher
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneHelper
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneLogger
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.ArrayList
import java.util.HashSet
import java.util.List
import javax.inject.Inject
import javax.inject.Singleton
import org.eclipse.e4.core.di.annotations.Creatable

import static de.tu_bs.cs.isf.e4cf.evaluation.string_table.CloneST.*

import static extension de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneHelper.getAllChildren

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
		
		println("Original tree nodes:" + originalTree.root.allChildren.size)
		println("Mod tree nodes:" + modifiedTree.root.allChildren.size)
		doComparison(comparison, matcher)
		
		// Use result similarity as it reflects the changes on the node
		val detectedChanges = comparison.allChildren.filter[c | c.resultSimilarity < 1.0]
		
		// For optionals add mandatory children
		val optionalNodes = detectedChanges.filter[c | c.resultSimilarity == 0.0]
		val detectedClones = new HashSet
		for (c : optionalNodes) {
			// Only count mandatory nodes
			if(c.leftArtifact !== null) {
				detectedClones.addAll(c.leftArtifact.allChildren.filter[n | n.variabilityClass == VariabilityClass.MANDATORY])
			}
			if(c.rightArtifact !== null) {
				detectedClones.addAll(c.rightArtifact.allChildren.filter[n | n.variabilityClass == VariabilityClass.MANDATORY])
			}
		}
		detectedChanges.forEach[c | detectedClones.add(c.mergeArtifacts)]
		var detectedClonesCount = detectedClones.size
		
		
		val detectableClones = getNumberOfClones(logEntries)
		
		// TODO: invert logic: look from detected clones and not log to avoid precision > 1
		var truePositives = new ArrayList
		var falsePositives = new ArrayList
		var truePositiveNodes = new HashSet
		for(logEntry : logEntries.filter[l | l.startsWith(ATOMIC)]) {
			val node = applyAtomic(logEntry, detectedClones)
			if(node !== null) {
				truePositives.add(logEntry)
				truePositiveNodes.add(node)
			} else {
				falsePositives.add(logEntry)
			}
		}
//		for(clone : detectedClones) {
//			var entries = logEntries.filter[l | l.startsWith(ATOMIC) && l.contains(clone.UUID.toString)]
//		}
		
		// Calculate metrics
		var metadata = new ArrayList
		metadata.add("Detectable (true + false positives)\t" + detectableClones)
		metadata.add("Detected (true positives + false negatives)\t" + detectedClonesCount)
		// TODO: investigate low recall -> output file
		metadata.add("Recall\t\t" + detectedClonesCount as float / detectableClones * 100 + " %")
		val truePositivesCount = truePositives.size
		metadata.add("True positives\t" + truePositivesCount)
		metadata.add("Precision\t" + truePositivesCount as float / detectedClonesCount * 100 + " %")
		
		// print to console
		metadata.forEach[e | println(e)]
		
		// TODO: output file
		// detected clones, undetected clones
		// tp, fp, ...
		// fn: detected but not detectable
		var outputLines = new ArrayList
		outputLines.addAll(metadata)
		outputLines.add("True positives (in generator log and correctly identified)")
		outputLines.addAll(truePositives)
		outputLines.add("False positives (in generator log and falsely not identified)")
		outputLines.addAll(falsePositives)
		outputLines.add("False negatives (not in generator log and falsely identified")
		var falseNegatives = new HashSet(detectedClones)
		falseNegatives.removeAll(truePositiveNodes)
		outputLines.addAll(falseNegatives.map[n | return n.UUID.toString])
//		outputLines.addAll()
//		outputLines.add("True negatives (not in generator log and correctly not identified )")
//		outputLines.addAll(trueNegatives)
		
		// TODO: plausibility check (lists vs counts?)
		
		var workspaceRoot = services.workspaceFileSystem.getWorkspaceDirectory().getFile();
		var selectedPath = Paths.get(services.rcpSelectionService.getCurrentSelectionFromExplorer().getRelativePath());
		val fileName = "CloneEvaluation.log";
		Files.write(workspaceRoot.resolve(selectedPath.resolve(fileName)), outputLines,
					StandardOpenOption.CREATE,
			        StandardOpenOption.TRUNCATE_EXISTING);
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
	
	def Node applyAtomic(String logEntry, HashSet<Node> detectedClones) {
		switch (logEntry.split(" ").get(0)) {
			// TODO: can / should we have stronger conditions?
			// We can't differentiate operation on the same node as by design of the comparison
			// there is only one similarity value which identifies a change
			case COPY: {
				val source = logger.readAttr(logEntry, SOURCE)
				val target = logger.readAttr(logEntry, TARGET)
				val clone = logger.readAttr(logEntry, CLONE)
				
				val detectedClone = detectedClones.filter[n | n.UUID.toString == clone]
				return detectedClone.empty ? null : detectedClone.get(0)
			}
			case MOVE: {
				val source = logger.readAttr(logEntry, SOURCE)
				val target = logger.readAttr(logEntry, TARGET)
				
				val detectedClone = detectedClones.filter[n | n.UUID.toString == target]
				return detectedClone.empty ? null : detectedClone.get(0)
			}
			case MOVEPOS: {
				val source = logger.readAttr(logEntry, SOURCE)
				val from = logger.readAttr(logEntry, FROM)
				val to = logger.readAttr(logEntry, TO)
				
				val detectedClone = detectedClones.filter[n | n.UUID.toString == source]
				return detectedClone.empty ? null : detectedClone.get(0)
			}
			case DELETE: {
				val target = logger.readAttr(logEntry, TARGET)
				
				val detectedClone = detectedClones.filter[n | n.UUID.toString == target]
				return detectedClone.empty ? null : detectedClone.get(0)
			}
			case SETATTR: {
				val target = logger.readAttr(logEntry, TARGET)
				val key = logger.readAttr(logEntry, KEY)
				val from = logger.readAttr(logEntry, FROM)
				val to = logger.readAttr(logEntry, TO)
				
				val detectedClone = detectedClones.filter[n | n.UUID.toString == target]
				return detectedClone.empty ? null : detectedClone.get(0)
			}
			default: {
				println("Unsupported atomic clone operation ignored: " + logEntry)
				return null
			}
		}
	}
}