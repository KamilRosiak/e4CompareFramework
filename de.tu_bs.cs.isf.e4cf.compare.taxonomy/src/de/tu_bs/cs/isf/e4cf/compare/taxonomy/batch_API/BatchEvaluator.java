/**
 * 
 */
package de.tu_bs.cs.isf.e4cf.compare.taxonomy.batch_API;

import java.io.File;
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
import de.tu_bs.cs.isf.e4cf.compare.taxonomy.graph.ArtifactGraphCompact;
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

	private final static List<String> SUPPORTED_FILE_ENDINGS = new ArrayList<String>();	
	private String gcjStrategy1DatasetLocation;
	private String gcjStrategy2DatasetLocation;
	private String gcjStrategy3DatasetLocation;
	private String directoryDatasetLocation;
	private String taxonomyMode = "";
	private String exportPath;

	
	private String exportFileName = "taxonomyResults" + LocalDate.now().toString() + "-"+ LocalTime.now().toString().replace(":", "-") +".csv";
	
	private List<TaxonomyEvaluationItem> datasetBatch = new ArrayList<TaxonomyEvaluationItem>(); 
	
	private List<BatchResult> batchResults = new ArrayList<BatchResult>(); 
	
	public List<FileTreeElement> artifactFileTrees = new ArrayList<FileTreeElement>();  
	
	public TaxonomySettings taxonomySettings = new TaxonomySettings();
	
	
	/**
	 * Constructor
	 */
	public BatchEvaluator() {
		
		gcjStrategy1DatasetLocation = new File((this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
				+ "lib/evaluation_data/GCJ/2008-SP-SD-SL/").substring(1)).getPath() + "\\";		
		gcjStrategy2DatasetLocation = new File((this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
				+ "lib/evaluation_data/GCJ/2008-SP-DD-SL/").substring(1)).getPath()+ "\\";		
		gcjStrategy3DatasetLocation = new File((this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
				+ "lib/evaluation_data/GCJ/2008-SP-DD-DL/").substring(1)).getPath()+ "\\";	
		
		exportPath = new File((this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()
				+ "lib/evaluation_data/results/").substring(1)).getPath() + "\\";
		
		initializeTaxonomySettings();
		initializeAcceptedSourceFileTypes();	
	}
	
	/**
	 * Starts Evaluation
	 */
	public void startEvaluation() {

	}
	
	public void exportResults(String evaluationMode) throws IOException {
		String fileName = exportPath + evaluationMode +"-"+ this.taxonomyMode +"-"+exportFileName;
	    FileWriter fileWriter = new FileWriter(fileName);
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    
	    printWriter.println("MEAN VALUES,,,,,,"+getMeanPrecision()+ ","+ getMeanRecall() + ","+getMeanAccuracy()+ ",,"+getMeanPCRA()+","+getMeanNDM()+",,"+getMeanRVPA());
	    
	    printWriter.println("Mode,Item Name,Developer,Problem Class,Variants Code,Path To Variants,Precision,Recall,Accuracy,ErrorRate,CPRA,NDM,TDA,RVPA");
		for (BatchResult aBatchResult: this.batchResults) {
			printWriter.println(aBatchResult.getPropertyAsCSVString());
		}
		
		printWriter.close();
	}
	
	@SuppressWarnings("serial")
	public void initializeAcceptedSourceFileTypes() {
		SUPPORTED_FILE_ENDINGS.addAll(
				new ArrayList<String>() {
					{
						add("java");
						add("cpp");
					}
				});
		
	}
	
	public void initializeTaxonomySettings() {
		this.taxonomySettings.setLevenshteinMode(true);				// Set String difference setter
		this.taxonomySettings.setSourceLevelComparison(true);		// Set Comparison Level
		this.taxonomySettings.setTaxonomyTypeAsTree(true);			// Set Tree Type
		this.taxonomySettings.setLanguageJava(true);				// Set Language Java Type
		this.taxonomySettings.setLanguageCplusplus(true);			// Set Language Type
		
		if (!this.taxonomySettings.getSourceLevelComparison()) {
			this.taxonomySettings.setDirNameMetric(true);			// Set Directory Name Metric
		}
	}
	
	public List<FileTreeElement> parseSourceVariantsInPath(String pathToVariants, String strategy) 
	{
		// Initialize List of found variants
		List<FileTreeElement> foundVariants = new ArrayList<FileTreeElement>();
		// Open a folder
		FileTreeElement variantFolder = null;
		
		if (strategy.equals("1")) { 
			variantFolder = new Directory(gcjStrategy1DatasetLocation + pathToVariants);
		} else if (strategy.equals("2")) {
			variantFolder = new Directory(gcjStrategy2DatasetLocation + pathToVariants);
		} else if (strategy.equals("3")) {
			variantFolder = new Directory(gcjStrategy3DatasetLocation + pathToVariants);
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
				if ((!sourceFileChild.isDirectory()) && SUPPORTED_FILE_ENDINGS.contains(sourceFileChild.getExtension())) {
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
		FileTreeElement variantFolder = new Directory(directoryDatasetLocation + pathToVariants);
		
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
	
	private float getMeanPrecision() {
		List<Float> batchMetricResults = new ArrayList<Float>(); 
		
		for (BatchResult aBatchResult: this.batchResults) {
			batchMetricResults.add(aBatchResult.precisionValue);
		}
		
		return getMeanValue(batchMetricResults);
	}
	
	private float getMeanRecall() {
		List<Float> batchMetricResults = new ArrayList<Float>(); 
		
		for (BatchResult aBatchResult: this.batchResults) {
			batchMetricResults.add(aBatchResult.recallValue);
		}
		
		return getMeanValue(batchMetricResults);
	}
	
	private float getMeanAccuracy() {
		List<Float> batchMetricResults = new ArrayList<Float>(); 
		
		for (BatchResult aBatchResult: this.batchResults) {
			batchMetricResults.add(aBatchResult.accuracyValue);
		}
		
		return getMeanValue(batchMetricResults);
	}
	
	private float getMeanPCRA() {
		List<Float> batchMetricResults = new ArrayList<Float>(); 
		
		for (BatchResult aBatchResult: this.batchResults) {
			batchMetricResults.add(aBatchResult.PCRA);
		}
		
		return getMeanValue(batchMetricResults);
	}
	
	private float getMeanNDM() {
		List<Float> batchMetricResults = new ArrayList<Float>(); 
		
		for (BatchResult aBatchResult: this.batchResults) {
			batchMetricResults.add(aBatchResult.NDM);
		}
		
		return getMeanValue(batchMetricResults);
	}
	
	private float getMeanRVPA() {
		List<Float> batchMetricResults = new ArrayList<Float>(); 
		
		for (BatchResult aBatchResult: this.batchResults) {
			batchMetricResults.add(aBatchResult.RVPA);
		}
		
		return getMeanValue(batchMetricResults);
	}
	
	private float getMeanValue(List<Float> values) {
		float sum = 0;
		for(float floatNum: values) {
			sum += floatNum;
		}
		
		return sum/(float)values.size();
	}
	
	/**
	 * Starts Same Developer Source Evaluation (Strategy 1)
	 */
	public void startSameDeveloperEvaluation() {
		// Clear previously entered Data
		this.datasetBatch.clear();
		// Define Mode
		String evalautionMode = "Same Developer";
		
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
				ArtifactGraphCompact artifactGraph = new ArtifactGraphCompact(engine.artifactComparisonList);
				artifactGraph.setMatchingResults(engine.getMatchingResult());
				artifactGraph.deriveArtifactDetails(artifactFiles);
				artifactGraph.setUpRelationshipGraph(); // Set up relation graph for display
				artifactGraph.printArtifactComparison(); //Print similarities between variants
				
				artifactGraph.setUpTaxonomyGraph();
//				artifactGraph.setUpTaxonomyGraphMarcHentze();
//				artifactGraph.setUpDAGTaxonomyGraph();
				
				// Updated Taxonomy Mode String
				this.taxonomyMode = artifactGraph.getTaxonomyMode();
				
				//3. Compare Ground truth taxonomy and Computed taxonomy 
				TaxonomyEvaluator performanceJudge = new TaxonomyEvaluator(aTaxonomyItem.getGroundTruth(), artifactGraph.taxonomyRootNode);
				//performanceJudge.computePrimaryMeasures();
				performanceJudge.computePrimaryMeasuresPS();
				performanceJudge.calculateSecondaryMeasures();
				performanceJudge.printMeasures();
				performanceJudge.computeCustomMeasures();
				performanceJudge.printCustomMeasures();
				
				BatchResult newBatch = new BatchResult(evalautionMode + "-" +artifactGraph.getTaxonomyMode(), aTaxonomyItem.getItemName(), aTaxonomyItem.getVariantsDeveloper() , aTaxonomyItem.getProblemClass(), aTaxonomyItem.getNoOfVariants(), aTaxonomyItem.getPathToVariants(), performanceJudge.getPrecisionValue(), performanceJudge.getRecallValue(), performanceJudge.getAccuracyValue(), performanceJudge.getErrorRateValue(), performanceJudge.getPCRA(), performanceJudge.getNDM(), performanceJudge.getTDA(), performanceJudge.getRVPA());
				batchResults.add(newBatch);
				try {
				exportResults(evalautionMode);
				} catch (Exception ex) {
				 System.out.println("Could not print: "+ ex.getMessage());	
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
		// Define Mode
		String evalautionMode = "DD-SL";
		// Add data to Batch before evaluation
//		addGCJSourceVariantsDifferentDevelopersN3();	// Add GCJ Variants for N = 3
//		addGCJSourceVariantsDifferentDevelopersN4();	// Add GCJ Variants for N = 4
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
				ArtifactGraphCompact artifactGraph = new ArtifactGraphCompact(engine.artifactComparisonList);
				artifactGraph.setMatchingResults(engine.getMatchingResult());
				artifactGraph.deriveArtifactDetails(artifactFiles);
				artifactGraph.setUpRelationshipGraph(); // Set up relation graph for display
				artifactGraph.printArtifactComparison(); //Print similarities between variants
				
//				artifactGraph.setUpTaxonomyGraph();
				artifactGraph.setUpTaxonomyGraphMarcHentze();
//				artifactGraph.setUpDAGTaxonomyGraph();
				
				// Updated Taxonomy Mode String
				this.taxonomyMode = artifactGraph.getTaxonomyMode();
				
				//3. Compare Ground truth taxonomy and Computed taxonomy 
				TaxonomyEvaluator performanceJudge = new TaxonomyEvaluator(aTaxonomyItem.getGroundTruth(), artifactGraph.taxonomyRootNode);
				//performanceJudge.computePrimaryMeasures();
				performanceJudge.computePrimaryMeasuresPS();
				performanceJudge.calculateSecondaryMeasures();
				performanceJudge.printMeasures();
				performanceJudge.computeCustomMeasures();
				performanceJudge.printCustomMeasures();
				
				BatchResult newBatch = new BatchResult(evalautionMode + "-" +artifactGraph.getTaxonomyMode(), aTaxonomyItem.getItemName(), aTaxonomyItem.getVariantsDeveloper() , aTaxonomyItem.getProblemClass(), aTaxonomyItem.getNoOfVariants(), aTaxonomyItem.getPathToVariants(), performanceJudge.getPrecisionValue(), performanceJudge.getRecallValue(), performanceJudge.getAccuracyValue(), performanceJudge.getErrorRateValue(), performanceJudge.getPCRA(), performanceJudge.getNDM(), performanceJudge.getTDA(), performanceJudge.getRVPA());

				batchResults.add(newBatch);
				try {
				exportResults(evalautionMode);
				} catch (Exception ex) {
					 System.out.println("Could not print: "+ ex.getMessage());	
				}
			}
		}
	}
	
	/**
	 * Starts Different Developers Source Evaluation (Strategy 3)
	 */
	public void startDifferentLanguagesEvaluation() {
		// Clear previously entered Data
		this.datasetBatch.clear();
		// Define Mode
		String evalautionMode = "DD-DL";
		// Add data to Batch before evaluation
		addGCJSourceVariantsDifferentLanguagesN5();	// Add GCJ Variants for N = 5
		addGCJSourceVariantsDifferentLanguagesN6();	// Add GCJ Variants for N = 6
		
		for (TaxonomyEvaluationItem aTaxonomyItem: this.datasetBatch) {
			TaxonomyCompareEngine engine = new TaxonomyCompareEngine();
			engine.setTaxnomySettings(this.taxonomySettings);
			//1.  Get variants by parsing paths
			List<FileTreeElement> artifactFiles = parseSourceVariantsInPath(aTaxonomyItem.getPathToVariants(), "3");
			List<Tree> artifacts = convertToTree(artifactFiles);
			//2.  Compute taxonomy
			if (artifacts.size() > 1) {
				// Create graph for artifacts
				engine.compare(artifacts);
				ArtifactGraphCompact artifactGraph = new ArtifactGraphCompact(engine.artifactComparisonList);
				artifactGraph.deriveArtifactDetails(artifactFiles);
				artifactGraph.setUpRelationshipGraph(); // Set up relation graph for display
				artifactGraph.printArtifactComparison(); //Print similarities between variants
				
//				artifactGraph.setUpTaxonomyGraph();
				artifactGraph.setUpTaxonomyGraphMarcHentze();
//				artifactGraph.setUpDAGTaxonomyGraph();
				
				// Updated Taxonomy Mode String
				this.taxonomyMode = artifactGraph.getTaxonomyMode();
				
				//3. Compare Ground truth taxonomy and Computed taxonomy 
				TaxonomyEvaluator performanceJudge = new TaxonomyEvaluator(aTaxonomyItem.getGroundTruth(), artifactGraph.taxonomyRootNode);
				//performanceJudge.computePrimaryMeasures();
				performanceJudge.computePrimaryMeasuresPS();
				performanceJudge.calculateSecondaryMeasures();
				performanceJudge.printMeasures();
				performanceJudge.computeCustomMeasures();
				performanceJudge.printCustomMeasures();
				
				BatchResult newBatch = new BatchResult(evalautionMode + "-" +artifactGraph.getTaxonomyMode(), aTaxonomyItem.getItemName(), aTaxonomyItem.getVariantsDeveloper() , aTaxonomyItem.getProblemClass(), aTaxonomyItem.getNoOfVariants(), aTaxonomyItem.getPathToVariants(), performanceJudge.getPrecisionValue(), performanceJudge.getRecallValue(), performanceJudge.getAccuracyValue(), performanceJudge.getErrorRateValue(), performanceJudge.getPCRA(), performanceJudge.getNDM(), performanceJudge.getTDA(), performanceJudge.getRVPA());
				batchResults.add(newBatch);
				try {
				exportResults(evalautionMode);
				} catch (Exception ex) {
					 System.out.println("Could not print: "+ ex.getMessage());	
				}
			}
		}
	}
	
	
	/**
	 * Starts RunningExampleDirectory Evaluation
	 */
	public void startGITLoggerEvaluation() {
		// Clear previously entered Data
		this.datasetBatch.clear();
		// Define Mode
		String evalautionMode = "GIT-Logger";
		
		// Add data to Batch before evaluation
		addGITLoggerN6Time();	// Add GIT Logger Variants for N = 6
		
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
				ArtifactGraphCompact artifactGraph = new ArtifactGraphCompact(engine.artifactComparisonList);
				artifactGraph.deriveArtifactDetails(artifactFolders);
				artifactGraph.setUpRelationshipGraph(); // Set up relation graph for display
				artifactGraph.printArtifactComparison(); //Print similarities between variants
				 
				artifactGraph.setUpTaxonomyGraph();
//				artifactGraph.setUpTaxonomyGraphMarcHentze();
//				artifactGraph.setUpDAGTaxonomyGraph();
				
				
				//3. Compare Ground truth taxonomy and Computed taxonomy 
				TaxonomyEvaluator performanceJudge = new TaxonomyEvaluator(aTaxonomyItem.getGroundTruth(), artifactGraph.taxonomyRootNode);
				performanceJudge.computePrimaryMeasuresPS();
				performanceJudge.calculateSecondaryMeasures();
				performanceJudge.printMeasures();
				performanceJudge.computeCustomMeasures();
				performanceJudge.printCustomMeasures();
				
				BatchResult newBatch = new BatchResult("GIT-Logger", aTaxonomyItem.getItemName(), aTaxonomyItem.getVariantsDeveloper() , aTaxonomyItem.getProblemClass(), aTaxonomyItem.getNoOfVariants(), aTaxonomyItem.getPathToVariants(), performanceJudge.getPrecisionValue(), performanceJudge.getRecallValue(), performanceJudge.getAccuracyValue(), performanceJudge.getErrorRateValue(), performanceJudge.getPCRA(), performanceJudge.getNDM(), performanceJudge.getTDA(), performanceJudge.getRVPA());
				batchResults.add(newBatch);
				
				try {
					exportResults(evalautionMode);
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
		// Define Mode
		String evalautionMode = "RunningExampleDirectoryEvaluation";
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
				ArtifactGraphCompact artifactGraph = new ArtifactGraphCompact(engine.artifactComparisonList);
				artifactGraph.deriveArtifactDetails(artifactFolders);
				artifactGraph.setUpRelationshipGraph(); // Set up relation graph for display
				artifactGraph.printArtifactComparison(); //Print similarities between variants
				 
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
				
				BatchResult newBatch = new BatchResult("RunningExampleDirectoryEvaluation", aTaxonomyItem.getItemName(), aTaxonomyItem.getVariantsDeveloper() , aTaxonomyItem.getProblemClass(), aTaxonomyItem.getNoOfVariants(), aTaxonomyItem.getPathToVariants(), performanceJudge.getPrecisionValue(), performanceJudge.getRecallValue(), performanceJudge.getAccuracyValue(), performanceJudge.getErrorRateValue(), performanceJudge.getPCRA(), performanceJudge.getNDM(), performanceJudge.getTDA(), performanceJudge.getRVPA());
				batchResults.add(newBatch);
				
				try {
					exportResults(evalautionMode);
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
		TaxonomyEvaluationItem halyavinN3D = new TaxonomyEvaluationItem("halyavinN3D", "halyavin", "Problem-D", "N=3", "N=3\\halyavin\\Problem-D", GCJGroundTruth.createGoogleCode2008N3halyavinDGT());
		TaxonomyEvaluationItem jediknightN3B = new TaxonomyEvaluationItem("jediknightN3B", "jediknight", "Problem-B", "N=3", "N=3\\jediknight\\Problem-B", GCJGroundTruth.createGoogleCode2008N3jediknightBGT());
		TaxonomyEvaluationItem kotehokN3A = new TaxonomyEvaluationItem("kotehokN3A", "kotehok", "Problem-A", "N=3", "N=3\\kotehok\\Problem-A", GCJGroundTruth.createGoogleCode2008N3kotehokAGT());
		TaxonomyEvaluationItem kotehokN3D = new TaxonomyEvaluationItem("kotehokN3D", "kotehok", "Problem-D", "N=3", "N=3\\kotehok\\Problem-D", GCJGroundTruth.createGoogleCode2008N3kotehokDGT());
		TaxonomyEvaluationItem wataN3D = new TaxonomyEvaluationItem("wataN3D", "wata", "Problem-D", "N=3", "N=3\\wata\\Problem-D", GCJGroundTruth.createGoogleCode2008N3wataDGT());
		TaxonomyEvaluationItem ymatsuxN3A = new TaxonomyEvaluationItem("ymatsuxN3A", "ymatsux", "Problem-A", "N=3", "N=3\\ymatsux\\Problem-A", GCJGroundTruth.createGoogleCode2008N3ymatsuxAGT());
		TaxonomyEvaluationItem ymatsuxN3C = new TaxonomyEvaluationItem("ymatsuxN3C", "ymatsux", "Problem-C", "N=3", "N=3\\ymatsux\\Problem-C", GCJGroundTruth.createGoogleCode2008N3ymatsuxCGT());
		
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
		TaxonomyEvaluationItem halyavinN4B = new TaxonomyEvaluationItem("halyavinN4B", "halyavin", "Problem-B", "N=4", "N=4\\halyavin\\Problem-B", GCJGroundTruth.createGoogleCode2008N4halyavinBGT());
		TaxonomyEvaluationItem halyavinN4C = new TaxonomyEvaluationItem("halyavinN4C", "halyavin", "Problem-C", "N=4", "N=4\\halyavin\\Problem-C", GCJGroundTruth.createGoogleCode2008N4halyavinCGT());
		TaxonomyEvaluationItem kotehokN4B = new TaxonomyEvaluationItem("kotehokN4B", "kotehok", "Problem-B", "N=4", "N=4\\kotehok\\Problem-B", GCJGroundTruth.createGoogleCode2008N4kotehokBGT());
		TaxonomyEvaluationItem kotehokN4C = new TaxonomyEvaluationItem("kotehokN4C", "kotehok", "Problem-C", "N=4", "N=4\\kotehok\\Problem-C", GCJGroundTruth.createGoogleCode2008N4kotehokCGT());
		TaxonomyEvaluationItem mysticN4B = new TaxonomyEvaluationItem("mysticN4B", "mystic", "Problem-B", "N=4", "N=4\\mystic\\Problem-B", GCJGroundTruth.createGoogleCode2008N4mysticBGT());
		TaxonomyEvaluationItem mysticN4D = new TaxonomyEvaluationItem("mysticN4D", "mystic", "Problem-D", "N=4", "N=4\\mystic\\Problem-D", GCJGroundTruth.createGoogleCode2008N4mysticDGT());
		TaxonomyEvaluationItem wataN4A = new TaxonomyEvaluationItem("wataN4A", "wata", "Problem-A", "N=4", "N=4\\wata\\Problem-A", GCJGroundTruth.createGoogleCode2008N4wataAGT());	
		TaxonomyEvaluationItem wataN4B = new TaxonomyEvaluationItem("wataN4B", "wata", "Problem-B", "N=4", "N=4\\wata\\Problem-B", GCJGroundTruth.createGoogleCode2008N4wataBGT());	
		TaxonomyEvaluationItem wataN4C = new TaxonomyEvaluationItem("wataN4C", "wata", "Problem-C", "N=4", "N=4\\wata\\Problem-C", GCJGroundTruth.createGoogleCode2008N4wataCGT());	
		
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
		TaxonomyEvaluationItem halyavinN5A = new TaxonomyEvaluationItem("halyavinN5A", "halyavin", "Problem-A", "N=5", "N=5\\halyavin\\Problem-A", GCJGroundTruth.createGoogleCode2008N5halyavinAGT());
		TaxonomyEvaluationItem mysticN5A = new TaxonomyEvaluationItem("mysticN5A", "mystic", "Problem-A", "N=5", "N=5\\mystic\\Problem-A", GCJGroundTruth.createGoogleCode2008N5mysticAGT());
		TaxonomyEvaluationItem mysticN5C = new TaxonomyEvaluationItem("mysticN5C", "mystic", "Problem-C", "N=5", "N=5\\mystic\\Problem-C", GCJGroundTruth.createGoogleCode2008N5mysticCGT());
		
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
	
	/* Data Set for Source Strategy 2- SPDSDDDL (Different Languages)*/
	
	/**
	 * Create GCJ Evaluation Items  and Add to Batch
	 * SPDSDDSL - Same Problem class, Different Stages, Different Developers, Same Language
	 * Number of Variants, N = 5
	 */
	public void addGCJSourceVariantsDifferentLanguagesN5() {
		// Mid N=5, ProblemA
		TaxonomyEvaluationItem set1N5DLA = new TaxonomyEvaluationItem("set1N5DLA", "Several", "A", "N=5", "N=5\\Problem-A\\Set-1", GCJGroundTruth.createGoogleCode2008N5DLSet1AGT());
		TaxonomyEvaluationItem set2N5DLA = new TaxonomyEvaluationItem("set2N5DLA", "Several", "A", "N=5", "N=5\\Problem-A\\Set-2", GCJGroundTruth.createGoogleCode2008N5DLSet2AGT());
		TaxonomyEvaluationItem set3N5DLA = new TaxonomyEvaluationItem("set3N5DLA", "Several", "A", "N=5", "N=5\\Problem-A\\Set-3", GCJGroundTruth.createGoogleCode2008N5DLSet3AGT());
		TaxonomyEvaluationItem set4N5DLA = new TaxonomyEvaluationItem("set4N5DLA", "Several", "A", "N=5", "N=5\\Problem-A\\Set-4", GCJGroundTruth.createGoogleCode2008N5DLSet4AGT());
	
		// Add Problem-A Sets
		datasetBatch.add(set1N5DLA);
		datasetBatch.add(set2N5DLA);
		datasetBatch.add(set3N5DLA);
		datasetBatch.add(set4N5DLA);

		// Mid N=5, Problem-B
		TaxonomyEvaluationItem set1N5DLB = new TaxonomyEvaluationItem("set1N5DLB", "Several", "B", "N=5", "N=5\\Problem-B\\Set-1", GCJGroundTruth.createGoogleCode2008N5DLSet1BGT());
		TaxonomyEvaluationItem set2N5DLB = new TaxonomyEvaluationItem("set2N5DLB", "Several", "B", "N=5", "N=5\\Problem-B\\Set-2", GCJGroundTruth.createGoogleCode2008N5DLSet2BGT());
		TaxonomyEvaluationItem set3N5DLB = new TaxonomyEvaluationItem("set3N5DLB", "Several", "B", "N=5", "N=5\\Problem-B\\Set-3", GCJGroundTruth.createGoogleCode2008N5DLSet3BGT());
		TaxonomyEvaluationItem set4N5DLB = new TaxonomyEvaluationItem("set4N5DLB", "Several", "B", "N=5", "N=5\\Problem-B\\Set-4", GCJGroundTruth.createGoogleCode2008N5DLSet4BGT());

		// Add Problem-B Sets
		datasetBatch.add(set1N5DLB);
		datasetBatch.add(set2N5DLB);
		datasetBatch.add(set3N5DLB);
		datasetBatch.add(set4N5DLB);
	}
	
	/**
	 * Create GCJ Evaluation Items  and Add to Batch
	 * SPDSDDSL - Same Problem class, Different Stages, Different Developers, Same Language
	 * Number of Variants, N = 6
	 */
	public void addGCJSourceVariantsDifferentLanguagesN6() {
		// Mid N=6, Problem-A
		TaxonomyEvaluationItem set1N6DLA = new TaxonomyEvaluationItem("set1N6DLA", "Several", "A", "N=6", "N=6\\Problem-A\\Set-1", GCJGroundTruth.createGoogleCode2008N6DLSet1AGT());
		TaxonomyEvaluationItem set2N6DLA = new TaxonomyEvaluationItem("set2N6DLA", "Several", "A", "N=6", "N=6\\Problem-A\\Set-2", GCJGroundTruth.createGoogleCode2008N6DLSet2AGT());
		TaxonomyEvaluationItem set3N6DLA = new TaxonomyEvaluationItem("set3N6DLA", "Several", "A", "N=6", "N=6\\Problem-A\\Set-3", GCJGroundTruth.createGoogleCode2008N6DLSet3AGT());
		TaxonomyEvaluationItem set4N6DLA = new TaxonomyEvaluationItem("set4N6DLA", "Several", "A", "N=6", "N=6\\Problem-A\\Set-4", GCJGroundTruth.createGoogleCode2008N6DLSet4AGT());
		
		// Add Problem-A Sets
		datasetBatch.add(set1N6DLA);
		datasetBatch.add(set2N6DLA);
		datasetBatch.add(set3N6DLA);
		datasetBatch.add(set4N6DLA);
		
		// Mid N=6, Problem-B
		TaxonomyEvaluationItem set1N6DLB = new TaxonomyEvaluationItem("set1N6DLB", "Several", "B", "N=6", "N=6\\Problem-B\\Set-1", GCJGroundTruth.createGoogleCode2008N6DLSet1BGT());
		TaxonomyEvaluationItem set2N6DLB = new TaxonomyEvaluationItem("set2N6DLB", "Several", "B", "N=6", "N=6\\Problem-B\\Set-2", GCJGroundTruth.createGoogleCode2008N6DLSet2BGT());
		TaxonomyEvaluationItem set3N6DLB = new TaxonomyEvaluationItem("set3N6DLB", "Several", "B", "N=6", "N=6\\Problem-B\\Set-3", GCJGroundTruth.createGoogleCode2008N6DLSet3BGT());
		TaxonomyEvaluationItem set4N6DLB = new TaxonomyEvaluationItem("set4N6DLB", "Several", "B", "N=6", "N=6\\Problem-B\\Set-4", GCJGroundTruth.createGoogleCode2008N6DLSet4BGT());
		
		// Add Problem-B Sets
		datasetBatch.add(set1N6DLB);
		datasetBatch.add(set2N6DLB);
		datasetBatch.add(set3N6DLB);
		datasetBatch.add(set4N6DLB);
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
	public void addGITLoggerN6Time() {
		
		// Mid N=6, Problem-A
		TaxonomyEvaluationItem set1Commits = new TaxonomyEvaluationItem("GIT-Logger set1Commits", "orhanobut", "Time Variants", "N=6", "Git\\time\\N=6\\logger\\Set-1", GCJGroundTruth.createGITLoggerN6TimeSet1GT());
		TaxonomyEvaluationItem set2Commits = new TaxonomyEvaluationItem("GIT-Logger set2Commits", "orhanobut", "Time Variants", "N=6", "Git\\time\\N=6\\logger\\Set-2", GCJGroundTruth.createGITLoggerN6TimeSet2GT());
		TaxonomyEvaluationItem set3Commits = new TaxonomyEvaluationItem("GIT-Logger set3Commits", "orhanobut", "Time Variants", "N=6", "Git\\time\\N=6\\logger\\Set-3", GCJGroundTruth.createGITLoggerN6TimeSet3GT());
		TaxonomyEvaluationItem set4Commits = new TaxonomyEvaluationItem("GIT-Logger set4Commits", "orhanobut", "Time Variants", "N=6", "Git\\time\\N=6\\logger\\Set-4", GCJGroundTruth.createGITLoggerN6TimeSet4GT());

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
