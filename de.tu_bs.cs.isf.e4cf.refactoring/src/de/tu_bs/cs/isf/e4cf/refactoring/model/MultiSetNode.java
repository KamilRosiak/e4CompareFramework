package de.tu_bs.cs.isf.e4cf.refactoring.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import de.tu_bs.cs.isf.e4cf.refactoring.model.result.AddAttributeResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.result.AddAttributeValueResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.result.AddChildResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.result.DeleteAttributeResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.result.DeleteResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.result.EditAttributeKeyResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.result.EditAttributeValueResult;
import de.tu_bs.cs.isf.e4cf.refactoring.model.result.MoveResult;
import de.tu_bs.cs.isf.e4cf.refactoring.util.SynchronizationUtil;

public class MultiSetNode {

	public Set<MultiSetNode> getReferences() {
		return references;
	}

	private Set<MultiSetNode> references;

	private MultiSetNode parent;

	private List<MultiSetNode> children;

	private Node node;

	private List<MultiSetAttribute> attributes;

	private boolean isRoot;

	public void setRoot(boolean root) {
		isRoot = root;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void addReference(MultiSetNode multiSetNode) {
		this.references.add(multiSetNode);
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public List<MultiSetNode> getChildren() {
		return children;
	}

	public List<MultiSetAttribute> getAttributes() {
		return attributes;
	}

	public Node getNode() {
		return node;
	}

	public MultiSetNode getParent() {
		return parent;
	}

	public MultiSetNode(Node node) {

		this.references = new HashSet<MultiSetNode>();
		this.children = new ArrayList<MultiSetNode>();
		this.node = node;
		this.attributes = new ArrayList<MultiSetAttribute>();

		for (Attribute attribute : node.getAttributes()) {
			attributes.add(new MultiSetAttribute(attribute, this));
		}

	}

	public List<DeleteResult> delete(Set<Node> deletedNodes) {
		return delete(deletedNodes, null);
	}

	public List<DeleteResult> delete(Set<Node> deletedNodes, Set<MultiSetNode> selectedReferences) {

		List<DeleteResult> deleteResults = new ArrayList<DeleteResult>();

		deletedNodes.add(node);
		if (!isRoot) {
			parent.children.remove(this);
		}
		deleteResults.add(new DeleteResult(node));

		Set<MultiSetNode> referenceList = selectedReferences == null ? references : selectedReferences;

		for (MultiSetNode reference : referenceList) {

			deletedNodes.add(reference.node);
			if (!reference.isRoot) {
				reference.parent.children.remove(reference);
			}
			deleteResults.add(new DeleteResult(reference.node));

		}
		return deleteResults;
	}

	public List<AddAttributeResult> addAttribute(Attribute attribute, Map<Node, Attribute> nodeToAttribute) {
		return addAttribute(attribute, nodeToAttribute, null);
	}

	public List<AddAttributeResult> addAttribute(Attribute attribute, Map<Node, Attribute> nodeToAttribute,
			Set<MultiSetNode> selectedReferences) {

		List<AddAttributeResult> addAttributeResults = new ArrayList<AddAttributeResult>();

		Attribute clonedAttribute = null;
		if (!nodeToAttribute.containsKey(node)) {
			clonedAttribute = attribute.cloneAttribute();
			node.addAttribute(clonedAttribute);
			nodeToAttribute.put(node, clonedAttribute);
		} else {
			clonedAttribute = nodeToAttribute.get(node);
		}

		addAttributeResults.add(new AddAttributeResult(node, clonedAttribute));

		MultiSetAttribute newMultiSetAttribute = new MultiSetAttribute(clonedAttribute, this);
		this.attributes.add(newMultiSetAttribute);

		List<MultiSetAttribute> newAttributes = new ArrayList<MultiSetAttribute>();
		newAttributes.add(newMultiSetAttribute);

		Set<MultiSetNode> referenceList = selectedReferences == null ? references : selectedReferences;

		for (MultiSetNode reference : referenceList) {

			if (!nodeToAttribute.containsKey(reference.node)) {
				clonedAttribute = attribute.cloneAttribute();
				reference.node.addAttribute(clonedAttribute);
				nodeToAttribute.put(reference.node, clonedAttribute);
			} else {
				clonedAttribute = nodeToAttribute.get(reference.node);
			}

			MultiSetAttribute newMultiSetReferenceAttribute = new MultiSetAttribute(clonedAttribute, reference);
			newAttributes.add(newMultiSetReferenceAttribute);
			reference.attributes.add(newMultiSetReferenceAttribute);

			addAttributeResults.add(new AddAttributeResult(reference.node, clonedAttribute));

		}

		for (MultiSetAttribute multiSetAttribute1 : newAttributes) {
			for (MultiSetAttribute multiSetAttribute2 : newAttributes) {

				if (multiSetAttribute1 != multiSetAttribute2) {
					multiSetAttribute1.addReference(multiSetAttribute2);
					multiSetAttribute2.addReference(multiSetAttribute1);
				}
			}
		}

		return addAttributeResults;

	}

	public List<DeleteAttributeResult> deleteAttribute(Attribute attribute, Set<Attribute> deletedAttributes) {
		return deleteAttribute(attribute, deletedAttributes, null);
	}

	public List<DeleteAttributeResult> deleteAttribute(Attribute attribute, Set<Attribute> deletedAttributes,
			Set<MultiSetAttribute> selectedReferences) {

		List<DeleteAttributeResult> deleteAttributeResults = new ArrayList<DeleteAttributeResult>();

		MultiSetAttribute multiSetAttribute = getAttribute(attribute);

		multiSetAttribute.getMultiSetNode().attributes.remove(multiSetAttribute);
		deletedAttributes.add(attribute);

		deleteAttributeResults.add(new DeleteAttributeResult(this.node, attribute));

		Set<MultiSetAttribute> referenceList = selectedReferences == null ? multiSetAttribute.getReferences()
				: selectedReferences;

		for (MultiSetAttribute reference : referenceList) {

			reference.getMultiSetNode().attributes.remove(reference);
			deletedAttributes.add(reference.getAttribute());

			deleteAttributeResults
					.add(new DeleteAttributeResult(reference.getMultiSetNode().node, reference.getAttribute()));
		}

		return deleteAttributeResults;

	}

	public List<MoveResult> move(int position) {
		return move(position, null);
	}

	public List<MoveResult> move(int position, Set<MultiSetNode> selectedReferences) {

		List<MoveResult> results = new ArrayList<MoveResult>();

		int currentPosition = this.getPosition();

		Collections.swap(parent.children, currentPosition, position);

		results.add(new MoveResult(node, position));

		Set<MultiSetNode> referenceList = selectedReferences == null ? references : selectedReferences;

		for (MultiSetNode reference : referenceList) {
			List<MultiSetNode> subsequence = SynchronizationUtil.findLongestCommonSubsequence(reference.parent.children,
					this.parent.children);

			List<MultiSetNode> childList = new ArrayList<MultiSetNode>(reference.parent.children);

			for (MultiSetNode child : childList) {

				if (!subsequence.contains(child)) {

					int referencePosition = -1;
					for (MultiSetNode childReference : child.references) {

						if (parent.children.contains(childReference)) {
							referencePosition = childReference.getPosition();
							break;
						}
					}

					if (referencePosition != -1) {
						subsequence.add(child);
						int currentReferencePosition = child.getPosition();

						Collections.swap(reference.parent.children, currentReferencePosition, referencePosition);

						results.add(new MoveResult(reference.node, referencePosition));

					}

				}

			}

		}

		return results;

	}

	public List<AddAttributeValueResult> addAttributeValue(Attribute attribute, Value value,
			Map<Attribute, Value> attributeToValue) {
		return addAttributeValue(attribute, value, attributeToValue, null);
	}

	public List<AddAttributeValueResult> addAttributeValue(Attribute attribute, Value value,
			Map<Attribute, Value> attributeToValue, Set<MultiSetAttribute> selectedReferences) {
		List<AddAttributeValueResult> addAttributeValueResults = new ArrayList<AddAttributeValueResult>();

		MultiSetAttribute multiSetAttribute = getAttribute(attribute);

		Value clonedValue = null;
		if (!attributeToValue.containsKey(attribute)) {
			clonedValue = new StringValueImpl((String) value.getValue());
			attributeToValue.put(attribute, clonedValue);
		} else {
			clonedValue = attributeToValue.get(attribute);
		}

		multiSetAttribute.addValue(clonedValue);

		addAttributeValueResults.add(new AddAttributeValueResult(attribute, clonedValue, node));

		Set<MultiSetAttribute> referenceList = selectedReferences == null ? multiSetAttribute.getReferences()
				: selectedReferences;

		for (MultiSetAttribute reference : referenceList) {
			if (!attributeToValue.containsKey(reference.getAttribute())) {
				clonedValue = new StringValueImpl((String) value.getValue());
				attributeToValue.put(reference.getAttribute(), clonedValue);
			} else {
				clonedValue = attributeToValue.get(reference.getAttribute());
			}

			reference.addValue(clonedValue);
			addAttributeValueResults.add(new AddAttributeValueResult(reference.getAttribute(), clonedValue,
					reference.getMultiSetNode().getNode()));
		}

		return addAttributeValueResults;
	}

	public List<EditAttributeValueResult> editAttributeValue(Attribute attribute, Value value,
			Map<Attribute, Value> attributeToValue) {
		return editAttributeValue(attribute, value, attributeToValue, null);
	}

	public List<EditAttributeValueResult> editAttributeValue(Attribute attribute, Value value,
			Map<Attribute, Value> attributeToValue, Set<MultiSetAttribute> selectedReferences) {
		List<EditAttributeValueResult> editAttributeValueResults = new ArrayList<EditAttributeValueResult>();
		MultiSetAttribute multiSetAttribute = getAttribute(attribute);

		Value clonedValue = null;
		if (!attributeToValue.containsKey(attribute)) {
			clonedValue = new StringValueImpl((String) value.getValue());
			attributeToValue.put(attribute, clonedValue);
		} else {
			clonedValue = attributeToValue.get(attribute);
		}

		multiSetAttribute.editValue(clonedValue);
		editAttributeValueResults.add(new EditAttributeValueResult(node, attribute, clonedValue));
		Set<MultiSetAttribute> referenceList = selectedReferences == null ? multiSetAttribute.getReferences()
				: selectedReferences;

		for (MultiSetAttribute reference : referenceList) {
			clonedValue = null;
			if (!attributeToValue.containsKey(attribute)) {
				clonedValue = new StringValueImpl((String) value.getValue());
				attributeToValue.put(attribute, clonedValue);
			} else {
				clonedValue = attributeToValue.get(attribute);
			}

			reference.editValue(clonedValue);
			editAttributeValueResults.add(new EditAttributeValueResult(reference.getMultiSetNode().getNode(),
					reference.getAttribute(), clonedValue));
		}

		return editAttributeValueResults;
	}

	public List<EditAttributeKeyResult> editAttributeKey(Attribute attribute, String key,
			Set<Attribute> editedAttributes) {
		return editAttributeKey(attribute, key, editedAttributes, null);
	}

	public List<EditAttributeKeyResult> editAttributeKey(Attribute attribute, String key,
			Set<Attribute> editedAttributes, Set<MultiSetAttribute> selectedReferences) {

		List<EditAttributeKeyResult> editAttributeKeyResults = new ArrayList<EditAttributeKeyResult>();

		MultiSetAttribute multiSetAttribute = getAttribute(attribute);
		multiSetAttribute.setKey(key);

		editAttributeKeyResults.add(new EditAttributeKeyResult(node, attribute, key));
		editedAttributes.add(attribute);

		Set<MultiSetAttribute> referenceList = selectedReferences == null ? multiSetAttribute.getReferences()
				: selectedReferences;

		for (MultiSetAttribute reference : referenceList) {
			reference.setKey(key);
			editedAttributes.add(reference.getAttribute());
			editAttributeKeyResults.add(
					new EditAttributeKeyResult(reference.getMultiSetNode().getNode(), reference.getAttribute(), key));
		}

		return editAttributeKeyResults;

	}

	public List<AddChildResult> addChild(Node child, int position, Map<Node, Node> parentToChild) {
		return addChild(child, position, parentToChild, null);
	}

	public List<AddChildResult> addChild(Node child, int position, Map<Node, Node> parentToChild,
			Set<MultiSetNode> selectedReferences) {

		List<AddChildResult> addChildResults = new ArrayList<AddChildResult>();

		MultiSetNode multiSetNodeChild = null;
		if (!parentToChild.containsKey(node)) {
			Node childClone = child.cloneNode();
			node.getChildren().add(position, childClone);
			childClone.setParent(node);

			multiSetNodeChild = MultiSetNode.create(childClone);
			position = position > children.size() ? children.size() : position;
			children.add(position, multiSetNodeChild);
			parentToChild.put(node, childClone);
			addChildResults.add(new AddChildResult(node, position, childClone));

		} else if (parentToChild.containsKey(node)) {
			multiSetNodeChild = MultiSetNode.create(parentToChild.get(node));
			position = position > children.size() ? children.size() : position;
			children.add(position, multiSetNodeChild);
			addChildResults.add(new AddChildResult(node, position, parentToChild.get(node)));
		}

		multiSetNodeChild.parent = this;

		List<MultiSetNode> newChildReferences = new ArrayList<MultiSetNode>();
		newChildReferences.add(multiSetNodeChild);

		Set<MultiSetNode> referenceList = selectedReferences == null ? references : selectedReferences;

		for (MultiSetNode reference : referenceList) {
			int referencePosition = getPositionOfCommonPredecessor(multiSetNodeChild, reference);

			MultiSetNode multiSetNodeReferenceChild = null;

			if (!reference.node.getChildren().contains(child) && !parentToChild.containsKey(reference.node)) {
				Node childClone = child.cloneNode();
				reference.node.getChildren().add(referencePosition, childClone);
				childClone.setParent(reference.node);
				multiSetNodeReferenceChild = MultiSetNode.create(childClone);
				reference.children.add(referencePosition, multiSetNodeReferenceChild);
				parentToChild.put(reference.node, childClone);

				addChildResults.add(new AddChildResult(reference.node, referencePosition, childClone));

			} else if (parentToChild.containsKey(reference.node)) {
				multiSetNodeReferenceChild = MultiSetNode.create(parentToChild.get(reference.node));
				reference.children.add(referencePosition, multiSetNodeReferenceChild);

				addChildResults.add(new AddChildResult(reference.node, referencePosition, parentToChild.get(node)));
			}

			multiSetNodeReferenceChild.parent = reference;

			newChildReferences.add(multiSetNodeReferenceChild);

		}

		addReferences(newChildReferences);

		return addChildResults;

	}

	private void addReferences(List<MultiSetNode> multiSetNodes) {

		for (MultiSetNode multiSetNode1 : multiSetNodes) {

			for (MultiSetNode multiSetNode2 : multiSetNodes) {
				if (multiSetNode1 != multiSetNode2) {
					multiSetNode1.references.add(multiSetNode2);
					multiSetNode2.references.add(multiSetNode1);

					for (MultiSetAttribute multiSetAttribute1 : multiSetNode1.attributes) {
						for (MultiSetAttribute multiSetAttribute2 : multiSetNode2.attributes) {
							if (multiSetAttribute1.getKey().equals(multiSetAttribute2.getKey())) {
								multiSetAttribute1.addReference(multiSetAttribute2);
								multiSetAttribute2.addReference(multiSetAttribute1);
							}

						}
					}

					addReferencesRecursively(multiSetNode1, multiSetNode2);
				}
			}
		}

	}

	private void addReferencesRecursively(MultiSetNode multiSetNode1, MultiSetNode multiSetNode2) {
		for (int i = 0; i < multiSetNode1.children.size(); i++) {
			multiSetNode1.children.get(i).references.add(multiSetNode2.children.get(i));
			multiSetNode2.children.get(i).references.add(multiSetNode1.children.get(i));

			for (MultiSetAttribute multiSetAttribute1 : multiSetNode1.children.get(i).attributes) {
				for (MultiSetAttribute multiSetAttribute2 : multiSetNode2.children.get(i).attributes) {
					if (multiSetAttribute1.getKey().equals(multiSetAttribute2.getKey())) {
						multiSetAttribute1.addReference(multiSetAttribute2);
						multiSetAttribute2.addReference(multiSetAttribute1);
					}

				}
			}
			addReferencesRecursively(multiSetNode1.children.get(i), multiSetNode2.children.get(i));

		}
	}

	private int getPosition() {
		return parent.children.indexOf(this);
	}

	private MultiSetNode getPredecessor() {

		int position = getPosition();
		if (position == 0) {
			return null;
		}

		return parent.children.get(position - 1);

	}

	private int getPositionOfCommonPredecessor(MultiSetNode original, MultiSetNode target) {

		MultiSetNode predecessor = original.getPredecessor();

		while (predecessor != null) {
			for (MultiSetNode reference : predecessor.references) {
				if (target.children.contains(reference)) {
					return reference.getPosition() + 1;
				}
			}
			predecessor = predecessor.getPredecessor();
		}
		return 0;

	}

	public static MultiSetNode create(Node node) {
		MultiSetNode multiSetNode = new MultiSetNode(node);
		for (Node child : node.getChildren()) {
			MultiSetNode childMultiSetNode = create(child);
			childMultiSetNode.parent = multiSetNode;
			multiSetNode.children.add(childMultiSetNode);
		}
		return multiSetNode;
	}

	public MultiSetNode getByNode(Node node) {
		if (this.node.equals(node)) {
			return this;
		}
		for (MultiSetNode multiSetNodeChild : children) {
			MultiSetNode multiSetNode = multiSetNodeChild.getByNode(node);
			if (multiSetNode != null) {
				return multiSetNode;
			}
		}
		return null;
	}

	public boolean containsNode(Node node) {
		return getByNode(node) != null;
	}

	public MultiSetAttribute getAttribute(Attribute attribute) {
		for (MultiSetAttribute multiSetAttribute : attributes) {
			if (multiSetAttribute.getAttribute().equals(attribute)) {
				return multiSetAttribute;
			}
		}
		return null;
	}

	public MultiSetAttribute getAttribute(String attributeKey) {
		for (MultiSetAttribute multiSetAttribute : attributes) {
			if (multiSetAttribute.getKey().equals(attributeKey)) {
				return multiSetAttribute;
			}
		}
		return null;
	}

	public List<MultiSetNode> getByGranularity(String granularity) {
		List<MultiSetNode> multiSetNodes = new ArrayList<MultiSetNode>();
		if (this.node.getNodeType().equals(granularity)) {
			multiSetNodes.add(this);
		}
		for (MultiSetNode multiSetNodeChild : children) {
			MultiSetNode multiSetNode = multiSetNodeChild.getByNode(node);

			if (multiSetNode != null) {
				multiSetNodes.add(multiSetNode);
			}
		}

		return multiSetNodes;

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

}
