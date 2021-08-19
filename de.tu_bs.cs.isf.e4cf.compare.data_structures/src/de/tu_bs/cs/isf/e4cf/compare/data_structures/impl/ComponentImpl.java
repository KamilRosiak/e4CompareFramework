package de.tu_bs.cs.isf.e4cf.compare.data_structures.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class ComponentImpl extends NodeImpl implements Component {

	private Map<Node, Set<Node>> synchronizationUnit;

	private Map<Node, Map<Integer, Configuration>> nodeComponentRelation;

	public Map<Node, Map<Integer, Configuration>> getNodeComponentRelation() {
		return nodeComponentRelation;
	}

	public void setNodeComponentRelation(Map<Node, Map<Integer, Configuration>> nodeComponentRelation) {
		this.nodeComponentRelation = nodeComponentRelation;
	}

	public ComponentImpl(Map<Node, Set<Node>> synchronizationUnit) {
		super("Component");
		this.synchronizationUnit = synchronizationUnit;

	}

	private ComponentImpl() {

	}

	public Map<Node, Set<Node>> getSynchronizationUnit() {
		return synchronizationUnit;
	}

	public void setSynchronizationUnit(Map<Node, Set<Node>> synchronizationUnit) {
		this.synchronizationUnit = synchronizationUnit;
	}

	public List<Configuration> getConfigurations() {
		return (List<Configuration>) (Object) this.getChildren();
	}

	public void setConfigurations(List<Configuration> configurations) {
		this.getChildren().clear();
		for (Configuration configuration : configurations) {
			this.getChildren().add(configuration);
		}
	}

	@Override
	public Node cloneNode() {

		ComponentImpl newNode = new ComponentImpl();
		newNode.setNodeComponentRelation(nodeComponentRelation);
		newNode.setNodeType(getNodeType());
		newNode.setVariabilityClass(getVariabilityClass());

		for (Attribute attribute : getAttributes()) {
			Attribute newAttribute = new AttributeImpl(attribute.getAttributeKey(), attribute.getAttributeValues());
			newNode.addAttribute(newAttribute);
		}

		for (Node child : getChildren()) {

			newNode.addChildWithParent(child.cloneNode());
		}

		return newNode;
	}

}
