package de.tu_bs.cs.isf.e4cf.refactoring.extraction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

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
import de.tu_bs.cs.isf.e4cf.refactoring.model.Action;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.model.ActionType;
import de.tu_bs.cs.isf.e4cf.refactoring.model.PositionAggregate;
import de.tu_bs.cs.isf.e4cf.refactoring.model.RefactoringResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.SynchronizationResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.SynchronizationScope;

@Singleton
@Creatable
public class SynchronizationEngine {

	public final float COMPONENT_THRESHOLD = 0.9f;

	private CompareEngineHierarchical compareEngine;
	private DetectionEngine detectionEngine;
	private SynchronizationUnitEngine synchronizationUnitEngine;

	public SynchronizationEngine() {
		compareEngine = new CompareEngineHierarchical(new SortingMatcher(), new MetricImpl("test"));
		detectionEngine = new DetectionEngine();
		synchronizationUnitEngine = new SynchronizationUnitEngine();
	}

	public Map<Component, List<ActionScope>> analyzeActions(RefactoringResult result1, RefactoringResult result2) {

		// compare components
		List<Comparison<Node>> componentComparisons = compareComponents(result1, result2);
		Map<Component, List<ActionScope>> componentToActions = new HashMap<Component, List<ActionScope>>();

		for (Comparison<Node> componentComparison : componentComparisons) {

			List<Action> actions = new LinkedList<Action>();
			List<ActionScope> actionScopes = new ArrayList<ActionScope>();

			if (componentComparison.getLeftArtifact() != null && componentComparison.getRightArtifact() != null) {

				Component leftComponent = (Component) componentComparison.getLeftArtifact();
				Component rightComponent = (Component) componentComparison.getRightArtifact();

				// component match
				if (componentComparison.getSimilarity() > COMPONENT_THRESHOLD) {

					List<Comparison<Node>> configurationComparisons = componentComparison.getChildComparisons();

					for (Comparison<Node> configurationComparison : configurationComparisons) {

						// configuration match
						if (configurationComparison.getLeftArtifact() != null
								&& configurationComparison.getRightArtifact() != null) {

							determineActionList(configurationComparison,
									configurationComparison.getChildComparisons().get(0), actions);
						}

					}

					// sort actions and create scope
					actions.sort(Comparator.comparing(Action::getActionType));
					for (Action action : actions) {
						actionScopes.add(new ActionScope(action, true));
					}
					componentToActions.put(leftComponent, actionScopes);

					// replace every matched component in right tree with component in left tree
					for (Tree tree : result2.getTrees()) {
						replaceComponentInTree(tree.getRoot(), rightComponent, leftComponent);
					}

				}
			}

		}

		return componentToActions;

	}

	private void addConfigurationToNodeComponentRelation(Component component, Configuration configuration,
			Map<Node, Map<Integer, Configuration>> nodeComponentRelation) {

		for (Entry<Node, Map<Integer, Configuration>> nodeEntrySet : component.getNodeComponentRelation().entrySet()) {
			for (Entry<Integer, Configuration> positionEntrySet : nodeEntrySet.getValue().entrySet()) {
				if (positionEntrySet.getValue().equals(configuration)) {

					if (!nodeComponentRelation.containsKey(nodeEntrySet.getKey())) {
						nodeComponentRelation.put(nodeEntrySet.getKey(), new HashMap<Integer, Configuration>());
					}
					nodeComponentRelation.get(nodeEntrySet.getKey()).put(positionEntrySet.getKey(), configuration);
				}
			}

		}
	}

	private void addAndReplaceConfigurationToNodeComponentRelation(Component component, Configuration configuration,
			Configuration newConfiguration, Map<Node, Map<Integer, Configuration>> nodeComponentRelation) {

		for (Entry<Node, Map<Integer, Configuration>> nodeEntrySet : component.getNodeComponentRelation().entrySet()) {
			for (Entry<Integer, Configuration> positionEntrySet : nodeEntrySet.getValue().entrySet()) {
				if (positionEntrySet.getValue().equals(configuration)) {

					if (!nodeComponentRelation.containsKey(nodeEntrySet.getKey())) {
						nodeComponentRelation.put(nodeEntrySet.getKey(), new HashMap<Integer, Configuration>());
					}
					nodeComponentRelation.get(nodeEntrySet.getKey()).put(positionEntrySet.getKey(), newConfiguration);
				}
			}

		}
	}

