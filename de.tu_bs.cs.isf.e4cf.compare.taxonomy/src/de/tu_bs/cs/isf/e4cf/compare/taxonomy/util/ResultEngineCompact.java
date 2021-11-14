/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.util;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparison.TaxonomyNodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.CollectedComparison;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.NodeComparisonResult;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.ResultMapping;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.SimpleResult;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.graph.ArtifactComparison;

/**
 * @author developer-olan
 *
 */
public class ResultEngineCompact {
	// Data storage for Level Comparison
	public List<NodeComparisonResult> listOfComparedNodes = new ArrayList<NodeComparisonResult>();
	public List<NodeComparisonResult> listOfComparedNodesRefined = new ArrayList<NodeComparisonResult>();

	private List<ResultMapping> directResultMapping = new ArrayList<ResultMapping>();
	private List<ResultMapping> potentialResultMapping = new ArrayList<ResultMapping>();
	private List<SimpleResult> matchingVariantSetMapping = new ArrayList<SimpleResult>();

	private List<CollectedComparison> cummulativeComparisons = new ArrayList<CollectedComparison>();

	public List<ArtifactComparison> artifactComparisonList = new ArrayList<ArtifactComparison>();

	private static final float SIMILARITY_THRESHOLD = 0.45f;
	
	private static final String EMPTY_TREE_NAME = "00000";

	public ResultEngineCompact() {

	}

//	public List<NodeComparisonResult> getListOfComparedNodes() {
//		return listOfComparedNodes;
//	}

	public void addToCumulativeComparisons(Tree leftArtifactToInsert, Tree rightArtifactToInsert,
			float similarityToAdd, boolean isDirectory) {
		CollectedComparison foundComparison = null;
		for (CollectedComparison eachComparison : cummulativeComparisons) {
			if (eachComparison.getLeftArtifact().equals(leftArtifactToInsert)
					&& eachComparison.getRightArtifact().equals(rightArtifactToInsert)) {
				foundComparison = eachComparison;
				break;
			}
		}

		if (foundComparison != null) {
			foundComparison.addCummulativeSimilarity(similarityToAdd, isDirectory);
		} else {
			cummulativeComparisons
					.add(new CollectedComparison(leftArtifactToInsert, rightArtifactToInsert, similarityToAdd, isDirectory));
		}

	}

	public void addToListOfComparedNodes(NodeComparisonResult compResult) {
		if ((compResult.getSimilarity() >= ResultEngineCompact.SIMILARITY_THRESHOLD || compResult.getSimilarity() == 0.0f) && compResult.getLeftNodeWeight() > 0.0f) {
			this.listOfComparedNodes.add(compResult);
			addResultToMapping(this.directResultMapping, compResult.getLeftNodeSignature(),
					compResult.getLeftNodeSignature(), compResult.getRightNodeSignature(), compResult.getSimilarity());
			addResultToMapping(this.potentialResultMapping, compResult.getRightNodeSignature(),
					compResult.getLeftNodeSignature(), compResult.getRightNodeSignature(), compResult.getSimilarity());
		}
	}

	private void removeWeightlessMappings() {
		List<NodeComparisonResult> nodesToRemove = new ArrayList<NodeComparisonResult>();
		for (NodeComparisonResult aNodeComparison : listOfComparedNodes) {
			if (aNodeComparison.getLeftNodeWeight() == 0.0f) {
				nodesToRemove.add(aNodeComparison);
			}
		}
		
		listOfComparedNodes.removeAll(nodesToRemove);
	}
	
	/**
	 * STEP 1: Match Nodes to extract unique Node comparisons with the highest
	 * similarity value
	 */
	public void matchNodes() {
		removeWeightlessMappings(); // Remove Node comparisons that do not meet threshold (under matched or only matched by type)
		while (directResultMapping.size() > 0) {
			ResultMapping resultMap = directResultMapping.get(0);
			if (resultMap.getMappedResults().size() == 0) {
				directResultMapping.remove(0);
			} else {
				resultMap.sortResultsBySimilarity(); // first Sort Matches by similarity (descending)
				SimpleResult t_bestTriple = resultMap.getMappedResults().get(0); // Then pick that with the highest
																					// similarity
				String m_matchedFragment = t_bestTriple.getRightNode();
				ResultMapping T_potential_ResultMap = findMappingByKey(potentialResultMapping, m_matchedFragment);
				T_potential_ResultMap.sortResultsBySimilarity(); // sort Matches by similarity (descending)
				SimpleResult t_matchedTriple = T_potential_ResultMap.getMappedResults().get(0); // Get t_matched with
																								// highest similarity

				matchingVariantSetMapping.add(t_matchedTriple); // Add Matching to final Match
				
				// Remove other potential mapping(s) after picking highest match (for one-to-one node mappings)
				removeMappingByKey(potentialResultMapping, m_matchedFragment); 
				
				// Remove Triple matches of potential matches from original match 
				// triples (so node is not compared again to other matches)
				removeTriplesFromEachResultMappings(T_potential_ResultMap.getMappedResults(), directResultMapping); 
				
				// Also Remove fragment whose best match has been found in potential (Brute force Removal)
				removeMappingByKey(directResultMapping, t_bestTriple.getLeftNode()); 

			}
		}

	}
	
