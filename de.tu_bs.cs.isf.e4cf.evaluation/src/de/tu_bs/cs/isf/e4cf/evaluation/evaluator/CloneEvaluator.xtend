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
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement
import de.tu_bs.cs.isf.e4cf.core.import_export.services.gson.GsonExportService
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneHelper
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneLogger
import de.tu_bs.cs.isf.e4cf.evaluation.string_table.CloneST
import java.nio.file.Files
import java.util.Collections
import java.util.List
import java.util.Map
import javax.inject.Inject
import javax.inject.Singleton
import org.eclipse.e4.core.di.annotations.Creatable

import static de.tu_bs.cs.isf.e4cf.evaluation.string_table.CloneST.*

import de.tu_bs.cs.isf.e4cf.evaluation.dialog.EvaluatorOptions
import java.nio.file.Path

@Singleton
@Creatable
class CloneEvaluator {
	
	@Inject CloneLogger logger
	@Inject ServiceContainer services
	@Inject ReaderManager readerManager
	@Inject GsonExportService gsonExportService
	@Inject CloneHelper helper
	
	val PROJECT_PATH = " 03 Metrics"
	val matcher = new SortingMatcher()
	val metric = new MetricImpl("Metric")
	val engine = new CompareEngineHierarchical(matcher, metric)
	
	Path outputDir
	
	/**
	 * Entry point of the evaluator
	 * Evaluates the selected directory
	 */
	def evaluate(EvaluatorOptions options) {		
		val directoryElement = services.rcpSelectionService.getCurrentSelectionFromExplorer()
		directoryElement.getChildren()
		
		var Tree originalTree = null
		val variantTrees = newHashMap
		val allTrees = newHashMap
		val allVariantNames = newArrayList
		
		// Read input folder
		for (FileTreeElement child : directoryElement.getChildren()) {
			val name = child.getFileName()
			
			if (name.endsWith("0~0.tree")) {
				originalTree = readerManager.readFile(child)
				allTrees.put(0, originalTree)
				allVariantNames.add(name)
			} else if (name.endsWith(".tree")) {
				var index = Integer.parseInt(name.split("[~.]").reverse.get(1))
				var tree = readerManager.readFile(child)
				variantTrees.put(index, tree)
				allTrees.put(index, tree)
				allVariantNames.add(name)
			} else if (name.endsWith(".log")) {
				logger.read(child.getAbsolutePath())
			}
		}
		
		val evaluatorResults = newArrayList
		
		logger.projectFolderName = PROJECT_PATH
		outputDir = logger.outPutDirBasedOnSelection !== null ? logger.outPutDirBasedOnSelection : logger.outputPath.resolve("Evaluation")
		// Setup clean result directory
		if(outputDir.toFile.exists) {
			Files.walk(outputDir, 1).filter(f | f.fileName.endsWith(".tree")).forEach(p | p.toFile.delete())
		} else {
			Files.createDirectories(outputDir);
		}
		
		// Do evaluation
		if(options.doIntraEvaluation) {
			// Not yet implemented
		}
		
		if(options.doInterEvaluation) {
			intervariantSimilarityEvaluation(originalTree, variantTrees, evaluatorResults, options)
		}
		
		if(options.doTaxonomyEvaluation) {
			taxonomyEvaluation(allVariantNames, allTrees, evaluatorResults, options)		
		}
		
		// Save Results
		logger.write(outputDir, "CloneEvaluation.results", evaluatorResults)
		
		// Clean up		
		logger.resetLogs
	}
	
