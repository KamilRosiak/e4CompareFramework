package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
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
import de.tu_bs.cs.isf.e4cf.refactoring.model.CloneModel;
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

	@Inject
	public SynchronizationManager(SynchronizationViewController synchronizationViewController) {
		compareEngine = new CompareEngineHierarchical(new SortingMatcher(), new MetricImpl("test"));
		this.synchronizationViewController = synchronizationViewController;
	}

	public boolean showSynchronizationView(CloneModel cloneModel,
			Map<ActionScope, List<ActionScope>> synchronizationScopes) {
		synchronizationViewController.showView(synchronizationScopes, cloneModel);
		return synchronizationViewController.isResult();

	}

	public void synchronize(Map<ActionScope, List<ActionScope>> synchronizationScopes, CloneModel cloneModel) {
		List<ActionScope> synchronizationScopesList = new ArrayList<ActionScope>();

		for (List<ActionScope> list : synchronizationScopes.values()) {
			synchronizationScopesList.addAll(list);
		}

		synchronize(synchronizationScopesList, cloneModel);
	}

	public Map<ActionScope, List<ActionScope>> translateActionsToSynchronizations(List<ActionScope> actionScopes,
			CloneModel cloneModel) {

		Map<ActionScope, List<ActionScope>> synchronizationMap = new HashMap<ActionScope, List<ActionScope>>();

		for (ActionScope actionScope : actionScopes) {
			if (actionScope.isApply()) {
				synchronizationMap.put(actionScope, new ArrayList<ActionScope>());
				Action action = actionScope.getAction();
				if (action instanceof Insert) {
					Insert insert = (Insert) action;

					Component component = actionScope.getComponent();
					Map<Node, Set<Node>> multiSet = cloneModel.getMultiSets().get(component).getMultiSet();

					if (multiSet.containsKey(insert.getY())) {
						for (Node node : multiSet.get(insert.getY())) {
							ActionScope synchronizationScope = new ActionScope(
									new Insert(insert.getX(), node, insert.getPosition()), true);
							synchronizationScope.getAction().setSourceNode(insert.getY());
							synchronizationMap.get(actionScope).add(synchronizationScope);
							synchronizationScope.setComponent(actionScope.getComponent());

						}
					}
				} else if (action instanceof Update) {
					Update update = (Update) action;

					Component component = actionScope.getComponent();
					Map<Node, Set<Node>> multiSet = cloneModel.getMultiSets().get(component).getMultiSet();

					if (multiSet.containsKey(update.getX())) {
						for (Node node : multiSet.get(update.getX())) {

							ActionScope synchronizationScope = new ActionScope(
									new Update(node, update.getY().cloneNode()), true);
							synchronizationScope.getAction().setSourceNode(update.getX());
							synchronizationMap.get(actionScope).add(synchronizationScope);
							synchronizationScope.setComponent(actionScope.getComponent());
						}
					}
				} else if (action instanceof Delete) {
					Delete delete = (Delete) action;

					Component component = actionScope.getComponent();
					Map<Node, Set<Node>> multiSet = cloneModel.getMultiSets().get(component).getMultiSet();

					if (multiSet.containsKey(delete.getX())) {
						for (Node node : multiSet.get(delete.getX())) {
							ActionScope synchronizationScope = new ActionScope(new Delete(node), true);
							synchronizationScope.getAction().setSourceNode(delete.getX());
							synchronizationMap.get(actionScope).add(synchronizationScope);
							synchronizationScope.setComponent(actionScope.getComponent());
						}
					}
				} else if (action instanceof Move) {
					Move move = (Move) action;

					Component component = actionScope.getComponent();
					Map<Node, Set<Node>> multiSet = cloneModel.getMultiSets().get(component).getMultiSet();

					if (multiSet.containsKey(move.getY())) {
						if (multiSet.containsKey(move.getX())) {
							for (Node node : multiSet.get(move.getX())) {
								ActionScope synchronizationScope = new ActionScope(
										new Move(node, node.getParent(), move.getPosition()), true);
								synchronizationScope.getAction().setSourceNode(move.getY());
								synchronizationMap.get(actionScope).add(synchronizationScope);
								synchronizationScope.setComponent(actionScope.getComponent());
							}
						}

					}
				}

			}

		}
		return synchronizationMap;

	}

	private void synchronize(List<ActionScope> actionScopes, CloneModel cloneModel) {

		actionScopes = sortSynchronizations(actionScopes);

		for (ActionScope actionScope : actionScopes) {
			Action action = actionScope.getAction();

			if (action instanceof Insert) {
				Insert insert = (Insert) action;

				// restore comparison with multisets
				Node sourceNode = insert.getSourceNode();
				Component component = actionScope.getComponent();
				MultiSet multiSet = cloneModel.getMultiSets().get(component);

				Comparison<Node> comparison = restoreComparison(insert.getY(), sourceNode, multiSet.getMultiSet());
				int position = SynchronizationUtil.getPositionOfCommonPredecessor(insert.getY(), sourceNode,
						insert.getX(), comparison) + 1;

				insert.setPosition(position);
				Node savedNode = insert.getX();
				insert.setX(insert.getX().cloneNode());
				insert.apply();

				// update multiset
				multiSet.add(insert.getX());
				multiSet.addAndExpand(insert.getX(), savedNode);

			} else if (action instanceof Move) {

				if (actionScope.isApply()) {
					Move move = (Move) action;
					Node y = move.getY();
					Node sourceNode = move.getSourceNode();
					Component component = cloneModel.getComponent(sourceNode);
					MultiSet multiSet = cloneModel.getMultiSets().get(component);

					alignNodes(y, sourceNode, multiSet);

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

		// Remove unapplied insert actions and update multisets
		for (ActionScope actionScope : actionScopes) {

			if (actionScope.getAction() instanceof Insert && !actionScope.isApply()) {
				Insert insert = (Insert) actionScope.getAction();
				Component component = actionScope.getComponent();
				MultiSet multiSet = cloneModel.getMultiSets().get(component);

				insert.undo();

				multiSet.remove(insert.getX());

			}
			if (actionScope.getAction() instanceof Delete) {
				Delete delete = (Delete) actionScope.getAction();
				Component component = actionScope.getComponent();
				MultiSet multiSet = cloneModel.getMultiSets().get(component);

				multiSet.remove(delete.getX());
				multiSet.remove(delete.getSourceNode());
			}
		}

	}

	private void alignNodes(Node node1, Node node2, MultiSet multiSet) {
		Node node1Clone = node1.cloneNode();
		Node node2Clone = node2.cloneNode();

		Comparison<Node> restoredComparison = restoreComparison(node1, node2, multiSet.getMultiSet());
		Map<Node, Node> mapping1 = buildMapping(node1, node1Clone);
		Map<Node, Node> mapping2 = buildMapping(node2, node2Clone);
		adaptComparison(node1Clone, node2Clone, mapping1, mapping2, restoredComparison);

		List<Node> addedNodes = adaptNodes(node1Clone, node2Clone);

		List<Node> sequence = SynchronizationUtil.findLongestCommonSubsequence(node1Clone.getChildren(),
				node2Clone.getChildren(), restoredComparison.getChildComparisons());

		List<Node> newList = new ArrayList<Node>(node1Clone.getChildren());

		for (Node child : newList) {
			if (!sequence.contains(child)) {
				Node partner = getPartner(child, restoredComparison.getChildComparisons());

				if (partner != null) {
					int previousPosition = child.getPosition();

					int position = SynchronizationUtil.getPositionOfCommonPredecessor(node1Clone, node2Clone, partner,
							restoredComparison) + 1;

					node1Clone.getChildren().remove(previousPosition);
					node1Clone.addChildAtPosition(child, position);

				}

			}

		}

		for (Node addedNode : addedNodes) {
			node1Clone.getChildren().remove(addedNode);
		}

		List<Node> newChildren = new ArrayList<Node>();
		for (Node child : node1Clone.getChildren()) {
			newChildren.add(mapping1.get(child));
		}

		node1.getChildren().clear();
		node1.getChildren().addAll(newChildren);
	}

	private List<ActionScope> sortSynchronizations(List<ActionScope> actionScopes) {

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

	private Map<Node, Node> buildMapping(Node node1, Node node2) {
		Map<Node, Node> mapping = new HashMap<Node, Node>();
		for (int i = 0; i < node1.getChildren().size(); i++) {
			mapping.put(node2.getChildren().get(i), node1.getChildren().get(i));
			mapping.put(node1.getChildren().get(i), node2.getChildren().get(i));
		}
		return mapping;

	}

	private void adaptComparison(Node node1, Node node2, Map<Node, Node> mapping1, Map<Node, Node> mapping2,
			Comparison<Node> comparison) {

		comparison.setLeftArtifact(node1);
		comparison.setRightArtifact(node2);

		for (Comparison<Node> childComparison : comparison.getChildComparisons()) {

			if (childComparison.getLeftArtifact() != null) {
				childComparison.setLeftArtifact(mapping1.get(childComparison.getLeftArtifact()));
			}

			if (childComparison.getRightArtifact() != null) {
				childComparison.setRightArtifact(mapping2.get(childComparison.getRightArtifact()));
			}
		}

	}

	private List<Node> adaptNodes(Node node1, Node node2) {

		List<Node> addedNodes = new ArrayList<Node>();
		Comparison<Node> parentComparison = compareEngine.compare(node1, node2);

		for (Comparison<Node> childComparison : parentComparison.getChildComparisons()) {

			Node leftArtifact = childComparison.getLeftArtifact();
			Node rightArtifact = childComparison.getRightArtifact();
			if (leftArtifact == null) {

				int position = SynchronizationUtil.getPositionOfCommonPredecessor(parentComparison.getLeftArtifact(),
						parentComparison.getRightArtifact(), rightArtifact);

				Node rightArtifactClone = rightArtifact.cloneNode();
				addedNodes.add(rightArtifactClone);
				parentComparison.getLeftArtifact().addChildAtPosition(rightArtifactClone, position + 1);

			}
			// deleted artifact on right side
			else if (rightArtifact == null) {

				int position = SynchronizationUtil.getPositionOfCommonPredecessor(parentComparison.getRightArtifact(),
						parentComparison.getLeftArtifact(), leftArtifact);

				Node leftArtifactClone = leftArtifact.cloneNode();
				parentComparison.getRightArtifact().addChildAtPosition(leftArtifactClone, position + 1);

			}

		}

		return addedNodes;

	}

	private Comparison<Node> restoreComparison(Node node1, Node node2, Map<Node, Set<Node>> multiSet) {

		List<Comparison<Node>> comparisons = new ArrayList<Comparison<Node>>();
		for (Node child1 : node1.getChildren()) {
			if (multiSet.containsKey(child1)) {
				Set<Node> correspondingNodes = multiSet.get(child1);
				boolean hasPartner = false;
				for (Node child2 : node2.getChildren()) {
					if (correspondingNodes.contains(child2)) {
						Comparison<Node> comparison = new NodeComparison(child1, child2);
						comparisons.add(comparison);
						hasPartner = true;
						break;
					}
				}

				if (!hasPartner) {
					Comparison<Node> comparison = new NodeComparison(child1, null);
					comparisons.add(comparison);
				}
			}
		}

		for (Node child2 : node2.getChildren()) {
			boolean hasPartner = false;
			for (Comparison<Node> comparison : comparisons) {
				if (comparison.getRightArtifact() == child2) {
					hasPartner = true;
				}
			}
			if (!hasPartner) {
				Comparison<Node> comparison = new NodeComparison(null, child2);
				comparisons.add(comparison);
			}

		}

		Comparison<Node> comparison = new NodeComparison(node1, node2);
		comparison.getChildComparisons().addAll(comparisons);

		return comparison;

	}

}
