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
import de.tu_bs.cs.isf.e4cf.compare.comparator.impl.node.NodeResultElement

import static de.tu_bs.cs.isf.e4cf.evaluation.string_table.CloneST.*

import static extension de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneHelper.getAllChildren
import java.util.Map

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

		var Tree originalTree = null
		var Map<Integer, Tree> variantTrees = newHashMap
		var Map<Integer, List<String>> logs = null
		
		for (FileTreeElement child : directoryElement.getChildren()) {
			val name = child.getFileName()
			
			if (name.endsWith("0~0.tree")) {
				originalTree = readerManager.readFile(child)
			} else if (name.endsWith(".tree")) {
				variantTrees.put(Integer.parseInt(name.split("[~.]").reverse.get(1)), readerManager.readFile(child))
			} else if (name.endsWith(".log")) {
				logger.read(child.getAbsolutePath())
				logs = logger.logs
			}
		}

		val matcher = new SortingMatcher()
		val metric = new MetricImpl("Metric")

		val engine = new CompareEngineHierarchical(matcher, metric)
		
		var outputLines = new ArrayList

		for (var i=1; i<=variantTrees.size(); i++) {
			val variantTree = variantTrees.get(i)
			val logEntries = logs.get(i)
			println("========")
			println(logEntries.reverse.findFirst[s | s.startsWith(VARIANT)])
			
			val comparison = engine.compare(originalTree.root, variantTree.root)
			
			println("Original tree nodes:" + originalTree.root.allChildren.size)
			println("Mod tree nodes:" + variantTree.root.allChildren.size)
			doComparison(comparison, matcher)
			
			// Atomic similarity -> result similarity == 1.0
			// An atomic similarity can be understood as an atomic cloned node ("identical" part of a clone)
			val detectedSimilarities = comparison.allChildren.filter[c | c.resultSimilarity == 1.0f].toList
			
			// Type 3: This detects mandatory children -> jeetus deletus
			
			// Attribute changes 
//			for(logEntry : logEntries.filter[l | l.split(" ").get(0).contains(SETATTR)]) {
//				val target = logger.readAttr(logEntry, TARGET)
//				
//				// wenn target not in detectedSimilarities -> count as detected
//			}

			val detectableSimilaritiesCnt = comparison.allChildren.size() - getNumberOfChanges(logEntries)
			val detectedSimilaritiesCnt = detectedSimilarities.size
			val recall = detectedSimilaritiesCnt as float / detectableSimilaritiesCnt * 100
			println("detected:" + detectedSimilaritiesCnt.toString)
			println("detectable:" + detectableSimilaritiesCnt.toString)
			if(recall > 100) {
				System.err.println("Recall\t\t" + recall + " %")
			} else {
				println("Recall\t\t" + recall + " %")
			}
			
			

			// Detected similarities (positives) that are also detectable from the log (true)
			// TODO: copyyyy?
			val truePositives = detectedSimilarities
			val falsePositives = newArrayList
			
			
			// For a change in the log
			for (entry : logEntries.filter[l | l.startsWith(ATOMIC)]) {
				
				// For expected true positives (= initial all similar nodes)
				var iterator = truePositives.iterator
				while (iterator.hasNext) {
					// Affected node in similar nodes -> false positive
					val truePositive = iterator.next
					if (isNodeChanged(entry, truePositive)) {
						falsePositives.add(truePositive)
						iterator.remove
					}
				}				
			}
			
			val truePositivesCount = truePositives.size
			val precision = truePositivesCount as float / detectedSimilaritiesCnt * 100
			println("true positives:" + truePositives.size)
			println("false positives:" + falsePositives.size)
			if(precision > 100) {
				System.err.println("Precision\t" + precision + " %")
			} else {
				println("Precision\t" + precision + " %")
			}
			
			// TODO: false positives and true negatives
			
			// Log (changes) -> clones
			// List<String> all uuids
			// iterate log -> apply atomic -> delete seen nodes
			
			
		}
		
		var workspaceRoot = services.workspaceFileSystem.getWorkspaceDirectory().getFile();
		var selectedPath = Paths.get(services.rcpSelectionService.getCurrentSelectionFromExplorer().getRelativePath());
		val fileName = "CloneEvaluation.results";
		Files.write(workspaceRoot.resolve(selectedPath.resolve(fileName)), outputLines,
					StandardOpenOption.CREATE,
			        StandardOpenOption.TRUNCATE_EXISTING);
			        
		logger.resetLogs
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

	def getAllChildren(Comparison<Node> root) {
		var nodes = newArrayList
		root._getAllChildren(nodes)
		return nodes
	}
	
	private def void _getAllChildren(Comparison<Node> root, List<Comparison<Node>> nodes) {
		root.childComparisons.forEach[c | nodes.add(c); c._getAllChildren(nodes)
		]
	}

	def getNumberOfChanges(List<String> logEntries) {
		logEntries.filter[l | l.startsWith(ATOMIC)].size
	}
	
	def boolean isNodeChanged(String logEntry, Comparison<Node> comparison) {
		val node = comparison.rightArtifact
		
		switch (logEntry.split(" ").get(0)) {
			// TODO: can / should we have stronger conditions?
			// We can't differentiate operation on the same node as by design of the comparison
			// there is only one similarity value which identifies a change
			case COPY: {
				// If the node is the target then only it's children changed but not the node itself
				val clone = logger.readAttr(logEntry, CLONE)
				
				return node.UUID.toString == clone
			}
			case MOVE, case MOVEPOS: {
				// If the node is the target then only it's children changed but not the node itself
				val source = logger.readAttr(logEntry, SOURCE)
				
				return node.UUID.toString == source
			}
			case DELETE: {
				val target = logger.readAttr(logEntry, TARGET)
				
				return node.UUID.toString == target
			}
			case SETATTR: {
				val target = logger.readAttr(logEntry, TARGET)
				val key = logger.readAttr(logEntry, KEY)
				
				if(node.UUID.toString == target) {
					// Check attribute 
					var resultWithAttribute = comparison.resultElements.filter[e | e instanceof NodeResultElement].findFirst[
						e | (e as NodeResultElement).attributeSimilarities.containsKey(key)
					]
					
					if(resultWithAttribute !== null) {
						return (resultWithAttribute as NodeResultElement).attributeSimilarities.get(key) < 1.0
					}
					
				}
				
				// No attribute change detected
				return false
			}
			default: {
				println("Unsupported atomic clone operation ignored: " + logEntry)
				return logEntry.contains(node.UUID.toString)
			}
		}
	}
}