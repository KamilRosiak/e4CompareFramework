package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractNode;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DataStructuresEditorST;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Utility Class for TreeViewController
 * 
 * @author Team05
 *
 */
public final class TreeViewUtilities {

	public static String treeName = "";

	public static final Image nodeImage = new Image("icons/file16.png");

	public static final Image rootImage = new Image("icons/rootSmall.png");

	//private static int searchCounter = 0;

	public static void switchToPart(String path, ServiceContainer services) {
		services.partService.showPart(path);
	}

	/**
	 * Adds a nodes children to the respective TreeItem as children recursively
	 * 
	 * @param	node
	 * @param	parent
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
	 * Builds a list of TreeItems which contain the given string based on a given root item
	 * @param	item	Start node for the search (should contain all children, which are to be included in the search) 
	 * @param	name	String to be searched for 
	 * @param	searchList	to store the search results
	 * @return	List	with all search results
	 */
	public static List<TreeItem<AbstractNode>> searchTreeItem(TreeItem<AbstractNode> item, String name, List<TreeItem<AbstractNode>> searchList) {
		if (item.getValue().toString().toLowerCase().contains(name)) {
			searchList.add(item);
		}
		List<TreeItem<AbstractNode>> result = new ArrayList<TreeItem<AbstractNode>>();
		for (TreeItem<AbstractNode> child : item.getChildren()) {
			result.addAll(searchTreeItem(child, name, searchList));
			if (result.size() < 1) {
				searchList.addAll(result);
			}
		}
		return searchList;
	}
    
	/**
	 * 
	 * @param treeView
	 */
	public static void serializesTree(TreeView<AbstractNode> treeView) {
		File file = new File(RCPContentProvider.getCurrentWorkspacePath() + "/" + treeName);
		writeToFile(file, treeView);
	}
	
	/**
	 * 
	 * @param treeView
	 * @param newFileName
	 */
	public static void serializesTree(TreeView<AbstractNode> treeView, String newFileName) {
		File file = new File(RCPContentProvider.getCurrentWorkspacePath() + "/" + newFileName);
		writeToFile(file, treeView);
	}

	/**
	 * 
	 * @param treeView
	 * @param newFileName
	 * @param tempList
	 */
	public static void extractTree(TreeView<AbstractNode> treeView, String newFileName,
			List<TreeItem<AbstractNode>> tempList) {
		File file = new File(RCPContentProvider.getCurrentWorkspacePath() + "/" + newFileName);
		try {
			FileWriter writer = new FileWriter(file);
			for (TreeItem<AbstractNode> node : tempList) {
				if(node.getValue().getNodeType().equals("LINE")) {
					continue;
				}
				writer.write(node.getValue().toString());
				writer.write("\n");
			}
			writer.close();
			informationAlert(String.format("Tree %s successfully stored at %s", newFileName, file.getAbsolutePath()));
		} catch (IOException e) {
			informationAlert(String.format(DataStructuresEditorST.EXCEPTION_MESSAGE, e));
		}
	}
	
	public static List<TreeItem<AbstractNode>> getSubTreeAsList(TreeItem<AbstractNode> item, List<TreeItem<AbstractNode>> tempList) {
		tempList.add(item);
		for (TreeItem<AbstractNode> ti : item.getChildren()) {
			if (!ti.isLeaf()) {
				getSubTreeAsList(ti, tempList);
			} else {
				tempList.add(ti);
			}
		}
		return tempList;		
	}

	/**
	 * Opens a TextInputDialog to get input from the user
	 * @param	displayedDialog	String which specify the displayed dialog
	 * @return	User input
	 */
	public static String getInput(String displayedDialog) {
		TextInputDialog td = new TextInputDialog();
		td.setHeaderText(displayedDialog);
		td.setGraphic(null);
		td.setTitle("Dialog");
		Stage stage = (Stage) td.getDialogPane().getScene().getWindow();
		stage.setAlwaysOnTop(true);
		Button closeButton = (Button) td.getDialogPane().lookupButton(ButtonType.CLOSE);
		Button cancelButton = (Button) td.getDialogPane().lookupButton(ButtonType.CANCEL);
		td.showAndWait();
		String s = td.getEditor().getText();
		if (s.equals("") || s.equals(null)) {
			if(confirmationAlert(DataStructuresEditorST.NO_INPUT_ALERT) == true) {
				return null;
			} else {
				getInput(displayedDialog);
			}
		}
		//Important because of overwriting returns in case of a recursion
		if (s.equals("") || s.equals(null) || closeButton.isPressed() || cancelButton.isPressed()) {
			return null;
		} else {
			return s;
		}

	}

	/**
	 * 
	 * @param file
	 * @param treeView
	 */
	public static void writeToFile(File file, TreeView<AbstractNode> treeView) {
		if (file.getName().equals(treeName)) {
			file.delete();
		}

		try {
			FileWriter writer = new FileWriter(file);
			TreeItem<AbstractNode> rootItem = treeView.getRoot();
			for (TreeItem<AbstractNode> node : rootItem.getChildren()) {
				if(node.getValue().getNodeType().equals("LINE")) {
					continue;
				}
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
		alert.setTitle(DataStructuresEditorST.ATTENTION_REQUIRED);
		alert.showAndWait();
	}

	public static boolean confirmationAlert(String outputText) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setContentText(outputText);
		alert.setTitle(DataStructuresEditorST.CONFIRMATION_REQUIRED);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		}
		return false;
	}
}
