package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.util.AttributeDictionary;

public class TaxonomyReachabilityGraph extends TaxonomyGraph {

	private RelationGraph relationGraph;
	private Set<Node> markedFragments;

	public TaxonomyReachabilityGraph(List<Tree> variants) {
		super(variants);
		this.relationGraph = new RelationGraph(variants);
		this.markedFragments = new HashSet<Node>();
		this.edges = new ArrayList<TaxonomyEdge>();
	}

	private void determineRootVariant() {

		Set<Tree> rootCandidates = new HashSet<Tree>();

		int reference = 0;
		for (Tree variant : variants) {

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

			float weight = 0;
			Tree selectedVariant = null;

			for (RelationEdge edge : relationGraph.getEdges()) {

				if (edge.getVariant1().equals(parentVariant) && candidates.contains(edge.getVariant2())) {

					if (edge.getWeight() > weight) {
						selectedVariant = edge.getVariant2();
						weight = edge.getWeight();
					}

				}

			}

			edges.add(new TaxonomyEdge(parentVariant, selectedVariant, weight));
			Set<Tree> reachableVariants = getReachableVariants(selectedVariant);
			availableVariants.removeAll(reachableVariants);
			availableVariants.remove(selectedVariant);
			createEdges(selectedVariant, reachableVariants);

		}

	}

	private void markFragments(Node node) {

		for (MatchSet matchSet : relationGraph.getMatchSets()) {
			if (matchSet.getNode1().equals(node)) {
				markedFragments.add(matchSet.getNode2());

			}
		}

		markedFragments.add(node);

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

	@Override
	public void build() {
		determineRootVariant();
		Set<Tree> availableVariants = new HashSet<Tree>();
		availableVariants.addAll(relationGraph.getVariants());
		availableVariants.remove(rootVariant);
		createEdges(rootVariant, availableVariants);

	}
}
