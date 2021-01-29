package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractNode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class AddChildNodeAction implements UndoAction {

	private String name;
	private TreeItem<AbstractNode> childNode;
	private TreeView<AbstractNode> treeView;

	public AddChildNodeAction(String name, TreeItem<AbstractNode> childNode, TreeView<AbstractNode> treeView) {
		this.name = name;
		this.childNode = childNode;
		this.treeView = treeView;
	}

	@Override
	public void execute() {
		System.out.println("Executing Action 1");
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
