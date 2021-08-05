package de.tu_bs.cs.isf.e4cf.refactoring.test;

import org.junit.jupiter.api.Test;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.SynchronizationEngine;

public class SynchronizationEngineTest {

	private SynchronizationEngine synchronizationEngine = new SynchronizationEngine();

	private CompareEngineHierarchical compareEngine = new CompareEngineHierarchical(new SortingMatcher(),
			new MetricImpl("test"));

	private String pathToMutation1 = "";

	private String pathToMutation2 = "";

	@Test
	public void shouldBeAbleToRestoreOriginalTrees_WhenSynchronize() {

		//TODO

	}

}
