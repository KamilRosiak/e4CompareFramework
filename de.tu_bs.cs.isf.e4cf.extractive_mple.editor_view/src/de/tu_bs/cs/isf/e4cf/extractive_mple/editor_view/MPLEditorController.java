package de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.extractive_mple.consts.MPLEEditorConsts;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.impl.ColoredTreeItem;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.impl.ExpandAllDecorator;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.impl.FamilyModelNodeDecorator;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.impl.StylableTreeItem;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.interfaces.NodeDecorator;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.manager.DecorationManager;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.utilities.TreeItemSelector;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.utilities.TreeViewUtilities;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.FeatureDiagram;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.IFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
<<<<<<< HEAD
=======
import javafx.event.ActionEvent;
>>>>>>> refs/heads/master_merg
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Callback;

/**
 * This class represents the controller implementation of the data structure
 * editor model view controller implementation. The corresponding view is
 * located at {@link /ui/view/VisualizeTreeView.fxml}.
 * 
 * @author Team05 , Kamil Rosiak
 *
 */
public class MPLEditorController implements Initializable {
	@Inject
	private ServiceContainer services;
	@Inject
	DecorationManager decoManager;

	@FXML
	private TreeTableView<Node> treeView;
	@FXML
	private TreeTableColumn<Node, String> nameCol, configCol, componentCol;
	@FXML
	private ComboBox<NodeDecorator> decoratorCombo;

	private MPLPlatform currentPlatform;
	private Configuration currentConfiguration;
	private TreeItemSelector selector;
	
	@FXML
	public void fxMoveArtifacts(ActionEvent e) {
		List<TreeItem<Node>> selectedNodes = this.treeView.getSelectionModel().getSelectedItems();
		System.out.println(selectedNodes.size());
	}

	/**
	 * Method to initialize the treeView from a given Tree
	 * 
	 * @param platform
	 */
	@Optional
	@Inject
	public void showMPL(@UIEventTopic(MPLEEditorConsts.SHOW_MPL) MPLPlatform platform) {		
		treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		services.partService.showPart(MPLEEditorConsts.TREE_VIEW_ID);
		this.setCurrentPlatform(platform);
		// load decorator and select the first
		decoratorCombo.setItems(FXCollections.observableArrayList(decoManager.getDecoraterFromExtension()));
		decoratorCombo.getSelectionModel().select(0);
		
		if (platform.getFeatureModel() != null && platform.getFeatureModel().isPresent()) {
			this.displayFeatures(platform.getFeatureModel().get());
		}

	}

