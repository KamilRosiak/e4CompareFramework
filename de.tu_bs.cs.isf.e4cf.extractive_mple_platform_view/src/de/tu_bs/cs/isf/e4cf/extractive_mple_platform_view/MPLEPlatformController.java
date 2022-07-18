package de.tu_bs.cs.isf.e4cf.extractive_mple_platform_view;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.inject.Inject;
import org.apache.commons.lang.SerializationUtils;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.ComponentConfiguration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.ConfigurationImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.extractive_mple.consts.MPLEEditorConsts;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * 
 * @author Team05 , Kamil Rosiak
 *
 */
public class MPLEPlatformController implements Initializable {
	@Inject
	private ServiceContainer services;

	@FXML
	TableView<Configuration> configTable;

	@FXML
	TableColumn<ConfigurationImpl, String> configNameCol;

	@FXML
	TableColumn<ConfigurationImpl, Number> amountCol;

	@FXML
	TableView<UUID> uuidTable, componentUUIDTable;

	@FXML
	TableColumn<UUID, String> configUUIDCol, componentUUIDCol;

	@FXML
	TableView<ComponentConfiguration> componentConfigs;

	@FXML
	TableColumn<ComponentConfiguration, String> componentIDCol, paarentCol;

	MPLPlatform currentPlatform;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		configNameCol.setCellValueFactory(e -> {
			return new SimpleStringProperty(e.getValue().getName());
		});
		amountCol.setCellValueFactory(e -> {
			ObservableValue<Number> sip = new SimpleIntegerProperty(e.getValue().getUUIDs().size());
			return sip;
		});

		componentUUIDCol.setCellValueFactory(e -> {
			return new SimpleStringProperty(e.getValue().toString());
		});

		configUUIDCol.setCellValueFactory(e -> {
			return new SimpleStringProperty(e.getValue().toString());
		});

		componentIDCol.setCellValueFactory(e -> {
			return new SimpleStringProperty(e.getValue().componentUUID.toString());
		});

		paarentCol.setCellValueFactory(e -> {
			return new SimpleStringProperty(e.getValue().parentUUID.toString());
		});

		configTable.setOnMouseClicked(e -> {
			if (e.getClickCount() == 2) {
				NodeImpl node = (NodeImpl) SerializationUtils.clone(currentPlatform.model);
				Configuration selectedConfig = configTable.getSelectionModel().getSelectedItem();
				node = configureVariant(selectedConfig, node);

			}
		});

		configTable.getSelectionModel().selectedItemProperty().addListener(e -> {
			uuidTable.getItems().clear();
			if (configTable.getSelectionModel().getSelectedItem() != null) {
				uuidTable.getItems().addAll(configTable.getSelectionModel().getSelectedItem().getUUIDs());
			}
			uuidTable.refresh();

			if (configTable.getSelectionModel().getSelectedItem() != null) {
				componentConfigs.getItems().clear();
				componentConfigs.getItems()
						.addAll(configTable.getSelectionModel().getSelectedItem().getComponentConfigurations());
			}
			componentConfigs.refresh();

		});

		componentConfigs.getSelectionModel().selectedItemProperty().addListener(e -> {
			componentUUIDTable.getItems().clear();
			if (componentConfigs.getSelectionModel().getSelectedItem() != null) {
				componentUUIDTable.getItems()
						.addAll(componentConfigs.getSelectionModel().getSelectedItem().configuration.getUUIDs());

			}
			componentUUIDTable.refresh();
		});

		componentUUIDTable.getSelectionModel().selectedItemProperty().addListener(e -> {
			services.eventBroker.send(MPLEEditorConsts.SHOW_UUID,
					componentUUIDTable.getSelectionModel().getSelectedItem());
		});

		uuidTable.getSelectionModel().selectedItemProperty().addListener(e -> {
			services.eventBroker.send(MPLEEditorConsts.SHOW_UUID, uuidTable.getSelectionModel().getSelectedItem());
		});

	}

	private NodeImpl configureVariant(Configuration selectedConfig, NodeImpl node) {
		Iterator<Node> nodeIterator = node.getChildren().iterator();

		while (nodeIterator.hasNext()) {
			Node node2 = (Node) nodeIterator.next();
			if (!node2.isComponent()) {
				if (selectedConfig.getUUIDs().contains(node2.getUUID())) {
					// If UUID is in configuration
					Iterator<Attribute> attrIterator = node2.getAttributes().iterator();
					while (attrIterator.hasNext()) {
						Attribute attribute = (Attribute) attrIterator.next();
						if (selectedConfig.getUUIDs().contains(attribute.getUuid())) {
							Iterator<Value> values = attribute.getAttributeValues().iterator();
							while (values.hasNext()) {
								Value value = (Value) values.next();
								if (!selectedConfig.getUUIDs().contains(value.getUUID())) {
									values.remove();
								}
							}

						} else {
							attrIterator.remove();
						}
					}

				} else {
					// If UUID is not in configuration remove node
					nodeIterator.remove();
				}

			} else {
				UUID componentID = node2.getUUID();
				
				
				
				
				
			}

		}

		return null;
	}

	@FXML
	private void showPlatfform() {
		if (currentPlatform != null) {
			services.partService.showPart(MPLEEditorConsts.TREE_VIEW_ID);
			services.eventBroker.send(MPLEEditorConsts.SHOW_MPL, currentPlatform);
		}
	}

	private void showConfigurations() {
		configTable.getItems().clear();
		configTable.getItems().addAll(currentPlatform.configurations);
	}

	/**
	 * Method to initialize the treeView from a given Tree
	 * 
	 * @param platform
	 */
	@Optional
	@Inject
	public void showMPL(@UIEventTopic(MPLEEditorConsts.SHOW_MPL) MPLPlatform platform) {
		currentPlatform = platform;
		showConfigurations();
	}

}
