package de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table;

public class FDDialogStringTable {
	
	public static final String FD_DIALOG_HEADER_OPERATION_ERROR = "Feature Model Error";
	public static final String FD_DIALOG_HEADER_OPERATION_WARNING = "Feature Model Warning";
	
	public static final String FD_DIALOG_OPERATION_ERROR_GROUP_NUMBER_TO_LOW = "Select at least two features to group them together.";
	public static final String FD_DIALOG_OPERATION_ERROR_MSG_GROUP_BRANCHES = "I can not group features across multiple branches";
	public static final String FD_DIALOG_OPERATION_ERROR_MSG_GROUP_CONTAINMENT = "You can not group together a feauture with itself.";
	public static final String FD_DIALOG_OPERATION_ERROR_MSG_SPLIT_NOT_LEAF = "You can only split a feature when it is a leaf.";
	public static final String FD_DIALOG_OPERATION_ERROR_MSG_SPLIT_ROOT = "You can not split the root of the feature model.";
	public static final String FD_DIALOG_OPERATION_ERROR_MSG_SPLIT_TOO_LOW_REFS = "You can not split this feature as it contains only one references to implementation artifacts. You can not split collapsed or single implementation artifacts";
	public static final String FD_DIALOG_OPERATION_ERROR_MSG_SPLIT_NO_REFS = "You can not split this feature as it contains no references at all.";
	
	public static final String FD_DIALOG_OPERATION_ERROR_MSG_MOVE_CONTAINMENT = "You can not relocate to a feature, which itself is to be relocated.";
	public static final String FD_DIALOG_OPERATION_WARNING_MSG_MOVE_SUBROOTS = "Your selection contains intermediate features, which are not part of the selection. Do you want to proceed?";
	public static final String FD_DIALOG_OPERATION_ERROR_MSG_MOVE_PARENT_CHILD = "You can not move a parent feature under one of its current children.";
}
