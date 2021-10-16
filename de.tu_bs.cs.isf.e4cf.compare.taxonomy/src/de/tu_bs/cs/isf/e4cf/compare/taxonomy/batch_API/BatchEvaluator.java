/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.batch_API;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
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
 * Class helps for Batch evaluation of Data sets for Taxonomy mining
 * @author developer-olan
 *
 */
public class BatchEvaluator {

	// public final static String[] SUPPORTED_FILE_ENDINGS = { "java", "h", "cpp" };
	public final static String SUPPORTED_FILE_ENDINGS = "java";
	private final static String datasetHomePath = "C:\\Users\\olant\\runtime-de.tu_bs.cs.isf.e4cf.core.product\\ 01 RAW\\";
	private final static String gcjStrategy1DatasetLocation = "GCJ\\2008-SP-SD-SL\\";
	private final static String gcjStrategy2DatasetLocation = "GCJ\\2008-SP-DD-SL\\";
	private final static String gcjStrategy3DatasetLocation = "GCJ\\2008-SP-DD-DL\\";
	private final static String directoryDatasetLocation = "Branches\\";


	
	private String exportFileName = "taxonomyResults" + LocalDate.now().toString() + "-"+ LocalTime.now().toString().replace(":", "-") +".csv";
	
	private List<TaxonomyEvaluationItem> datasetBatch = new ArrayList<TaxonomyEvaluationItem>(); 
	
	private List<BatchResult> batchResults = new ArrayList<BatchResult>(); 
	
	public List<FileTreeElement> artifactFileTrees = new ArrayList<FileTreeElement>();  
	
	public TaxonomySettings taxonomySettings = new TaxonomySettings();
	
	
	/**
	 * Constructor
	 */
	public BatchEvaluator() {
		initializeTaxonomySettings();
	}
	
	/**
	 * Starts Evaluation
	 */
	public void startEvaluation() {

	}
	
	public void exportResults() throws IOException {
		String fileName = "C:\\Users\\olant\\Desktop\\thesis-playground\\Results\\"+exportFileName;
	    FileWriter fileWriter = new FileWriter(fileName);
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    printWriter.println("Mode,ItemName,PathToVariants,Recall,Precision,Accuracy,ErrorRate,PCRA,NDM,RVPA");
	    
		for (BatchResult aBatchResult: this.batchResults) {
			printWriter.println(aBatchResult.getPropertyAsCSVString());
		}
		
		printWriter.close();
	}
	
	public void initializeTaxonomySettings() {
		this.taxonomySettings.setLevenshteinMode(true);
		this.taxonomySettings.setSourceLevelComparison(false);
		this.taxonomySettings.setTaxonomyTypeAsTree(true);
		
		if (!this.taxonomySettings.getSourceLevelComparison()) {
			this.taxonomySettings.setDirSizeMetric(true);
		}
	}
	
	public List<FileTreeElement> parseSourceVariantsInPath(String pathToVariants, String strategy) 
	{
		// Initialize List of found variants
		List<FileTreeElement> foundVariants = new ArrayList<FileTreeElement>();
		// Open a folder
		FileTreeElement variantFolder = null;
		
		if (strategy.equals("1")) { 
			variantFolder = new Directory(datasetHomePath + gcjStrategy1DatasetLocation + pathToVariants);
		} else if (strategy.equals("2")) {
			variantFolder = new Directory(datasetHomePath + gcjStrategy2DatasetLocation + pathToVariants);
		} else if (strategy.equals("3")) {
			variantFolder = new Directory(datasetHomePath + gcjStrategy3DatasetLocation + pathToVariants);
		}
		
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
		return foundVariants;
	}
	
	public List<FileTreeElement> parseDirectoryVariantsInPath(String pathToVariants) 
	{
		// pathToVariants.
		List<FileTreeElement> foundVariants = new ArrayList<FileTreeElement>();
		// Open a folder
		FileTreeElement variantFolder = new Directory(datasetHomePath + directoryDatasetLocation + pathToVariants);
		
		DefaultTreeBuilder treeBuilder = new DefaultTreeBuilder(3);
		try {
			treeBuilder.buildTree(variantFolder);
		} catch (Exception ex) {
			System.out.println("Folder Structure could not be built: " + ex.getMessage());
		}
		
		// Read all Folders in the folder
		if (variantFolder.exists()) {
			for (FileTreeElement sourceFolderChild : variantFolder.getChildren()) {
				if (sourceFolderChild.isDirectory()) {
					System.out.println("Folder:" + sourceFolderChild.getFileName());
					foundVariants.add(sourceFolderChild);
				}
			}
		}
		return foundVariants;
	}
	
	public List<Tree> convertToTree(List<FileTreeElement> variants) {
		List<Tree> convertedTrees = new ArrayList<Tree>(); 
		ReaderManager readerManager = new ReaderManager();
		variants.stream().forEach(e -> convertedTrees.add(readerManager.readFile(e)));
		return convertedTrees;
	}
	
