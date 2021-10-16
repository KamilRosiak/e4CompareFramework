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
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparators.DirectoryNameComparator;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparators.DirectoryNonSourceFileComparator;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparators.DirectorySizeComparator;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparators.DirectorySourceFileComparator;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparators.LevenshteinStringComparator;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparators.SimpleStringComparator;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.comparison.TaxonomyNodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.ArtifactFileDetails;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.NodeComparisonResult;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.graph.ArtifactComparison;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.interfaces.ITaxonomyMatcher;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.interfaces.ITaxonomyMetric;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.util.ResultEngine;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.taxonomy_control_view.data_structures.TaxonomySettings;
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
	private LevenshteinStringComparator levenshteinStringComparator = new LevenshteinStringComparator();
	private SimpleStringComparator defaultComparator = new SimpleStringComparator();

	private ITaxonomyMetric metric;
	private ITaxonomyMatcher matcher;

	private TaxonomySettings taxonomySetting;

	public List<ArtifactComparison> artifactComparisonList = new ArrayList<ArtifactComparison>();
	private int artifactIndexCounter = 0;

	// Taxonomy related class members
	private Tree currentLeftArtifact = null;
	private Tree currentRightArtifact = null;

	private List<ArtifactFileDetails> allArtifactFileDetails = new ArrayList<ArtifactFileDetails>();

	public ResultEngine taxonomyResultEngine = new ResultEngine();
	
	
	public TaxonomyCompareEngine() {
		// Default Constructor
	}
	
	public TaxonomyCompareEngine(ITaxonomyMatcher _selectedMatcher, TaxonomySettings _taxonomySetting) {
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
	 * Derive Artifact Details and Compare
	 * @param variants
	 * @param allArtifactTreeElements
	 */
	public void deriveAndCompare(List<Tree> variants, List<FileTreeElement> allArtifactTreeElements) {
		// Derive and Compare
		deriveArtifactDetails(allArtifactTreeElements);
		compare(variants);
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
					Stopwatch singleCompSw = Stopwatch.createStarted();
					// Add Comparison to List for GraphView
					currentLeftArtifact = artifactLeft;
					currentRightArtifact = artifactRight;
					System.out.println(
							"Comparing Variants: " + artifactLeft.getTreeName() + " and " + artifactRight.getTreeName());
					compare(artifactLeft.getRoot(), artifactRight.getRoot());
					singleCompSw.stop();
					long scTimeElapsed = singleCompSw.elapsed(TimeUnit.MILLISECONDS);
					System.out.println("Single Comparison Time: " + scTimeElapsed / 1000 + " secs. (" + scTimeElapsed + " milliseconds)");
					singleCompSw.reset();
					singleCompSw.start();
					taxonomyResultEngine.matchNodes();
					singleCompSw.stop();
					scTimeElapsed = singleCompSw.elapsed(TimeUnit.MILLISECONDS);
					System.out.println("Single Comparison (Match Time) : " + scTimeElapsed / 1000 + " secs. (" + scTimeElapsed + " milliseconds)");
					
					
//					taxonomyResultEngine.printCommulativeResults();
					
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
				"Variants Matching Time: " + timeElapsed / 1000 + " secs. (" + timeElapsed + " milliseconds)");
				
		return mergedTree;
	}

	public TaxonomyNodeComparison compare(Node first, Node second) {
		TaxonomyNodeComparison comparison = new TaxonomyNodeComparison(first, second);
		// if nodes are of the same type
		if (first.getNodeType().equals(second.getNodeType())) {
			//System.out.println();
			// If Source Comparison Option selected
			if (taxonomySetting.getSourceLevelComparison()) {
				if (taxonomySetting.getLevenshteinMode()) {
					comparison.addResultElement(levenshteinStringComparator.compare(first, second));
				} else {
					comparison.addResultElement(defaultComparator.compare(first, second));
				}
			} else {
				if (first.getNodeType().equals("Directory")) {
					
					if (taxonomySetting.getDirNameMetric()) {
						DirectoryNameComparator directoryNameComparator = new DirectoryNameComparator();
						comparison.addResultElement(directoryNameComparator.compare(first, second));
					} 

					if (taxonomySetting.getDirSizeMetric()) {
						DirectorySizeComparator directorySizeComparator = new DirectorySizeComparator();
						comparison.addResultElement(directorySizeComparator.compareWithDetail(allArtifactFileDetails, first, second));
					}
					
					if (taxonomySetting.getDirNumSourceMetric()) {
						DirectorySourceFileComparator directorySourceFileComparator = new DirectorySourceFileComparator();
						comparison.addResultElement(directorySourceFileComparator.compareWithDetail(allArtifactFileDetails, first, second));
					}
					
					if (taxonomySetting.getDirNumNonSourceMetric()) {
						DirectoryNonSourceFileComparator directoryNonSourceFileComparator = new DirectoryNonSourceFileComparator();
						comparison.addResultElement(directoryNonSourceFileComparator.compareWithDetail(allArtifactFileDetails, first, second));
					}
					
					
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
					currentLeftArtifact, first, second, currentRightArtifact, comparison.calculateSimilarity(taxonomySetting));
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
	
	public void setTaxnomySettings(TaxonomySettings newSetting) {
		this.taxonomySetting = newSetting;
	}
	
	public Comparator getDefaultComparator() {
		return this.defaultComparator;
	}

	public ITaxonomyMatcher getTaxonomyMatcher() {
		return this.matcher;
	}
	
	public ITaxonomyMetric getTaxonomyMetric() {
		return this.metric;
	}

}
