package de.tu_bs.cs.isf.e4cf.refactoring.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;

public class CloneModel {

	private Map<MultiSetReferenceTree, List<MultiSetTree>> components;

	private Map<String, MultiSetReferenceTree> integratedTrees;

	private Tree tree;

	private Set<Granularity> granularities;

	public Set<Granularity> getGranularities() {
		return granularities;
	}

	public void setGranularities(Set<Granularity> granularities) {
		this.granularities = granularities;
	}

	public Map<Node, Integer> addChild(Node target, Node newNode) {

		return addChild(target, newNode, -1, null);
	}

	public Set<MultiSetNode> collectReferencesNodes(Node target) {

		Set<MultiSetNode> referenceNodes = new HashSet<MultiSetNode>();
		for (Entry<MultiSetReferenceTree, List<MultiSetTree>> entry : components.entrySet()) {

			for (MultiSetTree component : entry.getValue()) {
				List<MultiSetNode> multiSetNodes = component.getByNode(target);

				for (MultiSetNode multiSetNode : multiSetNodes) {
					referenceNodes.addAll(multiSetNode.getReferences());

				}
			}

		}

		return referenceNodes;
	}

	public Map<Node, Integer> addChild(Node target, Node newNode, int position) {
		return addChild(target, newNode, position, null);
	}

	public Map<Node, Integer> addChild(Node target, Node newNode, int position, Set<MultiSetNode> selectedReferences) {

		if (!target.getChildren().contains(newNode)) {
			target.addChild(newNode, position);
		}

		Map<Node, Integer> affectedNodesWithPosition = new HashMap<Node, Integer>();
		Map<MultiSetNode, MultiSetNode> updatedReferenceNodes = new HashMap<MultiSetNode, MultiSetNode>();

		Set<MultiSetNode> affectedMultiSetNodes = new HashSet<MultiSetNode>();

		for (Entry<MultiSetReferenceTree, List<MultiSetTree>> entry : components.entrySet()) {

			MultiSetReferenceTree referenceTree = entry.getKey();
			for (MultiSetTree component : entry.getValue()) {

				List<MultiSetNode> multiSetNodes = component.getByNode(target);
				for (MultiSetNode multiSetNode : multiSetNodes) {

					if (affectedMultiSetNodes.contains(multiSetNode)) {
						continue;
					}

					AddActionResult actionResult = multiSetNode.addChild(newNode, position, selectedReferences);
					affectedMultiSetNodes.add(multiSetNode);
					affectedMultiSetNodes.addAll(multiSetNode.getReferences());
					affectedNodesWithPosition.putAll(actionResult.getAffectedNodesWithPosition());

					for (Entry<MultiSetNode, MultiSetNode> addedMultiSetNodes : actionResult.getAddedMultiSetNodes()
							.entrySet()) {

						MultiSetNode mappedNode = referenceTree.getMapped(addedMultiSetNodes.getKey());

						if (!updatedReferenceNodes.containsKey(mappedNode)) {
							AddActionResult referenceResult = mappedNode.addChild(newNode,
									addedMultiSetNodes.getValue().getPosition(), null);

							MultiSetNode createdReferenceNode = referenceResult.getAddedMultiSetNodes().entrySet()
									.iterator().next().getValue();

							updatedReferenceNodes.put(mappedNode, createdReferenceNode);

						}
						MultiSetNode createdReferenceNode = updatedReferenceNodes.get(mappedNode);
						referenceTree.addMapRecursively(addedMultiSetNodes.getValue(), createdReferenceNode);

					}

				}
			}

		}

		MultiSetTree newTree = null;

		List<Granularity> matchedGranularities = granularities.stream()
				.filter(w -> w.getLayer().equals(newNode.getNodeType())).collect(Collectors.toList());

		if (matchedGranularities.size() == 1) {

			Granularity granularity = matchedGranularities.get(0);

			newTree = MultiSetTree.build(Lists.newArrayList(newNode), granularity);

			for (MultiSetReferenceTree multiSetReferenceTree : integratedTrees.values()) {

				List<MultiSetNode> multiSetNodes = multiSetReferenceTree.getByNode(target);

				for (MultiSetNode multiSetNode : multiSetNodes) {

					if (updatedReferenceNodes.containsKey(multiSetNode)) {
						MultiSetNode addedMultiSetNode = updatedReferenceNodes.get(multiSetNode);
						multiSetReferenceTree.addMap(newTree.getRoots().get(0), addedMultiSetNode);
					} else {
						AddActionResult actionResult = multiSetNode.addChild(newNode, position, null);
						MultiSetNode createdReferenceNode = actionResult.getAddedMultiSetNodes().entrySet().iterator()
								.next().getValue();
						updatedReferenceNodes.put(multiSetNode, createdReferenceNode);
					}
				}

				this.components.get(multiSetReferenceTree).add(newTree);

			}
		} else {

		}

		for (MultiSetReferenceTree multiSetReferenceTree : this.integratedTrees.values()) {

			List<MultiSetNode> multiSetNodes = multiSetReferenceTree.getByNode(target);

			for (MultiSetNode multiSetNode : multiSetNodes) {

				if (!updatedReferenceNodes.containsKey(multiSetNode)) {
					AddActionResult actionResult = multiSetNode.addChild(newNode, position, null);
					MultiSetNode createdReferenceNode = actionResult.getAddedMultiSetNodes().entrySet().iterator()
							.next().getValue();
					updatedReferenceNodes.put(multiSetNode, createdReferenceNode);

				}

			}

		}

		return affectedNodesWithPosition;
	}

