package de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.impl;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableRow;

public abstract class StylableTreeItem extends TreeItem<Node> {
	
	public StylableTreeItem(Node node) {
		super(node);
	}
	
	public abstract void style(TreeTableRow<Node> row);

}
