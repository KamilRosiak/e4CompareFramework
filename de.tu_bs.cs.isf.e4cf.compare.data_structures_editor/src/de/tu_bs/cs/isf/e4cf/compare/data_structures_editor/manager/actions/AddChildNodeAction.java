package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.interfaces.NodeDecorator;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.utilities.TreeViewUtilities;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * Implementation of UndoAction for AddChildNode
 * 
 * @author Team05
 *
 */

public class AddChildNodeAction extends AbstractTreeAction {
	NodeDecorator decorator;

	private int position = 0;

	public AddChildNodeAction(TreeView<Node> treeView, TreeItem<Node> parent, NodeDecorator decorator) {
		this.setParentNode(parent);
		this.decorator = decorator;
		this.position = getParentNode().getValue().getChildren().size();
	}

	public AddChildNodeAction(TreeView<Node> treeView, TreeItem<Node> parent, NodeDecorator decorator,
			Node addedChild) {
		this(treeView, parent, decorator);
		this.position = getParentNode().getValue().getChildren().size();
		this.addedChild = addedChild;
	}

	public AddChildNodeAction(TreeView<Node> treeView, TreeItem<Node> parent, NodeDecorator decorator, Node addedChild,
			int position) {
		this(treeView, parent, decorator);
		this.position = position;
		this.addedChild = addedChild;
	}

	@Override
	public void undo() {
		getParentNode().getChildren().remove(getChildNode());
	}

	@Override
	public void execute() {
		if (addedChild == null) {
			addedChild = new NodeImpl(RCPMessageProvider.inputDialog("Create New Child", "Node Type"));
		}
		if(position > getParentNode().getValue().getChildren().size()) {
			position = getParentNode().getValue().getChildren().size() - 1;
		}
		
		setChildNode(TreeViewUtilities.createTreeItem(addedChild, decorator));
		getParentNode().getChildren().add(position, getChildNode());
		
		if(!getParentNode().getValue().getChildren().contains(addedChild)) {
			getParentNode().getValue().getChildren().add(position, addedChild);
		}
		
		
	}

	private Node addedChild;

	public Node getAddedChild() {
		return addedChild;
	}
}
