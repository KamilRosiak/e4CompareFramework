package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import com.google.common.collect.Lists;
import com.sun.org.apache.xpath.internal.compiler.PsuedoNames;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparison.impl.NodeComparison;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.refactoring.controllers.ActionViewController;
import de.tu_bs.cs.isf.e4cf.refactoring.controllers.SynchronizationViewController;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Action;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionType;
import de.tu_bs.cs.isf.e4cf.refactoring.model.AddAction;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ComponentLayer;
import de.tu_bs.cs.isf.e4cf.refactoring.model.DeleteAction;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ExtractionResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.MoveAction;
import de.tu_bs.cs.isf.e4cf.refactoring.model.SynchronizationResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.SynchronizationScope;
import de.tu_bs.cs.isf.e4cf.refactoring.model.UpdateAction;

@Singleton
@Creatable
public class SynchronizationEngine {

	public final float COMPONENT_THRESHOLD = 0.9f;

	private CompareEngineHierarchical compareEngine;

	private SynchronizationUnitEngine synchronizationUnitEngine;
	private SortingMatcher sortingMatcher;
	private SynchronizationViewController synchronizationViewController;
	private ActionViewController actionViewController;

	public SynchronizationEngine() {
		sortingMatcher = new SortingMatcher();
		compareEngine = new CompareEngineHierarchical(sortingMatcher, new MetricImpl("test"));

		synchronizationUnitEngine = new SynchronizationUnitEngine();
		synchronizationViewController = new SynchronizationViewController();
		actionViewController = new ActionViewController();
	}

