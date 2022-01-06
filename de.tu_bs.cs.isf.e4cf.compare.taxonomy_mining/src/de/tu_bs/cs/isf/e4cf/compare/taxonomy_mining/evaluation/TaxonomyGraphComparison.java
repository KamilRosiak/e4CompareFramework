package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.evaluation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.model.TaxonomyEdge;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.model.TaxonomyGraph;

public class TaxonomyGraphComparison {

	private List<Tree> variants;
	private TaxonomyGraph groundTruthGraph;
	private TaxonomyGraph derivedGraph;

	private int truePositives;
	private int falsePositives;
	private int trueNegatives;
	private int falseNegatives;

	public TaxonomyGraphComparison(List<Tree> variants, TaxonomyGraph groundTruthGraph, TaxonomyGraph derivedGraph) {
		this.variants = variants;
		this.groundTruthGraph = groundTruthGraph;
		this.derivedGraph = derivedGraph;
		compareTaxonomyGraphs();

	}

	public float getPrecision() {
		return (float) truePositives / (truePositives + falsePositives);
	}

	public float getRecall() {
		return (float) truePositives / (truePositives + falseNegatives);
	}

	public float getAccuracy() {
		return (float) (truePositives + trueNegatives)
				/ (truePositives + trueNegatives + falsePositives + falseNegatives);
	}

	public float getError() {
		return 1 - getAccuracy();
	}

	public int getRootPredictionAccuracy() {
		return groundTruthGraph.getRootVariant().equals(derivedGraph.getRootVariant()) ? 1 : 0;
	}

	public int getPredecessorAccuracy() {
		return truePositives;
	}

	public float getVariantDisplacementRate() {

		float accumulated = 0;

		for (Tree variant : variants) {
			
			int derivedDepth = getVariantDepth(derivedGraph.getRootVariant(), derivedGraph.getEdges(), variant);
			int truthDepth = getVariantDepth(groundTruthGraph.getRootVariant(), groundTruthGraph.getEdges(), variant);

			Set<Tree> derivedPredecessors = getPredecessors(derivedGraph.getEdges(), variant);
			Set<Tree> truthPredecessors = getPredecessors(groundTruthGraph.getEdges(), variant);

			int totalNumberOfPredecessors = derivedPredecessors.size() + truthPredecessors.size();

			derivedPredecessors.retainAll(truthPredecessors);

			int commonPredecessors = derivedPredecessors.size();
			int sameDepth = derivedDepth == truthDepth ? 1 : 0;
			float predecessorWeight = (float) commonPredecessors / totalNumberOfPredecessors;

			accumulated += 0.6 * predecessorWeight + sameDepth * 0.4;

		}

		return accumulated / variants.size();

	}

	private void compareTaxonomyGraphs() {

		for (TaxonomyEdge truthEdge : groundTruthGraph.getEdges()) {

			boolean foundEdge = false;
			for (TaxonomyEdge calculatedEdge : derivedGraph.getEdges()) {

				if (calculatedEdge.getVariant1().equals(truthEdge.getVariant1())
						&& calculatedEdge.getVariant2().equals(truthEdge.getVariant2())) {
					truePositives++;
					foundEdge = true;
					break;
				}

			}
			if (!foundEdge) {
				falsePositives++;
			}

		}

		for (TaxonomyEdge calculatedEdge : derivedGraph.getEdges()) {

			boolean foundEdge = false;
			for (TaxonomyEdge truthEdge : groundTruthGraph.getEdges()) {

				if (calculatedEdge.getVariant1().equals(truthEdge.getVariant1())
						&& calculatedEdge.getVariant2().equals(truthEdge.getVariant2())) {
					foundEdge = true;
					break;
				}

			}
			if (!foundEdge) {
				falseNegatives++;
			}

		}

		List<TaxonomyEdge> allEdgeCombinations = getAllCombinationsOfEdges();

		for (TaxonomyEdge edgeCombination : allEdgeCombinations) {

			boolean hasTruthEdge = false;
			boolean hasDerivedEdge = false;

			for (TaxonomyEdge truthEdge : groundTruthGraph.getEdges()) {

				if (truthEdge.getVariant1().equals(edgeCombination.getVariant1())
						&& truthEdge.getVariant2().equals(edgeCombination.getVariant2())) {
					hasTruthEdge = true;
					break;
				}

			}

			for (TaxonomyEdge derivedEdge : derivedGraph.getEdges()) {

				if (derivedEdge.getVariant1().equals(edgeCombination.getVariant1())
						&& derivedEdge.getVariant2().equals(edgeCombination.getVariant2())) {
					hasDerivedEdge = true;
					break;
				}

			}

			if (!hasTruthEdge && !hasDerivedEdge) {
				trueNegatives++;
			}

		}

	}

	private List<TaxonomyEdge> getAllCombinationsOfEdges() {

		List<TaxonomyEdge> edges = new ArrayList<TaxonomyEdge>();

		for (Tree variant1 : variants) {

			for (Tree variant2 : variants) {

				if (variant1 != variant2) {
					edges.add(new TaxonomyEdge(variant1, variant2, 0));
				}
			}

		}
		return edges;

	}

	private int getVariantDepth(Tree currentVariant, List<TaxonomyEdge> edges, Tree targetVariant) {

		int depth = 0;

		if (currentVariant.equals(targetVariant)) {
			return depth;
		}

		for (TaxonomyEdge edge : edges) {

			if (edge.getVariant1().equals(currentVariant)) {
				depth += getVariantDepth(edge.getVariant2(), edges, targetVariant);
			}

		}

		return depth;

	}

	private Set<Tree> getPredecessors(List<TaxonomyEdge> edges, Tree targetVariant) {
		Set<Tree> predecessors = new HashSet<Tree>();

		for (TaxonomyEdge edge : edges) {

			if (edge.getVariant2().equals(targetVariant)) {
				predecessors.add(edge.getVariant1());
			}

		}

		return predecessors;
	}

	public void toFile(Path path) throws IOException {

		String output = "precision;recall;accuracy;error;rootPredicitionAccuracy;variantDisplacementRate;predecessorAccuracy"
				+ System.lineSeparator();
		output += getPrecision() + ";" + getRecall() + ";" + getAccuracy() + ";" + getError() + ";" + getRootPredictionAccuracy() + ";"
				+ getVariantDisplacementRate() +";" + getPredecessorAccuracy();

		Files.write(path, output.getBytes());

	}

}
