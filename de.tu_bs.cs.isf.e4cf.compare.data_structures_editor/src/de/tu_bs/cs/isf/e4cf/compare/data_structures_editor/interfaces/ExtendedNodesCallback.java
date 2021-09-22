package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.interfaces;

import java.util.Map;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public interface ExtendedNodesCallback {
	public void handle(Map<Node, Integer> nodes);
}
