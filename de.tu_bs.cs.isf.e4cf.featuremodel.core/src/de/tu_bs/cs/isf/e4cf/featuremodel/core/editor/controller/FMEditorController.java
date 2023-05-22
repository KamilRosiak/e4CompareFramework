package de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.controller;
 
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import de.tu_bs.cs.isf.e4cf.featuremodel.core.editor.view.FMEditorControllerData;

/**
 * This class represents the controller of a MVC implementation. It handles events from FDEventTable.
 * @author {Kamil Rosiak}
 *
 */
@Singleton
@Creatable
public class FMEditorController {
	private FMEditorControllerData data = new FMEditorControllerData();

//	@PostConstruct
//	public void postConstruct(Composite parent, ServiceContainer services, EMenuService menuService) {
//		FXCanvas canvas = new FXCanvas(parent, SWT.NONE);
//		data.tabPane = new TabPane();
//		canvas.setScene(new Scene(data.tabPane));
//
//
//		menuService.registerContextMenu(canvas, "de.tu_bs.cs.isf.e4cf.featuremodel.core.featureModelMen�");
//		this.data.services = services;
//		this.data.errorListeners = new ArrayList<>();
//
//		createNewTab(FDStringTable.FD_DEFAULT_NAME);
//	}
//
//	private FMEditorView getCurrentView() {
//		Tab currentTab = data.tabPane.getSelectionModel().getSelectedItem();
//		if (currentTab == null) {
//			return (FMEditorView) createNewTab(FDStringTable.FD_DEFAULT_NAME).getUserData();
//		}
//		FMEditorView view = (FMEditorView) currentTab.getUserData();
//		return view;
//	}
//	
//	private Tab getCurrentTab() {
//		return data.tabPane.getSelectionModel().getSelectedItem();
//	}
//
//	private Tab createNewTab(String title) {
//		Tab tab = newTab(title);
//		FMEditorView view = new FMEditorView(tab, data.services);
//		tab.setUserData(view);
//		data.tabPane.getTabs().add(tab);
//		return tab;
//	}
//
//	private Tab newTab(String title) {
//		Tab tab = new Tab(title);
//		tab.setOnCloseRequest(event -> {
//			((FMEditorView) tab.getUserData()).askToSave();		
//			// don't allow the tabPane to be empty
//			if (data.tabPane.getTabs().size() == 1) {
//				createNewTab(FDStringTable.FD_DEFAULT_NAME);
//			}
//		});
//		return tab;
//	}
//	
//	@Optional
//	@Inject
//	public void openFeatureDiagram(@UIEventTopic(FDEventTable.OPEN_FEATURE_DIAGRAM) ComponentFeature feature) {
//		FeatureDiagramm diagram = feature.getFeaturediagramm();
//		Tab openedTab = getOpenedTabOrNull(diagram);
//		
//		if (openedTab != null) {
//			selectTab(openedTab);
//		} else {
//			data.services.eventBroker.send(FDEventTable.ADD_NEW_TAB, feature);
//		}
//		
//	}
//	
//	private Tab getOpenedTabOrNull(FeatureDiagramm diagram) {
//		for (Tab tab : data.tabPane.getTabs()) {
//			FeatureDiagramm tabDiagram = ((FMEditorView) tab.getUserData()).getCurrentModel();
//			if (diagram.getUuid().equals(tabDiagram.getUuid())) {
//				return tab;
//			}
//		}
//		return null;
//	}
//	
//	private void selectTab(Tab tab) {
//		data.tabPane.getSelectionModel().select(tab);
//	}
//
//	@Optional
//	@Inject
//	public void addNewTab(@UIEventTopic(FDEventTable.ADD_NEW_TAB) ComponentFeature feature) {
//		FXGraphicalFeature fxGraFeature = getCurrentView().getFXGraphicalFeature(feature);
//		Tab newTab = createNewTab(feature.getName());
//		selectTab(newTab);
//		getCurrentView().loadFeatureDiagram(feature.getFeaturediagramm(), false);
//		getCurrentTab().setText(getCurrentFeatureDiagram().getRoot().getName());
//	}
//	
//	@Optional
//	@Inject
//	public void loadComponentFeatureDiagram(@UIEventTopic(FDEventTable.LOAD_COMPONENTFEATUREDIAGRAM_EVENT) FXGraphicalFeature fxGraFeature) {
//		String filepath = RCPMessageProvider.getFilePathDialog("Load Feature Diagram", CompareST.FEATURE_MODELS);
//		try {
//			FeatureDiagramm featureDiagram = FeatureDiagramSerialiazer.loadFeatureDiagram(filepath);
//			((ComponentFeature) fxGraFeature.getFeature()).setFeaturediagramm(featureDiagram);
//			((ComponentFeature) fxGraFeature.getFeature()).getChildren().clear();
//			List<FXGraphicalFeature> temp = new ArrayList<FXGraphicalFeature>(fxGraFeature.getChildFeatures());
//			for(FXGraphicalFeature child: temp) {
//				getCurrentView().removeFeature(child, false, false, false);
//			};
//			fxGraFeature.setName(featureDiagram.getRoot().getName());
//			System.out.println("Feature Diagram " + featureDiagram.getRoot().getName() + " successfully loaded!");
//			// showConstraintView();
//		} catch (IllegalArgumentException e) {
//			System.out.println(e.getMessage());
//		}
//	}
//	
//	/**
//	 * returns the current shown diagram
//	 * @return
//	 */
//	public FeatureDiagramm getCurrentFeatureDiagram() {
//		return getCurrentView().getCurrentModel();
//	}
//	
//	@Optional
//	@Inject
//	public void componentFeatureVariabilityChangeMandatory(@UIEventTopic(FDEventTable.ROOT_FEATURE_MANDATORY_EVENT) String uuid) {
//		data.tabPane.getTabs().forEach(tab -> {
//			List<FXGraphicalFeature> componentList = ((FMEditorView) tab.getUserData()).getComponentFeatureList();
//			for (FXGraphicalFeature graFeature : componentList) {
//				ComponentFeature component = (ComponentFeature) graFeature.getFeature();
//				if (component.getFeaturediagramm().getUuid().equals(uuid)) {
//					graFeature.setMandatory();
//				}
//			}
//		});
//	}
//	
//	@Optional
//	@Inject
//	public void componentFeatureVariabilityChangeOptional(@UIEventTopic(FDEventTable.ROOT_FEATURE_OPTIONAL_EVENT) String uuid) {
//		data.tabPane.getTabs().forEach(tab -> {
//			List<FXGraphicalFeature> componentList = ((FMEditorView) tab.getUserData()).getComponentFeatureList();
//			for (FXGraphicalFeature graFeature : componentList) {
//				ComponentFeature component = (ComponentFeature) graFeature.getFeature();
//				if (component.getFeaturediagramm().getUuid().equals(uuid)) {
//					graFeature.setOptional();
//				}
//			}
//		});
//	}
//	
//	@Optional
//	@Inject 
//	public void registerErrorListener(@UIEventTopic(FDEventTable.REGISTER_ERR_LISTENER) ErrorListener errorListener) {
//		if (!data.errorListeners.contains(errorListener)) {
//			data.errorListeners.add(errorListener);
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void deregisterErrorListener(@UIEventTopic(FDEventTable.DEREGISTER_ERR_LISTENER) ErrorListener errorListener) {
//		data.errorListeners.remove(errorListener);
//	}
//	
//	@Optional
//	@Inject
//	public void addCompoundFeatureBelow(@UIEventTopic(FDEventTable.ADD_COMPONENTFEATURE) Pair<String, FXGraphicalFeature> pair) {
//		try {
//			getCurrentView().addFeatureBelow(pair);
////			getCurrentView().addComponentFeatureBelow(parentFeature);
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(pair.getValue(), FDEventTable.ADD_COMPONENTFEATURE, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void addFeature(@UIEventTopic(FDEventTable.ADD_FEATURE_EVENT) Feature feature) {
//		try {
//			getCurrentView().addFeatureToMousePosition(feature);
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.ADD_FEATURE_EVENT, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void addFeatureBelow(@UIEventTopic(FDEventTable.ADD_FEATURE_BELOW) Pair<String, FXGraphicalFeature> pair) {
//		try {
//			getCurrentView().addFeatureBelow(pair);
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(pair.getValue(), FDEventTable.ADD_FEATURE_BELOW, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void addFeatureAbove(@UIEventTopic(FDEventTable.ADD_FEATURE_ABOVE) FXGraphicalFeature feature) {
//		try {
//			getCurrentView().addFeatureAbove(feature);
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.ADD_FEATURE_ABOVE, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void removeFeature(@UIEventTopic(FDEventTable.REMOVE_FEATURE) FXGraphicalFeature feature) {
//		try {
//			getCurrentView().removeFeature(feature, true, false, true);
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.REMOVE_FEATURE, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void removeFeatureTrunk(@UIEventTopic(FDEventTable.REMOVE_FEATURE_TRUNK) FXGraphicalFeature feature) {
//		try {
//			getCurrentView().removeFeatureTrunk(feature);
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.REMOVE_FEATURE_TRUNK, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//
//	@Optional
//	@Inject 
//	public void removeFeatureByLogger(@UIEventTopic(FDEventTable.REMOVE_FEATURE_BY_LOGGER) FXGraphicalFeature feature) {
//		try {
//			getCurrentView().removeFeature(feature, false, false, false);
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.REMOVE_FEATURE_BY_LOGGER, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void setFeatureAndGroup(@UIEventTopic(FDEventTable.SET_FEATURE_AND_GROUP_BY_LOGGER) FXGraphicalFeature feature) {
//		try {
//			getCurrentView().setFeatureAndGroup(feature);
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_FEATURE_AND_GROUP_BY_LOGGER, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void setFeatureAlternativeGroup(@UIEventTopic(FDEventTable.SET_FEATURE_ALTERNATIVE_GROUP_BY_LOGGER) FXGraphicalFeature feature) {
//		try {
//			getCurrentView().setFeatureAlternativeGroup(feature);
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_FEATURE_ALTERNATIVE_GROUP_BY_LOGGER, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void setFeatureOrGroup(@UIEventTopic(FDEventTable.SET_FEATURE_OR_GROUP_BY_LOGGER) FXGraphicalFeature feature) {
//		try {	
//			getCurrentView().setFeatureOrGroup(feature);
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_FEATURE_OR_GROUP_BY_LOGGER, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void setCurrentFeature(@UIEventTopic(FDEventTable.SET_CURRENT_FEATURE) FXGraphicalFeature feature) {
//		try {
//			getCurrentView().setCurrentFeature(feature);						
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_CURRENT_FEATURE, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void setFeatureName(@UIEventTopic(FDEventTable.SET_FEATURE_NAME) Pair<FXGraphicalFeature, String> data) {
//		try {
//			data.getKey().setName(data.getValue());
//			if (data.getKey().getFeature() == getCurrentView().getFeatureDiagram().getRoot()) {
//				getCurrentTab().setText(data.getValue());
//			}
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(getCurrentView().getCurrentFeature(), FDEventTable.SET_FEATURE_NAME, e.getMessage());
//			this.data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void setConfigurationFeatureName(@UIEventTopic(FDEventTable.EVENT_RENAME_CONFIGURATIONFEATURE) Pair<String, String> name) {
//		try {
//			String oldName = name.getKey();
//			getCurrentView().getComponentFeatureList().forEach(componentFeature -> {
//				componentFeature.getChildFeatures().forEach(configurationFeature -> {
//					if (configurationFeature.getFeatureNameLabel().getText().equals(oldName)) {
//						configurationFeature.setName(name.getValue());
//						return;
//					}
//				});
//			});
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(getCurrentView().getCurrentFeature(), FDEventTable.EVENT_RENAME_CONFIGURATIONFEATURE, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void setConfiguration(@UIEventTopic(FDEventTable.SELECT_CONFIGURATION_EVENT) FXGraphicalFeature fxGraFeature) {
//		try {
//			FeatureDiagramm fd = ((ComponentFeature) fxGraFeature.getFeature()).getFeaturediagramm();
//        	FMESetConfigurationDialog dialog = new FMESetConfigurationDialog("Select Configuration", ((ComponentFeature) fxGraFeature.getFeature()));
//        	Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
//			Double x = primaryScreenBounds.getWidth() * .5 - dialog.getDialog().getWidth() * .5;
//			Double y = primaryScreenBounds.getHeight() * .5 - dialog.getDialog().getHeight() * .5;
//        	List<FeatureConfiguration> selectedConfig = dialog.show(x, y);
//        	
//			if (selectedConfig != null) {
//				selectedConfig.forEach(config -> {
//					boolean contained = !fxGraFeature.getChildFeatures().stream()
//							.map(FXGraphicalFeature::getFeature)
//							.map(ConfigurationFeature.class::cast)
//							.map(ConfigurationFeature::getConfigurationfeature)
//							.collect(Collectors.toList())
//							.contains(config);
//					if (contained) {
//						getCurrentView().addConfigurationBelow(new Pair<FeatureConfiguration, FXGraphicalFeature>(config, fxGraFeature));
//					} else {
//						System.out.println("ComponentFeature already contains " +  config.getName());
//					}
//				});
//			} 
//        	
//        	
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(getCurrentView().getCurrentFeature(), FDEventTable.SET_FEATURE_NAME, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void setFeatureAbstract(@UIEventTopic(FDEventTable.SET_FEATURE_ABSTRACT) FXGraphicalFeature feature) {
//		try {
//			getCurrentView().setFeatureAbstract(feature);
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_FEATURE_ABSTRACT, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void setFeatureOptional(@UIEventTopic(FDEventTable.SET_FEATURE_OPTIONAL) FXGraphicalFeature feature) {
//		try {
//			getCurrentView().setFeatureOptional(feature);
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_FEATURE_OPTIONAL, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void setFeatureMandatory(@UIEventTopic(FDEventTable.SET_FEATURE_MANDATORY) FXGraphicalFeature feature) {
//		try {
//			getCurrentView().setFeatureMandatory(feature);
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_FEATURE_MANDATORY, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	
//	@Optional
//	@Inject 
//	public void changeSubfeaturesVisibility(@UIEventTopic(FDEventTable.CHANGE_SUBFEATURES_VISIBILITY) FXGraphicalFeature feature) {
//		try {
//			getCurrentView().changeSubfeaturesVisibility(feature);
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.CHANGE_SUBFEATURES_VISIBILITY, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	 
//	@Optional
//	@Inject 
//	public void saveFeatureDiagram(@UIEventTopic(FDEventTable.SAVE_FEATURE_DIAGRAM) String path) {
//		try {
//			getCurrentView().saveFeatureDiagram();
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.SAVE_FEATURE_DIAGRAM, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void loadFeatureDiagramFromFile(@UIEventTopic(FDEventTable.LOAD_FEATURE_DIAGRAM_FROM_FILE) String filepath) {
//		FeatureDiagramm featureDiagram;
//		if (filepath.equals("")) {
//			filepath = RCPMessageProvider.getFilePathDialog("Load Feature Diagram", CompareST.FEATURE_MODELS);
//			if (filepath.equals("")) { return; }
//		}
//		try {	
//			featureDiagram = FeatureDiagramSerialiazer.loadFeatureDiagram(filepath);
//		} catch (IllegalArgumentException e) {
//			System.out.println(e.getMessage());
//			return;
//		}
//		loadFeatureDiagram(featureDiagram);
//	}
//	
//	@Optional
//	@Inject 
//	public void loadFeatureDiagram(@UIEventTopic(FDEventTable.LOAD_FEATURE_DIAGRAM) FeatureDiagramm featureDiagram) {
//		Tab tab = getOpenedTabOrNull(featureDiagram);
//		if (tab == null) {
//			tab = createNewTab(featureDiagram.getRoot().getName());
//			selectTab(tab);
//			getCurrentView().loadFeatureDiagram(featureDiagram, false);
//		} else {
//			selectTab(tab);
//		}
//		
//		System.out.println("Feature Diagram " + featureDiagram.getRoot().getName() + " successfully loaded.");
//		//showConstraintView();
//	}
//	
//	private void showConstraintView() {
//		data.services.partService.showPart(FDStringTable.CONSTRAINT_VIEW);
//		data.services.eventBroker.send(FDEventTable.SHOW_CONSTRAINT_EVENT, "");
//	}
//	
//	@Optional
//	@Inject 
//	public void formatFeatureDiagram(@UIEventTopic(FDEventTable.FORMAT_DIAGRAM_EVENT) String text) {
//		try {
//			getCurrentView().formatDiagram(false);			
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.FORMAT_DIAGRAM_EVENT, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void startLoggerForFeatureDiagram(@UIEventTopic(FDEventTable.START_LOGGER_DIAGRAM_EVENT) boolean startOrStop) {
//		try {
//			getCurrentView().logDiagramChanges(startOrStop);
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.START_LOGGER_DIAGRAM_EVENT, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void replayModificationSetOnFeatureDiagram(@UIEventTopic(FDEventTable.REPLAY_FD_MODIFICATION_SET) FeatureModelModificationSet modificationSet) {
//		try {
//			getCurrentView().replayModificationSet(modificationSet);
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.REPLAY_FD_MODIFICATION_SET, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void createNewDiagram(@UIEventTopic(FDEventTable.NEW_FEATURE_DIAGRAM) String text) {
//		try {
//			getCurrentView().askToSave();
//			getCurrentView().clearAll();
//			getCurrentView().createNewFeatureDiagram();
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.NEW_FEATURE_DIAGRAM, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//
//	@Optional
//	@Inject 
//	public void setTheme(@UIEventTopic(FDEventTable.SET_FDE_THEME) String cssLocation) {
//		try {
//			getCurrentView().setTheme(cssLocation);
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.SET_FDE_THEME, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void setSelectedFeature(@UIEventTopic(FDEventTable.SET_SELECTED_FEATURE) FXGraphicalFeature feature) {
//		try {
//			getCurrentView().setSelectedFeature(feature);
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_SELECTED_FEATURE, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void setSelectedFeature(@UIEventTopic(FDEventTable.SET_SELECTED_FEATURE) Feature feature) {
//		try {
//			for (FXGraphicalFeature fxGraphicalFeature : getCurrentView().getFeatureList()) {
//				if (fxGraphicalFeature.getFeature() == feature) {
//					getCurrentView().setSelectedFeature(fxGraphicalFeature);					
//				}
//			}
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_SELECTED_FEATURE, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void resetSelectedFeatures(@UIEventTopic(FDEventTable.RESET_SELECTED_FEATURES) String origin) {
//		try {
//			getCurrentView().resetSelectedFeatures();
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.RESET_SELECTED_FEATURES, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void groupSelectedFeatures(@UIEventTopic(FDEventTable.GROUP_SELECTED_FEATURES_IN_FEATURE) FXGraphicalFeature feature) {
//		try {
//			getCurrentView().fuseSelectedFeatures(feature);
//			getCurrentView().resetSelectedFeatures();		
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.GROUP_SELECTED_FEATURES_IN_FEATURE, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void moveSelectedFeaturesUnderFeature(@UIEventTopic(FDEventTable.MOVE_SELECTED_FEATURES_UNDER_FEATURE) FXGraphicalFeature feature) {
//		try {
//			getCurrentView().moveSelectedFeaturesUnderFeature(feature);
//			getCurrentView().resetSelectedFeatures();
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.MOVE_SELECTED_FEATURES_UNDER_FEATURE, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void splitFeature(@UIEventTopic(FDEventTable.SPLIT_FEATURE) FXGraphicalFeature feature) {
//		try {
//			getCurrentView().splitFeature(feature);
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SPLIT_FEATURE, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//	
//	@Optional
//	@Inject 
//	public void setFeatureDescription(@UIEventTopic(FDEventTable.SET_DESCRIPTION) FXGraphicalFeature graphicFeature) {
//		try {
//			Feature feature = graphicFeature.getFeature();
//			FMESimpleTextInputDialog inputDialog = new FMESimpleTextInputDialog("Set Feature Description", "");
//			String description = inputDialog.show(graphicFeature.getXPos().doubleValue(), graphicFeature.getYPos().doubleValue());
//			if (description != null && !description.trim().isEmpty()) {
//				feature.setDescription(description);
//			}
//		} catch (Exception e) {
//			FeatureModelViewError error = new FeatureModelViewError(graphicFeature, FDEventTable.SET_DESCRIPTION, e.getMessage());
//			data.errorListeners.forEach(listener -> listener.onError(error));
//		}
//	}
//
//	@Optional
//	@Inject 
//	public void sendAllFeature(@UIEventTopic(FDEventTable.SEND_ALL_FEATURE) String receiver) {
//		data.services.eventBroker.send(FDEventTable.RECEIVE_ALL_FEATURE, getCurrentView().getCurrentModel());
//	}
//	
//	@Optional
//	@Inject 
//	public void createFeatureModelFromTreeView(@UIEventTopic(FDEventTable.CREATE_FEATUREMODEL_FROM_TREEVIEW) Tree tree) {
//		FeatureDiagramm parsedModel = TreeParser.parse(tree);
//		loadFeatureDiagram(parsedModel);
//		data.services.eventBroker.send(FDEventTable.FORMAT_DIAGRAM_EVENT, "");
//	}
//	
//	@Optional
//	@Inject
//	public void createFeatureDiagramFromSyntaxGroups(@UIEventTopic(EventTable.PUBLISH_SYNTAX_GROUPS) List<SyntaxGroup> groups) {
//		getCurrentView().loadSyntaxGroups(groups);
//		return;
//	}

}