	public SynchronizationResult synchronize(ExtractionResult result1, ExtractionResult result2) {

		System.out.println("");
		System.out.println("|--------- Synchronize components ---------|");

		List<Component> components = new ArrayList<Component>();
		List<Comparison<Node>> componentComparisons = matchComponents(result1.getComponents(), result2.getComponents());

		Map<Component, Component> matchedComponents = new HashMap<Component, Component>();

		Map<Component, List<ActionScope>> componentToActionScopes = new HashMap<Component, List<ActionScope>>();
		Map<ActionScope, Configuration> actionToConfiguration = new HashMap<ActionScope, Configuration>();

		Map<Component, List<Configuration>> configurationsToAdd = new HashMap<Component, List<Configuration>>();
		Map<Configuration, Configuration> configurationsToUpdate = new HashMap<Configuration, Configuration>();
		Map<Component, List<Configuration>> configurationsToDelete = new HashMap<Component, List<Configuration>>();
		Map<ActionScope, List<SynchronizationScope>> actionToSynchronizationScopes = new HashMap<ActionScope, List<SynchronizationScope>>();

		Map<Node, Integer> pseudoNodeToPosition = new HashMap<Node, Integer>();

		for (Comparison<Node> componentComparison : componentComparisons) {

			if (componentComparison.getLeftArtifact() != null && componentComparison.getRightArtifact() != null
					&& componentComparison.getSimilarity() > COMPONENT_THRESHOLD) {
				// component match
				Component component1 = (Component) componentComparison.getLeftArtifact();
				Component component2 = (Component) componentComparison.getRightArtifact();

				matchedComponents.put(component1, component2);

				List<Comparison<Node>> configurationComparisons = matchConfigurations(component1.getConfigurations(),
						component2.getConfigurations());

				for (Comparison<Node> configurationComparison : configurationComparisons) {

					if (configurationComparison.getLeftArtifact() != null
							&& configurationComparison.getRightArtifact() != null) {
						// configuration match: update configuration

						Configuration configuration1 = (Configuration) configurationComparison.getLeftArtifact();
						Configuration configuration2 = (Configuration) configurationComparison.getRightArtifact();

						configurationsToUpdate.put(configuration1, configuration2);

					} else if (configurationComparison.getLeftArtifact() != null
							&& configurationComparison.getRightArtifact() == null) {
						// no configuration match on right side: delete saved configuration
						Configuration configuration1 = (Configuration) configurationComparison.getLeftArtifact();

						if (!configurationsToDelete.containsKey(component1)) {
							configurationsToDelete.put(component1, new ArrayList<Configuration>());
						}

						configurationsToDelete.get(component1).add(configuration1);

					} else {
						// no configuration match on left side: add configuration
						Configuration configuration2 = (Configuration) configurationComparison.getLeftArtifact();

						if (!configurationsToAdd.containsKey(component1)) {
							configurationsToAdd.put(component1, new ArrayList<Configuration>());
						}

						configurationsToAdd.get(component1).add(configuration2);

					}

				}

				// determine actions between matched configurations
				List<ActionScope> actionScopes = new ArrayList<ActionScope>();
				for (Entry<Configuration, Configuration> configurationPair : configurationsToUpdate.entrySet()) {

					List<ActionScope> localActionScopes = new ArrayList<ActionScope>();
					Comparison<Node> configurationComparison = compareEngine.compare(configurationPair.getKey(),
							configurationPair.getValue());

					if (configurationComparison.getSimilarity() != 1.0) {
						List<Node> pseudoNodes = new ArrayList<Node>();
						getActions(configurationComparison, configurationComparison.getChildComparisons().iterator(),
								localActionScopes, pseudoNodes);

						// remove pseudo nodes only added for matching purposes
						for (Node pseudoNode : pseudoNodes) {
							pseudoNodeToPosition.put(pseudoNode, pseudoNode.getPosition());
							pseudoNode.getParent().getChildren().remove(pseudoNode);

						}

						actionScopes.addAll(localActionScopes);
						for (ActionScope actionScope : localActionScopes) {
							actionToConfiguration.put(actionScope, configurationPair.getValue());
						}
					}

				}
				componentToActionScopes.put(component1, actionScopes);
				components.add(component1);

			} else if (componentComparison.getLeftArtifact() != null
					&& componentComparison.getRightArtifact() == null) {
				// no component match on right side: add component
				Component component1 = (Component) componentComparison.getLeftArtifact();
				components.add(component1);
			} else {
				// no component match on left side: add component
				Component component2 = (Component) componentComparison.getRightArtifact();
				components.add(component2);
			}

		}

		// visualize actions
		actionViewController.showView(componentToActionScopes);
		if (!actionViewController.isResult()) {
			return null;
		}

		// delete unmatched configurations
		for (Entry<Component, List<Configuration>> entry : configurationsToDelete.entrySet()) {
			Component component = entry.getKey();

			for (Configuration configuration : entry.getValue()) {

				// remove from synchronization unit
				synchronizationUnitEngine.removeFromSynchronizationUnit(configuration.getChildren().get(0),
						component.getSynchronizationUnit());

				Map<Node, Map<Integer, Configuration>> nodeComponentRelation = component.getNodeComponentRelation();
				for (Entry<Node, Map<Integer, Configuration>> nodeComponentRelationEntry : nodeComponentRelation
						.entrySet()) {

					Node node = nodeComponentRelationEntry.getKey();
					for (Entry<Integer, Configuration> configurationPositionEntry : nodeComponentRelationEntry
							.getValue().entrySet()) {

						if (configurationPositionEntry.getValue() == configuration) {
							nodeComponentRelation.get(node).put(configurationPositionEntry.getKey(),
									component.getConfigurations().get(0));
						}

					}

				}

				component.getChildren().remove(configuration);
			}
		}

		// Apply actions
		for (Entry<Component, List<ActionScope>> entry : componentToActionScopes.entrySet()) {
			Component component = entry.getKey();

			List<ActionScope> actionScopes = new ArrayList<ActionScope>(componentToActionScopes.get(component));
			actionScopes = sortActionScopes(actionScopes);

			// Add configurations of actions which are not applied
			for (ActionScope actionScope : actionScopes) {
				if (!actionScope.apply()) {
					Configuration sourceConfiguration = actionToConfiguration.get(actionScope);

					if (!configurationsToAdd.containsKey(component)) {
						configurationsToAdd.put(component, new ArrayList<Configuration>());
					}
					if (!configurationsToAdd.get(component).contains(sourceConfiguration)) {
						configurationsToAdd.get(component).add(sourceConfiguration);
					}

				}
			}

			applyActions(actionScopes, pseudoNodeToPosition);

			// determine synchronization scopes
			for (ActionScope actionScope : actionScopes) {
				List<SynchronizationScope> synchronizationScopes = new ArrayList<SynchronizationScope>();
				if (component.getSynchronizationUnit().containsKey(actionScope.getAction().getAffectedNode())) {

					for (Node node : component.getSynchronizationUnit()
							.get(actionScope.getAction().getAffectedNode())) {
						synchronizationScopes.add(new SynchronizationScope(node, true));
					}
				}
				actionToSynchronizationScopes.put(actionScope, synchronizationScopes);
			}

		}

		System.out.println("Matched configurations: " + matchedComponents.size());
		System.out.println("Configurations to add: " + configurationsToAdd.size());
		System.out.println("Configurations to delete: " + configurationsToDelete.size());
		System.out.println("Actions: " + actionToSynchronizationScopes.size());

		// visualize synchronizations
		synchronizationViewController.showView(actionToSynchronizationScopes, componentToActionScopes);
		if (!synchronizationViewController.isResult()) {
			return null;
		}

		// apply synchronizations
		applySynchronizations(actionToSynchronizationScopes);

		// add new configurations
		for (Entry<Component, List<Configuration>> entry : configurationsToAdd.entrySet()) {
			Component component = entry.getKey();

			for (Configuration configuration : entry.getValue()) {

				Component oldComponent = (Component) configuration.getParent();
				Map<Node, Map<Integer, Configuration>> oldNodeComponentRelation = oldComponent
						.getNodeComponentRelation();

				Map<Node, Map<Integer, Configuration>> newNodeComponentRelation = component.getNodeComponentRelation();

				for (Entry<Node, Map<Integer, Configuration>> oldNodeComponentRelationEntry : oldNodeComponentRelation
						.entrySet()) {

					Node node = oldNodeComponentRelationEntry.getKey();

					for (Entry<Integer, Configuration> configurationPositionEntry : oldNodeComponentRelationEntry
							.getValue().entrySet()) {

						if (configurationPositionEntry.getValue() == configuration) {

							if (!newNodeComponentRelation.containsKey(node)) {
								newNodeComponentRelation.put(node, new HashMap<Integer, Configuration>());
							}
							newNodeComponentRelation.get(node).put(configurationPositionEntry.getKey(),
									configurationPositionEntry.getValue());

						}

					}

				}

				component.addChildWithParent(configuration);

			}

		}

		// remove configurations from update list if some actions are not applied
		for (Entry<Component, List<Configuration>> entry : configurationsToAdd.entrySet()) {

			for (Configuration configuration : entry.getValue()) {

				if (configurationsToUpdate.containsValue(configuration)) {

					for (Entry<Configuration, Configuration> configurationMapping : configurationsToUpdate.entrySet()) {

						if (configurationsToUpdate.get(configurationMapping.getKey()) == configuration) {
							configurationsToUpdate.remove(configurationMapping.getKey());
						}
					}

				}

			}

		}

		// transfer node component relation
		for (Entry<Configuration, Configuration> entry : configurationsToUpdate.entrySet()) {

			Configuration configuration1 = entry.getKey();
			Configuration configuration2 = entry.getValue();

			Component component1 = (Component) configuration1.getParent();
			Component component2 = (Component) configuration2.getParent();

			Map<Node, Map<Integer, Configuration>> nodeComponentRelation1 = component1.getNodeComponentRelation();

			for (Entry<Node, Map<Integer, Configuration>> nodeComponentRelationEntry2 : component2
					.getNodeComponentRelation().entrySet()) {

				Node node = nodeComponentRelationEntry2.getKey();

				for (Entry<Integer, Configuration> configurationPositionEntry : nodeComponentRelationEntry2.getValue()
						.entrySet()) {

					if (configurationPositionEntry.getValue() == configuration2) {
						nodeComponentRelationEntry2.getValue().put(configurationPositionEntry.getKey(), configuration1);

					}

				}

				if (!nodeComponentRelation1.containsKey(nodeComponentRelationEntry2.getKey())) {
					nodeComponentRelation1.put(node, nodeComponentRelationEntry2.getValue());
				}

			}

		}

		// update configuration units
		for (Component component : result1.getComponents()) {
			updateSynchronizationUnit(component);
		}
		for (Component component : result2.getComponents()) {
			updateSynchronizationUnit(component);
		}

		// replace component
		for (Tree tree : result2.getTrees()) {
			for (Entry<Component, Component> entry : matchedComponents.entrySet()) {
				replaceComponentInTree(tree.getRoot(), entry.getKey(), entry.getValue());
			}

		}

		List<Tree> trees = new ArrayList<Tree>();
		trees.addAll(result1.getTrees());
		trees.addAll(result2.getTrees());

		Set<ComponentLayer> layers = new HashSet<ComponentLayer>();
		layers.addAll(result1.getLayers());
		layers.addAll(result2.getLayers());

		return new SynchronizationResult(components, trees, new ArrayList<ComponentLayer>(layers));

	}

