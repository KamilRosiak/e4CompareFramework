package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractNode;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.AddChildNodeAction;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.CommandManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.RenameNodeAction;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DataStructuresEditorST;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.utilities.TreeViewUtilities;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TreeViewController {

	@Inject
	private ServiceContainer services;

	@FXML
	private MenuItem properties;

	@FXML
	private VBox background;

	@FXML
	private Button search;

	@FXML
	private Label testLabel;

	@FXML
	private Text hitCount, totalNodeAmount;

	@FXML
	private TreeView<AbstractNode> treeView;

	@FXML
	private TextField searchTextField;

	@FXML
	private ContextMenu contextMenu;

	private String currentSearchText;

	public int searchCounter;

	private List<TreeItem<AbstractNode>> copyList = new ArrayList<TreeItem<AbstractNode>>();

	CommandManager TreeManager = new CommandManager();

	@Optional
	@Inject
	public void refreshTreeView(@UIEventTopic(DataStructuresEditorST.REFRESH_TREEVIEW_EVENT) boolean bool) {
		treeView.refresh();
	}

	@Optional
	@Inject
	public void removeNodeAttributeFromSelectedItem(
			@UIEventTopic(DataStructuresEditorST.DELETE_ATTRIBUTE_EVENT) String text) {
		List<Attribute> tempList = new ArrayList<Attribute>();
		for (Attribute attr : treeView.getSelectionModel().getSelectedItem().getValue().getAttributes()) {
			tempList.add(attr);
		}
		for (Attribute attribute : tempList) {
			if (attribute.getAttributeKey().equals(text)) {
				treeView.getSelectionModel().getSelectedItem().getValue().getAttributes().remove(attribute);
			}
		}
	}

	@Optional
	@Inject
	public void reopenItem(@UIEventTopic(DataStructuresEditorST.REOPEN_ITEM_EVENT) boolean bool) {
		services.eventBroker.send(DataStructuresEditorST.EMPTY_PROPERTIES_TABLE_EVENT, true);
		services.eventBroker.send(DataStructuresEditorST.NODE_PROPERTIES_EVENT,
				treeView.getSelectionModel().getSelectedItem().getValue());
	}

	private void initializeTree(Tree tree) {
		treeView.setContextMenu(contextMenu);
		background.setOnMouseEntered(event -> contextMenu.hide());
		treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		TreeViewUtilities.switchToPart(DataStructuresEditorST.TREE_VIEW_ID, services);
		closeFile();
		treeView.setRoot(new TreeItem<AbstractNode>(new NodeImpl(tree.getRoot())));
		treeView.getRoot().setExpanded(true);
		treeView.setShowRoot(true);
		TreeViewUtilities.fillTreeView(tree.getRoot(), treeView.getRoot());
		addListener();
		displayTotalNodeAmount();
	}

	private void displayTotalNodeAmount() {
		totalNodeAmount.setText("Total node amount: "
				+ (treeViewToList(treeView.getRoot(), new ArrayList<TreeItem<AbstractNode>>())).size());
		treeView.refresh();
	}

	void deleteNode() {
		treeView.getSelectionModel().getSelectedItem().getParent().getChildren()
				.remove(treeView.getSelectionModel().getSelectedItem());
		displayTotalNodeAmount();
		treeView.refresh();
	}

	private void addAttribute(String attributeName, String attributeValue) {
		treeView.getSelectionModel().getSelectedItem().getValue().addAttribute(attributeName, attributeValue);
		treeView.refresh();
	}

	@Optional
	@Inject
	public void openTree(@UIEventTopic(DataStructuresEditorST.OPEN_TREE_EVENT) Tree tree) {
		initializeTree(tree);
	}

	@FXML
	void addNodeAttribute() {
		String attrName = TreeViewUtilities.getInput("Enter attribute name");
		if (attrName != null) {
			String attrValue = TreeViewUtilities.getInput("Enter attribute value");
			if (attrValue != null) {
				treeView.getSelectionModel().getSelectedItem().getValue().addAttribute(attrName, attrValue);
				reopenItem(true);
			}
		}
		treeView.refresh();
	}

	/**
	 * 
	 */
	@FXML
	void addChildNode() {
		TreeItem<AbstractNode> newChild = new TreeItem<AbstractNode>();
		String childName = TreeViewUtilities.getInput("Enter child name");
		if (childName != null) {
			newChild.setValue(new NodeImpl(childName));
			newChild.getValue().addAttribute("name", childName);
		} else {
			return;
		}
		if (newChild.getValue().getAttributes().get(0).getAttributeKey().equals("TEXT")) {
			newChild.setGraphic(new ImageView(TreeViewUtilities.nodeImage));
		}
		treeView.getSelectionModel().getSelectedItem().getChildren().add(newChild);
		displayTotalNodeAmount();
		treeView.refresh();
		TreeManager.execute(new AddChildNodeAction("addChildNode", newChild, treeView));
	}

	/**
	 * 
	 */
	@FXML
	void copyNode() {
		copyList.clear();
		copyList.addAll(new ArrayList<TreeItem<AbstractNode>>(treeView.getSelectionModel().getSelectedItems()));
	}

	@FXML
	void pasteNode() {
		try {
			for (int i = copyList.size() - 1; i >= 0; i--) {
				TreeItem<AbstractNode> copiedNode = copyList.get(i);
				TreeItem<AbstractNode> tempNode = new TreeItem<AbstractNode>();
				tempNode.setValue(copiedNode.getValue());
				// Idee: Index verwalten über expand/collapse All
				treeView.getSelectionModel().getSelectedItem().getParent().getChildren()
						.add(treeView.getSelectionModel().getSelectedIndex(), tempNode);
				for (TreeItem<AbstractNode> child : copiedNode.getChildren()) {
					tempNode.getChildren().add(child);
				}
				displayTotalNodeAmount();
			}
		} catch (NullPointerException e) {
			return;
		}

	}

	/**
	 * 
	 */
	@FXML
	void renameNode() {
		Attribute atr = null;
		// String prevName =
		// treeView.getSelectionModel().getSelectedItem().getValue().getAttributeForKey("name").toString();
		// System.out.println(treeView.getSelectionModel().getSelectedItem().getValue().getAttributes().toString());
		if (treeView.getSelectionModel().getSelectedItems().size() > 1) {
			TreeViewUtilities
					.informationAlert("Multiple items selected. Rename can only be applied to one item at a time");
			return;
		} else {
			String newName = TreeViewUtilities.getInput("Enter new name");
			if (newName != null) {
				for (Attribute attribute : treeView.getSelectionModel().getSelectedItem().getValue().getAttributes()) {
					if (attribute.getAttributeKey().toLowerCase().equals("name")) {
						attribute.getAttributeValues().clear();
						atr = attribute;
					}
				} // atm noch hardcoded, bessere Version steht oben, funktioniert aber noch nicht
				TreeManager.execute(new RenameNodeAction("renameNode",
						treeView.getSelectionModel().getSelectedItem().getValue().getAttributes().get(0)
								.getAttributeValues().toString(),
						atr, treeView, treeView.getSelectionModel().getSelectedItem().getValue()));
				addAttribute("name", newName);
				treeView.refresh();
				services.eventBroker.send(DataStructuresEditorST.NODE_PROPERTIES_EVENT,
						treeView.getSelectionModel().getSelectedItem().getValue());
			}
		}
	}

	@FXML
	void cutNode() {
		copyNode();
		deleteNode();
	}

	@FXML
	void extractToFile() {
		String fileName = TreeViewUtilities.getInput("Please enter desired file name");
		if (fileName != null) {
			List<TreeItem<AbstractNode>> extractList = new ArrayList<TreeItem<AbstractNode>>();
			expandSelectedItems();
			extractList.addAll(treeView.getSelectionModel().getSelectedItems());
			TreeViewUtilities.extractTree(treeView, fileName, extractList);
			collapseSelectedItems();
		}
	}

	@FXML
	void deleteNodeFXML() {
		if (TreeViewUtilities.confirmationAlert("Are you sure you want to do this?") == true) {
			deleteNode();
		}
	}

	/**
	 * set treeview and its values to null, then remove it from the background
	 */
	@FXML
	void closeFile() {
		treeView.setRoot(null);
		hitCount.setText("");
		searchCounter = 0;
		currentSearchText = null;
		services.eventBroker.send(DataStructuresEditorST.EMPTY_PROPERTIES_TABLE_EVENT, true);
	}

	@FXML
	void saveFile() {
		TreeViewUtilities.serializesTree(treeView);
	}

	@FXML
	void saveAs() {
		String fileName = TreeViewUtilities.getInput("Please enter desired file name");
		if (fileName != null) {
			TreeViewUtilities.serializesTree(treeView, fileName);
		}
	}

	@FXML
	void undoAction() {
		TreeManager.undo();
	}

	@FXML
	void redoAction() {
		System.out.println("REDO");
	}

	/**
	 * Selects all elements in the treeview
	 */
	@FXML
	void selectAllNodes() {
		treeView.getSelectionModel().selectAll();
		services.eventBroker.send(DataStructuresEditorST.EMPTY_PROPERTIES_TABLE_EVENT, true);
	}

	/**
	 * Unselects all elements in the treeview
	 */
	@FXML
	void unselectAllNodes() {
		treeView.getSelectionModel().clearSelection();
		services.eventBroker.send(DataStructuresEditorST.EMPTY_PROPERTIES_TABLE_EVENT, true);
	}

	/**
	 * search the text given in the searchfield
	 */
	void searchForNode() {
//		TreeViewUtilities.searchList.clear();
		List<TreeItem<AbstractNode>> searchList = new ArrayList<TreeItem<AbstractNode>>();
		treeView.getSelectionModel().clearSelection();
		List<TreeItem<AbstractNode>> resultList = TreeViewUtilities.searchTreeItem(treeView.getRoot(),
				searchTextField.getText().toLowerCase(), searchList);
		if (searchCounter >= resultList.size()) {
			searchCounter = 0;
		}
		treeView.getSelectionModel().select(resultList.get(searchCounter));
		treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());
		hitCount.setText((searchCounter + 1) + "/" + resultList.size());
		resultList.clear();
	}

	@FXML
	void startSearch() {
		if (currentSearchText == null) { // Used for inital search string
			currentSearchText = searchTextField.getText();
			searchForNode();
		} else { // Used if second click on searchButton or enter occured
			if (currentSearchText.equalsIgnoreCase(searchTextField.getText())) { // Used if second call is with the same
																					// string
				searchCounter++;
				searchForNode();
			} else { // Used if second call is with another string than the initial one
				searchCounter = 0;
				searchForNode();
				currentSearchText = searchTextField.getText();
			}
		}
	}

	/**
	 * Adds a listener to the TreeView so that PropertiesTable of a highlighted node
	 * is displayed
	 * 
	 * @param treeView
	 * @param services
	 * @return
	 */
	public void addListener() {
		treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (treeView.getSelectionModel().getSelectedIndices().size() == 1) {
				TreeViewUtilities.switchToPart(DataStructuresEditorST.PROPERTIES_VIEW_ID, services);
				services.eventBroker.send(DataStructuresEditorST.NODE_PROPERTIES_EVENT,
						treeView.getSelectionModel().getSelectedItem().getValue());
			}
		});
	}

	@FXML
	public void expandAllItems() {
		for (TreeItem<AbstractNode> ti : treeViewToList(treeView.getRoot(), new ArrayList<TreeItem<AbstractNode>>())) {
			ti.setExpanded(true);
		}
	}

	@FXML
	public void collapseAllItems() {
		for (TreeItem<AbstractNode> ti : treeViewToList(treeView.getRoot(), new ArrayList<TreeItem<AbstractNode>>())) {
			ti.setExpanded(false);
		}
	}

	public List<TreeItem<AbstractNode>> treeViewToList(TreeItem<AbstractNode> item,
			List<TreeItem<AbstractNode>> treeViewList) {
		if (item.getValue().isRoot()) {
			treeViewList.add(item);
		}
		for (TreeItem<AbstractNode> ti : item.getChildren()) {
			treeViewList.add(ti);
			if (!ti.isLeaf()) {
				treeViewToList(ti, treeViewList);
			}
		}
		return treeViewList;
	}

	@FXML
	public void expandSelectedItems() {
		// tempList necessary because we wanted to select all children, too
		List<TreeItem<AbstractNode>> tempList = new ArrayList<TreeItem<AbstractNode>>();
		for (TreeItem<AbstractNode> ti : treeView.getSelectionModel().getSelectedItems()) {
			tempList.addAll(TreeViewUtilities.getSubTreeAsList(ti, new ArrayList<TreeItem<AbstractNode>>()));
		}
		for (TreeItem<AbstractNode> ti : tempList) {
			ti.setExpanded(true);
			treeView.getSelectionModel().select(ti);
		}
	}

	@FXML
	public void collapseSelectedItems() {
		for (TreeItem<AbstractNode> ti : treeView.getSelectionModel().getSelectedItems()) {
			ti.setExpanded(false);
		}
	}
}
