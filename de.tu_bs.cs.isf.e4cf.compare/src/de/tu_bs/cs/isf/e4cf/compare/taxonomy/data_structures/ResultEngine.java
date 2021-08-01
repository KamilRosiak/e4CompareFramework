/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;

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

	private static final float SIMILARITY_THRESHOLD = 0.4f;

	public ResultEngine() {

	}

	public List<NodeComparisonResult> getListOfComparedNodes() {
		return listOfComparedNodes;
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
	public void computeMatching() {
		for (NodeComparisonResult aComparedNodesTuple : listOfComparedNodes) {
			// Remove Matching which are lower than threshold
			if (aComparedNodesTuple.getSimilarity() <= this.SIMILARITY_THRESHOLD) {
				listOfComparedNodesRemoved.add(aComparedNodesTuple);
			}
		}

		listOfComparedNodes.removeAll(listOfComparedNodesRemoved);

		System.out.println("Removed: " + listOfComparedNodesRemoved.size());
		System.out.println("Kept: " + listOfComparedNodes.size());
	}

	

	/**
	 * STEP 1:
	 * Match Nodes to extract unique Node comparisons with the highest similarity value
	 */
	public void matchNodes() {
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

				// Removed found matches from old lists
				removeMappingByKey(potentialResultMapping, m_matchedFragment); // Remove other potential mapping(s) after picking highest match (for one-to-one node mappings)
				removeTriplesFromEachResultMappings(T_potential_ResultMap.getMappedResults(), directResultMapping); // Remove Triple matches of potential matches from original match triples (so node is not compared again to other matches)

			}
		}

	}
	
	/**
	 * STEP 2:
	 * Create list of refined unique comparisons 
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
	 * 
	 */
	public void compute() {
		
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
