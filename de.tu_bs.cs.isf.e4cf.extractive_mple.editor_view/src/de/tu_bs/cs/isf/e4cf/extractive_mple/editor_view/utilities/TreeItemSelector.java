package de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

public class TreeItemSelector {
	private TreeTableView<Node> treeView;
	
	public TreeItemSelector(TreeTableView<Node> view) {
		this.treeView = view;
	}
	
	/**
	 * Finds and selects a node in the tree by its UUID
	 * 
	 * @param uuid The UUID of the desired node
	 */
	public void selectNode(UUID uuid) {
		if (uuid == null) {
			return;
		}

		List<TreeItem<Node>> itemPath = TreeViewUtilities.findFirstItemPath(treeView.getRoot(), uuid);
		selectTreeItem(itemPath);
	}

	/**
	 * Selects a clone node under a parent node in the tree view.
	 * 
	 * @param parentToChildUuid The parentUuid followed by the childUuid separated
	 *                          by a '#' e.g. 4564-4021#8932-4893
	 */
	public void selectCloneNodeByParent(String parentToChildUuid) {
		String[] splitUuid = parentToChildUuid.split("#");
		String parentUuid = splitUuid[0];
		String childUuid = splitUuid[1];

		List<TreeItem<Node>> parentPath = TreeViewUtilities.findFirstItemPath(treeView.getRoot(), UUID.fromString(parentUuid));
		if (parentPath.size() > 0) {
			TreeItem<Node> parent = parentPath.get(parentPath.size() - 1);
			List<TreeItem<Node>> parentToChildPath = TreeViewUtilities.findFirstItemPath(parent, UUID.fromString(childUuid));
			List<TreeItem<Node>> childPath = new LinkedList<>(parentPath);
			childPath.add(parentToChildPath.get(parentToChildPath.size() - 1));

			selectTreeItem(childPath);
		}
	}

	/**
	 * Selects a tree item at the end of a path of tree items in the tree view. The
	 * tree item path has to start at the root item.
	 * 
	 * @param itemPath Sequence of tree items from the root to the item to select
	 */
	public void selectTreeItem(List<TreeItem<Node>> itemPath) {
		if (itemPath.size() > 0) {
			treeView.getSelectionModel().clearSelection();
			TreeItem<Node> node = itemPath.get(itemPath.size() - 1);
			for (TreeItem<Node> item : itemPath) {
				item.setExpanded(true);
			}
			treeView.getSelectionModel().select(node);
			treeView.refresh();
			int nodeIndex = treeView.getRow(node);
			treeView.scrollTo(nodeIndex - 3);
		}
	}
	
	public void selectNextTreeItem(Collection<UUID> uuids) {
		List<TreeItem<Node>> tree = TreeViewUtilities.getSubTreeAsList(treeView.getRoot(), new ArrayList<>());
		TreeItem<Node> selectedItem = treeView.getSelectionModel().getSelectedItem();
		
		if (selectedItem == null) {
			selectedItem = treeView.getRoot();
		}
		
		int startIndex = tree.indexOf(selectedItem);
		int nextBlockStart = -1;
		if (uuids.contains(selectedItem.getValue().getUUID())) {
			for (int i = startIndex+1; i < tree.size(); i++) {
				TreeItem<Node> item = tree.get(i);
				if (!uuids.contains(item.getValue().getUUID())) {
					nextBlockStart = i;
					break;
				}
			}
		}
		nextBlockStart = Math.max(startIndex, nextBlockStart);
		if (nextBlockStart == startIndex && uuids.contains(tree.get(tree.size() - 1).getValue().getUUID())) {
			nextBlockStart = 0;
		}
		
		TreeItem<Node> nextItem = null;
		if (nextBlockStart != -1) {
			for (int i = nextBlockStart+1; i < tree.size(); i++) {
				TreeItem<Node> item = tree.get(i);
				if (uuids.contains(item.getValue().getUUID())) {
					nextItem = item;
					break;
				}
			}
		}		
		if (nextItem == null) {
			for (int i = 0; i < startIndex; i++) {
				TreeItem<Node> item = tree.get(i);
				if (uuids.contains(item.getValue().getUUID())) {
					nextItem = item;
					break;
				}
			}
		}
		
		if (nextItem != null) {
			selectNode(nextItem.getValue().getUUID());
		}
	}

}
