package de.tu_bs.cs.isf.e4cf.extractive_mple_platform_view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.ComponentConfiguration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.ConfigurationImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.util.PipedDeepCopy;
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
				try {
					Node node = (Node) PipedDeepCopy.copy(currentPlatform.model);
					Configuration selectedConfig = configTable.getSelectionModel().getSelectedItem();
					node = configureVariant(selectedConfig, node);

					services.eventBroker.send(MPLEEditorConsts.SHOW_TREE, new TreeImpl(selectedConfig.getName(), node));
				} catch (Exception e2) {
					e2.printStackTrace();
				}
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

	/**
	 * Recovers the variant for the given configuration
	 */
	private Node configureVariant(Configuration selectedConfig, Node node) {
		if (selectedConfig.getUUIDs().contains(node.getUUID())) {

			node.setVariabilityClass(VariabilityClass.MANDATORY);
			List<Attribute> attributeToRemove = new ArrayList<Attribute>();
			// Configure Attributes
			node.getAttributes().forEach(attribute -> {
				if (selectedConfig.getUUIDs().contains(attribute.getUuid())) {
					List<Value> valuestoRemove = new ArrayList<Value>();
					// Configure Values
					attribute.getAttributeValues().forEach(value -> {
						if (!selectedConfig.getUUIDs().contains(value.getUUID())) {
							valuestoRemove.add(value);
						}
					});
					attribute.getAttributeValues().removeAll(valuestoRemove);

				} else {
					attributeToRemove.add(attribute);
				}
			});
			node.getAttributes().removeAll(attributeToRemove);
		}
		node.getChildren().removeAll(configureVariantRecursivly(selectedConfig, node));

		return node;
	}

	private List<Node> configureVariantRecursivly(Configuration selectedConfig, Node node) {
		List<Node> nodesToRemove = new ArrayList<Node>();
		List<Node> componentsToAdd = new ArrayList<Node>();

		node.getChildren().forEach(childNode -> {
			if (!childNode.isComponent()) {
				if (selectedConfig.getUUIDs().contains(childNode.getUUID())) {
					childNode.setVariabilityClass(VariabilityClass.MANDATORY);
					List<Attribute> attributeToRemove = new ArrayList<Attribute>();
					// Configure Attributes
					childNode.getAttributes().forEach(attribute -> {
						if (selectedConfig.getUUIDs().contains(attribute.getUuid())) {
							List<Value> valuestoRemove = new ArrayList<Value>();
							// Configure Values
							attribute.getAttributeValues().forEach(value -> {
								if (!selectedConfig.getUUIDs().contains(value.getUUID())) {
									valuestoRemove.add(value);
								}
							});
							attribute.getAttributeValues().removeAll(valuestoRemove);

						} else {
							attributeToRemove.add(attribute);
						}
					});
					childNode.getAttributes().removeAll(attributeToRemove);

					childNode.getChildren().removeAll(configureVariantRecursivly(selectedConfig, childNode));
				} else {
					nodesToRemove.add(childNode);
				}
			} else {
				// get all available configurations for this component
				List<ComponentConfiguration> componentConfigs = selectedConfig
						.getConfigurationsForComponent(childNode.getUUID());
				componentConfigs.forEach(config -> {
					// only configure components with node as parent
					if (config.parentUUID.equals(node.getUUID())) {
						Node configuredComponent = configureVariant(config.configuration,
								(Node) PipedDeepCopy.copy(childNode));
						configuredComponent.setParent(node);
						componentsToAdd.add(configuredComponent);
					}
				});
				nodesToRemove.add(childNode);
			}

		});
		node.getChildren().addAll(componentsToAdd);
		return nodesToRemove;
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