	public Map<String, List<MultiSetTree>> getGranularitiesToComponents(MultiSetTree target) {
		Map<String, List<MultiSetTree>> map = new HashMap<String, List<MultiSetTree>>();

		for (MultiSetTree multiSetTree : components.get(target)) {
			if (!map.containsKey(multiSetTree.getGranularity().getLayer())) {
				map.put(multiSetTree.getGranularity().getLayer(), new ArrayList<MultiSetTree>());
			}
			map.get(multiSetTree.getGranularity().getLayer()).add(multiSetTree);

		}
		return map;

	}

	public Map<MultiSetReferenceTree, List<MultiSetTree>> getComponents() {
		return components;
	}

	public List<MultiSetTree> getAllComponents() {

		List<MultiSetTree> allComponents = new ArrayList<MultiSetTree>();
		for (Entry<MultiSetReferenceTree, List<MultiSetTree>> entry : components.entrySet()) {
			allComponents.addAll(entry.getValue());
		}
		return allComponents;

	}

	public Set<Node> delete(Node target) {
		return delete(target, null);
	}

	public Set<Node> delete(Node target, Set<MultiSetNode> selectedReferences) {

		Set<Node> affectedNodes = new HashSet<Node>();
		Set<MultiSetNode> affectedMultiSetNodes = new HashSet<MultiSetNode>();
		Set<MultiSetNode> deletedMultiSetReferenceNodes = new HashSet<MultiSetNode>();

		for (Entry<MultiSetReferenceTree, List<MultiSetTree>> entry : components.entrySet()) {

			MultiSetReferenceTree referenceTree = entry.getKey();
			List<MultiSetTree> componentList = new ArrayList<MultiSetTree>(entry.getValue());

			for (MultiSetTree component : componentList) {
				List<MultiSetNode> multiSetNodes = component.getByNode(target);
				for (MultiSetNode multiSetNode : multiSetNodes) {

					if (affectedMultiSetNodes.contains(multiSetNode)) {
						continue;
					}

					DeleteActionResult actionResult = multiSetNode.delete(selectedReferences);
					affectedNodes.addAll(actionResult.getDeletedNodes());
					affectedMultiSetNodes.add(multiSetNode);
					affectedMultiSetNodes.addAll(actionResult.getDeletedMultiSetNodes().values());

					for (Entry<MultiSetNode, MultiSetNode> innerEntry : actionResult.getDeletedMultiSetNodes()
							.entrySet()) {

						MultiSetNode node = referenceTree.getMapped(innerEntry.getValue());

						if (!deletedMultiSetReferenceNodes.contains(node)) {
							node.delete(null);
							deletedMultiSetReferenceNodes.add(node);
						}

						referenceTree.deleteMap(innerEntry.getValue());

						if (innerEntry.getValue().isRoot()) {
							component.removeRoot(innerEntry.getValue());
						}

					}

				}
				if (component.getNumberOfRoots() == 0) {
					components.get(referenceTree).remove(component);
				}

			}

		}

		for (MultiSetReferenceTree multiSetReferenceTree : this.integratedTrees.values()) {

			List<MultiSetNode> multiSetNodes = multiSetReferenceTree.getByNode(target);

			for (MultiSetNode multiSetNode : multiSetNodes) {

				if (!deletedMultiSetReferenceNodes.contains(multiSetNode)) {
					multiSetNode.delete(null);
				}

			}

		}

		return affectedNodes;
	}

