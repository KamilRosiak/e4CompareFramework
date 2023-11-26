package de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.impl;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.enums.NodeType;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.interfaces.NodeDecorator;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.stringtable.FileTable;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class IEC61131NodeDecorator implements NodeDecorator {
	public static final String POU = "icons/iec61131/pou_24.png";
	public static final String POUS = "icons/iec61131/pous_24.png";
	public static final String RESOURCE = "icons/iec61131/resource_24.png";
	public static final String TASK = "icons/iec61131/task_24.png";
	public static final String VARIABLES = "icons/iec61131/variable_24.png";
	public static final String ACTION = "icons/iec61131/action_24.png";
	public static final String ACTIONS = "icons/iec61131/actions_24.png";
	public static final String TRANSITION = "icons/iec61131/transition_24.png";
	public static final String TRANSITIONS = "icons/iec61131/transitions_24.png";
	public static final String IMPLEMENTATION = "icons/iec61131/implementation_24.png";
	public static final String STATEMENT = "icons/iec61131/statement_16.png";
	public static final String STEP = "icons/iec61131/step_24.png";
	public static final String PARAM = "icons/iec61131/param_24.png";
	public static final String PARAMS = "icons/iec61131/params_24.png";

	public IEC61131NodeDecorator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public TreeItem<Node> decorateNode(TreeItem<Node> node) {
		node.setGraphic(new HBox(getVariabilityImage(node), getTypeImage(node.getValue())));

		// create node image with variability and type image
		String detail = getNameDetails(node.getValue());
		if (!detail.isEmpty()) {
			node.getValue().setRepresentation(node.getValue().getNodeType() + ": " + detail);
		} else {
			node.getValue().setRepresentation(node.getValue().getNodeType());
		}
		return node;
	}

	private String getNameDetails(Node node) {
		String nodeType = node.getNodeType();

		if (nodeType.equals(NodeType.CONFIGURATION.toString())) {
			return "" + node.getAttributeForKey("identifier").getValue(0).getValue();
		}

		if (nodeType.equals(NodeType.POUS.toString())) {
			return "POUs";
		}
		if (nodeType.equals(NodeType.POU.toString())) {
			return "" + node.getAttributeForKey("name").getValue(0);
		}
		if (nodeType.equals(NodeType.RESOURCES.toString()) || node.getNodeType().equals(NodeType.RESOURCE.toString())) {
			return "";
		}
		if (nodeType.equals(NodeType.TASK.toString()) || node.getNodeType().equals(NodeType.TASKS.toString())) {
			return "";
		}
		if (nodeType.equals(NodeType.POU.toString())) {
			return "";
		}
		if (nodeType.equals(NodeType.GLOBAL_VARIABELS.toString()) || nodeType.equals(NodeType.VARIABLE.toString())) {
			return "";
		}
		if (nodeType.equals(NodeType.ACTION.toString())) {
			return "" + node.getAttributeForKey("name").getValue(0);
		}
		if (nodeType.equals(NodeType.ACTIONS.toString())) {
			return "";
		}
		if (nodeType.equals(NodeType.IMPLEMENTATION.toString())) {
			return "";
		}

		if (nodeType.equals(NodeType.ASSIGNMENT.toString())) {
			return "" + node.getAttributeForKey("value").getValue(0);
		}

		if (nodeType.equals(NodeType.METHOD_CALL.toString())) {
			return "" + node.getAttributeForKey("symbol").getValue(0);
		}

		if (nodeType.equals(NodeType.VARIABLE.toString())) {
			return "" + node.getAttributeForKey("name").getValue(0) + " : "
					+ node.getAttributeForKey("type").getValue(0);
		}

		if (nodeType.equals(NodeType.STEP.toString())) {
			return "" + node.getAttributeForKey("name").getValue(0);
		}
		return "";
	}

	@Override
	public boolean isSupportedTree(Tree tree) {
		return true;
	}

	@Override
	public String toString() {
		return "IEC61131-3 Family Model";
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
			return new ImageView(TRANSITIONS);
		}
		if (nodeType.equals(NodeType.INCOMING_TRANSISTION.toString())
				|| nodeType.equals(NodeType.OUTGOING_TRANSISTION.toString())) {
			return new ImageView(TRANSITION);
		}
		if (nodeType.equals(NodeType.METHOD_CALL.toString()) || nodeType.equals(NodeType.ASSIGNMENT.toString())) {
			return new ImageView(STATEMENT);
		}
		if (nodeType.equals(NodeType.STEP.toString())) {
			return new ImageView(STEP);
		}
		if (nodeType.equals(NodeType.PARAMETER.toString())) {
			return new ImageView(PARAM);
		}
		if (nodeType.equals(NodeType.PARAMETERS.toString())) {
			return new ImageView(PARAMS);
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
