package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractNode;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

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
				System.out.println(nodeName);
				break;
			}
		}
		treeItem.getValue().addAttribute("name", nodeName);
		treeView.refresh();
	}
}