	private void updateSynchronizationUnit(Component component) {
		Map<Node, Set<Node>> synchronizationUnit = new HashMap<Node, Set<Node>>();

		List<Node> nodes = new ArrayList<Node>();
		for (Configuration configuration : component.getConfigurations()) {
			nodes.add(configuration.getChildren().get(0));
		}

		for (Node node1 : nodes) {
			for (Node node2 : nodes) {
				Comparison<Node> comparison = compareEngine.compare(node1, node2);
				synchronizationUnitEngine.determineSynchronizationUnit(comparison, synchronizationUnit);
			}
		}

		component.setSynchronizationUnit(synchronizationUnit);

	}

	private void replaceComponentInTree(Node node, Component component1, Component component2) {

		for (int i = 0; i < node.getChildren().size(); i++) {
			if (node.getChildren().get(i).equals(component2)) {
				int index = node.getChildren().indexOf(node.getChildren().get(i));
				node.getChildren().remove(index);
				node.getChildren().add(index, component1);
			}
			replaceComponentInTree(node.getChildren().get(i), component1, component2);
		}

	}

	private void applySynchronizations(Map<ActionScope, List<SynchronizationScope>> actionToSynchronizations) {

		List<Node> pseudoNodes = new ArrayList<Node>();

		// Step 1: align node of clones by adding pseudo nodes
		for (Entry<ActionScope, List<SynchronizationScope>> entry : actionToSynchronizations.entrySet()) {

			ActionScope actionScope = entry.getKey();

			for (SynchronizationScope synchronizationScope : entry.getValue()) {

				if (actionScope.getAction().getActionType() == ActionType.ADD) {
					AddAction addAction = (AddAction) actionScope.getAction();

					if (addAction.addAtPositionZero()) {
						pseudoNodes.addAll(alignNodes(addAction.getAffectedNode(), synchronizationScope.getNode()));
					} else {
						pseudoNodes.addAll(alignNodes(addAction.getAffectedNode().getParent(),
								synchronizationScope.getNode().getParent()));
					}

				} else if (actionScope.getAction().getActionType() == ActionType.DELETE) {
					pseudoNodes.addAll(
							alignNodes(actionScope.getAction().getAffectedNode(), synchronizationScope.getNode()));
				}

			}

		}

		// Step 2: compare aligned clones and determine move actions
		List<ActionScope> moveActionScopes = new ArrayList<ActionScope>();
		for (Entry<ActionScope, List<SynchronizationScope>> entry : actionToSynchronizations.entrySet()) {

			ActionScope actionScope = entry.getKey();

			if (actionScope.getAction().getActionType() == ActionType.MOVE && actionScope.apply()) {

				Node leftNode = actionScope.getAction().getAffectedNode();

				for (SynchronizationScope synchronizationScope : entry.getValue()) {

					Node rightNode = synchronizationScope.getNode();

					if (leftNode.getPosition() != rightNode.getPosition()) {
						moveActionScopes.add(new ActionScope(
								new MoveAction(rightNode, leftNode, rightNode.getPosition(), leftNode.getPosition()),
								true));
					}

				}

			}

		}

		// Step 3: apply move actions and remove pseudo nodes
		applyMoveActions(moveActionScopes);
		for (Node pseudoNode : pseudoNodes) {
			pseudoNode.getParent().getChildren().remove(pseudoNode);
		}

		// Step 4: synchronize add, delete and update actions
		for (Entry<ActionScope, List<SynchronizationScope>> entry : actionToSynchronizations.entrySet()) {

			ActionScope actionScope = entry.getKey();
			Action action = actionScope.getAction();
			Node actionNode = action.getActionNode();

			if (actionScope.apply()) {

				if (action.getActionType() == ActionType.ADD) {

					AddAction addAction = (AddAction) action;

					for (SynchronizationScope synchronizationScope : entry.getValue()) {
						if (synchronizationScope.synchronize()) {

							Node node = synchronizationScope.getNode();

							if (addAction.addAtPositionZero()) {
								node.addChildAtPosition(actionNode.cloneNode(), 0);
							} else {
								node.addSiblingAtPosition(actionNode.cloneNode(), node.getPosition() + 1);
							}
						}
					}

				}

				else if (action.getActionType() == ActionType.DELETE) {

					for (SynchronizationScope synchronizationScope : entry.getValue()) {

						if (synchronizationScope.synchronize()) {
							Node node = synchronizationScope.getNode();

							Node nodeToDelete = null;
							float similarity = 0;
							for (Node childNode : node.getChildren()) {

								NodeComparison comparison = compareEngine.compare(childNode, actionNode);
								if (comparison.getResultSimilarity() > similarity) {
									similarity = comparison.getResultSimilarity();
									nodeToDelete = childNode;
								}

							}

							if (nodeToDelete != null) {

								node.getChildren().remove(nodeToDelete);

							}
						}

					}

				} else if (action.getActionType() == ActionType.UPDATE) {

					for (SynchronizationScope synchronizationScope : entry.getValue()) {

						if (synchronizationScope.synchronize()) {
							Node node = synchronizationScope.getNode();

							node.setNodeType(actionNode.getNodeType());

							List<Attribute> attributes = Lists.newArrayList(actionNode.getAttributes());

							node.getAttributes().clear();
							for (Attribute attribute : attributes) {

								node.addAttribute(attribute.cloneAttribute());

							}

						}

					}

				}
			}
		}

	}

