package de.tu_bs.cs.isf.e4cf.compare.data_structures.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Component;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class ComponentImpl extends NodeImpl implements Component {

	public ComponentImpl() {
		super("Component");

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
		newNode.setNodeType(getNodeType());
		newNode.setVariabilityClass(getVariabilityClass());

		for (Attribute attribute : getAttributes()) {
			Attribute newAttribute = new AttributeImpl(attribute.getAttributeKey(), attribute.getAttributeValues());
			newNode.addAttribute(newAttribute);
		}

		for (Node child : getChildren()) {
			if (child == selectedConfiguration) {
				Configuration configuration = (Configuration) child;
				Configuration clonedChild = (Configuration) configuration.cloneNode();
				newNode.setSelectedConfiguration(clonedChild);
				newNode.addChildWithParent(clonedChild);
			} else {
				newNode.addChildWithParent(child.cloneNode());
			}

		}

		return newNode;
	}

	private String layer;

	@Override
	public String getLayer() {
		return layer;
	}

	@Override
	public void setLayer(String layer) {
		this.layer = layer;

	}

	private Configuration selectedConfiguration;

	@Override
	public Configuration getSelectedConfiguration() {

		return selectedConfiguration;
	}

	@Override
	public void setSelectedConfiguration(Configuration configuration) {
		this.selectedConfiguration = configuration;

	}

	@Override
	public List<Node> getAllTargets() {
		List<Node> targets = new ArrayList<Node>();
		for (Configuration configuration : getConfigurations()) {
			targets.add(configuration.getTarget());
		}

		return targets;
	}

	@Override
	public Map<Node, Configuration> getConfigurationByTarget() {

		Map<Node, Configuration> map = new HashMap<Node, Configuration>();
		for (Configuration configuration : getConfigurations()) {
			map.put(configuration.getTarget(), configuration);
		}
		return map;

	}

}