	/**
	 * Compares each variant with the original variant
	 * And then evaluates the results based on the generator log
	 */
	private def void intervariantSimilarityEvaluation(Tree originalTree, Map<Integer, Tree> variantTrees, List<String> evaluatorResults, EvaluatorOptions options) {
		val evaluations = newArrayList

		// Evaluate the comparison of each generated variant with the original variant
		for (var i=1; i<=variantTrees.size(); i++) {			
			val variantTree = variantTrees.get(i)
			val logEntries = logger.logs.get(i)
			val variantName = logEntries.reverse.findFirst[s | s.startsWith(VARIANT)]
			
			val eval = new Evaluation
			eval.name = variantName
			evaluations.add(eval)
			
			evaluatorResults.add(CloneST.LOG_SEPARATOR)
			evaluatorResults.add(variantName)
			evaluatorResults.add("Original tree nodes: " + originalTree.root.depthFirstSearch.size)
			evaluatorResults.add("Variant tree nodes: " + variantTree.root.depthFirstSearch.size)
			
			// Compare the variants
			// Deep Copy Original Tree because otherwise we experience side effects when merging
			val comparison = doComparison(helper.deepCopy(originalTree).root, variantTree.root)
			eval.comp = comparison
			evaluatorResults.add("Comparison tree nodes: " + comparison.allChildren.size)
			
			// Atomic similarity -> result similarity == 1.0
			// An atomic similarity can be understood as an atomic cloned node ("identical" part of a clone)
			val detectedSimilarities = comparison.allChildren.filter[c | c.resultSimilarity == 1.0f].toList
			
			// Similarity <> Change
			// Detected similarities (positives) that do not correspond to a change log-entry (true) (GOOD)
			val truePositives = detectedSimilarities
			// Detected similarities (positives) that do have a corresponding change log-entry (false) (BAD)
			val falsePositives = newArrayList
			// Changes (negatives) that do have a corresponding change log-entry (true) (GOOD)
			val trueNegatives = comparison.allChildren.filter[c | !detectedSimilarities.contains(c)].toList
			// Changes (negatives) that do not have a corresponding change log-entry (false) (BAD)
			val falseNegatives = newArrayList
			
			// Identify positives as true or false
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
			
			// Identify negatives as true or false
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
			
			// Calculate metrics
			val recall = truePositives.size as float / (truePositives.size + falseNegatives.size) * 100
			evaluatorResults.add("Detected:" + (truePositives.size + falsePositives.size))
			evaluatorResults.add("Detectable:" + (truePositives.size + falseNegatives.size))
			evaluatorResults.add("Recall: " + recall + "%")
			
			val precision = truePositives.size as float / (truePositives.size + falsePositives.size) * 100
			evaluatorResults.add("Precision: " + precision + "%")
			evaluatorResults.add("Similarities that really did not change (TP): " + truePositives.size)
			if(options.isLogVerbose) {
				evaluatorResults.logComparisions(truePositives)
			}
			evaluatorResults.add("Similarities that actually changed(FP): " + falsePositives.size)
			evaluatorResults.logComparisions(falsePositives)
			evaluatorResults.add("Changes that really occurred (TN): " + trueNegatives.size)
			if(options.isLogVerbose) {
				evaluatorResults.logComparisions(trueNegatives)
			}
			evaluatorResults.add("Changes that did not occur (FN): " + falseNegatives.size)
			evaluatorResults.logComparisions(falseNegatives)
			
			// Print Results to Console additionally
			val vName = variantName.replace("New", "")
			println(vName + String.join("", Collections.nCopies(20 - vName.length, " "))
				+ "recall:" + String.format("%10.5f", recall) + "% precision:" + String.format("%10.5f", precision) + "%"
			)
			
			// Store info in evaluation data object
			eval.precision = precision
			eval.recall = recall
			eval.truePositives = truePositives
			eval.falsePositives = falsePositives
			eval.trueNegatives = trueNegatives
			eval.falseNegatives = falseNegatives
			
		}
			        
		// Build and Save Trees
		buildEvaluationTrees(evaluations)
		for (e : evaluations) {
			val serialized = gsonExportService.exportTree(e.tree)
			val name = e.name.split(" ").reverse.get(0)
			logger.write(outputDir, "Comparison." + name + ".tree", newArrayList(serialized))
		}
	}
	
	/**
	 * Evaluates the correctness of the taxonomy calculation based on generated variants taxonomy
	 */
	private def void taxonomyEvaluation(List<String> allVariantNames, Map<Integer, Tree> allTrees, List<String> evaluatorResults, EvaluatorOptions options) {
		println("Calculating taxonomy...")
		
		// Get expected taxonomy from log names
		val variantToParentExpected = newHashMap
		allVariantNames.forEach[v | 
			val temp = v.split("\\.").get(v.split("\\.").size-2)
			variantToParentExpected.put(
				Integer.parseInt("" + temp.split("~").get(1)),
				Integer.parseInt("" + temp.split("~").get(0)))
		]
		
		// Simple algorithm to calculate taxonomy from variant trees
		val variantToParentCalculated = calculateTaxonomy(allVariantNames, allTrees)
		
		// Compare both
		var hits = (0..allVariantNames.size-1).map[i | variantToParentExpected.get(i) == variantToParentCalculated.get(i)].filter[b | b].size
		
		var expected = "Expected taxonomy:\t" + variantToParentExpected.toString.replace("=", "->")
		var calculated = "Calculated taxonomy:\t" + variantToParentCalculated.toString.replace("=", "->")
		var hitrate = "Hitrate: " + hits as float/allVariantNames.size*100 + "%"
		
		println(expected)
		println(calculated)
		println(hitrate)
		evaluatorResults.add(CloneST.LOG_SEPARATOR)
		evaluatorResults.add(expected)
		evaluatorResults.add(calculated)
		evaluatorResults.add(hitrate)
	}
	
