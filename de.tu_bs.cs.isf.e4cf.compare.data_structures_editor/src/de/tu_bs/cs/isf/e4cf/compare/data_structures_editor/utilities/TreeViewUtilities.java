package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractNode;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DataStructuresEditorST;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;

/**
 * Utility Class for TreeViewController
 * 
 * @author Team05
 *
 */
public final class TreeViewUtilities {

	public static List<TreeItem<AbstractNode>> searchList = new ArrayList<TreeItem<AbstractNode>>();

	public static String treeName = "";

	public static Image nodeImage = new Image("icons/file16.png");

	public static Image rootImage = new Image("icons/rootSmall.png");

	private static int searchCounter = 0;

	public static void switchToPart(String path, ServiceContainer services) {
		services.partService.showPart(path);
	}

	/**
	 * Adds a nodes children to the respective TreeItem as children
	 * 
	 * @param node
	 * @param parent
	 */
	public static void fillTreeView(Node node, TreeItem<AbstractNode> parent) {
		for (Node n : node.getChildren()) {
			TreeItem<AbstractNode> ti = new TreeItem<AbstractNode>(new NodeImpl(n));
			parent.getChildren().add(ti);
			if (!n.isLeaf()) {
				fillTreeView(n, ti);
			}
		}
	}

	/**
	 * 
	 * @param treeView
	 * @param services
	 * @return
	 */
	public static void addListener(TreeView<AbstractNode> treeView, ServiceContainer services) {
		treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (treeView.getSelectionModel().getSelectedIndices().size() == 1) {
				switchToPart(DataStructuresEditorST.PROPERTIES_VIEW_ID, services);
				services.eventBroker.send("NodePropertiesEvent",
						treeView.getSelectionModel().getSelectedItem().getValue());
			}
		});
	}

	/**
	 * 
	 * @param item
	 * @param name
	 * @return
	 */
	public static List<TreeItem<AbstractNode>> searchTreeItem(TreeItem<AbstractNode> item, String name) {
		if (item.getValue().toString().contains(name)) {
			searchList.add(item);
		}
		List<TreeItem<AbstractNode>> result = new ArrayList<TreeItem<AbstractNode>>();
		for (TreeItem<AbstractNode> child : item.getChildren()) {
			result.addAll(searchTreeItem(child, name));
			if (result.size() < 1) {
				searchList.addAll(result);
			}
		}
		return searchList;
	}

	public static void serializesTree(TreeView<AbstractNode> treeView) {
		File file = new File(RCPContentProvider.getCurrentWorkspacePath() + "/" + treeName);
		writeToFile(file, treeView);
	}

	public static void serializesTree(TreeView<AbstractNode> treeView, String newFileName) {
		File file = new File(RCPContentProvider.getCurrentWorkspacePath() + "/" + newFileName);
		writeToFile(file, treeView);
	}

	public static void extractTree(TreeView<AbstractNode> treeView, String newFileName,
			List<TreeItem<AbstractNode>> tempList) {
		File file = new File(RCPContentProvider.getCurrentWorkspacePath() + "/" + newFileName);
		try {
			FileWriter writer = new FileWriter(file);
			for (TreeItem<AbstractNode> node : tempList) {
				writer.write(node.getValue().toString());
				writer.write("\n");
			}
			writer.close();
			System.out.println("Tree: " + file.getAbsolutePath() + " stored.");
		} catch (IOException e) {
			informationAlert("Es ist eine " + e + "aufgetreten");
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
			informationAlert("Bitte einen Wert eingeben");
		}
		return s;

	}

	public static TreeItem<AbstractNode> getCurrentSearchItem(List<TreeItem<AbstractNode>> resultList) {
		TreeItem<AbstractNode> currentItem = new TreeItem<AbstractNode>();
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

	public static void writeToFile(File file, TreeView<AbstractNode> treeView) {
		if (file.getName().equals(treeName)) {
			file.delete();
		}

		try {
			FileWriter writer = new FileWriter(file);
			TreeItem<AbstractNode> rootItem = treeView.getRoot();
			for (TreeItem<AbstractNode> node : rootItem.getChildren()) {
				writer.write(node.getValue().toString());
				writer.write("\n");
			}
			writer.close();
			System.out.println("Tree: " + file.getAbsolutePath() + " stored.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void informationAlert(String outputText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(outputText);
		alert.setTitle("Error");
		alert.showAndWait();
	}

	public static boolean confirmationAlert(String outputText) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setContentText(outputText);
		alert.setTitle("Confirmation required");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		}
		return false;
	}
}
