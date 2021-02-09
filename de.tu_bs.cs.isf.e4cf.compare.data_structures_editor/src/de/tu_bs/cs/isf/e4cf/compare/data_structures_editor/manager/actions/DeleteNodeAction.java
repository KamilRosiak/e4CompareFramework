package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import javafx.scene.control.TreeItem;

/**
 * Implementation of UndoAction for DeleteNode
 * 
 * @author Team05
 *
 */

public class DeleteNodeAction extends AbstractAction {
    
    public DeleteNodeAction(String name, TreeItem<Node> treeItem, TreeItem<Node> parent) {
	this.setName(name);
	this.setChildNode(treeItem);
	this.setParentNode(parent);
    }

    @Override
    public void undo() {
	getParentNode().getChildren().add(getChildNode());
    }
}
