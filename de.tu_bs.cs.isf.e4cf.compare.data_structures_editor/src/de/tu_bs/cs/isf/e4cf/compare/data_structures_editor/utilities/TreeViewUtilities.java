package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DataStructuresEditorST;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.FileTable;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Utility Class for TreeViewController
 * 
 * @author Team05
 *
 */
public final class TreeViewUtilities {

    /**
     * Adds a nodes children to the respective TreeItem as children recursively
     * 
     * @param node   trees root object in first method call
     * @param parent treeViews root object in first method call
     */
    public static void createTreeView(Node node, TreeItem<Node> parent) {
	for (Node n : node.getChildren()) {
	    TreeItem<Node> ti = createTreeItem(n);
	    parent.getChildren().add(ti);
	    if (!n.isLeaf()) {
		createTreeView(n, ti);
	    }
	}
    }

    /**
     * Creates TreeItem from a node, assigns an icon
     * 
     * @param node to be transformed
     * @return TreeItem created from the node
     */
    public static TreeItem<Node> createTreeItem(Node node) {
	TreeItem<Node> ti = new TreeItem<Node>(node);
	switch (ti.getValue().getNodeType()) {

	case "WORD":
	    ti.setGraphic(new ImageView(FileTable.nodeImage));
	    break;
	case "LINE":
	    ti.setGraphic(new ImageView(FileTable.lineImage));
	    break;
	default:
	    ti.setGraphic(new ImageView(FileTable.defaultImage));
	    break;
	}
	return ti;
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
    public static List<TreeItem<Node>> searchTreeItem(TreeItem<Node> item, String name,
	    List<TreeItem<Node>> searchList) {
	if (item.getValue().toString().toLowerCase().contains(name)) {
	    searchList.add(item);
	}

	List<TreeItem<Node>> result = new ArrayList<TreeItem<Node>>();
	for (TreeItem<Node> child : item.getChildren()) {
	    result.addAll(searchTreeItem(child, name, searchList));
	    if (result.size() < 1) {
		searchList.addAll(result);
	    }
	}
	return searchList;
    }

    /**
     * Builds a list recursively, which contains item, items children and all
     * grandchildren
     * 
     * @param item        Start item from which the list should be built
     * @param subTreeList Initially empty list to be filled
     * @return Built subTreeList
     */
    public static List<TreeItem<Node>> getSubTreeAsList(TreeItem<Node> item, List<TreeItem<Node>> subTreeList) {
	subTreeList.add(item);
	for (TreeItem<Node> ti : item.getChildren()) {
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
     * @param displayedDialog String which specify the displayed dialog
     * @return User input as string or null in case of invalid input
     */
    public static String getInput(String displayedDialog) {
	// Initialize textInputDialogWindow
	TextInputDialog td = new TextInputDialog();
	td.setHeaderText(displayedDialog);
	td.setGraphic(null);
	td.setTitle("Dialog");
	// EventListener for cancel button - needed for clearing input in event of an
	// abort
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
	// Checks for invalid input
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
     * @param outputText Text that should be displayed
     */
    public static void informationAlert(String outputText) {
	Alert alert = new Alert(AlertType.INFORMATION);
	alert.setHeaderText(null);
	alert.setContentText(outputText);
	alert.setTitle(DataStructuresEditorST.ATTENTION_REQUIRED);
	Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	stage.setAlwaysOnTop(true);
	alert.showAndWait();
    }

    /**
     * Opens dialog window to get a users confirmation for given action
     * 
     * @param outputText Text that should be displayed
     * @return true if user clicks ok, false otherwise
     */
    public static boolean confirmationAlert(String outputText) {
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setHeaderText(null);
	alert.setContentText(outputText);
	alert.setTitle(DataStructuresEditorST.CONFIRMATION_REQUIRED);
	Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	stage.setAlwaysOnTop(true);
	Optional<ButtonType> result = alert.showAndWait();
	if (result.get() == ButtonType.OK) {
	    return true;
	}
	return false;
    }
}
