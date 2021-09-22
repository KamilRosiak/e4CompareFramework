package de.tu_bs.cs.isf.e4cf.refactoring.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import de.tu_bs.cs.isf.e4cf.refactoring.util.SynchronizationUtil;

public class MultiSetNode {

	private boolean isRoot;

	public void setRoot(boolean root) {
		isRoot = root;
	}

	public boolean isRoot() {
		return isRoot;
	}

	private Node node;

	private List<MultiSetAttribute> attributes;

	private Set<MultiSetNode> references;

	private List<MultiSetNode> children;

	private MultiSetNode parent;

	public MultiSetNode(Node node) {
		super();
		this.node = node;
		references = new HashSet<MultiSetNode>();
		children = new ArrayList<MultiSetNode>();
		attributes = new ArrayList<MultiSetAttribute>();

		for (Attribute attribute : node.getAttributes()) {
			attributes.add(new MultiSetAttribute(attribute, this));
		}

	}

	public MultiSetNode(Node node, boolean isRoot) {
		this(node);
		this.isRoot = isRoot;

	}

	public void addReference(MultiSetNode reference) {
		references.add(reference);
	}

	public MultiSetAttribute getByAttribute(Attribute attribute) {
		for (MultiSetAttribute multiSetAttribute : attributes) {
			if (multiSetAttribute.getAttribute().equals(attribute)) {
				return multiSetAttribute;
			}
		}
		return null;

	}

