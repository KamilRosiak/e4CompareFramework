package de.tu_bs.cs.isf.e4cf.refactoring.test;

import org.junit.jupiter.api.Test;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.ExtractionEngine;

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
