package de.tu_bs.cs.isf.e4cf.extractive_mple_platform_view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import com.opencsv.CSVWriter;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.CloneConfiguration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.Configuration;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.configuration.ConfigurationImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.VariabilityClass;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.TreeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.ReaderManager;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.io.writer.TreeWriter;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.util.PipedDeepCopy;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.extractive_mple.consts.MPLEEditorConsts;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLEPlatformUtil;
import de.tu_bs.cs.isf.e4cf.extractive_mple.structure.MPLPlatform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * 
 * @author Team05 , Kamil Rosiak
 *
 */
public class MPLEPlatformController implements Initializable {
	@Inject
	private ServiceContainer services;

	/**
	 * Variant Configurations
	 */
	@FXML
	TableView<Configuration> configTable;
	@FXML
	TableColumn<ConfigurationImpl, String> configNameCol;
	@FXML
	TableColumn<ConfigurationImpl, Number> amountCol;

	/**
	 * Variant Artifacts
	 */
	@FXML
	TableView<UUID> uuidTable;

	/**
	 * Clone Configuration Artifacts
	 */
	@FXML
	TableView<UUID> componentUUIDTable;

	@FXML
	SplitPane viewContainer;

	@FXML
	VBox configVbox, variantVbox, top, bottom;

	@FXML
	TableColumn<UUID, String> configUUIDCol, componentUUIDCol;

	/**
	 * Clone Configurations
	 */
	@FXML
	TableView<CloneConfiguration> componentConfigs;
	@FXML
	TableColumn<CloneConfiguration, String> componentIDCol, paarentCol;

	MPLPlatform currentPlatform;

	@Inject
	IEclipseContext context;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		configTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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

		// display single variant in MPLEditor on double-click
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

		// display variant artifacts and clone configs when a variant is selected
		configTable.getSelectionModel().selectedItemProperty().addListener(e -> {
			uuidTable.getItems().clear();
			componentConfigs.getItems().clear();

			if (configTable.getSelectionModel().getSelectedItem() != null) {
				uuidTable.getItems().addAll(configTable.getSelectionModel().getSelectedItem().getUUIDs());
				componentConfigs.getItems()
						.addAll(configTable.getSelectionModel().getSelectedItem().getCloneConfigurations());
			}
			uuidTable.refresh();
			componentConfigs.refresh();
		});

		// highlight parent of clone config in MPLEditor on selection of a clone config
		componentConfigs.getSelectionModel().selectedItemProperty().addListener(e -> {
			componentUUIDTable.getItems().clear();

			CloneConfiguration cloneConfig = componentConfigs.getSelectionModel().getSelectedItem();
			if (cloneConfig != null) {
				componentUUIDTable.getItems().addAll(cloneConfig.configuration.getUUIDs());
				String parentToChildUuid = cloneConfig.getParentUUID().toString() + "#"
						+ cloneConfig.getComponentUUID().toString();
				services.eventBroker.send(MPLEEditorConsts.SHOW_CLONE_UUID, parentToChildUuid);
			}
			componentUUIDTable.refresh();
		});

