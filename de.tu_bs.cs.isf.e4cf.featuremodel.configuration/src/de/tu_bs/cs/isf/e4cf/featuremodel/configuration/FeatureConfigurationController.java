 
package de.tu_bs.cs.isf.e4cf.featuremodel.configuration;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

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
import de.tu_bs.cs.isf.e4cf.featuremodel.configuration.FeatureConfiguration.FeatureConfiguration;

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
		
		this.resourceManager = new EMFResourceSetManager(Arrays.asList(FeatureModelConfigurationStrings.FC_FILE_EXTENSION, FDStringTable.FD_FILE_ENDING));
		this.featureConfigurationBuilder = new FeatureConfigurationBuilder();
		this.featureConfiguration = featureConfigurationBuilder.createFeatureConfiguration("dummyConfig", null, Collections.emptyMap());
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		this.view = new FeatureConfigurationView(parent, this);
		view.createControls();
	}
	
	
	@Optional
	@Inject
	public void createConfiguration(@UIEventTopic(FeatureModelConfigurationEvents.EVENT_CREATE_CONFIGURATION) FeatureDiagramm fd) {
		// construct feature configuration 
		featureConfiguration = featureConfigurationBuilder.createFeatureConfiguration(fd);
		view.refreshView(featureConfiguration);
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
		if (featureConfiguration == null || featureConfiguration.getFeatureDiagram() == null) {
			return;
		}
				
		// Only continue save process if feature diagram is already contained in a resource
		FeatureDiagramm fd = featureConfiguration.getFeatureDiagram();
		if (fd.eResource() == null) {
			boolean saveFd = !RCPMessageProvider.questionMessage("Save Feature Configuration", 
					"The feature model is not contained in a resource. Do you want to save it first.");
			if (!saveFd) {
				return;
			}
		}
		
		resourceManager.addResource(fd.eResource());
		
		// add feature configuration to resource manager
		try {
			URI fcUri = URI.createFileURI(path);
			Resource resource = resourceManager.addResource(fcUri);
			resource.getContents().add(featureConfiguration);				
		} catch (IllegalArgumentException e) {
			RCPMessageProvider.errorMessage(PART_DIALOG_TITLE, "The provided path("+path+") is not a valid file system path");
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
	
	
	@Persist
	public void save() {
		
	}
	
	@Optional
	@Inject
	public void loadConfiguration(@UIEventTopic(FeatureModelConfigurationEvents.EVENT_LOAD_CONFIGURATION) Object o) {
		LoadFeatureConfigurationResourceDialog loadDialog = new LoadFeatureConfigurationResourceDialog("Load Feature Configuration", 
				1024, 30);
		
		loadDialog.open();
		
		String resourcePath = loadDialog.getResourcePath();
		if (resourcePath == null) {
			return;
		}
		
		loadConfiguration(resourcePath);
	}
	
	@Optional
	@Inject
	public void loadConfiguration(@UIEventTopic(FeatureModelConfigurationEvents.EVENT_LOAD_CONFIGURATION_FROM_FILE) String path) {
		URI fcUri;
		try {
			fcUri = URI.createFileURI(path);			
			resourceManager.addResource(fcUri);
		} catch (IllegalArgumentException e) {
			RCPMessageProvider.errorMessage(PART_DIALOG_TITLE, "The path("+path+") is not a valid file system path.");
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
		view.refreshView(fc);
		
		// set internals
		this.featureConfiguration = fc;
		
		resourceManager.removeAllResources();
	}

	public void checkCurrentConfiguration() {
		try {
			FeatureConfigurationChecker configChecker = new DimacsCnfChecker();
			configChecker.initialize(featureConfiguration);
			
			OperationState op = configChecker.check();
			switch (op) {
				case SUCCESS: {
					boolean validConfig = configChecker.getResult();
					if (validConfig) {
						RCPMessageProvider.informationMessage(PART_DIALOG_TITLE, "The current configuration is valid.");						
					} else {
						if (op.hasInfo()) {
							RCPMessageProvider.informationMessage(PART_DIALOG_TITLE, op.getInfo());
						} else {
							RCPMessageProvider.informationMessage(PART_DIALOG_TITLE, "The current configuration is invalid.");
						}
					} 
				} break;
				case TIMEOUT: {
					RCPMessageProvider.informationMessage(PART_DIALOG_TITLE, "The check request timed out.");
				} break;
				case IO_ERROR: {
					RCPMessageProvider.informationMessage(PART_DIALOG_TITLE, "An IO error occurred.");
				} break;
				case PRECONDITION_ERROR: {
					RCPMessageProvider.informationMessage(PART_DIALOG_TITLE, "One or more preconditions were not met.");
				} break;
				case PARSE_ERROR: {
					RCPMessageProvider.informationMessage(PART_DIALOG_TITLE, "A Dimacs format error occurred.");
				} break;
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

	
}