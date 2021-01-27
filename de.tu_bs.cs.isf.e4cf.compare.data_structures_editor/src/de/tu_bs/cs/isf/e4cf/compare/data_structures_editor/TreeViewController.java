package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractAttribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractNode;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DataStructuresEditorST;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.utilities.TreeViewUtilities;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.event.ActionEvent;
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

	private List<TreeItem<AbstractNode>> copyList = new ArrayList<TreeItem<AbstractNode>>();

	@Optional
	@Inject
	public void refreshTreeView(@UIEventTopic("RefreshTreeViewEvent") boolean bool) {
		treeView.refresh();
	}

	@Optional
	@Inject
	public void removeNodeAttributeFromSelectedItem(@UIEventTopic("DeleteAttributeEvent") String text) {
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
	public void reopenItem(@UIEventTopic("ReopenItemEvent") boolean bool) {
		services.eventBroker.send("EmptyPropertiesTableEvent", true);
		services.eventBroker.send("NodePropertiesEvent", treeView.getSelectionModel().getSelectedItem().getValue());
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
		// totalNodeAmount
		// .setText("Total Node Amount: " +
		// TreeViewUtilities.searchTreeItem(treeView.getRoot(), "").size());
		displayTotalNodeAmount();
	}

	private void displayTotalNodeAmount() {
		totalNodeAmount.setText("Total Node Amount: " + (treeView.getRoot().getChildren().size() + 1));
		treeView.refresh();
	}

	void deleteNode() {
		treeView.getSelectionModel().getSelectedItem().getParent().getChildren()
				.remove(treeView.getSelectionModel().getSelectedItem());
		displayTotalNodeAmount();
		treeView.refresh();
	}

	void addAtrrOnIndex() {
		int place = Integer.parseInt(TreeViewUtilities.getInput("Enter Place"));
		AbstractAttribute attribute = (AbstractAttribute) treeView.getSelectionModel().getSelectedItem().getValue()
				.getAttributes().get(place);
		attribute.getAttributeValues().clear();
		attribute.setAttributeKey(TreeViewUtilities.getInput("Enter attribute name"));
		attribute.getAttributeValues().add(TreeViewUtilities.getInput("Enter attribute value"));
	}

	private void addAttribute(String attributeName, String attributeValue) {
		treeView.getSelectionModel().getSelectedItem().getValue().addAttribute(attributeName, attributeValue);
		treeView.refresh();
	}

	@Optional
	@Inject
	public void openTree(@UIEventTopic("OpenTreeEvent") Tree tree) {
		initializeTree(tree);
	}

	@FXML
	void addNodeAttribute() {
		treeView.getSelectionModel().getSelectedItem().getValue().addAttribute(
				TreeViewUtilities.getInput("Enter attribute name"),
				TreeViewUtilities.getInput("Enter attribute value"));
		// addAtrrOnIndex();
		treeView.refresh();
	}

	/**
	 * 
	 */
	@FXML
	void addChildNode() {
		TreeItem<AbstractNode> newChild = new TreeItem<AbstractNode>();
		newChild.setValue(new NodeImpl("DummyNode"));
		try {
			newChild.getValue().addAttribute("TEXT", TreeViewUtilities.getInput("Enter Child Text"));
		} catch (NullPointerException e) {
			return;
		}
		if (newChild.getValue().getAttributes().get(0).getAttributeKey().equals("TEXT")) {
			newChild.setGraphic(new ImageView(TreeViewUtilities.nodeImage));
		}
		treeView.getSelectionModel().getSelectedItem().getChildren().add(newChild);
		displayTotalNodeAmount();
		treeView.refresh();
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
		for (Attribute attribute : treeView.getSelectionModel().getSelectedItem().getValue().getAttributes()) {
			if (attribute.getAttributeKey().toLowerCase().equals("name")) {
				attribute.getAttributeValues().clear();
			}
		}
		addAttribute("name", TreeViewUtilities.getInput("Enter new name"));
		treeView.refresh();
		services.eventBroker.send("NodePropertiesEvent", treeView.getSelectionModel().getSelectedItem().getValue());
	}

	@FXML
	void cutNode() {
		copyNode();
		deleteNode();
	}

	@FXML
	void extractToFile() {
		List<TreeItem<AbstractNode>> extractList = new ArrayList<TreeItem<AbstractNode>>();
		extractList.addAll(treeView.getSelectionModel().getSelectedItems());
		TreeViewUtilities.extractTree(treeView, TreeViewUtilities.getInput("Extract to File"), extractList);
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
		services.eventBroker.send("EmptyPropertiesTableEvent", true);
	}

	@FXML
	void saveFile() {
		TreeViewUtilities.serializesTree(treeView);
	}

	@FXML
	void saveAs() {
		TreeViewUtilities.serializesTree(treeView, TreeViewUtilities.getInput("Save as"));
	}

	@FXML
	void undoAction() {
		System.out.println("UNDO");
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
		services.eventBroker.send("EmptyPropertiesTableEvent", true);
	}

	/**
	 * Unselects all elements in the treeview
	 */
	@FXML
	void unselectAllNodes() {
		treeView.getSelectionModel().clearSelection();
		services.eventBroker.send("EmptyPropertiesTableEvent", true);
	}

	/**
	 * search the text given in the searchfield
	 */
	@FXML
	void searchForNode() {
//		TreeViewUtilities.searchList.clear();
		List<TreeItem<AbstractNode>> searchList = new ArrayList<TreeItem<AbstractNode>>();
		treeView.getSelectionModel().clearSelection();
		List<TreeItem<AbstractNode>> resultList = TreeViewUtilities.searchTreeItem(treeView.getRoot(),
				searchTextField.getText(), searchList);
		treeView.getSelectionModel().select(TreeViewUtilities.getCurrentSearchItem(resultList));
		treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());
		hitCount.setText(TreeViewUtilities.getSearchCounter() + "/" + resultList.size());
		resultList.clear();
	}

	@FXML
	void startSearch(ActionEvent event) {
		if (currentSearchText == null) {
			currentSearchText = searchTextField.getText();
			searchForNode();
		} else {
			if (currentSearchText.equals(searchTextField.getText())) {
				searchForNode();
			} else {
				TreeViewUtilities.setSearchCounter(0);
				searchForNode();
				currentSearchText = searchTextField.getText();
			}
		}
	}
	
	/**
	 * Adds a listener to the TreeView so that PropertiesTable of a highlighted node is displayed
	 * @param treeView
	 * @param services
	 * @return
	 */
	public void addListener() {
		treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (treeView.getSelectionModel().getSelectedIndices().size() == 1) {
				TreeViewUtilities.switchToPart(DataStructuresEditorST.PROPERTIES_VIEW_ID, services);
				services.eventBroker.send("NodePropertiesEvent",
						treeView.getSelectionModel().getSelectedItem().getValue());
			}
		});
	}
	@FXML
	public void expandAllItems() {
		for(TreeItem<AbstractNode> ti : treeViewToList(treeView.getRoot(), new ArrayList<TreeItem<AbstractNode>>())) {
			ti.setExpanded(true);
		}
	}
	
	@FXML
	public void collapseAllItems() {
		for(TreeItem<AbstractNode> ti : treeViewToList(treeView.getRoot(), new ArrayList<TreeItem<AbstractNode>>())) {
			ti.setExpanded(false);
		}
	}
	
	public List<TreeItem<AbstractNode>> treeViewToList(TreeItem<AbstractNode> item, List<TreeItem<AbstractNode>> treeViewList) {
		if(item.getValue().isRoot()) {
			treeViewList.add(item);
		}
		for(TreeItem<AbstractNode> ti : item.getChildren()) {
			treeViewList.add(ti);
			if(!ti.isLeaf()) {
				treeViewToList(ti, treeViewList);
			}
		}
		return treeViewList;
	}
	
	@FXML
	public void expandSelectedItems() {
		for(TreeItem<AbstractNode> ti : treeView.getSelectionModel().getSelectedItems()) {
			ti.setExpanded(true);
		}
	}
	
	@FXML
	public void collapseSelectedItems() {
		for(TreeItem<AbstractNode> ti : treeView.getSelectionModel().getSelectedItems()) {
			ti.setExpanded(false);
		}
	}
}
