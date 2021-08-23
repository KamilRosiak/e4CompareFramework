package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentComparison;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ConfigurationComparison;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Delete;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Insert;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Move;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Update;
import de.tu_bs.cs.isf.e4cf.refactoring.util.SynchronizationUtil;

@Singleton
@Creatable
public class ConfigurationComparator {

	public List<ConfigurationComparison> compareConfigurations(List<ComponentComparison> componentComparisons) {

		List<ConfigurationComparison> configurationComparisonResults = new ArrayList<ConfigurationComparison>();
		for (ComponentComparison componentComparison : componentComparisons) {

			if (componentComparison.getComponent1() != null && componentComparison.getComponent2() != null) {
				for (Entry<Configuration, Configuration> entry : componentComparison.getMatchedConfigurations()
						.entrySet()) {
					List<ActionScope> actions = generateEditScript(entry.getKey().getChildren().get(0),
							entry.getValue().getChildren().get(0));
					configurationComparisonResults.add(new ConfigurationComparison(entry.getKey(), entry.getValue(),
							actions, componentComparison.getComponent1(), componentComparison.getComponent2()));

				}
			}

		}

		return configurationComparisonResults;

	}

	private CompareEngineHierarchical compareEngine;
	private Map<Node, Node> cloneMapping;
	private Map<Node, Node> mapping;

	private Node cloneTree1;
	private Node cloneTree2;

	private void createCloneMapping(Node node, Node clone) {
		cloneMapping.put(node, clone);
		cloneMapping.put(clone, node);
		for (int i = 0; i < node.getChildren().size(); i++) {
			createCloneMapping(node.getChildren().get(i), clone.getChildren().get(i));
		}

	}

	public ConfigurationComparator() {
		compareEngine = new CompareEngineHierarchical(new SortingMatcher(), new MetricImpl("test"));

	}

	private void buildMapping(Comparison<Node> comparison) {
		if (comparison.getLeftArtifact() != null && comparison.getRightArtifact() != null) {
			mapping.put(comparison.getLeftArtifact(), comparison.getRightArtifact());
			mapping.put(comparison.getRightArtifact(), comparison.getLeftArtifact());
		}
		for (Comparison<Node> childComparison : comparison.getChildComparisons()) {
			buildMapping(childComparison);
		}

	}

	private List<Comparison<Node>> sortComparisons(List<Comparison<Node>> comparisons) {

		// sort comparisons: INSERT, DELETE, MOVE, UPDATE
		List<Comparison<Node>> sortedComparison = new ArrayList<Comparison<Node>>();

		for (Comparison<Node> comparison : comparisons) {

			if (comparison.getLeftArtifact() == null) {
				sortedComparison.add(comparison);
			}
		}

		for (Comparison<Node> comparison : comparisons) {

			if (comparison.getRightArtifact() == null) {
				sortedComparison.add(comparison);

			}
		}
		for (Comparison<Node> comparison : comparisons) {

			if (comparison.getLeftArtifact() != null && comparison.getRightArtifact() != null) {

				if (comparison.getLeftArtifact().getPosition() != comparison.getRightArtifact().getPosition()) {
					sortedComparison.add(comparison);
				}

			}
		}
		for (Comparison<Node> comparison : comparisons) {

			if (!sortedComparison.contains(comparison)) {
				sortedComparison.add(comparison);
			}
		}
		return sortedComparison;
	}

	public List<ActionScope> generateEditScript(Node tree1, Node tree2) {
		cloneMapping = new HashMap<Node, Node>();
		cloneTree1 = tree1.cloneNode();
		cloneTree2 = tree2.cloneNode();
		mapping = new HashMap<Node, Node>();
		createCloneMapping(tree1, cloneTree1);
		buildMapping(compareEngine.compare(cloneTree1, cloneTree2));

		List<ActionScope> actionScopes = new ArrayList<ActionScope>();
		Comparison<Node> comparison = compareEngine.compare(cloneTree1, cloneTree2);

		getActions(comparison, comparison.getChildComparisons(), actionScopes, new LinkedList<Comparison<Node>>());
		return actionScopes;
	}

