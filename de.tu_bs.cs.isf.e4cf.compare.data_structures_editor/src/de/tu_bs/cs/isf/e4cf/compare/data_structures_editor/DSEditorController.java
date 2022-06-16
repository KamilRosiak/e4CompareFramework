package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import com.google.common.io.Files;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.AttributeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.gson.GsonExportService;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.util.ArtifactIOUtil;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.interfaces.NodeDecorator;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.CommandStack;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.DecorationManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions.AddAttributeAction;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions.AddChildNodeAction;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions.DeleteNodeAction;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions.NodeAttributePair;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.utilities.TreeViewUtilities;
import de.tu_bs.cs.isf.e4cf.compare.stringtable.CompareST;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.components.Directory;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.file.FileStreamUtil;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.extraction.ClusterEngine;
import de.tu_bs.cs.isf.e4cf.refactoring.data_structures.model.CloneModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;

/**
 * This class represents the controller implementation of the data structure
 * editor model view controller implementation. The corresponding view is
 * located at {@link /ui/view/VisualizeTreeView.fxml}.
 * 
 * @author Team05 , Kamil Rosiak
 *
 */
public class DSEditorController {

	private CloneModel cloneModel;
	
	@Inject
	private ClusterEngine clusterEngine;
	
	@Inject
	private GsonExportService exportService;

	@Inject
	private ServiceContainer services;

	@Inject
	DecorationManager decoManager;	

	@FXML
	private MenuItem properties;

	@FXML
	private Button search;

	@FXML
	private Label testLabel;

	@FXML
	private TreeView<Node> treeView;

	@FXML
	private TextField searchTextField;

	@FXML
	private ContextMenu contextMenu;

	@FXML
	private ComboBox<NodeDecorator> decoratorCombo;

	private String currentSearchText;
	private Tree currentTree;
	private int searchCounter;

	private List<TreeItem<Node>> copyList = new ArrayList<TreeItem<Node>>();

	private CommandStack commandStack = new CommandStack();

	public Tree getCurrentTree() {
		return currentTree;
	}

	public void setCurrentTree(Tree currentTree) {
		this.currentTree = currentTree;
	}

	/**
	 * Method to initialize the treeView from a given Tree
	 * 
	 * @param tree
	 */
	@Optional
	@Inject
	public void showTree(@UIEventTopic(DSEditorST.INITIALIZE_TREE_EVENT) Tree tree) {
		cloneModel = null;
		treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		services.partService.showPart(DSEditorST.TREE_VIEW_ID);
		closeFile();
		createTreeRoot(tree);
		setCurrentTree(tree);
		// load decorator and select the first
		decoratorCombo.setItems(FXCollections.observableArrayList(decoManager.getDecoratorForTree(tree)));
		decoratorCombo.getSelectionModel().select(0);

		TreeViewUtilities.createTreeView(tree.getRoot(), treeView.getRoot(), getSelectedDecorator());

		addListener();
	}

	/**
	 * Method to initialize the treeView from a given Tree and a clone model.
	 * 
	 * @param tree
	 */
	@Optional
	@Inject
	public void showTree(@UIEventTopic(DSEditorST.INITIALIZE_TREE_EVENT) Map<String, Object> event) {
		Tree tree = (Tree) event.get(DSEditorST.TREE);
		showTree(tree);
		this.cloneModel = (CloneModel) event.get(DSEditorST.CLONE_MODEL);
	}
	
	private void refreshCloneModel() {
		CloneModel savedModel = cloneModel;		
		showTree(savedModel.getTree());		
		this.cloneModel = savedModel;
	}
	

	private NodeDecorator getSelectedDecorator() {
		return decoratorCombo.getSelectionModel().getSelectedItem();
	}

	private void createTreeRoot(Tree tree) {
		treeView.setRoot(new TreeItem<Node>(tree.getRoot()));
		treeView.getRoot().setGraphic(new ImageView(FileTable.rootImage));
		treeView.getRoot().setExpanded(true);
		treeView.setShowRoot(true);
	}

	private void setContextMenu() {
		treeView.setContextMenu(contextMenu);
		treeView.setOnMouseEntered(event -> contextMenu.hide());
	}

	/**
	 * Method to add a new Attribute
	 * 
	 * @param attributeName
	 * @param attributeValue
	 */
	private void addAttribute(String attributeName, String attributeValue) {
		treeView.getSelectionModel().getSelectedItem().getValue().addAttribute(attributeName,
				new StringValueImpl(attributeValue));
		treeView.refresh();
	}