	/**
	 * Starts Same Developer Source Evaluation (Strategy 1)
	 */
	public void startSameDeveloperEvaluation() {
		// Clear previously entered Data
		this.datasetBatch.clear();
		// Add data to Batch before evaluation
		addGCJSourceVariantsSameDeveloperN3();	// Add GCJ Variants for N = 3
		addGCJSourceVariantsSameDeveloperN4();	// Add GCJ Variants for N = 4
		addGCJSourceVariantsSameDeveloperN5();	// Add GCJ Variants for N = 5
		
		for (TaxonomyEvaluationItem aTaxonomyItem: this.datasetBatch) {
			TaxonomyCompareEngine engine = new TaxonomyCompareEngine();
			engine.setTaxnomySettings(this.taxonomySettings);
			//1.  Get variants by parsing paths
			List<FileTreeElement> artifactFiles = parseSourceVariantsInPath(aTaxonomyItem.getPathToVariants(), "1");
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
				
				BatchResult newBatch = new BatchResult("New Metric Definition", aTaxonomyItem.getItemName(), aTaxonomyItem.getPathToVariants(), performanceJudge.getRecallValue(), performanceJudge.getPrecisionValue(), performanceJudge.getAccuracyValue(), performanceJudge.getErrorRateValue(), performanceJudge.getPCRA(), performanceJudge.getNDM());
				batchResults.add(newBatch);
				try {
				exportResults();
				} catch (Exception ex) {
				 System.out.println("Could not print ");	
				}
			}
		}
	}
	
	/**
	 * Starts Different Developers Source Evaluation (Strategy 2)
	 */
	public void startDifferentDevelopersEvaluation() {
		// Clear previously entered Data
		this.datasetBatch.clear();
		// Add data to Batch before evaluation
		addGCJSourceVariantsDifferentDevelopersN3();	// Add GCJ Variants for N = 3
		addGCJSourceVariantsDifferentDevelopersN4();	// Add GCJ Variants for N = 4
		addGCJSourceVariantsDifferentDevelopersN5();	// Add GCJ Variants for N = 5
		addGCJSourceVariantsDifferentDevelopersN6();	// Add GCJ Variants for N = 6
		
		for (TaxonomyEvaluationItem aTaxonomyItem: this.datasetBatch) {
			TaxonomyCompareEngine engine = new TaxonomyCompareEngine();
			engine.setTaxnomySettings(this.taxonomySettings);
			//1.  Get variants by parsing paths
			List<FileTreeElement> artifactFiles = parseSourceVariantsInPath(aTaxonomyItem.getPathToVariants(), "2");
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
				
				BatchResult newBatch = new BatchResult("New Metric Definition", aTaxonomyItem.getItemName(), aTaxonomyItem.getPathToVariants(), performanceJudge.getRecallValue(), performanceJudge.getPrecisionValue(), performanceJudge.getAccuracyValue(), performanceJudge.getErrorRateValue(), performanceJudge.getPCRA(), performanceJudge.getNDM());
				batchResults.add(newBatch);
				try {
				exportResults();
				} catch (Exception ex) {
				 System.out.println("Could not print ");	
				}
			}
		}
	}
	
	/**
	 * Starts RunningExampleDirectory Evaluation
	 */
	public void startGITJavapoetEvaluation() {
		// Clear previously entered Data
		this.datasetBatch.clear();
		// Add data to Batch before evaluation
		addGITJavapoetN6Time();	// Add GIT Javapoet Variants for N = 6
		
		for (TaxonomyEvaluationItem aTaxonomyItem: this.datasetBatch) {
			TaxonomyCompareEngine engine = new TaxonomyCompareEngine();
			engine.setTaxnomySettings(this.taxonomySettings);
			//1.  Get variants by parsing paths
			List<FileTreeElement> artifactFolders = parseDirectoryVariantsInPath(aTaxonomyItem.getPathToVariants());
			List<Tree> artifacts = convertToTree(artifactFolders);
			//2.  Compute taxonomy
			if (artifacts.size() > 1) {
				// Create graph for artifacts
				engine.compare(artifacts);
				ArtifactGraph artifactGraph = new ArtifactGraph(engine.artifactComparisonList);
				artifactGraph.deriveArtifactDetails(artifactFolders);
				artifactGraph.setUpRelationshipGraph(); // Set up relation graph for display
				 
				// Set up taxonomy graph for display
				if (this.taxonomySettings.getTaxonomyTypeAsTree()) {
					artifactGraph.setUpTaxonomyGraph();
				} else {
					artifactGraph.setUpDAGTaxonomyGraph();
				}
				
				
				//3. Compare Ground truth taxonomy and Computed taxonomy 
				TaxonomyEvaluator performanceJudge = new TaxonomyEvaluator(aTaxonomyItem.getGroundTruth(), artifactGraph.taxonomyRootNode);
				performanceJudge.computePrimaryMeasures();
				//performanceJudge.computePrimaryMeasuresPS();
				performanceJudge.calculateSecondaryMeasures();
				performanceJudge.printMeasures();
				performanceJudge.computeCustomMeasures();
				performanceJudge.printCustomMeasures();
				
				BatchResult newBatch = new BatchResult("New Metric Definition", aTaxonomyItem.getItemName(), aTaxonomyItem.getPathToVariants(), performanceJudge.getRecallValue(), performanceJudge.getPrecisionValue(), performanceJudge.getAccuracyValue(), performanceJudge.getErrorRateValue(), performanceJudge.getPCRA(), performanceJudge.getNDM());
				batchResults.add(newBatch);
				
				try {
					exportResults();
				} catch (Exception ex) {
					System.out.println("Could not Export Results: "+ex.getMessage());	
				}
			}
		}
	}
	
	/**
	 * Starts RunningExampleDirectory Evaluation
	 */
	public void startRunningExampleDirectoryEvaluation() {
		// Clear previously entered Data
		this.datasetBatch.clear();
		// Add data to Batch before evaluation
		addGitDirectoryRunningExampleVariants();	// Add GCJ Variants for N = 3
		
		for (TaxonomyEvaluationItem aTaxonomyItem: this.datasetBatch) {
			TaxonomyCompareEngine engine = new TaxonomyCompareEngine();
			engine.setTaxnomySettings(this.taxonomySettings);
			//1.  Get variants by parsing paths
			List<FileTreeElement> artifactFolders = parseDirectoryVariantsInPath(aTaxonomyItem.getPathToVariants());
			List<Tree> artifacts = convertToTree(artifactFolders);
			//2.  Compute taxonomy
			if (artifacts.size() > 1) {
				// Create graph for artifacts
				engine.compare(artifacts);
				ArtifactGraph artifactGraph = new ArtifactGraph(engine.artifactComparisonList);
				artifactGraph.deriveArtifactDetails(artifactFolders);
				artifactGraph.setUpRelationshipGraph(); // Set up relation graph for display
				 
				// Set up taxonomy graph for display
				if (this.taxonomySettings.getTaxonomyTypeAsTree()) {
					artifactGraph.setUpTaxonomyGraph();
				} else {
					artifactGraph.setUpDAGTaxonomyGraph();
				}
				
				
				//3. Compare Ground truth taxonomy and Computed taxonomy 
				TaxonomyEvaluator performanceJudge = new TaxonomyEvaluator(aTaxonomyItem.getGroundTruth(), artifactGraph.taxonomyRootNode);
				performanceJudge.computePrimaryMeasures();
				//performanceJudge.computePrimaryMeasuresPS();
				performanceJudge.calculateSecondaryMeasures();
				performanceJudge.printMeasures();
				performanceJudge.computeCustomMeasures();
				performanceJudge.printCustomMeasures();
				
				BatchResult newBatch = new BatchResult("New Metric Definition", aTaxonomyItem.getItemName(), aTaxonomyItem.getPathToVariants(), performanceJudge.getRecallValue(), performanceJudge.getPrecisionValue(), performanceJudge.getAccuracyValue(), performanceJudge.getErrorRateValue(), performanceJudge.getPCRA(), performanceJudge.getNDM());
				batchResults.add(newBatch);
				
				try {
					exportResults();
				} catch (Exception ex) {
					System.out.println("Could not Export Results: "+ex.getMessage());	
				}
			}
		}
	}
	
	
	/* Data Set for Source Strategy 1- SPDSSDSL (Different Developers)*/
	/**
	 * Create GCJ Evaluation Items  and Add to Batch
	 * SPDSSDSL - Same Problem class, Different Stages, Same Developer, Same Language
	 * Number of Variants, N = 3
	 */
	public void addGCJSourceVariantsSameDeveloperN3() {
		// Mid N=3
		TaxonomyEvaluationItem halyavinN3D = new TaxonomyEvaluationItem("halyavinN3D", "N=3\\halyavin\\Problem-D", GCJGroundTruth.createGoogleCode2008N3halyavinDGT());
		TaxonomyEvaluationItem jediknightN3B = new TaxonomyEvaluationItem("jediknightN3B", "N=3\\jediknight\\Problem-B", GCJGroundTruth.createGoogleCode2008N3jediknightBGT());
		TaxonomyEvaluationItem kotehokN3A = new TaxonomyEvaluationItem("kotehokN3A", "N=3\\kotehok\\Problem-A", GCJGroundTruth.createGoogleCode2008N3kotehokAGT());
		TaxonomyEvaluationItem kotehokN3D = new TaxonomyEvaluationItem("kotehokN3D", "N=3\\kotehok\\Problem-D", GCJGroundTruth.createGoogleCode2008N3kotehokDGT());
		TaxonomyEvaluationItem wataN3D = new TaxonomyEvaluationItem("wataN3D", "N=3\\wata\\Problem-D", GCJGroundTruth.createGoogleCode2008N3wataDGT());
		TaxonomyEvaluationItem ymatsuxN3A = new TaxonomyEvaluationItem("ymatsuxN3A", "N=3\\ymatsux\\Problem-A", GCJGroundTruth.createGoogleCode2008N3ymatsuxAGT());
		TaxonomyEvaluationItem ymatsuxN3C = new TaxonomyEvaluationItem("ymatsuxN3C", "N=3\\ymatsux\\Problem-C", GCJGroundTruth.createGoogleCode2008N3ymatsuxCGT());
		
		datasetBatch.add(halyavinN3D);
		datasetBatch.add(jediknightN3B);
		datasetBatch.add(kotehokN3A);
		datasetBatch.add(kotehokN3D);
		datasetBatch.add(wataN3D);
		datasetBatch.add(ymatsuxN3A);
		datasetBatch.add(ymatsuxN3C);
	}
	
	/**
	 * Create GCJ Evaluation Items  and Add to Batch
	 * SPDSSDSL - Same Problem class, Different Stages, Same Developer, Same Language
	 * Number of Variants, N = 4
	 */
	public void addGCJSourceVariantsSameDeveloperN4() {
		// Mid N=4
		TaxonomyEvaluationItem halyavinN4B = new TaxonomyEvaluationItem("halyavinN4B", "N=4\\halyavin\\Problem-B", GCJGroundTruth.createGoogleCode2008N4halyavinBGT());
		TaxonomyEvaluationItem halyavinN4C = new TaxonomyEvaluationItem("halyavinN4C", "N=4\\halyavin\\Problem-C", GCJGroundTruth.createGoogleCode2008N4halyavinCGT());
		TaxonomyEvaluationItem kotehokN4B = new TaxonomyEvaluationItem("kotehokN4B", "N=4\\kotehok\\Problem-B", GCJGroundTruth.createGoogleCode2008N4kotehokBGT());
		TaxonomyEvaluationItem kotehokN4C = new TaxonomyEvaluationItem("kotehokN4C", "N=4\\kotehok\\Problem-C", GCJGroundTruth.createGoogleCode2008N4kotehokCGT());
		TaxonomyEvaluationItem mysticN4B = new TaxonomyEvaluationItem("mysticN4B", "N=4\\mystic\\Problem-B", GCJGroundTruth.createGoogleCode2008N4mysticBGT());
		TaxonomyEvaluationItem mysticN4D = new TaxonomyEvaluationItem("mysticN4D", "N=4\\mystic\\Problem-D", GCJGroundTruth.createGoogleCode2008N4mysticDGT());
		TaxonomyEvaluationItem wataN4A = new TaxonomyEvaluationItem("wataN4A", "N=4\\wata\\Problem-A", GCJGroundTruth.createGoogleCode2008N4wataAGT());	
		TaxonomyEvaluationItem wataN4B = new TaxonomyEvaluationItem("wataN4B", "N=4\\wata\\Problem-B", GCJGroundTruth.createGoogleCode2008N4wataBGT());	
		TaxonomyEvaluationItem wataN4C = new TaxonomyEvaluationItem("wataN4C", "N=4\\wata\\Problem-C", GCJGroundTruth.createGoogleCode2008N4wataCGT());	
		
		datasetBatch.add(halyavinN4B);
		datasetBatch.add(halyavinN4C);
		datasetBatch.add(kotehokN4B);
		datasetBatch.add(kotehokN4C);
		datasetBatch.add(mysticN4B);
		datasetBatch.add(mysticN4D);
		datasetBatch.add(wataN4A);
		datasetBatch.add(wataN4B);
		datasetBatch.add(wataN4C);

	}
	
	/**
	 * Create GCJ Evaluation Items  and Add to Batch
	 * SPDSSDSL - Same Problem class, Different Stages, Same Developer, Same Language
	 * Number of Variants, N = 5
	 */
	public void addGCJSourceVariantsSameDeveloperN5() {
		// Mid N=5
		TaxonomyEvaluationItem halyavinN5A = new TaxonomyEvaluationItem("halyavinN5A", "N=5\\halyavin\\Problem-A", GCJGroundTruth.createGoogleCode2008N5halyavinAGT());
		TaxonomyEvaluationItem mysticN5A = new TaxonomyEvaluationItem("mysticN5A", "N=5\\mystic\\Problem-A", GCJGroundTruth.createGoogleCode2008N5mysticAGT());
		TaxonomyEvaluationItem mysticN5C = new TaxonomyEvaluationItem("mysticN5C", "N=5\\mystic\\Problem-C", GCJGroundTruth.createGoogleCode2008N5mysticCGT());
		
		datasetBatch.add(halyavinN5A);
		datasetBatch.add(mysticN5A);
		datasetBatch.add(mysticN5C);

	}
	
	/* Data Set for Source Strategy 2- SPDSDDSL (Different Developers)*/
	/**
	 * Create GCJ Evaluation Items  and Add to Batch
	 * SPDSDDSL - Same Problem class, Different Stages, Different Developers, Same Language
	 * Number of Variants, N = 3
	 */
	public void addGCJSourceVariantsDifferentDevelopersN3() {
		// Mid N=3, ProblemA
		TaxonomyEvaluationItem set1N3A = new TaxonomyEvaluationItem("set1N3A", "Several", "A", "N=3", "N=3\\Problem-A\\Set-1", GCJGroundTruth.createGoogleCode2008N3Set1AGT());
		TaxonomyEvaluationItem set2N3A = new TaxonomyEvaluationItem("set2N3A", "Several", "A", "N=3", "N=3\\Problem-A\\Set-2", GCJGroundTruth.createGoogleCode2008N3Set2AGT());
		TaxonomyEvaluationItem set3N3A = new TaxonomyEvaluationItem("set3N3A", "Several", "A", "N=3", "N=3\\Problem-A\\Set-3", GCJGroundTruth.createGoogleCode2008N3Set3AGT());
		TaxonomyEvaluationItem set4N3A = new TaxonomyEvaluationItem("set4N3A", "Several", "A", "N=3", "N=3\\Problem-A\\Set-4", GCJGroundTruth.createGoogleCode2008N3Set4AGT());
//		TaxonomyEvaluationItem set5N3A = new TaxonomyEvaluationItem("set5N3A", "Several", "A", "N=3", "N=3\\Problem-A\\Set-5", GCJGroundTruth.createGoogleCode2008N3Set5AGT());
//		TaxonomyEvaluationItem set6N3A = new TaxonomyEvaluationItem("set6N3A", "Several", "A", "N=3", "N=3\\Problem-A\\Set-6", GCJGroundTruth.createGoogleCode2008N3Set6AGT());
//		TaxonomyEvaluationItem set7N3A = new TaxonomyEvaluationItem("set7N3A", "Several", "A", "N=3", "N=3\\Problem-A\\Set-7", GCJGroundTruth.createGoogleCode2008N3Set7AGT());
//		TaxonomyEvaluationItem set8N3A = new TaxonomyEvaluationItem("set8N3A", "Several", "A", "N=3", "N=3\\Problem-A\\Set-8", GCJGroundTruth.createGoogleCode2008N3Set8AGT());
//		
		// Add Problem-A Sets
		datasetBatch.add(set1N3A);
		datasetBatch.add(set2N3A);
		datasetBatch.add(set3N3A);
		datasetBatch.add(set4N3A);
//		datasetBatch.add(set5N3A);
//		datasetBatch.add(set6N3A);
//		datasetBatch.add(set7N3A);
//		datasetBatch.add(set8N3A);
		
		// Mid N=3, Problem-B
		TaxonomyEvaluationItem set1N3B = new TaxonomyEvaluationItem("set1N3B", "Several", "B", "N=3", "N=3\\Problem-B\\Set-1", GCJGroundTruth.createGoogleCode2008N3Set1BGT());
		TaxonomyEvaluationItem set2N3B = new TaxonomyEvaluationItem("set2N3B", "Several", "B", "N=3", "N=3\\Problem-B\\Set-2", GCJGroundTruth.createGoogleCode2008N3Set2BGT());
		TaxonomyEvaluationItem set3N3B = new TaxonomyEvaluationItem("set3N3B", "Several", "B", "N=3", "N=3\\Problem-B\\Set-3", GCJGroundTruth.createGoogleCode2008N3Set3BGT());
		TaxonomyEvaluationItem set4N3B = new TaxonomyEvaluationItem("set4N3B", "Several", "B", "N=3", "N=3\\Problem-B\\Set-4", GCJGroundTruth.createGoogleCode2008N3Set4BGT());
//		TaxonomyEvaluationItem set5N3B = new TaxonomyEvaluationItem("set5N3B", "Several", "B", "N=3", "N=3\\Problem-B\\Set-5", GCJGroundTruth.createGoogleCode2008N3Set5BGT());
//		TaxonomyEvaluationItem set6N3B = new TaxonomyEvaluationItem("set6N3B", "Several", "B", "N=3", "N=3\\Problem-B\\Set-6", GCJGroundTruth.createGoogleCode2008N3Set6BGT());
//		TaxonomyEvaluationItem set7N3B = new TaxonomyEvaluationItem("set7N3B", "Several", "B", "N=3", "N=3\\Problem-B\\Set-7", GCJGroundTruth.createGoogleCode2008N3Set7BGT());
//		TaxonomyEvaluationItem set8N3B = new TaxonomyEvaluationItem("set8N3B", "Several", "B", "N=3", "N=3\\Problem-B\\Set-8", GCJGroundTruth.createGoogleCode2008N3Set8BGT());
		
		// Add Problem-Bs Sets
		datasetBatch.add(set1N3B);
		datasetBatch.add(set2N3B);
		datasetBatch.add(set3N3B);
		datasetBatch.add(set4N3B);
//		datasetBatch.add(set5N3B);
//		datasetBatch.add(set6N3B);
//		datasetBatch.add(set7N3B);
//		datasetBatch.add(set8N3B);
	}
	
	/**
	 * Create GCJ Evaluation Items  and Add to Batch
	 * SPDSDDSL - Same Problem class, Different Stages, Different Developers, Same Language
	 * Number of Variants, N = 4
	 */
	public void addGCJSourceVariantsDifferentDevelopersN4() {
		// Mid N=4, Problem-A
		TaxonomyEvaluationItem set1N4A = new TaxonomyEvaluationItem("set1N4A", "Several", "A", "N=4", "N=4\\Problem-A\\Set-1", GCJGroundTruth.createGoogleCode2008N4Set1AGT());
		TaxonomyEvaluationItem set2N4A = new TaxonomyEvaluationItem("set2N4A", "Several", "A", "N=4", "N=4\\Problem-A\\Set-2", GCJGroundTruth.createGoogleCode2008N4Set2AGT());
		TaxonomyEvaluationItem set3N4A = new TaxonomyEvaluationItem("set3N4A", "Several", "A", "N=4", "N=4\\Problem-A\\Set-3", GCJGroundTruth.createGoogleCode2008N4Set3AGT());
		TaxonomyEvaluationItem set4N4A = new TaxonomyEvaluationItem("set4N4A", "Several", "A", "N=4", "N=4\\Problem-A\\Set-4", GCJGroundTruth.createGoogleCode2008N4Set4AGT());
//		TaxonomyEvaluationItem set5N4A = new TaxonomyEvaluationItem("set5N4A", "Several", "A", "N=4", "N=4\\Problem-A\\Set-5", GCJGroundTruth.createGoogleCode2008N4Set5AGT());
//		TaxonomyEvaluationItem set6N4A = new TaxonomyEvaluationItem("set6N4A", "Several", "A", "N=4", "N=4\\Problem-A\\Set-6", GCJGroundTruth.createGoogleCode2008N4Set6AGT());
//		TaxonomyEvaluationItem set7N4A = new TaxonomyEvaluationItem("set7N4A", "Several", "A", "N=4", "N=4\\Problem-A\\Set-7", GCJGroundTruth.createGoogleCode2008N4Set7AGT());
//		TaxonomyEvaluationItem set8N4A = new TaxonomyEvaluationItem("set8N4A", "Several", "A", "N=4", "N=4\\Problem-A\\Set-8", GCJGroundTruth.createGoogleCode2008N4Set8AGT());
//		
		// Add Problem-A Sets
		datasetBatch.add(set1N4A);
		datasetBatch.add(set2N4A);
		datasetBatch.add(set3N4A);
		datasetBatch.add(set4N4A);
//		datasetBatch.add(set5N4A);
//		datasetBatch.add(set6N4A);
//		datasetBatch.add(set7N4A);
//		datasetBatch.add(set8N4A);
		
		// Mid N=4, Problem-B
		TaxonomyEvaluationItem set1N4B = new TaxonomyEvaluationItem("set1N4B", "Several", "B", "N=4", "N=4\\Problem-B\\Set-1", GCJGroundTruth.createGoogleCode2008N4Set1BGT());
		TaxonomyEvaluationItem set2N4B = new TaxonomyEvaluationItem("set2N4B", "Several", "B", "N=4", "N=4\\Problem-B\\Set-2", GCJGroundTruth.createGoogleCode2008N4Set2BGT());
		TaxonomyEvaluationItem set3N4B = new TaxonomyEvaluationItem("set3N4B", "Several", "B", "N=4", "N=4\\Problem-B\\Set-3", GCJGroundTruth.createGoogleCode2008N4Set3BGT());
		TaxonomyEvaluationItem set4N4B = new TaxonomyEvaluationItem("set4N4B", "Several", "B", "N=4", "N=4\\Problem-B\\Set-4", GCJGroundTruth.createGoogleCode2008N4Set4BGT());
//		TaxonomyEvaluationItem set5N4B = new TaxonomyEvaluationItem("set5N4B", "Several", "B", "N=4", "N=4\\Problem-B\\Set-5", GCJGroundTruth.createGoogleCode2008N4Set5BGT());
//		TaxonomyEvaluationItem set6N4B = new TaxonomyEvaluationItem("set6N4B", "Several", "B", "N=4", "N=4\\Problem-B\\Set-6", GCJGroundTruth.createGoogleCode2008N4Set6BGT());
//		TaxonomyEvaluationItem set7N4B = new TaxonomyEvaluationItem("set7N4B", "Several", "B", "N=4", "N=4\\Problem-B\\Set-7", GCJGroundTruth.createGoogleCode2008N4Set7BGT());
//		TaxonomyEvaluationItem set8N4B = new TaxonomyEvaluationItem("set8N4B", "Several", "B", "N=4", "N=4\\Problem-B\\Set-8", GCJGroundTruth.createGoogleCode2008N4Set8BGT());
		
		// Add Problem-B Sets
		datasetBatch.add(set1N4B);
		datasetBatch.add(set2N4B);
		datasetBatch.add(set3N4B);
		datasetBatch.add(set4N4B);
//		datasetBatch.add(set5N4B);
//		datasetBatch.add(set6N4B);
//		datasetBatch.add(set7N4B);
//		datasetBatch.add(set8N4B);
	}
	
	/**
	 * Create GCJ Evaluation Items  and Add to Batch
	 * SPDSDDSL - Same Problem class, Different Stages, Different Developers, Same Language
	 * Number of Variants, N = 5
	 */
	public void addGCJSourceVariantsDifferentDevelopersN5() {
		// Mid N=5, ProblemA
		TaxonomyEvaluationItem set1N5A = new TaxonomyEvaluationItem("set1N5A", "Several", "A", "N=5", "N=5\\Problem-A\\Set-1", GCJGroundTruth.createGoogleCode2008N5Set1AGT());
		TaxonomyEvaluationItem set2N5A = new TaxonomyEvaluationItem("set2N5A", "Several", "A", "N=5", "N=5\\Problem-A\\Set-2", GCJGroundTruth.createGoogleCode2008N5Set2AGT());
		TaxonomyEvaluationItem set3N5A = new TaxonomyEvaluationItem("set3N5A", "Several", "A", "N=5", "N=5\\Problem-A\\Set-3", GCJGroundTruth.createGoogleCode2008N5Set3AGT());
		TaxonomyEvaluationItem set4N5A = new TaxonomyEvaluationItem("set4N5A", "Several", "A", "N=5", "N=5\\Problem-A\\Set-4", GCJGroundTruth.createGoogleCode2008N5Set4AGT());
//		TaxonomyEvaluationItem set5N5A = new TaxonomyEvaluationItem("set5N5A", "Several", "A", "N=5", "N=5\\Problem-A\\Set-5", GCJGroundTruth.createGoogleCode2008N5Set5AGT());
//		TaxonomyEvaluationItem set6N5A = new TaxonomyEvaluationItem("set6N5A", "Several", "A", "N=5", "N=5\\Problem-A\\Set-6", GCJGroundTruth.createGoogleCode2008N5Set6AGT());
//		TaxonomyEvaluationItem set7N5A = new TaxonomyEvaluationItem("set7N5A", "Several", "A", "N=5", "N=5\\Problem-A\\Set-7", GCJGroundTruth.createGoogleCode2008N5Set7AGT());
//		TaxonomyEvaluationItem set8N5A = new TaxonomyEvaluationItem("set8N5A", "Several", "A", "N=5", "N=5\\Problem-A\\Set-8", GCJGroundTruth.createGoogleCode2008N5Set8AGT());
//		
		// Add Problem-A Sets
		datasetBatch.add(set1N5A);
		datasetBatch.add(set2N5A);
		datasetBatch.add(set3N5A);
		datasetBatch.add(set4N5A);
//		datasetBatch.add(set5N5A);
//		datasetBatch.add(set6N5A);
//		datasetBatch.add(set7N5A);
//		datasetBatch.add(set8N5A);
//		
		// Mid N=5, Problem-B
		TaxonomyEvaluationItem set1N5B = new TaxonomyEvaluationItem("set1N5B", "Several", "B", "N=5", "N=5\\Problem-B\\Set-1", GCJGroundTruth.createGoogleCode2008N5Set1BGT());
		TaxonomyEvaluationItem set2N5B = new TaxonomyEvaluationItem("set2N5B", "Several", "B", "N=5", "N=5\\Problem-B\\Set-2", GCJGroundTruth.createGoogleCode2008N5Set2BGT());
		TaxonomyEvaluationItem set3N5B = new TaxonomyEvaluationItem("set3N5B", "Several", "B", "N=5", "N=5\\Problem-B\\Set-3", GCJGroundTruth.createGoogleCode2008N5Set3BGT());
		TaxonomyEvaluationItem set4N5B = new TaxonomyEvaluationItem("set4N5B", "Several", "B", "N=5", "N=5\\Problem-B\\Set-4", GCJGroundTruth.createGoogleCode2008N5Set4BGT());
//		TaxonomyEvaluationItem set5N5B = new TaxonomyEvaluationItem("set5N5B", "Several", "B", "N=5", "N=5\\Problem-B\\Set-5", GCJGroundTruth.createGoogleCode2008N5Set5BGT());
//		TaxonomyEvaluationItem set6N5B = new TaxonomyEvaluationItem("set6N5B", "Several", "B", "N=5", "N=5\\Problem-B\\Set-6", GCJGroundTruth.createGoogleCode2008N5Set6BGT());
//		TaxonomyEvaluationItem set7N5B = new TaxonomyEvaluationItem("set7N5B", "Several", "B", "N=5", "N=5\\Problem-B\\Set-7", GCJGroundTruth.createGoogleCode2008N5Set7BGT());
//		TaxonomyEvaluationItem set8N5B = new TaxonomyEvaluationItem("set8N5B", "Several", "B", "N=5", "N=5\\Problem-B\\Set-8", GCJGroundTruth.createGoogleCode2008N5Set8BGT());
//		
		// Add Problem-B Sets
		datasetBatch.add(set1N5B);
		datasetBatch.add(set2N5B);
		datasetBatch.add(set3N5B);
		datasetBatch.add(set4N5B);
//		datasetBatch.add(set5N5B);
//		datasetBatch.add(set6N5B);
//		datasetBatch.add(set7N5B);
//		datasetBatch.add(set8N5B);
	}
	
	/**
	 * Create GCJ Evaluation Items  and Add to Batch
	 * SPDSDDSL - Same Problem class, Different Stages, Different Developers, Same Language
	 * Number of Variants, N = 6
	 */
	public void addGCJSourceVariantsDifferentDevelopersN6() {
		// Mid N=6, Problem-A
		TaxonomyEvaluationItem set1N6A = new TaxonomyEvaluationItem("set1N6A", "Several", "A", "N=6", "N=6\\Problem-A\\Set-1", GCJGroundTruth.createGoogleCode2008N6Set1AGT());
		TaxonomyEvaluationItem set2N6A = new TaxonomyEvaluationItem("set2N6A", "Several", "A", "N=6", "N=6\\Problem-A\\Set-2", GCJGroundTruth.createGoogleCode2008N6Set2AGT());
		TaxonomyEvaluationItem set3N6A = new TaxonomyEvaluationItem("set3N6A", "Several", "A", "N=6", "N=6\\Problem-A\\Set-3", GCJGroundTruth.createGoogleCode2008N6Set3AGT());
		TaxonomyEvaluationItem set4N6A = new TaxonomyEvaluationItem("set4N6A", "Several", "A", "N=6", "N=6\\Problem-A\\Set-4", GCJGroundTruth.createGoogleCode2008N6Set4AGT());
		
		// Add Problem-A Sets
		datasetBatch.add(set1N6A);
		datasetBatch.add(set2N6A);
		datasetBatch.add(set3N6A);
		datasetBatch.add(set4N6A);
		
		// Mid N=6, Problem-B
		TaxonomyEvaluationItem set1N6B = new TaxonomyEvaluationItem("set1N6B", "Several", "B", "N=6", "N=6\\Problem-B\\Set-1", GCJGroundTruth.createGoogleCode2008N6Set1BGT());
		TaxonomyEvaluationItem set2N6B = new TaxonomyEvaluationItem("set2N6B", "Several", "B", "N=6", "N=6\\Problem-B\\Set-2", GCJGroundTruth.createGoogleCode2008N6Set2BGT());
		TaxonomyEvaluationItem set3N6B = new TaxonomyEvaluationItem("set3N6B", "Several", "B", "N=6", "N=6\\Problem-B\\Set-3", GCJGroundTruth.createGoogleCode2008N6Set3BGT());
		TaxonomyEvaluationItem set4N6B = new TaxonomyEvaluationItem("set4N6B", "Several", "B", "N=6", "N=6\\Problem-B\\Set-4", GCJGroundTruth.createGoogleCode2008N6Set4BGT());
		
		// Add Problem-B Sets
		datasetBatch.add(set1N6B);
		datasetBatch.add(set2N6B);
		datasetBatch.add(set3N6B);
		datasetBatch.add(set4N6B);
	}
	
	
	/* Sample Data Set for Directory Strategy 0 */
	
	/**
	 * Create GIT Evaluation Items  and Add to Batch
	 * Number of Variants, N = 3
	 */
	public void addGitDirectoryRunningExampleVariants() {
		// Mid N=6, Problem-A
		TaxonomyEvaluationItem set1Branches = new TaxonomyEvaluationItem("GIT-Running Example set1Branches", "developer-olan", "Space Variants", "N=3", "Running-Example\\branches", GCJGroundTruth.createRunningExampleBranchesSpaceGT());
		//TaxonomyEvaluationItem set2commits = new TaxonomyEvaluationItem("GIT-Running Example  set2commits", "developer-olan", "Time Variants", "N=6", "Running-Example\\commits", GCJGroundTruth.createGoogleCode2008N6Set2AGT());
		
		// Add Problem-A Sets
		datasetBatch.add(set1Branches);
		//datasetBatch.add(set2commits);
	}
	
	
	/* Sample Data Set for Directory Strategy 0 */
	
	/**
	 * Create GIT Evaluation Items  and Add to Batch
	 * Number of Variants, N = 6
	 */
	public void addGITJavapoetN6Time() {
		
		// Mid N=6, Problem-A
		TaxonomyEvaluationItem set1Commits = new TaxonomyEvaluationItem("GIT-Javapoet set1Commits", "square", "Time Variants", "N=6", "Git\\time\\N=6\\javapoet\\Set-1", GCJGroundTruth.createGITJavapoetN6TimeSet1GT());
		TaxonomyEvaluationItem set2Commits = new TaxonomyEvaluationItem("GIT-Javapoet  set2Commits", "square", "Time Variants", "N=6", "Git\\time\\N=6\\javapoet\\Set-2", GCJGroundTruth.createGITJavapoetN6TimeSet2GT());
		TaxonomyEvaluationItem set3Commits = new TaxonomyEvaluationItem("GIT-Javapoet  set3Commits", "square", "Time Variants", "N=6", "Git\\time\\N=6\\javapoet\\Set-3", GCJGroundTruth.createGITJavapoetN6TimeSet3GT());
		TaxonomyEvaluationItem set4Commits = new TaxonomyEvaluationItem("GIT-Javapoet  set4Commits", "square", "Time Variants", "N=6", "Git\\time\\N=6\\javapoet\\Set-4", GCJGroundTruth.createGITJavapoetN6TimeSet4GT());

		// Add Problem-A Sets
		datasetBatch.add(set1Commits);
		datasetBatch.add(set2Commits);
		datasetBatch.add(set3Commits);
		datasetBatch.add(set4Commits);
	}
	
	public static void main(String args) {
		BatchEvaluator batchEvaluator = new BatchEvaluator();
		batchEvaluator.startEvaluation();
	}


}