	public Map<ActionScope, List<SynchronizationScope>> analyzeSynchronizations(RefactoringResult result1,
			RefactoringResult result2, Map<Component, List<ActionScope>> componentToActions) {

		// apply actions
		for (List<ActionScope> actionScopes : componentToActions.values()) {
			applyActions(actionScopes);
		}

		// for each component determine configurations
		List<Comparison<Node>> componentComparisons = compareComponents(result1, result2);
		for (Comparison<Node> componentComparison : componentComparisons) {

			if (componentComparison.getLeftArtifact() != null && componentComparison.getRightArtifact() != null) {
				List<Configuration> configurations = new ArrayList<Configuration>();

				// component match
				if (componentComparison.getSimilarity() > COMPONENT_THRESHOLD) {

					List<Comparison<Node>> configurationComparisons = componentComparison.getChildComparisons();

					Component leftComponent = (Component) componentComparison.getLeftArtifact();
					Component rightComponent = (Component) componentComparison.getLeftArtifact();

					Map<Node, Map<Integer, Configuration>> nodeComponentRelation = new HashMap<Node, Map<Integer, Configuration>>();

					for (Comparison<Node> configurationComparison : configurationComparisons) {

						// configuration match
						if (configurationComparison.getLeftArtifact() != null
								&& configurationComparison.getRightArtifact() != null) {

							Configuration leftConfiguration = (Configuration) configurationComparison.getLeftArtifact();
							Configuration rightConfiguration = (Configuration) configurationComparison
									.getRightArtifact();

							float similarity = configurationComparison.getChildSimilarity();
							// configurations are different and must be both added to component
							if (similarity != 1.0) {
								configurations.add(leftConfiguration);
								configurations.add(rightConfiguration);

								// add right configuration to node component relation
								addConfigurationToNodeComponentRelation(rightComponent, rightConfiguration,
										nodeComponentRelation);

								// add left configuration to node component relation
								addConfigurationToNodeComponentRelation(leftComponent, leftConfiguration,
										nodeComponentRelation);

							}
							// configurations are equal
							else {
								configurations.add((Configuration) configurationComparison.getLeftArtifact());

								// replace and add right configuration to node component relation
								addAndReplaceConfigurationToNodeComponentRelation(rightComponent, rightConfiguration,
										leftConfiguration, nodeComponentRelation);

								// add left configuration to node component relation
								addConfigurationToNodeComponentRelation(leftComponent, leftConfiguration,
										nodeComponentRelation);

							}
						}
						// new configuration
						else if (configurationComparison.getLeftArtifact() == null
								&& configurationComparison.getRightArtifact() != null) {

							Configuration rightConfiguration = (Configuration) configurationComparison
									.getRightArtifact();

							configurations.add(rightConfiguration);

							// add right configuration to node component relation
							addConfigurationToNodeComponentRelation(rightComponent, rightConfiguration,
									nodeComponentRelation);

						} else if (configurationComparison.getLeftArtifact() != null
								&& configurationComparison.getRightArtifact() == null) {
							Configuration leftConfiguration = (Configuration) configurationComparison.getLeftArtifact();

							configurations.add(leftConfiguration);

							// add left configuration to node component relation
							addConfigurationToNodeComponentRelation(leftComponent, leftConfiguration,
									nodeComponentRelation);
						}

					}

					leftComponent.setConfigurations(configurations);
					leftComponent.setNodeComponentRelation(nodeComponentRelation);

				}
			}

		}

		Map<ActionScope, List<SynchronizationScope>> actionToSynchronizationScopes = new HashMap<ActionScope, List<SynchronizationScope>>();

		// determine synchronization scopes
		for (Component component : componentToActions.keySet()) {

			synchronizationUnitEngine.updateSynchronizationUnit(component.getConfigurations(),
					component.getSynchronizationUnit());

			for (ActionScope actionScope : componentToActions.get(component)) {

				if (actionScope.apply()) {
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

		return actionToSynchronizationScopes;
	}

	public SynchronizationResult synchronize(RefactoringResult result1, RefactoringResult result2,
			Map<Component, List<ActionScope>> componentToActions,
			Map<ActionScope, List<SynchronizationScope>> actionToSynchronizations) {

		synchronizeActions(actionToSynchronizations);

		List<Component> synchronizedComponents = Lists.newArrayList(componentToActions.keySet());

		for (Component synchronizedComponent : synchronizedComponents) {
			removeDuplicateConfigurations(synchronizedComponent);
		}

		List<Tree> synchronizedTrees = new ArrayList<Tree>();
		synchronizedTrees.addAll(result1.getTrees());
		synchronizedTrees.addAll(result2.getTrees());

		return new SynchronizationResult(synchronizedComponents, synchronizedTrees);

	}

	private List<Comparison<Node>> compareComponents(RefactoringResult result1, RefactoringResult result2) {
		List<Comparison<Node>> nodeComparisons = new ArrayList<Comparison<Node>>();

		for (Component component1 : result1.getComponents()) {
			for (Component component2 : result2.getComponents()) {
				nodeComparisons.add(compareEngine.compare(component1, component2));
			}
		}
		return nodeComparisons;
	}

	private void removeDuplicateConfigurations(Component component) {

		// find clusters of configurations
		List<Node> configurations = component.getNodesOfType("Configuration");
		List<Set<Node>> clusters = detectionEngine.findClusters_Version2(configurations, 1.0f);

		for (Set<Node> cluster : clusters) {

			Node baseConfiguration = cluster.iterator().next();

			for (Node node : cluster) {

				Node child = node.getChildren().get(0);

				// remove configuration
				if (!node.equals(baseConfiguration)) {
					component.getChildren().remove(node);

					for (Entry<Node, Map<Integer, Configuration>> entry : component.getNodeComponentRelation()
							.entrySet()) {

						for (Entry<Integer, Configuration> configurationEntry : entry.getValue().entrySet()) {

							if (configurationEntry.getValue() == node) {
								entry.getValue().put(configurationEntry.getKey(), (Configuration) baseConfiguration);
							}

						}

					}

					synchronizationUnitEngine.removeFromSynchronizationUnit(child, component.getSynchronizationUnit());
				}

			}

		}

	}

	public Map<Set<ActionScope>, Set<SynchronizationScope>> scanForSynchronizationConflicts(
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

									if (synchronizationScope1.getNode() == synchronizationScope2.getNode()) {

										boolean inSet = false;

										for (Entry<Set<ActionScope>, Set<SynchronizationScope>> entry : conflicts
												.entrySet()) {
											Set<ActionScope> actionScopes = entry.getKey();
											Set<SynchronizationScope> synchronizationScopes = entry.getValue();

											if (actionScopes.contains(actionScope1)
													|| actionScopes.contains(actionScope2)) {
												actionScopes.add(actionScope1);
												actionScopes.add(actionScope2);

												if (synchronizationScopes == null) {

													System.out.println(actionScopes.hashCode());
													System.out.println(conflicts.keySet().iterator().next().hashCode());
												}

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

	private void replaceComponentInTree(Node node, Component component, Component synchronizedComponent) {

		for (int i = 0; i < node.getChildren().size(); i++) {
			if (node.getChildren().get(i).equals(component)) {
				int index = node.getChildren().indexOf(node.getChildren().get(i));
				node.getChildren().remove(index);
				node.getChildren().add(index, synchronizedComponent);
			}
			replaceComponentInTree(node.getChildren().get(i), component, synchronizedComponent);
		}

	}

	private void applyActions(List<ActionScope> actionScopes) {
		// apply actions
		for (ActionScope actionScope : actionScopes) {

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
	}

	private void synchronizeActions(Map<ActionScope, List<SynchronizationScope>> actionScopes) {
		// apply actions
		for (ActionScope actionScope : actionScopes.keySet()) {

			Action action = actionScope.getAction();
			Node actionNode = action.getActionNode();

			if (actionScope.apply()) {
				if (action.getActionType() == ActionType.ADD) {

					for (SynchronizationScope synchronizationScope : actionScopes.get(actionScope)) {
						if (synchronizationScope.synchronize()) {

							Node node = synchronizationScope.getNode();
							int position = determinePosition(node, actionNode);
							node.addChildWithParent(actionNode.cloneNode(), position);
						}
					}

				} else if (action.getActionType() == ActionType.DELETE) {

					for (SynchronizationScope synchronizationScope : actionScopes.get(actionScope)) {

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

					for (SynchronizationScope synchronizationScope : actionScopes.get(actionScope)) {

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

					for (SynchronizationScope synchronizationScope : actionScopes.get(actionScope)) {

						if (synchronizationScope.synchronize()) {
							Node node = synchronizationScope.getNode();

							node.updatePosition(actionNode.getPosition());
						}

					}

				}

			}

		}
	}

	private void determineActionList(Comparison<Node> parentComparison, Comparison<Node> comparison,
			List<Action> actions) {

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
				actions.add(new Action(ActionType.ADD, parentComparison.getLeftArtifact(), rightArtifact));
			}
			// deleted artifact
			else if (rightArtifact == null) {
				actions.add(new Action(ActionType.DELETE, parentComparison.getLeftArtifact(), leftArtifact));
			}
			// artifacts are different in content
			else {

				if (comparison.getResultSimilarity() != 1.0f) {

					// check for move actions
					// only moves within a configuration child are recognized
					if (!(leftArtifact.getParent() instanceof Configuration)
							&& !(rightArtifact.getParent() instanceof Configuration)) {
						if (leftArtifact.getPosition() != rightArtifact.getPosition()) {
							actions.add(new Action(ActionType.MOVE, leftArtifact, rightArtifact));
						}
					}

					// check for update
					Node cloneLeft = leftArtifact.cloneNode();
					Node cloneRight = rightArtifact.cloneNode();
					cloneLeft.getAttributes().remove(cloneLeft.getAttributeForKey("Position"));
					cloneRight.getAttributes().remove(cloneRight.getAttributeForKey("Position"));

					if (compareEngine.compare(cloneLeft, cloneRight).getSimilarity() != 1.0) {
						actions.add(new Action(ActionType.UPDATE, leftArtifact, rightArtifact));
					}

				}

				else {
					for (Comparison<Node> childComparison : comparison.getChildComparisons()) {
						determineActionList(comparison, childComparison, actions);
					}

				}

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

}
