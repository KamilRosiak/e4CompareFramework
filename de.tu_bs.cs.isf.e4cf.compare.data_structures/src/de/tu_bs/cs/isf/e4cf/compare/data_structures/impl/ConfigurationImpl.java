package de.tu_bs.cs.isf.e4cf.compare.data_structures.impl;

import java.util.List;
import java.util.Map;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public class ConfigurationImpl extends NodeImpl implements Configuration {

	public ConfigurationImpl() {
		super("Configuration");
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

}