		// highlight artifact in MPLEdito when it is selected in variant artifacts or
		// clone config artifacts table
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
		if (selectedConfig != null)
			if (selectedConfig.getUUIDs().contains(node.getUUID())) {
				node.setVariabilityClass(VariabilityClass.MANDATORY);
				List<Attribute> attributeToRemove = new ArrayList<Attribute>();
				// Configure Attributes
				node.getAttributes().forEach(attribute -> {
					if (selectedConfig.getUUIDs().contains(attribute.getUUID())) {
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
		configureVariantRecursivly(selectedConfig, node);

		return node;
	}

	private void configureVariantRecursivly(Configuration selectedConfig, Node node) {
		List<Node> nodesToRemove = new ArrayList<Node>();
		List<Node> componentsToAdd = new ArrayList<Node>();

		node.getChildren().forEach(childNode -> {
			if (!childNode.isClone()) {
				if (selectedConfig.getUUIDs().contains(childNode.getUUID())) {
					childNode.setVariabilityClass(VariabilityClass.MANDATORY);
					List<Attribute> attributeToRemove = new ArrayList<Attribute>();
					// Configure Attributes
					childNode.getAttributes().forEach(attribute -> {
						if (selectedConfig.getUUIDs().contains(attribute.getUUID())) {
							List<Value<?>> valuestoRemove = new ArrayList<>();
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

					configureVariantRecursivly(selectedConfig, childNode);
				} else {
					nodesToRemove.add(childNode);
				}
			} else {
				// get all available configurations for this component
				List<CloneConfiguration> componentConfigs = selectedConfig
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
		node.getChildren().removeAll(nodesToRemove);
		node.getChildren().addAll(componentsToAdd);
	}

	@FXML
	private void storeVariant() {
		configTable.getSelectionModel().getSelectedItems().forEach(config -> {
			Node node = (Node) PipedDeepCopy.copy(currentPlatform.model);
			Configuration selectedConfig = config;
			node = configureVariant(selectedConfig, node);
			Tree variantTree = new TreeImpl(selectedConfig.getName(), node);
			TreeWriter writter = new TreeWriter();
			ContextInjectionFactory.inject(writter, context);
			writter.writeArtifact(variantTree, services.workspaceFileSystem.getWorkspaceDirectory().getAbsolutePath()
					+ "\\" + selectedConfig.getName());
		});

	}

	@FXML
	private void showPlatform() {
		if (currentPlatform != null) {
			services.eventBroker.send(MPLEEditorConsts.SHOW_MPL, currentPlatform);
		}
	}

	@FXML
	private void addVariant() {
		FileChooser fc = new FileChooser();
		fc.setSelectedExtensionFilter(new ExtensionFilter("tree", "*.tree"));
		fc.setInitialDirectory(new File(ResourcesPlugin.getWorkspace().getRoot().getLocation().toString()));

		List<File> files = fc.showOpenMultipleDialog(new Stage());
		if (files != null) {
			List<FileTreeElement> ftes = files.stream()
					.map(file -> new de.tu_bs.cs.isf.e4cf.core.file_structure.components.File(file.getAbsolutePath()))
					.collect(Collectors.toList());

			ReaderManager reader = new ReaderManager();
			ftes.forEach(variant -> {
				Tree newVariant = reader.readFile(variant);
				currentPlatform.insertVariant(newVariant);

			});
			String fileName = currentPlatform.fileName;
			fileName = services.workspaceFileSystem.getWorkspaceDirectory().getAbsolutePath() + "//" + fileName
					+ ".mpl";
			MPLEPlatformUtil.storePlatform(fileName, currentPlatform);
			showPlatform();
			showConfigurations();
			showMPL(currentPlatform);
		}

		if (services.rcpSelectionService.getCurrentSelectionsFromExplorer().size() > 0) {
			ReaderManager reader = new ReaderManager();
			services.rcpSelectionService.getCurrentSelectionsFromExplorer().stream().map(reader::readFile)
					.forEach(currentPlatform::insertVariant);
			MPLEPlatformUtil.storePlatform(
					services.workspaceFileSystem.getWorkspaceDirectory().getAbsolutePath() + "//" + "clone_model1.mpl",
					currentPlatform);
			showPlatform();
			services.eventBroker.post(MPLEEditorConsts.ADD_VARIANT_TO_MPL, currentPlatform);
		}

	}

	@FXML
	private void storeCurrentPlatform() {
		if (currentPlatform.location.equals("")) {
			String name = RCPMessageProvider.inputDialog("Platform View", "Enter Name for Current Platform.");
			currentPlatform.location = services.workspaceFileSystem.getWorkspaceDirectory().getAbsolutePath() + "//"
					+ name + ".mpl";
		}

		MPLEPlatformUtil.storePlatform(currentPlatform.location, currentPlatform);
	}

	@FXML
	private void printDetails() {
		if (currentPlatform != null && currentPlatform.model != null) {
			System.out.println("Platform number of nodes: " + currentPlatform.model.getAmountOfNodes(0));
			System.out.println("Platform number of UUIDS: " + currentPlatform.model.getAllUUIDS().size());
			Map<UUID, Integer> cloneClasses = new HashMap<UUID, Integer>();
			currentPlatform.configurations.forEach(config -> {
				config.getCloneConfigurations().forEach(cloneConfig -> {
					if (!cloneClasses.containsKey(cloneConfig.componentUUID)) {
						cloneClasses.put(cloneConfig.componentUUID, 1);
					} else {
						cloneClasses.put(cloneConfig.componentUUID, cloneClasses.get(cloneConfig.componentUUID) + 1);
					}
				});
			});

			System.out.println("Total Clone Classes: " + cloneClasses.size());
			System.out.println("Clone Configurations:");
			int configCount = 0;
			for (Entry<UUID, Integer> e : cloneClasses.entrySet()) {
				System.out.println("CloneClassId: " + e.getKey() + " number of configs: " + e.getValue());
				configCount += e.getValue();
			}
			System.out.println("Total Configurations:" + configCount);

			printCloneDistribution();
		}
	}

	@FXML
	private void printCloneDistribution() {
		Map<UUID, Integer> cloneClasses = new HashMap<UUID, Integer>();
		currentPlatform.configurations.forEach(config -> {
			config.getCloneConfigurations().forEach(cloneConfig -> {
				if (!cloneClasses.containsKey(cloneConfig.componentUUID)) {
					cloneClasses.put(cloneConfig.componentUUID, 1);
				} else {
					cloneClasses.put(cloneConfig.componentUUID, cloneClasses.get(cloneConfig.componentUUID) + 1);
				}
			});
		});

		CSVWriter csvWritter = creatCSVWriter(
				new File(services.workspaceFileSystem.getWorkspaceDirectory().getAbsolutePath()
						+ "\\clone_distribution_family_model.csv"));
		List<String> nextLine = new ArrayList<String>();
		nextLine.add("");
		for (Entry<UUID, Integer> e : cloneClasses.entrySet()) {
			nextLine.add("Clone:" + e.getKey());
		}
		String[] line = new String[nextLine.size()];
		csvWritter.writeNext(nextLine.toArray(line));
		for (Configuration variantConfig : currentPlatform.configurations) {
			nextLine.clear();
			// varuant name
			nextLine.add(variantConfig.getName());
			int index = currentPlatform.configurations.indexOf(variantConfig) + 1;
			System.out.println("Current Variant: " + variantConfig.getName() + " (" + index + "/"
					+ currentPlatform.configurations.size() + ")");
			// value for each clone config
			for (Entry<UUID, Integer> e : cloneClasses.entrySet()) {
				int count = 0;
				for (CloneConfiguration cloneConfig : variantConfig.getCloneConfigurations()) {
					if (cloneConfig.getComponentUUID().equals(e.getKey())) {
						count++;
					}
				}
				if (count == 0) {
					nextLine.add("");
				} else {
					nextLine.add(String.valueOf(count));
				}
			}
			line = new String[nextLine.size()];
			csvWritter.writeNext(nextLine.toArray(line));
		}
		try {
			csvWritter.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * Create a UTF8 CSV Writer with semicolon as separation symbol
	 */
	public static CSVWriter creatCSVWriter(File file) {
		CSVWriter writer = null;
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file),
					StandardCharsets.UTF_8);
			writer = new CSVWriter(outputStreamWriter, ';', '"', '/', "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer;
	}

	@FXML
	private void locateFeatures() {
		if (currentPlatform != null) {
			services.partService.showPart("de.tu_bs.cs.isf.e4cf.featuremodel.synthesis.annotation_view");
			services.eventBroker.post(MPLEEditorConsts.LOCATE_FEATURES, currentPlatform);
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
		if (currentPlatform == null || !currentPlatform.equals(platform)) {
			try {
				currentPlatform = platform;
				/**
				 * view switch boolean hasClones = false;
				 * 
				 * for (Configuration config : platform.configurations) { if
				 * (!config.getCloneConfigurations().isEmpty()) { hasClones = true; break; } }
				 * if (!hasClones) { viewContainer.getItems().remove(top);
				 * viewContainer.getItems().remove(bottom);
				 * viewContainer.getItems().add(configVbox);
				 * viewContainer.getItems().add(variantVbox); }
				 **/
				showConfigurations();
				if (platform.getFeatureModel().isPresent()) {
					services.partService.showPart(MPLEEditorConsts.SYNTHESIS_PLUGIN);
					services.eventBroker.post(MPLEEditorConsts.SHOW_FEATURES, platform);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
