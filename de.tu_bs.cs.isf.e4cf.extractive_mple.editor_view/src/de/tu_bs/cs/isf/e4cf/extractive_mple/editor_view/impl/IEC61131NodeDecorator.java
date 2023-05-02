package de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.impl;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.interfaces.NodeDecorator;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.stringtable.FileTable;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

public class IEC61131NodeDecorator implements NodeDecorator {
	public static final String POU = "icons/iec61131/pou_24.png";
	public static final String POUS = "icons/iec61131/pous_24.png";
	public static final String RESOURCE = "icons/iec61131/resource_24.png";
	public static final String TASK = "icons/iec61131/task_24.png";
	public static final String VARIABLES = "icons/iec61131/variable_24.png";
	public static final String ACTION = "icons/iec61131/action_24.png";
	public static final String ACTIONS = "icons/iec61131/actions_24.png";
	public static final String TRANSITION = "icons/iec61131/transition_24.png";
	public static final String IMPLEMENTATION = "icons/iec61131/implementation_24.png";

	public IEC61131NodeDecorator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public TreeItem<Node> decorateNode(TreeItem<Node> node) {
		node.setGraphic(getTypeImage(node.getValue()));
		node.getValue().setRepresentation(node.getValue().getNodeType());
		return node;
	}

	@Override
	public boolean isSupportedTree(Tree tree) {
		return true;
	}

	@Override
	public String toString() {
		return "Family Model";
	}

	public ImageView getTypeImage(Node node) {
		String nodeType = node.getNodeType();
		if (nodeType.equals(NodeType.POUS.toString())) {
			return new ImageView(POUS);
		}
		if (nodeType.equals(NodeType.POU.toString())) {
			return new ImageView(POU);
		}
		if (nodeType.equals(NodeType.RESOURCES.toString()) || node.getNodeType().equals(NodeType.RESOURCE.toString())) {
			return new ImageView(RESOURCE);
		}

		if (nodeType.equals(NodeType.TASK.toString()) || node.getNodeType().equals(NodeType.TASKS.toString())) {
			return new ImageView(TASK);
		}

		if (nodeType.equals(NodeType.POU.toString())) {
			return new ImageView(POU);
		}

		if (nodeType.equals(NodeType.GLOBAL_VARIABELS.toString()) || nodeType.equals(NodeType.VARIABLE.toString())) {
			return new ImageView(VARIABLES);
		}

		if (nodeType.equals(NodeType.ACTION.toString())) {
			return new ImageView(ACTION);
		}
		if (nodeType.equals(NodeType.ACTIONS.toString())) {
			return new ImageView(ACTION);
		}
		if (nodeType.equals(NodeType.IMPLEMENTATION.toString())) {
			return new ImageView(IMPLEMENTATION);
		}
		if (nodeType.equals(NodeType.INCOMING_TRANSISTIONS.toString())
				|| nodeType.equals(NodeType.OUTGOING_TRANSISTIONS.toString())) {
			return new ImageView(TRANSITION);
		}

		return new ImageView();
	}

	public ImageView getVariabilityImage(TreeItem<Node> node) {
		try {
			switch (node.getValue().getVariabilityClass()) {
			case MANDATORY:
				return new ImageView(FileTable.FV_MANDATORY_16);
			case ALTERNATIVE:
				return new ImageView(FileTable.FV_ALTERNATIVE_16);
			case OPTIONAL:
				return new ImageView(FileTable.FV_OPTIONAL_16);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ImageView();
	}

}
