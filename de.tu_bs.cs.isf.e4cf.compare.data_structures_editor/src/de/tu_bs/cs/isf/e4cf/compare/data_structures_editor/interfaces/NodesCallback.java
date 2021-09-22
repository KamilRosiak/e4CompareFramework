package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.interfaces;

import java.util.Collection;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

public interface NodesCallback {
	
	public void handle(Collection<Node> nodes);
}
