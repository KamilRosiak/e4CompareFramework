package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * Implementation of UndoAction for AddChildNode
 * 
 * @author Team05
 *
 */

public class AddChildNodeAction extends AbstractAction {

	public AddChildNodeAction(String name, TreeItem<Node> childNode, TreeView<Node> treeView) {
		this.setName(name);
		this.setChildNode(childNode);
		this.setTree(treeView);
	}

	@Override
	public void undo() {
		getTree().getSelectionModel().clearSelection();
		getTree().getSelectionModel().select(getChildNode());
		getTree().getSelectionModel().getSelectedItem().getParent().getChildren()
				.remove(getTree().getSelectionModel().getSelectedItem());
		getTree().refresh();
	}
}
