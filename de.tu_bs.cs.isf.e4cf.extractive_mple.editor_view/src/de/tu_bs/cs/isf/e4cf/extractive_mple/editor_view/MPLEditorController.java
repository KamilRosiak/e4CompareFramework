package de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.util.TreeUtil;
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
	private UUID currentSelectedNode;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		nameCol.setCellValueFactory(e -> {
			return new SimpleStringProperty(e.getValue().getValue().getNodeType());
		});

		componentCol.setCellValueFactory(e -> {
			String name = "";
			if (e.getValue().getValue().isClone()) {
				name = "component " + e.getValue().getValue().getUUID();
			} else {
				name = "UUID: " + e.getValue().getValue().getUUID();
			}
			return new SimpleStringProperty(name);
		});

		configCol.setCellValueFactory(e -> {
			String configString = "";
			for (Configuration config : currentPlatform.configurations) {
				if (config.getUUIDs().contains(e.getValue().getValue().getUUID())) {
					configString = configString + config.getName() + " ";
				}

			}

			return new SimpleStringProperty(configString);
		});

	}

	private NodeDecorator getSelectedDecorator() {
		return new FamilyModelNodeDecorator();
	}

	private void decorateTreeRoot(Tree tree) {
		treeView.setRoot(new TreeItem<Node>(tree.getRoot()));
		treeView.getRoot().setGraphic(new ImageView(FileTable.rootImage));
		treeView.getRoot().setExpanded(true);
		treeView.setShowRoot(true);

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
						} else if (node != null && node.getUUID().equals(currentSelectedNode)) {
							setStyle("-fx-background-color:LIGHTGREEN;");
						} else {
							setStyle(null);
						}
					}
				};
			}
		});

	}

	/**
	 * Adds a listener to the TreeView so that PropertiesTable of a highlighted node
	 * is displayed
	 * 
	 */
	public void addListener() {
		treeView.getSelectionModel().selectedItemProperty().addListener(e -> {
			if (treeView.getSelectionModel().getSelectedIndices().size() == 1) {
				services.partService.showPart(MPLEEditorConsts.PROPERTIES_VIEW_ID);

				services.eventBroker.send(MPLEEditorConsts.NODE_PROPERTIES_EVENT,
						treeView.getSelectionModel().getSelectedItem().getValue());
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
		addListener();
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

	/**
	 * Method to initialize the treeView from a given Tree
	 * 
	 * @param platform
	 */
	@Optional
	@Inject
	public void selectNode(@UIEventTopic(MPLEEditorConsts.SHOW_UUID) UUID uuid) {
		currentSelectedNode = uuid;
		treeView.refresh();
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
