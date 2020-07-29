package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.changeLogger.logger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import FeatureDiagram.Feature;
import FeatureDiagram.FeatureDiagramm;
import FeatureDiagramModificationSet.DeltaProperties;
import FeatureDiagramModificationSet.FeatureDiagramModificationSetFactory;
import FeatureDiagramModificationSet.FeatureModelModificationSet;
import FeatureDiagramModificationSet.Modification;
import de.tu_bs.cs.isf.e4cf.core.file_structure.FileTreeElement;
import de.tu_bs.cs.isf.e4cf.core.file_structure.util.FileHandlingUtility;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDStringTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.FeatureDiagramModificationSetSerializer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.changeLogger.DiagramLogger;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.changeLogger.DiagramLoggerFactory;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.changeLogger.utilities.LoggerUtilities;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.elements.FXGraphicalFeature;

@Singleton
@Creatable
public class SimpleDiagramLogger implements DiagramLogger {
	
	private static FeatureModelModificationSet modificationSet;
	
	private static String initialFeatureModelName;
	private static String finalFeatureModelName;
	private static FeatureDiagramm currentDiagram;
	private static String selectedFeatureName;
	private static String selectedFeatureVariabilityGroup;
	private static int currentParentFeatureID;
	private static List<FXGraphicalFeature> currentFeatureSelection;
	
	@Override
	public void startLogging(FeatureDiagramm diagram, List<FXGraphicalFeature> featureList) {
		modificationSet = FeatureDiagramModificationSetFactory.eINSTANCE.createFeatureModelModificationSet();
		initialFeatureModelName = diagram.getRoot().getName();
		currentDiagram = diagram;
		modificationSet.setAffectedFeatureModelName(initialFeatureModelName);
	}

	@Override
	public void stopLogging() {
		exportLogs();
	}

	/**
	 * 
	 * SUPPORTED FUNCTIONS
	 * Add feature below	-	fired in EditorController
	 */
	@Optional
	@Inject 
	public void featureAddedToCurrentSelection(@UIEventTopic(FDEventTable.LOGGER_ADD_FEATURE_TO_SELECTION) FXGraphicalFeature feature) {
		Modification modification = LoggerUtilities.createModification(feature, FDEventTable.LOGGER_ADD_FEATURE_TO_SELECTION);		
		modificationSet.getModifications().add(modification);
		if (currentFeatureSelection == null) {
			currentFeatureSelection = new ArrayList<FXGraphicalFeature>();
		}
		currentFeatureSelection.add(feature);
	}
	
	@Optional
	@Inject 
	public void featureRemovedFromCurrentSelection(@UIEventTopic(FDEventTable.LOGGER_REMOVE_FEATURE_FROM_SELECTION) FXGraphicalFeature feature) {
		Modification modification = LoggerUtilities.createModification(feature, FDEventTable.LOGGER_REMOVE_FEATURE_FROM_SELECTION);		
		modificationSet.getModifications().add(modification);
		currentFeatureSelection.remove(feature);
	}
	
	@Optional
	@Inject 
	public void resetSelectedFeatures(@UIEventTopic(FDEventTable.LOGGER_RESET_SELECTED_FEATURES) String origin) {
		Modification modification = LoggerUtilities.createModification(FDEventTable.LOGGER_RESET_SELECTED_FEATURES);		
		modificationSet.getModifications().add(modification);
		if (currentFeatureSelection != null) {
			currentFeatureSelection.clear();			
		}
	}
	
	@Optional
	@Inject 
	public void moveSelectedFeaturesUnderFeature(@UIEventTopic(FDEventTable.LOGGER_MOVE_SELECTED_FEATURES_UNDER_FEATURE) FXGraphicalFeature feature) {
		Modification modification = LoggerUtilities.createModification(feature, FDEventTable.LOGGER_MOVE_SELECTED_FEATURES_UNDER_FEATURE);		
		modificationSet.getModifications().add(modification);
	}
	
	@Optional
	@Inject 
	public void groupSelectedFeaturesInFeature(@UIEventTopic(FDEventTable.LOGGER_GROUP_SELECTED_FEATURES_IN_FEATURE) FXGraphicalFeature feature) {
		Modification modification = LoggerUtilities.createModification(feature, FDEventTable.LOGGER_GROUP_SELECTED_FEATURES_IN_FEATURE);		
		modificationSet.getModifications().add(modification);
	}
	
