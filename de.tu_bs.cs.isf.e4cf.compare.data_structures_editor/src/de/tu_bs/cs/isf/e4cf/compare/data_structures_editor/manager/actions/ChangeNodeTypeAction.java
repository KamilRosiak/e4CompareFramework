package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;

/**
 * Implementation of UndoAction for RenameNode
 * 
 * @author Team05
 *
 */
public class ChangeNodeTypeAction extends AbstractTreeAction {
    private String nodeName;

    public ChangeNodeTypeAction(String newName, Node node) {
	this.setName(newName);
	this.setNode(node);
    }

    @Override
    public void undo() {
	setNodeType();
    }

    public String getNodeName() {
	return nodeName;
    }

    public void setNodeName(String nodeName) {
	this.nodeName = nodeName;
    }

    @Override
    public void execute() {
	setNodeType();
    }

    public void setNodeType() {
	String oldType = getNode().getNodeType();
	getNode().setNodeType(getName());
	setName(oldType);
    }
}
