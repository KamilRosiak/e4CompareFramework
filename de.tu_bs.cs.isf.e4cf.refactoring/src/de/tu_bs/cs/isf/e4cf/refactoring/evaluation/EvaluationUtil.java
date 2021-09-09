package de.tu_bs.cs.isf.e4cf.refactoring.evaluation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.core.import_export.services.gson.GsonImportService;

public class EvaluationUtil {

	private static GsonImportService gsonImportService = new GsonImportService();
	private static CompareEngineHierarchical compareEngine = new CompareEngineHierarchical(new SortingMatcher(),
			new MetricImpl("test"));

	public static Tree read(String path) {
		try {
			String json = new String(Files.readAllBytes(Paths.get(path)));
			Tree tree = gsonImportService.importTree(json);
			return tree;

		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static float getIntraSimilarity(Component component) {

		List<Node> nodes = component.getAllTargets();

		float lowestSimilarity = 100;
		for (Node node1 : nodes) {

			for (Node node2 : nodes) {

				if (node1 != node2) {

					float similarity = compareEngine.compare(node1, node2).getSimilarity();
					if (similarity < lowestSimilarity) {
						lowestSimilarity = similarity;
					}
				}
			}
		}

		return lowestSimilarity;
	}

	public static float getInterSimilarity(Component component1, Component component2) {
		
		float lowestSimilarity = 100;
		for (Node node1 : component1.getAllTargets()) {

			for (Node node2 : component2.getAllTargets()) {

				float similarity = compareEngine.compare(node1, node2).getSimilarity();
				if (similarity < lowestSimilarity) {
					lowestSimilarity = similarity;
				}
			}
		}

		return lowestSimilarity;
	}

}
