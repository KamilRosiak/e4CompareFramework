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
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

	public static void switchToPart(String path, ServiceContainer services) {
		services.partService.showPart(path);
	}

	/**
	 * Adds a nodes children to the respective TreeItem as children recursively
	 * 
	 * @param node	trees root object in first method call
	 * @param parent	treeViews root object in first method call 
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
	 * Builds a list of TreeItems which contain the given string based on a given
	 * root item
	 * 
	 * @param item       Start node for the search (should contain all children,
	 *                   which are to be included in the search)
	 * @param name       String to be searched for
	 * @param searchList to store the search results
	 * @return List with all search results
	 */
	public static List<TreeItem<AbstractNode>> searchTreeItem(TreeItem<AbstractNode> item, String name,
			List<TreeItem<AbstractNode>> searchList) {
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
	 * 
	 * @param treeView
	 * @param newFileName
	 */
	public static void serializesTree(TreeView<AbstractNode> treeView, String newFileName) {
		File file = new File(RCPContentProvider.getCurrentWorkspacePath() + "/" + newFileName);
		List<TreeItem<AbstractNode>> listWithoutRoot = getSubTreeAsList(treeView.getRoot(), new ArrayList<TreeItem<AbstractNode>>());
		listWithoutRoot.remove(0);
		writeToFile(listWithoutRoot, file);
	}

	/**
	 * Writes a given list of treeItems to a given file
	 * 
	 * @param list	Should contain all items that should be written to file
	 * @param file	Specifies the file to be written
	 * @return	Built file or null in case of IOException
	 */
	public static File writeToFile(List<TreeItem<AbstractNode>> list, File file) {
		try {
			FileWriter writer = new FileWriter(file);
			for (TreeItem<AbstractNode> node : list) {
				if (node.getValue().getNodeType().equals("LINE")) {
					continue;
				}
				writer.write(node.getValue().toString());
				writer.write("\n");
			}
			writer.close();
			informationAlert(String.format("Tree %s successfully stored at %s", file.getName(), file.getAbsolutePath()));
			return file;
		} catch (IOException e) {
			informationAlert(String.format(DataStructuresEditorST.EXCEPTION_MESSAGE, e));
			return null;
		}
	}

	/**
	 * Builds a list recursively, which contains item, items children and all grandchildren
	 *   
	 * @param item	Start item from which the list should be built
	 * @param subTreeList	Initially empty list to be filled	
	 * @return	Built subTreeList
	 */
	public static List<TreeItem<AbstractNode>> getSubTreeAsList(TreeItem<AbstractNode> item,
			List<TreeItem<AbstractNode>> subTreeList) {
		subTreeList.add(item);
		for (TreeItem<AbstractNode> ti : item.getChildren()) {
			if (!ti.isLeaf()) {
				getSubTreeAsList(ti, subTreeList);
			} else {
				subTreeList.add(ti);
			}
		}
		return subTreeList;
	}

	/**
	 * Opens a TextInputDialog to get input from the user
	 * 
	 * @param displayedDialog	String which specify the displayed dialog
	 * @return User input as string or null in case of invalid input 
	 */
	public static String getInput(String displayedDialog) {
		//Initialize textInputDialogWindow
		TextInputDialog td = new TextInputDialog();
		td.setHeaderText(displayedDialog);
		td.setGraphic(null);
		td.setTitle("Dialog");
		//EventListener for cancel button - needed for clearing input in event of an abort
		td.getDialogPane().lookupButton(ButtonType.CANCEL).addEventFilter(ActionEvent.ACTION,
				event -> td.getEditor().setText(null));
		Stage stage = (Stage) td.getDialogPane().getScene().getWindow();
		stage.setOnCloseRequest(e -> {
			e.consume();
			td.getEditor().setText(null);
			stage.close();
		});
		stage.setAlwaysOnTop(true);
		td.showAndWait();
		String s = td.getEditor().getText();
		//Checks for invalid input 
		if (s.equals("") || s.equals(null)) {
			if (confirmationAlert(DataStructuresEditorST.NO_INPUT_ALERT) == true) {
				return null;
			} else {
				getInput(displayedDialog);
			}
		}
		// Important because of overwriting returns in case of a recursion
		if (s.equals("") || s.equals(null)) {
			return null;
		} else {
			return s;
		}

	}

	/**
	 * Opens informative window to communicate with user
	 * 
	 * @param outputText	Text that should be displayed
	 */
	public static void informationAlert(String outputText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(outputText);
		alert.setTitle(DataStructuresEditorST.ATTENTION_REQUIRED);
		alert.showAndWait();
	}

	/**
	 * Opens dialog window to get a users confirmation for given action
	 * 
	 * @param outputText	Text that should be displayed
	 * @return	true if user clicks ok, false otherwise
	 */
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
