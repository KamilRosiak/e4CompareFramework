package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.impl;

import java.util.NoSuchElementException;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import javafx.scene.control.TreeItem;

public class DetailedFamilyModelNodeDecorator extends FamilyModelNodeDecorator {
	
	@Override
	public TreeItem<Node> decorateNode(TreeItem<Node> node) {
		super.decorateNode(node);
		Node nodeimpl = node.getValue();
		String nodeName = "";
		
		try {
			nodeName = " " + nodeimpl.getAttributeForKey("Name").getAttributeValues().get(0).getValue().toString();
		} catch (NoSuchElementException e) {}
		
		nodeimpl.setRepresenation(nodeimpl.getStandardizedNodeType() + nodeName);

		return node;
	}
	
	@Override
	public String toString() {
		return "Code Family View";
	}
}