	public void addAttributeValue(Node target, Attribute attribute, Value value) {

		if (!attribute.containsValue(value)) {
			attribute.addAttributeValue(value);
		}

		List<MultiSetAttribute> updatedReferenceAttributes = new ArrayList<MultiSetAttribute>();
		List<MultiSetNode> updatedReferenceNodes = new ArrayList<MultiSetNode>();
		Set<MultiSetAttribute> affectedMultiSetAttributes = new HashSet<MultiSetAttribute>();

		for (Entry<MultiSetReferenceTree, List<MultiSetTree>> entry : components.entrySet()) {

			MultiSetReferenceTree referenceTree = entry.getKey();
			for (MultiSetTree component : entry.getValue()) {
				List<MultiSetNode> multiSetNodes = component.getByNode(target);
				for (MultiSetNode multiSetNode : multiSetNodes) {

					if (affectedMultiSetAttributes.contains(multiSetNode.getByAttribute(attribute))) {
						continue;
					}

					if (multiSetNode.getByAttribute(attribute) != null) {
						Set<MultiSetAttribute> affectedAttributes = multiSetNode.addAttributeValue(attribute, value);

						affectedMultiSetAttributes.addAll(affectedAttributes);

						for (MultiSetAttribute affectedAttribute : affectedAttributes) {
							MultiSetAttribute mappedAttribute = referenceTree.getMapped(affectedAttribute);
							updatedReferenceNodes.add(mappedAttribute.getMultiSetNode());
							if (!updatedReferenceAttributes.contains(mappedAttribute)) {
								mappedAttribute.addValue(value);
								updatedReferenceAttributes.add(mappedAttribute);
							}
						}
					}

				}
			}
		}

		for (MultiSetReferenceTree multiSetReferenceTree : this.integratedTrees.values()) {

			List<MultiSetNode> multiSetNodes = multiSetReferenceTree.getByNode(target);

			for (MultiSetNode multiSetNode : multiSetNodes) {

				if (!updatedReferenceNodes.contains(multiSetNode)) {
					if (multiSetNode.getByAttribute(attribute) != null) {
						multiSetNode.addAttributeValue(attribute, value);
					}
				}

			}

		}

	}

	public void editAttributeValue(Node target, Attribute attribute, Value value) {

		if (!attribute.containsValue(value)) {
			attribute.getAttributeValues().clear();
			attribute.addAttributeValue(value);
		}

		List<MultiSetAttribute> updatedReferenceAttributes = new ArrayList<MultiSetAttribute>();
		List<MultiSetNode> updatedReferenceNodes = new ArrayList<MultiSetNode>();

		Set<MultiSetAttribute> affectedMultiSetAttributes = new HashSet<MultiSetAttribute>();

		for (Entry<MultiSetReferenceTree, List<MultiSetTree>> entry : components.entrySet()) {

			MultiSetReferenceTree referenceTree = entry.getKey();
			for (MultiSetTree component : entry.getValue()) {
				List<MultiSetNode> multiSetNodes = component.getByNode(target);
				for (MultiSetNode multiSetNode : multiSetNodes) {

					if (affectedMultiSetAttributes.contains(multiSetNode.getByAttribute(attribute))) {
						continue;
					}

					if (multiSetNode.getByAttribute(attribute) != null) {
						Set<MultiSetAttribute> affectedAttributes = multiSetNode.editAttributeValue(attribute, value);

						affectedMultiSetAttributes.addAll(affectedAttributes);

						for (MultiSetAttribute affectedAttribute : affectedAttributes) {
							MultiSetAttribute mappedAttribute = referenceTree.getMapped(affectedAttribute);
							updatedReferenceNodes.add(mappedAttribute.getMultiSetNode());

							if (!updatedReferenceAttributes.contains(mappedAttribute)) {
								mappedAttribute.editValue(value);
								updatedReferenceAttributes.add(mappedAttribute);
							}
						}
					}

				}
			}
		}
		for (MultiSetReferenceTree multiSetReferenceTree : this.integratedTrees.values()) {

			List<MultiSetNode> multiSetNodes = multiSetReferenceTree.getByNode(target);

			for (MultiSetNode multiSetNode : multiSetNodes) {

				if (!updatedReferenceNodes.contains(multiSetNode)) {
					if (multiSetNode.getByAttribute(attribute) != null) {
						multiSetNode.editAttributeValue(attribute, value);
					}
				}

			}

		}

	}