	/**
	 * search the text given in the searchfield
	 */
	void searchForNode() {
		List<TreeItem<Node>> searchList = new ArrayList<TreeItem<Node>>();
		treeView.getSelectionModel().clearSelection();

		List<TreeItem<Node>> resultList = TreeViewUtilities.searchTreeItem(treeView.getRoot(),
				searchTextField.getText().toLowerCase(), searchList);
		if (searchCounter >= resultList.size()) {
			searchCounter = 0;
		}
		treeView.getSelectionModel().select(resultList.get(searchCounter));
		treeView.scrollTo(treeView.getSelectionModel().getSelectedIndex());
		resultList.clear();
	}

	/**
	 * fills a list with all items currently in the treeView
	 * 
	 * @param item
	 * @param treeViewList
	 * @return list with treeItems
	 */
	public List<TreeItem<Node>> treeViewToList(TreeItem<Node> item, List<TreeItem<Node>> treeViewList) {
		if (item.getValue().isRoot()) {
			treeViewList.add(item);
		}
		for (TreeItem<Node> ti : item.getChildren()) {
			treeViewList.add(ti);
			if (!ti.isLeaf()) {
				treeViewToList(ti, treeViewList);
			}
		}
		return treeViewList;
	}

	private TreeItem<Node> getCurrentSelection() {
		return treeView.getSelectionModel().getSelectedItem();
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
				services.partService.showPart(DSEditorST.PROPERTIES_VIEW_ID);
				if (cloneModel != null) {
					Map<String, Object> event = new HashMap<String, Object>();
					event.put(DSEditorST.NODE, treeView.getSelectionModel().getSelectedItem().getValue());
					event.put(DSEditorST.CLONE_MODEL, cloneModel);

					services.eventBroker.send(DSEditorST.NODE_PROPERTIES_EVENT, event);
				} else {
					services.eventBroker.send(DSEditorST.NODE_PROPERTIES_EVENT,
							treeView.getSelectionModel().getSelectedItem().getValue());
				}

			}
		});

		decoratorCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null)
				return;
			decoManager.setCurrentDecorater(newValue);
			TreeViewUtilities.decorateTree(treeView.getRoot(), decoManager.getCurrentDecorater());
			services.eventBroker.send(DSEditorST.REFRESH_TREEVIEW_EVENT, true);
		});
	}

	/**
	 * Method to add an Attribute to a Node
	 */
	@FXML
	void addNodeAttribute() {

		if (cloneModel != null) {
			String attrName = RCPMessageProvider.inputDialog("Attribute Name", "Enter Attribute Name");
			String attrValue = RCPMessageProvider.inputDialog("Attribute Value", "Enter Attribute Value");
			Attribute addedAttr = new AttributeImpl(attrName, new StringValueImpl(attrValue));		
			
			cloneModel.addAttribute(getCurrentSelection().getValue(), addedAttr);
			clusterEngine.analyzeCloneModel(cloneModel);
			refreshCloneModel();

		} else {
			AddAttributeAction action = new AddAttributeAction(getCurrentSelection().getValue());
			commandStack.execute(action);

		}

		treeView.refresh();
	}

	/**
	 * Method to add a Child to a Node
	 */
	@FXML
	void addChildNode() {

		Node selectedNode = treeView.getSelectionModel().getSelectedItem().getValue();

		if (cloneModel != null) {
			Node newNode = new NodeImpl(RCPMessageProvider.inputDialog("Create New Child", "Node Type"));
						
			cloneModel.addChild(selectedNode, newNode, 0);
			clusterEngine.analyzeCloneModel(cloneModel);
			refreshCloneModel();
			
		} else {
			TreeItem<Node> selectedItem = treeView.getSelectionModel().getSelectedItem();
			AddChildNodeAction action = new AddChildNodeAction(treeView, selectedItem, getSelectedDecorator());
			commandStack.execute(action);

		}

		treeView.refresh();
	}

	@FXML
	public void restoreTrees() {

		if (cloneModel != null) {
			try {
				
				List<Tree> trees = cloneModel.restoreIntegratedTrees();

				Directory directory = services.workspaceFileSystem.getWorkspaceDirectory();
				boolean createNewDirectory = true;

				File baseDirectory = null;
				for (FileTreeElement subElement : directory.getChildren()) {

					if (subElement.getFileName().equals("Restored trees")) {
						createNewDirectory = false;
						baseDirectory = new File(subElement.getAbsolutePath());
						break;
					}
				}

				if (createNewDirectory) {
					baseDirectory = new File(directory.getAbsolutePath() + "/Restored trees");
					baseDirectory.mkdir();

				}

				int count = 0;
				for (File subElement : baseDirectory.listFiles()) {
					if (subElement.getName().equals("Result_" + count)) {
						count++;
					}

				}

				File resultDirectory = new File(baseDirectory.getAbsolutePath() + "/" + "Result_" + count);
				resultDirectory.mkdir();

				for (Tree tree : trees) {

					String json = exportService.exportTree((TreeImpl) tree);

					File concreteElement = new File(resultDirectory.getAbsolutePath() + "/" + tree.getTreeName() + ".tree");
					concreteElement.createNewFile();										
					
					Files.write(json.getBytes(), new File(concreteElement.getAbsolutePath()));

				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Method to copy the selected Items
	 */
	@FXML
	void copyNode() {
		copyList.clear();
		for (TreeItem<Node> ti : treeView.getSelectionModel().getSelectedItems()) {
			copyList.add(ti);
		}
	}

	/**
	 * Method to paste the copied Items
	 */
	@FXML
	void pasteNode() {
		try {
			for (TreeItem<Node> ti : copyList) {
				TreeItem<Node> tempNode = TreeViewUtilities.createTreeItem(ti.getValue(), getSelectedDecorator());
				// Idee: Index verwalten über expand/collapse All
				treeView.getSelectionModel().getSelectedItem().getChildren().add(tempNode);
				for (TreeItem<Node> child : ti.getChildren()) {
					tempNode.getChildren()
							.add(TreeViewUtilities.createTreeItem(child.getValue(), getSelectedDecorator()));
				}
			}
		} catch (NullPointerException e) {
			return;
		}
	}

	/**
	 * Method to rename a Node
	 */
	@FXML
	void renameNode() {

		if (treeView.getSelectionModel().getSelectedItems().size() > 1) {
			TreeViewUtilities
					.informationAlert("Multiple items selected. Rename can only be applied to one item at a time");
			return;
		} else {
			String prevName = treeView.getSelectionModel().getSelectedItem().getValue().getAttributeForKey("name")
					.getAttributeValues().toString();
			prevName = prevName.substring(1, prevName.length() - 1);

			String newName = TreeViewUtilities.getInput("Enter new name");
			if (newName != null) {
				for (Attribute attribute : treeView.getSelectionModel().getSelectedItem().getValue().getAttributes()) {
					if (attribute.getAttributeKey().toLowerCase().equals("name")) {
						attribute.getAttributeValues().clear();
					}
				}
				// commandStack.execute(new ChangeNodeTypeAction("renameNode", prevName,
				// treeView,
				// treeView.getSelectionModel().getSelectedItem().getValue()));
				addAttribute("name", newName);
				treeView.refresh();
				services.eventBroker.send(DSEditorST.NODE_PROPERTIES_EVENT,
						treeView.getSelectionModel().getSelectedItem().getValue());
			}
		}
	}

	/**
	 * Method to cut
	 */
	@FXML
	void cutNode() {
		copyNode();
		deleteNode();
	}

	/**
	 * Method to extract the selected Items into a new File
	 */
	@FXML
	void extractToFile() {

	}

	/**
	 * give function to the FXML Element delete
	 */
	@FXML
	void deleteNodeFXML() {
		if (treeView.getSelectionModel().getSelectedItems().size() > 1) {
			TreeViewUtilities.informationAlert("Attention! Multiple Items selected. Cancelling now.");
			unselectAllNodes();
			return;
		} else if (TreeViewUtilities.confirmationAlert("Are you sure you want to do this?") == true) {
			deleteNode();
		}
	}

	/**
	 * Method to delete a Node
	 */
	private void deleteNode() {

		TreeItem<Node> selectedItem = treeView.getSelectionModel().getSelectedItem();
		Node nodeToDelete = selectedItem.getValue();

		if (cloneModel != null) {				
			
			cloneModel.delete(nodeToDelete);
			clusterEngine.analyzeCloneModel(cloneModel);
			refreshCloneModel();
			
		} else {
			commandStack.execute(new DeleteNodeAction("deleteNode", selectedItem, selectedItem.getParent()));

		}

		unselectAllNodes();
		treeView.refresh();
	}

	/**
	 * set treeview and its values to null, then remove it from the background
	 */
	@FXML
	void closeFile() {
		treeView.setRoot(null);
		searchCounter = 0;
		currentSearchText = null;
		services.eventBroker.send(DSEditorST.EMPTY_PROPERTIES_TABLE_EVENT, true);
	}

	/**
	 * Method to save the treeView
	 */
	@FXML
	void saveFile() {
		String fileName = RCPMessageProvider.inputDialog("Filename", "Enter a File Name");
		try {

			String path = RCPContentProvider.getCurrentWorkspacePath() + CompareST.FAMILY_MODELS + "/" + fileName
					+ ".tree";
			String gson = exportService.exportTree((TreeImpl) currentTree);
			FileStreamUtil.writeTextToFile(path, gson);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return the name of the current tree if a tree is available else an empty
	 * string.
	 */
	private String getTreeName() {
		return currentTree != null ? getCurrentTree().getTreeName() : "";
	}

	/**
	 * Method to save the treeView as a new file type
	 */
	@FXML
	void saveAs() {
		ArtifactIOUtil.writeArtifactToFile(currentTree,
				RCPMessageProvider.getFilePathDialog("Select Path", RCPContentProvider.getCurrentWorkspacePath()));
	}

	/**
	 * Undoes the last action
	 */
	@FXML
	void undoAction() {
		commandStack.undo();
		treeView.refresh();
	}

	/**
	 * Selects all elements in the treeview
	 */
	@FXML
	void selectAllNodes() {
		treeView.getSelectionModel().selectAll();
		services.eventBroker.send(DSEditorST.EMPTY_PROPERTIES_TABLE_EVENT, true);
	}

	/**
	 * Unselects all elements in the treeview
	 */
	@FXML
	void unselectAllNodes() {
		treeView.getSelectionModel().clearSelection();
		services.eventBroker.send(DSEditorST.EMPTY_PROPERTIES_TABLE_EVENT, true);
	}

	/**
	 * is called when the user presses the search button
	 */
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
	 * expands all items
	 */
	@FXML
	public void expandAllItems() {
		for (TreeItem<Node> ti : treeViewToList(treeView.getRoot(), new ArrayList<TreeItem<Node>>())) {
			ti.setExpanded(true);
		}
	}

	/**
	 * collapses all items
	 */
	@FXML
	public void collapseAllItems() {
		for (TreeItem<Node> ti : treeViewToList(treeView.getRoot(), new ArrayList<TreeItem<Node>>())) {
			ti.setExpanded(false);
		}
	}

	/**
	 * collapses all selected items
	 */
	@FXML
	public void collapseSelectedItems() {
		for (TreeItem<Node> ti : treeView.getSelectionModel().getSelectedItems()) {
			ti.setExpanded(false);
		}
	}

	/**
	 * expands all selected items
	 */
	@FXML
	public void expandSelectedItems() {
		// tempList necessary because we wanted to select all children, too
		List<TreeItem<Node>> tempList = new ArrayList<TreeItem<Node>>();
		for (TreeItem<Node> ti : treeView.getSelectionModel().getSelectedItems()) {
			tempList.addAll(TreeViewUtilities.getSubTreeAsList(ti, new ArrayList<TreeItem<Node>>()));
		}
		for (TreeItem<Node> ti : tempList) {
			ti.setExpanded(true);
			treeView.getSelectionModel().select(ti);
		}
	}
	
	@FXML
	public void createFeatureModel() {
		services.eventBroker.send(FDEventTable.CREATE_FEATUREMODEL_FROM_TREEVIEW, currentTree);
		services.partService.showPart(FDStringTable.BUNDLE_NAME);
	}

	/**
	 * Event for refreshing the treeView
	 * 
	 * @param bool
	 */
	@Optional
	@Inject
	public void refreshTreeView(@UIEventTopic(DSEditorST.REFRESH_TREEVIEW_EVENT) boolean bool) {
		treeView.refresh();
	}

	/**
	 * Event to remove attributes
	 * 
	 * @param attribute
	 */
	@Optional
	@Inject
	public void removeNodeAttributeFromSelectedItem(
			@UIEventTopic(DSEditorST.DELETE_ATTRIBUTE_EVENT) Attribute attribute) {
		treeView.getSelectionModel().getSelectedItem().getValue().getAttributes().remove(attribute);
	}

	/**
	 * 
	 * @param bool
	 */
	@Optional
	@Inject
	public void reopenItem(@UIEventTopic(DSEditorST.REOPEN_ITEM_EVENT) boolean bool) {
		services.eventBroker.send(DSEditorST.EMPTY_PROPERTIES_TABLE_EVENT, true);
		services.eventBroker.send(DSEditorST.NODE_PROPERTIES_EVENT,
				treeView.getSelectionModel().getSelectedItem().getValue());
	}

	/**
	 * Event to add an attribute again after removal
	 * 
	 * @param pair
	 */
	@Optional
	@Inject
	private void addAttribute(@UIEventTopic(DSEditorST.ADD_ATTRIBUTE_EVENT) NodeAttributePair pair) {
		pair.getOwner().getAttributes().add(pair.getAttribute());

		treeView.refresh();
	}

	/**
	 * Event to get the selected items value
	 * 
	 * @param bool
	 */
	@Optional
	@Inject
	public void askForSelectedItem(@UIEventTopic(DSEditorST.ASK_FOR_SELECTED_ITEM_EVENT) boolean bool) {
		services.eventBroker.send(DSEditorST.RECEIVE_SELECTED_NODE_EVENT,
				treeView.getSelectionModel().getSelectedItem().getValue());
	}

}
