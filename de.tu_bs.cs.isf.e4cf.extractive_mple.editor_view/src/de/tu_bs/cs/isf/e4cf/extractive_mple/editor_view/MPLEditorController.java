package de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.extractive_mple.consts.MPLEEditorConsts;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.impl.FamilyModelNodeDecorator;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.interfaces.NodeDecorator;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.manager.DecorationManager;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.utilities.TreeViewUtilities;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
	private MenuItem properties;

	@FXML
	private Button search;

	@FXML
	private Label testLabel;

	@FXML
	private TreeTableView<Node> treeView;

	@FXML
	private TreeTableColumn<Node, String> nameCol, configCol, componentCol;

	@FXML
	private TextField searchTextField;

	@FXML
	private ContextMenu contextMenu;

	@FXML
	private ComboBox<NodeDecorator> decoratorCombo;

	private MPLPlatform currentPlatform;
	private Tree currentTree;
	private Configuration currentConfiguration;

	private List<Set<UUID>> currentAtomicSets;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		nameCol.setCellValueFactory(e -> {
			return new SimpleStringProperty(e.getValue().getValue().getNodeType());
		});

		componentCol.setCellValueFactory(e -> {
			String name = "";
			if (e.getValue().getValue().isClone()) {
				name = "Clone Node : " + e.getValue().getValue().getUUID();
			} else {
				name = e.getValue().getValue().getUUID().toString();
			}
			return new SimpleStringProperty(name);
		});

		configCol.setCellValueFactory(e -> {
			StringBuilder configString = new StringBuilder();
			for (int i = 0; i < currentPlatform.configurations.size(); i++) {
				Configuration config = currentPlatform.configurations.get(i);
				if (config.getUUIDs().contains(e.getValue().getValue().getUUID())) {
					configString.append(i + 1);
				} else {
					configString.append("  ");
				}
				configString.append(' ');
			}
			return new SimpleStringProperty(configString.toString());
		});

		treeView.setRowFactory(new Callback<TreeTableView<Node>, TreeTableRow<Node>>() {
			@Override
			public TreeTableRow<Node> call(TreeTableView<Node> param) {
				return new TreeTableRow<Node>() {
					@Override
					protected void updateItem(Node node, boolean paramBoolean) {
						super.updateItem(node, paramBoolean);
						if (node != null && currentConfiguration != null
								&& currentConfiguration.getUUIDs().contains(node.getUUID())) {
							setStyle("-fx-background-color:LIGHTGREEN;");
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
				selectSingleNode(currentItem.getParent().getValue().getUUID());
				keyEvent.consume();
			} else if (keyEvent.isShiftDown() && pressed == KeyCode.DOWN) { // jump to first expanded child
				TreeItem<Node> currentItem = treeView.getSelectionModel().getSelectedItem();
				TreeItem<Node> firstExpandedChild = currentItem.getChildren().stream()
						.filter(TreeItem::isExpanded)
						.findFirst()
						.orElseGet(() -> currentItem);
				selectSingleNode(firstExpandedChild.getValue().getUUID());
				keyEvent.consume();
			} else if (keyEvent.isControlDown() && pressed == KeyCode.UP) { // jump to previous sibling
				TreeItem<Node> currentItem = treeView.getSelectionModel().getSelectedItem();
				TreeItem<Node> parent = currentItem.getParent();
				if (parent.getChildren().size() > 1) {
					int prevIndex = parent.getChildren().indexOf(currentItem) - 1;
					// select previous sibling in parent child list, if the item isn't first in the list
					TreeItem<Node> nextSelection = parent.getChildren().get(prevIndex >= 0 ? prevIndex : 0);
					selectSingleNode(nextSelection.getValue().getUUID());
				}
				keyEvent.consume();
			} else if (keyEvent.isControlDown() && pressed == KeyCode.DOWN) { // jump to next sibling
				TreeItem<Node> currentItem = treeView.getSelectionModel().getSelectedItem();
				TreeItem<Node> parent = currentItem.getParent();
				int childCount = parent.getChildren().size();
				if (childCount > 1) {
					int nextIndex = parent.getChildren().indexOf(currentItem) + 1;
					// select next sibling in parent child list, if the item isn't last in the list
					TreeItem<Node> nextSelection = parent.getChildren().get(nextIndex < childCount ? nextIndex : childCount - 1);
					selectSingleNode(nextSelection.getValue().getUUID());
				}
				keyEvent.consume();
			}
		});
		
		addListeners();
	}

	private void decorateTreeRoot(Tree tree) {
		treeView.setRoot(new TreeItem<Node>(tree.getRoot()));
		treeView.getRoot().setGraphic(new ImageView(FileTable.rootImage));
		treeView.getRoot().setExpanded(true);
		treeView.setShowRoot(true);
	}

	/**
	 * Adds a listeners to the TreeView is displayed
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
		});
	}

	@FXML
	public void createFeatureModel() {
		services.eventBroker.send(FDEventTable.CREATE_FEATUREMODEL_FROM_TREEVIEW, currentTree);
		services.partService.showPart(FDStringTable.BUNDLE_NAME);
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
		Tree tree = new TreeImpl(platform.name, platform.model);
		setCurrentPlatform(platform);
		decorateTreeRoot(tree);
		setCurrentTree(tree);
		// load decorator and select the first
		// decoratorCombo.setItems(FXCollections.observableArrayList(decoManager.getDecoratorForTree(tree)));
		// decoratorCombo.getSelectionModel().select(0);
		TreeViewUtilities.createTreeView(tree.getRoot(), treeView.getRoot(), new FamilyModelNodeDecorator());
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
	
	@Optional
	@Inject
	public void showAtomicSets(@UIEventTopic("atomic_sets_found") List<Set<UUID>> atomicSets) {
		currentAtomicSets = atomicSets;
		
		treeView.setRowFactory(new Callback<TreeTableView<Node>, TreeTableRow<Node>>() {
			@Override
			public TreeTableRow<Node> call(TreeTableView<Node> param) {
				return new TreeTableRow<Node>() {
					@Override
					protected void updateItem(Node node, boolean paramBoolean) {
						super.updateItem(node, paramBoolean);						
						if (node != null && currentAtomicSets != null) {
							for (int i = 0; i < currentAtomicSets.size(); i++) {
								if (atomicSets.get(i).contains(node.getUUID())) {
									int hue = 360 / currentAtomicSets.size() * i;
									String color = String.format("hsb(%d, %s, %s)", hue, "100%", "50%");
									setStyle("-fx-background-color:" + color + ";");
								}
							}
							
						}
					}
				};
			}
		});
		treeView.refresh();
		
		TreeViewUtilities.decorateTree(treeView.getRoot(), new NodeDecorator() {
			@Override
			public boolean isSupportedTree(Tree tree) {
				return true;
			}

			@Override
			public TreeItem<Node> decorateNode(TreeItem<Node> node) {
				node.setExpanded(true);
				return node;
			}
			
		});
		treeView.refresh();
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
}
