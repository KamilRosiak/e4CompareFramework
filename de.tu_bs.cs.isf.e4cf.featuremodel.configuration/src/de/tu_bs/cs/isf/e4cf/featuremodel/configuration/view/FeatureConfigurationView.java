package de.tu_bs.cs.isf.e4cf.featuremodel.configuration.view;

import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.templates.FXToolbar;
import de.tu_bs.cs.isf.e4cf.core.gui.java_fx.util.JavaFXBuilder;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.stringtable.FileTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfigurationController;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.FeatureConfiguration;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.FeatureDiagram;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.GroupVariability;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.IFeature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.model.Variability;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import javafx.beans.binding.Bindings;
import javafx.embed.swt.FXCanvas;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Pair;

public class FeatureConfigurationView {
	private FeatureConfigurationController controller;
	private Composite parent;
	private TreeView<FeatureSelectionElement> featureSelectionTree;
	private TableView<FeatureConfiguration> configTable;
	private TitledPane featureSelectionPane;

	private FeatureDiagram featureDiagram;

	private FeatureConfiguration featureConfiguration;

	public FeatureConfigurationView(Composite parent, FeatureConfigurationController controller) {
		this.parent = parent;
		this.controller = controller;
	}

	public void createControls() {
		FXCanvas canvas = new FXCanvas(parent, SWT.NONE);
		BorderPane layout = new BorderPane();

		// create a toolbar for controls
		Button saveButton = JavaFXBuilder.createButton("Save Configurations", event -> {
			saveConfiguration();
		});
		Button loadButton = JavaFXBuilder.createButton("Load Configuration", event -> {
			controller.loadConfiguration(new Object());
		});
		Button checkButton = JavaFXBuilder.createButton("Check Configuration", event -> {
			controller.checkCurrentConfiguration();
		});

		Button loadFeatureModelButton = JavaFXBuilder.createButton("Load Feature Model", event -> {
			FeatureDiagram temp = controller.loadFeatureModel();
			if (temp == null) {
				System.out.println("No FeatureDiagram selected.");
			} else {
				setFeatureDiagram(temp);
			}
		});

		Button createConfigurationButton = JavaFXBuilder.createButton("Create Configuration", event -> {
			controller.createConfiguration();
		});

		FXToolbar toolbar = new FXToolbar(30, 10);
		toolbar.addItems(loadFeatureModelButton, loadButton, createConfigurationButton, saveButton, checkButton);
		layout.setTop(toolbar.getHbox());
		BorderPane.setMargin(toolbar.getHbox(), new Insets(10));

		// create a list of available configurations
		configTable = new TableView<FeatureConfiguration>();
		configTable.setEditable(true);
		TableColumn<FeatureConfiguration, String> configCol = new TableColumn<FeatureConfiguration, String>("Name");
		configCol.setEditable(true);
		configCol.setCellFactory(TextFieldTableCell.<FeatureConfiguration>forTableColumn());
		configCol.setCellValueFactory(new PropertyValueFactory<FeatureConfiguration, String>("name"));
		configCol.setOnEditCommit(event -> {

			controller.getServices().eventBroker.send(FDEventTable.EVENT_RENAME_CONFIGURATIONFEATURE,
					new Pair<String, String>(event.getRowValue().getName(), event.getNewValue()));
			event.getRowValue().setName(event.getNewValue());
			event.consume();
		});
		configTable.getColumns().add(configCol);
		configTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		configTable.setRowFactory(row -> {
			TableRow<FeatureConfiguration> configRow = new TableRow<>();
			configTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
				if (newSelection != null) {
					refreshView(newSelection);
				}
			});

			final ContextMenu contextMenu = new ContextMenu();
			final MenuItem removeMenuItem = new MenuItem("Remove Configuration");
			removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					FeatureConfiguration config = configRow.getItem();
					configTable.getItems().remove(config);
					removeFeatureConfiguration(config);
					saveConfiguration();
				}
			});
			contextMenu.getItems().add(removeMenuItem);
			configRow.contextMenuProperty()
					.bind(Bindings.when(configRow.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
			return configRow;
		});
		layout.setRight(configTable);

		// feature configuration tree
		featureSelectionTree = new TreeView<FeatureSelectionElement>();
		featureSelectionPane = new TitledPane("", featureSelectionTree);
		featureSelectionPane.setCollapsible(false);
		layout.setCenter(featureSelectionPane);
		// selection event
		featureSelectionTree.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (newValue == null) {
						return;
					}
					TreeItem<FeatureSelectionElement> selectedItem = (TreeItem<FeatureSelectionElement>) newValue;
					controller.getServices().eventBroker.send(FDEventTable.RESET_SELECTED_FEATURES, null);
					controller.getServices().eventBroker.send(FDEventTable.SET_SELECTED_FEATURE,
							selectedItem.getValue().getFeature());

				});

		Scene scene = new Scene(layout);
		canvas.setScene(scene);
	}

	private void saveConfiguration() {
		controller.saveConfiguration("");
	}

	private void removeFeatureConfiguration(FeatureConfiguration config) {
		featureDiagram.removeConfiguration(config);
		if (featureDiagram.getConfigurations().size() == 0) {
			featureSelectionTree.setRoot(null);
		}
	}

	public void refreshView(FeatureConfiguration fc) {
		this.featureConfiguration = fc;

		// iterates breadth-first over features
		Queue<CheckBoxTreeItem<FeatureSelectionElement>> parentQueue = new LinkedList<>();
		for (Entry<IFeature, Boolean> featureEntry : fc.getFeatureSelection().entrySet()) {
			IFeature feature = featureEntry.getKey();

			FeatureSelectionElement element = new FeatureSelectionElement(featureEntry);
			CheckBoxTreeItem<FeatureSelectionElement> item = createCheckedItem(element, false);

			// distinguish the root feature from others
			if (feature.getParent() == null) {
				featureSelectionTree.setRoot(item);
				item.setSelected(true);
			} else if (!parentQueue.isEmpty()) {
				// connect new item to a parent item according to the feature tree
				CheckBoxTreeItem<FeatureSelectionElement> parentItem = parentQueue.peek();
				FeatureSelectionElement parentEntry = parentItem.getValue();

				while (parentEntry.getFeature() != featureEntry.getKey().getParent().get()) {
					parentQueue.poll();
					parentItem = parentQueue.peek();
					parentEntry = parentItem.getValue();
				}

				// find an initial selection state from the variability upwards
				if (parentItem.isSelected()) {
					IFeature parentFeature = parentItem.getValue().getFeature();
					if (parentFeature.getGroupVariability().equals(GroupVariability.OR)
							|| parentFeature.getGroupVariability().equals(GroupVariability.ALTERNATIVE)) {
						// if the feature is the only child OR the feature is mandatory, select it too
						if (parentFeature.getChildren().size() <= 1
								|| feature.getVariability().equals(Variability.MANDATORY)) {
							item.setSelected(true);
						}
					} else {
						// in case of the parent feature variability state being AND, select feature
						item.setSelected(true);
					}
				}

				parentItem.getChildren().add(item);
			} else {
				// error
				throw new RuntimeException("The stack should never be empty.");
			}

			parentQueue.add(item);
		}

		// set the cell factory
		featureSelectionTree.setCellFactory(CheckBoxTreeCell.<FeatureSelectionElement>forTreeView());

		expandTree(featureSelectionTree.getRoot());
		// featureSelectionTree.refresh();

	}

	private <T> void expandTree(TreeItem<T> item) {
		item.setExpanded(true);
		for (TreeItem<T> child : item.getChildren()) {
			expandTree(child);
		}
	}

	private CheckBoxTreeItem<FeatureSelectionElement> createCheckedItem(FeatureSelectionElement element,
			boolean independent) {
		CheckBoxTreeItem<FeatureSelectionElement> item = new CheckBoxTreeItem<FeatureSelectionElement>(element);
		item.setIndependent(independent);
		boolean selected = element.isSelected();
		item.setSelected(selected);
		if (element.get().getKey().getGroupVariability().equals(GroupVariability.ALTERNATIVE)) {
			item.setGraphic(new ImageView(FileTable.FV_ALTERNATIVE_16));
		} else if (element.get().getKey().getVariability().equals(Variability.MANDATORY)) {
			item.setGraphic(new ImageView(FileTable.FV_MANDATORY_16));
		} else {
			item.setGraphic(new ImageView(FileTable.FV_OPTIONAL_16));
		}
		item.addEventHandler(CheckBoxTreeItem.<FeatureSelectionElement>checkBoxSelectionChangedEvent(), (event) -> {
			// propagate checkbox selection to configuration element
			item.getValue().setSelected(event.getTreeItem().isSelected());
			item.setIndeterminate(false);
			event.consume();
		});

		return item;
	}

	private void loadConfigurations(FeatureDiagram fd) {
		configTable.getItems().clear();
		configTable.getItems().setAll(fd.getConfigurations());
	}

	public FeatureConfiguration getFeatureConfiguration() {
		return featureConfiguration;
	}

	public void setFeatureConfiguration(FeatureConfiguration fc) {
		try {
			this.featureConfiguration = fc;
			loadConfigurations(featureDiagram);
			refreshView(featureConfiguration);
			configTable.getSelectionModel().select(featureConfiguration);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FeatureDiagram getFeatureDiagram() {
		return featureDiagram;
	}

	public void setFeatureDiagram(FeatureDiagram fd) {
		// set name of current diagram
		loadConfigurations(fd);
		featureSelectionTree.setRoot(null);
		this.featureDiagram = fd;

		this.featureSelectionPane.setCollapsible(true);
		this.featureSelectionPane.setText("Current Feature Model: " + fd.getRoot().getName());
		this.featureSelectionPane.setCollapsible(false);

		if (fd.getConfigurations().size() > 0) {
			setFeatureConfiguration(fd.getConfigurations().get(0));
		} else {
			setFeatureConfiguration(new FeatureConfiguration(fd));
		}
	}
}
