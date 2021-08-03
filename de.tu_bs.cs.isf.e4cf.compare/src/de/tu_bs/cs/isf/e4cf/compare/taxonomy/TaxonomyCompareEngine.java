package de.tu_bs.cs.isf.e4cf.compare.taxonomy;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.tu_bs.cs.isf.e4cf.compare.comparator.impl.node.NodeResultElement;
import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;
import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.compare.interfaces.ICompareEngine;
import de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces.Matcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.interfaces.Metric;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparators.SimpleStringComparator;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.ArtifactFileDetails;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.NodeComparisonResult;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.ResultEngine;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.graph.ArtifactComparison;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.interfaces.TaxonomyMetric;

/**
 * Decomposes two trees and compares , match and merges them hierarchical which
 * decomposes the Node bottom/up.
 * 
 * @author developer_olan
 *
 */
public class TaxonomyCompareEngine implements ICompareEngine<Node> {
	private SimpleStringComparator defaultComparator = new SimpleStringComparator();
	private TaxonomyMetric metric;
	private Matcher matcher;
	
	private Boolean asymmetry;

	public List<ArtifactComparison> artifactComparisonList = new ArrayList<ArtifactComparison>();
	private int artifactIndexCounter = 0;
	
	// Taxonomy related class members
	private Tree currentLeftArtifact = null;
	private Tree currentRightArtifact = null;
	
	private List<ArtifactFileDetails> allArtifactFileDetails = new ArrayList<ArtifactFileDetails>();

	
	public ResultEngine taxonomyResultEngine = new ResultEngine();
	

	public TaxonomyCompareEngine(Matcher _selectedMatcher, boolean _asymmetry) {
		this.matcher = _selectedMatcher;
		this.asymmetry = _asymmetry;
	}

	@Inject
	ServiceContainer services;

	@Override
	public Tree compare(Tree first, Tree second) {
		try {
			// compare
			NodeComparison root = compare(first.getRoot(), second.getRoot());
			// match
			root.updateSimilarity();
			getMatcher().calculateMatching(root);
			// getMatcher().sortBySimilarityDesc(artifactComparisonList);
			root.updateSimilarity();
			// Merge
			Node mergedRoot = root.mergeArtifacts();
			return new TreeImpl(first, second, mergedRoot);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Populates list of artifact to prepare for taxonomy computation
	 * 
	 * @param artifactComparison
	 * @param leftArtifactName
	 * @param rightArtifactName
	 */
	public void addArtifactComparisonsForGraph(NodeComparison artifactComparison, String leftArtifactName,
			String rightArtifactName) {
		artifactComparisonList.add(new ArtifactComparison(artifactComparison, leftArtifactName, rightArtifactName));
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

	@Override
	public Tree compare(List<Tree> variants) {
		Tree mergedTree = null;
		variants.stream().forEach(artifactLeft -> {
			variants.stream().forEach(artifactRight -> {
				if (artifactLeft != artifactRight) {
						// Add Comparison to List for GraphView
						currentLeftArtifact = artifactLeft;
						currentRightArtifact = artifactRight;
						NodeComparison root = compare(artifactLeft.getRoot(), artifactRight.getRoot());
						root.updateSimilarity();
						getMatcher().calculateMatching(root);
						root.updateSimilarity();
						addArtifactComparisonsForGraph(root, artifactLeft.getTreeName(), artifactRight.getTreeName());
				}

			});
			
			artifactIndexCounter++; // Increment artifact/variant counter by one
		});
		
		if (asymmetry) {
			taxonomyResultEngine.matchNodes();
			taxonomyResultEngine.createRefinedListofNodes();
			taxonomyResultEngine.computeWeightedSimilarity();
			artifactComparisonList = taxonomyResultEngine.createArtifactComparison();
		}
		
		return mergedTree;
	}

	
	@Override
	public NodeComparison compare(Node first, Node second) {
		NodeComparison comparison = new NodeComparison(first, second);
		// if nodes are of the same type
		if (first.getNodeType().equals(second.getNodeType())) {
			NodeResultElement nodeResultElement = defaultComparator.compare(first, second); // Compare Default
			comparison.addResultElement(nodeResultElement);
			
			// Add Result to Node Comparison Result
			NodeComparisonResult nodeComparisonResult = new NodeComparisonResult(artifactIndexCounter, currentLeftArtifact, first, second, currentRightArtifact, comparison.getResultSimilarity());
			taxonomyResultEngine.addToListOfComparedNodes(nodeComparisonResult);
			
			// if no children available the recursion ends here
			if (first.getChildren().isEmpty() && second.getChildren().isEmpty()) {
				return comparison;
			} else {
				// if one of both has no children the other elements are optional
				if (first.getChildren().isEmpty() || second.getChildren().isEmpty()) {
					first.getChildren().stream()
							.forEach(e -> comparison.addChildComparison(new NodeComparison(e, null, 0f)));
					second.getChildren().stream()
							.forEach(e -> comparison.addChildComparison(new NodeComparison(null, e, 0f)));
				} else {
					// compare children recursively
					first.getChildren().stream().forEach(e -> {
						second.getChildren().stream().forEach(f -> {

							NodeComparison innerComp = compare(e, f);
							if (innerComp != null) {
								comparison.addChildComparison(innerComp);
							}
						});
					});
				}
			}
			return comparison;
		} else {
			return comparison;
		}
	}

	@Override
	public Comparator getDefaultComparator() {
		return defaultComparator;
	}

	@Override
	public Matcher getMatcher() {
		return this.matcher;
	}

	@Override
	public Metric getMetric() {
		// TODO Auto-generated method stub
		return null;
	}

}
