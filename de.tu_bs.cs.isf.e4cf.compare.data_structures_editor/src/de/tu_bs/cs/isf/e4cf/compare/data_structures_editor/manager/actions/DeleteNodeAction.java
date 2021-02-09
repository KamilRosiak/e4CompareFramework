package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.UndoAction;
import javafx.scene.control.TreeItem;

/**
 * Implementation of UndoAction for DeleteNode
 * 
 * @author Team05
 *
 */

public class DeleteNodeAction implements UndoAction {
    private String name;
    private TreeItem<Node> treeItem;
    private TreeItem<Node> parent;

    public DeleteNodeAction(String name, TreeItem<Node> treeItem, TreeItem<Node> parent) {
	this.setName(name);
	this.treeItem = treeItem;
	this.parent = parent;
    }

    @Override
    public void undo() {
	parent.getChildren().add(treeItem);
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }
}
