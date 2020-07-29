package de.tu_bs.cs.isf.e4cf.featuremodel.core.util.helper;

import java.util.ArrayList;
import java.util.List;

import FeatureDiagram.ArtifactReference;
import FeatureDiagram.Feature;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table.FDDialogStringTable;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.dialogs.FMESimpleDecsionDialog;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.util.dialogs.FMESimpleNoticeDialog;
import de.tu_bs.cs.isf.e4cf.featuremodel.core.view.elements.FXGraphicalFeature;

public class FeatureModelEvaluator {
	
	public static boolean isFeatureFusingPossible(List<FXGraphicalFeature> selectedFeatures, FXGraphicalFeature newParentFeature) {
		
		if (selectedFeatures.contains(newParentFeature)) {
			new FMESimpleNoticeDialog(FDDialogStringTable.FD_DIALOG_HEADER_OPERATION_ERROR, 
					FDDialogStringTable.FD_DIALOG_OPERATION_ERROR_MSG_GROUP_CONTAINMENT);
			return false;
		}
		/*
		if (selectedFeatures.size() < 2) {
			new FMESimpleNoticeDialog(FDDialogStringTable.FD_DIALOG_HEADER_OPERATION_ERROR, 
					FDDialogStringTable.FD_DIALOG_OPERATION_ERROR_GROUP_NUMBER_TO_LOW);
			return false;
		}
		*/
		
//		Set<Integer> parentFeatureIDs = new HashSet<Integer>();
//		
//		// add all parent IDs
//		for (FXGraphicalFeature feature : selectedFeatures) {
//			if (feature.getParentFxFeature() != null) {
//				parentFeatureIDs.add(feature.getParentFxFeature().getFeature().getIdentifier());				
//			}
//		}
//		
//		// remove IDs ( may also be parent IDs )
//		for (FXGraphicalFeature feature : selectedFeatures) {
//			parentFeatureIDs.remove(feature.getFeature().getIdentifier());
//		}
//
//		// we can only merge features that form one branch, hence no two parent IDs exist
//		if (parentFeatureIDs.size() != 1) {
//			new FMESimpleNoticeDialog(FDDialogStringTable.FD_DIALOG_HEADER_OPERATION_ERROR, 
//					FDDialogStringTable.FD_DIALOG_OPERATION_ERROR_MSG_GROUP_BRANCHES);
//			return false;
//		}
		return true;
	}

	
	/**
	 * Evaluates whether a feature can be split, hence splitting it in two features.
	 * @param feature Feature selected to be split
	 * @return False if the feature is the root, TRUE otherwise
	 */
	public static boolean isFeatureSplitPossible(FXGraphicalFeature feature) {
		
		// we can not split the root feature
		if (feature.getParentFxFeature() == null) {
			new FMESimpleNoticeDialog(FDDialogStringTable.FD_DIALOG_HEADER_OPERATION_ERROR, 
					FDDialogStringTable.FD_DIALOG_OPERATION_ERROR_MSG_SPLIT_ROOT);
			return false;
		} else if (feature.getFeature().getArtifactReferences().isEmpty()) {
			System.out.println("I can not split this feature - artifact references size");
			return false;
		} else {
			int t = getNumberOfReferencedImplArtifacts(feature.getFeature());
			
			if (t == 0) {
				new FMESimpleNoticeDialog(FDDialogStringTable.FD_DIALOG_HEADER_OPERATION_ERROR, 
						FDDialogStringTable.FD_DIALOG_OPERATION_ERROR_MSG_SPLIT_NO_REFS);
				return false;
			} else if (t == 1) {
				new FMESimpleNoticeDialog(FDDialogStringTable.FD_DIALOG_HEADER_OPERATION_ERROR, 
						FDDialogStringTable.FD_DIALOG_OPERATION_ERROR_MSG_SPLIT_TOO_LOW_REFS);
				return false;
			}
		}
		return true;
	}

	
	private static Integer getNumberOfReferencedImplArtifacts(Feature feature) {
		int number = 0;
		for (ArtifactReference artifactRef : feature.getArtifactReferences()) {
			number += artifactRef.getReferencedArtifactIDs().size();
		}
		return number;
	}


