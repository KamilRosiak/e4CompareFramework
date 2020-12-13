package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DataStructuresEditorST;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public final class TreeViewUtilities {

	public static void switchToPart(String path, ServiceContainer services) {
		services.partService.showPart(path);
	}

	/**
	 * initializes Tree with Data from the .txt file
	 * 
	 * @param tr data structure of type tree
	 */
	public static TreeView<NodeUsage> initTree(Tree tr, TreeView<NodeUsage> hirarchy, TreeItem<NodeUsage> rootItem,
			ServiceContainer services) {
		NodeUsage rootNodeUsage = new NodeUsage(tr.getRoot());
		rootItem = new TreeItem<NodeUsage>(rootNodeUsage);
		rootItem.setExpanded(true);
		for (Node node : tr.getLeaves()) {
			NodeUsage nodeTest = new NodeUsage(node);
			TreeItem<NodeUsage> item = new TreeItem<NodeUsage>(nodeTest);
			rootItem.getChildren().add(item);
		}
		hirarchy.setRoot(rootItem);
		hirarchy.setShowRoot(true);

		hirarchy.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			switchToPart(DataStructuresEditorST.PROPERTIES_VIEW_ID, services);
			services.eventBroker.send("nodePropertiesEvent", hirarchy.getSelectionModel().getSelectedItem().getValue());
		});
		return hirarchy;
	}


}