	public Set<Node> getAllNodes() {

		List<MultiSetNode> multiSetNodes = new ArrayList<MultiSetNode>();
		Set<Node> nodes = new HashSet<Node>();
		for (Entry<String, MultiSetReferenceTree> entry : integratedTrees.entrySet()) {
			collectMultiSetNodes(entry.getValue().getRoots().get(0), multiSetNodes);
		}

		for (MultiSetNode multiSetNode : multiSetNodes) {
			nodes.add(multiSetNode.getNode());
		}

		return nodes;

	}

	public Set<Node> getAllNodes(String frameType) {

		List<MultiSetNode> multiSetNodes = new ArrayList<MultiSetNode>();
		Set<Node> nodes = new HashSet<Node>();
		for (Entry<String, MultiSetReferenceTree> entry : integratedTrees.entrySet()) {
			collectMultiSetNodes(entry.getValue().getRoots().get(0), multiSetNodes, frameType);
		}

		for (MultiSetNode multiSetNode : multiSetNodes) {
			nodes.add(multiSetNode.getNode());
		}

		return nodes;

	}

	private void collectMultiSetNodes(MultiSetNode multiSetNode, List<MultiSetNode> multiSetNodes, String frameType) {

		if (multiSetNode.getNode().getNodeType().equals(frameType)) {

			multiSetNodes.add(multiSetNode);
			for (MultiSetNode child : multiSetNode.getChildren()) {

				collectMultiSetNodes(child, multiSetNodes);

			}
		}

		for (MultiSetNode child : multiSetNode.getChildren()) {

			collectMultiSetNodes(child, multiSetNodes, frameType);

		}

	}

	private void collectMultiSetNodes(MultiSetNode multiSetNode, List<MultiSetNode> multiSetNodes) {

		multiSetNodes.add(multiSetNode);

		for (MultiSetNode child : multiSetNode.getChildren()) {

			collectMultiSetNodes(child, multiSetNodes);

		}

	}

	public void move(Node target, int position, Set<MultiSetNode> selectedReferences) {

		List<MultiSetNode> updatedReferenceNodes = new ArrayList<MultiSetNode>();
		Set<MultiSetNode> affectedMultiSetNodes = new HashSet<MultiSetNode>();

		for (Entry<MultiSetReferenceTree, List<MultiSetTree>> entry : components.entrySet()) {

			MultiSetReferenceTree referenceTree = entry.getKey();
			List<MultiSetTree> componentList = new ArrayList<MultiSetTree>(entry.getValue());

			for (MultiSetTree component : componentList) {
				List<MultiSetNode> multiSetNodes = component.getByNode(target);
				for (MultiSetNode multiSetNode : multiSetNodes) {

					if (affectedMultiSetNodes.contains(multiSetNode)) {
						continue;
					}

					MoveActionResult actionResult = multiSetNode.move(position, selectedReferences);
					affectedMultiSetNodes.add(multiSetNode);
					affectedMultiSetNodes.addAll(actionResult.getMovedNodesWithPosition().keySet());

					MultiSetNode node = referenceTree.getMapped(multiSetNode);

					node.move(position, null);

					for (Entry<MultiSetNode, Integer> positionEntry : actionResult.getMovedNodesWithPosition()
							.entrySet()) {

						node = referenceTree.getMapped(positionEntry.getKey());
						updatedReferenceNodes.add(node);
						node.move(positionEntry.getValue(), null);
					}

				}

			}

		}

		for (MultiSetReferenceTree multiSetReferenceTree : this.integratedTrees.values()) {

			List<MultiSetNode> multiSetNodes = multiSetReferenceTree.getByNode(target);

			for (MultiSetNode multiSetNode : multiSetNodes) {
				if (!updatedReferenceNodes.contains(multiSetNode)) {
					multiSetNode.move(position, null);
				}

			}

		}

	}

