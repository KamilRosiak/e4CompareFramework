package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.evaluation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

	public float getTrueNegativeRate() {
		return (float) trueNegatives / (trueNegatives + falsePositives);
	}

	public float getTruePositiveRate() {
		return (float) truePositives / (truePositives + falsePositives);
	}

	public float getAccuracy() {
		return (float) (truePositives + trueNegatives)
				/ (truePositives + trueNegatives + falsePositives + falseNegatives);
	}

	public float getF1Score() {
		return (float) (2 * truePositives) / (2 * truePositives + falsePositives + falseNegatives);
	}

	public float getBalancedAccuracy() {
		return (getTruePositiveRate() + getTrueNegativeRate()) / 2;
	}

	public int getPredecessorAccuracy() {
		return truePositives;
	}

	private void compareTaxonomyGraphs() {

		for (TaxonomyEdge truthEdge : groundTruthGraph.getEdges()) {

			boolean foundEdge = false;
			for (TaxonomyEdge calculatedEdge : derivedGraph.getEdges()) {

				if (hasEdge(calculatedEdge, truthEdge.getVariant1(), truthEdge.getVariant2())) {
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

				if (hasEdge(truthEdge, calculatedEdge.getVariant1(), calculatedEdge.getVariant2())) {
					foundEdge = true;
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

				if (hasEdge(truthEdge, edgeCombination.getVariant1(), edgeCombination.getVariant2())) {
					hasTruthEdge = true;
					break;
				}

			}

			for (TaxonomyEdge derivedEdge : derivedGraph.getEdges()) {

				if (hasEdge(derivedEdge, edgeCombination.getVariant1(), edgeCombination.getVariant2())) {
					hasDerivedEdge = true;
					break;
				}

			}

			if (!hasTruthEdge && !hasDerivedEdge) {
				trueNegatives++;
			}

		}

	}

	private boolean hasEdge(TaxonomyEdge edge, Tree variant1, Tree variant2) {

		if ((edge.getVariant1().equals(variant1) && edge.getVariant2().equals(variant2))) {
			return true;
		}

		return false;

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

	public void toFile(Path path) throws IOException {

		String output = "precision;recall;balancedAccuracy;f1score;predecessorAccuracy" + System.lineSeparator();
		output += getPrecision() + ";" + getRecall() + ";" + getBalancedAccuracy() + ";" + getF1Score() + ";"
				+ getPredecessorAccuracy();

		System.out.println(output);

		Files.write(path, output.getBytes());

	}

}