	public void printCommulativeResults() {
		for (SimpleResult aSimpleResult: matchingVariantSetMapping) {
			System.out.println("Similarity: "+aSimpleResult.getSimilarity()+", Type: "+aSimpleResult.getLeftNode()+" Type: "+ aSimpleResult.getRightNode());
		}
	}

	/**
	 * STEP 2: Create list of refined unique comparisons
	 */
	public void createRefinedListofNodes() {
		for (SimpleResult aSimpleResult : matchingVariantSetMapping) {
			for (NodeComparisonResult aNodeComparison : listOfComparedNodes) {
				if (aNodeComparison.getLeftNodeSignature().equals(aSimpleResult.getLeftNode())
						&& aNodeComparison.getRightNodeSignature().equals(aSimpleResult.getRightNode())
						&& aNodeComparison.getSimilarity() == aSimpleResult.getSimilarity()) {
					if (!listOfComparedNodesRefined.contains(aNodeComparison)) {

						listOfComparedNodesRefined.add(aNodeComparison);
					}
				}
			}
		}
		
		List<NodeComparisonResult> nodesCompToAdd = new ArrayList<NodeComparisonResult>();
		Boolean foundInNew = null;
		for (NodeComparisonResult anOldNodeComparison : listOfComparedNodes) {
			foundInNew = false;
			for (NodeComparisonResult aNewNodeComparison : listOfComparedNodesRefined) {
				if (anOldNodeComparison.getLeftNodeSignature().equals(aNewNodeComparison.getLeftNodeSignature())
						&& anOldNodeComparison.getArtifactOfRightNode().equals(aNewNodeComparison.getArtifactOfRightNode())) {
					foundInNew = true;
				}
			}
			if (!foundInNew && (!nodesCompToAdd.contains(anOldNodeComparison))) {
				anOldNodeComparison.setRightToEmptyTree(EMPTY_TREE_NAME);
				nodesCompToAdd.add(anOldNodeComparison);
			}
		}
		
		listOfComparedNodesRefined.addAll(nodesCompToAdd);
		
	}

	/**
	 * STEP 3: Compute weighted Similarity of Nodes and Variants
	 */
	public void computeWeightedSimilarity() {
		for (NodeComparisonResult comparedNodes : listOfComparedNodesRefined) {
			float comparisonWeightedSimilarity = 0.0f;
			if (!comparedNodes.IsDirectory()) {
				comparisonWeightedSimilarity = comparedNodes.getLeftNodeWeight() * comparedNodes.getSimilarity();
			} else {
				comparisonWeightedSimilarity = comparedNodes.getLeftNodeWeight() * comparedNodes.getSimilarity();
			}
			
			comparedNodes.setWeightedSimilarity(comparisonWeightedSimilarity);
//			printNodeDetails(comparedNodes);
			addToCumulativeComparisons(comparedNodes.getArtifactOfLeftNode(), comparedNodes.getArtifactOfRightNode(),
					comparedNodes.getWeightedSimilarity(), comparedNodes.IsDirectory());
			
		}
	}

	
	public void printNodeDetails(NodeComparisonResult comparedNodes) {
		System.out.println(comparedNodes.getArtifactOfLeftNode().getTreeName().toString() + "	|	"+comparedNodes.getArtifactOfRightNode().getTreeName().toString());
		for (Attribute aNodeAttribute: comparedNodes.getLeftNode().getAttributes()) {
			for (Value anAttValue: aNodeAttribute.getAttributeValues()) {
				System.out.println(anAttValue.getValue().toString());
			}
		}
		
		System.out.println("---------------vs--------------");
		
		for (Attribute aNodeAttribute: comparedNodes.getRightNode().getAttributes()) {
			for (Value anAttValue: aNodeAttribute.getAttributeValues()) {
				System.out.println(anAttValue.getValue().toString());
			}
		}
		System.out.println(comparedNodes.getSimilarity() + " 	| ");
		System.out.println("-----------------------------");
	}

