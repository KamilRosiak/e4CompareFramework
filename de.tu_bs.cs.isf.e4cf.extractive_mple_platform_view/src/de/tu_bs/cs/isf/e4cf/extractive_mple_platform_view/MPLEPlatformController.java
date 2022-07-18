package de.tu_bs.cs.isf.e4cf.extractive_mple_platform_view;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.ComponentConfiguration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.ConfigurationImpl;
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
	TableView<UUID> uuidTable;

	@FXML
	TableColumn<UUID, String> configUUIDCol;

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

		configUUIDCol.setCellValueFactory(e -> {
			return new SimpleStringProperty(e.getValue().toString());
		});

		componentIDCol.setCellValueFactory(e -> {
			return new SimpleStringProperty(e.getValue().componentUUID.toString());
		});

		paarentCol.setCellValueFactory(e -> {
			return new SimpleStringProperty(e.getValue().parentUUID.toString());
		});

		configTable.getSelectionModel().selectedItemProperty().addListener(e -> {
			uuidTable.getItems().clear();
			uuidTable.getItems().addAll(configTable.getSelectionModel().getSelectedItem().getUUIDs());
			uuidTable.refresh();

			componentConfigs.getItems().clear();
			componentConfigs.getItems()
					.addAll(configTable.getSelectionModel().getSelectedItem().getComponentConfigurations());
			componentConfigs.refresh();

		});

		uuidTable.getSelectionModel().selectedItemProperty().addListener(e -> {
			services.eventBroker.send(MPLEEditorConsts.SHOW_UUID, uuidTable.getSelectionModel().getSelectedItem());
		});
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
