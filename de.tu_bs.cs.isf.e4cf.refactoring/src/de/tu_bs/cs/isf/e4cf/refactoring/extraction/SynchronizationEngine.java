package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

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
import de.tu_bs.cs.isf.e4cf.refactoring.controllers.ComponentLayerViewController;
import de.tu_bs.cs.isf.e4cf.refactoring.controllers.SynchronizationConflictViewController;
import de.tu_bs.cs.isf.e4cf.refactoring.controllers.SynchronizationViewController;
import de.tu_bs.cs.isf.e4cf.refactoring.model.Action;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionType;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ExtractionResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.PositionAggregate;
import de.tu_bs.cs.isf.e4cf.refactoring.model.SynchronizationResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.SynchronizationScope;

@Singleton
@Creatable
public class SynchronizationEngine {

	public final float COMPONENT_THRESHOLD = 0.9f;

	private CompareEngineHierarchical compareEngine;
	private ClusterEngine clusterEngine;
	private SynchronizationUnitEngine synchronizationUnitEngine;
	private SortingMatcher sortingMatcher;
	private SynchronizationViewController synchronizationViewController;
	private ActionViewController actionViewController;
	private SynchronizationConflictViewController synchronizationConflictViewController;

	public SynchronizationEngine() {
		sortingMatcher = new SortingMatcher();
		compareEngine = new CompareEngineHierarchical(sortingMatcher, new MetricImpl("test"));
		clusterEngine = new ClusterEngine();
		synchronizationUnitEngine = new SynchronizationUnitEngine();
		synchronizationViewController = new SynchronizationViewController();
		actionViewController = new ActionViewController();
		synchronizationConflictViewController = new SynchronizationConflictViewController();
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

		for (Comparison<Node> componentComparison : componentComparisons) {

			if (componentComparison.getLeftArtifact() != null && componentComparison.getRightArtifact() != null
					&& componentComparison.getSimilarity() > COMPONENT_THRESHOLD) {
				// matched component
				Component component1 = (Component) componentComparison.getLeftArtifact();
				Component component2 = (Component) componentComparison.getRightArtifact();

				matchedComponents.put(component1, component2);

				List<Comparison<Node>> configurationComparisons = matchConfigurations(component1.getConfigurations(),
						component2.getConfigurations());

				for (Comparison<Node> configurationComparison : configurationComparisons) {

					if (configurationComparison.getLeftArtifact() != null
							&& configurationComparison.getRightArtifact() != null) {
						// matched configuration

						Configuration configuration1 = (Configuration) configurationComparison.getLeftArtifact();
						Configuration configuration2 = (Configuration) configurationComparison.getRightArtifact();

						configurationsToUpdate.put(configuration1, configuration2);

					} else if (configurationComparison.getLeftArtifact() != null
							&& configurationComparison.getRightArtifact() == null) {
						// deleted configuration
						Configuration configuration1 = (Configuration) configurationComparison.getLeftArtifact();

						if (!configurationsToDelete.containsKey(component1)) {
							configurationsToDelete.put(component1, new ArrayList<Configuration>());
						}

						configurationsToDelete.get(component1).add(configuration1);

					} else {
						// added configuration
						Configuration configuration2 = (Configuration) configurationComparison.getLeftArtifact();

						if (!configurationsToAdd.containsKey(component1)) {
							configurationsToAdd.put(component1, new ArrayList<Configuration>());
						}

						configurationsToAdd.get(component1).add(configuration2);

					}

				}
				List<ActionScope> actionScopes = new ArrayList<ActionScope>();

				for (Entry<Configuration, Configuration> configurationPair : configurationsToUpdate.entrySet()) {

					List<ActionScope> localActionScopes = new ArrayList<ActionScope>();

					Comparison<Node> configurationComparison = compareEngine.compare(configurationPair.getKey(),
							configurationPair.getValue());

					determineActionScopes(configurationComparison, configurationComparison.getChildComparisons().get(0),
							localActionScopes);

					actionScopes.addAll(localActionScopes);

					for (ActionScope actionScope : localActionScopes) {
						actionToConfiguration.put(actionScope, configurationPair.getValue());
					}

				}
				componentToActionScopes.put(component1, actionScopes);
				components.add(component1);

			} else if (componentComparison.getLeftArtifact() != null
					&& componentComparison.getRightArtifact() == null) {
				Component component1 = (Component) componentComparison.getLeftArtifact();
				components.add(component1);
			} else {
				Component component2 = (Component) componentComparison.getRightArtifact();
				components.add(component2);
			}

		}

		// visualize actions

		actionViewController.showView(componentToActionScopes);
		if (!actionViewController.isResult()) {
			return null;
		}

		// delete configurations
		for (Entry<Component, List<Configuration>> entry : configurationsToDelete.entrySet()) {
			Component component = entry.getKey();

			for (Configuration configuration : entry.getValue()) {

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

		// add still different configurations and determine synchronizations

		for (Entry<Component, List<ActionScope>> entry : componentToActionScopes.entrySet()) {
			Component component = entry.getKey();
			for (ActionScope actionScope : entry.getValue()) {
				if (!actionScope.apply()) {
					Configuration sourceConfiguration = actionToConfiguration.get(actionScope);
					configurationsToAdd.get(component).add(sourceConfiguration);
				} else {
					applyAction(actionScope);
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

		// conflict search

		Map<Set<ActionScope>, Set<SynchronizationScope>> conflicts = findConflicts(componentToActionScopes,
				actionToSynchronizationScopes);

		if (conflicts.size() != 0) {
			synchronizationConflictViewController.showView(conflicts, actionToSynchronizationScopes);
			if (!synchronizationConflictViewController.isResult()) {
				return null;
			}
		}

		// apply synchronization
		for (Entry<ActionScope, List<SynchronizationScope>> entry : actionToSynchronizationScopes.entrySet()) {
			ActionScope actionScope = entry.getKey();
			List<SynchronizationScope> synchronizationScopes = entry.getValue();

			synchronizeAction(actionScope, synchronizationScopes);

		}

		// add configurations
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

						if (!nodeComponentRelation1.containsKey(nodeComponentRelationEntry2.getKey())) {
							nodeComponentRelation1.put(node, nodeComponentRelationEntry2.getValue());
						}

					}

				}

			}

		}

		// synchronize configurations

		for (Component component : result1.getComponents()) {
			updateSynchronizationUnit(component);
		}

		for (Component component : result2.getComponents()) {
			updateSynchronizationUnit(component);
		}

		// replace component

		for (Tree tree : result2.getTrees()) {

			for (Entry<Component, Component> entry : matchedComponents.entrySet()) {
				replaceComponentInTree(tree.getRoot(), entry.getValue(), entry.getKey());
			}

		}

		List<Tree> trees = new ArrayList<Tree>();
		trees.addAll(result1.getTrees());
		trees.addAll(result2.getTrees());

		return new SynchronizationResult(components, trees);

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

	private void synchronizeAction(ActionScope actionScope, List<SynchronizationScope> synchronizationScopes) {
		// apply actions

		Action action = actionScope.getAction();
		Node actionNode = action.getActionNode();

		if (actionScope.apply()) {
			if (action.getActionType() == ActionType.ADD) {

				for (SynchronizationScope synchronizationScope : synchronizationScopes) {
					if (synchronizationScope.synchronize()) {

						Node node = synchronizationScope.getNode();
						int position = determinePosition(node, actionNode);
						node.addChildWithParent(actionNode.cloneNode(), position);
					}
				}

			} else if (action.getActionType() == ActionType.DELETE) {

				for (SynchronizationScope synchronizationScope : synchronizationScopes) {

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
							node.removeChild(nodeToDelete, nodeToDelete.getPosition());

						}
					}

				}

			} else if (action.getActionType() == ActionType.UPDATE) {

				for (SynchronizationScope synchronizationScope : synchronizationScopes) {

					if (synchronizationScope.synchronize()) {
						Node node = synchronizationScope.getNode();

						node.setNodeType(actionNode.getNodeType());

						List<Attribute> attributes = Lists.newArrayList(actionNode.getAttributes());

						Attribute position = node.getAttributeForKey("Position");

						node.getAttributes().clear();
						for (Attribute attribute : attributes) {

							if (!attribute.getAttributeKey().equals("Position")) {
								node.addAttribute(attribute.cloneAttribute());
							}

						}

						node.addAttribute(position);

					}

				}

			} else if (action.getActionType() == ActionType.MOVE) {

				for (SynchronizationScope synchronizationScope : synchronizationScopes) {

					if (synchronizationScope.synchronize()) {
						Node node = synchronizationScope.getNode();

						node.updatePosition(actionNode.getPosition());
					}

				}

			}

		}

	}