	public void getActions(Comparison<Node> parentComparison, List<Comparison<Node>> childComparisons,
			List<ActionScope> actions, Queue<Comparison<Node>> queue) {

		queue.addAll(childComparisons);

		childComparisons = sortComparisons(childComparisons);
		Iterator<Comparison<Node>> iterator = childComparisons.iterator();

		// perform breadth-first traversal
		while (iterator.hasNext()) {
			Comparison<Node> comparison = iterator.next();

			Node leftArtifact = comparison.getLeftArtifact();
			Node rightArtifact = comparison.getRightArtifact();

			// don't compare nested components, maybe TODO
			if (leftArtifact instanceof Component || rightArtifact instanceof Component) {
				return;
			}

			// new artifact on left side
			if (leftArtifact == null) {

				int position = SynchronizationUtil.getPositionOfLastCommonPredecessor(
						parentComparison.getLeftArtifact(), parentComparison.getRightArtifact(), rightArtifact);

				Node correspondingNode = cloneMapping.get(parentComparison.getLeftArtifact());
				Insert insert = new Insert(rightArtifact.cloneNode(), correspondingNode, position + 1);
				actions.add(new ActionScope(insert, true));

				Node rightArtifactClone = rightArtifact.cloneNode();
				parentComparison.getLeftArtifact().addChildAtPosition(rightArtifactClone, position + 1);

			}
			// deleted artifact on right side
			else if (rightArtifact == null) {
				Node correspondingNode = cloneMapping.get(leftArtifact);

				Delete delete = new Delete(correspondingNode);
				actions.add(new ActionScope(delete, true));

				int position = SynchronizationUtil.getPositionOfLastCommonPredecessor(
						parentComparison.getRightArtifact(), parentComparison.getLeftArtifact(), leftArtifact);

				Node leftArtifactClone = leftArtifact.cloneNode();
				parentComparison.getRightArtifact().addChildAtPosition(leftArtifactClone, position + 1);

			}
			// artifacts are matched but different in content
			else {

				// check for update
				Node cloneLeft = leftArtifact.cloneNode();
				cloneLeft.getChildren().clear();
				Node cloneRight = rightArtifact.cloneNode();
				cloneRight.getChildren().clear();
				if (compareEngine.compare(cloneLeft, cloneRight).getSimilarity() != 1.0) {
					Node correspondingNode = cloneMapping.get(leftArtifact);

					Update update = new Update(correspondingNode, rightArtifact.cloneNode());
					actions.add(new ActionScope(update, true));

				}

			}

		}

		Comparison<Node> comparison = compareEngine.compare(parentComparison.getLeftArtifact(),
				parentComparison.getRightArtifact());

		List<Node> sequence = SynchronizationUtil.findLongestCommonSubsequence(
				parentComparison.getLeftArtifact().getChildren(), parentComparison.getRightArtifact().getChildren(),
				comparison.getChildComparisons());

		List<Node> newList = new ArrayList<Node>(parentComparison.getLeftArtifact().getChildren());

		for (Node child : newList) {
			if (!sequence.contains(child)) {

				Node partner = getPartner(child, comparison.getChildComparisons());
				Node correspondingNode = cloneMapping.get(child);

				int position = SynchronizationUtil.getPositionOfLastCommonPredecessor(
						parentComparison.getLeftArtifact(), parentComparison.getRightArtifact(), partner) + 1;

				Move move = new Move(correspondingNode, correspondingNode.getParent(), position);
				actions.add(new ActionScope(move, true));

				int previousPosition = child.getPosition();

				parentComparison.getLeftArtifact().getChildren().remove(previousPosition);
				parentComparison.getLeftArtifact().addChildAtPosition(child, position);

			}

		}

		if (!queue.isEmpty()) {
			Comparison<Node> nextElement = queue.remove();
			if (nextElement.getLeftArtifact() != null && nextElement.getRightArtifact() != null) {

				getActions(nextElement, nextElement.getChildComparisons(), actions, queue);
			}

		}

	}

	private Node getPartner(Node node, List<Comparison<Node>> comparisons) {
		for (Comparison<Node> comparison : comparisons) {
			if (comparison.getLeftArtifact() == node) {
				return comparison.getRightArtifact();
			} else if (comparison.getRightArtifact() == node) {
				return comparison.getLeftArtifact();
			}
		}
		return null;
	}

}