	private List<Node> alignNodes(Node leftParent, Node rightParent) {

		List<Node> pseudoNodes = new ArrayList<Node>();
		Comparison<Node> comparison = compareEngine.compare(leftParent, rightParent);
		List<Comparison<Node>> childComparisons = sortComparisons(comparison.getChildComparisons());

		for (Comparison<Node> childComparison : childComparisons) {

			Node leftArtifact = childComparison.getLeftArtifact();
			Node rightArtifact = childComparison.getRightArtifact();

			if (leftArtifact == null) {
				int position = getPositionOfLastCommonPredecessor(comparison.getLeftArtifact(),
						comparison.getRightArtifact(), rightArtifact);

				Node pseudoNode = rightArtifact.cloneNode();
				pseudoNodes.add(pseudoNode);
				comparison.getLeftArtifact().addChildAtPosition(pseudoNode, position + 1);

			} else if (rightArtifact == null) {

				int position = getPositionOfLastCommonPredecessor(comparison.getRightArtifact(),
						comparison.getLeftArtifact(), leftArtifact);

				Node pseudoNode = leftArtifact.cloneNode();
				pseudoNodes.add(pseudoNode);

				comparison.getRightArtifact().addChildAtPosition(pseudoNode, position + 1);

			}

		}

		return pseudoNodes;

	}

