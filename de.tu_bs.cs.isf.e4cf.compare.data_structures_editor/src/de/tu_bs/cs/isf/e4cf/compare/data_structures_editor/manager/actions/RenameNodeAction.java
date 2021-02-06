package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractNode;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.UndoAction;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * Implementation of UndoAction for RenameNode
 * 
 * @author Team05
 *
 */

public class RenameNodeAction implements UndoAction {

	private String name;
	private String nodeName;
	private TreeView<AbstractNode> treeView;
	private TreeItem<AbstractNode> treeItem;

	public RenameNodeAction(String name, String nodeName, TreeView<AbstractNode> treeView, AbstractNode treeItem) {
		this.name = name;
		this.nodeName = nodeName;
		this.treeView = treeView;
		this.treeItem = new TreeItem<AbstractNode>(treeItem);

	}

	@Override
	public void undo() {

		for (Attribute attribute : treeItem.getValue().getAttributes()) {
			if (attribute.getAttributeKey().toLowerCase().equals("name")) {
				attribute.getAttributeValues().clear();
				break;
			}
		}
		treeItem.getValue().addAttribute("name", nodeName);
		treeView.refresh();
	}
}