	public void editAttributeKey(Node target, Attribute attribute, String key) {

		if (!attribute.getAttributeKey().equals(key)) {
			attribute.setAttributeKey(key);
		}

		List<MultiSetAttribute> updatedReferenceAttributes = new ArrayList<MultiSetAttribute>();
		List<MultiSetNode> updatedReferenceNodes = new ArrayList<MultiSetNode>();
		Set<MultiSetAttribute> affectedMultiSetAttributes = new HashSet<MultiSetAttribute>();

		for (Entry<MultiSetReferenceTree, List<MultiSetTree>> entry : components.entrySet()) {

			MultiSetReferenceTree referenceTree = entry.getKey();
			for (MultiSetTree component : entry.getValue()) {
				List<MultiSetNode> multiSetNodes = component.getByNode(target);
				for (MultiSetNode multiSetNode : multiSetNodes) {

					if (affectedMultiSetAttributes.contains(multiSetNode.getByAttribute(attribute))) {
						continue;
					}

					if (multiSetNode.getByAttribute(attribute) != null) {
						Set<MultiSetAttribute> affectedAttributes = multiSetNode.editAttributeKey(attribute, key);
						affectedMultiSetAttributes.addAll(affectedAttributes);

						for (MultiSetAttribute affectedAttribute : affectedAttributes) {
							MultiSetAttribute mappedAttribute = referenceTree.getMapped(affectedAttribute);
							updatedReferenceNodes.add(mappedAttribute.getMultiSetNode());

							if (!updatedReferenceAttributes.contains(mappedAttribute)) {
								mappedAttribute.editKey(key);
								updatedReferenceAttributes.add(mappedAttribute);
							}
						}
					}

				}
			}
		}
		for (MultiSetReferenceTree multiSetReferenceTree : this.integratedTrees.values()) {

			List<MultiSetNode> multiSetNodes = multiSetReferenceTree.getByNode(target);

			for (MultiSetNode multiSetNode : multiSetNodes) {

				if (!updatedReferenceNodes.contains(multiSetNode)) {
					if (multiSetNode.getByAttribute(attribute) != null) {
						multiSetNode.editAttributeKey(attribute, key);
					}
				}

			}

		}

	}

	public void addAttribute(Node target, Attribute attribute) {
		addAttribute(target, attribute, null);
	}

	public void addAttribute(Node target, Attribute attribute, Set<MultiSetNode> selectedReferences) {

		if (!target.getAttributes().contains(attribute)) {
			target.addAttribute(attribute);
		}

		Map<MultiSetNode, MultiSetAttribute> updatedReferenceNodes = new HashMap<MultiSetNode, MultiSetAttribute>();

		Set<MultiSetNode> affectedMultiSetNodes = new HashSet<MultiSetNode>();

		for (Entry<MultiSetReferenceTree, List<MultiSetTree>> entry : components.entrySet()) {

			MultiSetReferenceTree referenceTree = entry.getKey();
			for (MultiSetTree component : entry.getValue()) {
				List<MultiSetNode> multiSetNodes = component.getByNode(target);
				for (MultiSetNode multiSetNode : multiSetNodes) {

					if (affectedMultiSetNodes.contains(multiSetNode)) {
						continue;
					}

					AttributeActionResult actionResult = multiSetNode.addAttribute(attribute, selectedReferences);
					affectedMultiSetNodes.addAll(actionResult.getAffectedMultiSetNodes());
					affectedMultiSetNodes.add(multiSetNode);

					for (Entry<MultiSetNode, MultiSetAttribute> innerEntry : actionResult.getAffectedAttributes()
							.entrySet()) {

						MultiSetNode node = referenceTree.getMapped(innerEntry.getKey());

						if (!updatedReferenceNodes.containsKey(node)) {
							AttributeActionResult actionResult2 = node.addAttribute(attribute, null);

							MultiSetAttribute newAttribute = actionResult2.getAffectedAttributes().entrySet().iterator()
									.next().getValue();

							referenceTree.addMap(innerEntry.getValue(), newAttribute);
							updatedReferenceNodes.put(node, newAttribute);
						}
						MultiSetAttribute multiSetAttribute = updatedReferenceNodes.get(node);
						referenceTree.addMap(innerEntry.getValue(), multiSetAttribute);

					}

				}
			}

		}
		for (MultiSetReferenceTree multiSetReferenceTree : this.integratedTrees.values()) {

			List<MultiSetNode> multiSetNodes = multiSetReferenceTree.getByNode(target);

			for (MultiSetNode multiSetNode : multiSetNodes) {
				if (!updatedReferenceNodes.containsKey(multiSetNode)) {
					multiSetNode.addAttribute(attribute, null);
				}

			}

		}

	}

	public void removeAttribute(Node target, Attribute attribute) {
		removeAttribute(target, attribute, null);
	}

