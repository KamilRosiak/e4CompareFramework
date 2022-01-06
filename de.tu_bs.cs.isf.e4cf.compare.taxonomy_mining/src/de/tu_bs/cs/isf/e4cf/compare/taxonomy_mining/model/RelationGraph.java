package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.eclipse.gef.geometry.planar.Rectangle;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.compare.DirectoryComparator;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.compare.FileComparator;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.compare.SourceCodeComparator;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.compare.TaxonomyMetric;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.compare.TaxonomySettings;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.util.AttributeDictionary;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.GraphEdge;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.GraphNode;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.SimpleGraph;
import javafx.scene.paint.Color;

public class RelationGraph {

	private CompareEngineHierarchical compareEngine;

	private List<Tree> variants;
	private List<MatchSet> matchSets;
	private List<VariantCompareUnit> variantCompareUnits;
	private List<RelationEdge> edges;	

	public RelationGraph(List<Tree> variants) {
		this.variants = variants;
		this.matchSets = new ArrayList<MatchSet>();
		this.variantCompareUnits = new ArrayList<VariantCompareUnit>();
		this.edges = new ArrayList<RelationEdge>();
		

		this.compareEngine = new CompareEngineHierarchical(new SortingMatcher(), new TaxonomyMetric("TaxonomyMetric",
				new SourceCodeComparator(), new DirectoryComparator(), new FileComparator()));

		compareVariants();
		createEdges();
	}

	

	private void compareVariants() {
		for (Tree variant1 : variants) {
			for (Tree variant2 : variants) {
				if (variant1 != variant2) {
					Comparison<Node> comparison = compareEngine.compare(variant1.getRoot(), variant2.getRoot());
					matchFragments(variant1, variant2, comparison, matchSets);
					variantCompareUnits.add(new VariantCompareUnit(variant1, variant2, comparison.getSimilarity()));
				}
			}
		}
	}

	private void matchFragments(Tree variant1, Tree variant2, Comparison<Node> comparison, List<MatchSet> matchSets) {
		Node leftArtifact = comparison.getLeftArtifact();
		Node rightArtifact = comparison.getRightArtifact();

		matchSets.add(new MatchSet(variant1, variant2, leftArtifact, rightArtifact, comparison.getResultSimilarity()));

		for (Comparison<Node> childComparison : comparison.getChildComparisons()) {

			if (childComparison.getLeftArtifact() != null && childComparison.getRightArtifact() != null) {
				matchFragments(variant1, variant2, childComparison, matchSets);
			}

		}
	}

	private void createEdges() {

		Set<Tree> variants = new HashSet<Tree>();

		for (MatchSet matchSet : matchSets) {

			Node originalNode = matchSet.getNode1();
			float originalWeight = (float) originalNode.getAttributeForKey(AttributeDictionary.WEIGHT_ATTRIBUTE_KEY)
					.getAttributeValues().get(0).getValue();

			float similarity = matchSet.getSimilarity();

			Tree originalVariant = matchSet.getVariant1();
			Tree matchingVariant = matchSet.getVariant2();

			variants.add(originalVariant);
			variants.add(matchingVariant);

			RelationEdge edgeBetweenVariants = null;
			for (RelationEdge edge : edges) {

				if (edge.getVariant1().equals(originalVariant) && edge.getVariant2().equals(matchingVariant)) {
					edgeBetweenVariants = edge;
				}

			}

			if (edgeBetweenVariants == null) {
				edgeBetweenVariants = new RelationEdge(originalVariant, matchingVariant, 0);
			}
			float weight = edgeBetweenVariants.getWeight();
			weight = weight + (similarity * originalWeight);
			edgeBetweenVariants.setWeight(weight);
			edges.add(edgeBetweenVariants);

		}

	}



	public List<Tree> getVariants() {
		return variants;
	}

	public List<MatchSet> getMatchSets() {
		return matchSets;
	}

	public List<VariantCompareUnit> getVariantCompareUnits() {
		return variantCompareUnits;
	}

	public List<RelationEdge> getEdges() {
		return edges;
	}

	public SimpleGraph createSimpleGraph() {

		SimpleGraph simpleGraph = new SimpleGraph();

		Map<Node, GraphNode> graphMap = createGraphNodes();

		for (GraphNode graphNode : graphMap.values()) {
			simpleGraph.addChildElement(graphNode);
		}

		for (MatchSet matchSet : this.matchSets) {

			GraphNode from = graphMap.get(matchSet.getNode1());
			GraphNode to = graphMap.get(matchSet.getNode2());

			GraphEdge graphEdge = new GraphEdge();
			graphEdge.connect(from, to);
			graphEdge.setWeight(matchSet.getSimilarity());
			simpleGraph.addChildElement(graphEdge);

		}

		return simpleGraph;

	}

	private Map<Node, GraphNode> createGraphNodes() {

		Map<Tree, Color> colorMap = new HashMap<Tree, Color>();
		Map<Node, GraphNode> graphNodes = new HashMap<Node, GraphNode>();

		for (Tree variant : variants) {

			Random random = new Random();
			Color color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
			colorMap.put(variant, color);

			for (Node node : variant.getRoot().depthFirstSearch()) {

				GraphNode graphNode = new GraphNode();
				graphNode.setTitle(variant.getTreeName());
				graphNode.setDescription(node.getNodeType());
				graphNode.setColor(colorMap.get(variant));
				graphNode.setBounds(new Rectangle(250, 550, 225, 30));

				graphNodes.put(node, graphNode);

			}

		}

		return graphNodes;

	}

}
