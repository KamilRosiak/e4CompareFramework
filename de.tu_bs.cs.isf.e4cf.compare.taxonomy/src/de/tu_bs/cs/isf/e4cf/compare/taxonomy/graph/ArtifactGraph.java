package de.tu_bs.cs.isf.e4cf.compare.taxonomy.graph;

import java.util.ArrayList;
import java.util.List;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.ArtifactFileDetails;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.GraphEdge;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.GraphNode;
import de.tu_bs.cs.isf.e4cf.graph.core.elements.model.SimpleGraph;
import javafx.scene.paint.Color;
import org.eclipse.gef.geometry.planar.Rectangle;

/**
 * @author developer-olan
 *
 */
public class ArtifactGraph {
	private static final double WIDTH = 150;

	private double xAxisOffset = 0;
	private double yAxisOffset = 0;
	public List<ArtifactComparison> artifactComparisonList; // Similar to matching results in STMICS - Marc Hentze

	// Relation Graph class attributes
	public List<GraphNode> allArtifactNodes = new ArrayList<GraphNode>();
	public List<GraphEdge> allConnectionEdges = new ArrayList<GraphEdge>();
	public List<ArtifactFileDetails> allArtifactFileDetails = new ArrayList<ArtifactFileDetails>();

	// Taxonomy Graph class attributes
	public List<GraphNode> taxonomyArtifactNodes = new ArrayList<GraphNode>();
	public List<GraphEdge> taxonomyArtifactEdges = new ArrayList<GraphEdge>();

	public SimpleGraph mindMapRelationshipGraph;
	public SimpleGraph mindMapTaxonomyGraph;
	
	public SimpleGraph mindMapNoMatchGraph;

	public ArtifactGraph(List<ArtifactComparison> _artifactComparisonList) {
		mindMapRelationshipGraph = new SimpleGraph();
		mindMapTaxonomyGraph = new SimpleGraph();
		artifactComparisonList = _artifactComparisonList;
		
		// Instantiate Empty Map (In case of no match)
		mindMapNoMatchGraph = new SimpleGraph();
		GraphNode noMatchNode = new GraphNode();
		noMatchNode.setTitle("NO MATCH");
		noMatchNode.setDescription("The artifacts similarites do not meet threshold value");
		noMatchNode.setColor(Color.GREENYELLOW);
		noMatchNode.setBounds(new Rectangle(474, 50, WIDTH, 100));
		mindMapNoMatchGraph.addChildElement(noMatchNode);
	}
	
	/**
	 * Computes relation between artifacts
	 * @return
	 */
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

		// Add all child elements relation graph before returning Graph
		addAllChildElementsToRelationGraphMap();