	/**
	 * STEP 4: Creates artifact comparison for graph
	 */
	public List<ArtifactComparison> createArtifactComparison() {
		
		
		for (CollectedComparison cummulative : cummulativeComparisons) {
			if (!cummulative.getRightArtifact().getTreeName().equals(EMPTY_TREE_NAME)) {

				TaxonomyNodeComparison newArtifactNodeComparison = new TaxonomyNodeComparison(cummulative.getLeftArtifact().getRoot(),
						cummulative.getRightArtifact().getRoot(), cummulative.getCummulativeSimilarity());
				ArtifactComparison newArtifactComparison = new ArtifactComparison(newArtifactNodeComparison,
						cummulative.getLeftArtifact().getTreeName(), cummulative.getRightArtifact().getTreeName());
				artifactComparisonList.add(newArtifactComparison);
			}
		}
		
		// Normalize Similarity
		normalizeArtifactSimilarities();

		return artifactComparisonList;
	}
	
	public void normalizeArtifactSimilarities() {
		float minSim = 0.0f;
		float maxSim = 0.0f;
		
		// Sort by Weighted Similarity (Descending) 
		artifactComparisonList.sort((first, second) -> {
			return -1 * Float.compare(first.getNodeComparison().getSimilarity(), second.getNodeComparison().getSimilarity());
		});
		
		
		if (artifactComparisonList.size() > 2) {
			minSim = artifactComparisonList.get(artifactComparisonList.size() - 1).getNodeComparison().getSimilarity();
			maxSim = artifactComparisonList.get(0).getNodeComparison().getSimilarity();
			
			for (ArtifactComparison comparedNodes : artifactComparisonList) {
				float newWeightedSim = (comparedNodes.getNodeComparison().getSimilarity() - minSim)/ (maxSim - minSim);
				comparedNodes.getNodeComparison().setSimilarity(newWeightedSim);
			}
		}	
	}

	private void removeTriplesFromEachResultMappings(List<SimpleResult> simpleResults,
			List<ResultMapping> selectedResultMapping) {
		for (SimpleResult simpleResult : simpleResults) {
			for (ResultMapping aResultMapping : selectedResultMapping) {
				aResultMapping.getMappedResults().removeIf((element) -> {
					return element.getLeftNode().equals(simpleResult.getLeftNode())
							&& element.getRightNode().equals(simpleResult.getRightNode());
				});
			}
		}
	}

	private void removeMappingByKey(List<ResultMapping> givenResultMapping, String fragmentKey) {
		givenResultMapping.removeIf((element) -> {
			// Remove all elements whose key matches the above statement
			return element.getMappingKey().equals(fragmentKey);
		});

	}

	public void addResultToMapping(List<ResultMapping> selectedResultMapping, String keyFragment, String leftNode,
			String rightNode, float similarity) {
		ResultMapping foundMapping = findMappingByKey(selectedResultMapping, keyFragment);
		if (foundMapping == null) {
			selectedResultMapping.add(new ResultMapping(keyFragment, leftNode, rightNode, similarity));
		} else {
			foundMapping.getMappedResults().add(new SimpleResult(leftNode, rightNode, similarity));
		}
	}

	public ResultMapping findMappingByKey(List<ResultMapping> selectedResultMapping, String keyFragment) {
		ResultMapping foundResultMapping = null;
		for (ResultMapping aDirectResultMapping : selectedResultMapping) {
			if (aDirectResultMapping.getMappingKey().equals(keyFragment)) {
				foundResultMapping = aDirectResultMapping;
				break;
			}
		}
		return foundResultMapping;
	}

	public void printMatchingResults() {
		for (NodeComparisonResult aComparedNodesTuple : listOfComparedNodesRefined) {
			//System.out.print(aComparedNodesTuple.IsDirectory());
//			System.out.print(aComparedNodesTuple.getArtifactIndex());
			System.out.print(aComparedNodesTuple.getArtifactOfLeftNode().getTreeName().toString() + " 	| "
					+ aComparedNodesTuple.getLeftNode().getNodeType().toString());
			System.out.print(trimNodeDetails(aComparedNodesTuple.getLeftNode().getAttributes()) + " 	| "
					+ aComparedNodesTuple.getRightNode().getNodeType().toString());
			System.out.print(trimNodeDetails(aComparedNodesTuple.getRightNode().getAttributes()) + " 	| ");
			System.out.print(aComparedNodesTuple.getArtifactOfRightNode().getTreeName().toString() + " 	| ");
			System.out.print(aComparedNodesTuple.getSimilarity() + " 	| ");
			System.out.print(aComparedNodesTuple.getLeftNodeWeight() + " 	| ");
			System.out.print(aComparedNodesTuple.getWeightedSimilarity() + " 	| ");
			System.out.println();
		}
		System.out.println("Total: " + listOfComparedNodesRefined.size());
	}

	private String trimNodeDetails(List<Attribute> attributesSet) {
		String firstAttributeID = "";
		for (Attribute anAttribute : attributesSet) {
			firstAttributeID = anAttribute.toString();
			firstAttributeID = firstAttributeID.split("@")[1];
			firstAttributeID = "@" + firstAttributeID;
			break;
		}

		return firstAttributeID;
	}

}