	private void applyAction(ActionScope actionScope) {
		// apply actions

		Action action = actionScope.getAction();
		Node affectedNode = action.getAffectedNode();
		Node actionNode = action.getActionNode();

		if (actionScope.apply()) {
			if (action.getActionType() == ActionType.ADD) {

				int position = determinePosition(affectedNode, actionNode);
				affectedNode.addChildWithParent(actionNode.cloneNode(), position);

			} else if (action.getActionType() == ActionType.DELETE) {

				affectedNode.removeChild(actionNode, actionNode.getPosition());

			} else if (action.getActionType() == ActionType.UPDATE) {

				affectedNode.setNodeType(actionNode.getNodeType());

				List<Attribute> attributes = Lists.newArrayList(actionNode.getAttributes());

				Attribute position = affectedNode.getAttributeForKey("Position");

				affectedNode.getAttributes().clear();
				for (Attribute attribute : attributes) {
					if (!attribute.getAttributeKey().equals("Position")) {
						affectedNode.addAttribute(attribute.cloneAttribute());
					}

				}
				affectedNode.addAttribute(position);

			} else if (action.getActionType() == ActionType.MOVE) {

				affectedNode.updatePosition(actionNode.getPosition());

			}

		}

	}

	private int determinePosition(Node affectedNode, Node actionNode) {

		Node actionNodeParent = actionNode.getParent();

		// compare nodes
		NodeComparison comparison = compareEngine.compare(affectedNode, actionNodeParent);

		List<PositionAggregate> aggregates = new ArrayList<PositionAggregate>();

		// compare children position
		for (Comparison<Node> childComparison : comparison.getChildComparisons()) {
			if (childComparison.getLeftArtifact() != null && childComparison.getRightArtifact() != null) {

				if (!(childComparison.getLeftArtifact() instanceof Component)
						&& !(childComparison.getRightArtifact() instanceof Component)) {
					// match
					aggregates.add(new PositionAggregate(childComparison.getLeftArtifact().getPosition(),
							childComparison.getLeftArtifact(), childComparison.getRightArtifact().getPosition(),
							childComparison.getRightArtifact()));
				}

			}
		}

		aggregates.sort(Comparator.comparing(PositionAggregate::getRightPosition));

		if (!aggregates.isEmpty()) {
			PositionAggregate target = aggregates.get(0);

			// determine last predecessor
			for (int i = aggregates.size() - 1; i >= 0; i--) {
				if (aggregates.get(i).getRightPosition() > actionNode.getPosition()) {
					target = aggregates.get(i);
				}

			}

			return target.getLeftPosition();
		} else {
			return affectedNode.getChildren().size() == 0 ? 0 : affectedNode.getChildren().size();
		}

	}