	@Optional
	@Inject 
	public void featureAdded(@UIEventTopic(FDEventTable.LOGGER_ADD_FEATURE) FXGraphicalFeature feature) {
		Modification modification = LoggerUtilities.createModification(feature, FDEventTable.LOGGER_ADD_FEATURE);
		modificationSet.getModifications().add(modification);
	}
	
	@Optional
	@Inject 
	public void featureAddedBelow(@UIEventTopic(FDEventTable.LOGGER_ADD_FEATURE_BELOW) FXGraphicalFeature feature) {
		Modification modification = LoggerUtilities.createModification(feature, FDEventTable.LOGGER_ADD_FEATURE_BELOW);
		modificationSet.getModifications().add(modification);
	}

	@Optional
	@Inject 
	public void featureAddedAbove(@UIEventTopic(FDEventTable.LOGGER_ADD_FEATURE_ABOVE) FXGraphicalFeature feature) {
		Modification modification = LoggerUtilities.createModification(feature, FDEventTable.LOGGER_ADD_FEATURE_ABOVE);
		modificationSet.getModifications().add(modification);
	}
	
	@Optional
	@Inject 
	public void featureRemoved(@UIEventTopic(FDEventTable.LOGGER_REMOVE_FEATURE) FXGraphicalFeature feature) {
		Modification modification = LoggerUtilities.createModification(feature, FDEventTable.LOGGER_REMOVE_FEATURE);
		modificationSet.getModifications().add(modification);
	}
	
	@Optional
	@Inject 
	public void featureSelectedToChangeSubfeaturesVisibility(@UIEventTopic(FDEventTable.LOGGER_SELECTED_FEATURE_TO_CHANGE_SUBFEATURES_VISIBILITY) FXGraphicalFeature feature) {
		Modification modification = LoggerUtilities.createModification(feature, FDEventTable.LOGGER_SELECTED_FEATURE_TO_CHANGE_SUBFEATURES_VISIBILITY);		
		modificationSet.getModifications().add(modification);
	}
	
	@Optional
	@Inject 
	public void featureVisibilityChanged(@UIEventTopic(FDEventTable.LOGGER_CHANGED_FEATURE_VISIBILITY) FXGraphicalFeature feature) {
		Modification modification = LoggerUtilities.createModification(feature, FDEventTable.LOGGER_CHANGED_FEATURE_VISIBILITY);
		/*
		 * This event is received AFTER the visbility was changed. 
		 * Hence, a hidden feature was actually "visible" prior to reaching this code block
		 */
		if (!feature.isVisible()) {
			LoggerUtilities.createFeatureDelta(modification, DeltaProperties.FEATURE_VISIBILITY, "visible", "hidden");			
		} else {
			LoggerUtilities.createFeatureDelta(modification, DeltaProperties.FEATURE_VISIBILITY, "hidden", "visible");			
		}
		modificationSet.getModifications().add(modification);
	}
	
	@Optional
	@Inject 
	public void featureAbstractionChanged(@UIEventTopic(FDEventTable.LOGGER_CHANGED_FEATURE_ABSTRACTION) FXGraphicalFeature feature) {
		Modification modification = LoggerUtilities.createModification(feature, FDEventTable.LOGGER_CHANGED_FEATURE_ABSTRACTION);		
		if (feature.getFeature().isAbstract()) {
			LoggerUtilities.createFeatureDelta(modification, DeltaProperties.FEATURE_ABSTRACTION, "abstract", "concrete");			
		} else {
			LoggerUtilities.createFeatureDelta(modification, DeltaProperties.FEATURE_ABSTRACTION, "concrete", "abstract");			
		}
		modificationSet.getModifications().add(modification);
	}
	
	@Optional
	@Inject 
	public void featureMadeOptional(@UIEventTopic(FDEventTable.SET_FEATURE_OPTIONAL) FXGraphicalFeature feature) {
		if (feature.getFeature().isMandatory()) {
			// only log this change if feature is not ALREADY optional
			Modification modification = LoggerUtilities.createModification(feature, FDEventTable.LOGGER_CHANGED_FEATURE_VARIABILITY);		
			LoggerUtilities.createFeatureDelta(modification, DeltaProperties.FEATURE_VARIABILITY, "mandatory", "optional");			
			modificationSet.getModifications().add(modification);
		}
	}
	
