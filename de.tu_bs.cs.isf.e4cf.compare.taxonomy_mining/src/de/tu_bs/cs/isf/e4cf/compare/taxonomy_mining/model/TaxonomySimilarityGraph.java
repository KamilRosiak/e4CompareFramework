package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.model;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Iterators;

import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;

public class TaxonomySimilarityGraph extends TaxonomyGraph {

	public TaxonomySimilarityGraph(List<Tree> variants) {
		super(variants);
	}

	public void build() {

		int numberOfVertices = variants.size();
		float distanceMatrix[][] = new float[variants.size()][variants.size()];

		for (int i = 0; i < variants.size(); i++) {
			for (int j = i; j < variants.size(); j++) {
				if (i != j) {

					System.out.print("Comparing : " + i + "(" + variants.get(i).getTreeName() + ")" + " and " + j + "("
							+ variants.get(j).getTreeName() + ")" + " of total " + numberOfVertices + " : ");

					Comparison<Node> comparison = compareEngine.compare(variants.get(i).getRoot(),
							variants.get(j).getRoot());

					float distance = 1 - (comparison.getSimilarity());
					System.out.print(distance + "\n");

					distanceMatrix[i][j] = distance;
					distanceMatrix[j][i] = distance;
				} else {
					distanceMatrix[i][j] = 0;
					distanceMatrix[j][i] = 0;
				}
			}
		}

		int edgeNumber;
		boolean[] selected = new boolean[numberOfVertices];

		edgeNumber = 0;
		selected[0] = true;

		while (edgeNumber < numberOfVertices - 1) {

			float min = Float.MAX_VALUE;
			int x = 0;
			int y = 0;

			for (int i = 0; i < numberOfVertices; i++) {
				if (selected[i] == true) {
					for (int j = 0; j < numberOfVertices; j++) {
						if (!selected[j] && distanceMatrix[i][j] != 0) {
							if (min > distanceMatrix[i][j]) {
								min = distanceMatrix[i][j];
								x = i;
								y = j;
							}
						}
					}
				}
			}
			System.out.println(variants.get(x).getTreeName() + " - " + variants.get(y).getTreeName() + " :  "
					+ (1 - distanceMatrix[x][y]));
			selected[y] = true;

			edges.add(new TaxonomyEdge(variants.get(x), variants.get(y), distanceMatrix[x][y]));
			edges.add(new TaxonomyEdge(variants.get(y), variants.get(x), distanceMatrix[x][y]));

			edgeNumber++;
		}

		rootVariant = determineRootVariant();
		edges = transformToDirectedGraph(distanceMatrix);

	}

	private Tree determineRootVariant() {

		int numberOfNodes = Integer.MAX_VALUE;
		List<Tree> filteredCandidates = new ArrayList<Tree>();
		for (Tree candidate : variants) {
			int candidateNodes = Iterators.size(candidate.getRoot().depthFirstSearch().iterator());

			if (candidateNodes < numberOfNodes) {
				filteredCandidates.clear();
				numberOfNodes = candidateNodes;
			}
			if (numberOfNodes == candidateNodes) {
				filteredCandidates.add(candidate);
			}
		}

		Tree rootCandidate = null;
		float minimumDistance = Float.MAX_VALUE;
		for (Tree candidate : filteredCandidates) {

			float distance = 0;
			for (TaxonomyEdge edge : edges) {
				if (edge.getVariant1().equals(candidate)) {
					distance += edge.getWeight();
				}
			}

			if (distance < minimumDistance) {
				minimumDistance = distance;
				rootCandidate = candidate;
			}

		}

		return rootCandidate;

	}

	public List<TaxonomyEdge> transformToDirectedGraph(float[][] distanceMatrix) {

		int rootVariantIndex = variants.indexOf(rootVariant);
		int numberOfEdges = variants.size();
		float[] distance = new float[numberOfEdges];
		int[] predecessor = new int[numberOfEdges];

		for (int i = 0; i < numberOfEdges; i++) {
			distance[i] = Float.MAX_VALUE;
			predecessor[i] = -1;
		}

		distance[rootVariantIndex] = 0;

		for (int i = 0; i < numberOfEdges - 1; i++) {

			for (TaxonomyEdge edge : edges) {

				int u = variants.indexOf(edge.getVariant1());
				int v = variants.indexOf(edge.getVariant2());

				if (distance[u] + edge.getWeight() < distance[v]) {
					distance[v] = distance[u] + edge.getWeight();
					predecessor[v] = u;
				}

			}

		}

		for (TaxonomyEdge edge : edges) {

			int u = variants.indexOf(edge.getVariant1());
			int v = variants.indexOf(edge.getVariant2());

			if (distance[u] + edge.getWeight() < distance[v]) {
				System.out.println("Error");
			}
		}

		List<TaxonomyEdge> finalEdges = new ArrayList<TaxonomyEdge>();

		for (int i = 0; i < predecessor.length; i++) {

			if (predecessor[i] != -1) {

				TaxonomyEdge newEdge = new TaxonomyEdge(variants.get(predecessor[i]), variants.get(i),
						1 - distanceMatrix[i][predecessor[i]]);
				finalEdges.add(newEdge);
			}
		}

		return finalEdges;

	}

}
