package de.tu_bs.cs.isf.e4cf.refactoring.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.RefactoringEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.model.RefactoringLayer;
import de.tu_bs.cs.isf.e4cf.refactoring.test.util.TestUtil;

class RefactoringEngineTest {

	private RefactoringEngine refactoringEngine = new RefactoringEngine();
	private CompareEngineHierarchical compareEngine = new CompareEngineHierarchical(new SortingMatcher(),
			new MetricImpl("test"));

	private String pathToMutation = "";

	@Test
	public void shouldBeAbleToRestoreOriginalTree_WhenRefactor() {

		Tree tree = TestUtil.readFile(pathToMutation);
		Tree originalTree1 = tree.cloneTree();

		List<RefactoringLayer> refactoringLayers = new ArrayList<RefactoringLayer>();
		refactoringLayers.add(new RefactoringLayer("MethodDeclaration", true));

		refactoringEngine.refactor(tree, refactoringLayers, true);

		Tree originalTree2 = TestUtil.assembleTree(tree);

		NodeComparison comparison = compareEngine.compare(originalTree1.getRoot(), originalTree2.getRoot());

		Assertions.assertEquals(comparison.getResultSimilarity(), 1.0f);

	}

}
