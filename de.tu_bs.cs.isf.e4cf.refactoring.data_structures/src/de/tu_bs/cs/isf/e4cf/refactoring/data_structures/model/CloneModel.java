package de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.CompareEngineHierarchical;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import de.tu_bs.cs.isf.e4cf.compare.matcher.SortingMatcher;
import de.tu_bs.cs.isf.e4cf.compare.metric.MetricImpl;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.result.AddAttributeResult;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.result.AddAttributeValueResult;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.result.AddChildResult;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.result.DeleteAttributeResult;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.result.DeleteResult;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.result.EditAttributeKeyResult;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.result.EditAttributeValueResult;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.result.MoveResult;

public class CloneModel {

	private Tree tree;

	private Map<ReferenceTree, List<MultiSetTree>> components;

	private CompareEngineHierarchical compareEngine;

	private Map<String, ReferenceTree> integratedTrees;

	private Set<Granularity> granularities;

	public Set<Granularity> getGranularities() {
		return granularities;
	}

	public CloneModel(List<MultiSetTree> components, ReferenceTree integratedTree, String name,
			Set<Granularity> granularities) {
		super();

		this.compareEngine = new CompareEngineHierarchical(new SortingMatcher(), new MetricImpl("test"));

		this.components = new HashMap<ReferenceTree, List<MultiSetTree>>();
		this.components.put(integratedTree, components);

		this.integratedTrees = new HashMap<String, ReferenceTree>();
		this.integratedTrees.put(name, integratedTree);
		this.granularities = granularities;

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

	public Set<Node> getAllNodes() {

		List<MultiSetNode> multiSetNodes = new ArrayList<MultiSetNode>();
		Set<Node> nodes = new HashSet<Node>();
		for (Entry<String, ReferenceTree> entry : integratedTrees.entrySet()) {
			collectMultiSetNodes(entry.getValue().getRoots().get(0), multiSetNodes);
		}

		for (MultiSetNode multiSetNode : multiSetNodes) {
			nodes.add(multiSetNode.getNode());
		}

		return nodes;

	}

	public List<MultiSetTree> getAllComponents() {

		List<MultiSetTree> allComponents = new ArrayList<MultiSetTree>();
		for (Entry<ReferenceTree, List<MultiSetTree>> entry : components.entrySet()) {
			allComponents.addAll(entry.getValue());
		}
		return allComponents;

	}

	public Set<Node> getAllNodes(String frameType) {

		List<MultiSetNode> multiSetNodes = new ArrayList<MultiSetNode>();
		Set<Node> nodes = new HashSet<Node>();
		for (Entry<String, ReferenceTree> entry : integratedTrees.entrySet()) {
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

	public void setGranularities(Set<Granularity> granularities) {
		this.granularities = granularities;
	}

	public Set<MultiSetNode> collectReferenceNodes(Node node) {

		Set<MultiSetNode> references = new HashSet<MultiSetNode>();

		for (Entry<ReferenceTree, List<MultiSetTree>> referenceComponentEntry : components.entrySet()) {
			for (MultiSetTree component : referenceComponentEntry.getValue()) {

				MultiSetNode multiSetNode = component.getByNode(node);

				if (multiSetNode != null) {
					references.addAll(multiSetNode.getReferences());
				}

			}
		}

		return references;

	}

	public void move(Node target, int position) {
		move(target, position, null);
	}

	public void move(Node target, int position, Set<MultiSetNode> selectedReferences) {
		for (Entry<ReferenceTree, List<MultiSetTree>> referenceComponentEntry : components.entrySet()) {
			ReferenceTree referenceTree = referenceComponentEntry.getKey();

			List<MoveResult> results = new ArrayList<MoveResult>();

			for (MultiSetTree component : referenceComponentEntry.getValue()) {

				MultiSetNode multiSetNode = component.getByNode(target);

				if (multiSetNode != null) {
					results.addAll(multiSetNode.move(position, selectedReferences));
				}
			}
			referenceTree.move(results);
		}
	}

	public void addChild(Node target, Node child, int position) {
		addChild(target, child, position, null);
	}

	public void addChild(Node target, Node child, int position, Set<MultiSetNode> selectedReferences) {

		boolean hasComponentGranularity = granularities.stream().filter(w -> w.getLayer().equals(child.getNodeType()))
				.toArray().length != 0;
		Map<Node, Node> parentToChild = new HashMap<Node, Node>();
		for (Entry<ReferenceTree, List<MultiSetTree>> referenceComponentEntry : components.entrySet()) {
			ReferenceTree referenceTree = referenceComponentEntry.getKey();
			List<AddChildResult> results = new ArrayList<AddChildResult>();

			List<MultiSetTree> componentsToAdd = new ArrayList<MultiSetTree>();

			for (MultiSetTree component : referenceComponentEntry.getValue()) {

				MultiSetNode multiSetNode = component.getByNode(target);

				if (multiSetNode != null) {
					List<AddChildResult> provResults = multiSetNode.addChild(child, position, parentToChild,
							selectedReferences);

					if (hasComponentGranularity) {
						List<Node> nodes = new ArrayList<Node>();
						for (AddChildResult addChildResult : provResults) {
							nodes.add(addChildResult.getChild());
						}
						MultiSetTree newComponent = MultiSetTree.create(nodes,
								new Granularity(child.getNodeType(), true));
						componentsToAdd.add(newComponent);
					}

					results.addAll(provResults);
				}
			}

			if (results.isEmpty() && referenceTree.getByNode(target) != null) {
				Node childClone = child.cloneNode();
				parentToChild.put(target, childClone);
				childClone.setParent(target);
				target.getChildren().add(position, childClone);

				results.add(new AddChildResult(target, position, childClone));

				if (hasComponentGranularity) {
					MultiSetTree newComponent = MultiSetTree.create(Arrays.asList(childClone),
							new Granularity(child.getNodeType(), true));
					componentsToAdd.add(newComponent);
				}

			}
			referenceTree.addChild(results, parentToChild);
			referenceComponentEntry.getValue().addAll(componentsToAdd);
		}

	}

	public void delete(Node target) {
		delete(target, null);
	}

	public void delete(Node target, Set<MultiSetNode> selectedReferences) {
		Set<Node> deletedNodes = new HashSet<Node>();

		for (Entry<ReferenceTree, List<MultiSetTree>> referenceComponentEntry : components.entrySet()) {

			ReferenceTree referenceTree = referenceComponentEntry.getKey();

			List<DeleteResult> results = new ArrayList<DeleteResult>();
			List<MultiSetTree> componentsToDelete = new ArrayList<MultiSetTree>();

			for (MultiSetTree component : referenceComponentEntry.getValue()) {

				MultiSetNode multiSetNode = component.getByNode(target);
				if (multiSetNode != null) {
					if (multiSetNode.isRoot()) {
						componentsToDelete.add(component);
					}
					results.addAll(multiSetNode.delete(deletedNodes, selectedReferences));
				}
			}
			if (results.isEmpty() && referenceTree.getByNode(target) != null) {
				results.add(new DeleteResult(target));
			}

			referenceTree.delete(results, deletedNodes);
			referenceComponentEntry.getValue().removeAll(componentsToDelete);
		}

		Set<Node> nodesNotToDelete = new HashSet<Node>();
		for (Node node : deletedNodes) {
			for (ReferenceTree referenceTree : integratedTrees.values()) {
				if (referenceTree.getByNode(node) != null) {
					nodesNotToDelete.add(node);
				}
			}
		}

		deletedNodes.removeAll(nodesNotToDelete);

		for (Node node : deletedNodes) {
			node.getParent().getChildren().remove(node);
		}
	}

	public void addAttribute(Node target, Attribute attribute) {
		addAttribute(target, attribute, null);
	}

	public void addAttribute(Node target, Attribute attribute, Set<MultiSetNode> selectedReferences) {
		Map<Node, Attribute> nodeToAttribute = new HashMap<Node, Attribute>();

		for (Entry<ReferenceTree, List<MultiSetTree>> referenceComponentEntry : components.entrySet()) {

			ReferenceTree referenceTree = referenceComponentEntry.getKey();

			List<AddAttributeResult> results = new ArrayList<AddAttributeResult>();

			for (MultiSetTree component : referenceComponentEntry.getValue()) {

				MultiSetNode multiSetNode = component.getByNode(target);

				if (multiSetNode != null) {
					results.addAll(multiSetNode.addAttribute(attribute, nodeToAttribute, selectedReferences));
				}
			}
			if (results.isEmpty() && referenceTree.getByNode(target) != null) {
				results.add(new AddAttributeResult(target, attribute));
			}

			referenceTree.addAttribute(results, nodeToAttribute);
		}
	}

	public void editAttributeKey(Node target, Attribute attribute, String newKey) {
		editAttributeKey(target, attribute, newKey, null);
	}

	public void editAttributeKey(Node target, Attribute attribute, String newKey,
			Set<MultiSetAttribute> selectedReferences) {

		Set<Attribute> editedAttributes = new HashSet<Attribute>();

		for (Entry<ReferenceTree, List<MultiSetTree>> referenceComponentEntry : components.entrySet()) {

			ReferenceTree referenceTree = referenceComponentEntry.getKey();

			List<EditAttributeKeyResult> results = new ArrayList<EditAttributeKeyResult>();

			for (MultiSetTree component : referenceComponentEntry.getValue()) {

				MultiSetNode multiSetNode = component.getByNode(target);

				if (multiSetNode != null) {
					results.addAll(
							multiSetNode.editAttributeKey(attribute, newKey, editedAttributes, selectedReferences));
				}
			}
			if (results.isEmpty() && referenceTree.getByNode(target) != null && referenceTree.getByAttribute(attribute) != null) {
				results.add(new EditAttributeKeyResult(target, attribute, newKey));
			}

			referenceTree.editAttributeKey(results, editedAttributes);
		}

		Set<Attribute> attributesToUpdate = new HashSet<Attribute>();
		for (Attribute editedAttribute : editedAttributes) {
			for (ReferenceTree referenceTree : integratedTrees.values()) {

				MultiSetAttribute referenceAttribute = referenceTree.getByAttribute(editedAttribute);
				if (referenceAttribute != null) {

					if (referenceAttribute.getKey().equals(newKey)) {
						attributesToUpdate.add(editedAttribute);
					}

				}
			}
		}

		for (Attribute attributeToUpdate : attributesToUpdate) {
			attributeToUpdate.setAttributeKey(newKey);
		}

	}

	public void addAttributeValue(Node target, Attribute attribute, Value value) {
		addAttributeValue(target, attribute, value, null);
	}

	public void addAttributeValue(Node target, Attribute attribute, Value value,
			Set<MultiSetAttribute> selectedReferences) {

		Map<Attribute, Value> attributeToValue = new HashMap<Attribute, Value>();

		for (Entry<ReferenceTree, List<MultiSetTree>> referenceComponentEntry : components.entrySet()) {

			ReferenceTree referenceTree = referenceComponentEntry.getKey();
			List<AddAttributeValueResult> results = new ArrayList<AddAttributeValueResult>();
			for (MultiSetTree component : referenceComponentEntry.getValue()) {
				MultiSetNode multiSetNode = component.getByNode(target);
				if (multiSetNode != null) {
					results.addAll(
							multiSetNode.addAttributeValue(attribute, value, attributeToValue, selectedReferences));
				}

			}
			if (results.isEmpty() && referenceTree.getByNode(target) != null && referenceTree.getByAttribute(attribute) != null) {
				results.add(new AddAttributeValueResult(attribute, value, target));
			}
			referenceTree.addAttributeValue(results, attributeToValue);

		}

		for (Entry<Attribute, Value> entry : attributeToValue.entrySet()) {
			if (!entry.getKey().containsValue(entry.getValue())) {
				entry.getKey().addAttributeValue(entry.getValue());
			}

		}

	}

	public void editAttributeValue(Node target, Attribute attribute, Value value) {
		editAttributeValue(target, attribute, value, null);
	}

	public void editAttributeValue(Node target, Attribute attribute, Value value,
			Set<MultiSetAttribute> selectedReferences) {

		Map<Attribute, Value> attributeToValue = new HashMap<Attribute, Value>();

		for (Entry<ReferenceTree, List<MultiSetTree>> referenceComponentEntry : components.entrySet()) {

			ReferenceTree referenceTree = referenceComponentEntry.getKey();

			List<EditAttributeValueResult> results = new ArrayList<EditAttributeValueResult>();

			for (MultiSetTree component : referenceComponentEntry.getValue()) {

				MultiSetNode multiSetNode = component.getByNode(target);

				if (multiSetNode != null) {
					results.addAll(
							multiSetNode.editAttributeValue(attribute, value, attributeToValue, selectedReferences));
				}
			}
			if (results.isEmpty() && referenceTree.getByNode(target) != null && referenceTree.getByAttribute(attribute) != null) {
				results.add(new EditAttributeValueResult(target, attribute, value));
			}

			referenceTree.editAttributeValue(results, attributeToValue);
		}

		Map<Attribute, Value> attributesToAdd = new HashMap<Attribute, Value>();
		for (Entry<Attribute, Value> entry : attributeToValue.entrySet()) {

			for (ReferenceTree referenceTree : integratedTrees.values()) {

				MultiSetAttribute multiSetAttribute = referenceTree.getByAttribute(entry.getKey());

				if (multiSetAttribute != null) {
					if (!multiSetAttribute.containsValue(entry.getValue())) {
						attributesToAdd.put(entry.getKey(), entry.getValue());
					}
				}

			}

		}

		for (Entry<Attribute, Value> entry : attributesToAdd.entrySet()) {
			entry.getKey().addAttributeValue(entry.getValue());
		}

		for (Entry<Attribute, Value> entry : attributeToValue.entrySet()) {
			if (!attributesToAdd.containsKey(entry.getKey())) {
				entry.getKey().getAttributeValues().clear();
				entry.getKey().addAttributeValue(entry.getValue());
			}
		}

	}

	public void deleteAttribute(Node target, Attribute attribute) {
		deleteAttribute(target, attribute, null);
	}

	public void deleteAttribute(Node target, Attribute attribute, Set<MultiSetAttribute> selectedReferences) {
		Set<Attribute> deletedAttributes = new HashSet<Attribute>();

		for (Entry<ReferenceTree, List<MultiSetTree>> referenceComponentEntry : components.entrySet()) {

			ReferenceTree referenceTree = referenceComponentEntry.getKey();

			List<DeleteAttributeResult> results = new ArrayList<DeleteAttributeResult>();

			for (MultiSetTree component : referenceComponentEntry.getValue()) {

				MultiSetNode multiSetNode = component.getByNode(target);

				if (multiSetNode != null && referenceTree.getByNode(target) != null && referenceTree.getByAttribute(attribute) != null) {
					results.addAll(multiSetNode.deleteAttribute(attribute, deletedAttributes, selectedReferences));
				}
			}
			if (results.isEmpty()) {
				results.add(new DeleteAttributeResult(target, attribute));
			}
			referenceTree.deleteAttribute(results, deletedAttributes);
		}

		Set<Attribute> attributesNotToDelete = new HashSet<Attribute>();
		for (Attribute deletedAttribute : deletedAttributes) {
			for (ReferenceTree referenceTree : integratedTrees.values()) {
				if (referenceTree.getByAttribute(deletedAttribute) != null) {
					attributesNotToDelete.add(deletedAttribute);
				}
			}
		}

		deletedAttributes.removeAll(attributesNotToDelete);

		for (Attribute deletedAttribute : deletedAttributes) {
			deleteAttributeRecursively(tree.getRoot(), deletedAttribute);
		}
	}

	private void deleteAttributeRecursively(Node node, Attribute attribute) {
		if (node.getAttributes().contains(attribute)) {
			node.getAttributes().remove(attribute);
		}
		for (Node child : node.getChildren()) {
			deleteAttributeRecursively(child, attribute);
		}
	}

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

	public Map<ReferenceTree, List<MultiSetTree>> getComponents() {
		return components;
	}

	public void merge(CloneModel cloneModel) {

		for (Entry<ReferenceTree, List<MultiSetTree>> entry : cloneModel.components.entrySet()) {

			this.components.put(entry.getKey(), new ArrayList<MultiSetTree>());

			for (MultiSetTree component : entry.getValue()) {
				component.integrate(tree, cloneModel.tree);
				this.components.get(entry.getKey()).add(component);
			}

			entry.getKey().integrate(tree, cloneModel.tree);

		}

		tree = compareEngine.compare(tree, cloneModel.tree);

		for (Entry<String, ReferenceTree> entry : cloneModel.integratedTrees.entrySet()) {

			String name = entry.getKey();
			ReferenceTree multiSetTree = entry.getValue();

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
		for (Entry<String, ReferenceTree> entry : integratedTrees.entrySet()) {

			String name = entry.getKey();
			ReferenceTree multiSetTree = entry.getValue();

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

}
