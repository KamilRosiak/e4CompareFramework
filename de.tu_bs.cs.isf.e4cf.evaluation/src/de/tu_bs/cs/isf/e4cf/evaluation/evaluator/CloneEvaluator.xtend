package de.tu_bs.cs.isf.e4cf.evaluation.evaluator

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical
import de.tu_bs.cs.isf.e4cf.compare.comparator.impl.node.NodeResultElement
import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher
import de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces.Matcher
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement
import de.tu_bs.cs.isf.e4cf.core.import_export.services.gson.GsonExportService
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneHelper
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneLogger
import de.tu_bs.cs.isf.e4cf.evaluation.string_table.CloneST
import java.util.Collections
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
	@Inject GsonExportService gsonExportService
	@Inject CloneHelper helper
	
	val PROJECT_PATH = " 03 Metrics"
	
	def evaluate() {
		val directoryElement = services.rcpSelectionService.getCurrentSelectionFromExplorer()
		directoryElement.getChildren()
		
		var Tree originalTree = null
		val variantTrees = newHashMap
		val evaluatorResults = newArrayList
		val evaluations = newArrayList
		
		for (FileTreeElement child : directoryElement.getChildren()) {
			val name = child.getFileName()
			
			if (name.endsWith("0~0.tree")) {
				originalTree = readerManager.readFile(child)
			} else if (name.endsWith(".tree")) {
				variantTrees.put(Integer.parseInt(name.split("[~.]").reverse.get(1)), readerManager.readFile(child))
			} else if (name.endsWith(".log")) {
				logger.read(child.getAbsolutePath())
			}
		}

		val matcher = new SortingMatcher()
		val metric = new MetricImpl("Metric")

		val engine = new CompareEngineHierarchical(matcher, metric)


		for (var i=1; i<=variantTrees.size(); i++) {
			val eval = new Evaluation
			evaluations.add(eval)
			
			val variantTree = variantTrees.get(i)
			val logEntries = logger.logs.get(i)
			val variantName = logEntries.reverse.findFirst[s | s.startsWith(VARIANT)]
			evaluatorResults.add(CloneST.LOG_SEPARATOR)
			evaluatorResults.add(variantName)
			eval.name = variantName
			
			// Deep Copy Original Tree because otherwise we experience side effects when merging
			val comparison = engine.compare(helper.deepCopy(originalTree).root, variantTree.root)
			evaluatorResults.add("Original tree nodes:" + originalTree.root.allChildren.size)
			evaluatorResults.add("Mod tree nodes:" + variantTree.root.allChildren.size)
			doComparison(comparison, matcher)
			eval.comp = comparison
			
			evaluatorResults.add("Comparison Tree Nodes:" + comparison.allChildren.size)
			
			// Atomic similarity -> result similarity == 1.0
			// An atomic similarity can be understood as an atomic cloned node ("identical" part of a clone)
			val detectedSimilarities = comparison.allChildren.filter[c | c.resultSimilarity == 1.0f].toList
			
			// Type 3: This detects mandatory children -> jeetus deletus
			// TODO: prune detectedSimilarities for type 3 changes		
			
			// Similarity <> Change
			// Detected similarities (positives) that do not correspond to a change log-entry (true) (GOOD)
			val truePositives = detectedSimilarities
			// Detected similarities (positives) that do have a corresponding change log-entry (false) (BAD)
			val falsePositives = newArrayList
			// Changes (negatives) that do have a corresponding change log-entry (true) (GOOD)
			val trueNegatives = comparison.allChildren.filter[c | !detectedSimilarities.contains(c)].toList
			// Changes (negatives) that do not have a corresponding change log-entry (false) (BAD)
			val falseNegatives = newArrayList
			
			// For a change in the log
			for (entry : logEntries.filter[l | l.startsWith(ATOMIC)]) {
				// For expected true positives (= initial all similar nodes)
				var iterator = truePositives.iterator
				while (iterator.hasNext) {
					val positive = iterator.next
					// Affected node in similar nodes -> false positive
					if (isNodeChanged(entry, positive)) {
						falsePositives.add(positive)
						iterator.remove
					}
				}
						
			}
			
			// For a supposed true negative
			val iterator = trueNegatives.iterator
			while (iterator.hasNext) {
				val negative = iterator.next
				var negativeWasChanged = false
				// Check that a log entry exists, confirming that it was indeed changed
				for (entry : logEntries.filter[l | l.startsWith(ATOMIC)]) {
					if (isNodeChanged(entry, negative)) {
						negativeWasChanged = true
					}
				}
				// If no log entry confirms it's change, it's a false negative
				if (!negativeWasChanged) {
					falseNegatives.add(negative)
					iterator.remove
				}
			}
			
			val recall = truePositives.size as float / (truePositives.size + falseNegatives.size) * 100
			evaluatorResults.add("detected:" + (truePositives.size + falsePositives.size))
			evaluatorResults.add("detectable:" + (truePositives.size + falseNegatives.size))
			evaluatorResults.add("Recall: " + recall + "%")
			
			val precision = truePositives.size as float / (truePositives.size + falsePositives.size) * 100
			evaluatorResults.add("Precision: " + precision + " %")
			evaluatorResults.add("Similarities that really did not change (TP): " + truePositives.size)
			// TODO: enable logging by switch
			evaluatorResults.logComparisions(truePositives)
			evaluatorResults.add("Similarities that actually changed(FP): " + falsePositives.size)
			evaluatorResults.logComparisions(falsePositives)
			evaluatorResults.add("Changes that really occurred (TN): " + trueNegatives.size)
			// TODO: enable logging by switch
			evaluatorResults.logComparisions(trueNegatives)
			evaluatorResults.add("Changes that did not occur (FN): " + falseNegatives.size)
			evaluatorResults.logComparisions(falseNegatives)
			
			// Print Results to Console additionally
			val vName = variantName.replace("New", "")
			println(vName + String.join("", Collections.nCopies(20 - vName.length, " ")) + "recall:" + recall + "% precision:" + precision + "%")
			
			// Store info in evaluation data object
			eval.precision = precision
			eval.recall = recall
			eval.truePositives = truePositives
			eval.falsePositives = falsePositives
			eval.trueNegatives = trueNegatives
			eval.falseNegatives = falseNegatives
			
		}
		
		// Save Results
		logger.projectFolderName = PROJECT_PATH
		logger.write(logger.outPutDirBasedOnSelection, "CloneEvaluation.results", evaluatorResults)
			        
		// Build and Save Tree
		buildEvaluationTrees(evaluations)
		for (e : evaluations) {
			val serialized = gsonExportService.exportTree(e.tree)
			val name = e.name.split(" ").reverse.get(0)
			logger.write(logger.outPutDirBasedOnSelection, "Comparison." + name + ".tree", newArrayList(serialized))
		}
		
		        
		logger.resetLogs
	}
	
	def void doComparison(NodeComparison comparison, Matcher matcher) {
		comparison.updateSimilarity()
		matcher.calculateMatching(comparison)
		comparison.updateSimilarity()
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
		val node = comparison.rightArtifact !== null ? comparison.rightArtifact : comparison.leftArtifact
		
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
	
	def private logComparisions(List<String> log, List<Comparison<Node>> cNodes) {
		for (c : cNodes) {
			val leftUUID = c.leftArtifact !== null ? c.leftArtifact.UUID.toString : ""
			val rightUUID = c.rightArtifact !== null ? c.rightArtifact.UUID.toString : ""
			log.add("left:" + leftUUID + " " + "right:" + rightUUID)
		}
	}
	
	/** Return a huge ass tree containing all relevant information about variants and similarities */
	def buildEvaluationTrees(List<Evaluation> evaluations) {
		for (eval : evaluations) {
			val tree = new TreeImpl("Evaluation Results")
			tree.fileExtension = "EVAL"
			eval.tree = tree
			
			// Create Node Tree from Comparisons
			val Node variantCompRoot = new NodeImpl(NodeType.FILE, "Comparison")
			tree.root = variantCompRoot
			variantCompRoot.addChildWithParent(eval.comp.mergeArtifacts(false))
			// Attach Attributes
			variantCompRoot.addAttribute("Name", new StringValueImpl(eval.name))
			variantCompRoot.addAttribute("Precision", new StringValueImpl(""+eval.precision))
			variantCompRoot.addAttribute("Recall", new StringValueImpl(""+eval.recall))
			
			// Attach Confusion
			variantCompRoot.addConfusionAttributes("TP", eval.truePositives)
			variantCompRoot.addConfusionAttributes("FP", eval.falsePositives)
			variantCompRoot.addConfusionAttributes("TN", eval.trueNegatives)
			variantCompRoot.addConfusionAttributes("FN", eval.falseNegatives)
		}
	}
	
	// TODO: Add confusions to log just now and only with one UUID
	private def addConfusionAttributes(Node root, String label, List<Comparison<Node>> confusions) {
		val allChildren = root.allChildren
		
		val iterator = confusions.iterator
		while (iterator.hasNext) {
			val confusion = iterator.next
			var Node node
			if (confusion.leftArtifact !== null) {
				node = allChildren.findFirst[n | n.UUID == confusion.leftArtifact.UUID];
			} else {
				node = allChildren.findFirst[n | n.UUID == confusion.rightArtifact.UUID];
			}
			
			if (node !== null) {
				node.addAttribute("Confusion", new StringValueImpl(label))
				node.addAttribute("ResultSimilarity", new StringValueImpl(""+confusion.resultSimilarity))	
				iterator.remove
			}
			
		}
		
	}
	
	static class Evaluation {
		
		var public NodeComparison comp;
		var public TreeImpl tree;
		var public String name;
		var public float precision;
		var public float recall;
		var public List<Comparison<Node>> truePositives;
		var public List<Comparison<Node>> falsePositives;
		var public List<Comparison<Node>> trueNegatives;
		var public List<Comparison<Node>> falseNegatives;

	}
}