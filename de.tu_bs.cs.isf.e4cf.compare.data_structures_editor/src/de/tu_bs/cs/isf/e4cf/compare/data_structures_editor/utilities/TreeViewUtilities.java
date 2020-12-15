package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.utilities;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.NodeUsage;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DataStructuresEditorST;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * 
 * @author Team05
 *
 */
public final class TreeViewUtilities {

	public static void switchToPart(String path, ServiceContainer services) {
		services.partService.showPart(path);
	}

	/**
	 * 
	 * @param tr
	 * @param treeView
	 * @return
	 */
	public static TreeView<NodeUsage> getTreeViewFromTree(Tree tr, TreeView<NodeUsage> treeView) {
		TreeItem<NodeUsage> rootItem = new TreeItem<NodeUsage>(new NodeUsage(tr.getRoot()));
		rootItem.setExpanded(true);

		for (Node node : tr.getLeaves()) {
			TreeItem<NodeUsage> item = new TreeItem<NodeUsage>(new NodeUsage(node));
			rootItem.getChildren().add(item);
		}

		treeView.setRoot(rootItem);
		treeView.setShowRoot(true);

		return treeView;
	}

	/**
	 * 
	 * @param treeView
	 * @param services
	 * @return
	 */
	public static TreeView<NodeUsage> addListener(TreeView<NodeUsage> treeView, ServiceContainer services) {
		treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (treeView.getSelectionModel().getSelectedIndices().size() == 1) {
				switchToPart(DataStructuresEditorST.PROPERTIES_VIEW_ID, services);
				services.eventBroker.send("nodePropertiesEvent",
						treeView.getSelectionModel().getSelectedItem().getValue());
			}
		});

		return treeView;
	}

	/**
	 * 
	 * @param item
	 * @param name
	 * @return
	 */
	public static TreeItem<NodeUsage> searchTreeItem(TreeItem<NodeUsage> item, String name) {
		if (item.getValue().toString().equals(name)) {
			return item;
		}
		TreeItem<NodeUsage> result = null;
		for (TreeItem<NodeUsage> child : item.getChildren()) {
			result = searchTreeItem(child, name);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

}
