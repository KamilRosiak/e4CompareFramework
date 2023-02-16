package de.tu_bs.cs.isf.e4cf.extractive_mple.editor_view.impl;

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
		String confusion = getAttributeValueFromNode(nodeimpl, "Confusion");
		confusion = confusion == "" ? "" : "[" + confusion.trim() + "] ";

		nodeimpl.setRepresentation(confusion + nodeimpl.getStandardizedNodeType() + nodeName + nodeValue + nodeOperator);

		return node;
	}
	
	private String getAttributeValueFromNode(Node n, String key) {
		try {
			return " " + n.getAttributeForKey(key).getAttributeValues().stream().map(
					v -> v.getValue().toString() + " ").reduce("", String::concat);
		} catch (NoSuchElementException e) {
			return "";
		}
	}
	
	@Override
	public String toString() {
		return "Code Family View";
	}
}
