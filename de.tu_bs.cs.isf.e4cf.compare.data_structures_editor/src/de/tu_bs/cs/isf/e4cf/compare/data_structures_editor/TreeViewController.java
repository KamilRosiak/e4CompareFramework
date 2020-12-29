package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
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
	private Text hitCount;

	@FXML
	private Text totalNodeAmount;

	@FXML
	private TreeView<NodeUsage> treeView;

	@FXML
	private TextField searchTextField;

	private int searchCounter = 0;

	private String currentSearchText;

	private TreeItem<NodeUsage> copiedNode;

	@FXML
	private ContextMenu contextMenu;

	@Optional
	@Inject
	public void openTree(@UIEventTopic("OpenTreeEvent") Tree tree) {
		TreeViewUtilities.switchToPart(DataStructuresEditorST.TREE_VIEW_ID, services);
		treeView = TreeViewUtilities.getTreeViewFromTree(tree, this.treeView);
		treeView = TreeViewUtilities.addListener(treeView, services);
		totalNodeAmount
				.setText("Total Node Amount: " + TreeViewUtilities.searchTreeItem(treeView.getRoot(), "").size());
	}

	/**
	 * A method to close a file
	 */
	@FXML
	void closeFile() {
		// set treeview and its values to null, then remove it from the background
		treeView.setRoot(null);
		services.eventBroker.send("EmptyPropertiesTableEvent", true);
	}

	/**
	 * Selects all elements in the treeview
	 */
	@FXML
	void selectAll() {
		treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		treeView.getSelectionModel().selectAll();
		services.eventBroker.send("EmptyPropertiesTableEvent", true);
	}

	/**
	 * Unselects all elements in the treeview
	 */
	@FXML
	void unselectAll() {
		treeView.getSelectionModel().clearSelection();
		services.eventBroker.send("EmptyPropertiesTableEvent", true);
	}

	/**
	 * searchButton to search what is typed in searchField
	 */
	@FXML
	void search() {
		TreeViewUtilities.clearSearchList();
		treeView.getSelectionModel().clearSelection();
		String searchFieldTextToRead = searchTextField.getText();
		List<TreeItem<NodeUsage>> resultList = TreeViewUtilities.searchTreeItem(treeView.getRoot(),
				searchFieldTextToRead);
		treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		treeView.getSelectionModel().select(getCurrentSearchItem(resultList));
		treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());
		hitCount.setText(getSearchCounter() + "/" + resultList.size());
//		for (TreeItem<NodeUsage> t : TreeViewUtilities.searchTreeItem(treeView.getRoot(), searchFieldTextToRead)) {
//			treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//			treeView.getSelectionModel().select(t);
//			treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());
//		}
//		if (treeView.getSelectionModel().getSelectedItems().size() > 1) {
//			services.eventBroker.send("EmptyPropertiesTableEvent", true);
//		}
		resultList.clear();
	}

	@FXML
	void onEnter(ActionEvent event) {
		if (currentSearchText == null) {
			currentSearchText = searchTextField.getText();
			search();
		} else {
			if (currentSearchText.equals(searchTextField.getText())) {
				search();
			} else {
				setSearchCounter(0);
				search();
				currentSearchText = searchTextField.getText();
			}
		}
	}

	TreeItem<NodeUsage> getCurrentSearchItem(List<TreeItem<NodeUsage>> resultList) {
		TreeItem<NodeUsage> currentItem = new TreeItem<NodeUsage>();
		if (getSearchCounter() < resultList.size()) {
			currentItem = resultList.get(getSearchCounter());
			incrementSearchCounter();
		} else {
			setSearchCounter(0);
			currentItem = resultList.get(getSearchCounter());
			incrementSearchCounter();
		}
		return currentItem;
	}

	int getSearchCounter() {
		return searchCounter;
	}

	void setSearchCounter(int i) {
		searchCounter = i;
	}

	void incrementSearchCounter() {
		searchCounter++;
	}

	@FXML
	void deleteNode() {
		TreeItem<NodeUsage> deletingNode = treeView.getSelectionModel().getSelectedItem();
		treeView.getSelectionModel().getSelectedItem().setValue(null);
		treeView.getSelectionModel().getSelectedItem().setGraphic(null);
		deletingNode.getParent().getChildren().remove(deletingNode);
	}

	/**
	 * 
	 */
	@FXML
	void renameNode() {
		contextMenu.hide();
		List<Attribute> attributeList = new ArrayList<Attribute>();
		try {
			attributeList.add(new AttributeImpl("TEXT", TreeViewUtilities.getInput()));
		} catch (NullPointerException e) {
			return;
		}
		List<Attribute> currentAttributeList = treeView.getSelectionModel().getSelectedItem().getValue()
				.getAttributes();
		currentAttributeList.remove(0);
		attributeList.addAll(currentAttributeList);
		treeView.getSelectionModel().getSelectedItem().getValue().setAttributes(attributeList);
		treeView.refresh();
		services.eventBroker.send("nodePropertiesEvent", treeView.getSelectionModel().getSelectedItem().getValue());
	}

	/**
	 * 
	 */
	@FXML
	void addChild() {
		TreeItem<NodeUsage> newChild = new TreeItem<NodeUsage>();
		newChild.setValue(new NodeUsage("DummyNode"));
		contextMenu.hide();
		try {
			newChild.getValue().addAttribute("TEXT", TreeViewUtilities.getInput());
		} catch (NullPointerException e) {
			return;
		}
		treeView.getSelectionModel().getSelectedItem().getChildren().add(newChild);
	}

	/**
	 * 
	 */
	@FXML
	void copy() {
		copiedNode = treeView.getSelectionModel().getSelectedItem();
	}

	@FXML
	void save() {
		TreeViewUtilities.serializesTree(treeView);
	}

	@FXML
	void paste() {
		try {
			TreeItem<NodeUsage> t = new TreeItem<NodeUsage>();
			t.setValue(copiedNode.getValue());
			treeView.getSelectionModel().getSelectedItem().getParent().getChildren()
					.add(treeView.getSelectionModel().getSelectedIndex(), t);
		} catch (NullPointerException e) {
			System.out.println(e);
		}
	}

}