	private void determineActionScopes(Comparison<Node> parentComparison, Comparison<Node> comparison,
			List<ActionScope> actions) {

		Node leftArtifact = comparison.getLeftArtifact();
		Node rightArtifact = comparison.getRightArtifact();

		// artifacts are totally equal
		if (comparison.getSimilarity() == 1.0f) {
			return;
		}

		if (leftArtifact instanceof Component || rightArtifact instanceof Component) {
			return;
		}

		// artifacts are different in a way
		else {
			// new artifact
			if (leftArtifact == null) {
				actions.add(new ActionScope(
						new Action(ActionType.ADD, parentComparison.getLeftArtifact(), rightArtifact), true));
			}
			// deleted artifact
			else if (rightArtifact == null) {
				actions.add(new ActionScope(
						new Action(ActionType.DELETE, parentComparison.getLeftArtifact(), leftArtifact), true));
			}
			// artifacts are different in content
			else {

				if (comparison.getResultSimilarity() != 1.0f) {

					// check for move actions
					// only moves within a configuration child are recognized
					if (!(leftArtifact.getParent() instanceof Configuration)
							&& !(rightArtifact.getParent() instanceof Configuration)) {
						if (leftArtifact.getPosition() != rightArtifact.getPosition()) {
							actions.add(
									new ActionScope(new Action(ActionType.MOVE, leftArtifact, rightArtifact), true));
						}
					}

					// check for update
					Node cloneLeft = leftArtifact.cloneNode();
					Node cloneRight = rightArtifact.cloneNode();
					cloneLeft.getAttributes().remove(cloneLeft.getAttributeForKey("Position"));
					cloneRight.getAttributes().remove(cloneRight.getAttributeForKey("Position"));

					if (compareEngine.compare(cloneLeft, cloneRight).getSimilarity() != 1.0) {
						actions.add(new ActionScope(new Action(ActionType.UPDATE, leftArtifact, rightArtifact), true));
					}

				}

				else {
					for (Comparison<Node> childComparison : comparison.getChildComparisons()) {
						determineActionScopes(comparison, childComparison, actions);
					}

				}

			}
		}

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

	public Map<Set<ActionScope>, Set<SynchronizationScope>> findConflicts(
			Map<Component, List<ActionScope>> componentToActions,
			Map<ActionScope, List<SynchronizationScope>> actionsToSynchronizations) {

		Map<Set<ActionScope>, Set<SynchronizationScope>> conflicts = new HashMap<Set<ActionScope>, Set<SynchronizationScope>>();

		for (Entry<Component, List<ActionScope>> componentEntry : componentToActions.entrySet()) {

			for (ActionScope actionScope1 : componentEntry.getValue()) {

				for (ActionScope actionScope2 : componentEntry.getValue()) {

					if (actionScope1 != actionScope2 && actionScope1.apply() && actionScope2.apply()) {

						for (SynchronizationScope synchronizationScope1 : actionsToSynchronizations.get(actionScope1)) {

							for (SynchronizationScope synchronizationScope2 : actionsToSynchronizations
									.get(actionScope2)) {

								if (synchronizationScope1.synchronize() && synchronizationScope2.synchronize()) {

									boolean cross = actionScope1.getAction().getAffectedNode() == synchronizationScope2
											.getNode()
											&& actionScope2.getAction().getAffectedNode() == synchronizationScope1
													.getNode();

									if (synchronizationScope1.getNode() == synchronizationScope2.getNode() || cross) {

										boolean inSet = false;

										for (Entry<Set<ActionScope>, Set<SynchronizationScope>> entry : conflicts
												.entrySet()) {
											Set<ActionScope> actionScopes = entry.getKey();
											Set<SynchronizationScope> synchronizationScopes = entry.getValue();

											if (actionScopes.contains(actionScope1)
													|| actionScopes.contains(actionScope2)) {
												actionScopes.add(actionScope1);
												actionScopes.add(actionScope2);

												synchronizationScopes.add(synchronizationScope1);
												synchronizationScopes.add(synchronizationScope2);
												inSet = true;
											}
										}

										if (!inSet) {
											Set<ActionScope> set = new HashSet<ActionScope>();
											set.add(actionScope1);
											set.add(actionScope2);
											Set<SynchronizationScope> synchronizationScopes = new HashSet<SynchronizationScope>();
											synchronizationScopes.add(synchronizationScope1);
											synchronizationScopes.add(synchronizationScope2);

											conflicts.put(set, synchronizationScopes);
										}

									}
								}

							}

						}

					}

				}
			}

		}

		Iterator<Set<ActionScope>> iterator = conflicts.keySet().iterator();

		while (iterator.hasNext()) {

			Set<ActionScope> pivotCluster = iterator.next();

			if (conflicts.keySet().contains(pivotCluster)) {
				List<Set<ActionScope>> clustersToRemove = new ArrayList<Set<ActionScope>>();
				for (Set<ActionScope> cluster : conflicts.keySet()) {

					if (pivotCluster != cluster) {

						if (Sets.intersection(pivotCluster, cluster).size() != 0) {
							pivotCluster.addAll(cluster);
							clustersToRemove.add(cluster);

							conflicts.get(pivotCluster).addAll(conflicts.get(cluster));

						}

					}

				}

				for (Set<ActionScope> clusterToRemove : clustersToRemove) {
					conflicts.remove(clusterToRemove);
				}
			}
		}

		return conflicts;

	}

}