	public MultiSetAttribute getByAttributeKey(String attributeKey) {
		for (MultiSetAttribute multiSetAttribute : attributes) {
			if (multiSetAttribute.getKey().equals(attributeKey)) {
				return multiSetAttribute;
			}
		}
		return null;

	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public static MultiSetNode build(Node node) {

		MultiSetNode multiSetNode = new MultiSetNode(node);

		for (Node child : node.getChildren()) {
			MultiSetNode childMultiSetNode = build(child);
			childMultiSetNode.parent = multiSetNode;
			multiSetNode.children.add(childMultiSetNode);
		}

		return multiSetNode;
	}

	public List<MultiSetNode> getByNode(Node node) {

		List<MultiSetNode> multiSetNodes = new ArrayList<MultiSetNode>();
		if (this.node.equals(node)) {
			multiSetNodes.add(this);
		}

		for (MultiSetNode multiSetNodeChild : children) {
			multiSetNodes.addAll(multiSetNodeChild.getByNode(node));
		}

		return multiSetNodes;

	}

	public List<MultiSetNode> getByGranularity(String granularity) {

		List<MultiSetNode> multiSetNodes = new ArrayList<MultiSetNode>();
		if (this.node.getNodeType().equals(granularity)) {
			multiSetNodes.add(this);
		}

		for (MultiSetNode multiSetNodeChild : children) {
			multiSetNodes.addAll(multiSetNodeChild.getByNode(node));
		}

		return multiSetNodes;

	}

	public int getPosition() {
		return parent.children.indexOf(this);
	}

	public DeleteActionResult delete(Set<MultiSetNode> selectedReferences) {

		Set<Node> deletedNodes = new HashSet<Node>();
		Map<MultiSetNode, MultiSetNode> deletedMultiSetNodes = new HashMap<MultiSetNode, MultiSetNode>();

		if (!isRoot) {
			parent.children.remove(this);
			deletedMultiSetNodes.put(parent, this);
		} else {
			deletedMultiSetNodes.put(new MultiSetNode(node), this);
		}

		List<MultiSetNode> exclude = new ArrayList<MultiSetNode>();

		exclude.add(this);
		Set<MultiSetNode> referenceList = selectedReferences == null ? references : selectedReferences;

		for (MultiSetNode reference : referenceList) {
			if (!exclude.contains(reference)) {
				reference.delete(exclude, deletedNodes, deletedMultiSetNodes);
			}
		}

		for (MultiSetNode deletedNode : deletedMultiSetNodes.values()) {

			deleteRecursively(deletedNode);

		}

		return new DeleteActionResult(deletedNodes, deletedMultiSetNodes);
	}

	private void delete(List<MultiSetNode> exclude, Set<Node> deletedNodes,
			Map<MultiSetNode, MultiSetNode> deletedMultiSetNodes) {

		if (isRoot) {
			node.getParent().getChildren().remove(node);
			deletedMultiSetNodes.put(new MultiSetNode(node), this);
		} else {
			parent.node.getChildren().remove(this.node);
			parent.children.remove(this);
			deletedMultiSetNodes.put(parent, this);
		}

		deletedNodes.add(node);

		exclude.add(this);
		for (MultiSetNode reference : references) {
			if (!exclude.contains(reference)) {
				reference.delete(exclude, deletedNodes, deletedMultiSetNodes);
			}
		}

	}

	private void deleteRecursively(MultiSetNode node) {
		for (MultiSetNode reference : node.getReferences()) {
			reference.getReferences().remove(node);
		}

		for (MultiSetAttribute attribute : node.getAttributes()) {
			for (MultiSetAttribute reference : attribute.getReferences()) {
				reference.getReferences().remove(attribute);
			}
		}

		for (MultiSetNode child : node.getChildren()) {
			deleteRecursively(child);
		}

	}

	public Set<MultiSetNode> getReferences() {
		return references;
	}

	public MoveActionResult move(int position, Set<MultiSetNode> selectedReferences) {

		Map<MultiSetNode, Integer> moveMultiSetNodesWithPosition = new HashMap<MultiSetNode, Integer>();

		int currentPosition = this.getPosition();
		parent.children.remove(currentPosition);
		parent.children.add(position, this);

		List<MultiSetNode> exclude = new ArrayList<MultiSetNode>();
		exclude.add(this);

		Set<MultiSetNode> referenceList = selectedReferences == null ? references: selectedReferences;
		
		for (MultiSetNode reference : referenceList) {
			if (!exclude.contains(reference)) {

				List<MultiSetNode> subsequence = SynchronizationUtil
						.findLongestCommonSubsequence2(reference.parent.children, this.parent.children);

				List<MultiSetNode> childList = new ArrayList<MultiSetNode>(reference.parent.children);

				for (MultiSetNode child : childList) {

					if (!subsequence.contains(child)) {

						int referencePosition = -1;
						for (MultiSetNode childReference : child.references) {

							if (childReference.equals(this)) {
								referencePosition = childReference.getPosition();
								break;
							}

						}

						if (referencePosition != -1) {
							subsequence.add(child);
							int currentReferencePosition = child.getPosition();
							reference.parent.children.remove(currentReferencePosition);
							reference.parent.children.add(referencePosition, reference);
							moveMultiSetNodesWithPosition.put(reference, referencePosition);
						}

					}

				}

			}
		}

		return new MoveActionResult(moveMultiSetNodesWithPosition);

	}

	public AddActionResult addChild(Node node, int position, Set<MultiSetNode> selectedReferences) {

		Set<Node> affectedNodes = new HashSet<Node>();
		Map<Node, Integer> affectedNodesWithPosition = new HashMap<Node, Integer>();
		Map<MultiSetNode, MultiSetNode> addedMultiSetNodes = new HashMap<MultiSetNode, MultiSetNode>();

		MultiSetNode multiSetNode = new MultiSetNode(node);
		multiSetNode.parent = this;
		if (position == -1) {
			children.add(multiSetNode);
		} else {

			if (position > children.size()) {
				position = children.size();
			}

			children.add(position, multiSetNode);
		}

		addChildRecursively(multiSetNode, node);

		List<MultiSetNode> exclude = new ArrayList<MultiSetNode>();
		Set<MultiSetNode> createdNodes = new HashSet<MultiSetNode>();
		createdNodes.add(multiSetNode);
		exclude.add(this);
		addedMultiSetNodes.put(this, multiSetNode);

		MultiSetNode predecessor = (this.children.size() == 1 || multiSetNode.getPosition() == 0) ? null
				: this.children.get(multiSetNode.getPosition() - 1);

		Set<MultiSetNode> referenceList = selectedReferences == null ? references : selectedReferences;

		for (MultiSetNode reference : referenceList) {
			if (!exclude.contains(reference)) {
				reference.addChild(node, exclude, affectedNodes, createdNodes, predecessor, affectedNodesWithPosition,
						addedMultiSetNodes);
			}
		}

		for (MultiSetNode multiSetNode1 : createdNodes) {
			for (MultiSetNode multiSetNode2 : createdNodes) {
				if (!multiSetNode1.equals(multiSetNode2)) {
					updateReferences(multiSetNode1, multiSetNode2);
				}

			}
		}

		return new AddActionResult(affectedNodes, createdNodes, affectedNodesWithPosition, addedMultiSetNodes);

	}

	private void addChildRecursively(MultiSetNode multiSetNode, Node node) {

		for (Node child : node.getChildren()) {

			MultiSetNode childMultiSetNode = new MultiSetNode(child);
			childMultiSetNode.parent = multiSetNode;
			multiSetNode.getChildren().add(childMultiSetNode);
			addChildRecursively(childMultiSetNode, child);

		}

	}

	private void updateReferences(MultiSetNode multiSetNode1, MultiSetNode multiSetNode2) {

		multiSetNode1.addReference(multiSetNode2);

		for (MultiSetAttribute multiSetAttribute1 : multiSetNode1.getAttributes()) {

			for (MultiSetAttribute multiSetAttribute2 : multiSetNode2.getAttributes()) {

				if (multiSetAttribute1.getAttribute().equals(multiSetAttribute2.getAttribute())) {

					multiSetAttribute1.addReference(multiSetAttribute2);
					multiSetAttribute2.addReference(multiSetAttribute1);
				}

			}

		}

		for (int i = 0; i < multiSetNode1.children.size(); i++) {
			updateReferences(multiSetNode1.getChildren().get(i), multiSetNode2.getChildren().get(i));
		}

	}

	public MultiSetNode getParent() {
		return parent;
	}

	public List<MultiSetNode> getChildren() {
		return children;
	}

	private void addChild(Node node, List<MultiSetNode> exclude, Set<Node> affectedNodes,
			Set<MultiSetNode> createdNodes, MultiSetNode predecessor, Map<Node, Integer> affectedNodesWithPosition,
			Map<MultiSetNode, MultiSetNode> addedMultiSetNodes) {

		affectedNodes.add(this.node);

		MultiSetNode multiSetNode = new MultiSetNode(node);
		multiSetNode.parent = this;

		int position = this.children.size();
		if (predecessor == null) {
			children.add(0, multiSetNode);
		} else {
			position = 0;
			for (MultiSetNode predecessorReference : predecessor.references) {

				for (MultiSetNode child : children) {

					if (predecessorReference.equals(child)) {
						position = child.getPosition() + 1;
						break;
					}
				}

			}
			children.add(position, multiSetNode);

		}
		if (!this.node.getChildren().contains(node)) {
			this.node.addChild(node, position);
		}

		addChildRecursively(multiSetNode, node);
		affectedNodesWithPosition.put(this.node, position);
		createdNodes.add(multiSetNode);
		addedMultiSetNodes.put(this, multiSetNode);

		exclude.add(this);
		for (MultiSetNode reference : references) {
			if (!exclude.contains(reference)) {
				reference.addChild(node, exclude, affectedNodes, createdNodes, predecessor, affectedNodesWithPosition,
						addedMultiSetNodes);
			}

		}

	}

	public AttributeActionResult addAttribute(Attribute attribute, Set<MultiSetNode> selectedReferences) {

		Set<Node> affectedNodes = new HashSet<Node>();
		Set<MultiSetNode> affectedMultiSetNodes = new HashSet<MultiSetNode>();
		Map<MultiSetNode, MultiSetAttribute> affectedMultiSetAttributes = new HashMap<MultiSetNode, MultiSetAttribute>();
		List<MultiSetAttribute> createdAttributes = new ArrayList<MultiSetAttribute>();

		if (getByAttribute(attribute) == null) {
			MultiSetAttribute newAttribute = new MultiSetAttribute(attribute, this);
			attributes.add(newAttribute);
			createdAttributes.add(newAttribute);
			affectedMultiSetAttributes.put(this, newAttribute);

			if (!node.getAttributes().contains(attribute)) {
				node.getAttributes().add(attribute);
			}

		}

		List<MultiSetNode> exclude = new ArrayList<MultiSetNode>();
		exclude.add(this);

		Set<MultiSetNode> referenceList = selectedReferences == null ? references : selectedReferences;
		for (MultiSetNode reference : referenceList) {
			if (!exclude.contains(reference)) {
				reference.addAttribute(attribute, exclude, createdAttributes, affectedMultiSetNodes, affectedNodes,
						affectedMultiSetAttributes);
			}
		}

		for (MultiSetAttribute multiSetAttribute1 : createdAttributes) {
			for (MultiSetAttribute multiSetAttribute2 : createdAttributes) {
				if (!multiSetAttribute1.equals(multiSetAttribute2)) {
					multiSetAttribute1.addReference(multiSetAttribute2);
				}

			}
		}

		return new AttributeActionResult(affectedNodes, affectedMultiSetNodes, affectedMultiSetAttributes);

	}

	public List<MultiSetAttribute> getAttributes() {
		return attributes;
	}

	private void addAttribute(Attribute attribute, List<MultiSetNode> exclude,
			List<MultiSetAttribute> createdAttributes, Set<MultiSetNode> affectedMultiSetNodes, Set<Node> affectedNodes,
			Map<MultiSetNode, MultiSetAttribute> affectedMultiSetAttributes) {

		if (getByAttribute(attribute) == null) {
			MultiSetAttribute newAttribute = new MultiSetAttribute(attribute, this);
			attributes.add(newAttribute);
			createdAttributes.add(newAttribute);
			affectedMultiSetAttributes.put(this, newAttribute);
		}

		affectedMultiSetNodes.add(this);

		if (!node.getAttributes().contains(attribute)) {
			node.addAttribute(attribute);
		}

		exclude.add(this);
		for (MultiSetNode reference : references) {
			if (!exclude.contains(reference)) {
				reference.addAttribute(attribute, exclude, createdAttributes, affectedMultiSetNodes, affectedNodes,
						affectedMultiSetAttributes);
			}
		}

	}

	public AttributeActionResult removeAttribute(Attribute attribute, Set<MultiSetNode> selectedMultiSetNodes) {

		Set<Node> affectedNodes = new HashSet<Node>();
		Set<MultiSetNode> affectedMultiSetNodes = new HashSet<MultiSetNode>();
		Map<MultiSetNode, MultiSetAttribute> affectedMultiSetAttributes = new HashMap<MultiSetNode, MultiSetAttribute>();

		MultiSetAttribute target = getByAttribute(attribute);

		this.node.getAttributes().remove(target.getAttribute());
		attributes.remove(target);

		affectedMultiSetAttributes.put(this, target);
		Set<MultiSetAttribute> exclude = new HashSet<MultiSetAttribute>();
		exclude.add(target);

		Set<MultiSetAttribute> referenceAttributes = new HashSet<MultiSetAttribute>();
		if (selectedMultiSetNodes != null) {
			for (MultiSetAttribute referenceAttribute : target.getReferences()) {

				if (selectedMultiSetNodes.contains(referenceAttribute.getMultiSetNode())) {
					referenceAttributes.add(referenceAttribute);
				}
			}
		} else {
			referenceAttributes = target.getReferences();
		}

		for (MultiSetAttribute reference : referenceAttributes) {
			if (!exclude.contains(reference)) {
				reference.getMultiSetNode().removeAttribute(reference, exclude, affectedMultiSetAttributes,
						affectedMultiSetNodes);
			}
		}

		return new AttributeActionResult(affectedNodes, affectedMultiSetNodes, affectedMultiSetAttributes);

	}

	private void removeAttribute(MultiSetAttribute multiSetAttribute, Set<MultiSetAttribute> exclude,
			Map<MultiSetNode, MultiSetAttribute> affectedMultiSetAttributes, Set<MultiSetNode> affectedMultiSetNodes) {

		this.attributes.remove(multiSetAttribute);
		this.node.getAttributes().remove(multiSetAttribute.getAttribute());

		affectedMultiSetAttributes.put(this, multiSetAttribute);
		affectedMultiSetNodes.add(this);

		exclude.add(multiSetAttribute);
		for (MultiSetAttribute reference : multiSetAttribute.getReferences()) {
			if (!exclude.contains(reference)) {
				reference.getMultiSetNode().removeAttribute(reference, exclude, affectedMultiSetAttributes,
						affectedMultiSetNodes);
			}
		}
	}

	public Node restoreNode() {

		Node restoredNode = new NodeImpl(node.getNodeType());

		for (MultiSetAttribute multiSetAttribute : attributes) {
			restoredNode.addAttribute(multiSetAttribute.restore());
		}

		for (MultiSetNode multiSetNodeChild : children) {
			Node restoredChild = multiSetNodeChild.restoreNode();
			restoredChild.setParent(restoredNode);
			restoredNode.addChild(restoredChild);
		}
		return restoredNode;

	}

	public void separate() {
		separate(this);
	}

	private void separate(MultiSetNode multiSetNode) {
		for (MultiSetNode reference : multiSetNode.references) {
			reference.references.remove(multiSetNode);
		}

		multiSetNode.references.clear();

		for (MultiSetAttribute multiSetAttribute : attributes) {

			for (MultiSetAttribute reference : multiSetAttribute.getReferences()) {
				reference.getReferences().remove(multiSetAttribute);
			}

			multiSetAttribute.getReferences().clear();
		}

		for (MultiSetNode child : multiSetNode.children) {
			separate(child);
		}
	}

	public Node restoreNodeWithMapping(Map<Node, MultiSetNode> mapping) {

		Node restoredNode = new NodeImpl(node.getNodeType());

		for (MultiSetAttribute multiSetAttribute : attributes) {
			restoredNode.addAttribute(multiSetAttribute.restore());
		}

		mapping.put(restoredNode, this);

		for (MultiSetNode multiSetNodeChild : children) {
			restoredNode.addChild(multiSetNodeChild.restoreNodeWithMapping(mapping));
		}
		return restoredNode;

	}

	public Set<MultiSetAttribute> addAttributeValue(Attribute attribute, Value value) {

		MultiSetAttribute target = getByAttribute(attribute);

		return target.addValue(value);

	}

	public Set<MultiSetAttribute> editAttributeValue(Attribute attribute, Value value) {

		MultiSetAttribute target = getByAttribute(attribute);

		return target.editValue(value);

	}

	public Set<MultiSetAttribute> editAttributeKey(Attribute attribute, String key) {

		MultiSetAttribute target = getByAttribute(attribute);

		return target.editKey(key);

	}

}
