package de.tu_bs.cs.isf.e4cf.compare.taxonomy.graph;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.ArtifactFileDetails;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.VariantTaxonomyNode;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.util.TaxonomyToJSON;
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
	
	// Graph Objects
	public SimpleGraph mindMapRelationshipGraph;
	public SimpleGraph mindMapTaxonomyGraph;
	public SimpleGraph mindMapNoMatchGraph;
	
	// VariantTaxonomy
	public VariantTaxonomyNode taxonomyRootNode; 
	public TaxonomyToJSON taxonomyToJSON; 
	public String computedTaxonomyJSON;

	
	
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
			try {
			taxonomyRootNode = new VariantTaxonomyNode(rootNode.getDescription().substring(4), 0); // Create TaxonomyNode for JSON export
			// Remove Root Node from the rest
			allArtifactNodes.remove(rootNode);
			removeEdgesWithTarget(rootNode);

			// Identify other parents and their respective child nodes (recursively)
			GetChildrenForVariant(rootNode, allArtifactNodes, true);

			// Add Child Elements Taxonomy Map
			addAllChildElementsToTaxonomyGraphMap();
			
			// Add all Taxonomy Nodes to Graph
			taxonomyToJSON = new TaxonomyToJSON();
			computedTaxonomyJSON = taxonomyToJSON.convertToJSON(taxonomyRootNode);
			
			} catch (Exception ex) {
				System.out.println("What an error! Details: "+ ex.getMessage());
			}
		}
		else {
			
		}
	

		return mindMapTaxonomyGraph;
	}

	
	/**
	 * Computes DAG taxonomy for Display
	 * 
	 * @return
	 */
	public SimpleGraph setUpDAGTaxonomyGraph() {
		
		// Remove weaker edges of Pair-wise Comparisons
		removeWeakerEdges();
		
		// Remove cyclic edges
		removeCyclicEdges();
					
		// Identify Root Node
		GraphNode rootNode = IdentifyRootNode();
		
		if (rootNode != null) {
			try {
					
			taxonomyRootNode = new VariantTaxonomyNode(rootNode.getDescription().substring(4), 0); // Create TaxonomyNode for JSON export
			// Remove Root Node from the rest
			allArtifactNodes.remove(rootNode);
			removeEdgesWithTarget(rootNode);
			
					
			// Identify other parents and their respective child nodes (recursively)
			GetChildrenForVariantDAG(rootNode, allArtifactNodes, true);

			
			
			// Add Child Elements Taxonomy Map
			addAllChildElementsToTaxonomyGraphMap();
			
			// Add all Taxonomy Nodes to Graph
			taxonomyToJSON = new TaxonomyToJSON();
			computedTaxonomyJSON = taxonomyToJSON.convertToJSON(taxonomyRootNode);
			
			} catch (Exception ex) {
				System.out.println("What an error! Details: "+ ex.getMessage());
			}
		}
		else {
			
		}
	
		

		return mindMapTaxonomyGraph;
	}

	private void removeWeakerEdges() {
		List<GraphEdge> edgesToRemove = new ArrayList<GraphEdge>();

		for (GraphEdge firstArtifactEdge : allConnectionEdges) {
			for (GraphEdge secondArtifactEdge : allConnectionEdges) {
				if (!firstArtifactEdge.equals(secondArtifactEdge)) {
					if (firstArtifactEdge.getSource().equals(secondArtifactEdge.getTarget()) && firstArtifactEdge.getTarget().equals(secondArtifactEdge.getSource())) {
						if (firstArtifactEdge.getWeight() > secondArtifactEdge.getWeight()) {
							edgesToRemove.add(secondArtifactEdge);
						} else {
							edgesToRemove.add(firstArtifactEdge);
						}
					}
				}
			}
		}

		allConnectionEdges.removeAll(edgesToRemove);
	}
	
	private void removeCyclicEdges() {
		List<GraphEdge> edgesToRemove = new ArrayList<GraphEdge>(); 
		
		for (GraphEdge firstArtifactEdge : allConnectionEdges) {
			for (GraphEdge secondArtifactEdge : allConnectionEdges) {
				if (!firstArtifactEdge.equals(secondArtifactEdge)) {
					// If 2 edges share the same source/parent
					if (firstArtifactEdge.getSource().equals(secondArtifactEdge.getSource())) {
					// Find connection between parent sharing Nodes, Remove Cycles
					for (GraphEdge artifactEdgeToRemove : allConnectionEdges) {
						
						if ((artifactEdgeToRemove.getSource().equals(firstArtifactEdge.getTarget()) && artifactEdgeToRemove.getTarget().equals(secondArtifactEdge.getTarget())) || (artifactEdgeToRemove.getSource().equals(secondArtifactEdge.getTarget()) && artifactEdgeToRemove.getTarget().equals(firstArtifactEdge.getTarget()))) {
							List<GraphEdge> listOfEdges = new ArrayList<GraphEdge>();
							listOfEdges.add(firstArtifactEdge);
							listOfEdges.add(secondArtifactEdge);
							listOfEdges.add(artifactEdgeToRemove);
							
							float minWeight = 1.0f;
							
							// Find and Remove Edge with least weight
							for (GraphEdge anEdge: listOfEdges) {
								if (anEdge.getWeight() < minWeight) {
									edgesToRemove.clear();
									minWeight = anEdge.getWeight();
									edgesToRemove.add(anEdge);
								}
							}
							// Remove edges between nodes of same parent if only its weight is lower than
//							float firstEdgeWeight = firstArtifactEdge.getWeight();
//							float secondEdgeWeight = secondArtifactEdge.getWeight();
//							float bottomEdgeWeight = artifactEdgeToRemove.getWeight();
//							
//							
							
//							if ((bottomEdgeWeight < firstEdgeWeight) && (bottomEdgeWeight < secondEdgeWeight)) {
//								edgesToRemove.add(artifactEdgeToRemove);
//							} 
//							
//							else if ((firstEdgeWeight > bottomEdgeWeight) && (secondEdgeWeight < bottomEdgeWeight)) {
//								edgesToRemove.add(secondArtifactEdge);
//							} else if ((firstEdgeWeight < bottomEdgeWeight) && (secondEdgeWeight > bottomEdgeWeight)) {
//								edgesToRemove.add(firstArtifactEdge);
//							} else if ((firstEdgeWeight < bottomEdgeWeight) && (secondEdgeWeight < bottomEdgeWeight)) {
//								if (firstEdgeWeight > secondEdgeWeight) {
//									edgesToRemove.add(secondArtifactEdge);
//								} else {
//									edgesToRemove.add(firstArtifactEdge);
//								}
//							}
							
						}
					}
				}
			}
		}
		}
		

		allConnectionEdges.removeAll(edgesToRemove);
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
			
			// Add ChildNode to Taxonomy Representation 
			if (childNode != null) {
				taxonomyRootNode.addChildNodeFromRoot(childNode.getDescription().substring(4), parentNode.getDescription().substring(4));
			}

			// Assign childNode to Parent  
			GraphEdge parentChildEdge = new GraphEdge();
			parentChildEdge.setSource(parentNode);
			parentChildEdge.setTarget(childNode);
			parentChildEdge.setWeight(this.getHighestWeight(parentNode, childNode));
			
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

			// Recursively Call for the rest of the child/children Nodes
			GetChildrenForVariant(childNode, reachableByChild, false);

		}

	}
	
	/**
	 * Gets Children of a Variant for taxonomy (sub)tree
	 * 
	 * @param parentNode
	 */
	private void GetChildrenForVariantDAG(GraphNode parentNode, List<GraphNode> allAvailableArtifactNodes,
			boolean rootNode) {
		// After For-Loop Variables
		List<GraphNode> childrenNodes = null;
		// For-Loop Variables
		List<GraphNode> mostSimilarNodes = new ArrayList<GraphNode>();
		int noOfReacheableVariants;
		int noOfReacheableVariantsReferenceValue;

		while (allAvailableArtifactNodes.size() > 0) {
			mostSimilarNodes.clear(); // P
			noOfReacheableVariants = 0; // x
			noOfReacheableVariantsReferenceValue = 0; // ref
			// Get Nodes with the highest reachability
			for (GraphNode anArtifactNode : allAvailableArtifactNodes) {
				noOfReacheableVariants = getNumberOfEdgesFrom(parentNode, anArtifactNode);

				if (noOfReacheableVariants > noOfReacheableVariantsReferenceValue) {
					mostSimilarNodes.clear();
					noOfReacheableVariantsReferenceValue = noOfReacheableVariants;
				}

				if (noOfReacheableVariants == noOfReacheableVariantsReferenceValue) {
					mostSimilarNodes.add(anArtifactNode);
				}
			}

			// Get the mostSimilar Node out of the List of Similar Nodes
			childrenNodes = getNodeWithHighestSimilarityDAG(parentNode, mostSimilarNodes);
			
			// Add ChildNode to Taxonomy Representation 
			if (childrenNodes != null && childrenNodes.size() > 0) {
				for (GraphNode aChildNode: childrenNodes) {
					taxonomyRootNode.addChildNodeFromRoot(aChildNode.getDescription().substring(4), parentNode.getDescription().substring(4));
					
					// Assign childNode to Parent  
					GraphEdge parentChildEdge = new GraphEdge();
					parentChildEdge.setSource(parentNode);
					parentChildEdge.setTarget(aChildNode);
					parentChildEdge.setWeight(this.getHighestWeight(parentNode, aChildNode));
					
					// Add both to taxonomy Lists
					if (!taxonomyArtifactNodes.contains(parentNode)) {
						taxonomyArtifactNodes.add(parentNode);
					}

					if (!taxonomyArtifactNodes.contains(aChildNode)) {
						taxonomyArtifactNodes.add(aChildNode);
					}
					
					
					taxonomyArtifactEdges.add(parentChildEdge);
					
					// Get Reachable variants of child node
					List<GraphNode> reachableByChild = new ArrayList<GraphNode>();
					reachableByChild = getAllNodesReacheablebyNode(aChildNode, parentNode);

					// Remove child Node and remaining from Available list of Variants
					allAvailableArtifactNodes.remove(aChildNode);
					allAvailableArtifactNodes.removeAll(reachableByChild);
					removeEdgesWithTarget(parentNode);

					// Recursively Call for the rest of the child/children Nodes
					GetChildrenForVariantDAG(aChildNode, reachableByChild, false);
				}
			}

		}

	}
	
	
	private float getHighestWeight(GraphNode parent, GraphNode child) {
		float weight = 0.0f;
		for (GraphEdge aConnectingEdge : allConnectionEdges) {
			if((aConnectingEdge.getSource().getDescription().equals(parent.getDescription()) && aConnectingEdge.getTarget().getDescription().equals(child.getDescription()))
					|| (aConnectingEdge.getSource().getDescription().equals(child.getDescription()) && aConnectingEdge.getTarget().getDescription().equals(parent.getDescription())) ) {
				if (weight < aConnectingEdge.getWeight()) {
					weight = aConnectingEdge.getWeight();	
				}
			}
		}
		return weight;
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
			for (GraphNode aSimilarNode : mostSimilarNodes) {
				if (parentNode.getDescription().equals(aConectingEdge.getSource().getDescription()) && (aConectingEdge.getTarget().getDescription().equals(aSimilarNode.getDescription()))) {
					if (aConectingEdge.getWeight() > maxSimilarityValue) {
						mostSimilar = aConectingEdge.getTarget();
						maxSimilarityValue = aConectingEdge.getWeight();
					} else if (aConectingEdge.getWeight() == maxSimilarityValue) {
						mostSimilar = aConectingEdge.getTarget();
					}
				}

			}

		}

		return mostSimilar;
	}
	
	/**
	 * Find nodes most similar to parent node
	 * @param parentNode
	 * @param mostSimilarNodes
	 * @return
	 */
	private List<GraphNode> getNodeWithHighestSimilarityDAG(GraphNode parentNode, List<GraphNode> mostSimilarNodes) {
		List<GraphNode> mostSimilar = new ArrayList<GraphNode>();
		float maxSimilarityValue = 0;
		for (GraphEdge aConectingEdge : allConnectionEdges) {
			for (GraphNode aSimilarNode : mostSimilarNodes) {
				if (parentNode.getDescription().equals(aConectingEdge.getSource().getDescription()) && (aConectingEdge.getTarget().getDescription().equals(aSimilarNode.getDescription()))) {
					if (aConectingEdge.getWeight() > maxSimilarityValue) {
						mostSimilar.clear();
						mostSimilar.add(aConectingEdge.getTarget());
						maxSimilarityValue = aConectingEdge.getWeight();
					} else if (aConectingEdge.getWeight() == maxSimilarityValue) {
						mostSimilar.add(aConectingEdge.getTarget());
					}
				}

			}

		}

		return mostSimilar;
	}

	/***
	 * Identifies and Returns Root node(s) from a list of compared Artifacts
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
	private int getNumberOfEdgesFrom(GraphNode sourceNode, GraphNode targetNode) {
		int numberOfEdges = 0;

		for (GraphEdge aConectingEdge : allConnectionEdges) {
			// If the edge source variant is same as the source Node passed into the
			// function and the target node of the edge is not parent node
			// then increase count
			if (sourceNode.getDescription().equals(aConectingEdge.getSource().getDescription()))
				if (targetNode == null) {
					numberOfEdges++;
				} else {
					if (!aConectingEdge.getTarget().getDescription().equals(targetNode.getDescription())) {
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
		int rootVariantSize = getHighestSizeInVariants();
		GraphNode rootVariantNode = null;

		// iterate through all potential root nodes
		for (GraphNode aPotentialRootNode : potentialRootNodes) {
			// iterate through list of all artifact file details
			for (ArtifactFileDetails anArtifactFileDetails : allArtifactFileDetails) {
				// Find the right artifactFile detail and extract the number of characters
				if (aPotentialRootNode.getDescription().substring(0, 3).equals(anArtifactFileDetails.getArtifactID())) {
					// If number of characters of variant lower than previously calculated
					if (anArtifactFileDetails.getVariantSize() < rootVariantSize) {
						// Update rootVariantSize
						rootVariantSize = anArtifactFileDetails.getVariantSize();
						// Make variant the new root
						rootVariantNode = aPotentialRootNode;
					}
				}
			}
		}

		rootVariantNode.setColor(Color.PINK); // Change Root variant node Color.rgb(91, 127, 255)
		return rootVariantNode;
	}

	/**
	 * Gets value of the highest number of Characters from the variants (for file variants)
	 * or size(byte size) of largest variant (for folder variants)
	 */
	private int getHighestSizeInVariants() {
		int max = 0;
		for (ArtifactFileDetails anArtifactFileDetails : allArtifactFileDetails) {
			if (anArtifactFileDetails.getVariantSize() > max) {
				max = anArtifactFileDetails.getVariantSize();
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
		String artifactID = String.valueOf(System.identityHashCode(artifact)).substring(0, 3); 
		findAndUpdateArtifactID(artifact, artifactName, artifactID); // Update Artifact of the Node
		GraphNode center = new GraphNode();
		center.setTitle(artifact.getNodeType());
		center.setDescription(artifactID +"-"+ artifactName);
		center.setColor(Color.GREENYELLOW);
		//center.setBounds(new Rectangle(50 + (xAxisOffset * 200), 50 + (yAxisOffset * 550), WIDTH, 90));
		center.setBounds(new Rectangle(250, 550 + this.yAxisOffset, WIDTH, 90));

		this.xAxisOffset += 20;
		this.yAxisOffset += 20;
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
	
	/**
	 * Gets and Updates artifact ID of Artifact Details of a RootNode
	 * TODO: Update method to account for artifacts with same name
	 * @param artifact
	 * @param artifactID
	 */
	public void findAndUpdateArtifactID(Node artifact, String artifactName, String artifactID) {
		for (ArtifactFileDetails anArtifactFileDetails : allArtifactFileDetails) {
			if (anArtifactFileDetails.getArtifactName().equals(artifactName)) {
				anArtifactFileDetails.setArtifactID(artifactID);
				break;
			}
		}
	}
	
	public void printArtifactComparison() {
		for(ArtifactComparison comparison: artifactComparisonList) {
			System.out.println(" Left: " + comparison.getLeftArtifactName() + " Right: " + comparison.getRightArtifactName() + " Similarity: " + comparison.getNodeComparison().getSimilarity());
		}
	}
	
}
