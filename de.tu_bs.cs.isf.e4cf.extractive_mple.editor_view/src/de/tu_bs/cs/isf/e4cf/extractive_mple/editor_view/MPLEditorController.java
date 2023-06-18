package de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
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
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.utilities.widgets.color_picker.FeatureSelectionDialog;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.FeatureDiagram;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.IFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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
			this.displayFeatures(platform.getFeatureModel().get().getAllFeatures());
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

	@Optional
	@Inject
	public void selectNextOccurrence(@UIEventTopic(MPLEEditorConsts.SELECT_NEXT) IFeature feature) {
		selector.selectNextTreeItem(feature.getArtifactUUIDs());
	}

	@Optional
	@Inject
	public void selectNextOccurrence(@UIEventTopic(MPLEEditorConsts.COLORS_CHANGED) Set<IFeature> features) {
		displayFeatures(features);
	}

	private void displayFeatures(Set<IFeature> features) {
		final Function<Node, TreeItem<Node>> coloredTreeCreator = node -> {
			ColoredTreeItem item = new ColoredTreeItem(node, Color.WHITE);
			ContextInjectionFactory.inject(item, services.context);
			for (IFeature feature : features) {
				if (feature.getArtifactUUIDs().contains(node.getUUID())) {
					item.setColor(feature.getColor().orElseGet(() -> Color.WHITE));
				}
			}
			return item;
		};
		TreeItem<Node> root = TreeViewUtilities.createTreeItems(treeView.getRoot().getValue(), coloredTreeCreator);
		TreeViewUtilities.decorateTree(root, new FamilyModelNodeDecorator());
		setTree(root);
		TreeViewUtilities.decorateTree(root, new ExpandAllDecorator());
		treeView.refresh();
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
		this.selector = new TreeItemSelector(treeView);
		nameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().toString()));
		componentCol.setCellValueFactory(p -> {
			Node node = p.getValue().getValue();
			String id = "";
			if (node.isClone()) {
				id = "Clone Node : " + node.getUUID();
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
					int space = (i + 1) / 10 * 2 + 3;
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
				selectNode(firstExpandedChild.getValue().getUUID());
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
					selectNode(nextSelection.getValue().getUUID());
				}
				keyEvent.consume();
			}
		});

		addListeners();
	}

	@FXML
	private void changeColor() {
		FeatureSelectionDialog dialog = new FeatureSelectionDialog(
				currentPlatform.getFeatureModel().get().getAllFeatures());
		IFeature selectedFeature = dialog.getSelectedFeature();

		treeView.getSelectionModel().getSelectedItems().forEach(treeItem -> {
			try {
				if (treeItem instanceof ColoredTreeItem) {
					ColoredTreeItem cti = (ColoredTreeItem) treeItem;
					for (IFeature feature : currentPlatform.getFeatureModel().get().getAllFeatures()) {
						if (feature.getColor().get().equals(cti.getColor())) {
							feature.getArtifactUUIDs().removeAll(treeItem.getValue().getUUIDsForNode());
							break;
						}
					}

					cti.setColor(selectedFeature.getColor().get());
					selectedFeature.getArtifactUUIDs().addAll(treeItem.getValue().getUUIDsForNode());
				}
			} catch (Exception e) {
			}
		});
		treeView.refresh();
	}

	@FXML
	private void changeColorsWithChildren() {
		FeatureSelectionDialog dialog = new FeatureSelectionDialog(
				currentPlatform.getFeatureModel().get().getAllFeatures());
		IFeature selectedFeature = dialog.getSelectedFeature();

		treeView.getSelectionModel().getSelectedItems().forEach(treeItem -> {
			try {
				if (treeItem instanceof ColoredTreeItem) {
					ColoredTreeItem cti = (ColoredTreeItem) treeItem;
					for (IFeature feature : currentPlatform.getFeatureModel().get().getAllFeatures()) {
						if (feature.getColor().get().equals(cti.getColor())) {
							feature.getArtifactUUIDs().removeAll(treeItem.getValue().getAllUUIDS());
							break;
						}
					}
					colorChildRecursivly(treeItem, selectedFeature.getColor().get());
					selectedFeature.getArtifactUUIDs().addAll(treeItem.getValue().getAllUUIDS());
				}
			} catch (Exception e) {
			}
		});
		treeView.refresh();
	}

	private void colorChildRecursivly(TreeItem<Node> child2, Color color) {
		if (child2 instanceof ColoredTreeItem) {
			((ColoredTreeItem) child2).setColor(color);
		}

		child2.getChildren().forEach(child -> {
			colorChildRecursivly(child, color);
		});
	}

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
		});
	}

	@FXML
	public void createFeatureModel() {
		services.eventBroker.send(FDEventTable.CREATE_FEATUREMODEL_FROM_TREEVIEW, null);
		services.partService.showPart(FDStringTable.BUNDLE_NAME);
	}
}