	/**
	 * Evaluates if the target feature is itself contained within the selected features, which shall be relocated
	 * @param selectedFeatures A set of features selected to be relocated
	 * @param feature The feature under which selected features shall be moved to
	 * @return FALSE, if feature is within selectedFeatures, TRUE otherwise
	 */
	public static boolean isFeatureMovePossible(List<FXGraphicalFeature> selectedFeatures, FXGraphicalFeature feature) {
		if (selectedFeatures.contains(feature)) {
			new FMESimpleNoticeDialog(FDDialogStringTable.FD_DIALOG_HEADER_OPERATION_ERROR, 
					FDDialogStringTable.FD_DIALOG_OPERATION_ERROR_MSG_MOVE_CONTAINMENT);
			return false;
		}
		return true;
	}

	/**
	 * Retrieves the root features from a set of selected features. Features can be selected across branches
	 * and, hence multiple root nodes can exist within a selection. A root feature within the selection is a 
	 * feature with a parent feature "PF", with "PF" not being contained within the selectedFeatures 
	 * @param selectedFeatures A set of selected features
	 * @return A subset of the selectedFeatures, features with a parent feature not being contained in selectedFeatures
	 */
	public static List<FXGraphicalFeature> getRootNodesFromSelection(List<FXGraphicalFeature> selectedFeatures) {
		List<FXGraphicalFeature> subRoots = new ArrayList<FXGraphicalFeature>();
		
		for (FXGraphicalFeature feature : selectedFeatures) {
			if (feature.getParentFxFeature() != null && 
					!selectedFeatures.contains(feature.getParentFxFeature())) {
				subRoots.add(feature);								
			}
		}
		return subRoots;
	}
	
	/**
	 * Assesses dangling parent child relations within selected features and provides a warning to the user 
	 * whether the user want to continue or abort. 
	 * The following feature branch exists: 	A -> B -> C and A and C are selected, resulting in B being
	 * affected by the move operation but not explicitly selected. Hence, a warning is issued. 
	 * @param subRoots root feature within the previously selected features
	 * @return Result of user dialog whether he wants to continue or abort.
	 */
	public static boolean assessIntermediateFeatures(List<FXGraphicalFeature> subRoots) {
		for (FXGraphicalFeature subRoot : subRoots) {
			FXGraphicalFeature currentFeature = subRoot;
			while (currentFeature.getParentFxFeature() != null) {
				if (subRoots.contains(currentFeature.getParentFxFeature())){
					return new FMESimpleDecsionDialog(FDDialogStringTable.FD_DIALOG_HEADER_OPERATION_WARNING, 
							FDDialogStringTable.FD_DIALOG_OPERATION_WARNING_MSG_MOVE_SUBROOTS).show();
				}
				currentFeature = currentFeature.getParentFxFeature();
			}  
		}
		return true;
	}
	
	/**
	 * Evaluates if the move operation is invalid as it would cause the moving of a parent under one of its 
	 * current children. The parents of the target feature are traversed upwards and if one of the selected
	 * root features is encountered on the way upwards, we have detected a cycle. Thus, we are trying to add
	 * a parent (root node) under one of its children. 
	 * @param rootNodes Root nodes of the current feature selection
	 * @param targetFeature The feature under which the selected features shall be added
	 * @return FALSE, if the targetFeature is a child of either rootNode, TRUE otherwise
	 */
	public static boolean assessParentChildRelation(List<FXGraphicalFeature> rootNodes, FXGraphicalFeature targetFeature) {
		
		if (targetFeature.getParentFxFeature() != null) {
			FXGraphicalFeature currentParent = targetFeature.getParentFxFeature();			
			while (currentParent != null) {
				if (rootNodes.contains(currentParent)) {
					new FMESimpleNoticeDialog(FDDialogStringTable.FD_DIALOG_HEADER_OPERATION_ERROR, 
							FDDialogStringTable.FD_DIALOG_OPERATION_ERROR_MSG_MOVE_PARENT_CHILD);
					return false;
				}
				currentParent = currentParent.getParentFxFeature();
			}
		}
		return true;
	}
}
