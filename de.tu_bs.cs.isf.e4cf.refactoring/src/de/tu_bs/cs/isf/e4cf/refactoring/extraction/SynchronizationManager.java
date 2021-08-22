package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.refactoring.controllers.SynchronizationViewController;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Action;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ConfigurationComparison;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Delete;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Insert;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Move;
import de.tu_bs.cs.isf.e4cf.refactoring.model.MultiSet;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Update;
import de.tu_bs.cs.isf.e4cf.refactoring.util.SynchronizationUtil;

@Singleton
@Creatable
public class SynchronizationManager {

	private SynchronizationViewController synchronizationViewController;
	private CompareEngineHierarchical compareEngine;

	public SynchronizationManager() {
		synchronizationViewController = new SynchronizationViewController();
		compareEngine = new CompareEngineHierarchical(new SortingMatcher(), new MetricImpl("test"));
	}

	public boolean showSynchronizationView(List<ConfigurationComparison> configurationComparisons,
			Map<ActionScope, List<ActionScope>> synchronizationScopes) {
		synchronizationViewController.showView(synchronizationScopes, configurationComparisons);
		return synchronizationViewController.isResult();

	}

	public void applySynchronizations(List<ConfigurationComparison> configurationComparisons,
			Map<ActionScope, List<ActionScope>> synchronizationScopes, Map<Component, MultiSet> multisets) {

		for (ConfigurationComparison configurationComparison : configurationComparisons) {
			MultiSet multiset = multisets.get(configurationComparison.getComponent1());
			applySelectedSynchronizations(configurationComparison.getSynchronizationScopes(), multiset);
		}

	}

	public Map<ActionScope, List<ActionScope>> determineSynchronizations(
			List<ConfigurationComparison> configurationComparisons, Map<Component, MultiSet> multiSets) {

		Map<ActionScope, List<ActionScope>> synchronizationMap = new HashMap<ActionScope, List<ActionScope>>();
		for (ConfigurationComparison configurationComparison : configurationComparisons) {
			Component component = configurationComparison.getComponent1();
			Map<Node, Set<Node>> multiSet = multiSets.get(component).getMultiSet();

			List<ActionScope> synchronizationScopes = new ArrayList<ActionScope>();
			for (ActionScope actionScope : configurationComparison.getActionScopes()) {

				if (actionScope.isApply()) {

					synchronizationMap.put(actionScope, new ArrayList<ActionScope>());
					Action action = actionScope.getAction();

					if (action instanceof Insert) {
						Insert insert = (Insert) action;

						if (multiSet.containsKey(insert.getY())) {
							for (Node node : multiSet.get(insert.getY())) {

								ActionScope synchronizationScope = new ActionScope(
										new Insert(insert.getX(), node, insert.getPosition()), true);
								synchronizationScope.getAction().setSourceNode(insert.getY());
								synchronizationScopes.add(synchronizationScope);
								synchronizationMap.get(actionScope).add(synchronizationScope);

							}
						}
					} else if (action instanceof Update) {
						Update update = (Update) action;

						if (multiSet.containsKey(update.getX())) {
							for (Node node : multiSet.get(update.getX())) {

								ActionScope synchronizationScope = new ActionScope(
										new Update(node, update.getY().cloneNode()), true);
								synchronizationScope.getAction().setSourceNode(update.getX());
								synchronizationScopes.add(synchronizationScope);
								synchronizationMap.get(actionScope).add(synchronizationScope);
							}
						}
					} else if (action instanceof Delete) {
						Delete delete = (Delete) action;

						if (multiSet.containsKey(delete.getX())) {
							for (Node node : multiSet.get(delete.getX())) {
								ActionScope synchronizationScope = new ActionScope(new Delete(node), true);
								synchronizationScope.getAction().setSourceNode(delete.getX());
								synchronizationScopes.add(synchronizationScope);
								synchronizationMap.get(actionScope).add(synchronizationScope);
							}
						}
					} else if (action instanceof Move) {
						Move move = (Move) action;

						if (multiSet.containsKey(move.getY())) {
							for (Node node : multiSet.get(move.getX())) {

								ActionScope synchronizationScope = new ActionScope(
										new Move(node, node.getParent(), move.getPosition()), true);
								synchronizationScope.getAction().setSourceNode(move.getY());
								synchronizationScopes.add(synchronizationScope);
								synchronizationMap.get(actionScope).add(synchronizationScope);

							}
						}
					}

				}

			}
			configurationComparison.setSynchronizationScopes(synchronizationScopes);
		}
		return synchronizationMap;

	}