	private void applyActions(List<ActionScope> actionScopes, Map<Node, Integer> pseudoNodeToPosition) {

		// apply actions: first perform add actions even if they are not applied, then
		// move actions and delete actions.
		// Remove not applied add actions in the end.
		List<Node> addedNodes = new ArrayList<Node>();

		for (ActionScope actionScope : actionScopes) {
			Action action = actionScope.getAction();
			Node affectedNode = action.getAffectedNode();
			Node actionNode = action.getActionNode();

			if (action.getActionType() == ActionType.ADD) {

				AddAction addAction = (AddAction) action;
				Node newNode = actionNode.cloneNode();
				addedNodes.add(newNode);

				if (addAction.addAtPositionZero()) {
					affectedNode.addChildAtPosition(newNode, 0);
				} else {
					int position = affectedNode.getPosition();
					if (position == -1) {
						position = pseudoNodeToPosition.get(affectedNode);
					}

					affectedNode.addSiblingAtPosition(newNode, position + 1);

				}

			} else if (action.getActionType() == ActionType.DELETE) {

				if (actionScope.apply()) {
					affectedNode.getChildren().remove(actionNode);
				}

			} else if (action.getActionType() == ActionType.UPDATE) {

				if (actionScope.apply()) {
					affectedNode.setNodeType(actionNode.getNodeType());

					List<Attribute> attributes = Lists.newArrayList(actionNode.getAttributes());

					affectedNode.getAttributes().clear();
					for (Attribute attribute : attributes) {

						affectedNode.addAttribute(attribute.cloneAttribute());

					}
				}

			}
		}

		applyMoveActions(actionScopes);

		// Remove unapplied add actions
		for (ActionScope actionScope : actionScopes) {

			if (actionScope.getAction().getActionType() == ActionType.ADD && !actionScope.apply()) {
				AddAction addAction = (AddAction) actionScope.getAction();

				for (Node addedNode : addedNodes) {
					if (addAction.addAtPositionZero()) {
						addAction.getAffectedNode().getChildren().remove(addedNode);
					} else {
						addAction.getAffectedNode().getParent().getChildren().remove(addedNode);
					}

				}
			}
		}

	}