	/**
	 * Simple algorithm to calculate taxonomy from variant trees
	 */
	private def calculateTaxonomy(List<String> allVariantNames, Map<Integer, Tree> allTrees) {
		val variantComparsions = newArrayOfSize(allVariantNames.size, allVariantNames.size)
		
		// Create matrix storing comparisons of all variants
		for (var i=0; i < allVariantNames.size; i++) {
			for (var j=i; j < allVariantNames.size; j++) {
				if(i != j) {
					val comparison = doComparison(allTrees.get(i).root, allTrees.get(j).root)
					
					val detectedSimilarities = comparison.allChildren.filter[c | c.resultSimilarity == 1.0f].toList
					// [i][j] -> int simcount
					variantComparsions.set(i, j, detectedSimilarities.size)
					variantComparsions.set(j, i, detectedSimilarities.size)
				} else {
					// don't compare variant with itself
					variantComparsions.set(i, j, 0)
				}
			}
		}
		
		val variantToParentCalculated = newHashMap
		// For each variant choose the variant with the highest number of similarities
		// That the current variant is not parent of (to avoid cycles)
		for (var i=0; i < allVariantNames.size; i++) {
			val _i = i
			val predecessorsWithCurrentAsParent = variantToParentCalculated.entrySet.filter[
				e | e.value == _i
			].map[e | e.key]
			
			var int parent
			var int offset=0
			var similarities = variantComparsions.get(i).sort.reverse
			do {
				// max, max-1, ...
				val maxSimilarity = similarities.get(offset)
				parent = variantComparsions.get(i).indexOf(maxSimilarity)
				offset++				
			} while(predecessorsWithCurrentAsParent.contains(parent))
			
			variantToParentCalculated.put(i, parent)
		}
		
		// Sum all incoming similarities of a variant to detect a root variant
		val sumsOfVariantSimilarities = variantComparsions.map[e1 | 
			return e1.reduce[ i1, i2 | i1 + i2 ]
		]
		val maxSimilarity = sumsOfVariantSimilarities.max
		val root = sumsOfVariantSimilarities.indexOf(maxSimilarity)
		// That simply has no parent (is its own parent)
		variantToParentCalculated.put(root, root)
		
		return variantToParentCalculated
	}
	
	/* UTILITY FUNCTIONS */
	
	/**
	 * Compares the left and right node with the comparison engine
	 * @return comparison the resulting NodeComparison
	 */
	private def NodeComparison doComparison(Node leftRoot, Node rightRoot) {		
		val comparison = engine.compare(leftRoot, rightRoot)
		comparison.updateSimilarity()
		matcher.calculateMatching(comparison)
		comparison.updateSimilarity()
		
		return comparison
	}

	/** Returns all children of a comparison */
	private def getAllChildren(Comparison<Node> root) {
		var nodes = newArrayList
		root._getAllChildren(nodes)
		return nodes
	}
	
	/** Recursion to get all children of a comparison */
	private def void _getAllChildren(Comparison<Node> root, List<Comparison<Node>> nodes) {
		root.childComparisons.forEach[c | nodes.add(c); c._getAllChildren(nodes)]
	}
	
	/**
	 * Checks if a node of a comparison actually changed in a log entry
	 */
	private def boolean isNodeChanged(String logEntry, Comparison<Node> comparison) {
		val node = comparison.rightArtifact !== null ? comparison.rightArtifact : comparison.leftArtifact
		
		switch (logEntry.split(" ").get(0)) {
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
	
	/** Append information for each node comparison to a log */
	def private logComparisions(List<String> log, List<Comparison<Node>> cNodes) {
		for (c : cNodes) {
			val leftUUID = c.leftArtifact !== null ? c.leftArtifact.UUID.toString : ""
			val rightUUID = c.rightArtifact !== null ? c.rightArtifact.UUID.toString : ""
			log.add("left:" + leftUUID + " " + "right:" + rightUUID)
		}
	}
	
	/**
	 * Return a tree containing all relevant information about variants and similarities
	 */
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
	
	/**
	 * For a list of comparisons of a confusion class identified by a label,
	 * find a corresponding Node in the result tree and add the label and similarity to the Node
	 */
	private def addConfusionAttributes(Node root, String label, List<Comparison<Node>> confusions) {
		val allChildren = root.depthFirstSearch
		
		val iterator = confusions.iterator
		while (iterator.hasNext) {
			val confusion = iterator.next
			var Node node
			// For optionals two nodes with the same UUID may exist and they are left and right artifacts
			if (confusion.leftArtifact !== null) {
				node = allChildren.findFirst[n | n.UUID == confusion.leftArtifact.UUID];
			} else {
				// Should find the second node
				node = allChildren.findLast[n | n.UUID == confusion.rightArtifact.UUID];
			}
			
			if (node !== null) {
				node.addAttribute("Confusion", new StringValueImpl(label))
				node.addAttribute("ResultSimilarity", new StringValueImpl(""+confusion.resultSimilarity))	
				iterator.remove
			}
			
		}
		
	}
	
	/** Data class that holds evaluation information */
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