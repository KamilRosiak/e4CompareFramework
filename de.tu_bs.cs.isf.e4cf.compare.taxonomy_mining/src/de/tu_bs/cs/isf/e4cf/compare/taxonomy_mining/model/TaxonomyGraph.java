package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.eclipse.gef.geometry.planar.Rectangle;

import com.google.common.collect.Lists;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.util.AttributeDictionary;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.File;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.GraphEdge;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.GraphNode;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.SimpleGraph;
import javafx.scene.paint.Color;

public class TaxonomyGraph {

	private RelationGraph relationGraph;
	private Tree rootVariant;
	private Set<Node> markedFragments;
	private List<TaxonomyEdge> edges;
	private Set<Tree> processedVariants;

	public TaxonomyGraph(RelationGraph relationGraph) {
		this();
		this.relationGraph = relationGraph;
		determineRootVariant();
		Set<Tree> availableVariants = new HashSet<Tree>();
		availableVariants.addAll(relationGraph.getVariants());
		availableVariants.remove(rootVariant);
		createEdges(rootVariant, availableVariants);
	}

	public TaxonomyGraph() {
		this.markedFragments = new HashSet<Node>();
		this.edges = new ArrayList<TaxonomyEdge>();
		this.processedVariants = new HashSet<Tree>();
	}

	public void addEdge(TaxonomyEdge edge) {
		this.edges.add(edge);
	}

	public Tree getRootVariant() {
		return rootVariant;
	}

	public void setRootVariant(Tree rootVariant) {
		this.rootVariant = rootVariant;
	}

	public List<TaxonomyEdge> getEdges() {
		return edges;
	}

	private void determineRootVariant() {

		Set<Tree> rootCandidates = new HashSet<Tree>();

		int reference = 0;
		for (Tree variant : relationGraph.getVariants()) {

			int numberOfEdges = 0;
			for (RelationEdge edge : relationGraph.getEdges()) {
				if (edge.getVariant1().equals(variant)) {
					numberOfEdges++;
				}
			}

			if (numberOfEdges > reference) {
				rootCandidates.clear();
				reference = numberOfEdges;
			}
			if (numberOfEdges == reference) {
				rootCandidates.add(variant);
			}
		}

		int minNumberOfNodes = Integer.MAX_VALUE;
		rootVariant = null;
		for (Tree variant : rootCandidates) {

			int variantSize = getVariantSize(variant.getRoot());

			if (variantSize < minNumberOfNodes) {
				minNumberOfNodes = variantSize;
				rootVariant = variant;
			}

		}

	}

	private int getVariantSize(Node node) {

		String sourceCode = "";
		if (!node.getNodeType().equals(NodeType.DIRECTORY.toString())
				&& !node.getNodeType().equals(NodeType.FILE.toString())) {
			Attribute sourceCodeAttribute = node.getAttributeForKey(AttributeDictionary.SOURCE_CODE_ATTRIBUTE_KEY);

			for (Value<String> value : sourceCodeAttribute.getAttributeValues()) {
				sourceCode += value.getValue().replace(" ", "");
			}
		}

		int sourceCodeLength = sourceCode.length();

		for (Node child : node.getChildren()) {
			sourceCodeLength += getVariantSize(child);
		}

		return sourceCodeLength;
	}

	public void createEdges(Tree parentVariant, Set<Tree> availableVariants) {

		markFragments(parentVariant.getRoot());
		processedVariants.add(parentVariant);

		while (!availableVariants.isEmpty()) {
			Set<Tree> candidates = new HashSet<Tree>();
			int ref = 0;
			int reachableVariantsCount = 0;

			for (Tree variant : availableVariants) {

				reachableVariantsCount = getReachableVariants(variant).size();

				if (reachableVariantsCount > ref) {
					candidates.clear();
					ref = reachableVariantsCount;
				}
				if (reachableVariantsCount == ref) {
					candidates.add(variant);
				}

			}

			float similarity = 0;
			Tree selectedVariant = null;

			for (Tree candidate : candidates) {

				for (VariantCompareUnit variantCompareUnit : relationGraph.getVariantCompareUnits()) {

					if (variantCompareUnit.getVariant1().equals(parentVariant)
							&& variantCompareUnit.getVariant2().equals(candidate)) {

						if (variantCompareUnit.getSimilarity() > similarity) {
							similarity = variantCompareUnit.getSimilarity();
							selectedVariant = candidate;
						}

					}

				}
			}

			edges.add(new TaxonomyEdge(parentVariant, selectedVariant, similarity));
			Set<Tree> reachableVariants = getReachableVariants(selectedVariant);
			availableVariants.removeAll(reachableVariants);
			availableVariants.remove(selectedVariant);
			reachableVariants.removeAll(processedVariants);
			createEdges(selectedVariant, reachableVariants);

		}

	}

