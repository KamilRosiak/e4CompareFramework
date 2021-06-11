package de.tu_bs.cs.isf.e4cf.featuremodel.core;
 
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import FeatureDiagram.ComponentFeature;
import FeatureDiagram.Feature;
import FeatureDiagramModificationSet.FeatureModelModificationSet;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.error.ErrorListener;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.error.ErrorListener.FeatureModelViewError;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.FeatureDiagramSerialiazer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.dialogs.FMESimpleTextInputDialog;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.FeatureModelEditorView;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.elements.FXGraphicalFeature;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.util.Pair;

/**
 * This class represents the controller of a MVC implementation. It handles events from FDEventTable.
 * @author {Kamil Rosiak}
 *
 */
@Singleton
@Creatable
public class FeatureModelEditorController {
	private TabPane tabPane;
	private ServiceContainer services;
	
	private List<ErrorListener> errorListeners;
	
	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services, EMenuService menuService) {
		FXCanvas canvas = new FXCanvas(parent, SWT.NONE);
		tabPane = new TabPane();
		canvas.setScene(new Scene(tabPane));


		menuService.registerContextMenu(canvas, "de.tu_bs.cs.isf.e4cf.featuremodel.core.featureModelMenü");
		this.services = services;
		this.errorListeners = new ArrayList<>();

		createNewTab(FDStringTable.FD_DEFAULT_FEATURE_DIAGRAM_NAME);
	}

	private FeatureModelEditorView getCurrentView() {
		Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
		if (currentTab == null) {
			return (FeatureModelEditorView) createNewTab(FDStringTable.FD_DEFAULT_FEATURE_DIAGRAM_NAME).getUserData();
		}
		FeatureModelEditorView view = (FeatureModelEditorView) currentTab.getUserData();
		return view;
	}
	
	private Tab getCurrentTab() {
		return tabPane.getSelectionModel().getSelectedItem();
	}

	private Tab createNewTab(String title) {
		Tab tab = newTab(title);
		FeatureModelEditorView view = new FeatureModelEditorView(tab, services);
		tab.setUserData(view);
		tabPane.getTabs().add(tab);
		return tab;
	}

	private Tab newTab(String title) {
		Tab tab = new Tab(title);
		tab.setOnCloseRequest(event -> {
			((FeatureModelEditorView) tab.getUserData()).askToSave();		
			// don't allow the tabPane to be empty
			if (tabPane.getTabs().size() == 1) {
				createNewTab(FDStringTable.FD_DEFAULT_FEATURE_DIAGRAM_NAME);
			}
		});
		return tab;
	}
	
	@Optional
	@Inject
	public void openFeatureDiagram(@UIEventTopic(FDEventTable.OPEN_FEATURE_DIAGRAM) ComponentFeature feature) {
		FeatureDiagram diagram = (FeatureDiagram) feature.getFeaturediagramm();
		Tab openedTab = getOpenedTabOrNull(diagram);
		
		if (openedTab != null) {
			selectTab(openedTab);
		} else {
			services.eventBroker.send(FDEventTable.ADD_NEW_TAB, feature);
		}
		
	}
	
	private Tab getOpenedTabOrNull(FeatureDiagram diagram) {
		for (Tab tab : tabPane.getTabs()) {
			FeatureDiagram tabDiagram = ((FeatureModelEditorView) tab.getUserData()).getCurrentModel();
			if (diagram == tabDiagram) {
				return tab;
			}
		}
		return null;
	}
	
	private void selectTab(Tab tab) {
		tabPane.getSelectionModel().select(tab);
	}
	
	
	@Optional
	@Inject
	public void changeTab(@UIEventTopic(FDEventTable.CHANGE_TAB_EVENT) ComponentFeature feature) {
		FeatureDiagram diagram = (FeatureDiagram) (feature.getFeaturediagramm());
		for (Tab tab : tabPane.getTabs()) {
			
		}
	}

	@Optional
	@Inject
	public void addNewTab(@UIEventTopic(FDEventTable.ADD_NEW_TAB) ComponentFeature feature) {
		FXGraphicalFeature fxGraFeature = getCurrentView().getFXGraphicalFeature(feature);
		Tab newTab = createNewTab(feature.getName());
		selectTab(newTab);
		if (feature.getFeaturediagramm() == null) {
			feature.setFeaturediagramm(getCurrentFeatureDiagram());
			getCurrentView().getFeatureList().get(0).rename(feature.getName());
		}
		getCurrentView().loadFeatureDiagram((FeatureDiagram) feature.getFeaturediagramm(), false);
		getCurrentTab().setText(getCurrentFeatureDiagram().getRoot().getName());
		
		if (getCurrentFeatureDiagram().getRoot().isMandatory()) {
			fxGraFeature.setMandatory();
		}
	}

	
	/**
	 * returns the current shown diagram
	 * @return
	 */
	public FeatureDiagram getCurrentFeatureDiagram() {
		return getCurrentView().getCurrentModel();
	}
	
	@Optional
	@Inject
	public void componentFeatureVariabilityChangeMandatory(@UIEventTopic(FDEventTable.ROOT_FEATURE_MANDATORY_EVENT) String uuid) {
		tabPane.getTabs().forEach(tab -> {
			List<FXGraphicalFeature> componentList = ((FeatureModelEditorView) tab.getUserData()).getComponentFeatureList();
			for (FXGraphicalFeature graFeature : componentList) {
				ComponentFeature component = (ComponentFeature) graFeature.getFeature();
				if (component.getFeaturediagramm().getUuid().equals(uuid)) {
					graFeature.setMandatory();
				}
			}
		});
	}
	
	@Optional
	@Inject
	public void componentFeatureVariabilityChangeOptional(@UIEventTopic(FDEventTable.ROOT_FEATURE_OPTIONAL_EVENT) String uuid) {
		tabPane.getTabs().forEach(tab -> {
			List<FXGraphicalFeature> componentList = ((FeatureModelEditorView) tab.getUserData()).getComponentFeatureList();
			for (FXGraphicalFeature graFeature : componentList) {
				ComponentFeature component = (ComponentFeature) graFeature.getFeature();
				if (component.getFeaturediagramm().getUuid().equals(uuid)) {
					graFeature.setOptional();
				}
			}
		});
	}
	
	@Optional
	@Inject 
	public void registerErrorListener(@UIEventTopic(FDEventTable.REGISTER_ERR_LISTENER) ErrorListener errorListener) {
		if (!errorListeners.contains(errorListener)) {
			errorListeners.add(errorListener);
		}
	}
	
	@Optional
	@Inject 
	public void deregisterErrorListener(@UIEventTopic(FDEventTable.DEREGISTER_ERR_LISTENER) ErrorListener errorListener) {
		errorListeners.remove(errorListener);
	}
	
	@Optional
	@Inject
	public void addCompoundFeatureBelow(@UIEventTopic(FDEventTable.ADD_COMPONENTFEATURE) FXGraphicalFeature parentFeature) {
		try {
			getCurrentView().addComponentFeatureBelow(parentFeature);
			
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(parentFeature, FDEventTable.ADD_COMPONENTFEATURE, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void addFeature(@UIEventTopic(FDEventTable.ADD_FEATURE_EVENT) Feature feature) {
		try {
			getCurrentView().addFeatureToMousePosition(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.ADD_FEATURE_EVENT, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void addFeatureBelow(@UIEventTopic(FDEventTable.ADD_FEATURE_BELOW) FXGraphicalFeature parentFeature) {
		try {
			getCurrentView().addFeatureBelow(parentFeature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(parentFeature, FDEventTable.ADD_FEATURE_BELOW, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void addFeatureAbove(@UIEventTopic(FDEventTable.ADD_FEATURE_ABOVE) FXGraphicalFeature feature) {
		try {
			getCurrentView().addFeatureAbove(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.ADD_FEATURE_ABOVE, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void removeFeature(@UIEventTopic(FDEventTable.REMOVE_FEATURE) FXGraphicalFeature feature) {
		try {
			getCurrentView().removeFeature(feature, true, false, true);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.REMOVE_FEATURE, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void removeFeatureTrunk(@UIEventTopic(FDEventTable.REMOVE_FEATURE_TRUNK) FXGraphicalFeature feature) {
		try {
			getCurrentView().removeFeatureTrunk(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.REMOVE_FEATURE_TRUNK, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}

	@Optional
	@Inject 
	public void removeFeatureByLogger(@UIEventTopic(FDEventTable.REMOVE_FEATURE_BY_LOGGER) FXGraphicalFeature feature) {
		try {
			getCurrentView().removeFeature(feature, false, false, false);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.REMOVE_FEATURE_BY_LOGGER, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void setFeatureAndGroup(@UIEventTopic(FDEventTable.SET_FEATURE_AND_GROUP_BY_LOGGER) FXGraphicalFeature feature) {
		try {
			getCurrentView().setFeatureAndGroup(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_FEATURE_AND_GROUP_BY_LOGGER, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void setFeatureAlternativeGroup(@UIEventTopic(FDEventTable.SET_FEATURE_ALTERNATIVE_GROUP_BY_LOGGER) FXGraphicalFeature feature) {
		try {
			getCurrentView().setFeatureAlternativeGroup(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_FEATURE_ALTERNATIVE_GROUP_BY_LOGGER, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void setFeatureOrGroup(@UIEventTopic(FDEventTable.SET_FEATURE_OR_GROUP_BY_LOGGER) FXGraphicalFeature feature) {
		try {	
			getCurrentView().setFeatureOrGroup(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_FEATURE_OR_GROUP_BY_LOGGER, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void setCurrentFeature(@UIEventTopic(FDEventTable.SET_CURRENT_FEATURE) FXGraphicalFeature feature) {
		try {
			getCurrentView().setCurrentFeature(feature);						
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_CURRENT_FEATURE, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void setFeatureName(@UIEventTopic(FDEventTable.SET_FEATURE_NAME) Pair<FXGraphicalFeature, String> data) {
		try {
			data.getKey().rename(data.getValue());
			if (data.getKey().getFeature() == getCurrentView().getFeatureDiagram().getRoot()) {
				getCurrentTab().setText(data.getValue());
			}
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(getCurrentView().getCurrentFeature(), FDEventTable.SET_FEATURE_NAME, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void setFeatureAbstract(@UIEventTopic(FDEventTable.SET_FEATURE_ABSTRACT) FXGraphicalFeature feature) {
		try {
			getCurrentView().setFeatureAbstract(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_FEATURE_ABSTRACT, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void setFeatureOptional(@UIEventTopic(FDEventTable.SET_FEATURE_OPTIONAL) FXGraphicalFeature feature) {
		try {
			getCurrentView().setFeatureOptional(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_FEATURE_OPTIONAL, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void setFeatureMandatory(@UIEventTopic(FDEventTable.SET_FEATURE_MANDATORY) FXGraphicalFeature feature) {
		try {
			getCurrentView().setFeatureMandatory(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_FEATURE_MANDATORY, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	
	@Optional
	@Inject 
	public void changeSubfeaturesVisibility(@UIEventTopic(FDEventTable.CHANGE_SUBFEATURES_VISIBILITY) FXGraphicalFeature feature) {
		try {
			getCurrentView().changeSubfeaturesVisibility(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.CHANGE_SUBFEATURES_VISIBILITY, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	 
	@Optional
	@Inject 
	public void saveFeatureDiagram(@UIEventTopic(FDEventTable.SAVE_FEATURE_DIAGRAM) String path) {
		try {
			getCurrentView().saveFeatureDiagram();
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.SAVE_FEATURE_DIAGRAM, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void loadFeatureDiagramFromFile(@UIEventTopic(FDEventTable.LOAD_FEATURE_DIAGRAM_FROM_FILE) FileTreeElement file) {
		try {
			FeatureDiagram featureDiagram = (FeatureDiagram) FeatureDiagramSerialiazer.load(file.getAbsolutePath());
			Tab tab = getOpenedTabOrNull(featureDiagram);
			if ( tab == null) {
				tab = createNewTab(file.getFileName());
			}
			FeatureModelEditorView view = (FeatureModelEditorView) tab.getUserData();
			view.loadFeatureDiagram(featureDiagram, false);
			selectTab(tab);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.LOAD_FEATURE_DIAGRAM_FROM_FILE, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
		showConstraintView();
	}
	
	@Optional
	@Inject 
	public void loadFeatureDiagram(@UIEventTopic(FDEventTable.LOAD_FEATURE_DIAGRAM) FeatureDiagram featureDiagram) {
		try {
			Tab tab = getOpenedTabOrNull(featureDiagram);
			if ( tab == null) {
				tab = createNewTab(FDStringTable.FD_DEFAULT_FEATURE_DIAGRAM_NAME);
			}
			FeatureModelEditorView view = (FeatureModelEditorView) tab.getUserData();
			view.loadFeatureDiagram(featureDiagram, false);
			selectTab(tab);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.LOAD_FEATURE_DIAGRAM, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
		showConstraintView();
	}
	
	private void showConstraintView() {
		services.partService.showPart(FDStringTable.CONSTRAINT_VIEW);
		services.eventBroker.send(FDEventTable.SHOW_CONSTRAINT_EVENT, "");
	}
	
	@Optional
	@Inject 
	public void formatFeatureDiagram(@UIEventTopic(FDEventTable.FORMAT_DIAGRAM_EVENT) String text) {
		try {
			getCurrentView().formatDiagram(false);			
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.FORMAT_DIAGRAM_EVENT, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void startLoggerForFeatureDiagram(@UIEventTopic(FDEventTable.START_LOGGER_DIAGRAM_EVENT) boolean startOrStop) {
		try {
			getCurrentView().logDiagramChanges(startOrStop);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.START_LOGGER_DIAGRAM_EVENT, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void replayModificationSetOnFeatureDiagram(@UIEventTopic(FDEventTable.REPLAY_FD_MODIFICATION_SET) FeatureModelModificationSet modificationSet) {
		try {
			getCurrentView().replayModificationSet(modificationSet);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.REPLAY_FD_MODIFICATION_SET, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void createNewDiagram(@UIEventTopic(FDEventTable.NEW_FEATURE_DIAGRAM) String text) {
		try {
			getCurrentView().askToSave();
			getCurrentView().clearAll();
			getCurrentView().createNewFeatureDiagram();
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.NEW_FEATURE_DIAGRAM, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}

	@Optional
	@Inject 
	public void setTheme(@UIEventTopic(FDEventTable.SET_FDE_THEME) String cssLocation) {
		try {
			getCurrentView().setTheme(cssLocation);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.SET_FDE_THEME, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void setSelectedFeature(@UIEventTopic(FDEventTable.SET_SELECTED_FEATURE) FXGraphicalFeature feature) {
		try {
			getCurrentView().setSelectedFeature(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_SELECTED_FEATURE, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void setSelectedFeature(@UIEventTopic(FDEventTable.SET_SELECTED_FEATURE) Feature feature) {
		try {
			for (FXGraphicalFeature fxGraphicalFeature : getCurrentView().getFeatureList()) {
				if (fxGraphicalFeature.getFeature() == feature) {
					getCurrentView().setSelectedFeature(fxGraphicalFeature);					
				}
			}
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_SELECTED_FEATURE, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void resetSelectedFeatures(@UIEventTopic(FDEventTable.RESET_SELECTED_FEATURES) String origin) {
		try {
			getCurrentView().resetSelectedFeatures();
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.RESET_SELECTED_FEATURES, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void groupSelectedFeatures(@UIEventTopic(FDEventTable.GROUP_SELECTED_FEATURES_IN_FEATURE) FXGraphicalFeature feature) {
		try {
			getCurrentView().fuseSelectedFeatures(feature);
			getCurrentView().resetSelectedFeatures();		
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.GROUP_SELECTED_FEATURES_IN_FEATURE, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void moveSelectedFeaturesUnderFeature(@UIEventTopic(FDEventTable.MOVE_SELECTED_FEATURES_UNDER_FEATURE) FXGraphicalFeature feature) {
		try {
			getCurrentView().moveSelectedFeaturesUnderFeature(feature);
			getCurrentView().resetSelectedFeatures();
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.MOVE_SELECTED_FEATURES_UNDER_FEATURE, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void splitFeature(@UIEventTopic(FDEventTable.SPLIT_FEATURE) FXGraphicalFeature feature) {
		try {
			getCurrentView().splitFeature(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SPLIT_FEATURE, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void setFeatureDescription(@UIEventTopic(FDEventTable.SET_DESCRIPTION) FXGraphicalFeature graphicFeature) {
		try {
			Feature feature = graphicFeature.getFeature();
			FMESimpleTextInputDialog inputDialog = new FMESimpleTextInputDialog("Set Feature Description", "");
			String description = inputDialog.show(graphicFeature.getXPos().doubleValue(), graphicFeature.getYPos().doubleValue());
			if (description != null && !description.trim().isEmpty()) {
				feature.setDescription(description);
			}
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(graphicFeature, FDEventTable.SET_DESCRIPTION, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}

	@Optional
	@Inject 
	public void sendAllFeature(@UIEventTopic(FDEventTable.SEND_ALL_FEATURE) String receiver) {
		services.eventBroker.send(FDEventTable.RECEIVE_ALL_FEATURE, getCurrentView().getCurrentModel());
	}

}