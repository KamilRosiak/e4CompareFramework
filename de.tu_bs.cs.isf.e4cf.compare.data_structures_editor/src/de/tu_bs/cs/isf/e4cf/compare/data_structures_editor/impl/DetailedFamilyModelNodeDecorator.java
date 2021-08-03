package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.impl;

import java.util.NoSuchElementException;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import javafx.scene.control.TreeItem;

public class DetailedFamilyModelNodeDecorator extends FamilyModelNodeDecorator {
	
	@Override
	public TreeItem<Node> decorateNode(TreeItem<Node> node) {
		super.decorateNode(node);
		Node nodeimpl = node.getValue();
		String nodeName = getAttributeValueFromNode(nodeimpl, "Name");
		String nodeValue = getAttributeValueFromNode(nodeimpl, "Value");
		String nodeOperator = getAttributeValueFromNode(nodeimpl, "Operator");

		nodeimpl.setRepresentation(nodeimpl.getStandardizedNodeType() + nodeName + nodeValue + nodeOperator);

		return node;
	}
	
	private String getAttributeValueFromNode(Node n, String key) {
		try {
			return " " + n.getAttributeForKey(key).getAttributeValues().get(0).getValue().toString();
		} catch (NoSuchElementException e) {
			return "";
		}
	}
	
	@Override
	public String toString() {
		return "Code Family View";
	}
}
