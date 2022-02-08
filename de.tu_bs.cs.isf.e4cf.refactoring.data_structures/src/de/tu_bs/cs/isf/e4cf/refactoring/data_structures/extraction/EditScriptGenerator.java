package de.tu_bs.cs.isf.e4cf.refactoring.data_structures.extraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.comparison.interfaces.Comparison;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.ActionScope;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.Delete;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.Insert;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.Move;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.Update;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.util.SynchronizationUtil;

@Singleton
@Creatable
public class EditScriptGenerator {

	private CompareEngineHierarchical compareEngine;

	public EditScriptGenerator() {
		compareEngine = new CompareEngineHierarchical(new SortingMatcher(), new MetricImpl("test"));

	}

	private void checkRootUpdate(Comparison<Node> comparison, List<ActionScope> actions, Map<Node, Node> cloneMapping) {

		Node leftArtifact = comparison.getLeftArtifact();
		Node rightArtifact = comparison.getRightArtifact();

		// check similarity
		Node cloneLeft = leftArtifact.cloneNode();
		cloneLeft.getChildren().clear();
		Node cloneRight = rightArtifact.cloneNode();
		cloneRight.getChildren().clear();
		if (compareEngine.compare(cloneLeft, cloneRight).getSimilarity() != 1.0) {
			Node originalNode = cloneMapping.get(leftArtifact);
			Update update = new Update(originalNode, rightArtifact.cloneNode());
			actions.add(new ActionScope(update, true));

		}

	}

	private void createMapBetweenOriginalAndClonedNode(Node node, Node clone, Map<Node, Node> cloneMapping) {

		cloneMapping.put(node, clone);
		cloneMapping.put(clone, node);
		for (int i = 0; i < node.getChildren().size(); i++) {
			createMapBetweenOriginalAndClonedNode(node.getChildren().get(i), clone.getChildren().get(i), cloneMapping);
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

	public List<ActionScope> generateEditScript(Tree tree1, Tree tree2) {
		Map<Node, Node> cloneMapping = new HashMap<Node, Node>();
		Node root1 = tree1.getRoot();
		Node root2 = tree2.getRoot();

		Node cloneRoot1 = root1.cloneNode();
		Node cloneRoot2 = root2.cloneNode();
		createMapBetweenOriginalAndClonedNode(root1, cloneRoot1, cloneMapping);

		List<ActionScope> actionScopes = new ArrayList<ActionScope>();
		Comparison<Node> comparison = compareEngine.compare(cloneRoot1, cloneRoot2);

		checkRootUpdate(comparison, actionScopes, cloneMapping);

		getActions(comparison, actionScopes, new LinkedList<Comparison<Node>>(), cloneMapping);
		return sortActionScopes(actionScopes);

	}

	private List<ActionScope> sortActionScopes(List<ActionScope> actionScopes) {

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

	public void getActions(Comparison<Node> parentComparison, List<ActionScope> actions, Queue<Comparison<Node>> queue,
			Map<Node, Node> cloneMapping) {

		// perform breadth-first traversal
		List<Comparison<Node>> childComparisons = parentComparison.getChildComparisons();
		queue.addAll(childComparisons);
		childComparisons = sortComparisons(childComparisons);

		for (Comparison<Node> comparison : childComparisons) {

			Node leftArtifact = comparison.getLeftArtifact();
			Node rightArtifact = comparison.getRightArtifact();

			// INSERT
			if (leftArtifact == null) {

				int position = SynchronizationUtil.getPositionOfCommonPredecessor(parentComparison.getLeftArtifact(),
						parentComparison.getRightArtifact(), rightArtifact);

				Node originalNode = cloneMapping.get(parentComparison.getLeftArtifact());

				Insert insert = new Insert(rightArtifact.cloneNode(), originalNode, position + 1);
				actions.add(new ActionScope(insert, true));

				Node rightArtifactClone = rightArtifact.cloneNode();
				parentComparison.getLeftArtifact().addChildAtPosition(rightArtifactClone, position + 1);

			}
			// DELETE
			else if (rightArtifact == null) {
				Node originalNode = cloneMapping.get(leftArtifact);

				Delete delete = new Delete(originalNode);
				actions.add(new ActionScope(delete, true));

				int position = SynchronizationUtil.getPositionOfCommonPredecessor(parentComparison.getRightArtifact(),
						parentComparison.getLeftArtifact(), leftArtifact);

				Node leftArtifactClone = leftArtifact.cloneNode();
				parentComparison.getRightArtifact().addChildAtPosition(leftArtifactClone, position + 1);

			}
			// UPDATE
			else {

				// check similarity
				Node cloneLeft = leftArtifact.cloneNode();
				cloneLeft.getChildren().clear();
				Node cloneRight = rightArtifact.cloneNode();
				cloneRight.getChildren().clear();
				if (compareEngine.compare(cloneLeft, cloneRight).getSimilarity() != 1.0) {
					Node originalNode = cloneMapping.get(leftArtifact);

					Update update = new Update(originalNode, rightArtifact.cloneNode());
					actions.add(new ActionScope(update, true));

				}

			}

		}

		// nodes are aligned; check positions
		Node leftArtifact = parentComparison.getLeftArtifact();
		Node rightArtifact = parentComparison.getRightArtifact();
		Comparison<Node> comparison = compareEngine.compare(leftArtifact, rightArtifact);
		List<Node> sequence = SynchronizationUtil.findLongestCommonSubsequence(leftArtifact.getChildren(),
				rightArtifact.getChildren(), comparison.getChildComparisons());

		List<Node> iterationList = new ArrayList<Node>(leftArtifact.getChildren());

		for (Node child : iterationList) {
			if (!sequence.contains(child)) {

				// get partner of left child in right node
				Node partner = getPartner(child, comparison.getChildComparisons());
				Node originalNode = cloneMapping.get(child);

				int position = SynchronizationUtil.getPositionOfCommonPredecessor(parentComparison.getLeftArtifact(),
						parentComparison.getRightArtifact(), partner) + 1;

				// corner case: move node that is inserted before
				if (originalNode == null) {
					originalNode = cloneMapping.get(child.getParent());
					for (ActionScope actionScope : actions) {
						if (actionScope.getAction() instanceof Insert) {
							Insert insert = (Insert) actionScope.getAction();
							if (insert.getX() == child) {
								Move move = new Move(insert.getX(), originalNode, position);
								actions.add(new ActionScope(move, true));
								break;
							}
						}
					}

				} else {
					Move move = new Move(originalNode, originalNode.getParent(), position);
					actions.add(new ActionScope(move, true));
				}

				// apply move
				int previousPosition = child.getPosition();
				parentComparison.getLeftArtifact().getChildren().remove(previousPosition);
				parentComparison.getLeftArtifact().addChildAtPosition(child, position);
				sequence.add(child);

			}

		}

		if (!queue.isEmpty()) {
			Comparison<Node> nextElement = queue.remove();
			if (nextElement.getLeftArtifact() != null && nextElement.getRightArtifact() != null) {
				getActions(nextElement, actions, queue, cloneMapping);
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
