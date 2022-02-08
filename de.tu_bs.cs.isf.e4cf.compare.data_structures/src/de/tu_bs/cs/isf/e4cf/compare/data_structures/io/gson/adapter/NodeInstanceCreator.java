package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.gson.adapter;

import java.lang.reflect.Type;

import com.google.gson.InstanceCreator;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractNode;

public class NodeInstanceCreator implements InstanceCreator<AbstractNode> {
	/**
	 * Creates a NodeImpl with an empty string.
	 *
	 * @param type Unused.
	 */
	@Override
	public AbstractNode createInstance(Type type) {
		return new NodeImpl();
	}
}
