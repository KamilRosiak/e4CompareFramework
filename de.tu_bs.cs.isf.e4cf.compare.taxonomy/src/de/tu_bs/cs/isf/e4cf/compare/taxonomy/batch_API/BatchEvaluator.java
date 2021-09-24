/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.batch_API;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.TaxonomyCompareEngine;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.graph.ArtifactGraph;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.util.TaxonomyEvaluator;
import de.tu_bs.cs.isf.e4cf.core.compare.parts.taxonomy_control_view.data_structures.TaxonomySettings;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.Directory;
import de.tu_bs.cs.isf.e4cf.core.file_structure.tree.DefaultTreeBuilder;

/**
 * @author developer-olan
 *
 */
public class BatchEvaluator {

	// public final static String[] SUPPORTED_FILE_ENDINGS = { "java", "h", "cpp" };
	public final static String SUPPORTED_FILE_ENDINGS = "java";
	private final static String datasetHomePath = "C:\\Users\\olant\\runtime-de.tu_bs.cs.isf.e4cf.core.product\\ 01 RAW\\";
	//private final static String groundTruthLocation = "GCJ\\2008-SP-SD-SL\\GT\\";
	private final static String datasetLocation = "GCJ\\2008-SP-SD-SL\\";
	
	private String exportFileName = "taxonomyResults" + LocalDate.now().toString() + "-"+ LocalTime.now().toString().replace(":", "-") +".csv";
	
	private List<TaxonomyEvaluationItem> datasetBatch = new ArrayList<TaxonomyEvaluationItem>(); 
	
	private List<BatchResult> batchResults = new ArrayList<BatchResult>(); 
	
	public List<FileTreeElement> artifactFileTrees = new ArrayList<FileTreeElement>();  
	
	public TaxonomySettings taxonomySettings = new TaxonomySettings();
	
	
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
		
		initializeTaxonomySettings();
	}
	
	public void startEvaluation() {
		for (TaxonomyEvaluationItem aTaxonomyItem: this.datasetBatch) {
			TaxonomyCompareEngine engine = new TaxonomyCompareEngine();
			engine.setTaxnomySettings(this.taxonomySettings);
			//1.  Get variants by parsing paths
			List<FileTreeElement> artifactFiles = parseVariantsInPath(aTaxonomyItem.getPathToVariants());
			List<Tree> artifacts = convertToTree(artifactFiles);
			//2.  Compute taxonomy
			if (artifacts.size() > 1) {
				// Create graph for artifacts
				engine.compare(artifacts);
				ArtifactGraph artifactGraph = new ArtifactGraph(engine.artifactComparisonList);
				artifactGraph.deriveArtifactDetails(artifactFiles);
				artifactGraph.setUpRelationshipGraph(); // Set up relation graph for display
				 
				// Set up taxonomy graph for display
				if (this.taxonomySettings.getTaxonomyTypeAsTree()) {
					artifactGraph.setUpTaxonomyGraph();
				} else {
					artifactGraph.setUpDAGTaxonomyGraph();
				}
				
				
				//3. Compare Ground truth taxonomy and Computed taxonomy 
				TaxonomyEvaluator performanceJudge = new TaxonomyEvaluator(aTaxonomyItem.getGroundTruth(), artifactGraph.taxonomyRootNode);
				//performanceJudge.computePrimaryMeasures();
				performanceJudge.computePrimaryMeasuresPS();
				performanceJudge.calculateSecondaryMeasures();
				performanceJudge.printMeasures();
				performanceJudge.computeCustomMeasures();
				performanceJudge.printCustomMeasures();
				
				BatchResult newBatch = new BatchResult("New Metric Definition", aTaxonomyItem.getItemName(), aTaxonomyItem.getPathToVariants(), performanceJudge.getRecallValue(), performanceJudge.getPrecisionValue(), performanceJudge.getAccuracyValue(), performanceJudge.getErrorRateValue());
				batchResults.add(newBatch);
				try {
				exportResults();
				} catch (Exception ex) {
					
				}

				performanceJudge.computePrimaryMeasures();
				performanceJudge.calculateSecondaryMeasures();
				
				newBatch = new BatchResult("Former Metric Definition", aTaxonomyItem.getItemName(), aTaxonomyItem.getPathToVariants(), performanceJudge.getRecallValue(), performanceJudge.getPrecisionValue(), performanceJudge.getAccuracyValue(), performanceJudge.getErrorRateValue());
				batchResults.add(newBatch);
			}
		}
	}
	
	public void exportResults() throws IOException {
		String fileName = "C:\\Users\\olant\\Desktop\\thesis-playground\\Results\\"+exportFileName;
	    FileWriter fileWriter = new FileWriter(fileName);
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    printWriter.println("Mode,ItemName,PathToVariants,Recall,Precision,Accuracy,ErrorRate");
	    
		for (BatchResult aBatchResult: this.batchResults) {
			printWriter.println(aBatchResult.getPropertyAsCSVString());
		}
		
		printWriter.close();
	}
	
	public void initializeTaxonomySettings() {
		this.taxonomySettings.setLevenshteinMode(true);
		this.taxonomySettings.setSourceLevelComparison(false);
		this.taxonomySettings.setTaxonomyTypeAsTree(false);
		
		if (!this.taxonomySettings.getSourceLevelComparison()) {
			this.taxonomySettings.setDirSizeMetric(true);
		}
	}
	
	public List<FileTreeElement> parseVariantsInPath(String pathToVariants) {

		// pathToVariants.
		List<FileTreeElement> foundVariants = new ArrayList<FileTreeElement>();

		// Open a folder
		FileTreeElement variantFolder = new Directory(datasetHomePath + datasetLocation + pathToVariants);
		
		
		DefaultTreeBuilder treeBuilder = new DefaultTreeBuilder(3);
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
	
	public static void main(String args) {
		BatchEvaluator batchEvaluator = new BatchEvaluator();
		batchEvaluator.startEvaluation();
	}


}
