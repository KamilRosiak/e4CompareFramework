package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.interfaces;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import javafx.scene.control.TreeItem;

public interface NodeDecorator {
	
	public boolean isSupportedTree(Tree tree);
	public TreeItem<Node> decorateNode(TreeItem<Node> node);
	

}
