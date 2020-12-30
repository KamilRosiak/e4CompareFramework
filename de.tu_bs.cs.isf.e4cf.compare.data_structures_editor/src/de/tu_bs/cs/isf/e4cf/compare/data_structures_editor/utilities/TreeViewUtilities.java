package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.NodeUsage;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DataStructuresEditorST;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * 
 * @author Team05
 *
 */
public final class TreeViewUtilities {

	static List<TreeItem<NodeUsage>> searchList = new ArrayList<TreeItem<NodeUsage>>();

	public static String treeName = "";

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
	public static List<TreeItem<NodeUsage>> searchTreeItem(TreeItem<NodeUsage> item, String name) {
		if (item.getValue().toString().contains(name)) {
			searchList.add(item);
		}
		List<TreeItem<NodeUsage>> result = new ArrayList<TreeItem<NodeUsage>>();
		for (TreeItem<NodeUsage> child : item.getChildren()) {
			result.addAll(searchTreeItem(child, name));
			if (result.size() < 1) {
				searchList.addAll(result);
			}
		}
		return searchList;
	}

	public static List<TreeItem<NodeUsage>> getSearchList() {
		return searchList;
	}

	public static void clearSearchList() {
		searchList.clear();
	}

	public static void serializesTree(TreeView trview) {
		File file = new File(RCPContentProvider.getCurrentWorkspacePath() + "/" + "neuneu.txt");
		System.out.println(file.getAbsolutePath());

		// int choice = 0;
		// int inputChoice = 1;

		// if(file.exists()) {
		// choice = RCPMessageProvider.optionMessage(FILE_EXISTS,
		// METRIC_WITH_NAME_EXISTS, E4CStringTable.DIALOG_OK,
		// E4CStringTable.DIALOG_RENAME, E4CStringTable.DIALOG_CANCEL);
		// }
		/*
		 * if(choice == 1) { InputDialog dialog = new InputDialog(new Shell(),"Name");
		 * inputChoice = dialog.open(); if(inputChoice == 0) { file = new
		 * File(RCPContentProvider.getCurrentWorkspacePath() + CompareST.METRICS_FOLDER
		 * + "/" + dialog.getFirstVar()+"."+ CompareST.FILE_ENDING_METRIC);
		 * metric.setMetricName(dialog.getFirstVar()); if(file.exists()) { choice = 1;
		 * RCPMessageProvider.errorMessage(FILE_EXISTS, METRIC_WITH_NAME_EXISTS); } } }
		 */

		// if(choice == 0 || (choice == 1 && inputChoice == 0)) {
		try {
			FileWriter writer = new FileWriter(file);
			TreeItem<NodeUsage> rootItem = trview.getRoot();
			for (TreeItem<NodeUsage> node : rootItem.getChildren()) {
				System.out.println("in for schleife");
				writer.write(node.getValue().toString());
				System.out.println(node.toString());
				writer.write("\n");
			}
			writer.close();
			// output.write();
			// output.close();
			System.out.println("Tree: " + file.getAbsolutePath() + " stored.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getInput(String displayedDialog) {
		TextInputDialog td = new TextInputDialog();
		td.setHeaderText(displayedDialog);
		td.setGraphic(null);
		td.setTitle("Dialog");
		td.showAndWait();
		String s = td.getEditor().getText();
		if (s.equals("") || s.equals(null)) {
			throw new NullPointerException();
		}
		return s;

	}

}
