package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.Files;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.gson.GsonExportService;
import de.tu_bs.cs.isf.e4cf.evaluation.dialog.GeneratorOptions;
import de.tu_bs.cs.isf.e4cf.evaluation.generator.CloneGenerator.Variant;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Granularity;

public class Run {

	private String directory;

	private Granularity granularity;

	private boolean attributeChange;

	private boolean nodeChange;

	private GeneratorOptions generatorOptions;

	private List<ClusteringResult> clusteringResults;

	private List<StabilityResult> stabilityResults;

	private List<Variant> variants;

	public List<Variant> getVariants() {
		return variants;
	}

	public void setVariants(List<Variant> variants) {
		this.variants = variants;
	}

	public Run(Granularity granularity, boolean attributeChange, boolean nodeChange,
			GeneratorOptions generatorOptions) {
		super();
		this.granularity = granularity;
		this.attributeChange = attributeChange;
		this.nodeChange = nodeChange;
		this.generatorOptions = generatorOptions;
		this.clusteringResults = new ArrayList<ClusteringResult>();
		this.setStabilityResults(new ArrayList<StabilityResult>());
	}

	public List<ClusteringResult> getClusteringResults() {
		return clusteringResults;
	}

	public void setClusteringResults(List<ClusteringResult> clusteringResults) {
		this.clusteringResults = clusteringResults;
	}

	public Granularity getGranularity() {
		return granularity;
	}

	public void setGranularity(Granularity granularity) {
		this.granularity = granularity;
	}

	public boolean isAttributeChange() {
		return attributeChange;
	}

	public void setAttributeChange(boolean attributeChange) {
		this.attributeChange = attributeChange;
	}

	public boolean isNodeChange() {
		return nodeChange;
	}

	public void setNodeChange(boolean nodeChange) {
		this.nodeChange = nodeChange;
	}

	public GeneratorOptions getGeneratorOptions() {
		return generatorOptions;
	}

	public void setGeneratorOptions(GeneratorOptions generatorOptions) {
		this.generatorOptions = generatorOptions;
	}

	public List<StabilityResult> getStabilityResults() {
		return stabilityResults;
	}

	public void setStabilityResults(List<StabilityResult> stabilityResults) {
		this.stabilityResults = stabilityResults;
	}

	public void saveVariants(GsonExportService exportService) {

		String variantDirectory = "GeneratedVariants";

		try {
			File file = new File(directory);
			file.mkdir();

			file = new File(directory + "\\" + variantDirectory + "\\");
			file.mkdir();

			for (int i = 0; i < variants.size(); i++) {
				Variant variant = variants.get(i);
				TreeImpl tree = (TreeImpl) variant.tree;

				String json = exportService.exportTree(tree);

				if (i == 0) {
					file = new File(directory + "\\" + variantDirectory + "\\" + variant.tree.getTreeName() + ".tree");
				} else {
					file = new File(directory + "\\" + variantDirectory + "\\" + variant.tree.getTreeName() + "_" + i
							+ ".tree");
				}
				file.createNewFile();
				Files.write(json.getBytes(), file);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void saveClusteringResults() {

		String clusteringResultName = "clusteringResult.csv";
		String metricName = "metric.txt";

		try {

			String clusteringHeader = "averageIntraSimilarity,averageInterSimilarity,insufficientClusters,numberOfClusters,averageClusterSize";
			File file = new File(directory + "\\" + clusteringResultName);
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			PrintWriter printWriter = new PrintWriter(fileWriter);

			printWriter.println(clusteringHeader);

			for (ClusteringResult clusteringResult : clusteringResults) {
				printWriter.println(clusteringResult.toString());
			}

			printWriter.close();

			file = new File(directory + "\\" + metricName);
			file.createNewFile();
			Files.write(this.toString().getBytes(), file);

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public void saveRestoredTrees(List<Tree> restoredTrees, GsonExportService exportService) {
		String restoredTreesDirectory = "Restored trees";

		try {

			File file = new File(directory + "\\" + restoredTreesDirectory + "\\");
			file.mkdir();
			for (Tree restoredTree : restoredTrees) {
				TreeImpl tree = (TreeImpl) restoredTree;

				String json = exportService.exportTree(tree);

				file = new File(directory + "\\" + restoredTreesDirectory + "\\" + tree.getTreeName() + ".tree");

				file.createNewFile();
				Files.write(json.getBytes(), file);

			}

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public void saveStabilityResults() {

		String stabilityResultName = "stabilityResult.csv";

		try {

			String stabilityHeader = "clusterSplits,clusterMerges,changeAction";

			File file = new File(directory + "\\" + stabilityResultName);
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			PrintWriter printWriter = new PrintWriter(fileWriter);

			printWriter.println(stabilityHeader);

			for (StabilityResult stabilityResult : stabilityResults) {
				printWriter.println(stabilityResult.toString());
			}
			printWriter.close();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	@Override
	public String toString() {

		return "Granularity: " + granularity.getLayer() + "\n" + "Attribute change: " + attributeChange + "\n"
				+ "Node change: " + nodeChange + "\n" + "Variants: " + generatorOptions.variants + "\n"
				+ "ModificationRatioPercentage: " + generatorOptions.modificationRatioPercentage + "\n"
				+ "VariantChangeDegree: " + generatorOptions.variantChangeDegree + "\n" + "SyntaxSafe: "
				+ generatorOptions.isSyntaxSafe;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

}
