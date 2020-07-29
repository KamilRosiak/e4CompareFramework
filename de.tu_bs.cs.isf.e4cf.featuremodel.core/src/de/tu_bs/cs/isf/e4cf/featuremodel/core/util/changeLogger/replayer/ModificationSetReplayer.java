package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.changeLogger.replayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import FeatureDiagram.FeatureDiagramm;
import FeatureDiagramModificationSet.FeatureModelModificationSet;
import FeatureDiagramModificationSet.Modification;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDEventTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.elements.FXGraphicalFeature;

@Creatable
@Singleton
public class ModificationSetReplayer {
	
	FeatureModelModificationSet _modificationSet;
	ServiceContainer _services;
	List<FXGraphicalFeature> _features;
	Map<Integer, FXGraphicalFeature> _featureIDs = new HashMap<Integer, FXGraphicalFeature>();
	
	Integer featureID_ADD_operation;
	boolean addFeatureBelow;
	
	public ModificationSetReplayer(FeatureModelModificationSet modificationSet, List<FXGraphicalFeature> features, ServiceContainer services) {
		_modificationSet = modificationSet;
		_services = services;
		_features = features;
		retrieveFeatureIDs(_features);
	}

	private void retrieveFeatureIDs(List<FXGraphicalFeature> features) {
		for (FXGraphicalFeature feature : features) {
			_featureIDs.put(feature.getFeature().getIdentifier(), feature);
		}
	}

	public void replay(FeatureDiagramm currentModel) {
		for (Modification modification : _modificationSet.getModifications()) {
			if (dispatchModification(modification)) {
				_services.eventBroker.send(FDEventTable.REPLAY_FEATURE_DIAGRAM_CHANGED, currentModel);		
			} else {
				// process feature id not present anymore
				throw new NullPointerException("Feature with id="+modification.getFeatureID()+" is not recognized by the modification replayer.");
			}
		}
	}
	
	private boolean dispatchModification(Modification modification) {
		
		//check if the 
		if(_featureIDs.get(modification.getFeatureID()) == null) {
			return false;
		}
		
		switch(modification.getModificationType()) {
			case FDEventTable.LOGGER_CHANGED_FEATURE_ABSTRACTION:
				return replayFeatureAbstraction(modification);
			case FDEventTable.LOGGER_SELECTED_FEATURE_TO_CHANGE_SUBFEATURES_VISIBILITY:
				return replaySubfeatureVisibility(modification);
			case FDEventTable.LOGGER_CHANGED_FEATURE_VARIABILITY:
				return replayFeatureVariability(modification);
			case FDEventTable.LOGGER_ADD_FEATURE_BELOW:
				return replayFeatureAddedBelow(modification);
			case FDEventTable.LOGGER_ADD_FEATURE_ABOVE:
				return replayFeatureAddedAbove(modification);
			case FDEventTable.LOGGER_ADD_FEATURE:
				return replayFeatureAdded(modification);
			case FDEventTable.LOGGER_RENAMED_FEATURE:
				return replayFeatureRename(modification);
			case FDEventTable.LOGGER_REMOVE_FEATURE:
				return replayFeatureDelete(modification);
			case FDEventTable.LOGGER_CHANGED_FEATURE_VARIABILITY_GROUP:
				return replayFeatureGroupVariability(modification);
			case FDEventTable.LOGGER_ADD_FEATURE_TO_SELECTION:
				return replayFeatureAddedToSelection(modification);
			case FDEventTable.LOGGER_REMOVE_FEATURE_FROM_SELECTION:
				return replayFeatureRemovedFromSelection(modification);
			case FDEventTable.LOGGER_RESET_SELECTED_FEATURES:
				return replayFeatureSelectionResetted();
			case FDEventTable.LOGGER_MOVE_SELECTED_FEATURES_UNDER_FEATURE:
				return replayFeatureSelectionMoveUnderFeautre(modification);
			case FDEventTable.LOGGER_GROUP_SELECTED_FEATURES_IN_FEATURE:
				return replayFeatureSelectionGroupInFeautre(modification);
		}
		return true;
	}

	private boolean replayFeatureSelectionGroupInFeautre(Modification modification) {
		_services.eventBroker.send(FDEventTable.GROUP_SELECTED_FEATURES_IN_FEATURE, _featureIDs.get(modification.getFeatureID()));
		return true;
	}

