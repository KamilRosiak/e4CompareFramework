package de.tu_bs.cs.isf.e4cf.refactoring.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;

import de.tu_bs.cs.isf.e4cf.refactoring.extraction.SynchronizationEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Action;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionType;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentLayer;

import de.tu_bs.cs.isf.e4cf.refactoring.model.SynchronizationResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.SynchronizationScope;
import de.tu_bs.cs.isf.e4cf.refactoring.test.util.TestUtil;

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
