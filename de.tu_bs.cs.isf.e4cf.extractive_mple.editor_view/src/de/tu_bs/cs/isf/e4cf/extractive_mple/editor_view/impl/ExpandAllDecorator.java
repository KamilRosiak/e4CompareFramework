package de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.impl;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.interfaces.NodeDecorator;
import javafx.scene.control.TreeItem;

public class ExpandAllDecorator implements NodeDecorator {
	
	public ExpandAllDecorator() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isSupportedTree(Tree tree) {
		return true;
	}

	@Override
	public TreeItem<Node> decorateNode(TreeItem<Node> node) {
		node.setExpanded(true);
		return node;
	}
}