	/**
	 * Method to initialize the treeView from a given Tree
	 * 
	 * @param platform
	 */
	@Optional
	@Inject
	public void showTree(@UIEventTopic(MPLEEditorConsts.SHOW_TREE) Tree tree) {
		try {
			MPLPlatform platform = new MPLPlatform();
			platform.model = tree.getRoot();
			showMPL(platform);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	
	
	
	private void displayFeatures(FeatureDiagram diagram) {
		Set<IFeature> features = diagram.getAllFeatures();
		final Function<Node, TreeItem<Node>> coloredTreeCreator = node -> {
			ColoredTreeItem item = new ColoredTreeItem(node, Color.WHITE);
			for (IFeature feature : features) {
				if (feature.getArtifactUUIDs().contains(node.getUUID())) {
					item.setColor(feature.getColor().orElseGet(() -> Color.WHITE));
				}
			}
			return item;
		};

		TreeItem<Node> root = TreeViewUtilities.createTreeItems(treeView.getRoot().getValue(), coloredTreeCreator);
		TreeViewUtilities.decorateTree(root, new FamilyModelNodeDecorator());
		this.setTree(root);
		TreeViewUtilities.decorateTree(root, new ExpandAllDecorator());
	}

	@Optional
	@Inject
	public void selectNode(@UIEventTopic(MPLEEditorConsts.SHOW_UUID) UUID uuid) {
		selector.selectNode(uuid);
	}

	@Optional
	@Inject
	public void selectCloneNodeByParent(@UIEventTopic(MPLEEditorConsts.SHOW_CLONE_UUID) String parentToChildUuid) {
		selector.selectCloneNodeByParent(parentToChildUuid);
	}

	public MPLPlatform getCurrentPlatform() {
		return currentPlatform;
	}

	private void setCurrentPlatform(MPLPlatform platform) {
		this.currentPlatform = platform;
		TreeItem<Node> root = TreeViewUtilities.createTreeItems(platform.model);
		TreeViewUtilities.decorateTree(root, new FamilyModelNodeDecorator());
		setTree(root);
		treeView.refresh();
	}

	private void setTree(TreeItem<Node> root) {
		treeView.setRoot(root);
		treeView.getRoot().setGraphic(new ImageView(FileTable.rootImage));
		treeView.getRoot().setExpanded(true);
		treeView.setShowRoot(true);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
<<<<<<< HEAD

		nameCol.setCellValueFactory(treeItem -> {
			return new SimpleStringProperty(treeItem.getValue().getValue().toString());
		});

		componentCol.setCellValueFactory(e -> {
			String name = "";
			if (e.getValue().getValue().isClone()) {
				name = "Clone Node : " + e.getValue().getValue().getUUID();
=======
		this.selector = new TreeItemSelector(treeView);
		nameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
		componentCol.setCellValueFactory(p -> {
			Node node = p.getValue().getValue();
			String id = "";
			if (node.isClone()) {
				id = "Clone Node : " + node.getUUID();
>>>>>>> refs/heads/master_merg
			} else {
				id = node.getUUID().toString();
			}
			return new SimpleStringProperty(id);
		});
		configCol.setCellValueFactory(p -> {
			StringBuilder configString = new StringBuilder();
			for (int i = 0; i < currentPlatform.configurations.size(); i++) {
				Configuration config = currentPlatform.configurations.get(i);
				if (config.getUUIDs().contains(p.getValue().getValue().getUUID())) {
					configString.append((i + 1) + " ");
				} else {
					int space = (i+1)/10 * 2 + 3;
					for (int j = 0; j < space; j++) {
						configString.append(" ");
					}
				}
			}
			return new SimpleStringProperty(configString.toString());
		});

		treeView.setRowFactory(new Callback<TreeTableView<Node>, TreeTableRow<Node>>() {
			@Override
			public TreeTableRow<Node> call(TreeTableView<Node> view) {
				return new TreeTableRow<Node>() {
					@Override
					protected void updateItem(Node model, boolean empty) {
						super.updateItem(model, empty);

						if (empty || model == null) {
							setText(null);
							setGraphic(null);
						} else {
							if (currentConfiguration != null
									&& currentConfiguration.getUUIDs().contains(model.getUUID())) {
								setStyle("-fx-background-color:LIGHTGREEN;");
							}

							TreeItem<Node> treeItem = this.getTreeItem();
							if (treeItem instanceof StylableTreeItem) {
								StylableTreeItem styleTreeItem = (StylableTreeItem) treeItem;
								styleTreeItem.style(this);
							}

						}
					}
				};
			}
		});

		// keyboard shortcuts for keyboard only tree-navigation
		treeView.setOnKeyPressed(keyEvent -> {
			KeyCode pressed = keyEvent.getCode();
			if (pressed == KeyCode.SPACE) { // toggle current tree item expanded
				TreeItem<Node> currentItem = treeView.getSelectionModel().getSelectedItem();
				currentItem.setExpanded(!currentItem.isExpanded());
				keyEvent.consume();
			} else if (keyEvent.isShiftDown() && pressed == KeyCode.UP) { // jump to parent
				TreeItem<Node> currentItem = treeView.getSelectionModel().getSelectedItem();
				selectNode(currentItem.getParent().getValue().getUUID());
				keyEvent.consume();
			} else if (keyEvent.isShiftDown() && pressed == KeyCode.DOWN) { // jump to first expanded child
				TreeItem<Node> currentItem = treeView.getSelectionModel().getSelectedItem();
				TreeItem<Node> firstExpandedChild = currentItem.getChildren().stream().filter(TreeItem::isExpanded)
						.findFirst().orElseGet(() -> currentItem);
<<<<<<< HEAD
				selectSingleNode(firstExpandedChild.getValue().getUUID());
=======
				selectNode(firstExpandedChild.getValue().getUUID());
>>>>>>> refs/heads/master_merg
				keyEvent.consume();
			} else if (keyEvent.isControlDown() && pressed == KeyCode.UP) { // jump to previous sibling
				TreeItem<Node> currentItem = treeView.getSelectionModel().getSelectedItem();
				TreeItem<Node> parent = currentItem.getParent();
				if (parent.getChildren().size() > 1) {
					int prevIndex = parent.getChildren().indexOf(currentItem) - 1;
					// select previous sibling in parent child list, if the item isn't first in the
					// list
					TreeItem<Node> nextSelection = parent.getChildren().get(prevIndex >= 0 ? prevIndex : 0);
					selectNode(nextSelection.getValue().getUUID());
				}
				keyEvent.consume();
			} else if (keyEvent.isControlDown() && pressed == KeyCode.DOWN) { // jump to next sibling
				TreeItem<Node> currentItem = treeView.getSelectionModel().getSelectedItem();
				TreeItem<Node> parent = currentItem.getParent();
				int childCount = parent.getChildren().size();
				if (childCount > 1) {
					int nextIndex = parent.getChildren().indexOf(currentItem) + 1;
					// select next sibling in parent child list, if the item isn't last in the list
					TreeItem<Node> nextSelection = parent.getChildren()
							.get(nextIndex < childCount ? nextIndex : childCount - 1);
<<<<<<< HEAD
					selectSingleNode(nextSelection.getValue().getUUID());
=======
					selectNode(nextSelection.getValue().getUUID());
>>>>>>> refs/heads/master_merg
				}
				keyEvent.consume();
			}
		});

		addListeners();
	}

<<<<<<< HEAD
	private void decorateTreeRoot(Tree tree) {
		treeView.setRoot(new TreeItem<Node>(tree.getRoot(), new ImageView(FileTable.rootImage)));
		treeView.getRoot().setExpanded(true);
		treeView.setShowRoot(true);
	}

=======
>>>>>>> refs/heads/master_merg
	/**
	 * Adds a listeners to the TreeView
	 * 
	 */
	private void addListeners() {
		// display attributes of current tree item in properties view
		treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldVal, newVal) -> {
			if (newVal != null) {
				services.eventBroker.send(MPLEEditorConsts.NODE_PROPERTIES_EVENT, newVal.getValue());
			}
		});

		decoratorCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null)
				return;
			decoManager.setCurrentDecorater(newValue);
			TreeViewUtilities.decorateTree(treeView.getRoot(), decoManager.getCurrentDecorater());
			services.eventBroker.send(MPLEEditorConsts.REFRESH_TREEVIEW_EVENT, true);
			treeView.refresh();
		});
	}

