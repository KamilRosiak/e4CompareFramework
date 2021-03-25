package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.interfaces.NodeDecorator;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.utilities.TreeViewUtilities;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * Implementation of UndoAction for AddChildNode
 * 
 * @author Team05
 *
 */

public class AddChildNodeAction extends AbstractTreeAction {
	NodeDecorator decorator;
	public AddChildNodeAction(TreeView<Node> treeView, TreeItem<Node> parent, NodeDecorator decorator) {
		this.setParentNode(parent);
	}

	@Override
	public void undo() {
		getParentNode().getChildren().remove(getChildNode());
	}

	@Override
	public void execute() {
		setChildNode(TreeViewUtilities
				.createTreeItem(new NodeImpl(RCPMessageProvider.inputDialog("Create New Child", "Node Type")),decorator));
		getParentNode().getChildren().add(getChildNode());
	}
}
