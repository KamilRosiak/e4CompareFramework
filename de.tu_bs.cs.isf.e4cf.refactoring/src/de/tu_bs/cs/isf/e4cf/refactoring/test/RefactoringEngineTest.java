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
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ExtractionEngine;

import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentLayer;
import de.tu_bs.cs.isf.e4cf.refactoring.test.util.TestUtil;

class RefactoringEngineTest {

	private ExtractionEngine extractionEngine = new ExtractionEngine();
	private CompareEngineHierarchical compareEngine = new CompareEngineHierarchical(new SortingMatcher(),
			new MetricImpl("test"));

	private String pathToMutation = "";

	@Test
	public void shouldBeAbleToRestoreOriginalTree_WhenRefactor() {

		//TODO

	}

}
