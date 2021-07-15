/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.graph.artifact_graph;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.geometry.planar.Rectangle;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.GraphEdge;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.GraphNode;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.SimpleGraph;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.templates.AbstractGraphItem;
import javafx.scene.paint.Color;

/**
 * @author developer-olan
 *
 */
public class ArtifactGraphFactory {
	private static final double WIDTH = 150;
	private double xAxisOffset;
	private double yAxisOffset;
	public List<ArtifactComparison> artifactComparisonList; // Similar to matching results in STMICS - Marc Hentze
	public List<GraphNode> allArtifactNodes = new ArrayList<GraphNode>();
	public List<GraphEdge> allConnectionEdges = new ArrayList<GraphEdge>();
	public List<ArtifactFileDetails> allArtifactFileDetails = new ArrayList<ArtifactFileDetails>();

	public SimpleGraph mindMap;

	public ArtifactGraphFactory() {
		mindMap = new SimpleGraph();
	}

	public ArtifactGraphFactory(List<ArtifactComparison> _artifactComparisonList) {
		mindMap = new SimpleGraph();
		artifactComparisonList = _artifactComparisonList;
	}

	public void sortBySimilarityValues() {
		artifactComparisonList.sort((first, second) -> {
			// Multiply with -1 to sort descending
			return -1 * Float.compare(first.getNodeComparison().getSimilarity(),
					second.getNodeComparison().getSimilarity());
		});
	}

	public SimpleGraph createNodesAndGraph() {

		for (ArtifactComparison artifactComparison : artifactComparisonList) {
			xAxisOffset = 1;
			yAxisOffset = 1;

			GraphNode leftArtifactNode = createGraphNode(artifactComparison.getNodeComparison().getLeftArtifact(),
					artifactComparison.getLeftArtifactName());
			GraphNode rightArtifactNode = createGraphNode(artifactComparison.getNodeComparison().getRightArtifact(),
					artifactComparison.getRightArtifactName());

			mindMap.addChildElement(leftArtifactNode);
			mindMap.addChildElement(rightArtifactNode);

			GraphEdge conn = new GraphEdge();
			conn.connect(leftArtifactNode, rightArtifactNode);
			conn.setWeight(artifactComparison.getNodeComparison().getSimilarity());
			mindMap.addChildElement(conn);

		}
		return mindMap;
	}

	public SimpleGraph setUpRelationshipGraph() {

		// Search all Artifact List to identify compared artifacts and establish node
		// connections
		for (ArtifactComparison artifactComparison : artifactComparisonList) {

			GraphNode leftArtifactNode = createGraphNode(artifactComparison.getNodeComparison().getLeftArtifact(),
					artifactComparison.getLeftArtifactName());
			GraphNode rightArtifactNode = createGraphNode(artifactComparison.getNodeComparison().getRightArtifact(),
					artifactComparison.getRightArtifactName());

			if (!isNodeContainedInGraph(leftArtifactNode)) {
				allArtifactNodes.add(leftArtifactNode);
			}

			if (!isNodeContainedInGraph(rightArtifactNode)) {
				allArtifactNodes.add(rightArtifactNode);
			}

			// Get GraphNodes from internal List
			leftArtifactNode = fetchGraphNodeForMapping(leftArtifactNode);
			rightArtifactNode = fetchGraphNodeForMapping(rightArtifactNode);

			// Connect Edges
			GraphEdge conn = new GraphEdge();
			conn.connect(leftArtifactNode, rightArtifactNode);
			conn.setWeight(artifactComparison.getNodeComparison().getSimilarity());
			allConnectionEdges.add(conn);
		}

		// Add all child elements before returning Graph
		addAllChildElementsToMap();

		return mindMap;
	}

	public SimpleGraph setUpTaxonomyGraph() {
		// Identify Root Node
		GraphNode rootNode = IdentifyRootNode();
		
		
		return new SimpleGraph();
	}

	/***
	 * Identifies and Returns Root node from a list of compared Artifacts
	 * @return GraphNode
	 */
	public GraphNode IdentifyRootNode() {
		int x = 0;
		int ref = 0;

		List<GraphNode> potentialRootNodes = new ArrayList<GraphNode>();

		for (GraphNode nodeElement : allArtifactNodes) {
			x = getNumberOfEdgesFrom(nodeElement);
			if (x > ref) {
				ref = x;
			} else if (x == ref) {
				potentialRootNodes.add(nodeElement);
			}

		}
		

		return getSmallestRootNode(potentialRootNodes);
	}

	public int getNumberOfEdgesFrom(GraphNode sourceNode) {
		int numberOfEdges = 0;

		for (GraphEdge aConectingEdge : allConnectionEdges) {
			if (sourceNode.getDescription() == aConectingEdge.getSource().getDescription())
				numberOfEdges++;
		}

		return numberOfEdges;
	}

