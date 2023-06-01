
package de.tu_bs.cs.isf.e4cf.featuremodel.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.widgets.Composite;

import FeatureDiagram.FeatureDiagramm;
import de.tu_bs.cs.isf.e4cf.compare.stringtable.CompareST;
import de.tu_bs.cs.isf.e4cf.core.util.RCPContentProvider;
import de.tu_bs.cs.isf.e4cf.core.util.RCPMessageProvider;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.core.util.emf.EMFResourceSetManager;
import de.tu_bs.cs.isf.e4cf.core.util.emf.IResourceManager;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.checker.DimacsCnfChecker;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.checker.FeatureConfigurationChecker;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.checker.FeatureConfigurationChecker.OperationState;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.stringtable.FeatureModelConfigurationEvents;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.stringtable.FeatureModelConfigurationStrings;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.util.FeatureConfigurationBuilder;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.view.FeatureConfigurationView;
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.view.LoadFeatureConfigurationResourceDialog;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.FeatureDiagramSerialiazer;
import featureConfiguration.FeatureConfiguration;
import javafx.util.Pair;

public class FeatureConfigurationController {

	private static final String PART_DIALOG_TITLE = "Feature Configuration";

	private FeatureConfigurationBuilder featureConfigurationBuilder;
	private FeatureConfiguration featureConfiguration;

	private ServiceContainer services;

	private FeatureConfigurationView view;

	private IResourceManager resourceManager;

	@Inject
	public FeatureConfigurationController(ServiceContainer serviceContainer) {
		this.services = serviceContainer;

		this.resourceManager = new EMFResourceSetManager(
				Arrays.asList(FeatureModelConfigurationStrings.FC_FILE_EXTENSION, FDStringTable.FD_FILE_ENDING));
		this.featureConfigurationBuilder = new FeatureConfigurationBuilder();
		this.featureConfiguration = featureConfigurationBuilder.createFeatureConfiguration("dummyConfig", null,
				Collections.emptyMap());
	}

	@PostConstruct
	public void postConstruct(Composite parent) {
		this.view = new FeatureConfigurationView(parent, this);
		view.createControls();
	}

	@Optional
	@Inject
	public void showConfigurationView(@UIEventTopic(FDEventTable.EVENT_SHOW_CONFIGURATION_VIEW) FeatureDiagramm fd) {
		//TODO:FD
		if (fd != null) {
			removeNull(fd.getFeatureConfiguration());
			view.setFeatureDiagram(fd);
		}
	}

	@Optional
	@Inject
	public void showConfigurationComponentView(
			@UIEventTopic(FDEventTable.EVENT_SHOW_CONFIGURATION_VIEW) Pair<FeatureDiagramm, FeatureConfiguration> config) {
		showConfigurationView(config.getKey());
		view.setFeatureConfiguration(config.getValue());
	}

	public void createConfiguration() {
		// construct feature configuration
		services.partService.showPart(FDStringTable.FD_FEATURE_CONFIG_PART_NAME);
		featureConfiguration = featureConfigurationBuilder.createFeatureConfiguration(view.getFeatureDiagram());
		view.setFeatureConfiguration(featureConfiguration);
	}

	@PreDestroy
	public void preDestroy() {

	}

	@Focus
	public void onFocus() {

	}