	private void applySelectedSynchronizations(List<ActionScope> actionScopes, MultiSet multiSet) {

		// apply actions: first perform insert actions even if they are not applied,
		// then
		// move actions and delete actions.
		// Remove not applied add actions in the end.
		actionScopes = sortSynchronizationScopes(actionScopes);

		for (ActionScope actionScope : actionScopes) {
			Action action = actionScope.getAction();

			if (action instanceof Insert) {
				Insert insert = (Insert) action;

				Node z = insert.getSourceNode();

				Comparison<Node> comparison = buildComparison(insert.getY(), z, multiSet.getMultiSet());

				int position = SynchronizationUtil.getPositionOfLastCommonPredecessor(insert.getY(), z, insert.getX(),
						comparison);
				position++;

				int position2 = SynchronizationUtil.getPositionOfLastCommonPredecessor(insert.getY(), z, insert.getX(),
						comparison);
				position2++;

				if (position != position2) {
					System.out.print("asd");
				}

				insert.setPosition(position);
				Node savedNode = insert.getX();

				insert.setX(insert.getX().cloneNode());
				insert.apply();

				multiSet.add(insert.getX());
				multiSet.addTo(insert.getX(), savedNode);

			} else if (action instanceof Move) {

				if (actionScope.isApply()) {
					Move move = (Move) action;
					Node x = move.getY();
					Node z = move.getSourceNode();
					align(x, z, multiSet);

				}

			} else if (action instanceof Delete) {

				if (actionScope.isApply()) {

					Delete delete = (Delete) action;
					delete.apply();

				}
			} else if (action instanceof Update) {

				if (actionScope.isApply()) {
					Update update = (Update) action;
					update.apply();
				}
			}
		}

		// Remove unapplied insert actions
		for (ActionScope actionScope : actionScopes) {

			if (actionScope.getAction() instanceof Insert && !actionScope.isApply()) {
				Insert insert = (Insert) actionScope.getAction();
				insert.undo();
				multiSet.remove(insert.getX());
			}

			if (actionScope.getAction() instanceof Delete) {
				Delete delete = (Delete) actionScope.getAction();
				multiSet.remove(delete.getX());
				multiSet.remove(delete.getSourceNode());
			}
		}

	}

	private void align(Node x, Node z, MultiSet multiSet) {
		Node xClone = x.cloneNode();
		Node zClone = z.cloneNode();

		Comparison<Node> createdComparison = buildComparison(x, z, multiSet.getMultiSet());
		Map<Node, Node> mappingX = buildMapping(x, xClone);
		Map<Node, Node> mappingZ = buildMapping(z, zClone);
		adaptComparison(xClone, zClone, mappingX, mappingZ, createdComparison);

		List<Node> addedNodes = adapt(xClone, zClone);

		List<Node> sequence = SynchronizationUtil.findLongestCommonSubsequence(xClone.getChildren(),
				zClone.getChildren(), createdComparison.getChildComparisons());

		for (Node child : xClone.getChildren()) {
			if (!sequence.contains(child)) {

				Node partner = getPartner(child, createdComparison.getChildComparisons());

				if (partner != null) {
					int previousPosition = child.getPosition();
					int newPosition = partner.getPosition();

					xClone.getChildren().remove(previousPosition);
					xClone.addChildAtPosition(child, newPosition);

				}

			}

		}

		for (Node addedNode : addedNodes) {
			xClone.getChildren().remove(addedNode);
		}

		List<Node> newChildren = new ArrayList<Node>();
		for (Node child : xClone.getChildren()) {
			newChildren.add(mappingX.get(child));
		}

		x.getChildren().clear();
		x.getChildren().addAll(newChildren);
	}

