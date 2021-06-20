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
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.RefactoringEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.extraction.SynchronizationEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Action;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionType;
import de.tu_bs.cs.isf.e4cf.refactoring.model.RefactoringLayer;
import de.tu_bs.cs.isf.e4cf.refactoring.model.RefactoringResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.SynchronizationResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.SynchronizationScope;
import de.tu_bs.cs.isf.e4cf.refactoring.test.util.TestUtil;

public class SynchronizationEngineTest {

	private SynchronizationEngine synchronizationEngine = new SynchronizationEngine();
	private RefactoringEngine refactoringEngine = new RefactoringEngine();
	private CompareEngineHierarchical compareEngine = new CompareEngineHierarchical(new SortingMatcher(),
			new MetricImpl("test"));

	private String pathToMutation1 = "";

	private String pathToMutation2 = "";

	@Test
	public void shouldBeAbleToRestoreOriginalTrees_WhenSynchronize() {

		List<RefactoringLayer> refactoringLayers = new ArrayList<RefactoringLayer>();
		refactoringLayers.add(new RefactoringLayer("MethodDeclaration", true));

		Tree tree1 = TestUtil.readFile(pathToMutation1);

		Tree tree2 = TestUtil.readFile(pathToMutation2);

		Tree originalTree1a = tree1.cloneTree();
		RefactoringResult result1a = refactoringEngine.refactor(originalTree1a, refactoringLayers, false);

		Tree originalTree1b = tree1.cloneTree();
		RefactoringResult result1b = refactoringEngine.refactor(originalTree1b, refactoringLayers, false);
		Tree originalTree2 = tree2.cloneTree();
		RefactoringResult result2 = refactoringEngine.refactor(originalTree2, refactoringLayers, false);

		Map<Component, List<ActionScope>> componentToActions = synchronizationEngine.analyzeActions(result1a, result2);

		Map<Node, Node> affectedNodeMapping = new HashMap<Node, Node>();
		Map<Node, Node> actionNodeMapping = new HashMap<Node, Node>();

		for (List<ActionScope> actionScopes : componentToActions.values()) {

			for (ActionScope actionScope : actionScopes) {
				affectedNodeMapping.put(actionScope.getAction().getAffectedNode(),
						actionScope.getAction().getAffectedNode().cloneNode());
				actionNodeMapping.put(actionScope.getAction().getActionNode(),
						actionScope.getAction().getActionNode().cloneNode());
			}
		}

		Map<ActionScope, List<SynchronizationScope>> actionsToSynchronizations = synchronizationEngine
				.analyzeSynchronizations(result1a, result2, componentToActions);

		Map<Set<ActionScope>, Set<SynchronizationScope>> conflicts = synchronizationEngine
				.scanForSynchronizationConflicts(componentToActions, actionsToSynchronizations);

		for (Entry<Set<ActionScope>, Set<SynchronizationScope>> entry : conflicts.entrySet()) {

			for (SynchronizationScope synchronizationScope : entry.getValue()) {
				synchronizationScope.setSynchronize(false);
			}
			entry.getValue().iterator().next().setSynchronize(true);
		}

		SynchronizationResult synchronizationResult = synchronizationEngine.synchronize(result1a, result2,
				componentToActions, actionsToSynchronizations, false);

		// backwards

		Map<Component, List<ActionScope>> componentToActionsBackwards = new HashMap<Component, List<ActionScope>>();
		Map<ActionScope, ActionScope> actionScopeMapping = new HashMap<ActionScope, ActionScope>();

		for (Component component : Lists.reverse(Lists.newArrayList(componentToActions.keySet()))) {
			List<ActionScope> actionScopes = new ArrayList<ActionScope>();
			for (ActionScope actionScope : componentToActions.get(component)) {

				Action action = actionScope.getAction();

				Node affectedNode = affectedNodeMapping.get(action.getAffectedNode());
				Node actionNode = actionNodeMapping.get(action.getActionNode());

				ActionScope newActionScope = null;

				if (action.getActionType() == ActionType.ADD) {
					newActionScope = new ActionScope(
							new Action(ActionType.DELETE, action.getAffectedNode(), action.getActionNode()),
							actionScope.apply());
				} else if (action.getActionType() == ActionType.UPDATE) {
					newActionScope = new ActionScope(
							new Action(ActionType.UPDATE, action.getActionNode(), affectedNode), actionScope.apply());
				} else if (action.getActionType() == ActionType.DELETE) {
					newActionScope = new ActionScope(
							new Action(ActionType.ADD, action.getAffectedNode(), action.getActionNode()),
							actionScope.apply());
				} else if (action.getActionType() == ActionType.MOVE) {
					newActionScope = new ActionScope(new Action(ActionType.MOVE, action.getActionNode(), affectedNode),
							actionScope.apply());
				}

				actionScopes.add(newActionScope);
				actionScopeMapping.put(actionScope, newActionScope);
			}
			componentToActionsBackwards.put(component, actionScopes);

		}

		Map<ActionScope, List<SynchronizationScope>> actionsToSynchronizationsBackwards = new HashMap<ActionScope, List<SynchronizationScope>>();

		for (List<ActionScope> actionScopes : Lists.reverse(Lists.newArrayList(componentToActions.values()))) {

			for (ActionScope actionScope : Lists.reverse(actionScopes)) {
				List<SynchronizationScope> synchronizationScopes = new ArrayList<SynchronizationScope>();

				for (SynchronizationScope synchronizationScope : actionsToSynchronizations.get(actionScope)) {
					synchronizationScopes.add(new SynchronizationScope(synchronizationScope.getNode(),
							synchronizationScope.synchronize()));

				}

				actionsToSynchronizationsBackwards.put(actionScopeMapping.get(actionScope), synchronizationScopes);
			}

		}

		synchronizationResult = synchronizationEngine.synchronize(synchronizationResult, result1b,
				componentToActionsBackwards, actionsToSynchronizationsBackwards, false);
		synchronizationEngine.analyzeSynchronizations(synchronizationResult, result1b, componentToActionsBackwards);

		List<Comparison<Node>> nodeComparisons = new LinkedList<Comparison<Node>>();

		for (Component component1 : synchronizationResult.getComponents()) {
			for (Component component2 : result1b.getComponents()) {
				nodeComparisons.add(compareEngine.compare(component1, component2));
			}
		}

		Map<Component, Component> componentMapping = new HashMap<Component, Component>();

		for (Comparison<Node> comparison : nodeComparisons) {

			if (comparison.getLeftArtifact() != null && comparison.getRightArtifact() != null) {

				componentMapping.put((Component) comparison.getLeftArtifact(),
						(Component) comparison.getRightArtifact());

				for (Comparison<Node> childComparison : comparison.getChildComparisons()) {

					if (childComparison.getLeftArtifact() != null & childComparison.getRightArtifact() == null) {
						Component component = (Component) comparison.getLeftArtifact();
						component.getChildren().remove(childComparison.getLeftArtifact());
					}

				}

			}

		}

		for (Entry<Component, Component> entrySet : componentMapping.entrySet()) {
			NodeComparison comparison = compareEngine.compare(entrySet.getKey(), entrySet.getValue());
			Assertions.assertEquals(comparison.getResultSimilarity(), 1.0f);

		}

	}

}