	@Optional
	@Inject
	public void saveConfiguration(@UIEventTopic(FeatureModelConfigurationEvents.EVENT_SAVE_CONFIGURATION) String path) {
		if (featureConfiguration == null) {
			return;
		}

		// Only continue save process if feature diagram is already contained in a
		// resource
		FeatureDiagramm fd = view.getFeatureDiagram();
		if (fd.eResource() == null) {
			boolean saveFd = !RCPMessageProvider.questionMessage("Save Feature Configuration",
					"The feature model is not contained in a resource. Please save it first.");
			if (!saveFd) {
				return;
			}
		}

		resourceManager.addResource(fd.eResource());

		// add feature configuration to resource manager
		try {
			for (FeatureConfiguration config : view.getFeatureDiagram().getFeatureConfiguration()) {
				URI fcUri = URI.createFileURI(getFcFilePath(config));
				Resource resource = resourceManager.addResource(fcUri);
				resource.getContents().add(config);
			}
		} catch (IllegalArgumentException e) {
			RCPMessageProvider.errorMessage(PART_DIALOG_TITLE,
					"The provided path(" + path + ") is not a valid file system path");
			return;
		}

		// save resources
		try {
			resourceManager.save();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// clean up
		resourceManager.removeAllResources();
	}

	private String getFcFilePath(FeatureConfiguration fc) {
		return RCPContentProvider.getCurrentWorkspacePath() + CompareST.FEATURE_CONFIGURATIONS + "/" + fc.getName()
				+ "." + FeatureModelConfigurationStrings.FC_FILE_EXTENSION;
	}

	@Persist
	public void save() {

	}

	@Optional
	@Inject
	public void loadConfiguration(@UIEventTopic(FeatureModelConfigurationEvents.EVENT_LOAD_CONFIGURATION) Object o) {
		LoadFeatureConfigurationResourceDialog loadDialog = new LoadFeatureConfigurationResourceDialog(
				"Load Feature Configuration", 1024, 30);

		// loadDialog.open();
		// String resourcePath = loadDialog.getResourcePath();

		String filepath = RCPMessageProvider.getFilePathDialog("Load Feature Configuration",
				CompareST.FEATURE_CONFIGURATIONS);

		// System.out.println(resourcePath);
		// System.out.println(filepath);

		if (!filepath.equals("")) {
			loadConfiguration(filepath);
		}
		// if (resourcePath != null) {
		// loadConfiguration(resourcePath);
		// }
	}

	@Optional
	@Inject
	public void loadConfiguration(
			@UIEventTopic(FeatureModelConfigurationEvents.EVENT_LOAD_CONFIGURATION_FROM_FILE) String path) {
		URI fcUri;
		try {
			fcUri = URI.createFileURI(path);
			resourceManager.addResource(fcUri);
		} catch (IllegalArgumentException e) {
			RCPMessageProvider.errorMessage(PART_DIALOG_TITLE,
					"The path(" + path + ") is not a valid file system path.");
			return;
		}

		try {
			resourceManager.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// collect the loaded feature configuration
		Resource fcResource = resourceManager.getResource(fcUri);
		FeatureConfiguration fc = (FeatureConfiguration) fcResource.getContents().get(0);

		// load the corresponding feature model ?
		if (!fc.getFeatureDiagram().eIsProxy()) {
			boolean loadFeatureModel = RCPMessageProvider.questionMessage("Load Feature Configuration",
					"Do you want to load the feature model associated with the loaded feature configuration?");
			if (loadFeatureModel) {
				services.eventBroker.send(FDEventTable.LOAD_FEATURE_DIAGRAM, fc.getFeatureDiagram());
			}
		}

		// create the view
		view.setFeatureDiagram(fc.getFeatureDiagram());
		view.refreshView(fc);

		// set internals
		this.featureConfiguration = fc;

		resourceManager.removeAllResources();
	}

	public void checkCurrentConfiguration() {
		try {
			FeatureConfigurationChecker configChecker = new DimacsCnfChecker();
			configChecker.initialize(featureConfiguration);
			String name = featureConfiguration.getName().equals("") ? "The current configuration"
					: featureConfiguration.getName();

			OperationState op = configChecker.check();
			switch (op) {
			case SUCCESS: {
				boolean validConfig = configChecker.getResult();
				if (validConfig) {
					RCPMessageProvider.informationMessage(PART_DIALOG_TITLE, name + " is valid.");
				} else {
					if (op.hasInfo()) {
						RCPMessageProvider.informationMessage(PART_DIALOG_TITLE, op.getInfo());
					} else {
						RCPMessageProvider.informationMessage(PART_DIALOG_TITLE, name + " is invalid.");
					}
				}
			}
				break;
			case TIMEOUT: {
				RCPMessageProvider.informationMessage(PART_DIALOG_TITLE, "The check request timed out.");
			}
				break;
			case IO_ERROR: {
				RCPMessageProvider.informationMessage(PART_DIALOG_TITLE, "An IO error occurred.");
			}
				break;
			case PRECONDITION_ERROR: {
				RCPMessageProvider.informationMessage(PART_DIALOG_TITLE, "One or more preconditions were not met.");
			}
				break;
			case PARSE_ERROR: {
				RCPMessageProvider.informationMessage(PART_DIALOG_TITLE, "A Dimacs format error occurred.");
			}
				break;
			default:
				RCPMessageProvider.errorMessage(PART_DIALOG_TITLE, "Undefined behaviour occurred.");
			}

		} catch (Exception e) {
			return;
		}

	}

	public FeatureConfiguration getFeatureConfiguration() {
		return featureConfiguration;
	}

	public ServiceContainer getServices() {
		return services;
	}

	public FeatureConfigurationView getView() {
		return view;
	}

	public FeatureDiagramm loadFeatureModel() {
		FeatureDiagramm featureDiagram;
		String filepath = RCPMessageProvider.getFilePathDialog("Load Feature Diagram", CompareST.FEATURE_MODELS);
		if (filepath.equals("")) {
			return null;
		}
		try {
			featureDiagram = FeatureDiagramSerialiazer.loadFeatureDiagram(filepath);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return null;
		}
		removeNull(featureDiagram.getFeatureConfiguration());
		return featureDiagram;

	}

	private void removeNull(List<FeatureConfiguration> list) {
		List<FeatureConfiguration> nulls = new ArrayList<>();
		for (FeatureConfiguration config : list) {
			if (config.getFeatureDiagram() == null || config.getFeatureSelection() == null
					|| config.getName() == null) {
				nulls.add(config);
			}
		}
		for (FeatureConfiguration config : nulls) {
			list.remove(config);
		}
	}

}