	private void applyMoveActions(List<ActionScope> actionScopes) {

		// filter move actions
		List<ActionScope> moveActions = new ArrayList<ActionScope>();
		for (ActionScope actionScope : actionScopes) {
			if (actionScope.getAction().getActionType() == ActionType.MOVE) {
				moveActions.add(actionScope);
			}
		}

		// align the nodes until they are in the correct position.
		boolean aligned = false;
		while (!aligned) {

			for (ActionScope moveActionScope : moveActions) {
				if (moveActionScope.apply()) {
					MoveAction moveAction = (MoveAction) moveActionScope.getAction();
					Collections.swap(moveAction.getAffectedNode().getParent().getChildren(),
							moveAction.getAffectedNode().getPosition(), moveAction.getNewPosition());
				}
			}
			aligned = true;
			for (ActionScope moveActionScope : moveActions) {
				if (moveActionScope.apply()) {
					MoveAction moveAction = (MoveAction) moveActionScope.getAction();

					if (moveAction.getAffectedNode().getPosition() != moveAction.getNewPosition()) {
						aligned = false;
						break;
					}

				}
			}

		}
	}

	private List<ActionScope> sortActionScopes(List<ActionScope> actionScopes) {

		// sort action scopes: ADD, MOVE/UPDATE, DELETE
		List<ActionScope> sortedActionScopes = new ArrayList<ActionScope>();
		for (ActionScope actionScope : actionScopes) {
			if (actionScope.getAction().getActionType() == ActionType.ADD) {
				sortedActionScopes.add(actionScope);
			}
		}

		for (ActionScope actionScope : actionScopes) {
			if (actionScope.getAction().getActionType() == ActionType.MOVE
					|| actionScope.getAction().getActionType() == ActionType.UPDATE) {
				sortedActionScopes.add(actionScope);
			}
		}

		for (ActionScope actionScope : actionScopes) {
			if (actionScope.getAction().getActionType() == ActionType.DELETE) {
				sortedActionScopes.add(actionScope);
			}
		}
		return sortedActionScopes;

	}

