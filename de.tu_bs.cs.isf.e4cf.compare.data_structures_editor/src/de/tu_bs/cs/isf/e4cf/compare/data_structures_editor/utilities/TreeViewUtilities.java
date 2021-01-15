package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.NodeUsage;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DataStructuresEditorST;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * Utility Class for TreeViewController
 * @author Team05
 *
 */
public final class TreeViewUtilities {

	public static List<TreeItem<NodeUsage>> searchList = new ArrayList<TreeItem<NodeUsage>>();

	public static String treeName = "";

	private static int searchCounter = 0;
	
	public static List<TreeItem<NodeUsage>> list = new ArrayList<TreeItem<NodeUsage>>();

	public static void switchToPart(String path, ServiceContainer services) {
		services.partService.showPart(path);
	}

	/**
	 * Creates TreeView from given Tree
	 * @param tr
	 * @param treeView
	 * @return
	 */
	public static TreeView<NodeUsage> getTreeViewFromTree(Tree tr, TreeView<NodeUsage> treeView) {
		TreeItem<NodeUsage> rootItem = new TreeItem<NodeUsage>(new NodeUsage(tr.getRoot()));
		rootItem.setExpanded(true);
		treeView.setRoot(rootItem);
		System.out.println("root: " + rootItem + "children: " + rootItem.getChildren() + tr.getRoot().getChildren());
		treeView.setRoot(rootItem);
//		getTreeRecursively(tr, treeView, rootItem);
		for (Node node : tr.getLeaves()) {
			TreeItem<NodeUsage> item = new TreeItem<NodeUsage>(new NodeUsage(node));
			rootItem.getChildren().add(item);
		}

		//tv.setRoot(rootItem);
		treeView.setShowRoot(true);

		return treeView;
	}

	
	/**
	 * Creates TreeView from given Tree recursively
	 * @param tr
	 * @param treeView
	 * @param item
	 * @return
	 */
	public static TreeView<NodeUsage> getTreeViewFromTree(Tree tr, TreeView<NodeUsage> treeView,
			Node item) {

		if (item.isRoot()) {
			treeView.setRoot(new TreeItem<NodeUsage>(new NodeUsage(item)));
			treeView.getRoot().setExpanded(true);
			treeView.setShowRoot(true);
			list.add(new TreeItem<NodeUsage>(new NodeUsage(item)));
		} 
		if(!item.isLeaf()) {
			for(Node n : item.getChildren()) {
				getTreeViewFromTree(tr, treeView, n);
			}
		} else {
			treeView.getRoot().getChildren().add(new TreeItem<NodeUsage>(new NodeUsage(item)));		
		}
		return treeView;
	}
	
//	public static TreeItem<NodeUsage> findTreeItemInTreeView(TreeView<NodeUsage> treeView, Node item){
//		
//		treeViewToList(treeView.getRoot());
//		for (TreeItem<NodeUsage> n : list ) {
//			System.out.println("item: " + item);
//			System.out.println("n: " + n.getValue());
//			
//			NodeUsage x = new NodeUsage (item);
//			if (n.getValue().getUUID().equals(x.getUUID())) {
//				System.out.println("uuid gleich");
//				return n;
//			} else {
//				continue;
//			}
//			
//		}
//		return null;
//	}
//	
//	public static void treeViewToList(TreeItem<NodeUsage> item){
//		
//		if (!(item.getValue().getAttributes().isEmpty())){
//			list.add(item);
//		}
//		
//		if(!item.getValue().isLeaf()) {
//			System.out.println(item + "hasChildren");
//			for(TreeItem<NodeUsage> t : item.getChildren())
//			treeViewToList(t);
//		}
//		System.out.println("treeViewToList" + list);
//	}

//	public static TreeView<NodeUsage> getTreeRecursively(Tree tr, TreeView<NodeUsage> treeView, TreeItem<NodeUsage> item) {
//		nextItem = item;
//		System.out.println("for schleife");
//		//TreeItem<NodeUsage> nextItem = new TreeItem<NodeUsage>(new NodeUsage(node));
//		System.out.println("children: " + item.getValue().getChildren());
//		for(Node n: item.getValue().getChildren()) {
//			nextItem.getChildren().add(new TreeItem<NodeUsage>(new NodeUsage(n)));
//			System.out.println(n);
//			TreeItem<NodeUsage> next = new TreeItem<NodeUsage>(new NodeUsage(n));
//			getTreeRecursively(tr, treeView, next);
//		}
//	    
//		return treeView;
//	}

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

	public static void serializesTree(TreeView<NodeUsage> treeView) {
		File file = new File(RCPContentProvider.getCurrentWorkspacePath() + "/" + treeName);
		writeToFile(file, treeView);
	}

	public static void serializesTree(TreeView<NodeUsage> treeView, String newFileName) {
		File file = new File(RCPContentProvider.getCurrentWorkspacePath() + "/" + newFileName);
		writeToFile(file, treeView);
	}

	public static void extractTree(TreeView<NodeUsage> treeView, String newFileName, List<TreeItem> tempList) {
		File file = new File(RCPContentProvider.getCurrentWorkspacePath() + "/" + newFileName);
		try {
			FileWriter writer = new FileWriter(file);
			for (TreeItem<NodeUsage> node : tempList) {
				writer.write(node.getValue().toString());
				writer.write("\n");
			}
			writer.close();
			System.out.println("Tree: " + file.getAbsolutePath() + " stored.");
		} catch (IOException e) {
			alert("Es ist eine " + e + "aufgetreten");
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
			alert("Bitte einen Wert eingeben");
		}
		return s;

	}

	public static TreeItem<NodeUsage> getCurrentSearchItem(List<TreeItem<NodeUsage>> resultList) {
		TreeItem<NodeUsage> currentItem = new TreeItem<NodeUsage>();
		if (getSearchCounter() < resultList.size()) {
			currentItem = resultList.get(getSearchCounter());
		} else {
			setSearchCounter(0);
			currentItem = resultList.get(getSearchCounter());
		}
		incrementSearchCounter();
		return currentItem;
	}

	public static int getSearchCounter() {
		return searchCounter;
	}

	public static void setSearchCounter(int i) {
		searchCounter = i;
	}

	public static void incrementSearchCounter() {
		searchCounter++;
	}
	

	public static void writeToFile(File file, TreeView treeView) {
		if (file.getName().equals(treeName)) {
			file.delete();
		}

		try {
			FileWriter writer = new FileWriter(file);
			TreeItem<NodeUsage> rootItem = treeView.getRoot();
			for (TreeItem<NodeUsage> node : rootItem.getChildren()) {
				writer.write(node.getValue().toString());
				writer.write("\n");
			}
			writer.close();
			System.out.println("Tree: " + file.getAbsolutePath() + " stored.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void alert(String outputText) {
		Alert alert = new Alert (AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(outputText);
		alert.setTitle("Fehler");
		alert.showAndWait();
	}
}
