package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DataStructuresEditorST;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.utilities.TreeViewUtilities;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

/**
 * Controller for VisualizeTreeView.fxml
 * 
 * @author Team05
 *
 */
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
	private TreeView<NodeUsage> treeView;

	@FXML
	private TextField searchTextField;

	private int searchCounter = 0;

	private String currentSearchText;

	/**
	 * switching View to TreeView if a .txt file is selected from the explorer
	 * 
	 * @param tree data structure of type tree
	 */
	@Optional
	@Inject
	public void openTree(@UIEventTopic("OpenTreeEvent") Tree tree) {
		TreeViewUtilities.switchToPart(DataStructuresEditorST.TREE_VIEW_ID, services);
		setTreeView(TreeViewUtilities.getTreeViewFromTree(tree, this.treeView));
		setTreeView(TreeViewUtilities.addListener(treeView, services));
		//treeView = TreeViewUtilities.getTreeViewFromTree(tree, this.treeView);
		//treeView = TreeViewUtilities.addListener(treeView, services);
	}

	/**
	 * A method to close a file
	 */
	@FXML
	void closeFile() {
		// set treeview and its values to null, then remove it from the background
		getTreeView().setRoot(null);
//		treeView.setRoot(null);
		services.eventBroker.send("EmptyPropertiesTableEvent", true);
	}

	/**
	 * Selects all elements in the treeview
	 */
	@FXML
	void selectAll() {
		getTreeView().getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		getTreeView().getSelectionModel().selectAll();
//		treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//		treeView.getSelectionModel().selectAll();
		services.eventBroker.send("EmptyPropertiesTableEvent", true);
	}

	/**
	 * Unselects all elements in the treeview
	 */
	@FXML
	void unselectAll() {
		getTreeView().getSelectionModel().clearSelection();
//		treeView.getSelectionModel().clearSelection();
		services.eventBroker.send("EmptyPropertiesTableEvent", true);
	}

	/**
	 * searchButton to search what is typed in searchField
	 */
	@FXML
	void search() {
		treeView.getSelectionModel().clearSelection();
		String searchFieldTextToRead = searchTextField.getText();
		List<TreeItem<NodeUsage>> resultList = TreeViewUtilities.searchTreeItem(treeView.getRoot(),
				searchFieldTextToRead);
		treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		treeView.getSelectionModel().select(getCurrentSearchItem(resultList));
		treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());
//		System.out.println(getSearchCounter() + "/" + resultList.size());
//		for (TreeItem<NodeUsage> t : TreeViewUtilities.searchTreeItem(treeView.getRoot(), searchFieldTextToRead)) {
//			treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//			treeView.getSelectionModel().select(t);
//			treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());
//		}
//		if (treeView.getSelectionModel().getSelectedItems().size() > 1) {
//			services.eventBroker.send("EmptyPropertiesTableEvent", true);
//		}
		resultList.clear();
		TreeViewUtilities.clearSearchList();
	}

	/**
	 * 
	 * @param event
	 */
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

	/**
	 * 
	 * @param resultList
	 * @return
	 */
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

	/**
	 * 
	 * @return searchCounter
	 */
	int getSearchCounter() {
		return searchCounter;
	}

	/**
	 * 
	 * @param i setting the SearchCounter
	 */
	void setSearchCounter(int i) {
		this.searchCounter = i;
		//searchCounter = i;
	}

	/**
	 * 
	 */
	void incrementSearchCounter() {
		setSearchCounter(getSearchCounter() + 1 );
		//searchCounter++;
	}

	/**
	 * 
	 */

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
		treeView.getSelectionModel().getSelectedItem()
				.setValue(new NodeUsage("NNNN", treeView.getSelectionModel().getSelectedItem().getParent().getValue()));

	}

	/**
	 * 
	 */
	@FXML
	void addChild() {
		TreeItem<NodeUsage> treeV = new TreeItem<NodeUsage>();
		treeV.setValue(new NodeUsage("llllOOLLL"));
		treeView.getSelectionModel().getSelectedItem().getChildren().add(treeV);
	}

	/**
	 * 
	 */
	@FXML
	void copy() {
		TreeItem<NodeUsage> copiedNode = treeView.getSelectionModel().getSelectedItem();
		System.out.println(copiedNode + "sdhfg");
		System.out.println(treeView.getSelectionModel().getSelectedItem());
	}

	public TreeView<NodeUsage> getTreeView() {
		return treeView;
	}

	public void setTreeView(TreeView<NodeUsage> treeView) {
		this.treeView = treeView;
	}

	public ServiceContainer getServices() {
		return services;
	}

	public void setServices(ServiceContainer services) {
		this.services = services;
	}

	public MenuItem getProperties() {
		return properties;
	}

	public void setProperties(MenuItem properties) {
		this.properties = properties;
	}

	public VBox getBackground() {
		return background;
	}

	public void setBackground(VBox background) {
		this.background = background;
	}

	public Button getSearch() {
		return search;
	}

	public void setSearch(Button search) {
		this.search = search;
	}

	public Label getTestLabel() {
		return testLabel;
	}

	public void setTestLabel(Label testLabel) {
		this.testLabel = testLabel;
	}

	public TextField getSearchTextField() {
		return searchTextField;
	}

	public void setSearchTextField(TextField searchTextField) {
		this.searchTextField = searchTextField;
	}

	public String getCurrentSearchText() {
		return currentSearchText;
	}

	public void setCurrentSearchText(String currentSearchText) {
		this.currentSearchText = currentSearchText;
	}
	
	
}