	private List<ActionScope> sortSynchronizationScopes(List<ActionScope> actionScopes) {

		// sort action scopes: INSERT, MOVE/UPDATE, DELETE
		List<ActionScope> sortedActionScopes = new ArrayList<ActionScope>();
		for (ActionScope actionScope : actionScopes) {
			if (actionScope.getAction() instanceof Insert) {
				sortedActionScopes.add(actionScope);
			}
		}

		for (ActionScope actionScope : actionScopes) {
			if (actionScope.getAction() instanceof Move || actionScope.getAction() instanceof Update) {
				sortedActionScopes.add(actionScope);
			}
		}
		for (ActionScope actionScope : actionScopes) {
			if (actionScope.getAction() instanceof Delete) {
				sortedActionScopes.add(actionScope);
			}
		}

		return sortedActionScopes;

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

	private Map<Node, Node> buildMapping(Node x, Node xClone) {
		Map<Node, Node> mapping = new HashMap<Node, Node>();
		for (int i = 0; i < x.getChildren().size(); i++) {
			mapping.put(xClone.getChildren().get(i), x.getChildren().get(i));
			mapping.put(x.getChildren().get(i), xClone.getChildren().get(i));
		}
		return mapping;

	}

	private void adaptComparison(Node xClone, Node zClone, Map<Node, Node> mapX, Map<Node, Node> mapZ,
			Comparison<Node> comparison) {

		comparison.setLeftArtifact(xClone);
		comparison.setRightArtifact(zClone);

		for (Comparison<Node> childComparison : comparison.getChildComparisons()) {

			if (childComparison.getLeftArtifact() != null) {
				childComparison.setLeftArtifact(mapX.get(childComparison.getLeftArtifact()));
			}

			if (childComparison.getRightArtifact() != null) {
				childComparison.setRightArtifact(mapZ.get(childComparison.getRightArtifact()));
			}
		}

	}

	private List<Node> adapt(Node x, Node y) {

		List<Node> addedNodes = new ArrayList<Node>();
		Comparison<Node> parentComparison = compareEngine.compare(x, y);

		for (Comparison<Node> childComparison : parentComparison.getChildComparisons()) {

			Node leftArtifact = childComparison.getLeftArtifact();
			Node rightArtifact = childComparison.getRightArtifact();
			if (leftArtifact == null) {

				int position = SynchronizationUtil.getPositionOfLastCommonPredecessor(
						parentComparison.getLeftArtifact(), parentComparison.getRightArtifact(), rightArtifact);

				Node rightArtifactClone = rightArtifact.cloneNode();
				addedNodes.add(rightArtifactClone);
				parentComparison.getLeftArtifact().addChildAtPosition(rightArtifactClone, position + 1);

			}
			// deleted artifact on right side
			else if (rightArtifact == null) {

				int position = SynchronizationUtil.getPositionOfLastCommonPredecessor(
						parentComparison.getRightArtifact(), parentComparison.getLeftArtifact(), leftArtifact);

				Node leftArtifactClone = leftArtifact.cloneNode();
				parentComparison.getRightArtifact().addChildAtPosition(leftArtifactClone, position + 1);

			}

		}

		return addedNodes;

	}

	private Comparison<Node> buildComparison(Node x, Node z, Map<Node, Set<Node>> multiSet) {

		List<Comparison<Node>> comparisons = new ArrayList<Comparison<Node>>();
		for (Node childX : x.getChildren()) {
			if (multiSet.containsKey(childX)) {
				Set<Node> correspondingNodes = multiSet.get(childX);
				boolean hasPartner = false;
				for (Node childZ : z.getChildren()) {
					if (correspondingNodes.contains(childZ)) {
						Comparison<Node> comparison = new NodeComparison(childX, childZ);
						comparisons.add(comparison);
						hasPartner = true;
						break;
					}
				}

				if (!hasPartner) {
					Comparison<Node> comparison = new NodeComparison(childX, null);
					comparisons.add(comparison);
				}
			}
		}

		for (Node childZ : z.getChildren()) {
			boolean hasPartner = false;
			for (Comparison<Node> comparison : comparisons) {

				if (comparison.getRightArtifact() == childZ) {
					hasPartner = true;
				}
			}
			if (!hasPartner) {
				Comparison<Node> comparison = new NodeComparison(null, childZ);
				comparisons.add(comparison);
			}

		}

		Comparison<Node> comparison = new NodeComparison(x, z);
		comparison.getChildComparisons().addAll(comparisons);

		return comparison;

	}

}
