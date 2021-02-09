package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.UndoAction;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * Implementation of UndoAction for AddChildNode
 * 
 * @author Team05
 *
 */

public class AddChildNodeAction implements UndoAction {
	private String name;
	private TreeItem<Node> childNode;
	private TreeView<Node> treeView;

	public AddChildNodeAction(String name, TreeItem<Node> childNode, TreeView<Node> treeView) {
		this.name = name;
		this.childNode = childNode;
		this.treeView = treeView;
	}

	@Override
	public void undo() {
		treeView.getSelectionModel().clearSelection();
		treeView.getSelectionModel().select(childNode);
		treeView.getSelectionModel().getSelectedItem().getParent().getChildren()
				.remove(treeView.getSelectionModel().getSelectedItem());
		treeView.refresh();
	}
}