	/**
	 * Gets Node with the smallest number of 
	 * characters from the root Node
	 */
	public GraphNode getSmallestRootNode(List<GraphNode> potentialRootNodes) {
		int rootVariantSize = getHighestNumberOfCharacters();
		GraphNode rootVariantNode = null;

		// iterate through all potential root nodes
		for (GraphNode aPotentialRootNode : potentialRootNodes) {
			// iterate through list of all artifact file details
			for (ArtifactFileDetails anArtifactFileDetails : allArtifactFileDetails) {
				// Find the right artifactFile detail and extract the number of characters
				if (aPotentialRootNode.getDescription() == anArtifactFileDetails.getArtifactName()) {
					// If number of characters of variant lower than previously calculated
					if (anArtifactFileDetails.getNumberOfCharacters() < rootVariantSize) {
						// Make variant the new root
						rootVariantNode = aPotentialRootNode;
					}
				}
			}
		}
		return rootVariantNode;
	}

	/**
	 * Gets value of the highest number of Characters from the variants
	 */
	public int getHighestNumberOfCharacters() {
		int max = 0;
		for (ArtifactFileDetails anArtifactFileDetails : allArtifactFileDetails) {
			if (anArtifactFileDetails.getNumberOfCharacters() > max) {
				max = anArtifactFileDetails.getNumberOfCharacters();
			}
		}

		return max;
	}

	/**
	 * Checks if a Node is already included in compiled List of Artifact nodes
	 * @param artifactNode
	 * @return 
	 */
	public boolean isNodeContainedInGraph(GraphNode artifactNode) {

		boolean isContainedIn = false;

		for (GraphNode aVariantNode : allArtifactNodes) {
			if (aVariantNode.getDescription() == artifactNode.getDescription()) {
				isContainedIn = true;
				break;
			}
		}
		return isContainedIn;
	}

	/**
	 * Fetches specified Node from NodeList containing all specified nodes
	 * @param artifactNode
	 * @return
	 */
	public GraphNode fetchGraphNodeForMapping(GraphNode artifactNode) {
		GraphNode existingGraphNode = null;

		for (GraphNode aVariantNode : allArtifactNodes) {
			if (aVariantNode.getDescription() == artifactNode.getDescription()) {
				existingGraphNode = aVariantNode;
				break;
			}
		}

		return existingGraphNode;
	}

	public void addAllChildElementsToMap() {

		for (GraphNode aVariantNode : allArtifactNodes) {
			mindMap.addChildElement(aVariantNode);
		}

		for (GraphEdge aConnectionEdge : allConnectionEdges) {
			mindMap.addChildElement(aConnectionEdge);
		}
	}

	public GraphNode createGraphNode(Node artifact, String artifactName) {
		GraphNode center = new GraphNode();
		center.setTitle(artifact.getNodeType());
		center.setDescription(artifactName);
		center.setColor(Color.ALICEBLUE);
		center.setBounds(new Rectangle(50 + (xAxisOffset * 200), 50 + (yAxisOffset * 550), WIDTH, 90));
		xAxisOffset++;
		yAxisOffset++;
		return center;
	}

	public void deriveArtifactDetails(List<Path> allArtifactPaths) {
		for (Path anArtifactPath : allArtifactPaths) {
			allArtifactFileDetails.add(new ArtifactFileDetails(anArtifactPath));
		}
	}

	public SimpleGraph createComplexExample() {
		SimpleGraph mindMap = new SimpleGraph();

		GraphNode center = new GraphNode();
		center.setTitle("The Core Idea");
		center.setDescription("This is my Core idea");
		center.setColor(Color.GREENYELLOW);
		center.setBounds(new Rectangle(250, 50, WIDTH, 100));

		mindMap.addChildElement(center);

		GraphNode child = null;
		for (int i = 0; i < 5; i++) {
			child = new GraphNode();
			child.setTitle("Association #" + i);
			child.setDescription("Node1");
			child.setColor(Color.ALICEBLUE);

			child.setBounds(new Rectangle(50 + (i * 200), 250, WIDTH, 100));
			mindMap.addChildElement(child);

			GraphEdge conn = new GraphEdge();
			conn.connect(center, child);
			mindMap.addChildElement(conn);
		}

		GraphNode child2 = new GraphNode();
		child2.setTitle("Association #4-2");
		child2.setDescription("Node 2");
		child2.setColor(Color.LIGHTGRAY);
		child2.setBounds(new Rectangle(250, 550, WIDTH, 100));
		mindMap.addChildElement(child2);

		GraphEdge conn = new GraphEdge();
		conn.connect(child, child2);
		mindMap.addChildElement(conn);

		return mindMap;
	}

}
