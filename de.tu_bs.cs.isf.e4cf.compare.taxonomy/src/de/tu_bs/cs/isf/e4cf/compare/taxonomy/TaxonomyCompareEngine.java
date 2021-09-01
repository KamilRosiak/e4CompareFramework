package de.tu_bs.cs.isf.e4cf.compare.taxonomy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import com.google.common.base.Stopwatch;
import de.tu_bs.cs.isf.e4cf.compare.comparator.interfaces.Comparator;
import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.matcher.interfaces.Matcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.interfaces.Metric;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparators.DirectoryNameComparator;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparators.DirectorySizeComparator;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparators.LevenshteinStringComparator;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparators.SimpleStringComparator;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparison.TaxonomyNodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.ArtifactFileDetails;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.NodeComparisonResult;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.graph.ArtifactComparison;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.interfaces.ITaxonomyMatcher;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.interfaces.ITaxonomyMetric;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.util.ResultEngine;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.taxonomy_control_view.data_structures.TaxonomyControlSettings;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;

/**
 * Decomposes two trees and compares , match and merges them hierarchical which
 * decomposes the Node bottom/up.
 * 
 * @author developer_olan
 *
 */
public class TaxonomyCompareEngine {
	private SimpleStringComparator levenshteinStringComparator = new SimpleStringComparator();
	private SimpleStringComparator defaultComparator = new SimpleStringComparator();

	private DirectoryNameComparator directoryNameComparator = new DirectoryNameComparator();
	private DirectorySizeComparator directorySizeComparator = new DirectorySizeComparator();

	private ITaxonomyMetric metric;
	private ITaxonomyMatcher matcher;

	private TaxonomyControlSettings taxonomySetting;

	public List<ArtifactComparison> artifactComparisonList = new ArrayList<ArtifactComparison>();
	private int artifactIndexCounter = 0;

	// Taxonomy related class members
	private Tree currentLeftArtifact = null;
	private Tree currentRightArtifact = null;

	private List<ArtifactFileDetails> allArtifactFileDetails = new ArrayList<ArtifactFileDetails>();

	public ResultEngine taxonomyResultEngine = new ResultEngine();

	public TaxonomyCompareEngine(ITaxonomyMatcher _selectedMatcher, TaxonomyControlSettings _taxonomySetting) {
		this.matcher = _selectedMatcher;
		this.taxonomySetting = _taxonomySetting;
	}

	@Inject
	ServiceContainer services;

	/**
	 * Populates list of artifact to prepare for taxonomy computation
	 * 
	 * @param artifactComparison
	 * @param leftArtifactName
	 * @param rightArtifactName
	 */
	public void addArtifactComparisonsForGraph(TaxonomyNodeComparison artifactComparison, String leftArtifactName,
			String rightArtifactName) {
		artifactComparisonList.add(new ArtifactComparison(artifactComparison, leftArtifactName, rightArtifactName));
	}

	/**
	 * adds list to all artifacts to engine list of artifacts
	 * 
	 * @param allArtifactPaths
	 */
	public void deriveArtifactDetails(List<FileTreeElement> allArtifactTreeElements) {
		for (FileTreeElement anArtifactFileTreeElement : allArtifactTreeElements) {
			allArtifactFileDetails.add(new ArtifactFileDetails(anArtifactFileTreeElement));
		}
	}

	/**
	 * Compares variants in a list
	 */
	public Tree compare(List<Tree> variants) {
		Tree mergedTree = null;
		// Creates and starts a new stopwatch
		Stopwatch stopwatch = Stopwatch.createStarted();

		variants.stream().forEach(artifactLeft -> {
			variants.stream().forEach(artifactRight -> {
				if (artifactLeft != artifactRight) {
					// Add Comparison to List for GraphView
					currentLeftArtifact = artifactLeft;
					currentRightArtifact = artifactRight;
					System.out.println(
							"Comparing Nodes: " + artifactLeft.getTreeName() + " and " + artifactRight.getTreeName());
					compare(artifactLeft.getRoot(), artifactRight.getRoot());
					taxonomyResultEngine.matchNodes();
					taxonomyResultEngine.printCommulativeResults();

				}

			});

			artifactIndexCounter++; // Increment artifact/variant counter by one
		});

		// Finalize Matching
		taxonomyResultEngine.createRefinedListofNodes();
		taxonomyResultEngine.computeWeightedSimilarity();

		artifactComparisonList = taxonomyResultEngine.createArtifactComparison();

		// stop stop watch, get elapsed time, expressed in milliseconds
		stopwatch.stop();
		long timeElapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		System.out.println(
				"Taxonomy Generation Time: " + timeElapsed / 1000 + " secs. (" + timeElapsed + " milliseconds)");
		return mergedTree;
	}

	public TaxonomyNodeComparison compare(Node first, Node second) {
		TaxonomyNodeComparison comparison = new TaxonomyNodeComparison(first, second);
		// if nodes are of the same type
		if (first.getNodeType().equals(second.getNodeType())) {

			// If Source Comparison Option selected
			if (taxonomySetting.getSourceLevelComparison()) {
				if (taxonomySetting.getLevenshteinMode()) {
					comparison.addResultElement(levenshteinStringComparator.compare(first, second));
				} else {
					comparison.addResultElement(defaultComparator.compare(first, second));
				}
			} else {
				if (first.getNodeType().equals("Directory")) {
					comparison.addResultElement(directoryNameComparator.compare(first, second));
				} else {
					if (taxonomySetting.getLevenshteinMode()) {
						comparison.addResultElement(levenshteinStringComparator.compare(first, second));
					} else {
						comparison.addResultElement(defaultComparator.compare(first, second));
					}
				}
			}

			// Add Result to Node Comparison Result
			NodeComparisonResult nodeComparisonResult = new NodeComparisonResult(artifactIndexCounter,
					currentLeftArtifact, first, second, currentRightArtifact, comparison.getResultSimilarity());
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

							TaxonomyNodeComparison innerComp = compare(e, f);
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

	public Comparator getDefaultComparator() {
		return this.defaultComparator;
	}

	public ITaxonomyMatcher getTaxonomyMatcher() {
		return this.matcher;
	}

}