	private void getActions(Comparison<Node> parentComparison, Iterator<Comparison<Node>> iterator,
			List<ActionScope> actions, List<Node> pseudoNodes) {

		// perform preorder traversal
		while (iterator.hasNext()) {
			Comparison<Node> comparison = iterator.next();

			Node leftArtifact = comparison.getLeftArtifact();
			Node rightArtifact = comparison.getRightArtifact();

			// don't compare nested components
			if (leftArtifact instanceof Component || rightArtifact instanceof Component) {
				return;
			}

			// new artifact on left side
			if (leftArtifact == null) {

				int position = getPositionOfLastCommonPredecessor(parentComparison.getLeftArtifact(),
						parentComparison.getRightArtifact(), rightArtifact);

				if (position == 0) {
					actions.add(new ActionScope(new AddAction(parentComparison.getLeftArtifact(), rightArtifact, true),
							true));

				} else {
					actions.add(new ActionScope(
							new AddAction(parentComparison.getLeftArtifact().getChildren().get(position), rightArtifact,
									false),
							true));

				}

				Node pseudoNode = rightArtifact.cloneNode();
				pseudoNodes.add(pseudoNode);
				parentComparison.getLeftArtifact().addChildAtPosition(pseudoNode, position + 1);

			}
			// deleted artifact on right side
			else if (rightArtifact == null) {
				actions.add(new ActionScope(new DeleteAction(parentComparison.getLeftArtifact(), leftArtifact), true));

				int position = getPositionOfLastCommonPredecessor(parentComparison.getRightArtifact(),
						parentComparison.getLeftArtifact(), leftArtifact);

				// add pseudo node on right side
				Node pseudoNode = leftArtifact.cloneNode();
				pseudoNodes.add(pseudoNode);
				parentComparison.getRightArtifact().addChildAtPosition(pseudoNode, position + 1);

			}
			// artifacts are matched but different in content
			else {

				// first check for move actions
				// only moves within a configuration child are recognized
				if (!(leftArtifact.getParent() instanceof Configuration)
						&& !(rightArtifact.getParent() instanceof Configuration)) {

					if (leftArtifact.getPosition() != rightArtifact.getPosition()) {

						actions.add(new ActionScope(new MoveAction(leftArtifact, rightArtifact,
								leftArtifact.getPosition(), rightArtifact.getPosition()), true));

					}

				}

				// check for update
				Node cloneLeft = leftArtifact.cloneNode();
				cloneLeft.getChildren().clear();
				Node cloneRight = rightArtifact.cloneNode();
				cloneRight.getChildren().clear();
				if (compareEngine.compare(cloneLeft, cloneRight).getSimilarity() != 1.0) {
					actions.add(new ActionScope(new UpdateAction(leftArtifact, rightArtifact), true));
				}

			}

		}

		for (Comparison<Node> childComparison : parentComparison.getChildComparisons()) {

			if (childComparison.getLeftArtifact() != null && childComparison.getRightArtifact() != null) {
				List<Comparison<Node>> sortedComparisons = sortComparisons(childComparison.getChildComparisons());
				iterator = sortedComparisons.iterator();
				if (iterator.hasNext()) {
					getActions(childComparison, iterator, actions, pseudoNodes);

				}
			}

		}

	}

	private List<Comparison<Node>> sortComparisons(List<Comparison<Node>> comparisons) {

		// sort comparisons: ADD, DELETE, MOVE, UPDATE
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

	private List<Comparison<Node>> matchComponents(List<Component> components1, List<Component> components2) {

		List<Comparison<Node>> componentComparisons = new ArrayList<Comparison<Node>>();

		for (Component component1 : components1) {
			for (Component component2 : components2) {
				componentComparisons.add(compareEngine.compare(component1, component2));
			}
		}

		sortingMatcher.calculateMatching(componentComparisons);

		return componentComparisons;
	}

	private List<Comparison<Node>> matchConfigurations(List<Configuration> configurations1,
			List<Configuration> configurations2) {

		List<Comparison<Node>> configurationsComparisons = new ArrayList<Comparison<Node>>();

		for (Configuration configuration1 : configurations1) {
			for (Configuration configuration2 : configurations2) {
				configurationsComparisons.add(compareEngine.compare(configuration1, configuration2));
			}
		}

		sortingMatcher.calculateMatching(configurationsComparisons);

		return configurationsComparisons;
	}

	private int getPositionOfLastCommonPredecessor(Node parentLeft, Node parentRight, Node target) {

		Comparison<Node> comparison = compareEngine.compare(parentLeft, parentRight);
		int position = 0;
		for (int i = target.getPosition() - 1; i >= 0; i--) {

			Node currentNode = parentRight.getChildren().get(i);

			for (Comparison<Node> childComparison : comparison.getChildComparisons()) {

				if (childComparison.getRightArtifact() == currentNode) {

					if (childComparison.getLeftArtifact() != null) {
						position = childComparison.getLeftArtifact().getPosition();
						return position;
					}

				}

			}

		}

		return position;

	}

}