	@FXML
	public void createFeatureModel() {
		services.eventBroker.send(FDEventTable.CREATE_FEATUREMODEL_FROM_TREEVIEW, null);
		services.partService.showPart(FDStringTable.BUNDLE_NAME);
	}
<<<<<<< HEAD

	/**
	 * Method to initialize the treeView from a given Tree
	 * 
	 * @param platform
	 */
	@Optional
	@Inject
	public void showMPL(@UIEventTopic(MPLEEditorConsts.SHOW_MPL) MPLPlatform platform) {
		try {
			treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			services.partService.showPart(MPLEEditorConsts.TREE_VIEW_ID);
			Tree tree = platform.getModel();
			setCurrentPlatform(platform);
			setCurrentTree(tree);
			decorateTreeRoot(tree);
			// load decorator and select the first
			decoratorCombo.setItems(FXCollections.observableArrayList(decoManager.getDecoratorForTree(tree)));
			decoratorCombo.getSelectionModel().select(0);
			TreeViewUtilities.createTreeView(tree.getRoot(), treeView.getRoot(), new FamilyModelNodeDecorator());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to initialize the treeView from a given Tree
	 * 
	 * @param platform
	 */
	@Optional
	@Inject
	public void showTree(@UIEventTopic(MPLEEditorConsts.SHOW_TREE) Tree tree) {
		System.out.println(tree.getTreeName());
		try {
			MPLPlatform platform = new MPLPlatform();
			platform.setModel(tree);
			showMPL(platform);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Finds and selects a node in the tree by its UUID
	 * 
	 * @param uuid The UUID of the desired node
	 */
	@Optional
	@Inject
	public void selectSingleNode(@UIEventTopic(MPLEEditorConsts.SHOW_UUID) UUID uuid) {
		if (uuid == null) {
			return;
		}

		List<TreeItem<Node>> itemPath = TreeViewUtilities.findFirstItemPath(treeView.getRoot(), uuid.toString());
		selectTreeItem(itemPath);
	}

	/**
	 * Selects a clone node under a parent node in the tree view.
	 * 
	 * @param parentToChildUuid The parentUuid followed by the childUuid separated
	 *                          by a '#' e.g. 4564-4021#8932-4893
	 */
	@Optional
	@Inject
	public void selectCloneNodeByParent(@UIEventTopic(MPLEEditorConsts.SHOW_CLONE_UUID) String parentToChildUuid) {
		String[] splitUuid = parentToChildUuid.split("#");
		String parentUuid = splitUuid[0];
		String childUuid = splitUuid[1];

		List<TreeItem<Node>> parentPath = TreeViewUtilities.findFirstItemPath(treeView.getRoot(), parentUuid);
		if (parentPath.size() > 0) {
			TreeItem<Node> parent = parentPath.get(parentPath.size() - 1);
			List<TreeItem<Node>> parentToChildPath = TreeViewUtilities.findFirstItemPath(parent, childUuid);
			List<TreeItem<Node>> childPath = new LinkedList<>(parentPath);
			childPath.add(parentToChildPath.get(parentToChildPath.size() - 1));

			selectTreeItem(childPath);
		}
	}

	/**
	 * Selects a tree item at the end of a path of tree items in the tree view. The
	 * tree item path has to start at the root item.
	 * 
	 * @param itemPath Sequence of tree items from the root to the item to select
	 */
	private void selectTreeItem(List<TreeItem<Node>> itemPath) {
		if (itemPath.size() > 0) {
			treeView.getSelectionModel().clearSelection();
			TreeItem<Node> node = itemPath.get(itemPath.size() - 1);
			for (TreeItem<Node> item : itemPath) {
				item.setExpanded(true);
			}
			treeView.getSelectionModel().select(node);
			treeView.refresh();
			int nodeIndex = treeView.getRow(node);
			treeView.scrollTo(nodeIndex - 3);
		}
	}

	/**
	 * Method to initialize the treeView from a given Tree
	 * 
	 * @param platform
	 */
	@Optional
	@Inject
	public void searchInTree(@UIEventTopic(MPLEEditorConsts.SEARCH_UUID) UUID uuid) {
		openTree(uuid);
	}

	public void openTree(UUID uuid) {
		searchInTree(uuid, treeView.getRoot());
	}

	public void searchInTree(UUID uuid, TreeItem<Node> treeItem) {
		if (treeItem.getValue().getUUID().equals(uuid)) {
			TreeItem<Node> currentItem = treeItem;
			while (currentItem != treeView.getRoot()) {
				currentItem.setExpanded(true);
				currentItem = currentItem.getParent();
			}
		} else {
			treeItem.getChildren().forEach(childNode -> {
				searchInTree(uuid, childNode);
			});
		}
	}

	public MPLPlatform getCurrentPlatform() {
		return currentPlatform;
	}

	public void setCurrentPlatform(MPLPlatform currentPlatform) {
		this.currentPlatform = currentPlatform;
	}

	public Tree getCurrentTree() {
		return currentTree;
	}

	public void setCurrentTree(Tree currentTree) {
		this.currentTree = currentTree;
	}
=======
>>>>>>> refs/heads/master_merg
}