	public void removeAttribute(Node target, Attribute attribute, Set<MultiSetNode> selectedReferences) {

		if (target.getAttributes().contains(attribute)) {
			target.getAttributes().remove(attribute);
		}

		Map<MultiSetNode, MultiSetAttribute> updatedReferenceNodes = new HashMap<MultiSetNode, MultiSetAttribute>();
		Set<MultiSetNode> affectedMultiSetNodes = new HashSet<MultiSetNode>();

		for (Entry<MultiSetReferenceTree, List<MultiSetTree>> entry : components.entrySet()) {

			MultiSetReferenceTree referenceTree = entry.getKey();
			for (MultiSetTree component : entry.getValue()) {
				List<MultiSetNode> multiSetNodes = component.getByNode(target);
				for (MultiSetNode multiSetNode : multiSetNodes) {

					if (affectedMultiSetNodes.contains(multiSetNode)) {
						continue;
					}

					if (multiSetNode.getByAttribute(attribute) != null) {
						AttributeActionResult actionResult = multiSetNode.removeAttribute(attribute,
								selectedReferences);
						affectedMultiSetNodes.addAll(actionResult.getAffectedMultiSetNodes());
						affectedMultiSetNodes.add(multiSetNode);

						for (Entry<MultiSetNode, MultiSetAttribute> innerEntry : actionResult.getAffectedAttributes()
								.entrySet()) {
							MultiSetNode node = referenceTree.getMapped(innerEntry.getKey());

							if (!updatedReferenceNodes.containsKey(node)) {

								MultiSetAttribute attributeToRemove = referenceTree.getMapped(innerEntry.getValue());

								node.removeAttribute(attributeToRemove.getAttribute(), null);
								updatedReferenceNodes.put(node, attributeToRemove);

							}

							referenceTree.deleteMap(innerEntry.getValue());

						}
					}

				}

			}
		}
		for (MultiSetReferenceTree multiSetReferenceTree : this.integratedTrees.values()) {

			List<MultiSetNode> multiSetNodes = multiSetReferenceTree.getByNode(target);

			for (MultiSetNode multiSetNode : multiSetNodes) {

				if (!updatedReferenceNodes.containsKey(multiSetNode)) {
					if (multiSetNode.getByAttribute(attribute) != null) {
						multiSetNode.removeAttribute(attribute, null);
					}
				}

			}

		}

	}

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

	public void merge(CloneModel cloneModel) {

		CompareEngineHierarchical compareEngine = new CompareEngineHierarchical(new SortingMatcher(),
				new MetricImpl("test"));

		for (Entry<MultiSetReferenceTree, List<MultiSetTree>> entry : cloneModel.components.entrySet()) {

			this.components.put(entry.getKey(), new ArrayList<MultiSetTree>());

			for (MultiSetTree component : entry.getValue()) {
				component.integrate(tree, cloneModel.tree);
				this.components.get(entry.getKey()).add(component);
			}

			entry.getKey().integrate(tree, cloneModel.tree);

		}

		tree = compareEngine.compare(tree, cloneModel.tree);

		for (Entry<String, MultiSetReferenceTree> entry : cloneModel.integratedTrees.entrySet()) {

			String name = entry.getKey();
			MultiSetReferenceTree multiSetTree = entry.getValue();

			int counter = 1;
			String currentName = name;
			while (integratedTrees.containsKey(currentName)) {
				currentName = name + "_" + counter;
				counter++;
			}

			integratedTrees.put(currentName, multiSetTree);
		}
	}

	public List<Tree> restoreIntegratedTrees() {

		List<Tree> trees = new ArrayList<Tree>();
		for (Entry<String, MultiSetReferenceTree> entry : integratedTrees.entrySet()) {

			String name = entry.getKey();
			MultiSetReferenceTree multiSetTree = entry.getValue();

			Tree tree = multiSetTree.restoreTrees().get(0);
			Tree newTree = new TreeImpl(name, tree.getRoot());
			trees.add(newTree);
		}
		return trees;

	}

	public void mergeTrees(MultiSetTree original, MultiSetTree multiSetTree1, MultiSetTree multiSetTree2) {

		multiSetTree1.merge(multiSetTree2);
		List<MultiSetTree> list = components.get(original);
		list.remove(multiSetTree2);

	}

	public CloneModel(List<MultiSetTree> components, MultiSetReferenceTree integratedTree, String name,
			Set<Granularity> granularities) {
		super();

		this.components = new HashMap<MultiSetReferenceTree, List<MultiSetTree>>();
		this.components.put(integratedTree, components);

		this.integratedTrees = new HashMap<String, MultiSetReferenceTree>();
		this.integratedTrees.put(name, integratedTree);
		this.granularities = granularities;

	}

}
