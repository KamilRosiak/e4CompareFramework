/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.batch_API;

import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.TaxonomyCompareEngine;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.data_structures.VariantTaxonomyNode;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.graph.ArtifactComparison;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.graph.ArtifactGraph;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.util.TaxonomyEvaluator;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.Directory;
import de.tu_bs.cs.isf.e4cf.core.file_structure.tree.DefaultTreeBuilder;
import de.tu_bs.cs.isf.e4cf.core.file_structure.tree.util.TreeVisitor;
import de.tu_bs.cs.isf.e4cf.core.gui.swt.util.TreeBuilder;

/**
 * @author developer-olan
 *
 */
public class BatchEvaluator {

	// public final static String[] SUPPORTED_FILE_ENDINGS = { "java", "h", "cpp" };
	public final static String SUPPORTED_FILE_ENDINGS = "java";
	private final static String datasetHomePath = "C:\\Users\\olant\\runtime-de.tu_bs.cs.isf.e4cf.core.product\\ 01 RAW\\";
	private final static String groundTruthLocation = "GCJ\\2008-SP-SD-SL\\GT\\";
	private final static String datasetLocation = "GCJ\\2008-SP-SD-SL\\";
	
	private List<TaxonomyEvaluationItem> datasetBatch = new ArrayList<TaxonomyEvaluationItem>(); 
	
	
	List<FileTreeElement> artifactFileTrees = new ArrayList<FileTreeElement>();  
	
	// Mid N=3
	TaxonomyEvaluationItem halyavinN3D = new TaxonomyEvaluationItem("halyavinN3D", "N=3\\halyavin\\Problem-D", GCJGroundTruth.createGoogleCode2008N3halyavinDGT());
	TaxonomyEvaluationItem jediknightN3B = new TaxonomyEvaluationItem("jediknightN3B", "N=3\\jediknight\\Problem-B", GCJGroundTruth.createGoogleCode2008N3jediknightBGT());
	TaxonomyEvaluationItem kotehokN3A = new TaxonomyEvaluationItem("kotehokN3A", "N=3\\kotehok\\Problem-A", GCJGroundTruth.createGoogleCode2008N3kotehokAGT());
	TaxonomyEvaluationItem kotehokN3D = new TaxonomyEvaluationItem("kotehokN3D", "N=3\\kotehok\\Problem-D", GCJGroundTruth.createGoogleCode2008N3kotehokDGT());
	TaxonomyEvaluationItem wataN3D = new TaxonomyEvaluationItem("wataN3D", "N=3\\wata\\Problem-D", GCJGroundTruth.createGoogleCode2008N3wataDGT());
	TaxonomyEvaluationItem ymatsuxN3A = new TaxonomyEvaluationItem("ymatsuxN3A", "N=3\\ymatsux\\Problem-A", GCJGroundTruth.createGoogleCode2008N3ymatsuxAGT());
	TaxonomyEvaluationItem ymatsuxN3C = new TaxonomyEvaluationItem("ymatsuxN3C", "N=3\\ymatsux\\Problem-C", GCJGroundTruth.createGoogleCode2008N3ymatsuxCGT());
	
	
	/**
	 * 
	 */
	public BatchEvaluator() {
		datasetBatch.add(halyavinN3D);
		datasetBatch.add(jediknightN3B);
		datasetBatch.add(kotehokN3A);
		datasetBatch.add(kotehokN3D);
		datasetBatch.add(wataN3D);
		datasetBatch.add(ymatsuxN3A);
		datasetBatch.add(ymatsuxN3C);
	}
	
	public void startEvaluation() {
		for (TaxonomyEvaluationItem aTaxonomyItem: this.datasetBatch) {
			TaxonomyCompareEngine engine = new TaxonomyCompareEngine();
			//1.  Get variants by parsing paths
			List<FileTreeElement> artifactFiles = parseVariantsInPath(aTaxonomyItem.getPathToVariants());
			List<Tree> artifacts = convertToTree(artifactFiles);
			//2.  Compute taxonomy
			if (artifacts.size() > 1) {
				// Create graph for artifacts
				engine.batchCompare(artifacts);
				ArtifactGraph artifactGraph = new ArtifactGraph(engine.artifactComparisonList);
				artifactGraph.deriveArtifactDetails(artifactFiles);
				artifactGraph.setUpRelationshipGraph(); // Set up relation graph for display
				artifactGraph.setUpTaxonomyGraph(); // Set up taxonomy graph for display
				
				//3. Compare Ground truth taxonomy and Computed taxonomy 
				TaxonomyEvaluator performanceJudge = new TaxonomyEvaluator(GCJGroundTruth.createProjectExampleGT(), artifactGraph.taxonomyRootNode);
				performanceJudge.computeDifferences();
				performanceJudge.calculateSecondaryMeasures();
				performanceJudge.printMeasures();
				performanceJudge.computeCustomMeasures();
				performanceJudge.printCustomMeasures();
			}
		}
	}
	
	public List<FileTreeElement> parseVariantsInPath(String pathToVariants) {

		// pathToVariants.
		List<FileTreeElement> foundVariants = new ArrayList<FileTreeElement>();

		// Open a folder
		FileTreeElement variantFolder = new Directory(datasetHomePath + datasetLocation + pathToVariants);
		
		
		DefaultTreeBuilder treeBuilder = new DefaultTreeBuilder(10);
		try {
			treeBuilder.buildTree(variantFolder);
		} catch (Exception ex) {
			System.out.println("Folder Structure could not be built: " + ex.getMessage());
		}
		
		// Read all Java Files in the folder
		if (variantFolder.exists()) {
			for (FileTreeElement sourceFileChild : variantFolder.getChildren()) {
				if ((!sourceFileChild.isDirectory()) && sourceFileChild.getExtension().equals(SUPPORTED_FILE_ENDINGS)) {
					System.out.println("File:" + sourceFileChild.getFileName());
					foundVariants.add(sourceFileChild);
				}
			}
		}

		// Parse Variants into FileElementTree

		return foundVariants;
	}
	
	public List<Tree> convertToTree(List<FileTreeElement> variants) {
		List<Tree> convertedTrees = new ArrayList<Tree>(); 
		ReaderManager readerManager = new ReaderManager();
		variants.stream().forEach(e -> convertedTrees.add(readerManager.readFile(e)));
		return convertedTrees;
	}


}
