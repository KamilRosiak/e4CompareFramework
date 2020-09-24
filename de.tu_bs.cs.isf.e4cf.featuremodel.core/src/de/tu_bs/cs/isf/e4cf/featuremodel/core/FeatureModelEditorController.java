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

import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramm;
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

/**
 * This class represents the controller of a MVC implementation. It handles events from FDEventTable.
 * @author {Kamil Rosiak}
 *
 */
@Singleton
@Creatable
public class FeatureModelEditorController {
	private FeatureModelEditorView view;
	private ServiceContainer services;
	
	private List<ErrorListener> errorListeners;
	
	@PostConstruct
	public void postConstruct(Composite parent, ServiceContainer services, EMenuService menuService) {
		
		FXCanvas canvas = new FXCanvas(parent, SWT.NONE);
		menuService.registerContextMenu(canvas, "de.tu_bs.cs.isf.e4cf.featuremodel.core.featureModelMenü");
		this.services = services;
		this.view = new FeatureModelEditorView(canvas, services);
		this.errorListeners = new ArrayList<>();
	}
	
	/**
	 * returns the current shown diagram
	 * @return
	 */
	public FeatureDiagramm getCurrentFeatureDiagram() {
		return view.getCurrentModel();
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
	public void addFeature(@UIEventTopic(FDEventTable.ADD_FEATURE_EVENT) Feature feature) {
		try {
			view.addFeatureToMousePosition(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.ADD_FEATURE_EVENT, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void addFeatureBelow(@UIEventTopic(FDEventTable.ADD_FEATURE_BELOW) FXGraphicalFeature parentFeature) {
		try {
			view.addFeatureBelow(parentFeature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(parentFeature, FDEventTable.ADD_FEATURE_BELOW, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void addFeatureAbove(@UIEventTopic(FDEventTable.ADD_FEATURE_ABOVE) FXGraphicalFeature feature) {
		try {
			view.addFeatureAbove(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.ADD_FEATURE_ABOVE, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void removeFeature(@UIEventTopic(FDEventTable.REMOVE_FEATURE) FXGraphicalFeature feature) {
		try {
			view.removeFeature(feature, true, false, true);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.REMOVE_FEATURE, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void removeFeatureTrunk(@UIEventTopic(FDEventTable.REMOVE_FEATURE_TRUNK) FXGraphicalFeature feature) {
		try {
			view.removeFeatureTrunk(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.REMOVE_FEATURE_TRUNK, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}

	@Optional
	@Inject 
	public void removeFeatureByLogger(@UIEventTopic(FDEventTable.REMOVE_FEATURE_BY_LOGGER) FXGraphicalFeature feature) {
		try {
			view.removeFeature(feature, false, false, false);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.REMOVE_FEATURE_BY_LOGGER, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void setFeatureAndGroup(@UIEventTopic(FDEventTable.SET_FEATURE_AND_GROUP_BY_LOGGER) FXGraphicalFeature feature) {
		try {
			view.setFeatureAndGroup(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_FEATURE_AND_GROUP_BY_LOGGER, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void setFeatureAlternativeGroup(@UIEventTopic(FDEventTable.SET_FEATURE_ALTERNATIVE_GROUP_BY_LOGGER) FXGraphicalFeature feature) {
		try {
			view.setFeatureAlternativeGroup(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_FEATURE_ALTERNATIVE_GROUP_BY_LOGGER, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void setFeatureOrGroup(@UIEventTopic(FDEventTable.SET_FEATURE_OR_GROUP_BY_LOGGER) FXGraphicalFeature feature) {
		try {	
			view.setFeatureOrGroup(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_FEATURE_OR_GROUP_BY_LOGGER, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void setCurrentFeature(@UIEventTopic(FDEventTable.SET_CURRENT_FEATURE) FXGraphicalFeature feature) {
		try {
			view.setCurrentFeature(feature);						
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_CURRENT_FEATURE, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void setFeatureName(@UIEventTopic(FDEventTable.SET_FEATURE_NAME) String name) {
		try {
			view.renameCurrentFeature(name);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(view.getCurrentFeature(), FDEventTable.SET_FEATURE_NAME, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void setFeatureAbstract(@UIEventTopic(FDEventTable.SET_FEATURE_ABSTRACT) FXGraphicalFeature feature) {
		try {
			view.setFeatureAbstract(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_FEATURE_ABSTRACT, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void setFeatureOptional(@UIEventTopic(FDEventTable.SET_FEATURE_OPTIONAL) FXGraphicalFeature feature) {
		try {
			view.setFeatureOptional(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_FEATURE_OPTIONAL, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void setFeatureMandatory(@UIEventTopic(FDEventTable.SET_FEATURE_MANDATORY) FXGraphicalFeature feature) {
		try {
			view.setFeatureMandatory(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_FEATURE_MANDATORY, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void changeSubfeaturesVisibility(@UIEventTopic(FDEventTable.CHANGE_SUBFEATURES_VISIBILITY) FXGraphicalFeature feature) {
		try {
			view.changeSubfeaturesVisibility(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.CHANGE_SUBFEATURES_VISIBILITY, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void saveFeatureDiagram(@UIEventTopic(FDEventTable.SAVE_FEATURE_DIAGRAM) String path) {
		try {
			view.saveFeatureDiagram();
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.SAVE_FEATURE_DIAGRAM, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void loadFeatureDiagramFromFile(@UIEventTopic(FDEventTable.LOAD_FEATURE_DIAGRAM_FROM_FILE) FileTreeElement file) {
		try {
			FeatureDiagramm featureDiagram = FeatureDiagramSerialiazer.load(file.getAbsolutePath());
			view.loadFeatureDiagram(featureDiagram, true);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.LOAD_FEATURE_DIAGRAM_FROM_FILE, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
		showConstraintView();
	}
	
	@Optional
	@Inject 
	public void loadFeatureDiagram(@UIEventTopic(FDEventTable.LOAD_FEATURE_DIAGRAM) FeatureDiagramm featureDiagram) {
		try {
			view.loadFeatureDiagram(featureDiagram, false);
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
			view.formatDiagram(false);			
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.FORMAT_DIAGRAM_EVENT, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void startLoggerForFeatureDiagram(@UIEventTopic(FDEventTable.START_LOGGER_DIAGRAM_EVENT) boolean startOrStop) {
		try {
			view.logDiagramChanges(startOrStop);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.START_LOGGER_DIAGRAM_EVENT, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void replayModificationSetOnFeatureDiagram(@UIEventTopic(FDEventTable.REPLAY_FD_MODIFICATION_SET) FeatureModelModificationSet modificationSet) {
		try {
			view.replayModificationSet(modificationSet);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.REPLAY_FD_MODIFICATION_SET, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void createNewDiagram(@UIEventTopic(FDEventTable.NEW_FEATURE_DIAGRAM) String text) {
		try {
			view.askToSave();
			view.clearAll();
			view.createNewFeatureDiagram();
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.NEW_FEATURE_DIAGRAM, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}

	@Optional
	@Inject 
	public void setTheme(@UIEventTopic(FDEventTable.SET_FDE_THEME) String cssLocation) {
		try {
			view.setTheme(cssLocation);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.SET_FDE_THEME, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void setSelectedFeature(@UIEventTopic(FDEventTable.SET_SELECTED_FEATURE) FXGraphicalFeature feature) {
		try {
			view.setSelectedFeature(feature);
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.SET_SELECTED_FEATURE, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void setSelectedFeature(@UIEventTopic(FDEventTable.SET_SELECTED_FEATURE) Feature feature) {
		try {
			for (FXGraphicalFeature fxGraphicalFeature : view.getFeatureList()) {
				if (fxGraphicalFeature.getFeature() == feature) {
					view.setSelectedFeature(fxGraphicalFeature);					
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
			view.resetSelectedFeatures();
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(FDEventTable.RESET_SELECTED_FEATURES, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void groupSelectedFeatures(@UIEventTopic(FDEventTable.GROUP_SELECTED_FEATURES_IN_FEATURE) FXGraphicalFeature feature) {
		try {
			view.fuseSelectedFeatures(feature);
			view.resetSelectedFeatures();		
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.GROUP_SELECTED_FEATURES_IN_FEATURE, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void moveSelectedFeaturesUnderFeature(@UIEventTopic(FDEventTable.MOVE_SELECTED_FEATURES_UNDER_FEATURE) FXGraphicalFeature feature) {
		try {
			view.moveSelectedFeaturesUnderFeature(feature);
			view.resetSelectedFeatures();
		} catch (Exception e) {
			FeatureModelViewError error = new FeatureModelViewError(feature, FDEventTable.MOVE_SELECTED_FEATURES_UNDER_FEATURE, e.getMessage());
			errorListeners.forEach(listener -> listener.onError(error));
		}
	}
	
	@Optional
	@Inject 
	public void splitFeature(@UIEventTopic(FDEventTable.SPLIT_FEATURE) FXGraphicalFeature feature) {
		try {
			view.splitFeature(feature);
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
		services.eventBroker.send(FDEventTable.RECEIVE_ALL_FEATURE, view.getCurrentModel());
	}

}