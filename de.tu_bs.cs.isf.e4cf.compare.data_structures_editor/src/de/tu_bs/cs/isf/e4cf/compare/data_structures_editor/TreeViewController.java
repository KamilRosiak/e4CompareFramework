package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractAttribute;
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

	private String currentSearchText;

	@FXML
	private ContextMenu contextMenu;

	private List<TreeItem> copyList = new ArrayList<TreeItem>();

	private void initiallizeTree(Tree tree) {
		treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		TreeViewUtilities.switchToPart(DataStructuresEditorST.TREE_VIEW_ID, services);
		closeFile();
		treeView.setRoot(new TreeItem<NodeUsage>(new NodeUsage(tree.getRoot())));
		treeView = TreeViewUtilities.getTreeViewFromTree(tree, this.treeView,
				new TreeItem<NodeUsage>(new NodeUsage(tree.getRoot())));
		treeView = TreeViewUtilities.addListener(treeView, services);
		// totalNodeAmount
		// .setText("Total Node Amount: " +
		// TreeViewUtilities.searchTreeItem(treeView.getRoot(), "").size());
		displayTotalNodeAmount();
	}

	private void displayTotalNodeAmount() {
		totalNodeAmount.setText("Total Node Amount: " + (treeView.getRoot().getChildren().size() + 1));
		treeView.refresh();
	}

	private void addAttribute(String attributeName, String attributeValue) {
		treeView.getSelectionModel().getSelectedItem().getValue().addAttribute(attributeName, attributeValue);
		treeView.refresh();
	}

	@Optional
	@Inject
	public void openTree(@UIEventTopic("OpenTreeEvent") Tree tree) {
		initiallizeTree(tree);
	}

	@FXML
	void addAttribute() {
//			treeView.getSelectionModel().getSelectedItem().getValue().addAttribute(
//					TreeViewUtilities.getInput("Enter attribute name"),
//					TreeViewUtilities.getInput("Enter attribute value"));
		addAtrrOnIndex();
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

	/**
	 * 
	 */
	@FXML
	void addChild() {
		TreeItem<NodeUsage> newChild = new TreeItem<NodeUsage>();
		newChild.setValue(new NodeUsage("DummyNode"));
		contextMenu.hide();
		try {
			newChild.getValue().addAttribute("TEXT", TreeViewUtilities.getInput("Enter Child Text"));
		} catch (NullPointerException e) {
			return;
		}
		treeView.getSelectionModel().getSelectedItem().getChildren().add(newChild);
		displayTotalNodeAmount();
		treeView.refresh();
	}

	/**
	 * 
	 */
	@FXML
	void copy() {
		contextMenu.hide();
		copyList.clear();
		copyList.addAll(treeView.getSelectionModel().getSelectedItems()); // Bug: Paste ist spiegelverkehrt bei oben
																			// nach unten Markierung
	}

	@FXML
	void paste() {

		contextMenu.hide();

		try {
			for (TreeItem<NodeUsage> copiedNode : copyList) {
				TreeItem<NodeUsage> t = new TreeItem<NodeUsage>();
				t.setValue(copiedNode.getValue());
				treeView.getSelectionModel().getSelectedItem().getParent().getChildren()
						.add(treeView.getSelectionModel().getSelectedIndex(), t);
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
	void renameNode() { // Bug: Ändert nicht die Property sondern fügt nur neue Property hinzu mit neuem
						// Value
		contextMenu.hide();
		for (Attribute attribute : treeView.getSelectionModel().getSelectedItem().getValue().getAttributes()) {
			if (attribute.getAttributeKey().toLowerCase().equals("name")) {
				attribute.getAttributeValues().clear();
			}
		}
		addAttribute("name", TreeViewUtilities.getInput("Enter new name"));
		treeView.refresh();
		services.eventBroker.send("nodePropertiesEvent", treeView.getSelectionModel().getSelectedItem().getValue());
	}

	@FXML
	void cut() {
		copy();
		deleteNode();
	}

	@FXML
	void extractToFile() {
		List<TreeItem> extractList = new ArrayList<TreeItem>();
		extractList.addAll(treeView.getSelectionModel().getSelectedItems());
		TreeViewUtilities.extractTree(treeView, TreeViewUtilities.getInput("Extract to File"), extractList);
	}

	@FXML
	void deleteNode() {
		for (int i = 0; i < copyList.size(); i++) {
			treeView.getSelectionModel().getSelectedItem().getParent().getChildren()
					.remove(treeView.getSelectionModel().getSelectedItem());
			displayTotalNodeAmount();
		}
		treeView.refresh();
	}

	/**
	 * A method to close a file #UNFINISHED throws nullpointer when new file is
	 * opened
	 */
	@FXML
	void closeFile() {
		// set treeview and its values to null, then remove it from the background
		treeView.setRoot(null);
		services.eventBroker.send("EmptyPropertiesTableEvent", true);
	}

	@FXML
	void save() {
		TreeViewUtilities.serializesTree(treeView);
	}

	@FXML
	void saveAs() {
		TreeViewUtilities.serializesTree(treeView, TreeViewUtilities.getInput("Save as"));
	}

	@FXML
	void undo() {
		System.out.println("undo");

	}

	@FXML
	void redo() {
		System.out.println("redo");
	}

	/**
	 * Selects all elements in the treeview
	 */
	@FXML
	void selectAll() {
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
	 * search the text given in the searchfield
	 */
	@FXML
	void search() {
		TreeViewUtilities.clearSearchList();
		treeView.getSelectionModel().clearSelection();
		List<TreeItem<NodeUsage>> resultList = TreeViewUtilities.searchTreeItem(treeView.getRoot(),
				searchTextField.getText());
		treeView.getSelectionModel().select(TreeViewUtilities.getCurrentSearchItem(resultList));
		treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());
		hitCount.setText(TreeViewUtilities.getSearchCounter() + "/" + resultList.size());
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
				TreeViewUtilities.setSearchCounter(0);
				search();
				currentSearchText = searchTextField.getText();
			}
		}
	}

}