		return mindMapRelationshipGraph;
	}
	
	/**
	 * Computes taxonomy for Display
	 * 
	 * @return
	 */
	public SimpleGraph setUpTaxonomyGraph() {
		
		// Identify Root Node
		GraphNode rootNode = IdentifyRootNode();
		
		if (rootNode != null) {
			
			// Remove Root Node from the rest
			allArtifactNodes.remove(rootNode);
			removeEdgesWithTarget(rootNode);

			// Identify other parents and their respective child nodes (recursively)
			GetChildrenForVariant(rootNode, allArtifactNodes, true);

			// Add Child Elements Taxonomy Map
			addAllChildElementsToTaxonomyGraphMap();
		}
		else {
			
		}
	

		return mindMapTaxonomyGraph;
	}

	/**
	 * Gets Children of a Variant for taxonomy (sub)tree
	 * 
	 * @param parentNode
	 */
	private void GetChildrenForVariant(GraphNode parentNode, List<GraphNode> allAvailableArtifactNodes,
			boolean rootNode) {
		// After For-Loop Variables
		GraphNode childNode = null;
		// For-Loop Variables
		List<GraphNode> mostSimilarNodes = new ArrayList<GraphNode>();
		int noOfReacheableVariants;
		int noOfReacheableVariantsReferenceValue;
		// Others
		int colorShade = 20;

		while (allAvailableArtifactNodes.size() > 0) {
			mostSimilarNodes.clear(); // P
			noOfReacheableVariants = 0; // x
			noOfReacheableVariantsReferenceValue = 0; // ref
			// Get Nodes with the highest reachability
			for (GraphNode anArtifactNode : allAvailableArtifactNodes) {
				noOfReacheableVariants = getNumberOfEdgesFrom(anArtifactNode, parentNode);

				if (noOfReacheableVariants > noOfReacheableVariantsReferenceValue) {
					mostSimilarNodes.clear();
					noOfReacheableVariantsReferenceValue = noOfReacheableVariants;
				}

				if (noOfReacheableVariants == noOfReacheableVariantsReferenceValue) {
					mostSimilarNodes.add(anArtifactNode);
				}
			}

			// Get the mostSimilar Node out of the List of Similar Nodes
			childNode = getNodeWithHighestSimilarity(parentNode, mostSimilarNodes);

			// Change Parent Color
			if (!rootNode) {
				parentNode.setColor(Color.rgb(91, 127, 255 - colorShade >= 0 ? 255 - colorShade : 0 ));
			}

			// Assign childNode to Parent  
			GraphEdge parentChildEdge = new GraphEdge();
			parentChildEdge.setSource(parentNode);
			parentChildEdge.setTarget(childNode);
			
			// Add both to taxonomy Lists
			if (!taxonomyArtifactNodes.contains(parentNode)) {
				taxonomyArtifactNodes.add(parentNode);
			}

			if (!taxonomyArtifactNodes.contains(childNode)) {
				taxonomyArtifactNodes.add(childNode);
			}
			
			
			taxonomyArtifactEdges.add(parentChildEdge);

			// Get Reachable variants of child node
			List<GraphNode> reachableByChild = new ArrayList<GraphNode>();
			reachableByChild = getAllNodesReacheablebyNode(childNode, parentNode);

			// Remove child Node and remaining from Available list of Variants
			allAvailableArtifactNodes.remove(childNode);
			allAvailableArtifactNodes.removeAll(reachableByChild);
			removeEdgesWithTarget(parentNode);
			colorShade += 20;

			// Recursively Call for the rest of the child/children Nodes
			GetChildrenForVariant(childNode, reachableByChild, false);

		}

	}
	
	/**
	 * Removes Edges pointing to the specific nodes 
	 * @param targetNode
	 */
	private void removeEdgesWithTarget(GraphNode targetNode) {
		List<GraphEdge> edgesToRemove = new ArrayList<GraphEdge>();
		for (GraphEdge aConnectingEdge : allConnectionEdges) {
			if(aConnectingEdge.getTarget().getDescription().equals(targetNode.getDescription())) {
				edgesToRemove.add(aConnectingEdge);
			}
		}
		allConnectionEdges.removeAll(edgesToRemove);
	}

	/**
	 * Compiles a list of all Nodes reachable by the given node
	 * 
	 * @param parentNode
	 * @param mostSimilarNodes
	 * @return
	 */
	private List<GraphNode> getAllNodesReacheablebyNode(GraphNode givenNode, GraphNode parentNode) {
		List<GraphNode> allNodesReachable = new ArrayList<GraphNode>();
		float maxSimilarityValue = 0;
		for (GraphEdge aConnectingEdge : allConnectionEdges) {
			if (givenNode.getDescription().equals(aConnectingEdge.getSource().getDescription()) && !aConnectingEdge.getTarget().getDescription().equals(parentNode.getDescription())) {
				if (aConnectingEdge.getWeight() > maxSimilarityValue) {
					// Get the target node and add to list
					allNodesReachable.add(aConnectingEdge.getTarget());
				}
			}
		}

		return allNodesReachable;
	}

	/**
	 * Find nodes most similar to parent node
	 * @param parentNode
	 * @param mostSimilarNodes
	 * @return
	 */
	private GraphNode getNodeWithHighestSimilarity(GraphNode parentNode, List<GraphNode> mostSimilarNodes) {
		GraphNode mostSimilar = new GraphNode();
		float maxSimilarityValue = 0;
		for (GraphEdge aConectingEdge : allConnectionEdges) {
			if (parentNode.getDescription().equals(aConectingEdge.getSource().getDescription())) {
				if (aConectingEdge.getWeight() > maxSimilarityValue) {
					mostSimilar = aConectingEdge.getTarget();
					maxSimilarityValue = aConectingEdge.getWeight();
				}
			}
		}

		return mostSimilar;
	}

	/***
	 * Identifies and Returns Root node from a list of compared Artifacts
	 * 
	 * @return GraphNode
	 */
	private GraphNode IdentifyRootNode() {
		int x = 0;
		int ref = 0;

		List<GraphNode> potentialRootNodes = new ArrayList<GraphNode>();

		for (GraphNode nodeElement : allArtifactNodes) {
			x = getNumberOfEdgesFrom(nodeElement, null);

			if (x > ref) {
				potentialRootNodes.clear();
				ref = x;
			}

			if (x == ref) {
				potentialRootNodes.add(nodeElement);
			}

		}

		return getSmallestRootNode(potentialRootNodes);
	}

	/**
	 * Get number of edges connected to a graph node
	 * 
	 * @param sourceNode
	 * @return
	 */
	private int getNumberOfEdgesFrom(GraphNode sourceNode, GraphNode parentNode) {
		int numberOfEdges = 0;

		for (GraphEdge aConectingEdge : allConnectionEdges) {
			// If the edge source variant is same as the source Node passed into the
			// function and the target node of the edge is not parent node
			// then increase count
			if (sourceNode.getDescription().equals(aConectingEdge.getSource().getDescription()))
				if (parentNode == null) {
					numberOfEdges++;
				} else {
					if (!aConectingEdge.getTarget().getDescription().equals(parentNode.getDescription())) {
						numberOfEdges++;
					}
				}

		}

		return numberOfEdges;
	}

	/**
	 * Gets Node with the smallest number of characters from the root Node
	 */
	private GraphNode getSmallestRootNode(List<GraphNode> potentialRootNodes) {
		int rootVariantSize = getHighestNumberOfCharacters();
		GraphNode rootVariantNode = null;

		// iterate through all potential root nodes
		for (GraphNode aPotentialRootNode : potentialRootNodes) {
			// iterate through list of all artifact file details
			for (ArtifactFileDetails anArtifactFileDetails : allArtifactFileDetails) {
				// Find the right artifactFile detail and extract the number of characters
				if (aPotentialRootNode.getDescription().equals(anArtifactFileDetails.getArtifactName())) {
					// If number of characters of variant lower than previously calculated
					if (anArtifactFileDetails.getNumberOfCharacters() < rootVariantSize) {
						// Update rootVariantSize
						rootVariantSize = anArtifactFileDetails.getNumberOfCharacters();
						// Make variant the new root
						aPotentialRootNode.setColor(Color.PINK); // Color.rgb(91, 127, 255)
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
	private int getHighestNumberOfCharacters() {
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
	 * 
	 * @param artifactNode
	 * @return
	 */
	private boolean isNodeContainedInGraph(GraphNode artifactNode) {

		boolean isContainedIn = false;

		for (GraphNode aVariantNode : allArtifactNodes) {
			if (aVariantNode.getDescription().equals(artifactNode.getDescription())) {
				isContainedIn = true;
				break;
			}
		}
		return isContainedIn;
	}

	/**
	 * Fetches specified Node from NodeList containing all specified nodes
	 * 
	 * @param artifactNode
	 * @return
	 */
	private GraphNode fetchGraphNodeForMapping(GraphNode artifactNode) {
		GraphNode existingGraphNode = null;

		for (GraphNode aVariantNode : allArtifactNodes) {
			if (aVariantNode.getDescription().equals(artifactNode.getDescription())) {
				existingGraphNode = aVariantNode;
				break;
			}
		}

		return existingGraphNode;
	}

	/**
	 * Adds Child Elements to Relation Graph after similarity computation and matching
	 */
	private void addAllChildElementsToRelationGraphMap() {

		for (GraphNode aVariantNode : allArtifactNodes) {
			mindMapRelationshipGraph.addChildElement(aVariantNode);
		}

		for (GraphEdge aConnectionEdge : allConnectionEdges) {
			mindMapRelationshipGraph.addChildElement(aConnectionEdge);
		}
	}

	/**
	 * Adds Child Elements to Taxonomy Graph after taxonomy computation
	 */
	private void addAllChildElementsToTaxonomyGraphMap() {

		for (GraphNode aVariantNode : taxonomyArtifactNodes) {
			aVariantNode.setBounds(new Rectangle(250, 550 + yAxisOffset, WIDTH, 90));
			mindMapTaxonomyGraph.addChildElement(aVariantNode);
			yAxisOffset+=50;
		}

		for (GraphEdge aConnectionEdge : taxonomyArtifactEdges) {
			mindMapTaxonomyGraph.addChildElement(aConnectionEdge);
		}
	}

	/**
	 * Sets up Nodes for display in Graph
	 * @param artifact
	 * @param artifactName
	 * @return
	 */
	private GraphNode createGraphNode(Node artifact, String artifactName) {
		GraphNode center = new GraphNode();
		center.setTitle(artifact.getNodeType());
		center.setDescription((System.identityHashCode(artifact) +"-"+ artifactName));
		center.setColor(Color.ALICEBLUE);
		//center.setBounds(new Rectangle(50 + (xAxisOffset * 200), 50 + (yAxisOffset * 550), WIDTH, 90));
		center.setBounds(new Rectangle(250, 550 + yAxisOffset, WIDTH, 90));

		xAxisOffset += 20;
		yAxisOffset += 20;
		return center;
	}

	
	/**
	 * adds list to all artifacts to engine list of artifacts
	 * @param allArtifactPaths
	 */
	public void deriveArtifactDetails(List<FileTreeElement> allArtifactTreeElements) {
		for (FileTreeElement anArtifactFileTreeElement : allArtifactTreeElements) {
			allArtifactFileDetails.add(new ArtifactFileDetails(anArtifactFileTreeElement));
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