	@Optional
	@Inject 
	public void featureMadeMandatory(@UIEventTopic(FDEventTable.SET_FEATURE_MANDATORY) FXGraphicalFeature feature) {
		if (!feature.getFeature().isMandatory()) {
			// only log this change if feature is not ALREADY mandatory
			Modification modification = LoggerUtilities.createModification(feature, FDEventTable.LOGGER_CHANGED_FEATURE_VARIABILITY);		
			LoggerUtilities.createFeatureDelta(modification, DeltaProperties.FEATURE_VARIABILITY, "optional", "mandatory");
			modificationSet.getModifications().add(modification);
		}
	}
	
	@Optional
	@Inject 
	public void lineToParentFeatureRemoved(@UIEventTopic(FDEventTable.LOGGER_REMOVED_LINE_TO_PARENT_FEATURE) FXGraphicalFeature feature) {
		currentParentFeatureID = feature.getFeature().getIdentifier();
	}
	
	@Optional
	@Inject 
	public void lineToParentFeatureReset(@UIEventTopic(FDEventTable.LOGGER_RESET_LINE_TO_PARENT_FEATURE) FXGraphicalFeature feature) {
		Modification modification = LoggerUtilities.createModification(feature, FDEventTable.LOGGER_RESET_LINE_TO_PARENT_FEATURE);	
		LoggerUtilities.createFeatureDelta(modification, DeltaProperties.LINE_TO_PARENT_RESET, Integer.toString(currentParentFeatureID), Integer.toString(feature.getParentFxFeature().getFeature().getIdentifier()));
		modificationSet.getModifications().add(modification);
	}
	
	@Optional
	@Inject 
	public void featureSelectedToRename(@UIEventTopic(FDEventTable.LOGGER_SELECTED_FEATURE_TO_RENAME) Feature feature) {
		selectedFeatureName = feature.getName();
	}
	
	@Optional
	@Inject 
	public void featureRenamed(@UIEventTopic(FDEventTable.LOGGER_RENAMED_FEATURE) Feature feature) {
		Modification modification = LoggerUtilities.createModification(feature, FDEventTable.LOGGER_RENAMED_FEATURE);	
		LoggerUtilities.createFeatureDelta(modification, DeltaProperties.FEATURE_NAME, selectedFeatureName, feature.getName());
		modificationSet.getModifications().add(modification);
	}
	
	@Optional
	@Inject 
	public void featureSelectedToChangeVariabilityGroup(@UIEventTopic(FDEventTable.LOGGER_SELECTED_FEATURE_TO_CHANGE_VARIABILITY_GROUP) Feature feature) {
		selectedFeatureVariabilityGroup = LoggerUtilities.getFeatureVariabilityGroup(feature);
	}

	@Inject
	@Optional
	public void featureChangedVariabilityGroup(@UIEventTopic(FDEventTable.LOGGER_CHANGED_FEATURE_VARIABILITY_GROUP) Feature feature) {
		Modification modification = LoggerUtilities.createModification(feature, FDEventTable.LOGGER_CHANGED_FEATURE_VARIABILITY_GROUP);	
		LoggerUtilities.createFeatureDelta(modification, DeltaProperties.FEATURE_GROUP_VARIABILITY, selectedFeatureVariabilityGroup,
				LoggerUtilities.getFeatureVariabilityGroup(feature));
		modificationSet.getModifications().add(modification);
	}
	
	@Inject
	@Optional
	public void splitFeature(@UIEventTopic(FDEventTable.LOGGER_SELECTED_FEATURE_TO_SPLIT) FXGraphicalFeature feature) {
		Modification modification = LoggerUtilities.createModification(feature, FDEventTable.LOGGER_SELECTED_FEATURE_TO_SPLIT);	
		modificationSet.getModifications().add(modification);
	}

	@Override
	public void exportLogs() {
		finalFeatureModelName = currentDiagram.getRoot().getName();
		ServiceContainer services = ContextInjectionFactory.make(ServiceContainer.class, EclipseContextFactory.create());
		FileTreeElement root = services.workspaceFileSystem.getWorkspaceDirectory();
		Path rootPath = FileHandlingUtility.getPath(root);
		Path projectPath = rootPath.resolve("");		
		FeatureDiagramModificationSetSerializer.save(modificationSet, projectPath.resolve(DiagramLoggerFactory.getExportLocation().getAbsolutePath()).toUri() +
				initialFeatureModelName + 
				"_to_" + finalFeatureModelName + FDStringTable.FD_CHANGE_SEQUENCE_FILE_ENDING);
	}
}
