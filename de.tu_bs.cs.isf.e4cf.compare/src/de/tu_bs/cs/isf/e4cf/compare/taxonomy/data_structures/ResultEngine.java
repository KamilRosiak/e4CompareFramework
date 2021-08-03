/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.graph.ArtifactComparison;

/**
 * @author developer-olan
 *
 */
public class ResultEngine {
	private List<NodeComparisonResult> listOfComparedNodes = new ArrayList<NodeComparisonResult>();
	private List<NodeComparisonResult> listOfComparedNodesRemoved = new ArrayList<NodeComparisonResult>();
	private List<NodeComparisonResult> listOfComparedNodesRefined = new ArrayList<NodeComparisonResult>();

	private List<ResultMapping> directResultMapping = new ArrayList<ResultMapping>();
	private List<ResultMapping> potentialResultMapping = new ArrayList<ResultMapping>();
	private List<SimpleResult> matchingVariantSetMapping = new ArrayList<SimpleResult>();

	private List<CollectedComparison> cumulativeComparisons = new ArrayList<CollectedComparison>();

	public List<ArtifactComparison> artifactComparisonList = new ArrayList<ArtifactComparison>();

	private static final float SIMILARITY_THRESHOLD = 0.4f;

	public ResultEngine() {

	}

	public List<NodeComparisonResult> getListOfComparedNodes() {
		return listOfComparedNodes;
	}

	public void addToCumulativeComparisons(Tree leftArtifactToInsert, Tree rightArtifactToInsert,
			float similarityToAdd) {
		CollectedComparison foundComparison = null;
		for (CollectedComparison eachComparison : cumulativeComparisons) {
			if (eachComparison.getLeftArtifact().equals(leftArtifactToInsert)
					&& eachComparison.getRightArtifact().equals(rightArtifactToInsert)) {
				foundComparison = eachComparison;
				break;
			}
		}

		if (foundComparison != null) {
			foundComparison.addCummulativeSimilarity(similarityToAdd);
		} else {
			cumulativeComparisons
					.add(new CollectedComparison(leftArtifactToInsert, rightArtifactToInsert, similarityToAdd));
		}

	}

	public void addToListOfComparedNodes(NodeComparisonResult compResult) {
		this.listOfComparedNodes.add(compResult);
		addResultToMapping(this.directResultMapping, compResult.getLeftNodeSignature(),
				compResult.getLeftNodeSignature(), compResult.getRightNodeSignature(), compResult.getSimilarity());
		addResultToMapping(this.potentialResultMapping, compResult.getRightNodeSignature(),
				compResult.getLeftNodeSignature(), compResult.getRightNodeSignature(), compResult.getSimilarity());
	}

	/**
	 * Removes Matchings which don't meet the threshold
	 */
	private void applyThresholdMatching(List<NodeComparisonResult> selectedListOfComparedNodes) {
		for (NodeComparisonResult aComparedNodesTuple : selectedListOfComparedNodes) {
			// Remove Matching which are lower than threshold
			if (aComparedNodesTuple.getSimilarity() <= this.SIMILARITY_THRESHOLD) {
				listOfComparedNodesRemoved.add(aComparedNodesTuple);
			}
		}

		selectedListOfComparedNodes.removeAll(listOfComparedNodesRemoved);

		System.out.println("Removed: " + listOfComparedNodesRemoved.size());
		System.out.println("Kept: " + listOfComparedNodes.size());
	}

	/**
	 * STEP 1: Match Nodes to extract unique Node comparisons with the highest
	 * similarity value
	 */
	public void matchNodes() {
		applyThresholdMatching(listOfComparedNodes); // Remove Node comparisons that do not meet threshold
		while (directResultMapping.size() > 0) {
			ResultMapping resultMap = directResultMapping.get(0);
			if (resultMap.getMappedResults().size() == 0) {
				directResultMapping.remove(0);
			} else {
				resultMap.sortResultsBySimilarity(); // first sort Matches by similarity (descending)
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

			}
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
					listOfComparedNodesRefined.add(aNodeComparison);
				}
			}
		}
	}

	/**
	 * STEP 3: Compute weighted Similarity of Nodes and Variants
	 */
	public void computeWeightedSimilarity() {
		for (NodeComparisonResult comparedNodes : listOfComparedNodesRefined) {
			float comparisonWeightedSimilarity = 0.0f;
			comparisonWeightedSimilarity = comparedNodes.getLeftNodeWeight() * comparedNodes.getSimilarity();
			comparedNodes.setWeightedSimilarity(comparisonWeightedSimilarity);

			addToCumulativeComparisons(comparedNodes.getArtifactOfLeftNode(), comparedNodes.getArtifactOfRightNode(),
					comparedNodes.getWeightedSimilarity());
		}
	}

	/**
	 * STEP 4: Creates artifact comparison for graph
	 */
	public List<ArtifactComparison> createArtifactComparison() {
		for (CollectedComparison cummulative : cumulativeComparisons) {

			NodeComparison newArtifactNodeComparison = new NodeComparison(cummulative.getLeftArtifact().getRoot(),
					cummulative.getRightArtifact().getRoot(), cummulative.getCummulativeSimilarity());
			ArtifactComparison newArtifactComparison = new ArtifactComparison(newArtifactNodeComparison,
					cummulative.getLeftArtifact().getTreeName(), cummulative.getRightArtifact().getTreeName());
			artifactComparisonList.add(newArtifactComparison);
		}

		return artifactComparisonList;
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

	private void removeMappingByKey(List<ResultMapping> potentialResultMapping, String fragmentKey) {
		potentialResultMapping.removeIf((element) -> {
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
		for (NodeComparisonResult aComparedNodesTuple : listOfComparedNodes) {
			System.out.print(aComparedNodesTuple.getArtifactIndex());
			System.out.print(aComparedNodesTuple.getArtifactOfLeftNode().getTreeName().toString() + " 			| "
					+ aComparedNodesTuple.getLeftNode().getNodeType().toString());
			System.out.print(trimNodeDetails(aComparedNodesTuple.getLeftNode().getAttributes()) + " 			| "
					+ aComparedNodesTuple.getRightNode().getNodeType().toString());
			System.out.print(trimNodeDetails(aComparedNodesTuple.getRightNode().getAttributes()) + " 			| ");
			System.out.print(aComparedNodesTuple.getSimilarity());
			System.out.println();
		}
		System.out.println("Total: " + listOfComparedNodes.size());
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
