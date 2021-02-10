package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.Action;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public abstract class AbstractAction implements Action {
	private String name;
	private Node node;
	private TreeItem<Node> childNode;
	private TreeItem<Node> parentNode;
	private TreeView<Node> tree;
	
	public String getName() {
	    return name;
	}
	public void setName(String name) {
	    this.name = name;
	}
	public Node getNode() {
	    return node;
	}
	public void setNode(Node node) {
	    this.node = node;
	}
	public TreeItem<Node> getChildNode() {
	    return childNode;
	}
	public void setChildNode(TreeItem<Node> child) {
	    this.childNode = child;
	}
	public TreeView<Node> getTree() {
	    return tree;
	}
	public void setTree(TreeView<Node> parent) {
	    this.tree = parent;
	}
	public TreeItem<Node> getParentNode() {
	    return parentNode;
	}
	public void setParentNode(TreeItem<Node> parentNode) {
	    this.parentNode = parentNode;
	}
}