	private boolean replayFeatureSelectionMoveUnderFeautre(Modification modification) {
		_services.eventBroker.send(FDEventTable.MOVE_SELECTED_FEATURES_UNDER_FEATURE, _featureIDs.get(modification.getFeatureID()));
		return true;
	}

	private boolean replayFeatureSelectionResetted() {
		_services.eventBroker.send(FDEventTable.RESET_SELECTED_FEATURES, "");
		return true;
	}

	private boolean replayFeatureRemovedFromSelection(Modification modification) {
		_services.eventBroker.send(FDEventTable.SET_SELECTED_FEATURE, _featureIDs.get(modification.getFeatureID()));
		return true;
	}

	private boolean replayFeatureAddedToSelection(Modification modification) {
		_services.eventBroker.send(FDEventTable.SET_SELECTED_FEATURE, _featureIDs.get(modification.getFeatureID()));
		return true;
	}

	private boolean replayFeatureGroupVariability(Modification modification) {
		String valueAfterChange = modification.getDelta().getValueAfterChange();
		if (valueAfterChange.equals("alternative")) {
			_services.eventBroker.send(FDEventTable.SET_FEATURE_ALTERNATIVE_GROUP_BY_LOGGER, _featureIDs.get(modification.getFeatureID()));
		} else if (valueAfterChange.equals("and")) {
			_services.eventBroker.send(FDEventTable.SET_FEATURE_AND_GROUP_BY_LOGGER, _featureIDs.get(modification.getFeatureID()));
		} else if (valueAfterChange.equals("or")) {
			_services.eventBroker.send(FDEventTable.SET_FEATURE_OR_GROUP_BY_LOGGER, _featureIDs.get(modification.getFeatureID()));
		}
		
		return true;
	}

	private boolean replayFeatureDelete(Modification modification) {
		_services.eventBroker.send(FDEventTable.REMOVE_FEATURE_BY_LOGGER, _featureIDs.get(modification.getFeatureID()));
		retrieveFeatureIDs(_features);
		return true;
	}

	private boolean replayFeatureRename(Modification modification) {
		_services.eventBroker.send(FDEventTable.SET_CURRENT_FEATURE, _featureIDs.get(modification.getFeatureID()));
		_services.eventBroker.send(FDEventTable.SET_FEATURE_NAME, modification.getDelta().getValueAfterChange());
		return true;
	}

	private boolean replayFeatureAdded(Modification modification) {
		retrieveFeatureIDs(_features);
		return true;
	}

	private boolean replayFeatureAddedAbove(Modification modification) {
		_services.eventBroker.send(FDEventTable.ADD_FEATURE_ABOVE, _featureIDs.get(modification.getFeatureID()));
		return true;
	}

	private boolean replayFeatureAddedBelow(Modification modification) {
		_services.eventBroker.send(FDEventTable.ADD_FEATURE_BELOW, _featureIDs.get(modification.getFeatureID()));			
		return true;
	}
	private boolean replayFeatureVariability(Modification modification) {
		if (modification.getDelta().getValuePriorChange().equals("mandatory")){
			_services.eventBroker.send(FDEventTable.SET_FEATURE_OPTIONAL, _featureIDs.get(modification.getFeatureID()));			
		} else {
			_services.eventBroker.send(FDEventTable.SET_FEATURE_MANDATORY, _featureIDs.get(modification.getFeatureID()));
		}
		return true;
	}

	private boolean replaySubfeatureVisibility(Modification modification) {
		_services.eventBroker.send(FDEventTable.CHANGE_SUBFEATURES_VISIBILITY, _featureIDs.get(modification.getFeatureID()));		
		return true;
	}

	/**
	 * Changes the Abstraction of a feature. Internals are done in the FeatureModelEditorView, including
	 * assessing the current abstraction of a feature and then switching it. Hence, there is not separate
	 * method of making a feature concrete
	 * @param modification
	 * @return
	 */
	public boolean replayFeatureAbstraction(Modification modification) {	
		_services.eventBroker.send(FDEventTable.SET_FEATURE_ABSTRACT, _featureIDs.get(modification.getFeatureID()));			
		return true;
	}
	
	public boolean replayFeatureSplit(Modification modification) {
		_services.eventBroker.send(FDEventTable.SPLIT_FEATURE, _featureIDs.get(modification.getFeatureID()));			
		return true;
	}
}
