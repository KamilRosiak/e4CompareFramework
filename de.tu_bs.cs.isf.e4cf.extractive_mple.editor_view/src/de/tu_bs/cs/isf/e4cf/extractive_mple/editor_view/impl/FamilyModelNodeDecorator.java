package de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.impl;

import java.util.NoSuchElementException;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Tree;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.interfaces.NodeDecorator;
import de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.stringtable.FileTable;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

public class FamilyModelNodeDecorator implements NodeDecorator {

	public FamilyModelNodeDecorator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public TreeItem<Node> decorateNode(TreeItem<Node> node) {
		try {
			switch (node.getValue().getVariabilityClass()) {
			case MANDATORY:
				node.setGraphic(new ImageView(FileTable.FV_MANDATORY_16));
				break;
			case ALTERNATIVE:
				node.setGraphic(new ImageView(FileTable.FV_ALTERNATIVE_16));
				break;
			case OPTIONAL:
				node.setGraphic(new ImageView(FileTable.FV_OPTIONAL_16));
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String detail = "";
		try {
			Attribute scope = node.getValue().getAttributeForKey("Scope");
			if (scope != null) {
				detail += (String) scope.getValue(0).getValue() + ".";
			}
		} catch (NoSuchElementException e) {
			// value not present
		}
		try {
			Attribute name = node.getValue().getAttributeForKey("Name");
			if (name != null) {
				detail += (String) name.getValue(0).getValue();
			}
		} catch (NoSuchElementException e) {
			// value not present
		}
		try {
			Attribute value = node.getValue().getAttributeForKey("Value");
			if (value != null) {
				detail += (String) value.getValue(0).getValue();
			}
		} catch (NoSuchElementException e) {
			// value not present
		}
		try {
			Attribute type = node.getValue().getAttributeForKey("Type");
			if (type != null) {
				detail += ":" + (String) type.getValue(0).getValue();
			}
		} catch (NoSuchElementException e) {
			// value not present
		}
		
		try {
			Attribute target = node.getValue().getAttributeForKey("Target");
			if (target != null) {
				detail += (String) target.getValue(0).getValue();
			}
		} catch (NoSuchElementException e) {
			// value not present
		}
		try {
			Attribute operator = node.getValue().getAttributeForKey("Operator");
			if (operator != null) {
				detail += (String) operator.getValue(0).getValue();
			}
		} catch (NoSuchElementException e) {
			// value not present
		}
		
		if (!detail.isEmpty()) {
			node.getValue().setRepresentation(node.getValue().getNodeType() +  ": " + detail);
		} else {
			node.getValue().setRepresentation(node.getValue().getNodeType());
		}
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

}