	private void markFragments(Node node) {

		for (MatchSet matchSet : relationGraph.getMatchSets()) {
			if (matchSet.getNode1().equals(node)) {
				markedFragments.add(matchSet.getNode2());
			}
		}

		for (Node child : node.getChildren()) {
			markFragments(child);
		}

	}

	private Set<Tree> getReachableVariants(Tree variant) {

		Set<Tree> reachableVariants = new HashSet<Tree>();
		for (RelationEdge relationEdge : relationGraph.getEdges()) {
			if (relationEdge.getVariant1().equals(variant)) {

				List<Node> originalNodes = Lists.newArrayList(variant.getRoot().depthFirstSearch());
				List<Node> matchingNodes = Lists.newArrayList(relationEdge.getVariant2().getRoot().depthFirstSearch());

				for (MatchSet matchSet : relationGraph.getMatchSets()) {

					if (originalNodes.contains(matchSet.getNode1()) && matchingNodes.contains(matchSet.getNode2())) {

						if (!markedFragments.contains(matchSet.getNode2())) {
							reachableVariants.add(relationEdge.getVariant2());
							break;
						}

					}
				}

			}
		}

		return reachableVariants;

	}

	public SimpleGraph createSimpleGraph() {

		SimpleGraph simpleGraph = new SimpleGraph();

		Map<Tree, GraphNode> graphMap = createGraphNodes();

		for (GraphNode graphNode : graphMap.values()) {
			simpleGraph.addChildElement(graphNode);
		}

		for (TaxonomyEdge edge : this.edges) {

			GraphNode from = graphMap.get(edge.getVariant1());
			GraphNode to = graphMap.get(edge.getVariant2());

			GraphEdge graphEdge = new GraphEdge();
			graphEdge.connect(from, to);
			graphEdge.setWeight(edge.getWeight());
			simpleGraph.addChildElement(graphEdge);

		}

		return simpleGraph;

	}

	private Map<Tree, GraphNode> createGraphNodes() {

		Map<Tree, Color> colorMap = new HashMap<Tree, Color>();
		Map<Tree, GraphNode> graphNodes = new HashMap<Tree, GraphNode>();

		for (Tree variant : relationGraph.getVariants()) {

			Random random = new Random();
			Color color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
			colorMap.put(variant, color);

			GraphNode graphNode = new GraphNode();
			graphNode.setTitle(variant.getTreeName());
			graphNode.setDescription(variant.getTreeName());
			graphNode.setColor(colorMap.get(variant));
			graphNode.setBounds(new Rectangle(250, 550, 225, 30));

			graphNodes.put(variant, graphNode);

		}

		return graphNodes;

	}

	public void toFile(Path path, Map<Tree, String> treeToNameMap) throws IOException {

		String output = "";

		for (TaxonomyEdge edge : edges) {

			output += treeToNameMap.get(edge.getVariant1()) + "->" + treeToNameMap.get(edge.getVariant2())
					+ System.lineSeparator();
		}

		output += "->" + treeToNameMap.get(rootVariant);
		Files.write(path, output.getBytes());
	}

	public static TaxonomyGraph fromFile(Path path, Map<String, Tree> fileToTreeMap) throws IOException {
		List<String> lines = Files.readAllLines(path);

		TaxonomyGraph graph = new TaxonomyGraph();

		for (int i = 0; i < lines.size() - 1; i++) {
			String[] splittedLine = lines.get(i).split("->");

			String commitId1 = splittedLine[0];
			String commitId2 = splittedLine[1];
			Tree variant1 = fileToTreeMap.get(commitId1);
			Tree variant2 = fileToTreeMap.get(commitId2);
			TaxonomyEdge edge = new TaxonomyEdge(variant1, variant2, 0);
			graph.addEdge(edge);

		}

		String[] splittedLine = lines.get(lines.size() - 1).split("->");

		String rootId = splittedLine[1];
		Tree root = fileToTreeMap.get(rootId);
		graph.setRootVariant(root);

		return graph;

	}

}
