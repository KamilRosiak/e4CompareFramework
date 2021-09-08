package de.tu_bs.cs.isf.e4cf.compare.data_structures.impl;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class ConfigurationImpl extends NodeImpl implements Configuration {

	public ConfigurationImpl() {
		super("CONFIGURATION");
	}

	@Override
	public Node getTarget() {
		return this.getChildren().get(0);
	}

	@Override
	public void setTarget(Node node) {
		this.getChildren().clear();
		this.getChildren().add(node);

	}

	@Override
	public Configuration cloneNode() {
		Configuration newNode = new ConfigurationImpl();
		newNode.setNodeType(getNodeType());
		newNode.setVariabilityClass(getVariabilityClass());

		newNode.setTarget(getTarget().cloneNode());

		return newNode;
	}

}
