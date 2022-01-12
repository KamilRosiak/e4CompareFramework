package de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.evaluation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.io.reader.SrcMLReader;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.model.TaxonomyGraph;
import de.tu_bs.cs.isf.e4cf.compare.taxonomy_mining.model.TaxonomySimilarityGraph;

public class Evaluation {

	private static SrcMLReader srcMLReader;

	private static List<File> targetDirectories;

	public static void main(String[] args) {

		File srcMlDirectory = new File(
				(Evaluation.class.getProtectionDomain().getCodeSource().getLocation().getPath()).substring(1))
						.getParentFile();
		String srcmlPath = Paths.get(srcMlDirectory.getAbsolutePath(), "lib/srcml/srcml.exe").toString();
		srcMLReader = new SrcMLReader(srcmlPath);

		File evaluationDirectoryFile = new File(
				(Evaluation.class.getProtectionDomain().getCodeSource().getLocation().getPath()).substring(1))
						.getParentFile();
		String remoteGitEvaluationDirectory = Paths
				.get(evaluationDirectoryFile.getAbsolutePath(), "lib/evaluation/remote").toString();
		String localGitEvaluationDirectory = Paths
				.get(evaluationDirectoryFile.getAbsolutePath(), "lib/evaluation/local").toString();

		targetDirectories = GitExtractor.extractLocal(localGitEvaluationDirectory);
		targetDirectories.addAll(GitExtractor.extractRemote(remoteGitEvaluationDirectory));
		evaluateRepositories();

	}
	
	

	public static void evaluateRepositories() {

		try {
			for (File targetDirectory : targetDirectories) {

				File variantsDirectory = Paths.get(targetDirectory.getAbsolutePath(), "variants").toFile();
				Path groundTruthFilePath = Paths.get(targetDirectory.getAbsolutePath(), "groundTruth.txt");
				Path derivedTaxonomyFilePath = Paths.get(targetDirectory.getAbsolutePath(), "derivedTaxonomy.txt");
				Path resultFilePath = Paths.get(targetDirectory.getAbsolutePath(), "result.txt");

				Map<String, Tree> nameToTreeMap = new HashMap<String, Tree>();

				for (File variant : variantsDirectory.listFiles(File::isDirectory)) {
					Tree tree = srcMLReader.readArtifact(variant, targetDirectory.getName());
					nameToTreeMap.put(variant.getName(), tree);

				}

				TaxonomyGraph groundTruthGraph = new TaxonomySimilarityGraph(
						new ArrayList<Tree>(nameToTreeMap.values()));
				groundTruthGraph.fromFile(groundTruthFilePath, nameToTreeMap);

				TaxonomyGraph taxonomyGraph = new TaxonomySimilarityGraph(new ArrayList<Tree>(nameToTreeMap.values()));
				taxonomyGraph.build();

				taxonomyGraph.toFile(derivedTaxonomyFilePath);

				TaxonomyGraphComparison graphComparison = new TaxonomyGraphComparison(
						new ArrayList<Tree>(nameToTreeMap.values()), groundTruthGraph, taxonomyGraph);

				graphComparison.toFile(resultFilePath);

			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
