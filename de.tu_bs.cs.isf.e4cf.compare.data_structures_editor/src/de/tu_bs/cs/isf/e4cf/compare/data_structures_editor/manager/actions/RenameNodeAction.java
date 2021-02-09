package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * Implementation of UndoAction for RenameNode
 * 
 * @author Team05
 *
 */
public class RenameNodeAction extends AbstractAction {
    private String nodeName;

    public RenameNodeAction(String name, String nodeName, TreeView<Node> treeView, Node treeItem) {
	this.setName(name);
	this.nodeName = nodeName;
	this.setTree(treeView);
	this.setNode(treeItem);
	this.setChildNode(new TreeItem<Node>(treeItem));
    }

    @Override
    public void undo() {
	for (Attribute attribute : getNode().getAttributes()) {
	    if (attribute.getAttributeKey().toLowerCase().equals("name")) {
		attribute.getAttributeValues().clear();
		break;
	    }
	}
	getNode().addAttribute("name", nodeName);
	getTree().refresh();
    }

    public String getNodeName() {
	return nodeName;
    }

    public void setNodeName(String nodeName) {
	this.nodeName = nodeName;
    }
}
